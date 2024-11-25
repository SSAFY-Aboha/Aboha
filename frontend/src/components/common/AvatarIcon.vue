<script setup>
import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar'
import { UserIcon } from 'lucide-vue-next'

const BASE_URL = import.meta.env.VITE_API_BASE_URL

defineProps({
  src: {
    type: String,
  },
  customClass: {
    type: String,
  },
})
</script>

<template>
  <Avatar class="size-10">
    <AvatarImage
      :src="`${BASE_URL}${src}` || `/src/assets/mainPage_image.jpg`"
      :class="customClass"
      alt="avatar"
    />
    <AvatarFallback>
      <UserIcon class="size-6" />
    </AvatarFallback>
  </Avatar>
</template>
