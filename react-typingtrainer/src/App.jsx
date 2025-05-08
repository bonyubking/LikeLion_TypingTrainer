
import Header from './components/Header/Header';
import AppRoutes from './Router';
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import HeaderRouterWrapper from './HeaderRouterWrapper';


function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        <HeaderRouterWrapper />
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