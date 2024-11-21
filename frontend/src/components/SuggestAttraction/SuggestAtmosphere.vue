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
    name: '활기찬',
  },
  {
    code: 4,
    name: '모험',
  },
  {
    code: 5,
    name: '평화로운',
  },
  {
    code: 6,
    name: '고즈넉한',
  },
  {
    code: 7,
    name: '신나는',
  },
  {
    code: 8,
    name: '자연친화적',
  },
  {
    code: 9,
    name: '전통적',
  },
  {
    code: 10,
    name: '현대적',
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
