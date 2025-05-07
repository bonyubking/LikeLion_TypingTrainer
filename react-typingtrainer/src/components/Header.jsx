import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { IoNotificationsOutline } from 'react-icons/io5';
import { IoNotifications } from 'react-icons/io5';
import TypeTalkTalk from '../assets/mp3/TypeTalkTalk.mp3';
import './Header.css';

const Header = () => {
  const navigate = useNavigate();
  const [isMusicPlaying, setIsMusicPlaying] = useState(false);
  const audioRef = useRef(null);

  useEffect(() => {
    // 컴포넌트가 마운트될 때 오디오 객체 생성
    audioRef.current = new Audio(TypeTalkTalk);
    audioRef.current.loop = true;
    audioRef.current.volume = 0.5; // 볼륨을 50%로 설정

    // 오디오 로드 에러 처리
    audioRef.current.addEventListener('error', (e) => {
      console.error('오디오 로드 에러:', e);
    });

    // 컴포넌트가 언마운트될 때 오디오 정리
    return () => {
      if (audioRef.current) {
        audioRef.current.pause();
        audioRef.current = null;
      }
    };
  }, []);

  const handleBack = () => {
    navigate(-1);
  };

  const toggleMusic = async () => {
    try {
      if (isMusicPlaying) {
        await audioRef.current.pause();
      } else {
        await audioRef.current.play();
      }
      setIsMusicPlaying(!isMusicPlaying);
    } catch (error) {
      console.error('음악 재생/정지 에러:', error);
    }
  };

  return (
    <header className="header">
      <div className="header-content">
        <button className="back-button" onClick={handleBack}>
          <IoChevronBack size={24} />
        </button>
        <button 
          className={`notification-button ${isMusicPlaying ? 'active' : ''}`} 
          onClick={toggleMusic}
        >
          {isMusicPlaying ? (
            <IoNotifications size={24} />
          ) : (
            <IoNotificationsOutline size={24} />
          )}
        </button>
      </div>
    </header>
  );
};

export default Header; 