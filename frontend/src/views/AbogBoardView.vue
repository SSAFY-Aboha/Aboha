<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <div>
    <!-- 다른 컨텐츠들 -->
    <RouterView v-slot="{ Component }">
      <component :is="Component" />
    </RouterView>
  </div>
</template>

<style scoped></style>
