<script setup>
import { useInfiniteScroll } from '@/composables/useInfiniteScroll'
import AbogBoardItem from './AbogBoardItem.vue'
import AbogBoardSkeleton from '@/components/AbogBoard/AbogBoardSkeleton.vue'
import DataHasMore from '@/components/common/DataHasMore.vue'
import { ref, onMounted } from 'vue'
import abogAPI from '@/api/abog'

const boardList = ref([])
const observerTarget = ref(null)
const page = ref(1)
const hasMore = ref(true)
const isLoading = ref(false)

const handleGetAbog = async () => {
  if (isLoading.value || !hasMore.value) return

  isLoading.value = true
  try {
    const { data, error } = await abogAPI.getAbog(page.value)

    if (error) {
      console.error('Error fetching abog:', error)
      return
    }

    if (data && data.length > 0) {
      boardList.value = [...boardList.value, ...data]
      page.value += 1
    } else {
      hasMore.value = false
    }
  } catch (error) {
    console.error('Failed to fetch abog:', error)
  } finally {
    isLoading.value = false
  }
}

// 초기 데이터 로드
onMounted(() => {
  handleGetAbog()
})

// 무한 스크롤 설정
useInfiniteScroll(observerTarget, handleGetAbog)
</script>

<template>
  <div class="w-full max-w-3xl mx-auto">
    <!-- 게시글 목록 -->
    <ul v-if="boardList.length > 0" class="flex flex-col gap-6">
      <li
        v-for="each in boardList"
        :key="each.abog.id"
        class="bg-white rounded-lg shadow-sm overflow-hidden"
      >
        <AbogBoardItem :data="each" />
      </li>
    </ul>

    <!-- 스켈레톤 및 관찰 대상 요소 -->
    <div
      ref="observerTarget"
      class="w-full h-20 flex items-center justify-center"
    >
      <!-- 로딩 스켈레톤 -->
      <div v-if="isLoading" class="w-full">
        <ul class="flex flex-col gap-6">
          <li
            v-for="n in 2"
            :key="n"
            class="bg-white rounded-lg shadow-sm overflow-hidden"
          >
            <AbogBoardSkeleton />
          </li>
        </ul>
      </div>

      <!-- 더 이상 데이터가 없는 경우 -->
      <div v-if="!hasMore && !isLoading" class="py-8">
        <DataHasMore />
      </div>
    </div>
  </div>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>
