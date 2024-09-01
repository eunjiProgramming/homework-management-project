import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  completeHomework,
  deleteHomework,
  getAllHomeworks,
  inCompleteHomework,
} from '../services/HomeworkService';
import { isAdminUser } from '../services/AuthService';

const ListHomeworkComponent = () => {
  const [homeworks, setHomeworks] = useState([]);
  const navigate = useNavigate();
  const isAdmin = isAdminUser();

  useEffect(() => {
    listHomeworks();
  }, []);

  function listHomeworks() {
    getAllHomeworks()
      .then((response) => {
        setHomeworks(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }

  function addNewHomework() {
    navigate('/add-homework');
  }

  function updateHomework(id) {
    navigate(`/update-homework/${id}`);
  }

  function removeHomework(id) {
    deleteHomework(id)
      .then(() => {
        listHomeworks();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function markCompleteHomework(id) {
    completeHomework(id)
      .then(() => {
        listHomeworks();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function markInCompleteHomework(id) {
    inCompleteHomework(id)
      .then(() => {
        listHomeworks();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const truncateText = (text, maxLength) => {
    return text.length > maxLength
      ? text.substring(0, maxLength) + '...'
      : text;
  };

  const goToHomeworkDetail = (id) => {
    navigate(`/homework/${id}`);
  };

  return (
    <div className='container mx-auto px-4 py-8'>
      <div className='flex justify-between items-center mb-8'>
        <h2 className='text-3xl font-bold text-gray-800'>List of Homeworks</h2>
        {isAdmin && (
          <button
            className='bg-blue-500 hover:bg-blue-600 text-white p-2 rounded-full transition duration-300 ease-in-out transform hover:scale-110'
            onClick={addNewHomework}
            title='Add Homework'
          >
            <svg
              xmlns='http://www.w3.org/2000/svg'
              className='h-6 w-6'
              viewBox='0 0 20 20'
              fill='currentColor'
            >
              <path
                fillRule='evenodd'
                d='M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z'
                clipRule='evenodd'
              />
            </svg>
          </button>
        )}
      </div>

      <div className='bg-white shadow-md rounded-lg overflow-hidden'>
        <table className='min-w-full divide-y divide-gray-200'>
          <thead className='bg-gray-50'>
            <tr>
              <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                Class Name
              </th>
              <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                Instructor
              </th>
              <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                Homework Title
              </th>
              <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                Homework Description
              </th>
              <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                Homework Completed
              </th>
              <th className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                Actions
              </th>
            </tr>
          </thead>
          <tbody className='bg-white divide-y divide-gray-200'>
            {homeworks.map((homework) => (
              <tr
                key={homework.id}
                className='hover:bg-gray-50 cursor-pointer'
                onClick={() => goToHomeworkDetail(homework.id)}
              >
                <td className='px-6 py-4 whitespace-nowrap text-sm text-gray-500'>
                  {homework.className}
                </td>
                <td className='px-6 py-4 whitespace-nowrap text-sm text-gray-500'>
                  {homework.instructor}
                </td>
                <td className='px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900'>
                  {homework.title}
                </td>
                <td className='px-6 py-4 text-sm text-gray-500 max-w-xs overflow-hidden'>
                  <span className='block truncate' title={homework.description}>
                    {truncateText(homework.description, 50)}
                  </span>
                </td>
                <td className='px-6 py-4 whitespace-nowrap'>
                  <span
                    className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                      homework.completed
                        ? 'bg-green-100 text-green-800'
                        : 'bg-red-100 text-red-800'
                    }`}
                  >
                    {homework.completed ? 'YES' : 'NO'}
                  </span>
                </td>
                <td className='px-6 py-4 whitespace-nowrap text-sm font-medium'>
                  <div className='flex space-x-2'>
                    {isAdmin && (
                      <>
                        <button
                          onClick={(e) => {
                            e.stopPropagation();
                            updateHomework(homework.id);
                          }}
                          className='text-indigo-600 hover:text-indigo-900'
                          title='Update'
                        >
                          <svg
                            xmlns='http://www.w3.org/2000/svg'
                            className='h-5 w-5'
                            viewBox='0 0 20 20'
                            fill='currentColor'
                          >
                            <path d='M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z' />
                          </svg>
                        </button>
                        <button
                          onClick={(e) => {
                            e.stopPropagation();
                            removeHomework(homework.id);
                          }}
                          className='text-red-600 hover:text-red-900'
                          title='Delete'
                        >
                          <svg
                            xmlns='http://www.w3.org/2000/svg'
                            className='h-5 w-5'
                            viewBox='0 0 20 20'
                            fill='currentColor'
                          >
                            <path
                              fillRule='evenodd'
                              d='M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z'
                              clipRule='evenodd'
                            />
                          </svg>
                        </button>
                      </>
                    )}
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        markCompleteHomework(homework.id);
                      }}
                      className='text-green-600 hover:text-green-900'
                      title='Mark as Complete'
                    >
                      <svg
                        xmlns='http://www.w3.org/2000/svg'
                        className='h-5 w-5'
                        viewBox='0 0 20 20'
                        fill='currentColor'
                      >
                        <path
                          fillRule='evenodd'
                          d='M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z'
                          clipRule='evenodd'
                        />
                      </svg>
                    </button>
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        markInCompleteHomework(homework.id);
                      }}
                      className='text-yellow-600 hover:text-yellow-900'
                      title='Mark as Incomplete'
                    >
                      <svg
                        xmlns='http://www.w3.org/2000/svg'
                        className='h-5 w-5'
                        viewBox='0 0 20 20'
                        fill='currentColor'
                      >
                        <path
                          fillRule='evenodd'
                          d='M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z'
                          clipRule='evenodd'
                        />
                      </svg>
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListHomeworkComponent;
