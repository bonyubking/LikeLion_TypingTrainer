import axios from 'axios';
const SERVER_URL = process.env.REACT_APP_HTTP_URL;

export const fetchRandomProblem = (language, difficulty, type) =>
    axios.get('/api/problems/random', { params: { language, difficulty, type } });


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
