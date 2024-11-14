<script setup>
import { RouterView } from 'vue-router'
import BaseHeader from '@/components/layout/BaseHeader.vue'
import BaseFooter from '@/components/layout/BaseFooter.vue'
</script>

<template>
  <!-- Toast -->
  <!-- <Toast position="center" /> -->
  <div class="flex flex-col w-full h-svh">
    <!-- Header -->
    <BaseHeader />
    <main class="flex-1 w-full will-change-auto">
      <RouterView v-slot="{ Component }">
        <Transition name="fade" mode="out-in" appear>
          <component :is="Component" :key="$route.path" />
        </Transition>
      </RouterView>
    </main>
    <!-- Footer -->
    <BaseFooter />
  </div>
</template>

<style scoped>
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 150ms ease-in;
}
</style>
