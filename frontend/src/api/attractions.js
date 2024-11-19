import { localAxios } from '@/utils/http-commons.js'

const axios = localAxios()

const getAttractions = async (searchData, success, fail) => {
  try {
    const response = await axios.get(`/api/v1/attractions`, {
      params: searchData,
    })
    if (response.status === 200) {
      success && success(response.data)
    } else {
      fail && fail(response.data)
    }
  } catch (error) {
    fail && fail(error)
  }
}

// 페이지네이션을 통한 관광지 조회
const getAttractionsPageNaition = async (page, size, success, fail) => {
  try {
    const response = await axios.get(`/api/v1/attractions`, {
      params: { page, size },
    })
    if (response.status === 200) {
      success && success(response.data)
      return response.data
    } else {
      return response.data
    }
  } catch (error) {
    fail && fail(error)
  }
}

// 관광지 상세 조회
const getAttractionDetail = async (attractionId, success, fail) => {
  const response = await axios.get(`/api/v1/attractions/${attractionId}`)
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail()
    return response
  }
}

// 관광지 검색 by keyword
const getAttractionsByKeyword = async (keyword, success, fail) => {
  const response = await axios.get(`/api/v1/attractions`, {
    params: { keyword },
  })
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail()
    return response
  }
}

// 관광지 검색 by address
const getAttractionsByAddress = async (
  { sidoCode, gugunCode },
  success,
  fail,
) => {
  const response = await axios.get(`/api/v1/attractions`, {
    params: { sidoCode, gugunCode },
  })
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail()
    return response
  }
}

// 관광지 검색 by contentTypeId
const getAttractionsByContentTypeId = async (contentTypeId, success, fail) => {
  const response = await axios.get(`/api/v1/attractions`, {
    params: { contentTypeId },
  })
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail()
    return response
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

export default {
  getContentType,
  getAttractionsPageNaition,
  getSido,
  getGugun,
  getAttractions,
  getAttractionDetail,
  getAttractionsByKeyword,
  getAttractionsByAddress,
  getAttractionsByContentTypeId,
}
