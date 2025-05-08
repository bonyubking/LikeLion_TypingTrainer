
import React from 'react';
import './ModeModal.css';

export default function Modal({ message, onClose }) {
    return (
        <div className="overlay">
            <div className="modal">
                <p>{message}</p>
                <button onClick={onClose}>확인</button>
            </div>
        </div>
    );
}
