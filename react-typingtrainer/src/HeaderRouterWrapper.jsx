
import React from 'react';
import { useLocation } from 'react-router-dom';
import Header from './components/Header/Header';


const HeaderRouterWrapper = () => {
    const location = useLocation();

    const titleMap = {
        '/typing/mode': {
            title: '타자 연습',
            subtitle: '주어진 단어와 문장들을 빠르게 입력해보세요!'
        },
        '/typing/play': {
            title: '타자 연습',
            subtitle: '높은 랭킹을 달성해보세요!'
        },
        '/home': '홈',
        '/chat': '채팅',
    };

    const routeInfo = titleMap[location.pathname] || { title: '', subtitle: '' };
    return <Header {...routeInfo} />;
};

export default HeaderRouterWrapper;
