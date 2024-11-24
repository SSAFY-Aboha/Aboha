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

onMounted(
  () => {
    getCurrentLocation()
  },
  {
    immediate: true,
  },
)

const onLoadKakaoMap = mapRef => {
  map.value = mapRef
}

const initialCenter = ref({
  lat: 0,
  lng: 0,
})

const isOpen = ref(true)

const attractionList = ref([]) // 관광지 리스트
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
  navigator.geolocation.getCurrentPosition(position => {
    const { latitude, longitude } = position.coords
    initialCenter.value = { lat: latitude, lng: longitude }
  })
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
    <!-- nav -->
    <div
      class="absolute right-0 z-40 flex items-center justify-start w-full h-16 px-4 py-3"
    >
      <div class="basis-[420px]"></div>
      <div class="flex gap-x-2">
        <Button
          @click="getCurrentLocation"
          variant="outline"
          class="border-green-500 rounded-full"
        >
          <CurrencyIcon class="w-4 h-4" />
        </Button>
        <Button
          variant="outline"
          class="border-green-500 rounded-full"
          @click="handleCurrentLocation"
        >
          <MapPinIcon class="w-4 h-4" />
          <span>현재 위치에서 검색</span>
        </Button>
      </div>
    </div>

    <!-- 지도 -->
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
    <!-- 컨텐츠 -->
    <div
      class="absolute top-0 z-50 flex h-full p-4 transition-all duration-300 bg-white shadow-md"
      :class="isOpen ? 'left-0' : '-left-96'"
    >
      <!-- 사이드바 -->
      <div class="relative flex flex-col w-full h-full basis-96">
        <div class="flex flex-col h-full w-96 gap-y-4">
          <div class="relative">
            <AttractionSearchInput
              @update:model-value="handleKeywordChange"
              :styleClass="{
                'border-green-500 border-2 focus-visible:ring-0': true,
              }"
            />
          </div>
          <!-- 중간 내용 -->
          <div class="w-full">
            <h1 class="text-xl font-bold">중간 내용</h1>
            <div class="w-full h-52 bg-slate-100"></div>
          </div>
          <!-- 검색 결과 -->
          <div
            v-if="attractionList.length > 0"
            class="w-full h-full overflow-hidden"
          >
            <h1 class="pb-2 text-xl font-bold">검색 결과</h1>
            <AttractionList
              v-model:attractionList="attractionList"
              v-model:isLoading="isLoading"
            />
          </div>
        </div>
        <!-- toggle 버튼 -->
        <Button
          variant="outline"
          @click="handleToggle"
          class="absolute w-2 h-10 -translate-y-1/2 -right-9 top-1/2"
        >
          <ChevronLeftIcon v-if="isOpen" class="w-4 h-4 text-gray-500" />
          <ChevronRightIcon v-else class="w-4 h-4 text-gray-500" />
        </Button>
      </div>

      <!-- 다른 영역 -->
      <!-- <div class="flex flex-col w-full h-full bg-red-500 basis-96 w-96">ㅇ</div> -->
    </div>
  </div>
</template>

<style scoped></style>
