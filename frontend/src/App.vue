<script setup>
import { onMounted } from 'vue'
import { RouterView } from 'vue-router'
import BaseHeader from '@/components/layout/BaseHeader.vue'
import BaseFooter from '@/components/layout/BaseFooter.vue'
import useUserStore from '@/stores/user'

const userStore = useUserStore()
// cookie에서 JSESSIONID 확인
onMounted(() => {
  // userStore.initializeAuth()
})
</script>

<template>
  <!-- Toast -->
  <!-- <Toast position="center" /> -->
  <div class="flex flex-col w-full h-svh">
    <!-- Header -->
    <BaseHeader v-if="!$route.meta.hideLayout" />
    <main class="flex-1 w-full will-change-auto">
      <RouterView v-slot="{ Component }">
        <Transition
          name="fade"
          mode="out-in"
          appear
          v-if="!$route.meta.transition"
        >
          <KeepAlive v-if="$route.meta.keepAlive">
            <component :is="Component" :key="$route.path" />
          </KeepAlive>
          <component v-else :is="Component" :key="$route.path" />
        </Transition>
      </RouterView>
    </main>
    <!-- Footer -->
    <BaseFooter v-if="!$route.meta.hideLayout" />
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
