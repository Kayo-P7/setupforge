import { create } from 'axios';

const api = create({
    baseURL: 'https://setupforge-backend.onrender.com',
    timeout: 15000,
});

export default api;