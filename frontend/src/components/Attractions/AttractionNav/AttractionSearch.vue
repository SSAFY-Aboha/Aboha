<script setup>
import { Input } from '@/components/ui/input'
import { Search } from 'lucide-vue-next'

const emit = defineEmits(['update:model-value'])

const searchDataName = defineModel('searchDataName')

const handleKeywordChange = value => {
  searchDataName.value.keyword = value
  emit('update:model-value', value)
}

defineOptions({
  inheritAttrs: false,
})
</script>

<template>
  <div :class="$attrs.class">
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
  </div>
</template>

<style scoped></style>
