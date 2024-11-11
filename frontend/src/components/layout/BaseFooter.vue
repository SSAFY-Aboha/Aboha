<script setup>
import { Button } from 'primevue'
</script>

<template>
  <footer
    class="flex items-center justify-between w-full px-6 py-8 bg-gray-100"
  >
    <div>
      <span>ⓒ 2024 Aboha, Inc.</span>
    </div>
    <Button class="hover:text-black" to="/mypage">
      개발자에게 커피 사주기
    </Button>
    <div>
      <span>SSAFY 12th</span>
    </div>
  </footer>
</template>

<style scoped></style>
