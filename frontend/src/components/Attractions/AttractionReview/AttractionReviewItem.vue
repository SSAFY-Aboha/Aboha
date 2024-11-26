<script setup>
import AvatarIcon from '@/components/common/AvatarIcon.vue'
import RatingStar from '@/components/common/RatingStar.vue'

const props = defineProps({
  review: {
    type: Object,
    required: true,
  },
})

console.log(props.review.user.profileImageUrl)
</script>

<template>
  <li
    class="flex flex-col items-center justify-start w-3/4 gap-2 max-h-48 rounded-xl"
  >
    <!-- 유저 정보 -->
    <div class="flex items-center w-full gap-2">
      <AvatarIcon :src="review.user?.profileImageUrl ?? defaultProfile" />
      <span class="text-md">{{ review.user?.nickname }}</span>
    </div>
    <div class="flex flex-col w-full gap-2">
      <!-- 별점 -->
      <div class="flex items-center gap-1 text-lg">
        <RatingStar :rating="review.review.rating" />
        <span class="pl-2 text-sm">{{ review.review.createdAt }}</span>
      </div>
      <!-- 리뷰 내용 -->
      <p class="text-md line-clamp-2">{{ review.review.content }}</p>
    </div>
  </li>
</template>

<style lang="scss" scoped></style>
