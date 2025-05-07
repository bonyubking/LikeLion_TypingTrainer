import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import common from '../../styles/common.module.css';
import styles from './play.module.css';

export default function TypingPlay() {
    return (
        <div className={common.container}>

            {/* 통계 정보 */}
            <div className={styles.stats}>
                <span>남은 시간 : XX초</span>
                <span>정확도 : XX%</span>
                <span>타수 : XX타</span>
                <span>문제 수 : N / M</span>
            </div>

            {/* 문제 영역 */}
            <div className={styles.questionBox}>
                <p className={styles.question}>무궁화 삼천리 화려강산</p>
            </div>

            {/* 입력 영역 */}
            <div className={styles.inputSection}>
                <span className={styles.timer}>남은 시간 : XX초</span>
                <div className={styles.inputGroup}>
                    <input type="text" placeholder="정답을 입력해주세요." className={styles.inputField} />
                    <button className={styles.submitButton}>입력</button>
                </div>
            </div>
        </div>
    );
}
