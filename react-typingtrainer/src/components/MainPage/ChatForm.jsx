import React, { useState, useRef, useEffect } from "react";
import styles from "./ChatForm.module.css";
import { FaPlus, FaPaperPlane } from "react-icons/fa";
import { useUser } from "../../contexts/UserContext";

// 채팅 폼 컴포넌트
const ChatForm = ({ messages, onSendMessage }) => {
  // const [messages, setMessages] = useState(initialMessages || []);
  const [input, setInput] = useState("");
  const chatAreaRef = useRef(null);
  const lastMessageRef = useRef(null);
  const { isLoggedIn, nickname } = useUser();
  // 메시지가 추가될 때마다 스크롤을 맨 아래로 이동
  useEffect(() => {
    if (lastMessageRef.current) {
      lastMessageRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  const handleSend = () => {
    if (!input.trim()) return;
    onSendMessage(input);
    setInput("");
  };

  return (
    <div className={styles.chat_form_wrap}>
      <div className={styles.chat_form_wrap_header}>
        <div className={styles.circles}>
          <div className={styles.circle}></div>
          <div className={styles.circle}></div>
          <div className={styles.circle}></div>
        </div>
      </div>
      <div className={styles.chat_form}>
        <div className={styles.chat_area} ref={chatAreaRef}>
          {messages.map((msg, idx) => (
            <div
              key={idx}
              ref={idx === messages.length - 1 ? lastMessageRef : null}
              className={`${styles.message_row} ${
                msg.nickname === nickname
                  ? styles.me
                  : styles.other
              }`}
            >
              {msg.nickname !== nickname && (
                <div className={styles.nickname}>{msg.nickname}</div>
              )}         
              {msg.nickname === nickname && (
                <div className={styles.nickname}>{msg.nickname}</div>
              )}
              <div className={styles.bubble}>{msg.content}</div>
              <div
                className={`${styles.date} ${
                  msg.nickname === nickname
                    ? styles.date_right
                    : styles.date_left
                }`}
              >
                {new Date(msg.createdAt).toLocaleString('sv-SE', { timeZone: 'Asia/Seoul' })}
              </div>
            </div>
          ))}
        </div>
      </div>
      <div className={styles.input_area}>
        <FaPlus className={styles.plus_icon} />
        <input
          className={styles.input}
          placeholder="채팅을 입력해 주세요"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === "Enter") { 
              e.preventDefault();
              handleSend();
            }
          }}
        />
        <FaPaperPlane className={styles.send_icon} onClick={handleSend} />
      </div>
      {!isLoggedIn && (
        <>
          <div className={styles.overlay} />
          <div className={styles.login_popup}>
            <h3>로그인이 필요한 서비스입니다</h3>
            <p>채팅을 이용하시려면 로그인해주세요.</p>
          </div>
        </>
      )}
    </div>
  );
};

export default ChatForm;
