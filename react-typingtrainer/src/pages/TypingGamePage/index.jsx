import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import common from '../../styles/common.module.css';
import styles from './mode.module.css';
import Modal from '../../components/TypingGame/ModeModal';
import Header from '../../components/Header';


export default function TypingIntro() {
    const navigate = useNavigate();
    const [level, setLevel] = useState('');
    const [time, setTime] = useState('');
    const [mode, setMode] = useState('');
    const [showModal, setShowModal] = useState(false);

    const handleStart = async () => {
        if (!level || !time || !mode) {
            setShowModal(true);
            return;
        }

        try {
            const response = await fetch(`${process.env.REACT_APP_HTTP_URL}/api/start`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ level, time, mode })
            });

            const result = await response.json();
            console.log('응답:', result);

            navigate('/typing/play', { state: { level, time, mode } });
        } catch (error) {
            console.error('요청 오류:', error);
            alert('서버 요청 실패');
        }
    };

    return (
        <>
            <div className={common.container}>
                <div className={styles.cardWrapper}>
                    <div className={styles.card}>
                        <h2>난이도</h2>
                        <select onChange={(e) => setLevel(e.target.value)} defaultValue="">
                            <option value="" disabled>난이도</option>
                            <option>하</option>
                            <option>중</option>
                            <option>상</option>
                            <option>랜덤</option>
                        </select>
                    </div>

                    <div className={styles.card}>
                        <h2>제한 시간</h2>
                        <select onChange={(e) => setTime(e.target.value)} defaultValue="">
                            <option value="" disabled>시간</option>
                            <option>1분</option>
                            <option>2분</option>
                            <option>5분</option>
                            <option>10분</option>
                        </select>
                    </div>

                    <div className={styles.card}>
                        <h2>언어 / 문제 형식</h2>
                        <select onChange={(e) => setMode(e.target.value)} defaultValue="">
                            <option value="" disabled>언어/형식</option>
                            <option>한국어/단어</option>
                            <option>한국어/문장</option>
                            <option>영어/단어</option>
                            <option>영어/문장</option>
                        </select>
                    </div>
                </div>

                <button className={styles.modalButton} onClick={handleStart}>게임 시작</button>

                {showModal && (
                    <Modal
                        message="모든 항목을 선택해주세요."
                        onClose={() => setShowModal(false)}
                    />
                )}
            </div>
        </>
    );
}