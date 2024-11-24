<script setup>
import AbogBoardList from '@/components/AbogBoard/AbogBoardList.vue'
import { ref, watch } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { Button } from '../ui/button'
import abog from '@/api/abog'

const route = useRoute()

watch(
  () => route.query,
  query => {
    console.log(query)
  },
)

/**
 * @data {Array} boardList
 * { abog: {}, attraction: {}, user: {} }
 */
</script>

<template>
  <main class="flex flex-col h-full">
    <div
      class="sticky top-0 z-10 flex items-center justify-around py-3 bg-white shadow-sm"
    >
      <div class="">
        <h1 class="text-3xl font-bold font-Namum">아보하 Log</h1>
        <p class="pt-2 italic text-gray-500 text-md">
          여행 후기를 공유하고 소통해보세요!
        </p>
      </div>
      <!-- 글 작성하기 버튼 -->
      <div class="w-40 bottom-10 right-16">
        <RouterLink :to="{ name: 'abog-edit' }">
          <Button
            class="w-full font-bold text-black transition-all bg-green-300 border duration-100000 hover:bg-green-200 hover:scale-125 animate-bounce"
            >글 작성하러 가기</Button
          >
        </RouterLink>
      </div>
    </div>

    <div
      class="flex items-center justify-center w-full mb-3 bg-gray-100 md:px-8"
    >
      <AbogBoardList />
    </div>
    <div class="w-full bg-[#eee] h-52"></div>
  </main>
</template>

<style scoped></style>
