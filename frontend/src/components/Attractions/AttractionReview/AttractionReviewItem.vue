<script setup>
import RatingStar from '@/components/common/RatingStar.vue'
import { Avatar, AvatarImage } from '@/components/ui/avatar'

defineProps({
  review: {
    type: Object,
    required: true,
  },
})
</script>

<template>
  <li
    class="flex flex-col items-center justify-start w-3/4 gap-2 max-h-48 rounded-xl"
  >
    <!-- 유저 정보 -->
    <div class="flex items-center w-full gap-2">
      <Avatar class="size-8">
        <AvatarImage src="/src/assets/mainPage_image.jpg" alt="avatar" />
      </Avatar>
      <span class="text-md">{{ review.nickname }}</span>
    </div>
    <div class="flex flex-col w-full gap-2">
      <!-- 별점 -->
      <div class="flex items-center gap-1 text-lg">
        <RatingStar :rating="review.rating" />
        <span class="pl-2 text-sm">{{ review.createdAt }}</span>
      </div>
      <!-- 리뷰 내용 -->
      <p class="text-md line-clamp-2">{{ review.content }}</p>
    </div>
  </li>
</template>

<style lang="scss" scoped></style>
