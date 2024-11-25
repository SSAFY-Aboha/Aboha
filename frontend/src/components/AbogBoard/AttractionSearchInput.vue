<script setup>
import { Input } from '@/components/ui/input'
import { Search } from 'lucide-vue-next'

const props = defineProps({
  styleClass: {
    type: String,
    required: false,
  },
})

const keyword = defineModel('keyword')
const emit = defineEmits(['search'])
</script>

<template>
  <div :class="styleClass">
    <Input
      id="search"
      type="text"
      placeholder="키워드 입력 후 Enter..."
      class="pl-10"
      v-model="keyword"
      @keyup.enter="emit('search')"
    />
    <span
      class="absolute inset-y-0 flex items-center justify-center px-2 start-0"
    >
      <Search class="size-6 text-muted-foreground" />
    </span>
  </div>
</template>

<style scoped></style>
