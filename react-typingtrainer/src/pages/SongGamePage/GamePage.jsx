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
  const [showResult, setShowResult] = useState(false);
  const [startTime, setStartTime] = useState(null);

  const hintCountRef = useRef(0);
  const hintRequestedRef = useRef(false);

  const intervalRef = useRef();

  useEffect(() => {
    const savedSettings = JSON.parse(sessionStorage.getItem("songGameSettings"));
    setSettings(savedSettings);
    setStartTime(new Date());

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
          hintCountRef.current = 0;
          hintRequestedRef.current = false;
          setProblemTime(60);
          setHintTimer(savedSettings.hintInterval);
          setTotalCount((prev) => prev + 1);
          break;
        case "hint":
          if (hintCountRef.current === 0) {
            setHint1(message.data);
          } else if (hintCountRef.current === 1) {
            setHint2(message.data);
          }
          hintCountRef.current++;
          hintRequestedRef.current = false;
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
        // case "end":
        //   clearInterval(intervalRef.current);
        //   const endTime = new Date();
          
        //   // 디버깅을 위한 로그 추가
        //   console.log("startTime:", startTime);
        //   console.log("endTime:", endTime);
        //   console.log("userId:", sessionStorage.getItem("userId"));
        //   console.log("settings:", settings);
        //   console.log("correctCount:", correctCount);
        //   console.log("hintTimer:", hintTimer);
          
        //   const gameData = {
        //     type: "end",
        //     data: {
        //       userId: Number(sessionStorage.getItem("userId")),
        //       playtime: Math.floor((endTime - startTime) / 1000),
        //       correctCount: correctCount,
        //       genre: settings.genres,
        //       hintTime: hintTimer
        //     }
        //   };
          
        //   console.log("전송할 데이터:", gameData);
        //   ws.send(JSON.stringify(gameData));
        //   setShowResult(true);
        //   ws.close();
        //   break;
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
      // 전체 시간 타이머
      setGlobalTime((prev) => {
        if (prev <= 1) {
          const endTime = new Date();
          const gameData = {
            type: "end",
            data: {
              userId: Number(sessionStorage.getItem("userId")),
              playtime: Math.floor((endTime - startTime) / 1000),
              correctCount: correctCount,
              genre: settings.genres,
              hintTime: hintTimer
            }
          };
          console.log("전송할 데이터:", gameData);
          socket.send(JSON.stringify(gameData));
          clearInterval(intervalRef.current);
          setShowResult(true);
          window.location.href = "/song-game";
        }
        return prev - 1;
      });

      // 문제 제한 타이머
      setProblemTime((prev) => {
        if (prev <= 1) {
          socket.send(JSON.stringify({ type: "skip" }));
          return 60;
        }
        return prev - 1;
      });

      // 힌트 타이머
      setHintTimer((prev) => {
        if (
          prev <= 1 &&
          !hintRequestedRef.current &&
          hintCountRef.current < 2
        ) {
          socket.send(JSON.stringify({ type: "hint" }));
          hintRequestedRef.current = true;
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
        <div>
          문제 수: {correctCount}/{totalCount}
        </div>
      </div>

      <div className={styles.questionBox}>{lyrics}</div>

      <div className={styles.hintSection}>
        <div className={styles.hintBox}>
          <div>힌트1</div>
          <div>{hint1 || "정보 힌트 대기 중!"}</div>
        </div>
        <div className={styles.hintBox}>
          <div>힌트2</div>
          <div>{hint2 || "초성 힌트 대기 중!"}</div>
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

      {showResult && (
        <div className={styles.modalOverlay}>
          <div className={styles.resultModal}>
            <h2>게임 종료!</h2>
            <p>총 제한 시간: {settings.playtime}초</p>
            <p>맞춘 문제 수: {correctCount}개</p>
            <div className={styles.modalButtons}>
              <button onClick={() => window.location.href = "/song-game"}>다시 하기</button>
              <button onClick={() => window.location.href = "/"}>홈으로</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default GamePlayPage;
