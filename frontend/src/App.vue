<script setup>
import { RouterView } from 'vue-router'
import BaseHeader from '@/components/layout/BaseHeader.vue'
import BaseFooter from '@/components/layout/BaseFooter.vue'

import { Toast } from 'primevue'
</script>

<template>
  <!-- Toast -->
  <Toast position="center" />
  <div class="flex flex-col w-full h-svh">
    <!-- Header -->
    <BaseHeader />
    <main class="flex flex-col items-center justify-center flex-1 w-full">
      <RouterView />
    </main>
    <!-- Footer -->
    <BaseFooter />
  </div>
</template>

<style scoped></style>
