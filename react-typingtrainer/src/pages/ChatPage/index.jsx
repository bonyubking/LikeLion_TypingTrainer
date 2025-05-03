import React, { useState, useEffect, useRef } from "react";
import { getChats } from "../../api/api";
import common from "../../styles/common.module.css";
import styles from "./ChatPage.module.css";
import ChatForm from "./ChatForm";
import LoginForm from "./LoginForm";
// ChatPage 컴포넌트로 변경
const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const ws = useRef(null); // WebSocket 연결을 위한 useRef
  const [preMessages, setprevMessages] = useState([]);
  const [activeTab, setActiveTab] = useState("chat");
  // useEffect(() => {
  //   // 초기 채팅 데이터 로드
  //   const loadChats = async () => {
  //     try {
  //     } catch (error) {}
  //   };

  //   loadChats();

  //   // WebSocket 서버와 연결 (서버 주소는 localhost:8081로 설정)
  //   ws.current = new WebSocket("ws://localhost:8081");

  //   // 서버로부터 메시지를 받았을 때 처리
  //   ws.current.onmessage = (event) => {
  //     const receivedMsg = JSON.parse(event.data);

  //     // 메시지를 메시지 리스트에 추가
  //     setMessages((prevMessages) => [
  //       ...prevMessages,
  //       {
  //         chatId: receivedMsg.chatId,
  //         userId: receivedMsg.userId,
  //         content: receivedMsg.content,
  //         createdAt: new Date(receivedMsg.createdAt).toLocaleString(), // 날짜 포맷 변환
  //         nickname: receivedMsg.nickname,
  //       },
  //     ]);
  //   };

  //   // WebSocket 연결이 성공했을 때
  //   ws.current.onopen = () => {
  //     console.log("WebSocket 연결 성공");
  //   };

  //   // WebSocket 연결이 종료됐을 때
  //   ws.current.onclose = () => {
  //     console.log("WebSocket 연결 종료");
  //   };
  // }, []);
  // 컴포넌트가 언마운트되면 WebSocket 연결을 종료
  // return () => {
  //   if (ws.current) {
  //     ws.current.close();
  //   }
  // };
  // 메시지 전송 함수
  // const sendMessage = () => {};

  return (
    <div className={common.container}>
      {/* 상단 타이틀 */}
      <div className={styles.title_container}>
        <h1 className={styles.title}>타자 연습</h1>
        <p className={styles.subtitle}>
          Start Practicing typing and write it down
        </p>
      </div>

      {/* 카드 3개 */}
      <div className={styles.card_row}>
        <div className={styles.card}>
          <img
            src="/img/Game_Start.png"
            alt="게임 시작"
            className={styles.card_icon}
          />
          <h2>게임 시작</h2>
          <p>타자 연습 게임을 시작하고 훌륭한 성과를 거두세요!</p>
          <button className={styles.card_button}>Start Game</button>
        </div>
        <div className={styles.card}>
          <img
            src="/img/Game_Record.png"
            alt="게임 기록"
            className={styles.card_icon}
          />
          <h2>게임 기록</h2>
          <p>타자 연습 결과를 기록하고 공유해보세요</p>
          <button className={styles.card_button}>Start Recording</button>
        </div>
        <div className={styles.card}>
          <img src="/img/Post.png" alt="게시판" className={styles.card_icon} />
          <h2>게시판</h2>
          <p>다양한 유저와 다양한 얘기를 나눠보세요</p>
          <button className={styles.card_button}>Start Talking</button>
        </div>
      </div>

      {/* 네비게이션 */}
      <div className={styles.nav}>
        <div
          className={`${styles.nav_item} ${
            activeTab === "chat" ? styles.active : ""
          }`}
          onClick={() => setActiveTab("chat")}
        >
          채팅창
        </div>
        <div
          className={`${styles.nav_item} ${
            activeTab === "login" ? styles.active : ""
          }`}
          onClick={() => setActiveTab("login")}
        >
          로그인
        </div>
      </div>

      {/* 메인 컨텐츠 */}
      <div className={styles.main_content}>
        {activeTab === "chat" ? <ChatForm /> : <LoginForm />}
      </div>
    </div>
  );
};

export default ChatPage;
