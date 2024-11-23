<script setup>
import { useInfiniteScroll } from '@/composables/useInfiniteScroll'
import AbogBoardItem from './AbogBoardItem.vue'
import AbogBoardSkeleton from '@/components/AbogBoard/AbogBoardSkeleton.vue'
import DataHasMore from '@/components/common/DataHasMore.vue'
import { ref } from 'vue'
import abogAPI from '@/api/abog'

const boardList = ref([])
const observerTarget = ref(null)

const hasMore = ref(true) // 더 이상 로드할 데이터 여부
const isLoading = ref(false) // 로딩 중 상태

const handleGetAbog = async () => {
  isLoading.value = true

  const { data, status, error } = await abogAPI.getAbog(console.log, () =>
    console.log('아보그 조회 실패'),
  )

  if (error) {
    console.error(error)
    return
  }

  if (data.length > 0) {
    boardList.value = [...boardList.value, ...data]
  } else {
    hasMore.value = false // 더 이상 로드할 데이터가 없는 경우
  }
  isLoading.value = false
}

// 무한 스크롤
useInfiniteScroll(observerTarget, () => {
  hasMore.value && handleGetAbog()
})
</script>

<template>
  <div class="flex flex-col w-full h-full gap-4">
    <ul v-if="!isLoading && boardList" class="flex flex-col gap-6">
      <AbogBoardItem
        v-for="each in boardList"
        :data="each"
        :key="each.abog.id"
      />
    </ul>

    <!-- 스켈레톤 및 관찰 대상 요소, isLoading 상태일 때 로드 중 표시 -->
    <div ref="observerTarget" class="flex-1 w-full h-full">
      <ul v-if="isLoading" class="flex flex-col gap-6">
        <AbogBoardSkeleton v-for="each in 1" :key="each" />
      </ul>

      <!-- 더 이상 로드할 데이터가 없는 경우 -->
      <div v-if="!hasMore && !isLoading" class="flex-1 w-full">
        <DataHasMore />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
