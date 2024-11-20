import axios from 'axios'
import { useRouter } from 'vue-router'
const router = useRouter()

// const { VITE_API_BASE_URL } = import.meta.env

// local vue api axios instance
const localAxios = () => {
  const instance = axios.create({
    // baseURL: url,
    baseURL: 'http://70.12.60.184:8080',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
    withCredentials: true,
    timeout: 10000,
  })

  // 요청 인터셉터 설정
  instance.interceptors.request.use(
    config => {
      // 예: 인증 토큰 추가
      const token = localStorage.getItem('authToken')
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`
      }

      // 요청 로깅
      console.log('Request 시작:', config)
      return config
    },
    error => {
      // 요청 에러 처리
      console.error('Request Error:', error)
      return Promise.reject(error)
    },

    // 응답 인터셉터: 데이터 추출 및 에러 처리
    // instance.interceptors.response.use(error => {
    //   if (error.response && error.response.status === 401) {
    //     console.error('Authentication required. Redirecting to login.')
    //     router.push('/login')
    //   }
    //   return Promise.reject(error)
    // }),
  )
  return instance
}

export { localAxios }
