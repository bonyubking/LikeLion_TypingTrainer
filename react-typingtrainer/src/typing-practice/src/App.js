// App.js 파일
import React, { useState } from 'react';
import './App.css';
import controllerImg from './assets/controller.png';
import diskImg from './assets/disk.png';
import menuImg from './assets/menu.png';
import peopleImg from './assets/people.png';

function App() {
  const [activeTab, setActiveTab] = useState('로그인');

  return (
    <div className="typing-app">
      <header>
        <div className="title">타자 연습</div>
        <div className="notifications">
          <span className="icon">🔔</span>
          <span className="icon">🔕</span>
        </div>
      </header>

      <div className="subtitle">
        <p>Start Practicing typing and write it down</p>
      </div>

      <div className="cards-container">
        <div className="card">
          <img src={controllerImg} alt="게임 컨트롤러" className="card-icon" />
          <h3>게임 시작</h3>
          <p>타자 연습 게임을 시작하고 흥미한 성과를 거두세요</p>
          <button>Start Game</button>
        </div>

        <div className="card">
          <img src={diskImg} alt="디스크" className="card-icon" />
          <h3>게임 기록</h3>
          <p>타자 연습 결과를 기록하고 공유해보세요</p>
          <button>Start Recording</button>
        </div>

        <div className="card">
          <img src={menuImg} alt="메뉴" className="card-icon" />
          <h3>게시판</h3>
          <p>다양한 유저와 다양한 에기를 나눠보세요</p>
          <button>Start Talking</button>
        </div>
      </div>

      <div className="tabs-section">
        <div className="tab-buttons">
          <button 
            className={activeTab === '채팅창' ? 'tab-button' : 'tab-button'}
            onClick={() => setActiveTab('채팅창')}
          >
            채팅창
          </button>
          <button 
            className={activeTab === '로그인' ? 'tab-button active' : 'tab-button'}
            onClick={() => setActiveTab('로그인')}
          >
            로그인
          </button>
        </div>

        <div className="tab-content">
          <div className="login-form">
            <h3>안녕!</h3>
            <h2>로그인</h2>
            <p>세상에서 제일 빠른 로그인</p>
            
            <div className="form-group">
              <label>ID</label>
              <input type="email" placeholder="JohnDoe@gmail.com" />
            </div>
            
            <div className="form-group">
              <label>Password</label>
              <div className="password-field">
                <input type="password" placeholder="Please Enter your password" />
                <button className="toggle-password">👁️</button>
              </div>
            </div>
            
            <div className="checkbox-group">
              <input type="checkbox" id="remember" />
              <label htmlFor="remember">Remember me</label>
              <a href="#" className="forgot-password">Forgot Password?</a>
            </div>
            
            <button className="login-button">로그인</button>
            
            <div className="signup-link">
              <span>계정이 없으신가요? </span>
              <a href="#">등록하기</a>
            </div>
          </div>
          
          <div className="illustration">
            <img src={peopleImg} alt="사람들 일러스트" />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
