import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

export const fetchApi = async (method, url, data, options = {}) => {
  try {
    const response = await axios[method](url, data, options)
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response?.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}
