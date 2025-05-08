/* 라우팅 설정을 담당. 페이지별 URL path와 컴포넌트를 연결함*/
/*
페이지 만든 후 여기에 Import해서 아래 return문에 <Route>처럼 추가
path = "/" -> http://localhost:3000/chat 임
*/

import { Routes, Route } from 'react-router-dom';

import MainPage from './pages/MainPage';
import ChatPage from './pages/ChatPage';
import HomePage from './pages/HomePage';
import Mode from './pages/TypingGamePage/mode';
import TypingGamePage from './pages/TypingGamePage/Play';
// import SignIn from './pages/SignIn';
// import SignUp from './pages/SignUp';
// 필요한 페이지 import...

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/chat" element={<ChatPage />} />
      <Route path="/home" element={<HomePage />} />
      {/* <Route path="/signin" element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} /> */}
      {/* 다른 Route도 이 아래에 계속 추가 */}
      <Route path="/typing/mode" element={<Mode />} />
      <Route path="/typing/play" element={<TypingGamePage />} />
    </Routes>
  );
}