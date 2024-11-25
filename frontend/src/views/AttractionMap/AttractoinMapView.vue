<script setup>
import AttractionSearchInput from '@/components/AbogBoard/AttractionSearchInput.vue'
import RatingStar from '@/components/common/RatingStar.vue'
import Button from '@/components/ui/button/Button.vue'
import {
  ChevronLeftIcon,
  ChevronRightIcon,
  CurrencyIcon,
  MapPinIcon,
} from 'lucide-vue-next'
import { onMounted, provide, ref, watch } from 'vue'
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps'
import attractionAPI from '@/api/attractions'
import AttractionList from '@/components/Attractions/AttractionMap/AttractionList.vue'

const map = ref(null)
// mapLoading 상태 선언
const mapLoading = ref(true)

const onLoadKakaoMap = mapRef => {
  map.value = mapRef
}

onMounted(() => {
  getCurrentLocation()
  // 지도 로딩 완료 시 로딩 상태 해제
  setTimeout(() => {
    mapLoading.value = false
  }, 1000)
})

const initialCenter = ref({
  lat: 0,
  lng: 0,
})

const isOpen = ref(true)

const attractionList = ref([]) // 관광지 리스트

// 마커 리스트 : 검색 후 마커 위치 설정
const markerList = ref([
  {
    id: 1,
    latitude: initialCenter.value.lat,
    longitude: initialCenter.value.lng,
  },
]) // 마커 리스트
const pageNo = ref(0)
const isLoading = ref(false)
const hasMore = ref(true)

const searchParams = ref({
  sidoCode: '',
  gugunCode: '',
  contentTypeId: '',
  keyword: '',
  sort: 'NEW',
  lastId: 0,
  lastSortValue: 0,
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
    content.length > 0 && (attractionList.value = content)

    console.log(content)

    // totalElements.value = total
    searchParams.value.lastId = lastId
    searchParams.value.lastSortValue = lastSortValue

    hasNext && (pageNo.value = pageNumber + 1) // 다음 페이지 존재 시 페이지 번호 업데이트
    hasMore.value = hasNext // 더 가져올 데이터 존재 여부 업데이트
  }
  isLoading.value = false // 로딩 종료
}

// const onLoadKakaoMap = mapRef => {
//   map.value = mapRef
// }

const handleToggle = () => {
  isOpen.value = !isOpen.value
}

const handleKeywordChange = keyword => {
  searchParams.value.keyword = keyword
  handleSearch(searchParams.value)
}

const handleCurrentLocation = () => {
  console.log('현재 위치에서 검색')
}

const setCenter = (lat, lng) => {
  if (map.value) {
    map.value.setCenter(new kakao.maps.LatLng(lat, lng))
  }
}

const getCurrentLocation = () => {
  navigator.geolocation.getCurrentPosition(
    position => {
      const { latitude, longitude } = position.coords
      initialCenter.value = { lat: latitude, lng: longitude }
      setCenter(latitude, longitude)
    },
    error => {
      console.error('위치 정보를 가져오는데 실패했습니다:', error)

      initialCenter.value = { lat: 37.5666805, lng: 126.9784147 }
      mapLoading.value = false
    },
  )
}

const setOneMarker = (lat, lng) => {
  markerList.value = [{ id: 1, latitude: lat, longitude: lng }]
}

provide('handleSearch', handleSearch)
provide('searchParams', searchParams)
provide('pageNo', pageNo)
provide('hasMore', hasMore)
provide('setCenter', setCenter)
provide('setOneMarker', setOneMarker)
console.log(markerList.value)
</script>

<template>
  <div class="relative w-screen h-screen">
    <!-- 네비게이션 바 개선 -->
    <div class="absolute right-0 z-40 w-full shadow-sm bg-white/80">
      <div
        class="container flex items-center justify-between h-16 px-4 mx-auto"
      >
        <div class="flex items-center gap-x-4">
          <h1 class="text-xl font-bold text-green-600">관광지도</h1>
        </div>
        <div class="flex gap-x-3">
          <Button
            @click="getCurrentLocation"
            variant="outline"
            class="border-green-500 rounded-full hover:bg-green-50"
            title="현재 위치로 이동"
          >
            <CurrencyIcon class="w-4 h-4 text-green-600" />
          </Button>
          <Button
            variant="outline"
            class="border-green-500 rounded-full hover:bg-green-50"
            @click="handleCurrentLocation"
          >
            <MapPinIcon class="w-4 h-4 text-green-600" />
            <span class="ml-2">현재 위치에서 검색</span>
          </Button>
        </div>
      </div>
    </div>

    <!-- 지도 로딩 상태 표시 -->
    <div
      v-if="mapLoading"
      class="absolute inset-0 z-30 flex items-center justify-center bg-gray-50"
    >
      <div class="flex flex-col items-center gap-2">
        <div
          class="w-8 h-8 border-4 border-green-500 rounded-full border-t-transparent animate-spin"
        ></div>
        <p class="text-sm text-gray-600">지도를 불러오는 중...</p>
      </div>
    </div>

    <!-- 지도 컴포넌트 -->
    <KakaoMap
      width="100%"
      height="100%"
      :lat="initialCenter?.lat"
      :lng="initialCenter?.lng"
      :draggable="true"
      @onLoadKakaoMap="onLoadKakaoMap"
    >
      <KakaoMapMarker
        v-for="attraction in markerList"
        :key="attraction.id"
        :lat="attraction.latitude"
        :lng="attraction.longitude"
        :clickable="true"
      />
    </KakaoMap>

    <!-- 사이드바 개선 -->
    <div
      class="absolute z-50 flex transition-all duration-300 bg-white shadow-lg top-16"
      :class="[isOpen ? 'left-0' : '-left-96']"
      style="height: calc(100vh - 4rem)"
    >
      <div class="relative flex flex-col w-96">
        <div class="flex flex-col h-full p-4 gap-y-6">
          <!-- 검색 영역 -->
          <div class="relative">
            <AttractionSearchInput
              v-model:keyword="searchParams.keyword"
              styleClass="border-2 border-green-500 shadow-sm rounded-md focus-visible:ring-0"
              @search="handleSearch(searchParams)"
            />
          </div>

          <!-- 필터 영역 -->
          <div class="p-4 rounded-lg bg-gray-50">
            <h2 class="mb-3 text-lg font-semibold text-gray-800">필터</h2>
            <div class="w-full h-40 rounded-md bg-gray-100/50"></div>
          </div>

          <!-- 검색 결과 -->
          <div v-if="attractionList.length > 0" class="flex-1 overflow-auto">
            <div class="flex items-center justify-between mb-3">
              <h2 class="text-lg font-semibold text-gray-800">검색 결과</h2>
              <span class="text-sm text-gray-500"
                >{{ attractionList.length }}개의 장소</span
              >
            </div>
            <AttractionList
              v-model:attractionList="attractionList"
              v-model:isLoading="isLoading"
            />
          </div>

          <!-- 검색 결과가 없을 때 -->
          <div
            v-else-if="!isLoading"
            class="flex items-center justify-center flex-1"
          >
            <p class="text-gray-500">검색 결과가 없습니다</p>
          </div>
        </div>

        <!-- 토글 버튼 개선 -->
        <Button
          variant="outline"
          @click="handleToggle"
          class="absolute w-6 h-12 -translate-y-1/2 bg-white border-gray-200 -right-6 top-1/2 hover:bg-gray-50"
        >
          <ChevronLeftIcon v-if="isOpen" class="w-4 h-4 text-gray-500" />
          <ChevronRightIcon v-else class="w-4 h-4 text-gray-500" />
        </Button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 스크롤바 스타일링 */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #666;
}
</style>
