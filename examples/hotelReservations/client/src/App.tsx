import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.css';
import './App.css'
import './Buttons.css'

import Home from './pages/Home'
import Register from './pages/Register'
import Login from './pages/Login'
import Navigationbar from './layouts/NavigationBar';
import UnauthorizedPage from './pages/UnauthorizedPage';
import SearchContextProvider from './context/SearchContextProvider';
import HotelsPage from './pages/hotels/HotelsPage';
import BookingsPage from './pages/BookingsPage';

function App() {

  return (
    <div className='app'>
      <Router>
          <SearchContextProvider>
            <Navigationbar />
            <div className='container container-fluid'>
              <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/home' element={<Home />} />
                <Route path='/register' element={<Register />} />
                <Route path='/login' element={<Login />} />
                <Route path='/hotels/*' element={<HotelsPage />} />
                <Route path='/bookings/*' element={<BookingsPage />} />
                <Route path='/unauthorized' element={<UnauthorizedPage />} />
                <Route path='*' element={<Navigate to="/" />} />
              </Routes>
            </div>
          </SearchContextProvider>
      </Router>

    </div>
  )
}

export default App
