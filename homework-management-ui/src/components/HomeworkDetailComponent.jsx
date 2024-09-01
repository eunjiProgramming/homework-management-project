import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getHomework } from '../services/HomeworkService';

const HomeworkDetailComponent = () => {
  const [homework, setHomework] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    getHomework(id)
      .then((response) => {
        setHomework(response.data);
      })
      .catch((error) => {
        console.error('Error fetching homework details: ', error);
      });
  }, [id]);

  if (!homework) return <div>Loading...</div>;

  return (
    <div className='container mx-auto px-4 py-8'>
      <div className='bg-white shadow-md rounded-lg p-6'>
        <h2 className='text-2xl font-bold mb-4'>{homework.title}</h2>
        <p className='mb-2'>
          <strong>Description:</strong> {homework.description}
        </p>
        <p className='mb-2'>
          <strong>Class:</strong> {homework.className}
        </p>
        <p className='mb-2'>
          <strong>Instructor:</strong> {homework.instructor}
        </p>
        <p className='mb-2'>
          <strong>Status:</strong>{' '}
          {homework.completed ? 'Completed' : 'Not Completed'}
        </p>
        <button
          onClick={() => navigate('/homeworks')}
          className='mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
        >
          Back to List
        </button>
      </div>
    </div>
  );
};

export default HomeworkDetailComponent;
