<script setup>
import defaultImage from '@/assets/default_image_xl.png'
import Map from '../common/Map.vue'
import AttractionReview from './AttractionReview/AttractionReview.vue'
import { Separator } from '@/components/ui/separator'
import { Badge } from '@/components/ui/badge'
import { computed, ref } from 'vue'
import attractionAPI from '@/api/attractions'
const props = defineProps({
  attraction: Object,
})

const likeCount = defineModel('likeCount')

const isLiked = ref(props.attraction.isLiked)

const mapData = computed(() => {
  return {
    lat: props.attraction.latitude,
    lng: props.attraction.longitude,
  }
})

// const likeCount = computed(() => {
//   return props.attraction.likeCount
// })

const handleLike = async () => {
  const { data, status, error } = await attractionAPI.toggleAttractionLike(
    props.attraction.id,
  )

  if (error) {
    alert(error)
    return
  }
  isLiked.value = !isLiked.value
  isLiked.value = data.isLiked
  likeCount.value += data.isLiked ? 1 : -1
}
</script>

<template>
  <main class="flex flex-col w-full max-w-4xl gap-8 pb-6">
    <div class="flex flex-col gap-4">
      <div class="flex items-baseline justify-between gap-3">
        <div class="flex gap-4">
          <h1 class="text-3xl font-bold">{{ attraction.title }}</h1>
          <!-- 지역 -->
          <div class="flex items-center gap-2 text-lg text-gray-600">
            <i class="pi pi-map-marker"></i>
            <span>{{ attraction.sidoName }}</span>
            <span>{{ attraction.gugunName }}</span>
          </div>
        </div>
        <div class="flex items-center justify-end gap-4 text-xl">
          <div class="flex items-center justify-end gap-2">
            <i class="overflow-hidden cursor-pointer pi pi-eye hover:font-bold">
            </i>
            <span>{{ attraction.viewCount }}</span>
          </div>

          <div class="flex items-center justify-end gap-2">
            <i
              @click="handleLike"
              :class="`overflow-hidden text-red-500 cursor-pointer pi ${isLiked ? 'pi-heart-fill' : 'pi-heart'} hover:font-bold`"
            >
            </i>
            <span>{{ likeCount }}</span>
          </div>
        </div>
        <!-- 좋아요 -->
      </div>
      <!-- 이미지 -->
      <div class="w-full overflow-hidden rounded-2xl h-96">
        <img
          class="object-cover w-full h-full"
          :src="attraction.image || defaultImage"
          alt="attraction image"
        />
      </div>
      <!-- 설명 -->
      <div class="flex flex-col items-start justify-between w-full gap-3">
        <div class="flex items-start justify-between w-full gap-3">
          <span class="text-lg italic text-gray-600">{{
            `"${attraction.description}"`
          }}</span>
        </div>
      </div>
      <!-- 관련 태그 -->
      <div class="flex flex-col items-start w-full gap-3">
        <span class="text-2xl font-bold">관련 태그</span>
        <div class="flex items-center justify-start gap-4">
          <Badge
            v-for="tag in attraction.tags"
            :key="tag"
            class="px-4 py-2 text-black bg-gray-200 text-md hover:bg-gray-300"
            >{{ `# ${tag}` }}</Badge
          >
        </div>
      </div>
    </div>
    <Separator />
    <!--  -->
    <!-- 지도 -->
    <div class="flex flex-col w-full gap-4 overflow-hidden rounded-2xl">
      <h1 class="text-3xl font-bold">위치</h1>
      <p class="text-lg text-gray-600">{{ attraction.address }}</p>
      <Map :map-Data="mapData" />
    </div>
    <Separator />
    <!-- 리뷰 -->
    <div class="flex flex-col w-full gap-4 overflow-hidden">
      <AttractionReview :reviews="attraction.reviews" />
    </div>
  </main>
</template>

<style scoped></style>
