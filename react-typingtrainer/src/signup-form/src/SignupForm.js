import React, { useState } from 'react';
import './SignupForm.css';

function SignupForm() {
  const [email, setEmail] = useState('');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('회원가입 정보가 제출되었습니다!');
  };

  return (
    <div className="signup-page">
      <div className="header">
        <div className="left">
          <span>타자 연습</span>
          <button className="back-button">←</button>
        </div>
        <div className="right">
          <span className="notification">🔔</span>
          <span className="notification">🔕</span>
        </div>
      </div>
      
      <div className="content">
        <div className="illustration">
          {/* 이미지는 src 폴더에 저장하고 경로를 수정하세요 */}
          <img src="/people.png" alt="사람들 일러스트" />
        </div>
        
        <div className="form-container">
          <h2>안녕!</h2>
          <h1>회원가입</h1>
          <p>우리와 함께해요</p>
          
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>ID</label>
              <input 
                type="email" 
                placeholder="Enter your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            
            <div className="form-group">
              <label>NICKNAME</label>
              <input 
                type="text" 
                placeholder="Enter your user name"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
              />
            </div>
            
            <div className="form-group">
              <label>Password</label>
              <input 
                type="password" 
                placeholder="Enter your Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            
            <div className="form-group">
              <label>Confirm Password</label>
              <input 
                type="password" 
                placeholder="Confirm your Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
            </div>
            
            <button type="submit" className="signup-button">회원가입</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignupForm;
