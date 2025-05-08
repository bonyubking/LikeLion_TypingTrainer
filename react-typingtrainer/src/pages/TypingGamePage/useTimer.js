import { useState, useEffect } from 'react';

const useTimer = (totalTime, onFinish) => {
    const [timeLeft, setTimeLeft] = useState(totalTime);

    useEffect(() => {
        if (!totalTime || isNaN(totalTime)) return;

        const interval = setInterval(() => {
            setTimeLeft((prev) => {
                if (prev <= 1) {
                    clearInterval(interval);
                    onFinish?.(); // 안전하게 함수 호출
                    return 0;
                }
                return prev - 1;
            });
        }, 1000);

        return () => clearInterval(interval); // 언마운트 시 클리어
    }, [totalTime, onFinish]);

    return [timeLeft, setTimeLeft];
};

export default useTimer;
