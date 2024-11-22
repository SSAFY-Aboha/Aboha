import { fetchApi } from '@/utils/api-util'

// 아보그 조회
const getAbog = () => {
  return fetchApi('get', '/api/v1/abogs')
}

// 아보그 상세 조회
const getAbogDetail = abogId => {
  return fetchApi('get', `/api/v1/abogs/${abogId}`)
}

// 아보그 추가
const createAbog = abog => {
  return fetchApi('post', '/api/v1/abogs', abog, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// 아보그 수정
const updateAbog = abog => {
  return fetchApi('put', '/api/v1/abogs', abog)
}

// 아보그 삭제
const deleteAbog = abog => {
  return fetchApi('delete', `/api/v1/abogs/${abog.id}`)
}

// 아보그 좋아요 토글
const toggleAbogLike = abogId => {
  return fetchApi('post', `/api/v1/abogs/${abogId}/like`)
}

// 아보그 댓글 조회
const getAbogComments = abogId => {
  return fetchApi('get', `/api/v1/abogs/${abogId}/comments`)
}

// 아보그 댓글 추가
const addAbogComment = (abogId, comment) => {
  return fetchApi('post', `/api/v1/abogs/${abogId}/comments`, comment)
}

// 아보그 댓글 삭제
const deleteAbogComment = (abogId, commentId) => {
  return fetchApi('delete', `/api/v1/abogs/${abogId}/comments/${commentId}`)
}

export default {
  getAbogComments,
  getAbog,
  createAbog,
  updateAbog,
  deleteAbog,
  getAbogDetail,
  toggleAbogLike,
  addAbogComment,
  deleteAbogComment,
}
