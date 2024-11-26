<script setup>
import { Input } from '@/components/ui/input'
import { Search } from 'lucide-vue-next'

const props = defineProps({
  styleClass: {
    type: String,
    required: false,
  },
})

const emit = defineEmits(['handleSearch', 'update:model-value'])
// const keyword = defineModel('keyword')

const handleSearch = keyword => {
  emit('update:model-value', keyword)
}
</script>

<template>
  <div :class="styleClass">
    <Input
      id="search"
      type="text"
      placeholder="키워드 입력 후 Enter..."
      class="pl-10"
      @keyup.enter="emit('handleSearch')"
      @update:model-value="handleSearch"
    />
    <span
      class="absolute inset-y-0 flex items-center justify-center px-2 start-0"
    >
      <Search class="size-6 text-muted-foreground" />
    </span>
  </div>
</template>

<style scoped></style>
