import axios from 'axios';

// Create an Axios instance pointing to our Spring Boot Backend
const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Axios Interceptor to automatically add the JWT token to requests
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        // Authentication requests must never carry a previous/stale JWT.
        if (token && !config.url?.startsWith('/auth/')) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export const AuthService = {
    login: (credentials, accountType) => api.post('/auth/authenticate', { ...credentials, accountType }),
    register: (userData) => api.post('/auth/register', userData)
};

export const StudentService = {
    getMyProfile: () => api.get('/students/me'),
    getAllStudents: (params) => api.get('/students', { params }),
    getStudentById: (id) => api.get(`/students/${id}`),
    createStudent: (student) => api.post('/students', student),
    updateStudent: (id, student) => api.put(`/students/${id}`, student),
    deleteStudent: (id) => api.delete(`/students/${id}`)
};

export const DashboardService = {
    getStats: () => api.get('/dashboard')
};

export default api;
