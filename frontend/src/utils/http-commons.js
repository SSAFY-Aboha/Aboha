import axios from 'axios'

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
  })
  return instance
}

export { localAxios }
