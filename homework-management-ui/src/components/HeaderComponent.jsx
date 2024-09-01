import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';

const HeaderComponent = ({ isAuthenticated, onLogout }) => {
  const [isScrolled, setIsScrolled] = useState(false);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 100);
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  function handleLogout() {
    onLogout();
    navigate('/login', { replace: true });
  }

  const handleLogoClick = () => {
    if (isAuthenticated) {
      navigate('/homeworks');
    } else {
      navigate('/login');
    }
  };

  return (
    <header
      className={`fixed top-0 left-0 right-0 z-50 transition-colors duration-300 ${
        isScrolled ? 'bg-gray-800/90' : 'bg-gray-800'
      }`}
    >
      <div className='container mx-auto px-4'>
        <div className='flex items-center justify-between h-16'>
          <button
            onClick={handleLogoClick}
            className='text-white text-xl font-bold'
          >
            Homework Management
          </button>
          <div className='hidden md:flex space-x-4'>
            {isAuthenticated && (
              <NavLink
                to='/homeworks'
                className='text-white hover:text-gray-300'
              >
                Homework
              </NavLink>
            )}
            {!isAuthenticated && (
              <>
                <NavLink
                  to='/register'
                  className='text-white hover:text-gray-300'
                >
                  Register
                </NavLink>
                <NavLink to='/login' className='text-white hover:text-gray-300'>
                  Login
                </NavLink>
              </>
            )}
            {isAuthenticated && (
              <button
                onClick={handleLogout}
                className='text-white hover:text-gray-300'
              >
                Logout
              </button>
            )}
          </div>
          <div className='md:hidden'>
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              className='text-white'
            >
              <svg
                className='w-6 h-6'
                fill='none'
                stroke='currentColor'
                viewBox='0 0 24 24'
                xmlns='http://www.w3.org/2000/svg'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth={2}
                  d='M4 6h16M4 12h16m-7 6h7'
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
      {isMenuOpen && (
        <div className='md:hidden bg-gray-700'>
          <div className='px-2 pt-2 pb-3 space-y-1 sm:px-3'>
            {isAuthenticated && (
              <NavLink
                to='/homeworks'
                className='block text-white hover:bg-gray-600 px-3 py-2 rounded-md'
              >
                Homework
              </NavLink>
            )}
            {!isAuthenticated && (
              <>
                <NavLink
                  to='/register'
                  className='block text-white hover:bg-gray-600 px-3 py-2 rounded-md'
                >
                  Register
                </NavLink>
                <NavLink
                  to='/login'
                  className='block text-white hover:bg-gray-600 px-3 py-2 rounded-md'
                >
                  Login
                </NavLink>
              </>
            )}
            {isAuthenticated && (
              <button
                onClick={handleLogout}
                className='block text-white hover:bg-gray-600 px-3 py-2 rounded-md w-full text-left'
              >
                Logout
              </button>
            )}
          </div>
        </div>
      )}
    </header>
  );
};

export default HeaderComponent;
