<script setup>
import AttractionSortSelector from '@/components/Attractions/AttractionFilter/AttractionSortSelector.vue'
import AttractionList from '@/components/Attractions/AttractionList.vue'
import AttractionNav from '@/components/Attractions/AttractionNav/AttractionNav.vue'
import { onMounted, onUnmounted, provide, ref, watch } from 'vue'
import attractionAPI from '@/api/attractions'
import { ArrowUp } from 'lucide-vue-next'

// 현재 상태
const attractionList = ref([]) // 관광지 리스트
const pageNo = ref(0)
const isLoading = ref(false)
const hasMore = ref(true)
const totalElements = ref(0)

provide('hasMore', hasMore)

const searchParams = ref({
  sidoCode: '',
  gugunCode: '',
  contentTypeId: '',
  keyword: '',
  sort: 'NEW',
  lastId: 0,
  lastSortValue: 0,
})

// 정렬 변경 시 검색
watch(
  () => searchParams.value.sort,
  () => {
    console.log(searchParams.value)
    handleSearch(searchParams.value)
  },
)

watch(hasMore, () => {
  console.log('hasMore', hasMore.value)
})

const handleSearch = async searchData => {
  isLoading.value = true // 로딩 시작
  attractionList.value = [] // 관광지 리스트 초기화 : 이전 검색 결과 초기화

  pageNo.value = 0 // 페이지 초기화
  //관광지 조회
  const { status, data, error } = await attractionAPI.getAttractions(searchData)

  if (error) {
    console.error(error)
    return
  }
  if (status === 200) {
    const {
      content,
      hasNext,
      pageNumber,
      totalElements: total,
      lastId,
      lastSortValue,
    } = data
    content?.length > 0 && (attractionList.value = content)

    totalElements.value = total
    searchParams.value.lastId = lastId
    searchParams.value.lastSortValue = lastSortValue

    hasNext && (pageNo.value = pageNumber + 1) // 다음 페이지 존재 시 페이지 번호 업데이트
    hasMore.value = hasNext // 더 가져올 데이터 존재 여부 업데이트
  }
  isLoading.value = false // 로딩 종료
}

provide('handleSearch', handleSearch)
provide('searchParams', searchParams)
provide('pageNo', pageNo)
provide('totalElements', totalElements)

// 최상단 이동 버튼 표시 여부
const showScrollTop = ref(false)

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
</script>

<template>
  <div>
    <div class="sticky top-0 z-20 py-4 bg-white shadow-sm">
      <!-- header -->
      <div class="flex flex-col gap-3 mx-auto my-0 max-w-7xl md:px-8">
        <h1 class="max-w-6xl text-3xl">여행 리스트</h1>
        <!-- tag -->
        <AttractionNav />
      </div>
    </div>
    <div class="flex flex-col gap-4 mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <div class="flex justify-between">
        <div>
          <span>총 </span>
          <span class="text-lg font-bold">{{
            new Intl.NumberFormat('ko-KR').format(totalElements)
          }}</span
          ><span>개의 여행지가 있습니다.</span>
        </div>
        <div>
          <div class="flex items-center gap-3">
            <AttractionSortSelector />
          </div>
        </div>
      </div>
      <AttractionList
        ref="attractionListRef"
        v-model:isLoading="isLoading"
        v-model:attractionList="attractionList"
      />
    </div>

    <!-- 최상단 이동 버튼 -->
    <Transition name="fade">
      <button
        v-show="showScrollTop"
        @click="scrollToTop"
        class="fixed z-50 p-3 text-white transition-all duration-300 bg-green-400 rounded-full shadow-lg bottom-8 right-8 hover:bg-green-500 hover:scale-110"
        aria-label="맨 위로 이동"
      >
        <ArrowUp class="size-6" />
      </button>
    </Transition>
  </div>
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
