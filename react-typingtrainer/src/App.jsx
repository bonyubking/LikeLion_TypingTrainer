import { BrowserRouter, useLocation } from 'react-router-dom';
import { UserProvider } from './contexts/UserContext';
import Click from './assets/mp3/흘릭.mp3';
import { useEffect } from 'react';

import Header from './components/Header';
import AppRoutes from './Router';


function AppContent() {
  const location = useLocation();
  const isSignupPage = location.pathname === '/signup';

  useEffect(() => {
    const audio = new Audio(Click);
    audio.volume = 0.2;
    const handleClick = (e) => {
      if (e.target.tagName === 'BUTTON') {
        audio.currentTime = 0;
        audio.play();
      }
    };
    document.addEventListener('mousedown', handleClick);
    return () => document.removeEventListener('mousedown', handleClick);
  }, []);

  return (
    <div className="app-container">
      <Header />
      <main className="main-content">
        <div className="content-wrapper">
          <AppRoutes />
        </div>
      </main>
    </div>
  );
}

function App() {
  return (
    <UserProvider>
      <BrowserRouter>
        <AppContent />
      </BrowserRouter>
    </UserProvider>
  );
}

export default App;