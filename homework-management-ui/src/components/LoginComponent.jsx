import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  loginAPICall,
  saveLoggedInUser,
  storeToken,
} from '../services/AuthService';

const LoginComponent = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');

  const navigator = useNavigate();

  async function handleLoginForm(e) {
    e.preventDefault();
    setError('');

    try {
      const response = await loginAPICall(username, password);
      const token = 'Bearer ' + response.data.accessToken;
      storeToken(token);
      saveLoggedInUser(username, response.data.role);
      navigator('/homeworks');
      window.location.reload(false);
    } catch (error) {
      console.error(error);
      setError('Invalid credentials. Please try again.');
    }
  }

  return (
    <div className='flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8'>
      <div className='max-w-md w-full space-y-8'>
        <div>
          <h2 className='mt-6 text-center text-3xl font-extrabold text-gray-900'>
            Login to your account
          </h2>
        </div>
        <form className='mt-8 space-y-6' onSubmit={handleLoginForm}>
          <div className='rounded-md shadow-sm -space-y-px'>
            <div>
              <label htmlFor='username' className='sr-only'>
                Username or Email
              </label>
              <input
                id='username'
                name='username'
                type='text'
                required
                className='appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm'
                placeholder='Username or Email'
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
            <div>
              <label htmlFor='password' className='sr-only'>
                Password
              </label>
              <input
                id='password'
                name='password'
                type={showPassword ? 'text' : 'password'}
                required
                className='appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm'
                placeholder='Password'
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
          </div>

          <div className='flex items-center justify-between'>
            <div className='flex items-center'>
              <input
                id='show-password'
                name='show-password'
                type='checkbox'
                className='h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded'
                checked={showPassword}
                onChange={() => setShowPassword(!showPassword)}
              />
              <label
                htmlFor='show-password'
                className='ml-2 block text-sm text-gray-900'
              >
                Show password
              </label>
            </div>
          </div>

          {error && <p className='text-red-500 text-sm'>{error}</p>}

          <div>
            <button
              type='submit'
              className='group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'
            >
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginComponent;
