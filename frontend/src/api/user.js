import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

// 로그인
/**
 * @param { email : string, password : string } user
 * */
const login = async user => {
  const response = await axios.get('/api/v1/auth/login', user)
  return response.data
}

// 로그아웃
/**
 * @param { email : string, password : string } user
 * @header { Authorization : Bearer token }
 * */
const logout = async user => {
  const response = await axios.post('/api/v1/auth/logout', user)
  return response.data
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
  const response = await axios.post('/api/v1/users/signup', user)
  return response.data
}

// 닉네임 중복 확인
const checkNickname = async nickname => {
  const response = await axios.get('/api/v1/users/check-nickname', nickname)
  return response.data
}

// 이메일 중복 확인
const checkEmail = async email => {
  const response = await axios.get('/api/v1/users/check-email', email)
  return response.data
}

// 회원 정보 조회

// 회원 정보 수정

// 회원 탈퇴

export { login, signup, logout, checkNickname, checkEmail }
