<script setup>
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'
import { inject, ref } from 'vue'
import Input from '../ui/input/Input.vue'
import { Search } from 'lucide-vue-next'
import AttractionFilter from './AttractionFilter/AttractionFilter.vue'
import { Badge } from '../ui/badge'

const tagList = ref(['문화', '음식', '여행', '일상', '기타'])
const searchDataName = inject('searchDataName')
const searchData = inject('searchData')
const handleSearch = inject('handleSearch')

const handleKeywordChange = keyword => {
  searchData.value.keyword = keyword
  console.log(searchData.value)
  handleSearch()
}
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
    <div class="flex gap-3">
      <Badge v-if="searchDataName.sidoName">{{
        searchDataName.sidoName
      }}</Badge>
      <Badge v-if="searchDataName.gugunName">{{
        searchDataName.gugunName
      }}</Badge>
      <Badge v-if="searchDataName.contentName">{{
        searchDataName.contentName
      }}</Badge>
    </div>
    <!-- 검색 -->
    <div class="flex items-center gap-3">
      <div class="relative items-center w-full max-w-sm">
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
      <!-- 필터 버튼 -->
      <AttractionFilter />
    </div>
  </nav>
</template>

<style scoped></style>
