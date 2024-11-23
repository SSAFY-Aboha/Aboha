<script setup>
import { Skeleton } from '../ui/skeleton'
</script>

<template>
  <!-- content -->
  <main class="flex flex-col w-full max-w-4xl gap-8 pb-6">
    <div class="flex flex-col gap-4">
      <div class="flex items-baseline justify-between gap-3">
        <Skeleton class="w-2/4 h-10" />
        <Skeleton class="w-1/4 h-10" />
        <!-- 좋아요 -->
      </div>
      <!-- 이미지 -->
      <Skeleton class="w-full h-96 rounded-2xl" />
      <!-- 설명 -->
      <Skeleton class="w-full h-20" />
      <!-- 관련 태그 -->
      <div class="flex gap-4">
        <Skeleton v-for="n in 3" :key="n" class="w-1/6 h-10" />
      </div>
    </div>
    <!-- 지도 -->
    <Skeleton class="w-full h-96" />
    <!-- 리뷰 -->
    <Skeleton class="w-full h-80" />
  </main>
</template>

<style scoped></style>
