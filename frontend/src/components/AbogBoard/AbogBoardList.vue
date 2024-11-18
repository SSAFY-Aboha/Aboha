<script setup>
import { ref } from 'vue'
import AbogBoardItem from './AbogBoardItem.vue'

const boardList = ref([
  {
    id: 1,
    nickname: 'nickname',
    title: '여기 다녀왔어요!',
    date: '2021. 10. 10',
    content:
      '어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구',
    createdAt: '2021-10-10',
    updatedAt: '2021-10-10',
    tags: ['tag1', 'tag2', 'tag3'],
    image: '/src/assets/mainPage_image.jpg',
    like: 10,
    comment: 10,
    view: 10,
  },
  {
    id: 1,
    nickname: 'nickname',
    title: '여기 다녀왔어요!',
    date: '2021. 10. 10',
    content:
      '어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구',
    createdAt: '2021-10-10',
    updatedAt: '2021-10-10',
    tags: ['tag1', 'tag2', 'tag3'],
    image: '/src/assets/mainPage_image.jpg',
    like: 10,
    comment: 10,
    view: 10,
  },
])
</script>

<template>
  <div class="w-full h-full">
    <ul class="flex flex-col gap-6">
      <AbogBoardItem v-for="each in boardList" :data="each" :key="each.id" />
    </ul>
  </div>
</template>

<style scoped></style>
