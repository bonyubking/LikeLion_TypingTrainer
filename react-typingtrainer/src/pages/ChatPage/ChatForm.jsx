import React, { useState } from "react";
import styles from "./ChatForm.module.css";
import { FaPlus, FaPaperPlane } from "react-icons/fa";

const ChatForm = ({ messages, onSendMessage }) => {
  // const [messages, setMessages] = useState(initialMessages || []);
  const [input, setInput] = useState("");

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
        <div className={styles.chat_area}>
          {messages.map((msg,idx) => (
            <div
              key={idx}
              className={`${styles.message_row} ${
                msg.nickname === sessionStorage.getItem("nickname")
                  ? styles.me
                  : styles.other
              }`}
            >
              {msg.nickname !== sessionStorage.getItem("nickname") && (
                <div className={styles.nickname}>{msg.nickname}</div>
              )}         
              {msg.nickname === sessionStorage.getItem("nickname")&& (
                <div className={styles.nickname}>{msg.nickname}</div>
              )}
              <div className={styles.bubble}>{msg.content}</div>
              <div
                className={`${styles.date} ${
                  msg.nickname === sessionStorage.getItem("nickname")
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
    </div>
  );
};

export default ChatForm;
