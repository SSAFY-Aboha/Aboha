<script setup>
import { onMounted, ref, watch } from 'vue'
import defaultImage from '@/assets/default_image_xl.png'
import attractionAPI from '@/api/attractions'
import Map from '../common/Map.vue'
import AttractionReview from './AttractionReview.vue'
import { Button } from '../ui/button'
import { Separator } from '@/components/ui/separator'
import { Badge } from '@/components/ui/badge'
import AttractionItem from '@/components/Attractions/AttractionItem.vue'

const props = defineProps({
  tripId: {
    type: String,
  },
})

onMounted(() => {
  console.log(props.tripId)
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
  viewCount: 0,
  description:
    '어쩌구어쩌구어쩌구어쩌구어쩌구어쩌구어쩌구어쩌구어쩌구어쩌구어쩌구ㄴ 저쩌구',
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
    class="flex flex-col items-center justify-start w-screen h-full gap-4 mx-auto min-w-80"
  >
    <!-- header -->
    <header class="w-full py-4">
      <div class="flex items-center justify-start max-w-6xl mx-auto px-7">
        <Button
          class="px-3 py-1 text-lg text-black transition-all bg-white rounded-lg shadow-none hover:bg-gray-200"
          @click="$router.go(-1)"
          ><i class="pi pi-angle-left"></i
          ><span>목록으로 돌아가기</span></Button
        >
      </div>
    </header>
    <!-- content -->
    <Main class="flex flex-col w-full max-w-6xl gap-8 pb-6">
      <div class="flex flex-col gap-4">
        <div class="flex items-baseline justify-between w-full gap-3">
          <div class="flex gap-4">
            <h1 class="text-3xl font-bold">{{ data.title }}</h1>
            <!-- 지역 -->
            <div class="flex items-center gap-2 text-lg text-gray-600">
              <i class="pi pi-map-marker"></i>
              <span>{{ data.sidoName }}</span>
              <span>{{ data.gugunName }}</span>
            </div>
          </div>
          <div class="flex items-center justify-end gap-4 text-xl">
            <div class="flex items-center justify-end gap-2">
              <i
                class="overflow-hidden cursor-pointer pi pi-eye hover:font-bold"
              >
              </i>
              <span>{{ data.viewCount }}</span>
            </div>

            <div class="flex items-center justify-end gap-2">
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
          <div class="flex items-start justify-between w-full gap-3">
            <span class="text-2xl italic">{{ `"${data.description}"` }}</span>
          </div>
        </div>
        <!-- 관련 태그 -->
        <div class="flex flex-col items-start w-full gap-3">
          <span class="text-2xl font-bold">관련 태그</span>
          <Badge
            class="px-4 py-2 text-black bg-gray-200 text-md hover:bg-gray-300"
            >{{ `# ${data.tag}` }}</Badge
          >
        </div>
      </div>
      <Separator />
      <!--  -->
      <!-- 지도 -->
      <div class="flex flex-col w-full gap-4 overflow-hidden rounded-2xl">
        <h1 class="text-3xl font-bold">위치</h1>
        <Map :map-Data="mapData" />
      </div>
      <Separator />
      <!-- 리뷰 -->
      <div class="flex flex-col w-full gap-4 overflow-hidden">
        <h1 class="text-3xl font-bold">후기</h1>
        <AttractionReview />
      </div>
    </Main>

    <!-- footer -->
    <footer class="w-full max-w-6xl py-4">
      <div class="flex items-center justify-start max-w-6xl gap-4 mx-auto">
        <Button
          class="px-4 py-6 text-lg text-black bg-white border shadow-sm hover:bg-gray-200"
          >후기 {{ `123` }}개 모두 보기</Button
        >
        <Button
          class="px-4 py-6 text-lg text-black bg-white border shadow-sm hover:bg-gray-200"
          >후기 작성하기</Button
        >
      </div>
    </footer>

    <!-- 추가 정보 -->
    <div class="flex flex-col w-full max-w-6xl gap-4 py-4">
      <h1 class="text-3xl font-bold">함께 추천 하는 관광지</h1>
      <ul class="flex flex-wrap gap-4">
        <AttractionItem v-for="each in 6" :data="data" :key="each" />
      </ul>
    </div>
  </div>
</template>

<style scoped></style>
