import React, { useState, useEffect } from 'react';
import './result.css';

export function GameResultPopup({
    userId, accuracy, typingSpeed, correctCount, wrongCount, totalTime, closePopup, saveGameRecord
}) {

    const userId = sessionStorage.getItem('userId');

    const gameResult = {
        userId,
        accuracy,
        typingSpeed,
        correctCount,
        wrongCount,
        totalTime,
    };

    const handleSaveRecord = async () => {
        try {
            // 게임 기록을 DB에 저장하는 API 호출 예시
            await saveGameRecord(gameResult);
            console.log('게임 결과가 성공적으로 저장되었습니다!');
            closePopup();  // 결과 저장 후 팝업 닫기
        } catch (error) {
            console.error('게임 기록 저장 오류:', error);
        }
    };

    return (
        <div className="overlay">
            <div className="modal">
                <h2>게임 종료</h2>
                <p>사용자 ID: {userId}</p>
                <p>정확도: {accuracy}%</p>
                <p>타수: {typingSpeed}타</p>
                <p>정답 수: {correctCount}개</p>
                <p>틀린 문제 수: {wrongCount}개</p>
                <p>제한 시간: {totalTime}초</p>
                <button onClick={handleSaveRecord}>게임 결과 저장</button>
                <button onClick={closePopup}>닫기</button>
            </div>
        </div>
    );
}



export default function Game() {
    const [isPopupVisible, setPopupVisible] = useState(false);  // 팝업 상태 관리
    const [gameStats, setGameStats] = useState({
        userId: sessionStorage.getItem('userId'),  // 기본 사용자 ID (로그인 시 실제 ID로 교체)
        accuracy: 100,       // 정확도 (초기값)
        typingSpeed: 0,      // 타수 (초기값)
        correctCount: 0,     // 맞춘 문제 수 (초기값)
        wrongCount: 0       // 틀린 문제 수 (초기값)
    });
    const [showGameStartButton, setShowGameStartButton] = useState(true);  // 게임 시작 버튼 상태

    // 게임 종료 후 결과를 저장하고, 팝업을 표시하는 함수
    const handleGameEnd = () => {
        // 로그인 여부 체크 후, 기록 저장
        if (gameStats.userId !== 'guest') {
            console.log('게임 기록 저장');
        }

        setPopupVisible(true);  // 게임 끝나면 팝업 보이게 설정
        setShowGameStartButton(false);  // 게임 시작 버튼 숨기기
    };

    const closePopup = () => {
        setPopupVisible(false); // 팝업 닫기
        setShowGameStartButton(true);  // 게임 시작 버튼 다시 보이게 설정
    };

    // 게임 결과를 DB에 저장하는 함수
    const saveGameRecord = async (result) => {
        try {
            // 예시: 게임 기록을 저장하는 API 호출
            const response = await fetch(`${SERVER_URL}/game/typing`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(result),
            });

            if (response.ok) {
                console.log('게임 결과가 DB에 저장되었습니다!');
            } else {
                console.error('게임 결과 저장 실패');
            }
        } catch (error) {
            console.error('API 호출 오류:', error);
        }
    };

    // 정확도, 타수, 맞춘 문제 수 등을 실시간으로 업데이트하는 로직을 작성
    useEffect(() => {
        // 게임이 시작될 때마다 초기화하거나, 게임 진행 중 상태 변경
        const interval = setInterval(() => {
            // 예시: 타수와 정확도 업데이트
            setGameStats(prevStats => ({
                ...prevStats,
                typingSpeed: prevStats.correctCount + prevStats.wrongCount,  // 예시로 타수를 맞춘 문제 수 + 틀린 문제 수로 계산
                accuracy: prevStats.correctCount + prevStats.wrongCount === 0
                    ? 100
                    : Math.round((prevStats.correctCount / (prevStats.correctCount + prevStats.wrongCount)) * 100)
            }));
        }, 1000);  // 매 초마다 상태 업데이트 (예시)

        return () => clearInterval(interval); // cleanup
    }, [gameStats.correctCount, gameStats.wrongCount]);

    useEffect(() => {
        // 예시로 게임 종료를 5초 뒤에 실행 (실제 게임 로직에 맞게 수정)
        setTimeout(() => {
            handleGameEnd();
        }, 5000);  // 5초 후에 게임 종료
    }, []);

    return (
        <div>
            {showGameStartButton && <button onClick={handleGameEnd}>게임 시작</button>}  {/* 게임 시작 버튼 */}

            {isPopupVisible && (
                <GameResultPopup
                    userId={gameStats.userId}
                    accuracy={gameStats.accuracy}
                    typingSpeed={gameStats.typingSpeed}
                    correctCount={gameStats.correctCount}
                    wrongCount={gameStats.wrongCount}
                    totalTime={5000}  // 예시 제한시간 (5초)
                    closePopup={closePopup}  // 팝업 닫기 함수
                    saveGameRecord={saveGameRecord}  // 게임 기록 저장 함수 전달
                />
            )}
        </div>
    );
}
