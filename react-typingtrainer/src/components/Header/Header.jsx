import React from 'react';
import { useNavigate } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { IoNotificationsOutline } from 'react-icons/io5';
import './Header.css';

const Header = ({ title, subtitle }) => {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  return (
    <header className="header">
      <div className="header-left">
        <button className="back-button" onClick={handleBack}>
          <IoChevronBack size={24} />
        </button>
      </div>

      <div className="header-center">
        {title && <h2 className="header-title">{title}</h2>}
        {subtitle && <p className="header-subtitle">{subtitle}</p>}
      </div>

      <div className="header-right">
        <button className="notification-button">
          <IoNotificationsOutline size={24} />
        </button>
      </div>
    </header>
  );
};

export default Header; 