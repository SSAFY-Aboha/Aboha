<script setup>
import { computed, onMounted, ref } from 'vue'
import AttractionCard from './common/AttractionCard.vue'
import attractionAPI from '@/api/attractions'

// 여기서 데이터 검색해서 가져오기
const attractions = ref([])

const sliceAttractions = computed(() => attractions.value.slice(0, 10))

onMounted(async () => {
  const { status, data, error } = await attractionAPI.getAttractions({
    sort: 'POPULAR',
  })
  if (status === 200) {
    attractions.value = data.content
  }
  if (error) {
    console.error(error)
  }
})
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-8 py-12 size-full">
    <div class="flex flex-col items-center gap-2">
      <h1 class="text-3xl font-bold font-Namum">가장 인기있는 여행지</h1>
    </div>
    <div class="relative w-full overflow-hidden ul-wrapper">
      <ul
        class="flex items-center justify-around gap-5 px-10 carousel shrink-0"
      >
        <AttractionCard
          v-for="each in sliceAttractions"
          :key="each.id"
          :data="each"
        />
      </ul>
    </div>
  </div>
</template>

<style scoped>
.carousel {
  position: relative;
  white-space: nowrap;
  animation: carousel 20s linear infinite;
}

@keyframes carousel {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}

/* 선택사항: 마우스 호버시 애니메이션 일시정지 */
.carousel:hover {
  animation-play-state: paused;
}
</style>
