<script setup>
import { Button } from '@/components/ui/button'
import Input from '@/components/ui/input/Input.vue'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed, nextTick, ref, watch } from 'vue'
import userAPI from '@/api/user'
import { isPasswordValid } from '@/utils/isPasswordValid'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)
const router = useRouter()

// const userUpdateData = ref(userInfo.value)
const userUpdateData = ref({
  email: userInfo.value.email || '이메일',
  nickname: userInfo.value.nickname || '닉네임',
  password: null,
  passwordCheck: '',
  profileImageUrl: userInfo.value.profileImageUrl,
})

const isPasswordValidState = computed(() => {
  const password = userUpdateData.value.password
  if (!password) return false

  return isPasswordValid(password)
})

// 비밀번호 일치 여부
const isPasswordCheck = computed(
  () => userUpdateData.value.password !== userUpdateData.value.passwordCheck,
)
const isDuplicationNickname = ref(false)

// 닉네임 중복 확인
watch(
  () => userUpdateData.value.nickname,
  async newNickname => {
    // 빈 값이거나 현재 닉네임과 동일한 경우 API 호출하지 않음
    if (newNickname.length < 2 || newNickname === userInfo.value.nickname) {
      console.log('닉네임 중복 확인 중복 아님')
      isDuplicationNickname.value = false
      return
    }

    const { data, status, error } = await userAPI.checkNickname(newNickname)
    console.log(status, data, error)
    nextTick(() => {
      isDuplicationNickname.value = data.isAvailable
    })
  },
  {
    immediate: true,
    flush: 'post',
    debounce: 300,
  },
)

// 프로필 이미지 변경
const handleProfileImageChange = e => {
  const fileURL = URL.createObjectURL(e.target.files?.[0])
  fileURL && (userUpdateData.value.profileImageUrl = fileURL)

  console.log('프로필 이미지 변경')
}

const handleSave = async () => {
  if (confirm('저장하시겠습니까?')) {
    // 저장 api 호출

    const formData = new FormData()
    formData.append('profileImageUrl', userUpdateData.value.profileImageUrl)
    formData.append('nickname', userUpdateData.value.nickname)
    formData.append('password', userUpdateData.value.password)

    const { error } = await userAPI.updateUserInfo(formData)
    if (error) {
      alert(error)
      return
    }
    userInfo.value = { ...userInfo.value, ...userUpdateData.value }
    alert('저장되었습니다.')
    router.push({ name: 'mypage' })
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
              userUpdateData.profileImageUrl ||
              '/src/assets/default_profile.png'
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
          :default-value="userUpdateData.email || '이메일'"
          disabled
        />
      </section>
      <!-- 닉네임 수정 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">닉네임</h1>
        <div class="relative w-2/4 text-center">
          <span
            v-if="
              userUpdateData.nickname?.trim() !== userInfo.nickname?.trim() &&
              userUpdateData.nickname.length > 1
            "
            :class="`absolute text-sm ${isDuplicationNickname ? 'text-green-500' : 'text-red-500'}   left-2 -top-6`"
            >{{
              isDuplicationNickname
                ? '사용 가능합니다!'
                : '이미 존재하는 닉네임입니다.'
            }}
          </span>
          <Input class="" v-model="userUpdateData.nickname" />
        </div>
      </section>
      <!-- 비밀번호 수정 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">비밀번호</h1>
        <div class="relative w-2/4 text-center">
          <span
            v-if="userUpdateData.password"
            :class="`absolute text-sm ${isPasswordValidState ? 'text-green-500' : 'text-red-500'}   left-2 -top-6`"
            >{{
              isPasswordValidState
                ? '사용 가능합니다!'
                : '특수문자 포함 8자 이상 입력해주세요.'
            }}</span
          >
          <Input class="" type="password" v-model="userUpdateData.password" />
        </div>
      </section>
      <!-- 비밀번호 확인 -->
      <section class="flex items-center justify-center w-full gap-x-10">
        <h1 class="text-xl font-bold text-right basis-1/6">비밀번호 확인</h1>
        <div class="relative w-2/4 text-center">
          <span
            v-if="
              isPasswordValid &&
              userUpdateData.password &&
              userUpdateData.passwordCheck
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
            v-model="userUpdateData.passwordCheck"
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
