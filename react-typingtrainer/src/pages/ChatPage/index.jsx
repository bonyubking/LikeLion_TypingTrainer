import React, { useState, useEffect, useRef } from "react";
import { getChats } from "../../api/api";

// ChatPage 컴포넌트로 변경
const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const ws = useRef(null); // WebSocket 연결을 위한 useRef
  const [preMessages, setprevMessages] = useState([]);

  useEffect(() => {
    // 초기 채팅 데이터 로드
    const loadChats = async () => {
      try {
        const chatData = await getChats();
        console.log(chatData);
        setprevMessages(chatData.map(chat => ({
          chatId: chat.chatId,
          userId: chat.userId,
          content: chat.content,
          createdAt: new Date(chat.createdAt).toLocaleString(),
          nickname: chat.nickname
        })));
      } catch (error) {
        console.error('채팅 데이터 로드 실패:', error);
      }
    };

    loadChats();
  

// WebSocket 서버와 연결 (서버 주소는 localhost:8081로 설정)
ws.current = new WebSocket("ws://localhost:8081");

// 서버로부터 메시지를 받았을 때 처리
ws.current.onmessage = (event) => {
  const receivedMsg = JSON.parse(event.data);

  // 메시지를 메시지 리스트에 추가
  setMessages((prevMessages) => [
    ...prevMessages,
    {
      chatId: receivedMsg.chatId,
      userId: receivedMsg.userId,
      content: receivedMsg.content,
      createdAt: new Date(receivedMsg.createdAt).toLocaleString(), // 날짜 포맷 변환
      nickname: receivedMsg.nickname
    },
  ]);
};

// WebSocket 연결이 성공했을 때
ws.current.onopen = () => {
  console.log("WebSocket 연결 성공");
};

// WebSocket 연결이 종료됐을 때
ws.current.onclose = () => {
  console.log("WebSocket 연결 종료");
};
}, []);
// 컴포넌트가 언마운트되면 WebSocket 연결을 종료
// return () => {
//   if (ws.current) {
//     ws.current.close();
//   }
// };
  // 메시지 전송 함수
  const sendMessage = () => {
    if (ws.current && input.trim() !== "") {
      const messageDto = {
        userId: 1, // 실제 사용자 ID로 교체
        content: input,
        createdAt: new Date().toISOString(),
      };

      ws.current.send(JSON.stringify(messageDto));
      setInput("");
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>실시간 채팅</h2>
      <div
        style={{
          border: "1px solid #ccc",
          height: 200,
          overflowY: "scroll",
          padding: 10,
        }}
      >
        {messages.map((msg, index) => (
          <div key={index}>
            <strong>{msg.nickname}</strong>: {msg.content} <br />
            <span style={{ fontSize: "0.8em", color: "gray" }}>
              {msg.createdAt}
            </span>
          </div>
        ))}
      </div>
      <input
        value={input}
        onChange={(e) => setInput(e.target.value)} // 입력값 상태 업데이트
        onKeyDown={(e) => e.key === "Enter" && sendMessage()} // Enter 키로 메시지 전송
      />
      <button onClick={sendMessage}>전송</button>
      <br />
      <div>
        <h1>채팅 기록 조회</h1>
        {preMessages.map((msg, index) => (
          <div key={index} >
            <div>
              <span>{msg.nickname}</span>
              <span>{msg.createdAt}</span>
            </div>
            <div>
              {msg.content}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ChatPage;
