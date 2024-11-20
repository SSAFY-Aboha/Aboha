<script setup>
import Button from '@/components/ui/button/Button.vue'
import AttractionReviewList from './AttractionReviewList.vue'
import { onMounted, ref } from 'vue'
import AttractionReviewEditor from '@/components/Attractions/AttractionReview/AttractionReviewEditor.vue'
// import attractionAPI from '@/api/attraction'

const limit = ref(4)

const props = defineProps({
  attractionId: [Number, String],
  // reviews: Array,
})

const reviews = ref([
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content:
      '재밌게재밌게재밌게재밌게재밌게재밌게재밌게재재밌게재밌게재재밌게재밌게재재밌게재밌게재재밌게재밌게재재밌게재밌게재재밌게재밌게재재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게재밌게',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
  {
    id: 1,
    nickname: '닉네임',
    rating: 3,
    content: '재밌게 놀다 갑니다~',
    createdAt: '2024-01-01',
    profileImage: '',
  },
])

// review 데이터 받아오기
// onMounted(() => {
//   attractionAPI.getAttractionReview(props.attractionId, () => {})
// })
</script>
<template>
  <div class="flex items-center justify-between w-full">
    <h1 class="text-3xl font-bold">후기</h1>
    <AttractionReviewEditor v-model:reviews="reviews" />
  </div>
  <section class="flex flex-col w-full gap-3 rounded-xl">
    <!-- 전체 리뷰 평점 -->
    <div class="flex items-center gap-2">
      <span class="text-lg font-bold">전체 리뷰 평점</span>
      <span class="text-lg font-bold">4.5 / 5.0</span>
    </div>
    <AttractionReviewList :reviews="reviews" :limit="limit" />
  </section>
  <!-- 버튼 -->
  <div class="flex items-center justify-start w-full gap-4 mx-auto">
    <Button
      v-if="limit < reviews.length"
      @click="limit = reviews.length"
      class="px-4 py-6 text-lg text-black bg-white border shadow-sm hover:bg-gray-200"
      >후기 {{ reviews.length }}개 모두 보기</Button
    >
    <Button
      v-if="limit === reviews.length"
      @click="limit = 4"
      class="px-4 py-6 text-lg text-black bg-white border shadow-sm hover:bg-gray-200"
      >후기 축소하기</Button
    >
  </div>
</template>

<style scoped></style>
