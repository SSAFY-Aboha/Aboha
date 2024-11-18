<script setup>
import AttractionList from '@/components/Attractions/AttractionList.vue'
import { ref } from 'vue'
import AttractionNav from './AttractionNav.vue'

const attractionList = ref([
  {
    id: 26183,
    title: '괴산고추축제',
    sidoCode: 33,
    sidoName: '충청북도',
    gugunCode: 1,
    gugunName: '괴산군',
    image: 'http://tong.visitkorea.or.kr/cms/resource/77/2997377_image2_1.png',
    likeCount: 0,
    tags: ['축제', '충청북도', '괴산군'],
  },
])
</script>

<template>
  <main class="flex flex-col h-full gap-4">
    <div class="sticky top-0 z-20 py-4 bg-white shadow-sm">
      <!-- header -->
      <div class="flex flex-col gap-3 mx-auto my-0 max-w-7xl md:px-8">
        <h1 class="max-w-6xl text-3xl">여행 리스트</h1>
        <!-- tag -->
        <AttractionNav />
      </div>
    </div>

    <div class="mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <AttractionList ref="attractionListRef" :initData="attractionList" />
    </div>
    <div class="w-full bg-[#eee] h-52"></div>
  </main>
</template>

<style scoped></style>
