<script setup>
import { Button } from '@/components/ui/button'
import Input from '@/components/ui/input/Input.vue'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed, ref, watch } from 'vue'
import userAPI from '@/api/user'
import { isPasswordValid } from '@/utils/isPasswordValid'

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

// const userInfoData = ref(userInfo.value)
const userInfoData = ref({
  email: userInfo.value.email || '이메일',
  nickname: userInfo.value.nickname || '닉네임',
  password: null,
  passwordCheck: '',
  profileImageUrl: userInfo.value.profileImageUrl,
})

const isPasswordValidState = computed(() => {
  const password = userInfoData.value.password
  if (!password) return false

  return isPasswordValid(password)
})

// 비밀번호 일치 여부
const isPasswordCheck = computed(
  () => userInfoData.value.password !== userInfoData.value.passwordCheck,
)
const isDuplicationNickname = ref(false)

// 닉네임 중복 확인
watch(
  () => userInfoData.value.nickname,
  async newNickname => {
    // 닉네임이 변경되지 않았거나 현재 닉네임과 동일한 경우 중복 체크 불필요
    if (!newNickname || newNickname === userInfo.value.nickname) {
      isDuplicationNickname.value = false
      return
    }
    console.log(newNickname)

    // 닉네임 중복 확인
    try {
      const state = await userAPI.checkNickname(newNickname)
      isDuplicationNickname.value = state
    } catch (error) {
      console.error('닉네임 중복 확인 중 오류 발생:', error)
    }
  },
)

// 프로필 이미지 변경
const handleProfileImageChange = e => {
  const fileURL = URL.createObjectURL(e.target.files?.[0])
  fileURL && (userInfoData.value.profileImageUrl = fileURL)

  console.log('프로필 이미지 변경')
}

const handleSave = () => {
  if (confirm('저장하시겠습니까?')) {
    // 저장 api 호출

    const formData = new FormData()
    formData.append('profileImageUrl', userInfoData.value.profileImageUrl)
    formData.append('nickname', userInfoData.value.nickname)
    formData.append('password', userInfoData.value.password)

    userAPI.updateUserInfo(
      userInfo.value.id,
      formData,
      () => {
        alert('저장되었습니다.')
      },
      error => {
        console.error(error)
      },
    )
  }
}
</script>

<template>
  <div class="flex flex-col w-full h-full max-w-6xl mx-auto min-w-[900px]">
    <!-- header -->
    <header class="flex flex-col items-start justify-center py-5 gap-y-8">
      <div class="w-full px-8">
        <h1 class="text-3xl font-bold font-Namum">회원정보 수정하기</h1>
      </div>
    </header>
    <!-- content -->
    <div
      class="flex flex-col gap-y-12 w-full px-10 py-8 h-[calc(100vh-14rem)] bg-gray-50"
    >
      <!-- 프로필 이미지 -->
      <section class="flex flex-col items-center justify-center w-full gap-9">
        <div class="overflow-hidden rounded-full size-36">
          <img
            class="object-center"
            :src="
              userInfoData.profileImageUrl || '/src/assets/default_profile.png'
            "
            alt="logo"
          />
        </div>
        <label
          for="profileImage"
          class="px-3 py-2 border rounded-md shadow-sm cursor-pointer hover:bg-gray-100"
          >프로필 이미지 변경</label
        >
        <input
          id="profileImage"
          accept="image/*"
          @change="handleProfileImageChange"
          type="file"
          class="hidden"
        />
      </section>
      <!-- 이메일 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">이메일</h1>
        <Input
          class="w-2/4"
          :default-value="userInfoData.email || '이메일'"
          disabled
        />
      </section>
      <!-- 닉네임 수정 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">닉네임</h1>
        <div class="relative w-2/4 text-center">
          <span
            v-if="
              userInfoData.nickname !== userInfo.nickname &&
              userInfoData.nickname
            "
            :class="`absolute text-sm ${isDuplicationNickname ? 'text-red-500' : 'text-green-500'}   left-2 -top-6`"
            >{{
              isDuplicationNickname
                ? '이미 존재하는 닉네임입니다.'
                : '사용 가능합니다!'
            }}</span
          >
          <Input class="" v-model="userInfoData.nickname" />
        </div>
      </section>
      <!-- 비밀번호 수정 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">비밀번호</h1>
        <div class="relative w-2/4 text-center">
          <span
            v-if="userInfoData.password"
            :class="`absolute text-sm ${isPasswordValidState ? 'text-green-500' : 'text-red-500'}   left-2 -top-6`"
            >{{
              isPasswordValidState
                ? '사용 가능합니다!'
                : '특수문자 포함 8자 이상 입력해주세요.'
            }}</span
          >
          <Input
            class=""
            type="password"
            v-model.lazy="userInfoData.password"
          />
        </div>
      </section>
      <!-- 비밀번호 확인 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">비밀번호 확인</h1>
        <div class="relative w-2/4 text-center">
          <span
            v-if="
              isPasswordValid &&
              userInfoData.password &&
              userInfoData.passwordCheck
            "
            :class="`absolute text-sm ${isPasswordCheck ? 'text-red-500' : 'text-green-500'}   left-2 -top-6`"
            >{{
              isPasswordCheck
                ? '비밀번호가 일치하지 않습니다.'
                : '비밀번호가 일치합니다!'
            }}</span
          >
          <Input
            class=""
            type="password"
            v-model="userInfoData.passwordCheck"
          />
        </div>
      </section>
    </div>

    <!-- 저장 버튼 -->
    <div class="flex items-center justify-center w-full py-8 bg-gray-50">
      <Button variant="outline" @click="handleSave">저장하기</Button>
    </div>
  </div>
</template>

<style scoped></style>
