<script setup>
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import { UserIcon } from 'lucide-vue-next'
import AvatarIcon from '../common/AvatarIcon.vue'

const BASE_URL = import.meta.env.VITE_API_BASE_URL

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
})

const emit = defineEmits(['handleDeleteComment'])

const { id, nickname, profileImageUrl } = props.data.user
const { content, createdAt } = props.data.comment
</script>

<template>
  <div class="flex items-center gap-3 pb-3 border-b">
    <AvatarIcon :src="profileImageUrl" />
    <div>
      <span class="pr-2 font-bold">{{ nickname }}</span>
      <span>{{ content }}</span>
    </div>
    <Button variant="outline" @click="emit('handleDeleteComment', id)">
      <i class="pi pi-trash hover:text-red-500"></i>
    </Button>
  </div>
</template>

<style scoped></style>
