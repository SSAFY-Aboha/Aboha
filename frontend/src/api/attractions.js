import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

// 페이지네이션을 통한 관광지 조회
const getAttractions = async (page, limit, success, fail) => {
  try {
    const response = await axios.get(
      `/api/v1/attractions?page=${page}&limit=${limit}`,
    )
    if (response.status === 200) {
      success && success(response.data)
    } else {
      fail && fail(response)
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
  const response = await axios.get(`/api/v1/attractions?keyword=${keyword}`)
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail()
    return response
  }
}

// 관광지 검색 by address
const getAttractionsByAddress = async (address, success, fail) => {
  const response = await axios.get(`/api/v1/attractions?address=${address}`)
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
  const response = await axios.get(
    `/api/v1/attractions?contentTypeId=${contentTypeId}`,
  )
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail()
    return response
  }
}

export default {
  getAttractions,
  getAttractionDetail,
  getAttractionsByKeyword,
  getAttractionsByAddress,
  getAttractionsByContentTypeId,
}
