<script setup>
import { computed } from 'vue'
import AttractionReviewItem from './AttractionReviewItem.vue'

const props = defineProps({
  reviews: {
    type: Array,
    required: true,
  },
  limit: {
    type: Number,
    required: true,
  },
})

const limitedReviews = computed(() => {
  return props.reviews.slice(0, props.limit) // 8개만 보여주기
})
</script>

<template>
  <ul class="grid grid-cols-2 gap-x-3 gap-y-10 justify-items-start">
    <AttractionReviewItem
      v-for="review in limitedReviews"
      :key="review.id"
      :review="review"
    />
  </ul>
</template>

<style lang="scss" scoped></style>
