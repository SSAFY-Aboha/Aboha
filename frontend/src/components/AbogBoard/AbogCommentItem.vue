<script setup>
import { Avatar, AvatarImage } from '@/components/ui/avatar'

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
})

const { id, nickname, profileImageUrl } = props.data.user
const { content, createdAt } = props.data.comment
</script>

<template>
  <div class="flex items-center gap-3 pb-3 border-b">
    <Avatar class="size-8">
      <AvatarImage
        :src="profileImageUrl || '/src/assets/default_profile.png'"
        alt="avatar"
      />
    </Avatar>
    <div>
      <span class="pr-2 font-bold">{{ nickname }}</span>
      <span>{{ content }}</span>
    </div>
  </div>
</template>

<style scoped></style>
