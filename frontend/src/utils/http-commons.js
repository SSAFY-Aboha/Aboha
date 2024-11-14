import axios from 'axios'

// const { VITE_API_BASE_URL } = import.meta.env

// local vue api axios instance
const localAxios = () => {
  const instance = axios.create({
    // baseURL: url,
    baseURL: 'http://localhost:8080',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  })
  return instance
}

export { localAxios }
