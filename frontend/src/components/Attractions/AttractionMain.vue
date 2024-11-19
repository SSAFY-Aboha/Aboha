<script setup>
import AttractionList from '@/components/Attractions/AttractionList.vue'
import { provide, ref } from 'vue'
import AttractionNav from './AttractionNav.vue'
import attractionAPI from '@/api/attractions'

const attractionList = ref([])
const pageNo = ref(1)
const SIZE = 12

const searchDataName = ref({
  sidoName: '',
  gugunName: '',
  contentName: '',
})

const searchData = ref({
  sidoCode: '',
  gugunCode: '',
  contentTypeId: '',
  keyword: '',
})
const isLoading = ref(false)

const handleSearch = async () => {
  // 로딩 시작
  isLoading.value = true

  // 관광지 조회
  try {
    await attractionAPI.getAttractions(searchData.value, data => {
      attractionList.value = data
    })
  } finally {
    // 로딩 종료
    isLoading.value = false
  }
}

provide('handleSearch', handleSearch)
provide('searchData', searchData)
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

    <div class="mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <AttractionList
        ref="attractionListRef"
        v-model:isLoading="isLoading"
        v-model:attractionList="attractionList"
      />
    </div>
  </main>
</template>

<style scoped></style>
