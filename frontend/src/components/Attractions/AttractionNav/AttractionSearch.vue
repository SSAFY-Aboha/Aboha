<script setup>
import { Input } from '@/components/ui/input'
import { Search } from 'lucide-vue-next'

const emit = defineEmits(['update:model-value'])

const handleKeywordChange = value => {
  emit('update:model-value', value)
}
</script>

<template>
  <Input
    id="search"
    type="text"
    placeholder="키워드 입력 후 Enter..."
    class="pl-10"
    @update:model-value="handleKeywordChange"
  />
  <span
    class="absolute inset-y-0 flex items-center justify-center px-2 start-0"
  >
    <Search class="size-6 text-muted-foreground" />
  </span>
</template>

<style scoped></style>
