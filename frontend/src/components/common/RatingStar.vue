<script setup>
defineProps({
  rating: {
    type: Number,
    required: true,
  },
})
</script>

<template>
  <div class="flex items-center gap-1">
    <i
      v-for="star in rating"
      :key="star"
      class="text-yellow-500 pi pi-star-fill"
    ></i>
    <i
      v-for="star in 5 - rating"
      :key="star"
      class="text-gray-500 pi pi-star"
    ></i>
  </div>
</template>

<style lang="scss" scoped></style>
