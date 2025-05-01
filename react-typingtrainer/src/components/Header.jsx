import React from 'react';
import { useNavigate } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { IoNotificationsOutline } from 'react-icons/io5';
import './Header.css';

const Header = () => {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  return (
    <header className="header">
      <div className="header-content">
        <button className="back-button" onClick={handleBack}>
          <IoChevronBack size={24} />
        </button>
        <button className="notification-button">
          <IoNotificationsOutline size={24} />
        </button>
      </div>
    </header>
  );
};

export default Header; 