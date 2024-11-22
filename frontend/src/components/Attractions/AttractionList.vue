<script setup>
import AttractionItem from '@/components/Attractions/AttractionItem.vue'
import { ref, inject } from 'vue'
import attractionAPI from '@/api/attractions'
import AttractionSkeleton from '@/components/Attractions/AttractionSkeleton.vue'
import AttractionHasMore from '../common/DataHasMore.vue'
import { useInfiniteScroll } from '@/composables/useInfiniteScroll'

defineProps({
  initData: Array,
})

const attractionList = defineModel('attractionList')
const isLoading = defineModel('isLoading')
const searchParams = inject('searchParams')
const pageNo = inject('pageNo')
const hasMore = inject('hasMore')
const total = inject('totalElements')

const observerTarget = ref(null) // 무한 스크롤 타겟

const handleGetAttraction = async () => {
  // 로딩 시작
  isLoading.value = true
  try {
    const data = await attractionAPI.getAttractions(
      { ...searchParams.value, page: pageNo.value },
      () => console.log('성공'),
      res => console.log('조회 실패', res),
    )
    console.log(data)
    const {
      content,
      hasNext,
      pageNumber,
      totalElements,
      lastId,
      lastSortValue,
    } = data

    if (content && content.length > 0) {
      attractionList.value = [...attractionList.value, ...content]
    } else {
      hasMore.value = false
    }

    total.value = totalElements
    searchParams.value.lastId = lastId
    searchParams.value.lastSortValue = lastSortValue

    hasNext && (pageNo.value = pageNumber + 1) // 다음 페이지 존재 시 페이지 번호 업데이트
    !hasNext && (hasMore.value = false) // 다음 페이지 없을 시 더 가져올 데이터 없음 처리
  } finally {
    // 로딩 완료
    isLoading.value = false
  }
}

// 무한 스크롤
useInfiniteScroll(observerTarget, () => {
  hasMore.value && handleGetAttraction() // 더 가져올 데이터가 있는 경우 관광지 조회
})
</script>

<template>
  <div class="flex flex-col w-full h-full">
    <ul
      class="grid grid-cols-1 gap-x-8 gap-y-11 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
    >
      <AttractionItem
        v-for="each in attractionList"
        :data="each"
        :key="each.id"
      />
    </ul>

    <!-- 스켈레톤 및 관찰 대상 요소, isLoading 상태일 때 로드 중 표시 -->
    <div ref="observerTarget" class="flex-1 w-full h-full">
      <ul
        v-if="isLoading"
        class="grid grid-cols-1 mt-4 gap-x-8 gap-y-11 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
      >
        <AttractionSkeleton v-for="n in 8" :key="n" />
      </ul>

      <!-- 더 이상 로드할 데이터가 없는 경우 -->
      <div v-if="!hasMore && !isLoading" class="flex-1 w-full">
        <AttractionHasMore />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
