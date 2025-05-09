import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import useTimer from './useTimer';
import CorrectModal from '../../components/TypingGame/CorrectModal';
import WrongModal from '../../components/TypingGame/WrongModal';
import common from '../../styles/common.module.css';
import styles from './play.module.css';
import { fetchRandomProblem } from '../../api/gameApi';
import ModeModal from '../../components/TypingGame/ModeModal';
import ResultModal from '../../components/TypingGame/ResultModal';

// 한글 자모 분리 함수
function splitHangul(char) {
    const HANGUL_START = 0xac00;
    const HANGUL_END = 0xd7a3;
    const INITIALS = ['ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];
    const MEDIALS = ['ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'];
    const FINALS = ['', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'];

    const charCode = char.charCodeAt(0);
    if (charCode < HANGUL_START || charCode > HANGUL_END) return [char];

    const offset = charCode - HANGUL_START;
    const initialIndex = Math.floor(offset / 588);
    const medialIndex = Math.floor((offset % 588) / 28);
    const finalIndex = offset % 28;

    return [INITIALS[initialIndex], MEDIALS[medialIndex], FINALS[finalIndex]];
}

function splitHangulString(str) {
    return str.split('').flatMap(splitHangul);
}

function countTypingUnits(text, language) {
    if (language === '한국어') {
        return splitHangulString(text).length;
    } else {
        return text.length;  // 영어는 각 문자가 하나의 유닛
    }
}

export default function TypingPlay() {
    const location = useLocation();
    const { userId = sessionStorage.getItem('userId'), language, difficulty, type, totalTime } = location.state || {};

    const [question, setQuestion] = useState('');
    const [userInput, setUserInput] = useState('');
    const [correctCount, setCorrectCount] = useState(0);
    const [wrongCount, setWrongCount] = useState(0);
    const [totalTypedUnits, setTotalTypedUnits] = useState(0);
    const [speed, setSpeed] = useState(0);
    const [showCorrectModal, setShowCorrectModal] = useState(false);
    const [showWrongModal, setShowWrongModal] = useState(false);
    const [showResultModal, setShowResultModal] = useState(false);

    const navigate = useNavigate();
    const [gameStats, setGameStats] = useState({
        userId,
        accuracy: 0,  // 정확도 계산
        typingSpeed: 0,  // 타수 계산
        correctCount: 0,
        wrongCount: 0,
        totalTime
    });

    const handleFinish = () => {
        setShowResultModal(true);
        saveGameRecord(gameStats); // 결과 저장
    };

    const [timeLeft, setTimeLeft] = useTimer(totalTime, handleFinish);
    const elapsedTime = totalTime - timeLeft;

    useEffect(() => {
        loadQuestion();
    }, []);

    useEffect(() => {
        const minutes = elapsedTime / 60;
        const newSpeed = minutes > 0 ? Math.floor(totalTypedUnits / minutes) : 0;
        setSpeed(newSpeed);
    }, [totalTypedUnits, elapsedTime]);

    const accuracy = correctCount + wrongCount === 0
        ? 100
        : Math.round((correctCount / (correctCount + wrongCount)) * 100);

    const loadQuestion = async () => {
        const data = await fetchRandomProblem(language, difficulty, type);
        setQuestion(data.content);
    };

    function handleSubmit() {
        const typedUnits = countTypingUnits(userInput, language);
        setTotalTypedUnits(prev => prev + typedUnits);

        if (userInput === question) {
            setCorrectCount(prev => prev + 1);
            setShowCorrectModal(true);
        } else {
            setWrongCount(prev => prev + 1);
            setShowWrongModal(true);
        }

        setUserInput('');
        loadQuestion();
    }

    // 게임 기록 저장 (DB 저장하는 API 연동 필요)
    const saveGameRecord = (result) => {
        console.log('게임 결과 저장:', result);
        // 서버로 결과 저장하는 API 호출 필요
    };
    const goHome = () => {
        navigate('/game-mode');
    };

    return (
        <div>


            <div className={common.container}>
                <div className={styles.stats}>
                    <span>남은 시간 : {timeLeft}초</span>
                    <span>정확도 : {accuracy}%</span>
                    <span>타수 : {speed}타</span>
                    <span>문제 수 : 맞은 {correctCount}개 / 틀린 {wrongCount}개</span>
                </div>

                <div className={styles.questionBox}>
                    <p className={styles.question}>{question}</p>
                </div>

                <div className={styles.inputSection}>
                    <span className={styles.timer}>남은 시간 : {timeLeft}초</span>
                    <div className={styles.inputGroup}>
                        <input
                            type="text"
                            placeholder="정답을 입력해주세요."
                            className={styles.inputField}
                            value={userInput}
                            onChange={(e) => setUserInput(e.target.value)}
                            onKeyDown={(e) => e.key === 'Enter' && handleSubmit()}
                        />
                        <button className={styles.submitButton} onClick={handleSubmit}>입력</button>
                    </div>
                </div>

                {showCorrectModal && <CorrectModal onClose={() => setShowCorrectModal(false)} />}
                {showWrongModal && <WrongModal onClose={() => setShowWrongModal(false)} />}
                {showResultModal && (
                    <ResultModal
                        onClose={() => setShowResultModal(false)}  // 모달 닫기 함수
                        accuracy={accuracy}
                        typingSpeed={gameStats.typingSpeed}
                        correctCount={correctCount}
                        wrongCount={wrongCount}
                        totalTime={totalTime}
                    />
                )}
            </div>
        </div>
    );
}
