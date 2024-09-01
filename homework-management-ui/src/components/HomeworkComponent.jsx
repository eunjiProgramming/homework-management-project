import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  getHomework,
  saveHomework,
  updateHomework,
} from '../services/HomeworkService';

const HomeworkComponent = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [completed, setCompleted] = useState(false);
  const [instructor, setInstructor] = useState('');
  const [className, setClassName] = useState('basic');
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      getHomework(id)
        .then((response) => {
          const homework = response.data;
          setTitle(homework.title);
          setDescription(homework.description);
          setCompleted(homework.completed);
          setInstructor(homework.instructor);
          setClassName(homework.className);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [id]);

  const saveOrUpdateHomework = (e) => {
    e.preventDefault();
    const homework = { title, description, completed, instructor, className };

    if (id) {
      updateHomework(id, homework)
        .then(() => navigate('/homeworks'))
        .catch((error) => console.error(error));
    } else {
      saveHomework(homework)
        .then(() => navigate('/homeworks'))
        .catch((error) => console.error(error));
    }
  };

  const pageTitle = id ? 'Update Homework' : 'Add Homework';

  return (
    <div className='flex items-center justify-center min-h-screen bg-gray-100'>
      <div className='px-8 py-6 mx-4 mt-4 text-left bg-white shadow-lg md:w-1/3 lg:w-1/3 sm:w-1/3'>
        <h3 className='text-2xl font-bold text-center'>{pageTitle}</h3>
        <form onSubmit={saveOrUpdateHomework}>
          <div className='mt-4'>
            <div className='mt-4'>
              <label className='block' htmlFor='title'>
                Homework Title
              </label>
              <input
                type='text'
                id='title'
                className='w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600'
                placeholder='Enter Homework Title'
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            <div className='mt-4'>
              <label className='block' htmlFor='description'>
                Homework Description
              </label>
              <textarea
                id='description'
                className='w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600'
                placeholder='Enter Homework Description'
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
              />
            </div>
            <div className='mt-4'>
              <label className='block' htmlFor='instructor'>
                Instructor
              </label>
              <input
                type='text'
                id='instructor'
                className='w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600'
                placeholder='Enter Instructor Name'
                value={instructor}
                onChange={(e) => setInstructor(e.target.value)}
                required
              />
            </div>
            <div className='mt-4'>
              <label className='block' htmlFor='className'>
                Class Name
              </label>
              <select
                id='className'
                className='w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600'
                value={className}
                onChange={(e) => setClassName(e.target.value)}
                required
              >
                <option value='basic'>Basic</option>
                <option value='intermediate'>Intermediate</option>
                <option value='advanced'>Advanced</option>
              </select>
            </div>
            <div className='mt-4'>
              <label className='block' htmlFor='completed'>
                Homework Completed
              </label>
              <select
                id='completed'
                className='w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600'
                value={completed}
                onChange={(e) => setCompleted(e.target.value === 'true')}
              >
                <option value='false'>No</option>
                <option value='true'>Yes</option>
              </select>
            </div>
            <div className='flex items-center justify-center mt-6'>
              <button
                type='submit'
                className='px-6 py-2 leading-5 text-white transition-colors duration-200 transform bg-blue-500 rounded-md hover:bg-blue-700 focus:outline-none focus:bg-gray-600'
              >
                {id ? 'Update' : 'Submit'}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default HomeworkComponent;
