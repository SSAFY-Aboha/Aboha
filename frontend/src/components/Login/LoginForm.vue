<script setup>
import Button from '@/components/ui/button/Button.vue'
import Input from '@/components/ui/input/Input.vue'
import { ref } from 'vue'

// 입력값
const inputValue = ref({ email: '', password: '', save: false })

const handleLogin = () => {
  console.log(inputValue.value)
}
</script>
<template>
  <form class="w-full" @submit.prevent="handleLogin">
    <div class="flex flex-col flex-1 gap-3">
      <div class="flex flex-col gap-4">
        <Input
          class="bg-gray-100"
          type="email"
          placeholder="Email"
          @input="inputValue.email"
        />
        <Input
          class="bg-gray-100"
          type="password"
          placeholder="Password"
          @input="inputValue.password"
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
