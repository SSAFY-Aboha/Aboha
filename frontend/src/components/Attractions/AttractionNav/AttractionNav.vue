<script setup>
import { inject } from 'vue'
import AttractionFilter from '@/components/Attractions/AttractionFilter/AttractionFilter.vue'
import { Badge } from '@/components/ui/badge'
import AttractionSearch from '@/components/Attractions/AttractionNav/AttractionSearch.vue'
import { XCircleIcon } from 'lucide-vue-next'

const searchDataName = inject('searchDataName')
const searchParams = inject('searchParams')
const handleSearch = inject('handleSearch')

const handleKeywordChange = keyword => {
  searchParams.value.keyword = keyword
  handleSearch(searchParams.value)
}
</script>

<template>
  <nav
    class="flex flex-col w-full gap-4 sm:flex-row sm:items-center sm:justify-between"
  >
    <!-- 선택된 태그 -->
    <div class="flex flex-wrap gap-2">
      <Badge
        v-for="tag in searchDataName"
        :key="tag"
        class="px-3 py-1.5 flex items-center gap-2 transition-all hover:bg-gray-200"
      >
        {{ tag }}
        <XCircleIcon
          class="w-4 h-4 cursor-pointer hover:text-red-500"
          @click="removeTag(tag)"
        />
      </Badge>
      <span v-if="!searchDataName.length" class="text-sm italic text-gray-500">
        선택된 필터가 없습니다
      </span>
    </div>

    <!-- 검색 영역 -->
    <div class="flex items-center gap-3">
      <div class="relative items-center w-full max-w-sm">
        <AttractionSearch
          @update:model-value="handleKeywordChange"
          class="transition-all focus-within:shadow-md"
        />
      </div>
      <!-- 필터 버튼 -->
      <AttractionFilter class="shrink-0" />
    </div>
  </nav>
</template>

<style scoped>
.badge-transition {
  @apply transition-all duration-200;
}
</style>
