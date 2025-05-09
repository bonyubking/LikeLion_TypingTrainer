import axios from 'axios';
const SERVER_URL = process.env.REACT_APP_HTTP_URL;

export async function fetchRandomProblem(language, difficulty, type) {
    try {
        const response = await fetch(`${SERVER_URL}/api/problem/random?lang=${language}&diff=${difficulty}&type=${type}`);

        if (!response.ok) {
            throw new Error('문제를 불러오는 데 실패했습니다. 상태 코드: ' + response.status);
        }

        const contentType = response.headers.get('Content-Type');
        if (!contentType || !contentType.includes('application/json')) {
            throw new Error('응답이 JSON 형식이 아닙니다.');
        }

        const data = await response.json();
        console.log("🔵 문제 로딩 성공:", data);

        return data;
    } catch (error) {
        console.error('문제 로딩 실패:', error);
        return { content: '문제를 불러올 수 없습니다.' };
    }
}
