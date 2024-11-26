import { defineStore } from 'pinia'
import userAPI from '@/api/user'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import defaultProfile from '@/assets/default_profile.png'

const BASE_URL = import.meta.env.VITE_API_BASE_URL

const useUserStore = defineStore('user', () => {
  const router = useRouter()

  // ? state
  // 로그인 상태
  const isAuthenticated = ref(false)
  const userInfo = ref({
    nickname: '',
    email: '',
    save: false,
    profileImageUrl: null,
  })

  // ? computed

  // ? action
  // 초기화 함수 - 쿠키에서 JSESSIONID 확인
  const initializeAuth = async () => {
    const { status, data, error } = await userAPI.checkLogin()

    if (error) {
      isAuthenticated.value = false
      return
    }

    isAuthenticated.value = true

    userInfo.value = { ...userInfo.value, ...data }
    userInfo.value.profileImageUrl =
      data.profileImageUrl === null
        ? defaultProfile
        : `${BASE_URL}${data.profileImageUrl}`

    console.log('초기화', userInfo.value)
  }

  // 로그인
  const login = async user => {
    const { status, data, error } = await userAPI.login(user)

    if (error) {
      alert(error)
    } else if (status === 200) {
      router.push('/')
      isAuthenticated.value = true
      userInfo.value = data
      console.log('login data', data)
      //
      userInfo.value.profileImageUrl =
        data.profileImageUrl === null
          ? defaultProfile
          : `${BASE_URL}${data.profileImageUrl}`
    } else if (status === 401) {
      alert('아이디 및 비밀번호를 확인해주세요.')
    } else if (status === 400) {
      alert('이메일 형식을 확인해주세요.')
    }
  }

  // 로그아웃
  const logout = async () => {
    const { status, error } = await userAPI.logout()

    if (error) {
      alert(error)
    } else if (status === 200) {
      isAuthenticated.value = false
    }
  }

  // 회원가입
  const signup = async user => {
    const { status, data, error } = await userAPI.signup(user)

    if (error) {
      alert(error)
    } else if (status === 200) {
      alert('회원가입 성공! 로그인을 진행해주세요.')
      router.push('/login')
    }
  }

  // 닉네임 중복확인
  const checkNickname = async nickname => {
    const { status, data, error } = await userAPI.checkNickname(nickname)

    if (error) {
      return false
    }
    return data
  }

  // 이메일 중복확인
  const checkEmail = async email => {
    const { status, data, error } = await userAPI.checkEmail(email)

    if (error) {
      return false
    }
    return data
  }

  // 회원정보 조회
  // 회원정보 수정
  // 회원탈퇴

  return {
    initializeAuth,
    userInfo,
    isAuthenticated,
    login,
    logout,
    signup,
    checkNickname,
    checkEmail,
  }
})

export default useUserStore
