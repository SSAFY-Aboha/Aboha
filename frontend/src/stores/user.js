import { defineStore } from 'pinia'
import userAPI from '@/api/user'
import { ref } from 'vue'

const useUserStore = defineStore('user', () => {
  // ? state
  // 로그인 상태
  const isLogin = ref(false)

  // ? computed

  // ? action
  // 로그인
  const login = async () => {
    const response = await userAPI.login()
  }

  // 로그아웃
  const logout = async () => {}

  // 회원가입
  const signup = async () => {}

  // 닉네임 중복확인
  const checkNickname = async () => {}

  // 이메일 중복확인
  const checkEmail = async () => {}

  // 회원정보 조회
  // 회원정보 수정
  // 회원탈퇴
})

export default useUserStore
