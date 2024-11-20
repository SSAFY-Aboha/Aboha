<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import defaultImage from '@/assets/default_image_xl.png'
import attractionAPI from '@/api/attractions'
import Map from '../common/Map.vue'
import AttractionReview from './AttractionReview/AttractionReview.vue'
import { Button } from '../ui/button'
import { Separator } from '@/components/ui/separator'
import { Badge } from '@/components/ui/badge'
import AttractionDetailSkeleton from './AttractionDetailSkeleton.vue'

const props = defineProps({
  tripId: {
    type: [String, Number],
  },
})

const isLoading = ref(false)

const attractionData = ref({
  id: null,
  title: '',
  sidoCode: null,
  sidoName: '',
  gugunCode: null,
  gugunName: '',
  contentTypeId: null,
  contentTypeName: '',
  image: '',
  address: '',
  latitude: null,
  longitude: null,
  description: '',
  likeCount: null,
  viewCount: null,
  reviews: {
    data: [{}],
    count: null,
  },
})

onMounted(async () => {
  isLoading.value = true
  try {
    const data = await attractionAPI.getAttractionDetail(props.tripId)
    attractionData.value = data
    console.log(data)
  } catch (err) {
    console.error('Error loading trip data:', err)
  } finally {
    isLoading.value = false
  }
})

const mapData = computed(() => {
  return {
    lat: attractionData.value.latitude,
    lng: attractionData.value.longitude,
  }
})

// 컴포넌트가 업데이트될 때도 데이터를 다시 가져오도록
watch(
  () => props.tripId,
  async newId => {
    try {
      isLoading.value = true
      const data = await attractionAPI.getAttractionDetail(newId)
      attractionData.value = data
    } catch (err) {
      console.error('Error loading trip data:', err)
    } finally {
      isLoading.value = false
    }
  },
)
</script>

<template>
  <div
    class="flex flex-col items-center justify-start h-full gap-4 mx-auto min-w-80"
  >
    <!-- header -->
    <header class="w-full py-4">
      <div
        class="flex items-center justify-start max-w-6xl min-w-[56rem] mx-auto px-7"
      >
        <Button
          class="px-3 py-1 text-lg text-black transition-all bg-white rounded-lg shadow-none hover:bg-gray-200"
          @click="$router.push('/trips')"
          ><i class="pi pi-angle-left"></i
          ><span>목록으로 돌아가기</span></Button
        >
      </div>
    </header>
    <!-- content -->
    <AttractionDetailSkeleton v-if="isLoading" />
    <Main v-if="!isLoading" class="flex flex-col w-full max-w-4xl gap-8 pb-6">
      <div class="flex flex-col gap-4">
        <div class="flex items-baseline justify-between gap-3">
          <div class="flex gap-4">
            <h1 class="text-3xl font-bold">{{ attractionData.title }}</h1>
            <!-- 지역 -->
            <div class="flex items-center gap-2 text-lg text-gray-600">
              <i class="pi pi-map-marker"></i>
              <span>{{ attractionData.sidoName }}</span>
              <span>{{ attractionData.gugunName }}</span>
            </div>
          </div>
          <div class="flex items-center justify-end gap-4 text-xl">
            <div class="flex items-center justify-end gap-2">
              <i
                class="overflow-hidden cursor-pointer pi pi-eye hover:font-bold"
              >
              </i>
              <span>{{ attractionData.viewCount }}</span>
            </div>

            <div class="flex items-center justify-end gap-2">
              <i
                class="overflow-hidden text-red-500 cursor-pointer pi pi-heart hover:font-bold"
              >
              </i>
              <span>{{ attractionData.likeCount }}</span>
            </div>
          </div>
          <!-- 좋아요 -->
        </div>
        <!-- 이미지 -->
        <div class="w-full overflow-hidden rounded-2xl h-96">
          <img
            class="object-cover w-full h-full"
            :src="attractionData.image || defaultImage"
            alt="attraction image"
          />
        </div>
        <!-- 설명 -->
        <div class="flex flex-col items-start justify-between w-full gap-3">
          <div class="flex items-start justify-between w-full gap-3">
            <span class="text-lg italic text-gray-600">{{
              `"${attractionData.description}"`
            }}</span>
          </div>
        </div>
        <!-- 관련 태그 -->
        <div class="flex flex-col items-start w-full gap-3">
          <span class="text-2xl font-bold">관련 태그</span>
          <Badge
            class="px-4 py-2 text-black bg-gray-200 text-md hover:bg-gray-300"
            >{{ `# ${attractionData.tag}` }}</Badge
          >
        </div>
      </div>
      <Separator />
      <!--  -->
      <!-- 지도 -->
      <div class="flex flex-col w-full gap-4 overflow-hidden rounded-2xl">
        <h1 class="text-3xl font-bold">위치</h1>
        <p class="text-lg text-gray-600">{{ attractionData.address }}</p>
        <Map :map-Data="mapData" />
      </div>
      <Separator />
      <!-- 리뷰 -->
      <div class="flex flex-col w-full gap-4 overflow-hidden">
        <AttractionReview />
      </div>
    </Main>

    <!-- 추가 정보 -->
    <!-- <div class="flex flex-col w-full max-w-4xl gap-4 py-4">
      <h1 class="text-3xl font-bold">함께 추천 하는 관광지</h1>
      <ul class="flex flex-wrap gap-4">
        <AttractionItem v-for="each in 6" :data="data" :key="each" />
      </ul>
    </div> -->
  </div>
</template>

<style scoped></style>
