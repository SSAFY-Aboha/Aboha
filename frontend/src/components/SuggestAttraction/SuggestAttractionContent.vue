<script setup>
import { ref, watch } from 'vue'
import SuggestLocation from './SuggestLocation.vue'
import { Button } from '../ui/button'
import SuggestContentType from './SuggestContentType.vue'
import SuggestAtmosphere from './SuggestAtmosphere.vue'
import { CheckCircle } from 'lucide-vue-next'

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
  () => pickedData.value.contentTypeId,
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

const steps = [
  { title: '지역 선택', description: '여행하고 싶은 지역을 선택해주세요' },
  { title: '테마 선택', description: '원하시는 여행 테마를 선택해주세요' },
  { title: '분위기 선택', description: '선호하는 분위기를 선택해주세요' },
]
</script>

<template>
  <div class="flex flex-col min-h-[calc(100vh-6rem)] w-full">
    <!-- 전체 컨테이너 -->
    <div class="flex-1 flex flex-col max-w-4xl mx-auto w-full px-4 py-8">
      <!-- Steps indicator - 상단에 고정 -->
      <div class="mb-8">
        <div class="flex justify-between">
          <div
            v-for="(s, index) in steps"
            :key="index"
            class="flex flex-col items-center w-1/3"
          >
            <div
              class="flex items-center justify-center w-10 h-10 mb-2 rounded-full"
              :class="[
                step > index + 1
                  ? 'bg-green-100 text-green-600'
                  : step === index + 1
                    ? 'bg-green-600 text-white'
                    : 'bg-gray-100 text-gray-400',
              ]"
            >
              <CheckCircle v-if="step > index + 1" class="w-6 h-6" />
              <span v-else>{{ index + 1 }}</span>
            </div>
            <span
              class="text-sm font-medium"
              :class="step === index + 1 ? 'text-green-600' : 'text-gray-500'"
            >
              {{ s.title }}
            </span>
            <span class="text-xs text-gray-400 text-center mt-1">
              {{ s.description }}
            </span>
          </div>
        </div>
      </div>

      <!-- Content - 스크롤 가능한 영역 -->
      <div class="flex-1 flex flex-col">
        <div
          class="flex-1 overflow-y-auto min-h-[400px] max-h-[calc(100vh-20rem)]"
        >
          <TransitionGroup name="slide">
            <SuggestLocation
              v-if="step === 1"
              :key="1"
              v-model:pickedData="pickedData"
            />
            <SuggestContentType
              v-if="step === 2"
              :key="2"
              v-model:pickedData="pickedData"
            />
            <SuggestAtmosphere
              v-if="step === 3"
              :key="3"
              v-model:pickedData="pickedData"
            />
          </TransitionGroup>
        </div>

        <!-- Navigation buttons - 하단에 고정 -->
        <div class="flex justify-center gap-4 mt-8 pt-4 border-t">
          <Button
            @click="handleBackBtn"
            variant="outline"
            class="w-32 text-gray-600 hover:text-gray-800"
            :disabled="step === 1"
          >
            이전
          </Button>

          <Button
            v-if="!isComplete"
            @click="handleNextBtn"
            class="w-32 bg-green-600 hover:bg-green-700 text-white"
            :disabled="!isNextBtnVisible"
          >
            다음
          </Button>

          <Button
            v-else
            @click="handleCompleteBtn"
            class="w-32 bg-purple-600 hover:bg-purple-700 text-white"
          >
            결과 보기
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease-out;
}

.slide-enter-from {
  opacity: 0;
  transform: translateX(20px);
}

.slide-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

/* 스크롤바 스타일링 */
.overflow-y-auto {
  scrollbar-width: thin;
  scrollbar-color: #e2e8f0 #ffffff;
}

.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #ffffff;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background-color: #e2e8f0;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background-color: #cbd5e1;
}
</style>
