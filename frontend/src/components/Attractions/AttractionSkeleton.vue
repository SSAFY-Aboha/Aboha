<script setup>
import { Skeleton } from '@/components/ui/skeleton'
</script>

<template>
  <li
    class="max-w-72 min-w-60 flex flex-col items-start w-full gap-3 max-h-[40rem]"
  >
    <!-- 이미지 -->
    <Skeleton class="w-full h-52 rounded-xl" />

    <!-- 제목 & 내용 -->
    <div class="flex flex-col justify-start w-full gap-3">
      <Skeleton class="w-3/4 h-6" />
      <Skeleton class="w-2/4 h-6" />

      <div class="flex gap-2">
        <Skeleton class="w-1/5 h-6" v-for="each in 3" :key="each" />
      </div>
    </div>
  </li>
</template>

<style scoped></style>
