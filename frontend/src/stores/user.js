import { defineStore } from 'pinia'
import userAPI from '@/api/user'
import { ref } from 'vue'

const useUserStore = defineStore('user', () => {
  // ? state
  // 로그인 상태
  const isLogin = ref(false)
  const userInfo = ref({})

  // ? computed

  // ? action
  // 초기화 함수 - 쿠키에서 JSESSIONID 확인
  const initializeAuth = () => {
    userAPI.checkLogin().then(response => {
      if (response.status === 200) {
        console.log('in store', response)
        isLogin.value = true
        userInfo.value = response.data
      }
    })
  }

  // 로그인
  const login = async (user, success, fail) => {
    const response = await userAPI.login(
      user,
      () => {
        // 세션 저장
        isLogin.value = true
        success()
      },
      fail,
    )
    console.log('in store', response)
  }

  // 로그아웃
  const logout = async () => {}

  // 회원가입
  const signup = async () => {}

  // 닉네임 중복확인
  const checkNickname = async (nickname, success, fail) => {
    const response = await userAPI.checkNickname(nickname, success, fail)
    console.log('in store', response)
  }

  // 이메일 중복확인
  const checkEmail = async () => {}

  // 회원정보 조회
  // 회원정보 수정
  // 회원탈퇴

  return {
    initializeAuth,
    isLogin,
    login,
    logout,
    signup,
    checkNickname,
    checkEmail,
  }
})

export default useUserStore
