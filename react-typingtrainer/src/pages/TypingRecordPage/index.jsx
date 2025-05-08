import React, { useState, useEffect } from 'react';
import { fetchTypingRecords, fetchSongRecords } from '../../api/api';  // 앞서 만든 fetch 함수
import styles from './TypingRecordPage.module.css';

export default function TypingRecordPage() {
  
  const userId = 1;
  const [scope, setScope] = useState('all');        // 'all' | 'mine'
  const [gameType, setGameType] = useState('typing'); // 'typing' | 'song'

  // 필터 상태
  const [difficulty, setDifficulty] = useState('');
  const [language, setLanguage] = useState('');
  const [contentType, setContentType] = useState('');
  const [duration, setDuration] = useState('');

  // 데이터 + 페이징
  const [records, setRecords] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가
  const [error, setError] = useState(null); 
  const itemsPerPage = 10;

  const [genre, setGenre] = useState(''); // 장르 필터
  const [hintTime, setHintTime] = useState(''); // 힌트 시간 필터


  const load = async () => {
    try {
        setLoading(true);
        const qs = new URLSearchParams();
        if (scope === 'mine') qs.set('userId', userId);
        
        // 공통 필터
        if (duration) qs.set('duration', duration);
        
        if (gameType === 'typing') {
          if (difficulty) qs.set('difficulty', difficulty);
          if (language)    qs.set('language', language);
          if (contentType) qs.set('content_type', contentType);
        } else {
          if (genre) qs.set('genre', genre);
          if (hintTime) qs.set('hint_time', hintTime);
        }
        qs.set('page', page);

        console.log('API 요청 URL:', qs.toString());

        const data = gameType === 'song' 
            ? await fetchSongRecords(qs.toString())
            : await fetchTypingRecords(qs.toString());

        setRecords(data);
        setTotalPages(Math.ceil(data.length / itemsPerPage));
        setError(null);
    } catch (err) {
        console.error('기록 로드 실패:', err);
        setError('기록을 불러오는데 실패했습니다.');
        setRecords([]);
    } finally {
        setLoading(false);
    }
};

const getCurrentPageData = () => {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return records.slice(startIndex, endIndex);
  };

  useEffect(() => { 
    load(); 
}, [scope, gameType, difficulty, language, duration, contentType, genre, hintTime, page]);


  return (
    <div className={styles.container}>
      <h1>게임 기록</h1>
      <p className={styles.subtitle}>높은 랭킹에 도전해 보세요!</p>

      {/* 상단 버튼 그룹 */}
      <div className={styles.buttonGroup}>
        <button
          className={scope==='mine'? styles.activeBtn:styles.btn}
          onClick={()=>setScope('mine')}
        >내 기록</button>
        <button
          className={scope==='all'? styles.activeBtn:styles.btn}
          onClick={()=>setScope('all')}
        >전체 기록</button>
      </div>
      <div className={styles.buttonGroup}>
        <button
          className={gameType==='song'? styles.activeRed:styles.redBtn}
          onClick={()=>setGameType('song')}
        >노래 맞추기</button>
        <button
          className={gameType==='typing'? styles.activeRed:styles.redBtn}
          onClick={()=>setGameType('typing')}
        >타자 연습</button>
      </div>

      {/* 필터 폼 (난이도 예시) */}

      <div className={styles.filters}>
    {gameType === 'typing' ? (
        <>
            <select value={difficulty} onChange={e=>setDifficulty(e.target.value)}>
                <option value="">난이도</option>
                <option value="상">상</option>
                <option value="중">중</option>
                <option value="하">하</option>
            </select>
            <select value={language} onChange={e=>setLanguage(e.target.value)}>
                <option value="">언어</option>
                <option value="한">한글</option>
                <option value="영">영어</option>
            </select>
            <select value={contentType} onChange={e=>setContentType(e.target.value)}>
                <option value="">문장/단어</option>
                <option value="sentence">문장</option>
                <option value="word">단어</option>
            </select>
            <select value={duration} onChange={e=>setDuration(e.target.value)}>
              <option value="">제한 시간</option>
              <option value="1">1분</option>
              <option value="2">2분</option>
              <option value="5">5분</option>
              <option value="10">10분</option>
            </select>
        </>
    ) : (
        <>
            <select value={genre} onChange={e=>setGenre(e.target.value)}>
                <option value="">장르</option>
                <option value="KPOP">한국가요</option>
                <option value="POP">미국가요</option>
                <option value="KKIDS">한국동요</option>
            </select>
            <select value={hintTime} onChange={e=>setHintTime(e.target.value)}>
                <option value="">힌트 시간</option>
                <option value="15">15초</option>
                <option value="30">30초</option>
                <option value="45">45초</option>
                <option value="60">60초</option>
            </select>
            <select value={duration} onChange={e=>setDuration(e.target.value)}>
              <option value="">제한 시간</option>
              <option value="1">1분</option>
              <option value="2">2분</option>
              <option value="5">5분</option>
              <option value="10">10분</option>
            </select>
        </>
    )}
</div>

      {/* 레코드 테이블 */}
      <table className={styles.table}>
    <thead>
        <tr>
            {gameType === 'typing' ? (
                <>  
                    <th>난이도</th>
                    <th>언어</th>
                    <th>문장/단어</th>
                    <th>제한 시간</th>
                    <th>정확도(%)</th>
                    <th>타자속도</th>
                    <th>정답 수</th>
                </>
            ) : (
                <>  
                    <th>장르</th>
                    <th>제한 시간</th>
                    <th>힌트 간격</th>
                    <th>정답 수</th>
                </>
            )}
            <th>닉네임</th>
            <th>플레이 날짜</th>
        </tr>
    </thead>
    <tbody>
        {getCurrentPageData().map(r => (
            <tr key={r.recordId}>
                {gameType === 'typing' ? (
                  <>
                <td>{r.difficulty}</td>
                <td>{r.language}</td>
                <td>{r.contentType === 'sentence' ? '문장' : '단어'}</td>
                <td>{r.duration}분</td>
                <td>{r.accuracy}%</td>
                <td>{r.typingSpeed}타</td>
                <td>{r.correctCount}개</td>
                </> 
                ) : (
                  <>
                    <td>{r.genre}</td>
                    <td>{r.duration}분</td>
                    <td>{r.hintTime}초</td>
                    <td>{r.correctCount}개</td>
                  </>
                ) 
                }
                <td>{r.nickname}</td>
                <td>{new Date(r.playedAt).toLocaleDateString()}</td>
            </tr>
        ))}
        {records.length === 0 && (
            <tr><td colSpan={gameType === 'typing' ? 9 : 8} className={styles.empty}>조회된 기록이 없습니다.</td></tr>
        )}
    </tbody>
</table>

      {/* 페이징 */}
      <div className={styles.pagination}>
        {[...Array(totalPages)].map((_, i) => (
          <button
            key={i}
            className={i+1===page ? styles.pageActive : styles.pageBtn}
            onClick={()=>setPage(i+1)}
          >{i+1}</button>
        ))}
      </div>
    </div>
  );
}
