import React, { useState, useEffect } from 'react';
import './App.css';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import RegisterComponent from './components/RegisterComponent';
import LoginComponent from './components/LoginComponent';
import { isUserLoggedIn, logout } from './services/AuthService';
import ListHomeworkComponent from './components/ListHomeworkComponent';
import HomeworkComponent from './components/HomeworkComponent';
import HomeworkDetailComponent from './components/HomeworkDetailComponent';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setIsAuthenticated(isUserLoggedIn());
    setIsLoading(false);
  }, []);

  const handleLogout = () => {
    logout();
    setIsAuthenticated(false);
  };

  function AuthenticatedRoute({ children }) {
    if (isLoading) {
      return <div>Loading...</div>;
    }
    return isAuthenticated ? children : <Navigate to='/login' />;
  }

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <BrowserRouter>
      <div id='root'>
        <HeaderComponent
          isAuthenticated={isAuthenticated}
          onLogout={handleLogout}
        />
        <main className='main-content'>
          <Routes>
            <Route
              path='/'
              element={
                isAuthenticated ? (
                  <Navigate to='/homeworks' />
                ) : (
                  <Navigate to='/login' />
                )
              }
            />
            <Route
              path='/login'
              element={
                isAuthenticated ? (
                  <Navigate to='/homeworks' />
                ) : (
                  <LoginComponent setIsAuthenticated={setIsAuthenticated} />
                )
              }
            />
            <Route
              path='/homeworks'
              element={
                <AuthenticatedRoute>
                  <ListHomeworkComponent />
                </AuthenticatedRoute>
              }
            />
            <Route
              path='/add-homework'
              element={
                <AuthenticatedRoute>
                  <HomeworkComponent />
                </AuthenticatedRoute>
              }
            />
            <Route
              path='/update-homework/:id'
              element={
                <AuthenticatedRoute>
                  <HomeworkComponent />
                </AuthenticatedRoute>
              }
            />
            <Route path='/register' element={<RegisterComponent />} />
            <Route
              path='/homework/:id'
              element={
                <AuthenticatedRoute>
                  <HomeworkDetailComponent />
                </AuthenticatedRoute>
              }
            />
          </Routes>
        </main>
        <FooterComponent />
      </div>
    </BrowserRouter>
  );
}

export default App;
