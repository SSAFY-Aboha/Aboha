<script setup>
import { ref, watch } from 'vue'
import SuggestLocation from './SuggestLocation.vue'
import { Button } from '../ui/button'
import SuggestContentType from './SuggestContentType.vue'
import SuggestAtmosphere from './SuggestAtmosphere.vue'

const step = defineModel('step')
const pickedData = defineModel('pickedData')

const isNextBtnVisible = ref(false)
const isComplete = ref(false)
const isCompleteSuggest = defineModel('isCompleteSuggest')

watch(
  () => pickedData.value.gugunCode,
  () => {
    isNextBtnVisible.value = true
  },
)

watch(
  () => pickedData.value.contentType,
  () => {
    isNextBtnVisible.value = true
  },
)

watch(
  () => pickedData.value.atmosphere,
  () => {
    isComplete.value = true
  },
)

// 다음 step으로 넘어가기
const handleNextBtn = () => {
  step.value += 1
  isNextBtnVisible.value = false
}

// 완료 버튼 클릭 시
const handleCompleteBtn = () => {
  console.log(pickedData.value)
  isCompleteSuggest.value = true
}

// 뒤로가기
const handleBackBtn = () => {
  step.value = step.value === 1 ? 1 : step.value - 1
}
</script>

<template>
  <div class="flex flex-col flex-1 gap-8 py-4">
    <SuggestLocation v-if="step === 1" v-model:pickedData="pickedData" />
    <SuggestContentType v-if="step === 2" v-model:pickedData="pickedData" />
    <SuggestAtmosphere v-if="step === 3" v-model:pickedData="pickedData" />
    <div class="flex justify-center w-full gap-4">
      <Button
        @click="handleBackBtn"
        variant="outline"
        class="w-1/4 text-lg text-white bg-green-300 rounded-full hover:text-white hover:bg-green-500"
        >뒤로가기</Button
      >
      <Transition name="fade">
        <Button
          v-if="step > 1 && isNextBtnVisible"
          @click="handleNextBtn"
          variant="outline"
          class="w-1/4 text-lg text-white bg-green-600 rounded-full"
          >다음</Button
        >
      </Transition>
      <Transition name="fade">
        <Button
          v-if="isComplete"
          @click="handleCompleteBtn"
          variant="outline"
          class="w-1/4 text-lg text-white bg-purple-500 rounded-full hover:text-white hover:bg-purple-400 animate-bounce"
          >결과 확인하기</Button
        >
      </Transition>
    </div>
  </div>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
