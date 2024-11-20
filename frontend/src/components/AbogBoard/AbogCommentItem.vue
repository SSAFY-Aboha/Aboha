<script setup>
import { Avatar, AvatarImage } from '@/components/ui/avatar'

defineProps({
  data: {
    type: Object,
    required: true,
  },
})

// {
//     id: 1,
//     profileImage: '/src/assets/mainPage_image.jpg',
//     nickname: '닉네임',
//     content: '댓글 내용',
//     date: '2024-01-01',
//   },
</script>

<template>
  <div class="flex items-center gap-3 pb-3 border-b">
    <Avatar class="size-8">
      <AvatarImage src="/src/assets/mainPage_image.jpg" alt="avatar" />
    </Avatar>
    <div>
      <span class="pr-2 font-bold">{{ data.nickname }}</span>
      <span>{{ data.content }}</span>
    </div>
  </div>
</template>

<style scoped></style>
