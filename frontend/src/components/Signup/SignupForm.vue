<script setup>
import SignupInput from '@/components/Signup/SignupInput.vue'
import Button from '@/components/ui/button/Button.vue'
import { ref, watch } from 'vue'

const metaData = ref([
  { id: 'nickname', text: 'nickname', type: 'text' },
  { id: 'email', text: 'Email', type: 'text' },
  { id: 'password', text: 'password', type: 'password' },
  { id: 'password-check', text: 'password check', type: 'text' },
])

// 입력값
const inputValue = ref({ nickname: '', email: '', password: '' })

// 회원가입 버튼 클릭 시
const handleSubmit = () => {
  console.log(inputValue.value)
}

watch(inputValue, newVal => {
  console.log(newVal)
})
</script>
<template>
  <h1 class="mb-4 text-3xl font-bold">Create account</h1>
  <form @submit.prevent="handleSubmit" class="flex flex-col w-full gap-4">
    <SignupInput
      v-for="data in metaData"
      :metaData="data"
      v-model:inputValue="inputValue"
      :key="data.id"
    />
    <Button type="submit" class="w-full">회원가입</Button>
  </form>
</template>

<style scoped></style>
