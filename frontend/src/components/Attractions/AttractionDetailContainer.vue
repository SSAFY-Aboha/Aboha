<script setup>
import { onMounted, ref, watch } from 'vue'
import attractionAPI from '@/api/attractions'
import { Button } from '../ui/button'
import AttractionDetailSkeleton from './AttractionDetailSkeleton.vue'
import AttractionDetail from '@/components/Attractions/AttractionDetail.vue'
import { getSummarizeAIResponse } from '@/services/summarizeAIService'
import { getTagAIResponse } from '@/services/getTagAIService'

const props = defineProps({
  tripId: {
    type: [String, Number],
  },
})

const isLoading = ref(false)

const attraction = ref(null)

onMounted(async () => {
  isLoading.value = true
  const { status, data, error } = await attractionAPI.getAttractionDetail(
    props.tripId,
  )
  if (status === 200) {
    const { title, address, description } = data

    const message = `여행지 이름: ${title}\n여행지 주소: ${address}\n여행지 소개글: ${description}`
    // const tagString = await getTagAIResponse(message)
    // const tags = tagString.split(',').map(tag => tag.trim())
    // const summarize = await getSummarizeAIResponse(message)
    // console.log(tags)

    // attraction.value = { ...data, tags }
    // attraction.value = { ...data, description: summarize, tags }

    attraction.value = { ...data }
  }
  if (error) {
    console.error('Error loading trip data:', error)
  }
  isLoading.value = false
})

// 컴포넌트가 업데이트될 때도 데이터를 다시 가져오도록
watch(
  () => props.tripId,
  async newId => {
    isLoading.value = true
    const { status, data, error } =
      await attractionAPI.getAttractionDetail(newId)
    if (status === 200) {
      attraction.value = data
    }
    if (error) {
      console.error('Error loading trip data:', error)
    }
    isLoading.value = false
  },
)
</script>

<template>
  <div
    class="flex flex-col items-center justify-start h-full gap-4 mx-auto min-w-80"
  >
    <!-- header -->
    <header class="w-full py-4">
      <div
        class="flex items-center justify-start max-w-6xl min-w-[56rem] mx-auto px-7"
      >
        <Button
          class="px-3 py-1 text-lg text-black transition-all bg-white rounded-lg shadow-none hover:bg-gray-200"
          @click="$router.push('/trips')"
          ><i class="pi pi-angle-left"></i
          ><span>목록으로 돌아가기</span></Button
        >
      </div>
    </header>
    <!-- content -->
    <AttractionDetailSkeleton v-if="isLoading" />
    <AttractionDetail
      v-if="!isLoading && attraction"
      :attraction="attraction"
      v-model:likeCount="attraction.likeCount"
    />
    <!-- 추가 정보 -->
    <!-- <div class="flex flex-col w-full max-w-4xl gap-4 py-4">
      <h1 class="text-3xl font-bold">함께 추천 하는 관광지</h1>
      <ul class="flex flex-wrap gap-4">
        <AttractionItem v-for="each in 6" :data="data" :key="each" />
      </ul>
    </div> -->
  </div>
</template>

<style scoped></style>
