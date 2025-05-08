import React from 'react';
import styles from './GameStartPage.module.css';
import { useNavigate } from "react-router-dom";

export default function GameStartPage() {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>게임 시작</h1>
      <p className={styles.subtitle}>모드를 선택해주세요.</p>
      <div className={styles.cardsWrapper}>
        <div className={styles.card}>
          <div className={styles.icon}>🎧</div>
          <div className={styles.cardTitle}>노래 맞추기 모드</div>
          <div className={styles.cardDesc}>제한 시간 내 가사를 듣고 맞춰보세요!</div>
          <button className={styles.startBtn}>시작하기</button>
        </div>
        <div className={styles.card}>
          <div className={styles.icon}>😊</div>
          <div className={styles.cardTitle}>타자 연습 모드</div>
          <div className={styles.cardDesc}>다양한 문장/단어로 연습해보세요!</div>
          <button onClick={() => navigate("/typing/mode")}
          className={styles.startBtn}>시작하기</button>
        </div>
      </div>
    </div>
  );
}