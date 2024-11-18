<script setup>
import AttractionItem from '@/components/Attractions/AttractionItem.vue'
import { ref, onMounted, onUnmounted } from 'vue'
import attractionAPI from '@/api/attractions'
import AttractionSkeleton from '@/components/Attractions/AttractionSkeleton.vue'

const props = defineProps({
  initData: Array,
})

const isLoading = ref(false)
const hasMore = ref(true) // 더 가져올 데이터가 있는지 여부
const observerTarget = ref(null)
const pageNo = ref(1)
const limit = 10
const attractionList = ref(props.initData)

const handleGetAttraction = async () => {
  // 로딩 시작
  // isLoading.value = true
  try {
    await attractionAPI.getAttractions(
      pageNo.value,
      limit,
      ({ data }) => {
        if (data.length > 0) {
          attractionList.value = [...attractionList.value, ...data]
          console.log(data)
          pageNo.value++
        } else {
          hasMore.value = false
        }
      },
      () => console.log('조회 실패'),
    )
  } finally {
    // 로딩 완료
    isLoading.value = false
  }
}

// 무한 스크롤
onMounted(() => {
  const infiniteScrollHandler = entries => {
    const target = entries[0]

    if (target.isIntersecting && hasMore.value) {
      // 데이터 가져오기
      handleGetAttraction()
      // 현재 observer에서 제외
    }
  }
  // 무한 스크롤을 위한 observer
  const observer = new IntersectionObserver(infiniteScrollHandler, {
    threshold: 1,
  })

  observerTarget.value && observer.observe(observerTarget.value)

  onUnmounted(() => {
    observerTarget.value && observer.unobserve(observerTarget.value)
  })
})
</script>

<template>
  <div class="w-full h-full">
    <ul
      class="grid grid-cols-1 gap-x-8 gap-y-11 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
    >
      <AttractionItem
        v-for="each in attractionList"
        :data="each"
        :key="each.id"
      />
    </ul>

    <!-- 스켈레톤 및 관찰 대상 요소 -->
    <ul
      v-if="isLoading || hasMore"
      ref="observerTarget"
      class="grid grid-cols-1 mt-4 gap-x-8 gap-y-11 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
    >
      <AttractionSkeleton v-for="n in 5" :key="n" />
    </ul>

    <!-- 더 이상 로드할 데이터가 없는 경우 -->
    <div v-else class="end-of-list">모든 데이터를 불러왔습니다.</div>
  </div>
</template>

<style scoped></style>
