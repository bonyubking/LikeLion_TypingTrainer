import axios from 'axios';
const SERVER_URL = process.env.REACT_APP_HTTP_URL;

export async function fetchRandomProblem(language, difficulty, type) {
    try {
        const res = await fetch(`${SERVER_URL}/api/problem/random?lang=${language}&diff=${difficulty}&type=${type}`);
        const data = await res.json();
        console.log("🔵 서버 응답:", data);
        if (!res.ok) throw new Error('문제 로딩 실패');
        return data;
    } catch (err) {
        console.error("❌ 문제 로딩 실패:", err);
        return { content: '문제를 불러올 수 없습니다.' };
    }
}



/**
 * 게임 기록 저장
 * @param {Object} data - 기록할 정보
 * @param {string} data.userId - 사용자 ID
 * @param {number} data.speed - 타수 (타자 속도)
 * @param {number} data.accuracy - 정확도 (%)
 * @param {number} data.problemIndex - 푼 문제 수
 * @param {number} data.totalTime - 전체 제한 시간 (초)
 */
export const saveGameRecord = async (data) => {
    try {
        const response = await axios.post(`${SERVER_URL}/api/gamerecord/save`, data);
        return response.data;
    } catch (error) {
        console.error('게임 기록 저장 실패:', error);
        throw error;
    }
};
