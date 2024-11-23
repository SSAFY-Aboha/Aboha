import { fetchApi } from '@/utils/api-util'

// 로그인 상태 확인
const checkLogin = () => {
  return fetchApi('get', '/api/v1/auth/check-status')
}

// 로그인
/**
 * @param { email : string, password : string } user
 * */
const login = user => {
  return fetchApi('post', '/api/v1/auth/login', user)
}

// 로그아웃
/**
 * @param { email : string, password : string } user
 * @header { Authorization : Bearer token }
 * */
const logout = user => {
  return fetchApi('post', '/api/v1/auth/logout', user)
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
  return fetchApi('post', '/api/v1/users/signup', user)
}

// 닉네임 중복 확인
const checkNickname = nickname => {
  return fetchApi('get', `/api/v1/users/check-nickname`, {
    params: { nickname },
  })
}

// 이메일 중복 확인
const checkEmail = email => {
  return fetchApi('get', '/api/v1/users/check-email', { params: email })
}

// 회원 정보 조회
const getUserInfo = userId => {
  return fetchApi('get', `/api/v1/users/${userId}`)
}

// 회원 정보 수정
/**
 *
 * @body { email: String, nickname: String, password: String, profileImageUrl: String }
 */
const updateUserInfo = async (userId, formData) => {
  return fetchApi('put', `/api/v1/users/${userId}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// 회원 탈퇴
const deleteUser = userId => {
  return fetchApi('delete', `/api/v1/users/${userId}`)
}

export default {
  checkLogin,
  login,
  signup,
  logout,
  checkNickname,
  checkEmail,
  updateUserInfo,
  getUserInfo,
  deleteUser,
}
