/* 라우팅 설정을 담당. 페이지별 URL path와 컴포넌트를 연결함*/

import { Routes, Route } from 'react-router-dom';

import MainPage from './pages/MainPage';
import ChatPage from './pages/ChatPage';
// import SignIn from './pages/SignIn';
// import SignUp from './pages/SignUp';
// 필요한 페이지 import...

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/chat" element={<ChatPage />} />
      {/* <Route path="/signin" element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} /> */}
      {/* 다른 Route도 이 아래에 계속 추가 */}
    </Routes>
  );
}