import axios from 'axios';
import { getToken } from './AuthService';

const BASE_REST_API_URL = 'http://localhost:8090/api/homeworks';

// 요청 인터셉터 추가
axios.interceptors.request.use(
  function (config) {
    config.headers['Authorization'] = getToken();
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

// 모든 homework를 가져오는 함수
export const getAllHomeworks = () => axios.get(BASE_REST_API_URL);

// 새로운 homework를 저장하는 함수
export const saveHomework = (homework) =>
  axios.post(BASE_REST_API_URL, homework);

// 특정 homework를 가져오는 함수
export const getHomework = (id) => axios.get(`${BASE_REST_API_URL}/${id}`);

// 특정 homework를 업데이트하는 함수
export const updateHomework = (id, homework) =>
  axios.put(`${BASE_REST_API_URL}/${id}`, homework);

// 특정 homework를 삭제하는 함수
export const deleteHomework = (id) =>
  axios.delete(`${BASE_REST_API_URL}/${id}`);

// 특정 homework를 완료 상태로 변경하는 함수
export const completeHomework = (id) =>
  axios.patch(`${BASE_REST_API_URL}/${id}/complete`);

// 특정 homework를 미완료 상태로 변경하는 함수
export const inCompleteHomework = (id) =>
  axios.patch(`${BASE_REST_API_URL}/${id}/in-complete`);
