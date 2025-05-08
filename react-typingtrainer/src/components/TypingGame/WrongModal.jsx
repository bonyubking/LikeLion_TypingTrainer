import React, { useEffect } from 'react';
import './ModeModal.css';

export default function WrongModal({ onClose }) {
    useEffect(() => {
        const timer = setTimeout(onClose, 500); // 1초 후 자동 닫힘
        return () => clearTimeout(timer);
    }, [onClose]);

    return (
        <div className="overlay">
            <div className="modal">
                <p>오답입니다</p>
                <button onClick={onClose}>다음</button>
            </div>
        </div>
    );
}
