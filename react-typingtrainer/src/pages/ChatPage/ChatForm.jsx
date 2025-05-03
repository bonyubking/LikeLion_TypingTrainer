import React, { useState } from "react";
import styles from "./ChatForm.module.css";
import { FaPlus, FaPaperPlane } from "react-icons/fa";

const dummyMessages = [
  {
    id: 1,
    userType: "other", // 또는 "me"
    content: "안녕하세요!",
    date: "2025-04-25 00시 00분",
  },
  {
    id: 2,
    userType: "me",
    content: "안녕하세요! 반갑습니다.",
    date: "2025-04-25 00시 00분",
  },
];

const ChatForm = () => {
  const [messages, setMessages] = useState(dummyMessages);
  const [input, setInput] = useState("");

  const handleSend = () => {
    if (!input.trim()) return;
    setMessages([
      ...messages,
      {
        id: messages.length + 1,
        userType: "me",
        content: input,
        date: "2025-04-25 00시 00분",
      },
    ]);
    setInput("");
  };

  return (
    <div className={styles.chat_form_wrap}>
      <div className={styles.chat_form_wrap_header}>
        <div className={styles.circles}>
        <div className={styles.circle}></div>
        <div className={styles.circle}></div>
        <div className={ styles.circle}></div>  
        </div>
        
      </div>
      <div className={styles.chat_form}>
        <div className={styles.chat_area}>
          {messages.map((msg) => (
            <div
              key={msg.id}
              className={`${styles.message_row} ${
                msg.userType === "me" ? styles.me : styles.other
              }`}
            >
              {msg.userType === "other" && (
                <div className={styles.profile}></div>
              )}
              <div className={styles.bubble}>{msg.content}</div>
              {msg.userType === "me" && <div className={styles.profile}></div>}
              <div
                className={`${styles.date} ${
                  msg.userType === "me" ? styles.date_right : styles.date_left
                }`}
              >
                {msg.date}
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
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
          />
          <FaPaperPlane className={styles.send_icon} onClick={handleSend} />
        </div>
    </div>
  );
};

export default ChatForm;
