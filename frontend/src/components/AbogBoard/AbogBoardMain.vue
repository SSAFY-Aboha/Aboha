<script setup>
import AbogBoardList from '@/components/AbogBoard/AbogBoardList.vue'
import AbogBoardNav from '@/components/AbogBoard/AbogBoardNav.vue'
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

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
      <!-- header -->
      <div class="flex flex-col gap-3 mx-auto my-0 max-w-7xl md:px-8">
        <h1 class="max-w-6xl text-3xl">아보하 Log</h1>
        <!-- tag -->
        <AbogBoardNav />
      </div>
    </div>

    <div class="mx-auto my-0 mb-3 max-w-7xl md:px-8">
      <AbogBoardList />
    </div>
    <div class="w-full bg-[#eee] h-52"></div>
  </main>
</template>

<style scoped></style>
