<script setup>
import AttractionList from '@/components/Attractions/AttractionList.vue'
import { provide, ref, watch, watchEffect } from 'vue'
import AttractionNav from './AttractionNav.vue'
import attractionAPI from '@/api/attractions'
import AttractionSortSelector from './AttractionFilter/AttractionSortSelector.vue'

// 현재 상태
const attractionList = ref([])
const pageNo = ref(0)
const SIZE = 12
const isLoading = ref(false)

const searchParams = ref({
  sidoCode: '',
  gugunCode: '',
  contentTypeId: '',
  keyword: '',
  sort: 'NEW',
})

// 정렬 변경 시 검색
watch(
  () => searchParams.value.sort,
  () => {
    console.log(searchParams.value)
    handleSearch(searchParams.value)
  },
)

// watch(
//   () => searchParams.value.sidoCode,
//   () => searchParams.value.gugunCode,
//   () => searchParams.value.contentTypeId,
//   () => {

//     searchDataName.value = []
//   },
// )

// 부가 상태
const searchDataName = ref([])

const handleSearch = async searchData => {
  // 로딩 시작
  isLoading.value = true

  //관광지 조회
  try {
    attractionAPI.getAttractions(searchData, data => {
      attractionList.value = data.content
    })
  } finally {
    // 로딩 종료
    isLoading.value = false
  }
}

provide('handleSearch', handleSearch)
provide('searchParams', searchParams)
provide('searchDataName', searchDataName)
provide('pageNo', pageNo)
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

    <div class="flex flex-col gap-4 mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <div class="flex justify-between">
        <div>
          <span>총 </span>
          <span class="text-lg font-bold">{{ attractionList.length }}</span
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
  </main>
</template>

<style scoped></style>
