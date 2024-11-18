<script setup>
import AttractionList from '@/components/Attractions/AttractionList.vue'
import { onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import AttractionNav from './AttractionNav.vue'

const route = useRoute()

const attractionListRef = ref(null)

onMounted(() => {
  const infiniteScrollHandler = (entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        // 데이터 가져오기
        console.log('데이터 가져오기')

        // 현재 observer에서 제외
        observer.unobserve(entry.target)

        // 새로운 observer 추가
        // observer.observe(attractionListRef.value.observerTarget)
      }
    })
  }
  // 무한 스크롤을 위한 observer
  const observer = new IntersectionObserver(infiniteScrollHandler, {
    threshold: 0.7,
  })

  attractionListRef.value?.observerTarget &&
    observer.observe(attractionListRef.value.observerTarget)
})

watch(
  () => route.query,
  query => {
    console.log(query)
  },
)
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
      <AttractionList ref="attractionListRef" />
    </div>
    <div class="w-full bg-[#eee] h-52"></div>
  </main>
</template>

<style scoped></style>
