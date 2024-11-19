<script setup>
import { onMounted, ref, watch } from 'vue'
import defaultImage from '@/assets/default_image_xl.png'
import attractionAPI from '@/api/attractions'
import Map from '../common/Map.vue'
import AttractionReview from './AttractionReview.vue'
import { Button } from '../ui/button'

const props = defineProps({
  tripId: {
    type: Number,
  },
})

onMounted(() => {
  attractionAPI.getAttractionDetail(props.tripId, data => {
    console.log(data)
    data.value = data
  })
})

const mapData = ref({
  lat: 33.450701,
  lng: 126.570667,
})

const data = ref({
  id: 26183,
  title: '괴산고추축제',
  sidoCode: 33,
  sidoName: '충청북도',
  gugunCode: 1,
  gugunName: '괴산군',
  image: 'http://tong.visitkorea.or.kr/cms/resource/77/2997377_image2_1.png',
  likeCount: 0,
})

watch(data.value, () => {
  mapData.value = {
    lat: data.value.lat,
    lng: data.value.lng,
  }
})
</script>

<template>
  <div
    class="flex flex-col items-center justify-start w-screen h-full mx-auto min-w-80"
  >
    <!-- header -->
    <header class="w-full py-4">
      <div class="flex items-center justify-start mx-auto max-w-7xl">
        <Button
          class="px-3 py-1 text-lg text-black transition-all bg-white rounded-lg shadow-none hover:bg-gray-200"
          @click="$router.go(-1)"
          ><i class="pi pi-angle-left"></i
          ><span>목록으로 돌아가기</span></Button
        >
      </div>
    </header>
    <!-- content -->
    <Main class="flex flex-col w-full max-w-4xl gap-6 pb-6">
      <!-- title -->
      <!-- 날짜 & 태그 -->
      <!-- 제목 & 내용 -->
      <div class="flex flex-col items-start justify-between w-full gap-3">
        <!-- 지역 -->
        <div class="flex items-center gap-2 text-lg text-gray-600">
          <i class="pi pi-map-marker"></i>
          <span>{{ data.sidoName }}</span>
          <span>{{ data.gugunName }}</span>
        </div>
        <!-- title -->
        <div class="flex items-baseline justify-between w-full gap-3">
          <h1 class="text-3xl font-bold">{{ data.title }}</h1>
          <div class="flex items-center justify-end gap-3">
            <span>좋아요</span>
            <i
              class="overflow-hidden text-red-500 cursor-pointer pi pi-heart hover:font-bold"
            >
            </i>
            <span>{{ data.likeCount }}</span>
          </div>
        </div>
        <!-- 좋아요 -->
      </div>
      <!-- 이미지 -->
      <div class="w-full overflow-hidden rounded-2xl h-96">
        <img
          class="object-cover w-full h-full"
          :src="data.image || defaultImage"
          alt="attraction image"
        />
      </div>
      <!-- 설명 -->
      <div class="flex flex-col items-start justify-between w-full gap-3">
        <div class="flex items-center gap-2 text-lg text-gray-600">
          <i class="pi pi-info"></i>
          <span>설명</span>
        </div>
        <div class="flex items-start justify-between w-full gap-3">
          <span>{{ data.description }}</span>
        </div>
      </div>
      <!-- 지도 -->
      <div class="flex flex-col w-full gap-3 overflow-hidden rounded-2xl">
        <h1 class="text-2xl font-bold">위치</h1>
        <Map :map-Data="mapData" />
      </div>
      <!-- 리뷰 -->
      <div class="flex flex-col w-full gap-3 overflow-hidden">
        <h1 class="text-2xl font-bold">리뷰</h1>
        <AttractionReview />
      </div>
    </Main>
  </div>
</template>

<style scoped></style>
