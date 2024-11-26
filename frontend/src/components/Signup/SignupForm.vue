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
    validator: async value => {
      const { isAvailable } = await userStore.checkNickname(value)
      return isAvailable
    },
    customErrorMsg: '이미 존재하는 닉네임 입니다.',
  },
  {
    id: 'email',
    text: 'Email',
    type: 'email',
    errorMsg: '이미 존재하는 이메일 입니다.',
    isValidate: false,
    validator: async value => {
      try {
        const emailRegex =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
        // 이메일 형식 검증
        if (emailRegex.test(value)) {
          const { isAvailable } = await userStore.checkEmail(value)
          if (!isAvailable) {
            console.log('이미 존재하는 이메일 입니다.')
            return {
              isValid: false,
              errorMsg: '이미 존재하는 이메일 입니다.',
            }
          }
          return {
            isValid: true,
          }
        }
        // 이메일 형식 검증 실패
        return {
          isValid: false,
          errorMsg: '올바른 이메일 형식이 아닙니다.',
        }
      } catch (error) {
        console.error('이메일 검증 중 오류 발생:', error)
        return {
          isValid: false,
          errorMsg: '이메일 검증 중 오류가 발생했습니다.',
        }
      }
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
      if (
        value.length >= 8 &&
        /[A-Za-z]/.test(value) &&
        /[0-9]/.test(value) &&
        /[!@#$%^&*(),.?":{}|<>]/.test(value)
      ) {
        return true
      }
      return false
    },
    customErrorMsg:
      '비밀번호는 8자 이상이며, 영문, 숫자, 특수문자를 모두 포함해야 합니다.',
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

const validateField = async (field, value) => {
  const fieldMeta = metaData.value.find(m => m.id === field)
  if (!fieldMeta || !fieldMeta.validator) return true

  if (!value) {
    delete errors.value[field]
    fieldMeta.isValidate = false
    return false
  }

  try {
    const result = await fieldMeta.validator(value, inputValue.value)
    const isValid = result.isValid ?? result // 기존 validator들과의 호환성 유지

    if (!isValid) {
      errors.value[field] = result.errorMsg || fieldMeta.customErrorMsg
    } else {
      delete errors.value[field]
    }
    fieldMeta.isValidate = isValid
    return isValid
  } catch (error) {
    console.error('Validation error:', error)
    return false
  }
}

const validateForm = async () => {
  let isValid = true
  for (const [field, value] of Object.entries(inputValue.value)) {
    const fieldValid = await validateField(field, value)
    if (!fieldValid) {
      isValid = false
    }
  }
  isFormValid.value = isValid
  return isValid
}

watch(
  inputValue,
  async (newValue, oldValue) => {
    for (const field of Object.keys(newValue)) {
      if (newValue[field] !== oldValue[field]) {
        await validateField(field, newValue[field])
      }
    }
    await validateForm()
  },
  { deep: true },
)

const handleSubmit = async () => {
  if (await validateForm()) {
    isLoading.value = true
    try {
      await userStore.signup(inputValue.value)
      alert('회원가입이 완료되었습니다. 로그인을 해주세요.')
      router.push('/login')
    } catch (error) {
      console.error('Error during signup:', error)
      alert('회원가입에 실패하였습니다. 다시 시도해주세요.')
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
