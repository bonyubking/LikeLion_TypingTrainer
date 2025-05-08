import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { IoVolumeHigh, IoVolumeMute } from 'react-icons/io5';
import TypeTalkTalk from '../assets/mp3/TypeTalkTalk.mp3';
import Trot from '../assets/mp3/트로트.mp3';
import Hiphop from '../assets/mp3/힙합.mp3';
import Children from '../assets/mp3/동요.mp3';
import { useUser } from '../contexts/UserContext';
import './Header.css';

const Header = () => {
  const navigate = useNavigate();
  const { nickname } = useUser();
  const [volume, setVolume] = useState(0.5);
  const [showVolumeSlider, setShowVolumeSlider] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentMusic, setCurrentMusic] = useState('가요');
  const audioRef = useRef(null);
  const sliderRef = useRef(null);

  const musicOptions = [
    { label: '가요', file: TypeTalkTalk },
    { label: '트로트', file: Trot },
    { label: '힙합', file: Hiphop },
    { label: '동요', file: Children }
  ];

  // 초기 오디오 설정
  useEffect(() => {
    const selectedMusic = musicOptions.find(option => option.label === currentMusic);
    audioRef.current = new Audio(selectedMusic.file);
    audioRef.current.loop = true;
    audioRef.current.volume = volume;

    // 오디오 로드 에러 처리
    audioRef.current.addEventListener('error', (e) => {
      console.error('오디오 로드 에러:', e);
    });

    // 오디오 재생 상태 변경 이벤트 리스너
    audioRef.current.addEventListener('play', () => setIsPlaying(true));
    audioRef.current.addEventListener('pause', () => setIsPlaying(false));

    // 초기 재생 시도
    if (volume > 0) {
      audioRef.current.play().catch(error => {
        console.error('초기 오디오 재생 실패:', error);
      });
    }

    // 컴포넌트가 언마운트될 때 오디오 정리
    return () => {
      if (audioRef.current) {
        audioRef.current.pause();
        audioRef.current = null;
      }
    };
  }, []);

  // 볼륨 상태가 변경될 때마다 슬라이더 업데이트
  useEffect(() => {
    updateSliderStyle();
  }, [volume]);

  // 슬라이더가 표시될 때마다 스타일 업데이트
  useEffect(() => {
    if (showVolumeSlider) {
      requestAnimationFrame(() => {
        updateSliderStyle();
      });
    }
  }, [showVolumeSlider]);

  const updateSliderStyle = () => {
    if (sliderRef.current) {
      sliderRef.current.style.setProperty('--volume-level', `${volume * 100}%`);
    }
  };

  const handleBack = () => {
    navigate(-1);
  };

  const handleVolumeChange = (e) => {
    const newVolume = parseFloat(e.target.value);
    setVolume(newVolume);
    if (audioRef.current) {
      audioRef.current.volume = newVolume;
      if (newVolume > 0 && !isPlaying) {
        audioRef.current.play().catch(error => {
          console.error('오디오 재생 실패:', error);
        });
      }
    }
  };

  const handleMusicChange = (e) => {
    const newMusic = e.target.value;
    setCurrentMusic(newMusic);
    if (audioRef.current) {
      audioRef.current.pause();
      const selectedMusic = musicOptions.find(option => option.label === newMusic);
      audioRef.current.src = selectedMusic.file;
      audioRef.current.load();
      
      // loadeddata 이벤트를 기다렸다가 재생
      audioRef.current.addEventListener('loadeddata', () => {
        if (volume > 0) {
          audioRef.current.play().catch(error => {
            console.error('오디오 재생 실패:', error);
          });
        }
      }, { once: true }); // 이벤트 리스너를 한 번만 실행
    }
  };

  const toggleVolumeSlider = () => {
    setShowVolumeSlider(!showVolumeSlider);
    if (!showVolumeSlider && volume > 0 && !isPlaying && audioRef.current) {
      audioRef.current.play().catch(error => {
        console.error('오디오 재생 실패:', error);
      });
    }
  };

  return (
    <header className="header">
      <div className="header-content">
        <button className="back-button" onClick={handleBack}>
          <IoChevronBack size={24} />
        </button>
        <div className="audio-controls">
          {nickname && <span className="nickname"><strong>{nickname}</strong>님</span>}
          <select 
            value={currentMusic} 
            onChange={handleMusicChange}
            className="music-select"
          >
            {musicOptions.map(option => (
              <option key={option.label} value={option.label}>
                {option.label}
              </option>
            ))}
          </select>
          <button 
            className="volume-button"
            onClick={toggleVolumeSlider}
          >
            {volume === 0 ? (
              <IoVolumeMute size={24} />
            ) : (
              <IoVolumeHigh size={24} />
            )}
          </button>
          {showVolumeSlider && (
            <div className="volume-slider-container">
              <input
                ref={sliderRef}
                type="range"
                min="0"
                max="1"
                step="0.1"
                value={volume}
                onChange={handleVolumeChange}
                className="volume-slider"
              />
            </div>
          )}
        </div>
      </div>
    </header>
  );
};

export default Header; 