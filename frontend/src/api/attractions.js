import { localAxios } from '@/utils/http-commons.js'

const axios = localAxios()

// 관광지 조회
/**
 * @param {Object} searchData - 검색 조건
 * @param {Function} success - 성공 콜백 함수
 * @param {Function} fail - 실패 콜백 함수
 */
const getAttractions = async (searchData, success, fail) => {
  try {
    const response = await axios.get(`/api/v1/attractions`, {
      params: searchData,
    })
    if (response.status === 200) {
      success && success(response.data)
      return response.data
    } else {
      fail && fail(response)
      return response
    }
  } catch (error) {
    fail && fail(error)
    return error
  }
}

// 관광지 상세 조회
const getAttractionDetail = async (attractionId, success, fail) => {
  const response = await axios.get(`/api/v1/attractions/${attractionId}`)
  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail && fail(response.data)
    return response.data
  }
}

// 시도 조회
const getSido = async (success, fail) => {
  const response = await axios.get(`/api/v1/sidos`)
  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail()
    return response
  }
}

// 구군 조회
const getGugun = async (sidoCode, success, fail) => {
  const response = await axios.get(`/api/v1/guguns`, { params: { sidoCode } })
  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail()
    return response
  }
}

// 관광지 카테고리 조회
const getContentType = async (success, fail) => {
  const response = await axios.get(`/api/v1/contenttypes`)
  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail()
    return response
  }
}

// 관광지 추천 조회
const getSuggestAttraction = async (pickedData, success, fail) => {
  const response = await axios.post(`/api/v1/recommendations`, pickedData)

  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail(response)
    return response
  }
}

const getAttractionReview = async (attractionId, success, fail) => {
  const response = await axios.get(`/api/v1/reviews/${attractionId}`)
  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail()
    return response
  }
}

// 관광지 후기 작성
const postAttractionReview = async (reviewData, success, fail) => {
  const response = await axios.post(`/api/v1/reviews`, reviewData)
  if (response.status === 201) {
    success && success(response.data)
    return response.data
  } else {
    fail()
    return response
  }
}

// 관광지 좋아요 토글
const toggleAttractionLike = async (attractionId, success, fail) => {
  const response = await axios.post(
    `/api/v1/attractions/${attractionId}/like`,
    null,
  )
  if (response.status === 200) {
    success && success(response)
    return response
  } else {
    fail(response)
    return response
  }
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
}
