// src/api/client.js
import axios from 'axios';
// import { mockServers } from '../data/mock'; 이제 필요없음

// 실제 백엔드 API의 기본 주소, 나중에 백엔드를 만들면 여기에 그 주소를 넣을 것이다.
const API_BASE_URL = 'http://localhost:8080';

// 프로젝트 전용 Axios 클라이언트를 생성한다.
const client = axios.create({
    baseURL: API_BASE_URL
});

// 서버 목록을 가져오는 API 함수
export const fatchServers = () => {
    // 지금은 실제 백엔드가 없다. 1초뒤에 mock.js의 데이터를 실제 API 응답처럼 포장해서 돌려준다.
    // return client.get('/api/v1/server/SERVICE01/info');
    return client.get('/api/v1/server');
    // new Promise(resolve => {
    //     setTimeout(() => {
    //         resolve({
    //             // 우리가 설계한 공통 응답 포멧과 동일한 구조를 만든다.
    //             data: {
    //                 code: 200,
    //                 message: 'success',
    //                 data: mockServers
    //             }
    //         });
    //     }, 1000); // 1초 뒤에 데이터를 돌려준다.
    // }
}

// RealTimeMetrics 가져오는 함수
export const fatchRealTimeMetrics = (serviceId) => {
    return client.get(`/api/v1/server/${serviceId}/metrics/realtime`);
};

export default client;