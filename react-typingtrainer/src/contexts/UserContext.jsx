import React, { createContext, useState, useContext, useEffect } from 'react';

const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [nickname, setNickname] = useState('');
  const [userId, setUserId] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // 초기 로드 시 sessionStorage에서 닉네임 가져오기
    const storedNickname = sessionStorage.getItem('nickname');
    const storedUserId = sessionStorage.getItem('userId');
    const storedIsLoggedIn = sessionStorage.getItem('isLoggedIn');
    if (storedNickname) {
      setNickname(storedNickname);
    }
    if (storedUserId) {
      setUserId(storedUserId);
    }
    if (storedIsLoggedIn) {
      setIsLoggedIn(storedIsLoggedIn === 'true');
    }
  }, []);

  const userLogin = (nickname, userId, isLoggedIn) => {
    sessionStorage.setItem('nickname', nickname);
    sessionStorage.setItem('userId', userId);
    sessionStorage.setItem('isLoggedIn', isLoggedIn);
    setNickname(nickname);
    setUserId(userId);
    setIsLoggedIn(isLoggedIn);
  };

  const userLogout = () => {
    sessionStorage.clear();
    setNickname('');
    setUserId('');
    setIsLoggedIn(false);
  };

  return (
    <UserContext.Provider value={{ nickname, userId, isLoggedIn, userLogin, userLogout }}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
}; 