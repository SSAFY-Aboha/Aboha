<script setup>
import MainIntroduction from '@/components/MainIntroduction.vue'
import SpecsIntroduction from '@/components/SpecsIntroduction.vue'
import SuggestTrip from '@/components/SuggestTrip.vue'
</script>

<template>
  <section
    class="flex flex-col w-full items-center justify-center h-[calc(100vh-7rem)]"
  >
    <MainIntroduction />
  </section>

  <!-- 아보아 스펙 컨텐츠 -->
  <section
    class="flex flex-col items-center justify-center w-full py-6 bg-[#eee]"
  >
    <SpecsIntroduction />
  </section>

  <!-- 여행지 컨텐츠 -->
  <section class="flex flex-col items-center justify-center w-full py-6">
    <SuggestTrip />
  </section>

  <!-- 여행지 컨텐츠 -->
  <section class="flex flex-col items-center justify-center w-full py-6 h-96">
    <!-- dfs -->
    <div
      class="flex flex-col items-center justify-center gap-8 py-12 size-full"
    >
      <div class="flex flex-col items-center gap-2">
        <h1 class="text-3xl font-bold font-Namum">어떤 내용을 채울까</h1>
      </div>
    </div>
  </section>
</template>
