<script setup>
import Button from '@/components/ui/button/Button.vue'
import AttractionReviewList from './AttractionReviewList.vue'
import { ref, computed } from 'vue'
import AttractionReviewEditor from '@/components/Attractions/AttractionReview/AttractionReviewEditor.vue'
import RatingStar from '@/components/common/RatingStar.vue'

const limit = ref(4)

const reviews = defineModel('reviews')

defineProps({
  attractionId: Number,
})

const reviewData = computed(() => reviews?.value?.data || [])
</script>

<template>
  <div class="flex flex-col gap-6">
    <!-- 헤더 -->
    <div
      class="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between"
    >
      <div class="flex flex-col gap-2">
        <h1 class="text-3xl font-bold">후기</h1>
        <div class="flex items-center gap-3">
          <div class="flex items-center gap-2">
            <span class="text-2xl font-bold">{{ reviews.totalAvgRating }}</span>
            <span class="text-gray-500">/</span>
            <span class="text-gray-500">5.0</span>
          </div>
          <div class="w-px h-6 bg-gray-300"></div>
          <RatingStar :rating="Math.round(reviews.totalAvgRating)" />
          <div class="w-px h-6 bg-gray-300"></div>
          <span class="text-gray-600">{{ reviewData.length }}개의 후기</span>
        </div>
      </div>
      <AttractionReviewEditor
        v-model:reviews="reviews"
        :attraction-id="attractionId"
        class="shrink-0"
      />
    </div>

    <!-- 리뷰 리스트 -->
    <AttractionReviewList :reviews="reviewData" :limit="limit" />

    <!-- 더보기 버튼 -->
    <div class="flex justify-center">
      <Button
        v-if="limit < reviewData.length"
        @click="limit = reviewData.length"
        variant="outline"
        class="px-6 py-2 text-base hover:bg-gray-100"
      >
        후기 {{ reviewData.length }}개 모두 보기
        <i class="ml-2 pi pi-chevron-down"></i>
      </Button>
      <Button
        v-else-if="limit === reviewData.length && reviewData.length > 4"
        @click="limit = 4"
        variant="outline"
        class="px-6 py-2 text-base hover:bg-gray-100"
      >
        접기
        <i class="ml-2 pi pi-chevron-up"></i>
      </Button>
    </div>
  </div>
</template>

<style scoped></style>
