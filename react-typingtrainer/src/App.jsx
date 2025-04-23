import { BrowserRouter } from 'react-router-dom';

import Header from './components/Header';
import Footer from './components/Footer';
import AppRoutes from './Router';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <main style={{ padding: '20px' }}>
        <AppRoutes />
      </main>
      <Footer />
    </BrowserRouter>
  );
}

export default App;