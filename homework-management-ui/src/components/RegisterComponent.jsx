import React, { useState } from 'react';
import { registerAPICall } from '../services/AuthService';
import { Link } from 'react-router-dom';

const RegisterComponent = () => {
  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);

  function handleRegistrationForm(e) {
    e.preventDefault();
    const register = { name, username, email, password };
    console.log(register);
    registerAPICall(register)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <div className='flex items-center justify-center  bg-gray-100'>
      <div className='w-full max-w-md px-8 py-6 bg-white shadow-lg rounded-lg'>
        <h3 className='text-2xl font-bold text-center mb-6'>Create Account</h3>
        <form onSubmit={handleRegistrationForm}>
          <div className='space-y-4'>
            <div>
              <label
                className='block text-sm font-medium text-gray-700'
                htmlFor='name'
              >
                Name
              </label>
              <input
                type='text'
                placeholder='Enter your name'
                id='name'
                name='name'
                className='mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
            <div>
              <label
                className='block text-sm font-medium text-gray-700'
                htmlFor='username'
              >
                Username
              </label>
              <input
                type='text'
                placeholder='Choose a username'
                id='username'
                name='username'
                className='mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div>
              <label
                className='block text-sm font-medium text-gray-700'
                htmlFor='email'
              >
                Email
              </label>
              <input
                type='email'
                placeholder='name@example.com'
                id='email'
                name='email'
                className='mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div>
              <label
                className='block text-sm font-medium text-gray-700'
                htmlFor='password'
              >
                Password
              </label>
              <div className='relative mt-1'>
                <input
                  type={showPassword ? 'text' : 'password'}
                  placeholder='Create a password'
                  id='password'
                  name='password'
                  className='block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500'
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
                <button
                  type='button'
                  className='absolute inset-y-0 right-0 pr-3 flex items-center text-sm leading-5 text-gray-500 hover:text-gray-700'
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? 'Hide' : 'Show'}
                </button>
              </div>
            </div>
            <div>
              <button
                type='submit'
                className='w-full px-4 py-2 text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:outline-none focus:bg-blue-600'
              >
                Sign Up
              </button>
            </div>
          </div>
        </form>
        <div className='mt-4 text-sm text-center text-gray-600'>
          Already have an account?{' '}
          <Link to='/login' className='text-blue-600 hover:underline'>
            Sign in
          </Link>
        </div>
      </div>
    </div>
  );
};

export default RegisterComponent;
