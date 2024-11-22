import { fetchApi } from '@/utils/api-util'
// 관광지 조회
/**
 * @param {Object} searchData - 검색 조건
 * @param {Function} success - 성공 콜백 함수
 * @param {Function} fail - 실패 콜백 함수
 */
const getAttractions = searchData => {
  return fetchApi('get', '/api/v1/attractions', { params: searchData })
}

// 관광지 이름 조회
const getAttractionName = keyword => {
  return fetchApi('get', '/api/v1/attractions/search', { params: keyword })
}

// 관광지 상세 조회
const getAttractionDetail = attractionId => {
  return fetchApi('get', `/api/v1/attractions/${attractionId}`)
}

// 시도 조회
const getSido = () => {
  return fetchApi('get', '/api/v1/sidos')
}

// 구군 조회
const getGugun = sidoCode => {
  return fetchApi('get', '/api/v1/guguns', { params: { sidoCode } })
}

// 관광지 카테고리 조회
const getContentType = () => {
  return fetchApi('get', '/api/v1/contenttypes')
}

// 관광지 추천 조회
const getSuggestAttraction = pickedData => {
  return fetchApi('post', '/api/v1/recommendations', pickedData)
}

// 관광지 후기 조회
const getAttractionReview = attractionId => {
  return fetchApi('get', `/api/v1/reviews/${attractionId}`)
}

// 관광지 후기 작성
const postAttractionReview = reviewData => {
  return fetchApi('post', '/api/v1/reviews', reviewData)
}

// 관광지 좋아요 토글
const toggleAttractionLike = attractionId => {
  return fetchApi('post', `/api/v1/attractions/${attractionId}/like`, null)
}

export default {
  getContentType,
  getSido,
  getGugun,
  getAttractions,
  getAttractionDetail,
  getSuggestAttraction,
  postAttractionReview,
  toggleAttractionLike,
  getAttractionReview,
  getAttractionName,
}
