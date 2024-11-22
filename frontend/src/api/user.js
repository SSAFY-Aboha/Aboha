import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

// 로그인 상태 확인
const checkLogin = async () => {
  try {
    const response = await axios.get('/api/v1/auth/check-status')
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 로그인
/**
 * @param { email : string, password : string } user
 * */
const login = async user => {
  try {
    const response = await axios.post('/api/v1/auth/login', user)
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 로그아웃
/**
 * @param { email : string, password : string } user
 * @header { Authorization : Bearer token }
 * */
const logout = async user => {
  try {
    const response = await axios.post('/api/v1/auth/logout', user)
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.log('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 회원가입
/**
 * @param { nickname : string, email : string, password : string } user */
/**
 *
 *비밀번호
 * ❗8자 이상 20자 이하 ❗
 * ❗영문자, 숫자, 특수문자 조합 ❗
 */
const signup = async user => {
  try {
    const response = await axios.post('/api/v1/users/signup', user)
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 닉네임 중복 확인
const checkNickname = async nickname => {
  try {
    const response = await axios.get('/api/v1/users/check-nickname', {
      params: nickname,
    })
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 이메일 중복 확인
const checkEmail = async email => {
  try {
    const response = await axios.get('/api/v1/users/check-email', {
      params: email,
    })
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 회원 정보 조회

// 회원 정보 수정
/**
 *
 * @body { email: String, nickname: String, password: String, profileImageUrl: String }
 */
const updateUserInfo = async (userId, formData) => {
  try {
    const response = await axios.put(`/api/v1/users/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    return { status: response.status, data: response.data, error: null }
  } catch (error) {
    console.error('API 호출 실패:', error)
    return {
      status: error.response.status,
      data: null,
      error: error.response?.data?.message || '알 수 없는 오류',
    }
  }
}

// 회원 탈퇴

export default {
  checkLogin,
  login,
  signup,
  logout,
  checkNickname,
  checkEmail,
  updateUserInfo,
}
