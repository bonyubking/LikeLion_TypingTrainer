import React, { useState } from 'react';
import './App.css';

function App() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  return (
    <div className="signup-page">
      <header>
        <div className="header-left">
          <span className="title">타자 연습</span>
          <button className="back-button">←</button>
        </div>
        <div className="header-right">
          <span className="notification-icon">🔔</span>
          <span className="notification-icon">🔕</span>
        </div>
      </header>

      <main>
        <div className="illustration">
          <img src="/people.png" alt="사람들 일러스트" />
        </div>
        
        <div className="form-card">
          <h3>안녕!</h3>
          <h2>회원가입</h2>
          <p>우리와 함께해요</p>
          
          <form>
            <div className="form-group">
              <label>ID</label>
              <input type="email" placeholder="Enter your email" />
            </div>
            
            <div className="form-group">
              <label>NICKNAME</label>
              <input type="text" placeholder="Enter your user name" />
            </div>
            
            <div className="form-group">
              <label>Password</label>
              <div className="password-input">
                <input 
                  type={showPassword ? "text" : "password"} 
                  placeholder="Enter your Password" 
                />
                <button 
                  type="button" 
                  className="toggle-password"
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? "👁️" : "👁️‍🗨️"}
                </button>
              </div>
            </div>
            
            <div className="form-group">
              <label>Confirm Password</label>
              <div className="password-input">
                <input 
                  type={showConfirmPassword ? "text" : "password"} 
                  placeholder="Confirm your Password" 
                />
                <button 
                  type="button" 
                  className="toggle-password"
                  onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                >
                  {showConfirmPassword ? "👁️" : "👁️‍🗨️"}
                </button>
              </div>
            </div>
            
            <button type="submit" className="signup-button">회원가입</button>
          </form>
        </div>
      </main>
    </div>
  );
}

export default App;
