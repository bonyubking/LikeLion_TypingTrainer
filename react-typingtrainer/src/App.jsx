import { BrowserRouter } from 'react-router-dom';

import Header from './components/Header';
import AppRoutes from './Router';

function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        <Header />
        <main className="main-content">
          <div className="content-wrapper">
            <AppRoutes />
          </div>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;