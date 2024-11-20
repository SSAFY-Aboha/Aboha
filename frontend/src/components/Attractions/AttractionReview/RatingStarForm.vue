<script setup>
import { ref } from 'vue'

const rating = defineModel('rating')

const hoverRating = ref(0) // hover시 몇번째 별까지 채워졌는지 표시

const starClass = star => {
  return star <= hoverRating.value || rating.value >= star
    ? 'pi pi-star-fill'
    : 'pi pi-star'
}
</script>

<template>
  <div>
    <div
      class="flex items-center gap-1"
      @mouseleave="() => rating === 0 && (hoverRating = 0)"
    >
      <i
        v-for="star in 5"
        :key="star"
        :class="`cursor-pointer text-yellow-500 text-2xl ${starClass(star)}`"
        @mouseover="hoverRating = star"
        @click="rating = star"
      ></i>
    </div>
  </div>
</template>

<style scoped></style>
