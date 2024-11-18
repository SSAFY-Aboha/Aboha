<script setup>
import { Skeleton } from '@/components/ui/skeleton'
</script>

<template>
  <li class="max-w-6xl min-w-80">
    <div class="flex flex-col gap-3">
      <!-- header -->
      <div class="flex items-center max-w-2xl gap-4">
        <!-- user 정보 -->
        <Skeleton class="rounded-full size-12" />
        <div class="flex flex-col w-2/4 gap-2">
          <Skeleton class="w-full h-4 rounded-lg" />
          <!-- 장소 마크 -->
          <Skeleton class="w-full h-4 rounded-lg" />
        </div>
      </div>
      <!-- 제목 -->
      <Skeleton class="w-3/4 h-5 rounded-lg" />
      <!-- 이미지 -->
      <div class="flex items-center justify-center overflow-hidden rounded-xl">
        <Skeleton class="w-full rounded-md aspect-square" />
      </div>

      <Skeleton class="w-3/4 h-5" />
      <Skeleton class="w-full h-5" />
    </div>
  </li>
</template>

<style scoped></style>
