<script setup>
import { onMounted, ref } from 'vue'
import SuggestToggleGroup from './SuggestToggleGroup.vue'
import attractionsAPI from '@/api/attractions'

const contentTypeList = ref([])

const pickedData = defineModel('pickedData')

onMounted(async () => {
  // 시도 조회
  const { status, data, error } = await attractionsAPI.getContentType()
  if (status === 200) {
    contentTypeList.value = data
  }
})

const handleContentTypeChange = contentTypeId => {
  pickedData.value.contentTypeId = +contentTypeId
}
</script>

<template>
  <h2 class="text-2xl font-bold text-center font-Nanum">
    2. 테마를 선택해주세요.
  </h2>
  <div class="flex flex-col h-3/4">
    <div class="flex flex-col items-center h-full">
      <div class="flex flex-col justify-start h-full gap-24">
        <!-- 주요 지역 -->
        <SuggestToggleGroup
          :infoData="contentTypeList"
          @update:modelValue="handleContentTypeChange"
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
