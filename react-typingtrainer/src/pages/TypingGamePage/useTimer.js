import { useState, useEffect } from 'react';

const useTimer = (totalTime, onFinish) => {
    const [timeLeft, setTimeLeft] = useState(totalTime);

    useEffect(() => {
        // 타이머가 시작될 때만 실행되도록 해야 함
        if (isNaN(totalTime) || totalTime <= 0) return; // 유효성 검사

        const interval = setInterval(() => {
            setTimeLeft((prev) => {
                if (prev <= 1) {
                    clearInterval(interval);  // 타이머 종료
                    onFinish?.();  // 타이머 종료 후 콜백 호출
                    return 0;  // 0으로 설정
                }
                return prev - 1;  // 1초씩 차감
            });
        }, 1000);

        return () => clearInterval(interval); // 컴포넌트 언마운트 시 클리어
    }, []); // 의존성 배열에서 `totalTime` 제거

    return [timeLeft, setTimeLeft];
};

export default useTimer;
