import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./GameSetupPage.module.css";

const GameSetupPage = () => {
  const navigate = useNavigate();

  const [playtime, setPlaytime] = useState("");
  const [genres, setGenres] = useState([]);
  const [hintInterval, setHintInterval] = useState("");

  const genreOptions = [
    { label: "K-POP", value: "한국 가요" },
    { label: "동요", value: "한국 동요" },
    { label: "POP", value: "외국 가요" },
  ];

  const handleGenreChange = (e) => {
    const { value, checked } = e.target;
    setGenres((prev) =>
      checked ? [...prev, value] : prev.filter((g) => g !== value)
    );
  };

  const handleStart = () => {
    if (!playtime || !hintInterval || genres.length === 0) {
      alert("모든 항목을 선택해주세요.");
      return;
    }

    const settings = {
      playtime: parseInt(playtime),
      genres,
      hintInterval,
    };

    // 설정값을 sessionStorage에 저장
    sessionStorage.setItem("songGameSettings", JSON.stringify(settings));

    navigate("/song-game/play");
  };

  return (
    <div className={styles.container}>
      <div className={styles["title-wrapper"]}>
        <h1 className={styles.mainTitle}>노래 맞추기</h1>
        <p className={styles.subtitle}>가사를 보고 제목을 맞춰 보세요!</p>
      </div>

      <div className={styles.cardWrapper}>
        <div className={styles.card}>
          <label className={styles.title}>제한 시간</label>
          <select
            className={styles.selectBox}
            value={playtime}
            onChange={(e) => setPlaytime(e.target.value)}
          >
            <option value="">시간 선택</option>
            <option value="60">1분</option>
            <option value="180">3분</option>
            <option value="300">5분</option>
            <option value="600">10분</option>
          </select>
        </div>

        <div className={styles.card}>
          <label className={styles.title}>장르 선택</label>
          {genreOptions.map((genre) => (
            <label key={genre.value}>
              <input
                type="checkbox"
                value={genre.value}
                onChange={handleGenreChange}
                checked={genres.includes(genre.value)}
              />{" "}
              {genre.label}
            </label>
          ))}
        </div>

        <div className={styles.card}>
          <label className={styles.title}>힌트 표기 간격</label>
          <select
            className={styles.selectBox}
            value={hintInterval}
            onChange={(e) => setHintInterval(e.target.value)}
          >
            <option value="">시간 선택</option>
            <option value="10">10초</option>
            <option value="20">20초</option>
            <option value="40">40초 (힌트 1개)</option>
            <option value="0">힌트 없음</option>
          </select>
        </div>
      </div>

      <div style={{ textAlign: "center" }}>
        <button className={styles.modalButton} onClick={handleStart}>
          게임 시작
        </button>
      </div>
    </div>
  );
};

export default GameSetupPage;
