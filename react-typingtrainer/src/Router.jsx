/* 라우팅 설정을 담당. 페이지별 URL path와 컴포넌트를 연결함*/
/*
페이지 만든 후 여기에 Import해서 아래 return문에 <Route>처럼 추가
path = "/" -> http://localhost:3000/chat 임
*/

import { Routes, Route } from 'react-router-dom';

import MainPage from './pages/MainPage';
import ChatPage from './pages/ChatPage';
import HomePage from './pages/HomePage';
import PostPage from './pages/PostPage';
import PostDetailPage from './pages/PostPage/PostDetailPage';
import PostWritePage from './pages/PostPage/PostWritePage';
import TypingRecordPage from './pages/TypingRecordPage';
// import SignIn from './pages/SignIn';
import SignupPage from './pages/SignupPage';
import SongGamePage from './pages/SongGamePage';
import SongGamePlayPage from './pages/SongGamePage/GamePage';
// 필요한 페이지 import...


export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/chat" element={<ChatPage />} />
      <Route path="/home" element={<HomePage />} />
      <Route path="/signup" element={<SignupPage />} />

      {/* <Route path="/signin" element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} /> */}
      {/* 다른 Route도 이 아래에 계속 추가 */}
      <Route path="/post" element={<PostPage />} />
      <Route path="/post/:postId" element={<PostDetailPage />} />
      <Route path="/post/write" element={<PostWritePage />} />
      <Route path="/typing-record" element={<TypingRecordPage />} />
      <Route path="/song-record" element={<TypingRecordPage />} />
      <Route path="/song-game" element={<SongGamePage />} />
      <Route path="/song-game/play" element={<SongGamePlayPage />} />
    </Routes>
  );
}