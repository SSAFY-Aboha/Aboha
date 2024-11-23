import { defineStore } from 'pinia'
import userAPI from '@/api/user'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

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
    userInfo.value = data
  }

  // 로그인
  const login = async user => {
    const { status, data, error } = await userAPI.login(user)

    if (error) {
      alert(error)
    } else if (status === 200) {
      isAuthenticated.value = true
      userInfo.value = data
      return { status, data }
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
      alert(error)
    } else if (status === 200) {
      return data
    }
  }

  // 이메일 중복확인
  const checkEmail = async email => {
    const { status, data, error } = await userAPI.checkEmail(email)

    if (error) {
      alert(error)
    } else if (status === 200) {
      return data
    }
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
