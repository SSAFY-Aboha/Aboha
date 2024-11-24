<script setup>
import { Button } from '@/components/ui/button'
import { ListIcon, MapIcon } from 'lucide-vue-next'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const handleViewMode = async () => {
  try {
    if (route.name === 'trips-list') {
      await router.push({ name: 'trips-map' })
    } else {
      await router.push({ name: 'trips-list' })
    }
  } catch (error) {
    console.error('Navigation error:', error)
  }
}
</script>

<template>
  <div class="relative min-h-screen">
    <router-view v-slot="{ Component }">
      <transition name="slide" mode="out-in">
        <component :is="Component" :key="route.name" />
      </transition>
    </router-view>

    <div
      class="fixed z-50 flex justify-center w-full bottom-16 hover:cursor-pointer"
    >
      <Button
        class="px-4 py-2 transition rounded-full shadow-lg text-md hover:scale-110"
        @click="handleViewMode"
      >
        <MapIcon v-if="route.name === 'trips-list'" class="w-4 h-4 mr-2" />
        <ListIcon v-else class="w-4 h-4 mr-2" />
        {{ route.name === 'trips-list' ? '지도에서 보기' : '목록으로 보기' }}
      </Button>
    </div>
  </div>
</template>

<style scoped>
.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease-out;
}

.slide-enter-from {
  opacity: 0;
  transform: translateY(-30px);
}

.slide-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>
