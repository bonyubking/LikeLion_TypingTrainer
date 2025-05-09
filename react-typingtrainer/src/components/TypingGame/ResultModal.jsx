import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './AnswerModal.css';

export default function GameResultModal({ onClose, accuracy, typingSpeed, correctCount, wrongCount, totalTime }) {
    const navigate = useNavigate();

    const goHome = () => {
        navigate('/home');
    };
    const replay = () => {
        navigate('/typing/mode');
    };

    return (
        <div className="overlay">
            <div className="modal">
                <h2>게임 종료</h2>
                <p>정확도: {accuracy}%</p>
                <p>타수: {typingSpeed}타</p>
                <p>정답 수: {correctCount}개</p>
                <p>틀린 문제 수: {wrongCount}개</p>
                <p>제한 시간: {totalTime}초</p>
                <button onClick={replay}>다시하기</button>
                <button onClick={goHome}>홈으로</button>
            </div>
        </div>
    );
}
