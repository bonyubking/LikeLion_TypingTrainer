import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import common from '../../styles/common.module.css';
import styles from './mode.module.css';
import Modal from '../../components/TypingGame/ModeModal';

export default function TypingIntro() {
    const navigate = useNavigate();
    const [level, setLevel] = useState('');
    const [time, setTime] = useState('');
    const [mode, setMode] = useState('');
    const [showModal, setShowModal] = useState(false);

    const timeMap = {
        '1분': 60,
        '2분': 120,
        '5분': 300,
        '10분': 600
    };

    const handleStart = () => {
        if (!level || !time || !mode) {
            setShowModal(true);
            return;
        }

        navigate('/typing/play', {
            state: {
                userId: 'testUser',
                difficulty: level,
                type: mode.split('/')[1],
                language: mode.split('/')[0],
                totalTime: timeMap[time],
            },
        });
    };

    return (
        <>
            <div className={common.container}>
                <div className={styles.cardWrapper}>
                    <div className={styles.card}>
                        <h2>난이도</h2>
                        <select className={styles.modeSelect} onChange={(e) => setLevel(e.target.value)} defaultValue="">
                            <option value="" disabled>난이도</option>
                            <option>하</option>
                            <option>중</option>
                            <option>상</option>
                            <option>랜덤</option>
                        </select>
                    </div>

                    <div className={styles.card}>
                        <h2>제한 시간</h2>
                        <select className={styles.modeSelect} onChange={(e) => setTime(e.target.value)} defaultValue="">
                            <option value="" disabled>시간</option>
                            <option>1분</option>
                            <option>2분</option>
                            <option>5분</option>
                            <option>10분</option>
                        </select>
                    </div>

                    <div className={styles.card}>
                        <h2>언어 / 문제 형식</h2>
                        <select className={styles.modeSelect} onChange={(e) => setMode(e.target.value)} defaultValue="">
                            <option value="" disabled>언어/형식</option>
                            <option>한국어/단어</option>
                            <option>한국어/문장</option>
                            <option>영어/단어</option>
                            <option>영어/문장</option>
                            <option>밈(Meme)</option>
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
