<script setup>
import { ref } from 'vue'
import SuggestToggleGroup from './SuggestToggleGroup.vue'

const pickedData = defineModel('pickedData')

const atmosphereList = ref([
  {
    code: 1,
    name: '휴식',
  },
  {
    code: 2,
    name: '로맨틱',
  },
  {
    code: 3,
    name: '모험',
  },
  {
    code: 4,
    name: '힐링',
  },
  {
    code: 5,
    name: '문화예술',
  },
  {
    code: 6,
    name: '자연친화',
  },
  {
    code: 7,
    name: '역사탐방',
  },
  {
    code: 8,
    name: '활기찬',
  },
  {
    code: 9,
    name: '고요한',
  },
  {
    code: 10,
    name: '이국적',
  },
  {
    code: 11,
    name: '전통적',
  },
  {
    code: 12,
    name: '현대적',
  },
  {
    code: 13,
    name: '럭셔리',
  },
  {
    code: 14,
    name: '서정적',
  },
  {
    code: 15,
    name: '신비로운',
  },
  {
    code: 16,
    name: '즐거운',
  },
  {
    code: 17,
    name: '평화로운',
  },
  {
    code: 18,
    name: '모던한',
  },
  {
    code: 19,
    name: '감성적',
  },
  {
    code: 20,
    name: '액티브',
  },
])

const handleAtmosphereChange = atmosphere => {
  pickedData.value.atmosphere = atmosphere
}
</script>

<template>
  <h2 class="text-2xl font-bold text-center font-Nanum">
    마지막으로, 원하는 분위기를 선택해주세요 😍
  </h2>
  <div class="flex flex-col h-3/4">
    <div class="flex flex-col items-center h-full">
      <div class="flex flex-col justify-start h-full gap-24">
        <!-- 주요 지역 -->
        <SuggestToggleGroup
          :type="'multiple'"
          :infoData="atmosphereList"
          @update:modelValue="handleAtmosphereChange"
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
