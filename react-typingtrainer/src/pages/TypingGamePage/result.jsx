import React, { useState, useEffect } from 'react';

function GameResultPopup({ userId, accuracy, typingSpeed, correctCount, wrongCount, closePopup }) {
    return (
        <div className="popup-overlay">
            <div className="popup">
                <h2>게임 결과</h2>
                <div className="result">
                    <p><strong>ID:</strong> {userId}</p>
                    <p><strong>정확도:</strong> {accuracy}%</p>
                    <p><strong>평균 타수:</strong> {typingSpeed} 타/분</p>
                    <p><strong>맞춘 문제 수:</strong> {correctCount}</p>
                </div>
                <div className="popup-buttons">
                    <button onClick={closePopup}>홈으로</button>
                    <button onClick={() => window.location.reload()}>다시 플레이하기</button>
                </div>
            </div>
        </div>
    );
}


export default function Game() {
    const [isPopupVisible, setPopupVisible] = useState(false);
    const [gameStats, setGameStats] = useState({
        userId: 'testUser',
        accuracy: 98.5,
        typingSpeed: 300,
        correctCount: 40,
        wrongCount: 5
    });

    const handleGameEnd = () => {
        // 로그인 여부 체크 후, 기록 저장
        if (gameStats.userId !== 'guest') {
            // 사용자 기록 DB 저장 로직 (예: POST API 호출)
            console.log('게임 기록 저장');
        }

        setPopupVisible(true);  // 게임 끝나면 팝업 보이게 설정
    };

    const closePopup = () => {
        setPopupVisible(false); // 팝업 닫기
    };

    useEffect(() => {
        // 예시로 게임 종료를 5초 뒤에 실행 (실제 게임 로직에 맞게 수정)
        setTimeout(() => {
            handleGameEnd();
        }, 5000);
    }, []);

    return (
        <div>
            <button onClick={handleGameEnd}>게임 시작</button>
            {showResultModal && (
                <GameResultPopup
                    userId={gameStats.userId}
                    accuracy={accuracy}
                    typingSpeed={speed}
                    correctCount={correctCount}
                    wrongCount={wrongCount}
                    closePopup={() => setShowResultModal(false)}  // 팝업 닫기 함수
                />
            )}

        </div>
    );
}
