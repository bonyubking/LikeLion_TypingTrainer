
const handleStart = () => {
    if (!difficulty || !time || !languageType) {
        alert('모든 항목을 선택해주세요.');
        return;
    }

    // 문자열을 초 단위로 변환
    const timeMap = {
        '1분': 60,
        '2분': 120,
        '5분': 300,
        '10분': 600
    };
    const seconds = timeMap[time] ?? 60; // 기본값 60초

    navigate('/typing/play', {
        state: {
            userId: currentUserId,
            difficulty,
            type: languageType.type,
            language: languageType.language,
            totalTime: seconds, // 선택한 제한 시간을 전달
        },
    });
};
