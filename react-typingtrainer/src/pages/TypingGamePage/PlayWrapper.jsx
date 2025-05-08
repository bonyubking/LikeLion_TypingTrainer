import { useLocation, Navigate } from 'react-router-dom';
import TypingPlay from './Play';

export default function PlayWrapper() {
    const location = useLocation();
    const state = location.state;

    // location.state가 없으면 모드 선택 페이지로 리디렉트
    if (!state || !state.language || !state.difficulty || !state.type) {
        return <Navigate to="/typing/mode" replace />;
    }

    return <TypingPlay />;
}
