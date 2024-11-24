<script setup>
import AbogBoardList from '@/components/AbogBoard/AbogBoardList.vue'
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { Button } from '../ui/button'
import abog from '@/api/abog'

const route = useRoute()
const isLoading = ref(false)
const showScrollTop = ref(false)

watch(
  () => route.query,
  query => {
    console.log(query)
  },
)

// 스크롤 위치에 따라 버튼 표시 여부 결정
const handleScroll = () => {
  showScrollTop.value = window.scrollY > 300
}

// 최상단으로 스크롤
const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  })
}

// 컴포넌트 마운트/언마운트 시 이벤트 리스너 관리
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

/**
 * @data {Array} boardList
 * { abog: {}, attraction: {}, user: {} }
 */
</script>

<template>
  <main class="flex flex-col min-h-screen">
    <div
      class="sticky top-0 z-10 flex flex-col items-center justify-between px-4 py-4 bg-white shadow-md md:flex-row md:px-8"
    >
      <div class="mb-4 text-center md:text-left md:mb-0">
        <h1 class="text-2xl font-bold md:text-3xl font-Namum">아보하 Log</h1>
        <p class="pt-1 text-sm italic text-gray-500 md:text-md">
          여행 후기를 공유하고 소통해보세요!
        </p>
      </div>

      <div class="w-full md:w-40">
        <RouterLink :to="{ name: 'abog-edit' }">
          <Button
            class="w-full font-bold text-black transition-all duration-300 transform bg-green-300 hover:bg-green-400 hover:scale-105 focus:ring-2 focus:ring-green-500 focus:ring-opacity-50"
          >
            <span class="flex items-center justify-center">
              <i class="mr-2 fas fa-pen"></i> 글 작성하기
            </span>
          </Button>
        </RouterLink>
      </div>
    </div>

    <div class="flex-1 w-full px-4 py-4 md:px-8 bg-gray-50">
      <div class="flex max-w-screen-xl gap-4 mx-auto">
        <aside
          class="sticky hidden w-64 p-4 bg-white rounded-lg shadow-sm lg:block h-fit top-24"
        >
          <h3 class="mb-3 text-lg font-bold">인기 태그</h3>
          <div class="flex flex-wrap gap-2">
            <span
              v-for="tag in ['#제주도', '#서울', '#부산', '#강원도', '#맛집']"
              :key="tag"
              class="px-3 py-1 text-sm bg-gray-100 rounded-full cursor-pointer hover:bg-green-100"
            >
              {{ tag }}
            </span>
          </div>

          <h3 class="mt-6 mb-3 text-lg font-bold">빠른 필터</h3>
          <div class="space-y-2">
            <button
              class="w-full px-3 py-2 text-left rounded hover:bg-green-50"
            >
              🔥 인기 게시글
            </button>
            <button
              class="w-full px-3 py-2 text-left rounded hover:bg-green-50"
            >
              ⭐ 추천 많은 순
            </button>
            <button
              class="w-full px-3 py-2 text-left rounded hover:bg-green-50"
            >
              💬 댓글 많은 순
            </button>
          </div>
        </aside>

        <div class="flex-1">
          <Transition name="fade">
            <div v-if="isLoading" class="flex justify-center py-8">
              <div
                class="w-8 h-8 border-b-2 border-green-300 rounded-full animate-spin"
              ></div>
            </div>
            <AbogBoardList v-else />
          </Transition>
        </div>

        <aside
          class="sticky hidden p-4 bg-white rounded-lg shadow-sm xl:block w-72 h-fit top-24"
        >
          <h3 class="mb-3 text-lg font-bold">인기 게시글</h3>
          <div class="space-y-3">
            <div
              v-for="i in 3"
              :key="i"
              class="p-3 rounded-lg cursor-pointer bg-gray-50 hover:bg-green-50"
            >
              <h4 class="text-sm font-medium">제주도 여행 후기 {{ i }}</h4>
              <p class="mt-1 text-xs text-gray-500">
                조회수 {{ 100 * i }} • 좋아요 {{ 20 * i }}
              </p>
            </div>
          </div>

          <h3 class="mt-6 mb-3 text-lg font-bold">여행 통계</h3>
          <div class="space-y-2 text-sm">
            <p>📝 오늘 작성된 글: 23개</p>
            <p>👥 현재 접속자: 145명</p>
            <p>✨ 이번 주 인기 지역: 제주도</p>
          </div>
        </aside>
      </div>
    </div>

    <footer
      class="w-full px-4 py-6 text-center text-gray-600 bg-gray-100 md:px-8"
    >
      <p class="text-sm">더 많은 여행 이야기를 공유해보세요!</p>
    </footer>

    <!-- 최상단 이동 버튼 -->
    <Transition name="fade">
      <button
        v-show="showScrollTop"
        @click="scrollToTop"
        class="fixed z-50 p-3 text-white transition-all duration-300 bg-green-400 rounded-full shadow-lg bottom-8 right-8 hover:bg-green-500 hover:scale-110"
        aria-label="맨 위로 이동"
      >
        <i class="text-xl fas fa-arrow-up"></i>
      </button>
    </Transition>
  </main>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
