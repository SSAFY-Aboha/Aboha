<script setup>
import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar'
import { UserIcon } from 'lucide-vue-next'
import defaultProfile from '@/assets/default_profile.png'

defineProps({
  src: {
    type: String,
    default: defaultProfile,
  },
  customClass: {
    type: String,
  },
})
</script>

<template>
  <Avatar class="size-10">
    <AvatarImage :src="src" :class="customClass" alt="avatar" />
    <AvatarFallback>
      <UserIcon class="size-6" />
    </AvatarFallback>
  </Avatar>
</template>
