import axios from 'axios';

// Tạo một instance của axios
const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1', // Trỏ thẳng vào Backend Spring Boot
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // Hỗ trợ CORS tốt hơn nếu có cookie
});

// Interceptor Request: Tự động nhét JWT Token vào Header trước khi gửi đi
api.interceptors.request.use(
  (config) => {
    // Trong môi trường Next.js client-side, ta lấy token từ localStorage
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('accessToken');
      if (token && config.headers) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Interceptor Response: Xử lý lỗi toàn cục (Ví dụ token hết hạn)
api.interceptors.response.use(
  (response) => response.data, // Chỉ lấy data, bỏ qua config/headers của axios
  (error) => {
    if (error.response?.status === 401) {
      // Bị lỗi 401 (Unauthorized) -> Xóa token cũ -> Đẩy về trang đăng nhập
      if (typeof window !== 'undefined') {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('user');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default api;