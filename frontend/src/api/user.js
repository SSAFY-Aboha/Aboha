import { localAxios } from '@/utils/http-commons'

const axios = localAxios()

// 로그인
/**
 * @param { email : string, password : string } user
 * */
const login = async (user, success, fail) => {
  const response = await axios.post('/api/v1/auth/login', user)

  if (response.status === 200) {
    success()
    return response.data
  } else {
    fail()
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
    success()
    return response
  } else {
    fail()
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
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 닉네임 중복 확인
const checkNickname = async (nickname, success, fail) => {
  const response = await axios.get('/api/v1/users/check-nickname', nickname)

  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 이메일 중복 확인
const checkEmail = async (email, success, fail) => {
  const response = await axios.get('/api/v1/users/check-email', email)

  if (response.status === 200) {
    success()
    return response
  } else {
    fail()
    return response
  }
}

// 회원 정보 조회

// 회원 정보 수정

// 회원 탈퇴

export default { login, signup, logout, checkNickname, checkEmail }
