<script setup>
import Button from '@/components/ui/button/Button.vue'
import AttractionReviewList from './AttractionReviewList.vue'
import { ref, watch } from 'vue'
import AttractionReviewEditor from '@/components/Attractions/AttractionReview/AttractionReviewEditor.vue'

const limit = ref(4)

const props = defineProps({
  reviews: Object,
})

const reviewData = ref(props.reviews?.data || [])

watch(
  () => props.reviews,
  newData => {
    reviewData.value = newData?.data
    console.log('reviewData', reviewData.value)
  },
  { immediate: true },
)
</script>

<template>
  <div class="flex items-center justify-between w-full">
    <h1 class="text-3xl font-bold">후기</h1>
    <AttractionReviewEditor v-model:reviews="reviewData" />
  </div>
  <section class="flex flex-col w-full gap-3 rounded-xl">
    <!-- 전체 리뷰 평점 -->
    <div class="flex items-center gap-2">
      <span class="text-lg font-bold">전체 리뷰 평점</span>
      <span class="text-lg font-bold">4.5 / 5.0</span>
    </div>
    <AttractionReviewList :reviews="reviewData" :limit="limit" />
  </section>
  <!-- 버튼 -->
  <div class="flex items-center justify-start w-full gap-4 mx-auto">
    <Button
      v-if="limit < reviewData.length"
      @click="limit = reviewData.length"
      class="px-4 py-6 text-lg text-black bg-white border shadow-sm hover:bg-gray-200"
      >후기 {{ reviewData.length }}개 모두 보기</Button
    >
    <Button
      v-if="limit === reviewData.length"
      @click="limit = 4"
      class="px-4 py-6 text-lg text-black bg-white border shadow-sm hover:bg-gray-200"
      >후기 축소하기</Button
    >
  </div>
</template>

<style scoped></style>
