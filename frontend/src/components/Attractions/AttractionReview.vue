<script setup>
import { ref } from 'vue'

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
</script>
<template>
  <section class="flex flex-col w-full gap-3 rounded-xl">
    <!-- 전체 리뷰 평점 -->
    <div class="flex items-center gap-2">
      <span class="text-lg font-bold">전체 리뷰 평점</span>
      <span class="text-lg font-bold">4.5 / 5.0</span>
    </div>
    <ul class="grid grid-cols-2 gap-x-3 gap-y-10 justify-items-start">
      <li
        v-for="review in reviews"
        :key="review.id"
        class="flex flex-col items-center justify-start w-3/4 gap-2 max-h-48 rounded-xl"
      >
        <div class="flex items-start w-full gap-2">
          <div class="size-9 rounded-3xl bg-sky-500">
            <!-- <img :src="review.profileImage" alt="프로필" /> -->
          </div>
          <span class="text-xl">{{ review.nickname }}</span>
        </div>
        <div class="flex flex-col w-full gap-2">
          <!-- 별점 -->
          <div class="flex items-center gap-1 text-lg">
            <i
              v-for="star in review.rating"
              :key="star"
              class="pi pi-star-fill"
            ></i>
            <i
              v-for="star in 5 - review.rating"
              :key="star"
              class="pi pi-star"
            ></i>
            <span class="pl-2 text-sm">{{ review.createdAt }}</span>
          </div>
          <!-- 리뷰 내용 -->
          <p class="text-lg line-clamp-2">{{ review.content }}</p>
        </div>
      </li>
    </ul>
  </section>
</template>

<style scoped></style>
