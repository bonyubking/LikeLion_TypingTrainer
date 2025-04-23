import React, { useState, useEffect, useRef } from 'react';

// ChatPage 컴포넌트로 변경
const ChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const ws = useRef(null); // WebSocket 연결을 위한 useRef

  useEffect(() => {
    // WebSocket 서버와 연결 (서버 주소는 localhost:8081로 설정)
    ws.current = new WebSocket('ws://localhost:8081');

    // 서버로부터 메시지를 받았을 때 처리
    ws.current.onmessage = (event) => {
      setMessages(prev => [...prev, event.data]);
    };

    // WebSocket 연결이 성공했을 때
    ws.current.onopen = () => {
      console.log('WebSocket 연결 성공');
    };

    // WebSocket 연결이 종료됐을 때
    ws.current.onclose = () => {
      console.log('WebSocket 연결 종료');
    };

    // 컴포넌트가 언마운트되면 WebSocket 연결을 종료
    return () => {
      if (ws.current) {
        ws.current.close();
      }
    };
  }, []);

  // 메시지 전송 함수
  const sendMessage = () => {
    if (ws.current && input) {
      ws.current.send(input); // WebSocket 서버에 메시지 전송
      setInput(''); // 메시지 입력 필드 비우기
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>실시간 채팅</h2>
      <div style={{ border: '1px solid #ccc', height: 200, overflowY: 'scroll', padding: 10 }}>
        {messages.map((msg, i) => (
          <div key={i}>{msg}</div> // 메시지를 화면에 출력
        ))}
      </div>
      <input
        value={input}
        onChange={e => setInput(e.target.value)} // 입력값 상태 업데이트
        onKeyDown={e => e.key === 'Enter' && sendMessage()} // Enter 키로 메시지 전송
      />
      <button onClick={sendMessage}>전송</button>
    </div>
  );
};

export default ChatPage;