<script setup>
import { onMounted, ref } from 'vue'

const data = ref({
  id: 26183,
  title: '괴산고추축제',
  sidoCode: 33,
  sidoName: '충청북도',
  gugunCode: 1,
  gugunName: '괴산군',
  image: 'http://tong.visitkorea.or.kr/cms/resource/77/2997377_image2_1.png',
  likeCount: 0,
})

onMounted(() => {
  console.log('mounted')
})
</script>

<template>
  <div
    class="flex flex-col items-center justify-start w-screen h-full mx-auto min-w-80"
  >
    <!-- header -->
    <header class="w-full py-4">
      <div class="flex items-center justify-between w-full">d</div>
    </header>
    <!-- content -->
    <div class="flex flex-col w-full max-w-4xl gap-10">
      <!-- 이미지 -->
      <div class="w-full overflow-hidden bg-red-300 rounded-lg h-96">
        <img
          class="object-cover w-full h-full"
          :src="data.image"
          alt="attraction image"
        />
      </div>
      <!-- title -->
      <!-- 날짜 & 태그 -->
      <!-- 제목 & 내용 -->
      <div class="flex items-center justify-between w-full">
        <div class="flex items-baseline w-full gap-3">
          <h1 class="text-3xl font-bold">{{ data.title }}</h1>
          <div class="flex items-center gap-2 text-lg text-gray-600">
            <span>{{ data.sidoName }}</span>
            <span>{{ data.gugunName }}</span>
          </div>
        </div>
        <!-- 좋아요 -->
        <div class="flex items-center justify-end gap-2 basis-1/5">
          <span>좋아요</span>
          <i class="text-red-500 pi pi-heart"></i>
          <span>{{ data.likeCount }}</span>
        </div>
      </div>
    </div>
    <!-- 지도 -->
    <!-- 리뷰 -->
  </div>
</template>

<style scoped></style>
