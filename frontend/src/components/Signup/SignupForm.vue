<script setup>
import SignupInput from '@/components/Signup/SignupInput.vue'
import Button from '@/components/ui/button/Button.vue'
import { ref, watch } from 'vue'

const metaData = ref([
  {
    id: 'nickname',
    text: 'nickname',
    type: 'text',
    errorMsg: '이미 존재하는 닉네임 입니다.',
    isValidate: false,
    validator: value => {
      return value.length >= 2 && value.length <= 10
    },
    customErrorMsg: '닉네임은 2-10자 사이여야 합니다.',
  },
  {
    id: 'email',
    text: 'Email',
    type: 'text',
    errorMsg: '이미 존재하는 이메일 입니다.',
    isValidate: false,
    validator: value => {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      return emailRegex.test(value)
    },
    customErrorMsg: '올바른 이메일 형식이 아닙니다.',
  },
  {
    id: 'password',
    text: 'password',
    type: 'password',
    errorMsg: '비밀번호가 일치하지 않습니다.',
    isValidate: false,
    validator: value => {
      return value.length >= 8 && /[A-Za-z]/.test(value) && /[0-9]/.test(value)
    },
    customErrorMsg: '비밀번호는 8자 이상, 영문과 숫자를 포함해야 합니다.',
  },
  {
    id: 'password-check',
    text: 'password check',
    type: 'password',
    errorMsg: '비밀번호가 일치하지 않습니다.',
    isValidate: false,
    validator: (value, allValues) => {
      return value === allValues.password
    },
    customErrorMsg: '비밀번호가 일치하지 않습니다.',
  },
])

const inputValue = ref({
  nickname: '',
  email: '',
  password: '',
  'password-check': '',
})
const errors = ref({})
const isFormValid = ref(false)

const validateField = (field, value) => {
  const fieldMeta = metaData.value.find(m => m.id === field)
  if (!fieldMeta || !fieldMeta.validator) return true

  const isValid = fieldMeta.validator(value, inputValue.value)
  if (!isValid) {
    errors.value[field] = fieldMeta.customErrorMsg
  } else {
    delete errors.value[field]
  }

  fieldMeta.isValidate = isValid
  return isValid
}

const validateForm = () => {
  let isValid = true
  Object.entries(inputValue.value).forEach(([field, value]) => {
    if (!validateField(field, value)) {
      isValid = false
    }
  })
  isFormValid.value = isValid
  return isValid
}

watch(
  inputValue,
  () => {
    validateForm()
  },
  { deep: true },
)

const handleSubmit = () => {
  if (validateForm()) {
    console.log('Form is valid:', inputValue.value)
    // API 호출 등 추가 로직
  } else {
    console.log('Form is invalid:', errors.value)
  }
}
</script>

<template>
  <div
    @click="$router.push('/')"
    class="absolute text-sm text-left text-gray-500 cursor-pointer top-20 left-20 pb-7 hover:text-black"
  >
    <i class="pi pi-home"></i>
    <span class="ml-2">홈으로 돌아가기</span>
  </div>
  <h1 class="mb-4 text-3xl font-bold">Create account</h1>
  <form @submit.prevent="handleSubmit" class="flex flex-col w-full gap-12">
    <SignupInput
      v-for="data in metaData"
      :key="data.id"
      :metaData="data"
      v-model:inputValue="inputValue"
      :error="errors[data.id]"
    />
    <Button type="submit" class="w-full" :disabled="!isFormValid">
      회원가입
    </Button>
  </form>
</template>

<style scoped></style>
