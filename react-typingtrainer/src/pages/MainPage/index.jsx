import React, { useState, useEffect, useRef } from "react";
import styles from "./MainPage.module.css";
import common from "../../styles/common.module.css";
import LoginForm from "../../components/MainPage/LoginForm";
import ChatForm from "../../components/MainPage/ChatForm";
import { useNavigate } from "react-router-dom";
import { getChats } from "../../api/api";
import gameController from "../../assets/img/controller.png";
import disk from "../../assets/img/disk.png";
import menu from "../../assets/img/menu.png";

const MainPage = () => {
  const [messages, setMessages] = useState([]);
  const ws = useRef(null); // WebSocket 연결을 위한 useRef
  const [activeTab, setActiveTab] = useState("로그인");
  const [nickname, setNickname] = useState(
    sessionStorage.getItem("nickname") || ""
  );
  const [isLoggedIn, setIsLoggedIn] = useState(
    sessionStorage.getItem("isLoggedIn") === "true"
  );
  const navigate = useNavigate();
  const nicknameRef = useRef("");
  useEffect(() => {
    nicknameRef.current = sessionStorage.getItem("nickname");
    console.log(nicknameRef.current);
  }, [nickname]);
  useEffect(() => {
    setNickname(sessionStorage.getItem("nickname"));
    setIsLoggedIn(sessionStorage.getItem("isLoggedIn") === "true");
    const CHAT_SERVER_URL = process.env.REACT_APP_WEBSOCKET_URL;

    // 초기 채팅 데이터 로드
    const loadChats = async () => {
      try {
        if (isLoggedIn) {  // 로그인한 상태에서만 채팅 기록을 불러옵니다
          const response = await getChats();
          setMessages(response);
        } else {
          setMessages([]);  // 로그인하지 않은 상태에서는 빈 배열로 설정
        }
      } catch (error) {
        console.error("채팅 데이터를 불러오는데 실패했습니다:", error);
      }
    };

    loadChats();
    // WebSocket 서버와 연결
    ws.current = new WebSocket(`${CHAT_SERVER_URL}`);

    // 서버로부터 메시지를 받았을 때 처리
    ws.current.onmessage = (event) => {
      const receivedMsg = JSON.parse(event.data);
      console.log("receivedMsg")
      console.log(receivedMsg);
      const newMessage = {
        id: receivedMsg.chatId,
        content: receivedMsg.content,
        createdAt: new Date(receivedMsg.createdAt).toISOString(),
        nickname: receivedMsg.nickname
      };

      // 메시지를 메시지 리스트에 추가
      setMessages((prevMessages) => {
        const newMessages = [...prevMessages, newMessage];
        return newMessages.length > 30
          ? newMessages.slice(newMessages.length - 30)
          : newMessages;
      });
    };

    // WebSocket 연결이 성공했을 때
    ws.current.onopen = () => {
      console.log("WebSocket 연결 성공");
    };

    // WebSocket 연결이 종료됐을 때
    ws.current.onclose = () => {
      console.log("WebSocket 연결 종료");
    };

    // 컴포넌트가 언마운트되면 WebSocket 연결을 종료
    return () => {
      if (ws.current) {
        ws.current.close();
      }
    };
  }, [isLoggedIn]);

  // 메시지 전송 함수
  const sendMessage = (content) => {
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      const message = {
        content,
        createdAt: new Date().toISOString(), // UTC로 변환된 시간을 보내되, KST 기준으로 조정됨.
        userId: sessionStorage.getItem("userId"),
        nickname: sessionStorage.getItem("nickname"),
      };
      console.log(message);
      ws.current.send(JSON.stringify(message));
    }
  };

  return (
    <div className={common.container}>
      <div className={styles.title_container}>
        <h1 className={styles.title}>타자 연습</h1>
        <p className={styles.subtitle}>
          Start Practicing typing and write it down
        </p>
      </div>

      {/* 카드 컨테이너 */}
      <div className={styles.cards_container}>
        <div className={styles.card}>
          <img src={gameController} alt="게임 컨트롤러" className={styles.card_icon} />
          <h3>게임 시작</h3>
          <p>타자 연습 게임을 시작하고 흥미한 성과를 거두세요</p>
          <button>Start Game</button>
        </div>

        <div className={styles.card}>
          <img src={ disk} alt="디스크" className={styles.card_icon} />
          <h3>게임 기록</h3>
          <p>타자 연습 결과를 기록하고 공유해보세요</p>
          <button onClick={() => navigate("/typing-record")}>
            Start Recording
          </button>
        </div>

        <div className={styles.card}>
          <img src={ menu} alt="메뉴" className={styles.card_icon} />
          <h3>게시판</h3>
          <p>다양한 유저와 다양한 에기를 나눠보세요</p>
          <button onClick={() => navigate("/post")}>Start Talking</button>
        </div>
      </div>

      {/* 채팅창/로그인 선택 section*/}
      <div className={styles.tabs_section}>
        <div
          className={`${styles.tab_button} ${
            activeTab === "채팅창" ? styles.active : ""
          }`}
          onClick={() => setActiveTab("채팅창")}
        >
          채팅창
        </div>
        <div
          className={`${styles.tab_button} ${
            activeTab === "로그인" ? styles.active : ""
          }`}
          onClick={() => setActiveTab("로그인")}
        >
          로그인
        </div>
      </div>

      <div className={styles.tab_content}>
        {activeTab === "채팅창" ? (
          <ChatForm
            messages={messages}
            onSendMessage={sendMessage}
            isLoggedIn={isLoggedIn}
          />
        ) : (
          <LoginForm isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
        )}
      </div>
    </div>
  );
};

export default MainPage;
