import React, { useEffect } from 'react';
import './AnswerModal.css';

export default function CorrectModal({ onClose }) {
    useEffect(() => {
        const timer = setTimeout(onClose, 500); // 1초 후 자동 닫힘
        return () => clearTimeout(timer);
    }, [onClose]);

    return (
        <div className="overlay">
            <div className="modal">
                <p>⭕</p>
                <p>정답입니다!</p>
            </div>
        </div>
    );
}
