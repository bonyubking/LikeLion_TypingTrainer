import { BrowserRouter, useLocation } from 'react-router-dom';

import Header from './components/Header';
import AppRoutes from './Router';

function AppContent() {
  const location = useLocation();
  const isSignupPage = location.pathname === '/signup';

  return (
    <div className="app-container">
      {!isSignupPage && <Header />}
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
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  );
}

export default App;