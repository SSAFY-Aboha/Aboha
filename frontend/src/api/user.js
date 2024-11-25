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

// 사용자가 좋아요한 관광지 목록 조회
const getUserLikes = () => {
  return fetchApi('get', `/api/v1/users/likes`)
}

// 사용자가 남긴 리뷰 목록 조회
const getUserReviews = () => {
  return fetchApi('get', `/api/v1/users/reviews`)
}

// 사용자의 아보그 목록 조회
const getUserAbogs = () => {
  return fetchApi('get', `/api/v1/users/abogs`)
}

// 사용자 정보 조회
const getUserInfo = () => {
  return fetchApi('get', `/api/v1/users/me`)
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
  getUserLikes,
  getUserReviews,
  getUserAbogs,
}
