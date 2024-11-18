<script setup>
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'
import { ref } from 'vue'
import Input from '../ui/input/Input.vue'
import { Search } from 'lucide-vue-next'

const tagList = ref(['문화', '음식', '여행', '일상', '기타'])
</script>

<template>
  <nav class="flex items-center justify-between w-full">
    <!-- 태그 검색 -->
    <ToggleGroup class="flex gap-3" type="multiple">
      <ToggleGroupItem
        class="w-16 py-0 border rounded-full"
        v-for="tag in tagList"
        :key="tag"
        :value="tag"
        :aria-label="tag"
      >
        <span>{{ tag }}</span>
      </ToggleGroupItem>
    </ToggleGroup>
    <!-- 검색 -->
    <div class="relative items-center w-full max-w-sm">
      <Input id="search" type="text" placeholder="키워드 검색" class="pl-10" />
      <span
        class="absolute inset-y-0 flex items-center justify-center px-2 start-0"
      >
        <Search class="size-6 text-muted-foreground" />
      </span>
    </div>
  </nav>
</template>

<style scoped></style>
