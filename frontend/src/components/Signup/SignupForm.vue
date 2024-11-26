<script setup>
import SignupInput from '@/components/Signup/SignupInput.vue'
import Button from '@/components/ui/button/Button.vue'
import { ref, watch } from 'vue'
import useUserStore from '@/stores/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()

const metaData = ref([
  {
    id: 'nickname',
    text: 'nickname',
    type: 'text',
    errorMsg: '이미 존재하는 닉네임 입니다.',
    isValidate: false,
    validator: value => {
      return value.length >= 2
    },
    customErrorMsg: '닉네임은 2-10자 사이여야 합니다.',
  },
  {
    id: 'email',
    text: 'Email',
    type: 'text',
    errorMsg: '이미 존재하는 이메일 입니다.',
    isValidate: false,
    validator: async value => {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      if (emailRegex.test(value)) {
        // const { isAvailable } = await userStore.checkEmail(value)
        // return isAvailable
        return true
      }
      return false
    },
    customErrorMsg: '올바른 이메일 형식이 아닙니다.',
  },
  {
    id: 'password',
    text: 'password',
    type: 'password',
    errorMsg: '비밀번호가 일치하지 않습니다.',
    isValidate: false,
    validator: async value => {
      if (value.length >= 8 && /[A-Za-z]/.test(value) && /[0-9]/.test(value)) {
        // const { isAvailable } = await userStore.checkNickname(value)
        // return isAvailable
        return true
      }
      return false
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
const isLoading = ref(false)

const validateField = (field, value) => {
  const fieldMeta = metaData.value.find(m => m.id === field)
  if (!fieldMeta || !fieldMeta.validator) return true

  if (!value) {
    delete errors.value[field]
    fieldMeta.isValidate = false
    return false
  }

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
  (newValue, oldValue) => {
    Object.keys(newValue).forEach(field => {
      if (newValue[field] !== oldValue[field]) {
        validateField(field, newValue[field])
      }
    })
    validateForm()
  },
  { deep: true },
)

const handleSubmit = async () => {
  if (validateForm()) {
    isLoading.value = true
    try {
      await userStore.signup(inputValue.value)
      // 성공 메시지 표시
    } catch (error) {
      // 에러 메시지 표시
    } finally {
      isLoading.value = false
    }
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
    <Button type="submit" class="w-full" :disabled="!isFormValid || isLoading">
      <span v-if="isLoading" class="flex items-center justify-center gap-2">
        <i class="pi pi-spinner animate-spin"></i>
        처리중...
      </span>
      <span v-else>회원가입</span>
    </Button>
  </form>
</template>

<style scoped></style>
