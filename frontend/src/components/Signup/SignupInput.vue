<script setup>
import { computed, ref } from 'vue'
import Input from '../ui/input/Input.vue'

const props = defineProps({
  metaData: Object,
  error: String,
})
const inputValue = defineModel('inputValue')

const isInput = computed(() => {
  // inputValue 값을 직접 참조
  return inputValue.value[props.metaData.id] !== ''
})

const isValidate = computed(() => {
  return props.metaData.isValidate
})

const showPassword = ref(false)

const inputType = computed(() => {
  if (props.metaData.type !== 'password') return props.metaData.type
  return showPassword.value ? 'text' : 'password'
})

const validationStatus = computed(() => {
  if (!inputValue.value[props.metaData.id]) return null
  return isValidate.value ? 'success' : 'error'
})
</script>

<template>
  <div class="relative flex flex-col w-full gap-5">
    <div class="floating-input-container">
      <Input
        :type="inputType"
        :id="metaData.id"
        :class="[
          'floating-input focus-visible:ring-0',
          {
            'border-green-500': validationStatus === 'success',
            'border-red-500': validationStatus === 'error',
          },
        ]"
        placeholder=" "
        v-model="inputValue[metaData.id]"
      />

      <!-- 비밀번호 토글 버튼 -->
      <button
        v-if="metaData.type === 'password'"
        type="button"
        class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700"
        @click="showPassword = !showPassword"
      >
        <i :class="showPassword ? 'pi pi-eye-slash' : 'pi pi-eye'"></i>
      </button>

      <label :for="metaData.id" class="floating-label">
        {{ metaData.text }} <span class="text-red-300">*</span>
      </label>
    </div>

    <!-- 유효성 검사 상태 표시 개선 -->
    <div class="absolute text-xs left-3 top-12">
      <p v-if="error" class="flex items-center gap-1 text-red-500">
        <i class="pi pi-times-circle"></i>
        {{ error }}
      </p>
      <p
        v-else-if="inputValue[props.metaData.id]"
        :class="{
          'text-red-500': !isValidate,
          'text-green-500 flex items-center gap-1': isValidate,
        }"
      >
        <i
          :class="isValidate ? 'pi pi-check-circle' : 'pi pi-times-circle'"
        ></i>
        {{ !isValidate ? metaData.errorMsg : '사용 가능합니다.' }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.floating-input-container {
  position: relative;
}

.floating-input {
  width: 100%;
  padding: 0.75rem;
  background: transparent;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  appearance: none;
}

.floating-input:focus {
  outline: none;
  border-color: #84ce99;
}

.floating-label {
  position: absolute;
  left: 0.5rem;
  padding: 0 0.5rem;
  background: white;
  color: #6b7280;
  font-size: 0.875rem;
  transition: all 0.3s ease;
  pointer-events: none;

  /* 기본 위치 (input이 비어있을 때) */
  top: 50%;
  transform: translateY(-50%);
}

/* input이 focus되거나 값이 있을 때 label 위치 */
.floating-input:focus ~ .floating-label,
.floating-input:not(:placeholder-shown) ~ .floating-label {
  top: 0;
  transform: translateY(-50%) scale(0.85);
  color: #6b7280;
}

/* input이 focus될 때 label 스타일 */
.floating-input:focus ~ .floating-label {
  color: #84ce99;
}

/* 유효성 검사 오류시  */
.input-error {
  border-color: red;
}

/* 트랜지션 효과 추가 */
.floating-input {
  transition: all 0.2s ease-in-out;
}

/* 유효성 검사 아이콘 애니메이션 */
.pi {
  transition: all 0.2s ease-in-out;
}
</style>
