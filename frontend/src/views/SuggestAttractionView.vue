<script setup>
import SuggestAttractionContent from '@/components/SuggestAttraction/SuggestAttractionContent.vue'
import SuggestCompletePage from '@/components/SuggestAttraction/SuggestCompletePage.vue'
import { Progress } from '@/components/ui/progress'
import { computed, ref } from 'vue'

const step = ref(1)
const progress = computed(() => (step.value / 3) * 100)
const isComplete = ref(false)
const isLoading = ref(false)

const pickedData = ref({
  sidoCode: null,
  gugunCode: null,
  contentTypeId: 0,
  atmosphere: [],
})
</script>

<template>
  <div
    class="flex flex-col items-center justify-start h-full max-w-6xl pt-12 mx-auto gap-14"
  >
    <div
      @click="$router.push('/')"
      class="w-full text-sm text-left text-gray-500 cursor-pointer hover:text-black"
    >
      <i class="pi pi-home"></i>
      <span class="ml-2">홈으로 돌아가기</span>
    </div>
    <h1 v-if="!isComplete" class="text-3xl font-bold">
      아보하가 여행지를 추천해드립니다 😉
    </h1>
    <h1 v-if="isLoading" class="text-3xl font-bold animate-bounce">
      아보하가 여행지를 찾고 있습니다.
    </h1>
    <!-- 선택 완료 및 로딩 완료시 -->
    <h1
      v-if="!isLoading && isComplete"
      class="text-3xl font-bold animate-bounce"
    >
      당신에게 추천하는 여행지 입니다.
    </h1>
    <Progress
      v-if="!isComplete"
      v-model="progress"
      class="w-full [&>div]:bg-green-400 bg-green-100"
    />
    <SuggestAttractionContent
      v-if="!isComplete"
      v-model:step="step"
      v-model:isCompleteSuggest="isComplete"
      v-model:pickedData="pickedData"
    />
    <SuggestCompletePage
      v-if="isComplete"
      v-model:isLoading="isLoading"
      v-model:pickedData="pickedData"
    />
  </div>
</template>

<style lang="scss" scoped></style>
