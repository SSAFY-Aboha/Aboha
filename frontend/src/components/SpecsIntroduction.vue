<script setup>
import 'primeicons/primeicons.css'
</script>

<template>
  <div class="flex flex-col items-center justify-start py-10 gap-7 size-full">
    <div class="flex flex-col items-center gap-2">
      <h1 class="text-5xl font-bold font-Namum">아보하</h1>
      <span class="text-xl text-gray-500">아보하 한눈에 보기</span>
    </div>
    <div class="w-5/6">
      <ul
        class="flex items-center justify-around gap-5 px-10 basis-4/5 shrink-0"
      >
        <li class="flex flex-col items-center justify-center gap-5">
          <div class="flex flex-col items-center justify-center gap-3">
            <span class="text-3xl font-bold">{{ 20 }}</span>
            <span class="text-xl text-gray-500">등록된 리뷰</span>
          </div>
          <span class="text-2xl pi pi-star icon-size"></span>
        </li>

        <li class="flex flex-col items-center justify-center gap-5">
          <div class="flex flex-col items-center justify-center gap-3">
            <span class="text-3xl font-bold">{{ 20 }}</span>
            <span class="text-xl text-gray-500">이용자 수</span>
          </div>
          <span class="text-2xl pi pi-users icon-size"></span>
        </li>

        <li class="flex flex-col items-center justify-center gap-5">
          <div class="flex flex-col items-center justify-center gap-3">
            <span class="text-3xl font-bold">{{ 22 }}</span>
            <span class="text-xl text-gray-500">등록된 여행지</span>
          </div>
          <span class="text-2xl pi pi-map-marker icon-size"></span>
        </li>

        <li class="flex flex-col items-center justify-center gap-5">
          <div class="flex flex-col items-center justify-center gap-3">
            <span class="text-3xl font-bold">{{ 22 }}</span>
            <span class="text-xl text-gray-500">등록된 여행 계획</span>
          </div>
          <span class="text-2xl pi pi-calendar icon-size"></span>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.icon-size {
  font-size: 4rem;
}
</style>
