import React from 'react';
import './Home.css';
import common from '../../styles/common.module.css'

const Home = () => {
  return (
    <div className="home-container">
      <h1 className="home-title">타자 연습</h1>
      <p className="home-subtitle">Start Practicing typing and write it down</p>
      
      <div className="practice-cards">
        <div className="practice-card">
          <div className="card-icon">⌨️</div>
          <h3>제한 시간</h3>
          <p>시간 제한 게임을 시작하고 실력을 테스트해보세요</p>
          <button className="start-button">Start Game</button>
        </div>
        
        <div className="practice-card">
          <div className="card-icon">📝</div>
          <h3>제한 개수</h3>
          <p>단어 개수 목표를 정하고 도전해보세요</p>
          <button className="start-button">Start Recording</button>
        </div>
        
        <div className="practice-card">
          <div className="card-icon">🎯</div>
          <h3>자유연습</h3>
          <p>자유롭게 타자연습을 하면서 실력을 키워보세요</p>
          <button className="start-button">Start Testing</button>
        </div>
      </div>

      <div className="chat-section">
        <div className="chat-container">
          <h2>최근 기록</h2>
          {[...Array(10)].map((_, index) => (
            <div key={index} className="chat-item" style={{ padding: '20px', margin: '10px 0', background: '#fff', borderRadius: '8px' }}>
              <h3>테스트 기록 #{index + 1}</h3>
              <p>정확도: {85 + Math.floor(Math.random() * 15)}%</p>
              <p>속도: {40 + Math.floor(Math.random() * 30)} WPM</p>
              <p>날짜: 2024-03-{String(index + 1).padStart(2, '0')}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Home; 