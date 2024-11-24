<template>
  <div class="fixed z-50 space-y-2 bottom-4 right-4">
    <TransitionGroup name="toast">
      <div
        v-for="toast in toasts"
        :key="toast.id"
        :class="[
          'px-4 py-2 rounded-lg shadow-lg text-white transform transition-all duration-300',
          {
            'bg-red-500': toast.type === 'error',
            'bg-green-500': toast.type === 'success',
            'bg-blue-500': toast.type === 'info',
            'bg-yellow-500': toast.type === 'warning',
            'bg-gray-700': toast.type === 'default',
          },
        ]"
      >
        {{ toast.message }}
      </div>
    </TransitionGroup>
  </div>
</template>

<script setup>
import { useToast } from '@/composables/useToast'

const { toasts } = useToast()
</script>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>
