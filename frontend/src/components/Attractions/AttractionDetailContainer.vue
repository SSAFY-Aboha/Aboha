<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import attractionAPI from '@/api/attractions'
import { Button } from '../ui/button'
import AttractionDetailSkeleton from './AttractionDetailSkeleton.vue'
import AttractionDetail from '@/components/Attractions/AttractionDetail.vue'

const props = defineProps({
  tripId: {
    type: [String, Number],
  },
})

const isLoading = ref(false)

const attraction = ref({
  id: 1,
  title: '경복궁',
  sidoCode: 1,
  sidoName: '서울특별시',
  gugunCode: 1,
  gugunName: '종로구',
  contentTypeId: 12,
  contentTypeName: '관광지',
  image: 'https://www.royalpalace.go.kr/images/sub/gbg.jpg',
  address: '서울특별시 종로구 사직로 161',
  latitude: 37.579617,
  longitude: 126.977041,
  description:
    '조선왕조 제일의 법궁인 경복궁은 1395년 태조 이성계가 창건하였다. 경복궁은 조선왕조의 법궁으로서 500년 조선왕조의 중심이었다.',
  likeCount: 1234,
  viewCount: 5678,
  reviews: {
    content: [
      {
        id: 1,
        nickname: '여행자1',
        rating: 5,
        content:
          '정말 아름다운 궁궐이에요. 한복 입고 방문하니 더욱 운치있었습니다.',
        createdAt: '2024-01-15',
        profileImage: '',
      },
      {
        id: 2,
        nickname: '역사탐방러',
        rating: 4,
        content: '해설사와 함께하는 투어가 매우 유익했어요.',
        createdAt: '2024-01-14',
        profileImage: '',
      },
    ],
    count: 2,
  },
})

// onMounted(async () => {
//   isLoading.value = true
//   try {
//     const data = await attractionAPI.getAttractionDetail(props.tripId)
//     attraction.value = data
//     console.log(data)
//   } catch (err) {
//     console.error('Error loading trip data:', err)
//   } finally {
//     isLoading.value = false
//   }
// })

// 컴포넌트가 업데이트될 때도 데이터를 다시 가져오도록
// watch(
//   () => props.tripId,
//   async newId => {
//     try {
//       isLoading.value = true
//       const data = await attractionAPI.getAttractionDetail(newId)
//       attraction.value = data
//     } catch (err) {
//       console.error('Error loading trip data:', err)
//     } finally {
//       isLoading.value = false
//     }
//   },
// )
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
    <AttractionDetail v-if="!isLoading" :attraction="attraction" />
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
