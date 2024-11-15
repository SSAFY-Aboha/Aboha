<script setup>
import Button from '@/components/ui/button/Button.vue'
</script>

<template>
  <div class="flex flex-col items-center w-full gap-4">
    <Button
      class="w-full text-black bg-white hover:bg-gray-100"
      @click="() => console.log('구글 로그인')"
      >구글 로그인</Button
    >
    <Button
      class="w-full bg-[#ffeb00] text-black hover:bg-[#fcd200]"
      @click="() => console.log('카카오 로그인')"
      >카카오 로그인</Button
    >
  </div>
</template>

<style lang="scss" scoped></style>
