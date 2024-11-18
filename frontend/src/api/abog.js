import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

// 아보그 조회
const getAbog = async (success, fail) => {
  const response = await axios.get('/api/v1/abogs')
  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 아보그 댓글 조회
const getAbogComments = async (abogId, success, fail) => {
  const response = await axios.get(`/api/v1/abogs/${abogId}/comments`)
  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 아보그 상세 조회
const getAbogDetail = async (abogId, success, fail) => {
  const response = await axios.get(`/api/v1/abogs/${abogId}`)
  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 아보그 추가
const addAbog = async (abog, success, fail) => {
  const response = await axios.post('/api/v1/abogs', abog)
  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 아보그 수정
const updateAbog = async (abog, success, fail) => {
  const response = await axios.put('/api/v1/abogs', abog)
  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 아보그 삭제
const deleteAbog = async (abog, success, fail) => {
  const response = await axios.delete(`/api/v1/abogs/${abog.id}`)
  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

export default {
  getAbogComments,
  getAbog,
  addAbog,
  updateAbog,
  deleteAbog,
  getAbogDetail,
}
