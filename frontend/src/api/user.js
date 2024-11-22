import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

// 로그인 상태 확인
const checkLogin = async (success, fail) => {
  try {
    const response = await axios.get('/api/v1/auth/check-status')
    if (response.status === 200) {
      success && success()
      return response
    }
  } catch (error) {
    fail && fail()
    return error
  }
}

// 로그인
/**
 * @param { email : string, password : string } user
 * */
const login = async (user, success, fail) => {
  const response = await axios.post('/api/v1/auth/login', user)

  if (response.status === 200) {
    success && success(response.data)
    return response.data
  } else {
    fail && fail(response)
    return response
  }
}

// 로그아웃
/**
 * @param { email : string, password : string } user
 * @header { Authorization : Bearer token }
 * */
const logout = async (user, success, fail) => {
  const response = await axios.post('/api/v1/auth/logout', user)

  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail && fail()
    return response
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
const signup = async (user, success, fail) => {
  const response = await axios.post('/api/v1/users/signup', user)
  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail && fail()
    return response
  }
}

// 닉네임 중복 확인
const checkNickname = async (nickname, success, fail) => {
  const response = await axios.get('/api/v1/users/check-nickname', nickname)

  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail && fail()
    return response
  }
}

// 이메일 중복 확인
const checkEmail = async (email, success, fail) => {
  const response = await axios.get('/api/v1/users/check-email', email)

  if (response.status === 200) {
    success && success()
    return response
  } else {
    fail && fail()
    return response
  }
}

// 회원 정보 조회

// 회원 정보 수정
/**
 *
 * @body { email: String, nickname: String, password: String, profileImageUrl: String }
 */
const updateUserInfo = async (userId, formData, success, fail) => {
  try {
    const response = await axios.put(`/api/v1/users/${userId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    // 성공 :200
    if (response.status === 200) {
      success && success(response.data)
      return response.data
    } else {
      fail && fail(response.data)
      return response.data
    }
  } catch (error) {
    return error
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
