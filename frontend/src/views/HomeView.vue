<script setup>
import MainIntroduction from '@/components/MainIntroduction.vue'
import SpecsIntroduction from '@/components/SpecsIntroduction.vue'
</script>

<template>
  <section
    class="flex flex-col w-full items-center justify-center h-[calc(100vh-7rem)]"
  >
    <MainIntroduction />
  </section>

  <!-- 아보아 스펙 컨텐츠 -->
  <section class="flex flex-col w-full items-center justify-center bg-[#eee]">
    <SpecsIntroduction />
  </section>

  <!-- 여행지 컨텐츠 -->
  <section class="flex flex-col items-center justify-center w-full">
    <SpecsIntroduction />
  </section>
</template>
