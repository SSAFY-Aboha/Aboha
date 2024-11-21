<script setup>
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'
import SuggestToggleGroup from './SuggestToggleGroup.vue'
import { onMounted, ref } from 'vue'
import attractionsAPI from '@/api/attractions'

const pickedData = defineModel('pickedData')

const sidoList = ref([]) // 시도 목록
const gugunList = ref([]) // 구군 목록

onMounted(() => {
  // 시도 조회
  attractionsAPI.getSido(data => (sidoList.value = data))
})

const handleSidoChange = sidoCode => {
  if (pickedData.value.sidoCode === sidoCode) return // 이미 선택한 시도라면 중단

  pickedData.value.sidoCode = sidoCode
  pickedData.value.gugunCode = null
  gugunList.value = []
  // 구군 조회
  attractionsAPI.getGugun(sidoCode, data => (gugunList.value = data))
}

// 구군 변경 시 이름 저장
const handleGugunChange = gugunCode => {
  pickedData.value.gugunCode = gugunCode
}
</script>

<template>
  <h2 class="text-2xl font-bold text-center font-Nanum">
    1. 지역을 선택해주세요.
  </h2>
  <div class="flex flex-col h-3/4">
    <div class="flex flex-col items-center h-full">
      <div class="flex flex-col justify-start h-full gap-16">
        <!-- 주요 지역 -->
        <SuggestToggleGroup
          :infoData="sidoList"
          @update:modelValue="handleSidoChange"
        />

        <!-- 상세 지역 -->
        <SuggestToggleGroup
          :infoData="gugunList"
          @update:modelValue="handleGugunChange"
        />
      </div>
    </div>
  </div>
</template>
