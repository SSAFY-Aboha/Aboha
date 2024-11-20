<script setup>
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'
import { inject, ref } from 'vue'
import AttractionFilter from './AttractionFilter/AttractionFilter.vue'
import { Badge } from '../ui/badge'
import AttractionSearch from '@/components/Attractions/AttractionNav/AttractionSearch.vue'

const searchDataName = inject('searchDataName')
const searchParams = inject('searchParams')
const handleSearch = inject('handleSearch')

const handleKeywordChange = keyword => {
  searchParams.value.keyword = keyword
  handleSearch(searchParams.value)
}
</script>

<template>
  <nav class="flex items-center justify-between w-full">
    <!-- 선택된 태그 -->
    <div class="flex gap-3">
      <Badge v-for="tag in searchDataName" :key="tag">{{ tag }}</Badge>
    </div>
    <!-- 검색 -->
    <div class="flex items-center gap-3">
      <div class="relative items-center w-full max-w-sm">
        <AttractionSearch @update:model-value="handleKeywordChange" />
      </div>
      <!-- 필터 버튼 -->
      <AttractionFilter />
    </div>
  </nav>
</template>

<style scoped></style>
