<script setup>
import AbogBoardList from '@/components/AbogBoard/AbogBoardList.vue'
import { watch } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { Button } from '../ui/button'

const route = useRoute()

watch(
  () => route.query,
  query => {
    console.log(query)
  },
)
</script>

<template>
  <main class="flex flex-col h-full gap-4">
    <div class="sticky top-0 z-20 py-4 bg-white shadow-sm">
      <h1>아보하 Log</h1>
    </div>

    <div class="mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <AbogBoardList />
    </div>
    <div class="w-full bg-[#eee] h-52"></div>
  </main>

  <!-- 글 작성하기 버튼 -->
  <div class="fixed w-40 bottom-10 right-16">
    <RouterLink to="/abog-board/write">
      <Button
        class="w-full font-bold text-black transition-all bg-green-100 border duration-100000 hover:bg-green-200 hover:scale-125 animate-bounce"
        >글 작성하러 가기</Button
      >
    </RouterLink>
  </div>
</template>

<style scoped></style>
