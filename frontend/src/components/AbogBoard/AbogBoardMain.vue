<script setup>
import AbogBoardList from '@/components/AbogBoard/AbogBoardList.vue'
import { ref, watch } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { Button } from '../ui/button'

const route = useRoute()

watch(
  () => route.query,
  query => {
    console.log(query)
  },
)

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
])
</script>

<template>
  <main class="flex flex-col h-full gap-4">
    <div class="py-4 bg-white shadow-sm">
      <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-bold font-Namum">아보하 Log</h1>
        <p class="pt-2 italic text-gray-500 text-md">
          여행 후기를 공유하고 소통해보세요!
        </p>
      </div>
    </div>

    <div class="mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <AbogBoardList v-model:boardList="boardList" />
    </div>
    <div class="w-full bg-[#eee] h-52"></div>
  </main>

  <!-- 글 작성하기 버튼 -->
  <div class="fixed w-40 bottom-10 right-16">
    <RouterLink :to="{ name: 'abog-edit' }">
      <Button
        class="w-full font-bold text-black transition-all bg-green-100 border duration-100000 hover:bg-green-200 hover:scale-125 animate-bounce"
        >글 작성하러 가기</Button
      >
    </RouterLink>
  </div>
</template>

<style scoped></style>
