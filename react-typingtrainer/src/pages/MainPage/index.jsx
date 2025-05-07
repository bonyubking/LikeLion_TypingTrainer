import React, { useState } from "react";
import styles from "./MainPage.module.css";
import common from "../../styles/common.module.css";
import LoginForm from "../../components/MainPage/LoginForm";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
  const [activeTab, setActiveTab] = useState('로그인');
  const navigate = useNavigate();
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
          <img src="" alt="게임 컨트롤러" className={styles.card_icon} />
          <h3>게임 시작</h3>
          <p>타자 연습 게임을 시작하고 흥미한 성과를 거두세요</p>
          <button>Start Game</button>
        </div>

        <div className={styles.card}>
          <img src="" alt="디스크" className={styles.card_icon} />
          <h3>게임 기록</h3>
          <p>타자 연습 결과를 기록하고 공유해보세요</p>
          <button onClick={() => navigate('/typing-record')}>
            Start Recording</button>
        </div>

        <div className={styles.card}>
          <img src="" alt="메뉴" className={styles.card_icon} />
          <h3>게시판</h3>
          <p>다양한 유저와 다양한 에기를 나눠보세요</p>
          <button onClick={() => navigate('/post')}
            >Start Talking</button>
          
        </div>
      </div>

      { /* 채팅창/로그인 선택 section*/}
      <div className={styles.tabs_section}>
        <div 
          className={`${styles.tab_button} ${activeTab === '채팅창' ? styles.active : ''}`} 
          onClick={() => setActiveTab('채팅창')}
        >
          채팅창
        </div>
        <div 
          className={`${styles.tab_button} ${activeTab === '로그인' ? styles.active : ''}`} 
          onClick={() => setActiveTab('로그인')}
        >
          로그인
        </div>
      </div>

      <div className={styles.tab_content}>
      <LoginForm />
        {/*           
          {activeTab === "chat" ? <ChatForm messages={messages} onSendMessage={sendMessage} /> : <LoginForm />}
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
          </div> */}
      </div>
    </div>
  
  );
};

export default MainPage;
