<script setup>
import Button from '@/components/ui/button/Button.vue'
import Input from '@/components/ui/input/Input.vue'
import { ref } from 'vue'
import useUserStore from '@/stores/user'
import { useRouter } from 'vue-router'
const userStore = useUserStore()
const router = useRouter()

// 입력값
const inputValue = ref({ email: '', password: '', save: false })

const handleLogin = async () => {
  const { status, data } = await userStore.login(inputValue.value)

  if (status === 200) {
    router.push('/')
  } else if (status === 401) {
    alert('아이디 및 비밀번호를 확인해주세요.')
  } else if (status === 400) {
    alert('이메일 형식을 확인해주세요.')
  }
}
</script>
<template>
  <form class="w-full" @submit.prevent="handleLogin">
    <div class="flex flex-col flex-1 gap-4">
      <div class="flex flex-col gap-4">
        <Input
          class="bg-gray-100"
          type="email"
          placeholder="Email"
          @input="e => (inputValue.email = e.target.value)"
        />
        <Input
          class="bg-gray-100"
          type="password"
          placeholder="Password"
          @input="e => (inputValue.password = e.target.value)"
        />
      </div>
      <div class="flex justify-between w-full">
        <div class="flex items-center">
          <input class="" type="checkbox" id="save" @input="inputValue.save" />
          <label class="px-2 text-xs" for="save">이메일 저장</label>
        </div>
        <router-link to="#" class="text-sm hover:underline"
          >비빌번호을 잊으셨나요?</router-link
        >
      </div>
      <div class="">
        <Button type="submit" class="w-full">로그인</Button>
      </div>
    </div>
  </form>
</template>

<style lang="scss" scoped></style>
