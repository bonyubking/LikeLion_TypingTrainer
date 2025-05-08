import React, { useEffect, useState, useRef } from "react";
import styles from "./GamePage.module.css";

const GamePlayPage = () => {
  const [settings, setSettings] = useState(null);
  const [lyrics, setLyrics] = useState("문제가 여기에 표시됩니다");
  const [hint1, setHint1] = useState("");
  const [hint2, setHint2] = useState("");
  const [answer, setAnswer] = useState("");
  const [correctCount, setCorrectCount] = useState(0);
  const [totalCount, setTotalCount] = useState(0);
  const [socket, setSocket] = useState(null);

  const [globalTime, setGlobalTime] = useState(0);
  const [problemTime, setProblemTime] = useState(60);
  const [hintTimer, setHintTimer] = useState(0);

  const intervalRef = useRef();

  useEffect(() => {
    const savedSettings = JSON.parse(sessionStorage.getItem("songGameSettings"));
    setSettings(savedSettings);

    if (!savedSettings) {
      alert("게임 설정이 없습니다. 처음 화면으로 돌아갑니다.");
      window.location.href = "/song-game";
      return;
    }

    const ws = new WebSocket("ws://localhost:8082/game/song");
    setSocket(ws);

    ws.onopen = () => {
      ws.send(
        JSON.stringify({
          type: "startGame",
          data: {
            userId: sessionStorage.getItem("userId") || 0,
            genres: savedSettings.genres,
            hintInterval: parseInt(savedSettings.hintInterval),
            playtime: parseInt(savedSettings.playtime),
          },
        })
      );
      setGlobalTime(savedSettings.playtime);
      setHintTimer(savedSettings.hintInterval);
    };

    ws.onmessage = (event) => {
      const message = JSON.parse(event.data);
      switch (message.type) {
        case "question":
          setLyrics(message.lyrics);
          setAnswer("");
          setHint1("");
          setHint2("");
          setProblemTime(60);
          setTotalCount((prev) => prev + 1);
          break;
        case "hint":
          if (!hint1) setHint1(message.data);
          else setHint2(message.data);
          break;
        case "correct":
          setCorrectCount((prev) => prev + 1);
          break;
        case "wrong":
          alert("오답입니다!");
          break;
        case "timeout":
          alert("시간 초과!");
          break;
        case "skipped":
          alert("문제를 건너뜁니다.");
          break;
        case "end":
          alert("게임이 종료되었습니다.");
          ws.close();
          break;
        default:
          break;
      }
    };

    ws.onerror = (e) => {
      console.error("WebSocket error:", e);
    };

    ws.onclose = () => {
      console.log("WebSocket closed");
    };

    return () => {
      ws.close();
      clearInterval(intervalRef.current);
    };
  }, []);

  useEffect(() => {
    if (!socket || !settings) return;

    intervalRef.current = setInterval(() => {
      setGlobalTime((prev) => {
        if (prev <= 1) {
          socket.send(JSON.stringify({ type: "end" }));
          clearInterval(intervalRef.current);
          return 0;
        }
        return prev - 1;
      });

      setProblemTime((prev) => {
        if (prev <= 1) {
          socket.send(JSON.stringify({ type: "skip" }));
          return 60;
        }
        return prev - 1;
      });

      setHintTimer((prev) => {
        if (prev <= 1) {
          socket.send(JSON.stringify({ type: "hint" }));
          return settings.hintInterval;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(intervalRef.current);
  }, [socket, settings]);

  const handleSubmit = () => {
    if (socket && answer.trim()) {
      socket.send(
        JSON.stringify({
          type: "answer",
          input: answer.trim(),
        })
      );
    }
  };

  const handleSkip = () => {
    if (socket) {
      socket.send(JSON.stringify({ type: "skip" }));
    }
  };

  if (!settings) return null;

  return (
    <div className={styles.container}>
      <div className={styles.stats}>
        <div>남은 시간: {globalTime}s</div>
        <div>맞춘 문제 수: {correctCount}개</div>
        <div>문제 수: {correctCount}/{totalCount}</div>
      </div>

      <div className={styles.questionBox}>{lyrics}</div>

      <div className={styles.hintSection}>
        <div className={styles.hintBox}>
          <div>힌트1</div>
          <div>{hint1 || "(없음)"}</div>
        </div>
        <div className={styles.hintBox}>
          <div>힌트2</div>
          <div>{hint2 || "(없음)"}</div>
        </div>
      </div>

      <div className={styles.timer}>문제 타이머: {problemTime}s</div>

      <div className={styles.inputSection}>
        <div className={styles.inputGroup}>
          <input
            className={styles.inputField}
            placeholder="정답을 입력하세요"
            value={answer}
            onChange={(e) => setAnswer(e.target.value)}
          />
          <button className={styles.submitButton} onClick={handleSubmit}>
            정답
          </button>
          <button className={styles.skipButton} onClick={handleSkip}>
            스킵
          </button>
        </div>
      </div>
    </div>
  );
};

export default GamePlayPage;
