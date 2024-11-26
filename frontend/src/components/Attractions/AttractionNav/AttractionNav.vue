<script setup>
import { computed, inject, ref, watchEffect } from 'vue'
import AttractionFilter from '@/components/Attractions/AttractionFilter/AttractionFilter.vue'
import { Badge } from '@/components/ui/badge'
import AttractionSearch from '@/components/Attractions/AttractionNav/AttractionSearch.vue'
import { XCircleIcon } from 'lucide-vue-next'
import { Button } from '@/components/ui/button'

const searchParams = inject('searchParams')
const handleSearch = inject('handleSearch')

// 부가 상태 (태그 목록, 검색어)
const searchDataName = ref({
  keyword: '',
  tags: {
    sidoCode: {
      meta: 'sidoCode',
      code: '',
      name: '',
    },
    gugunCode: {
      meta: 'gugunCode',
      code: '',
      name: '',
    },
    contentTypeId: {
      meta: 'contentTypeId',
      code: '',
      name: '',
    },
  },
})

const reactiveTags = computed(() => {
  return Object.values(searchDataName.value.tags).filter(tag => tag.name !== '')
})

watchEffect(() => {
  console.log('searchDataName', searchDataName.value)
})

const handleKeywordChange = keyword => {
  searchParams.value.keyword = keyword
  handleSearch(searchParams.value)
}

const removeTag = meta => {
  searchDataName.value.tags[meta].code = ''
  searchDataName.value.tags[meta].name = ''

  if (meta === 'sidoCode' || meta === 'gugunCode') {
    searchParams.value.sidoCode = ''
    searchParams.value.gugunCode = ''
  }
  searchParams.value.contentTypeId = ''
  handleSearch(searchParams.value)
}

const resetFilters = () => {
  searchDataName.value = {
    keyword: '',
    tags: {
      sido: {
        meta: 'sidoCode',
        code: '',
        name: '',
      },
      gugun: {
        meta: 'gugunCode',
        code: '',
        name: '',
      },
      contentTypeId: {
        meta: 'contentTypeId',
        code: '',
        name: '',
      },
    },
  }
  searchParams.value = {
    sidoCode: '',
    gugunCode: '',
    contentTypeId: '',
    keyword: searchParams.value.keyword,
    sort: searchParams.value.sort,
    lastId: 0,
    lastSortValue: 0,
  }
  handleSearch(searchParams.value)
}
</script>

<template>
  <nav
    class="flex flex-col w-full gap-4 sm:flex-row sm:items-center sm:justify-between"
  >
    <!-- 선택된 태그 -->
    <div class="flex flex-wrap items-center gap-2">
      <Badge
        variant="outline"
        v-for="{ name, code, meta } in reactiveTags"
        :key="code"
        class="px-3 py-1.5 flex items-center gap-2 transition-all hover:bg-gray-200"
      >
        {{ name }}
        <XCircleIcon
          class="w-4 h-4 cursor-pointer hover:text-red-500"
          @click="removeTag(meta)"
        />
      </Badge>
      <span
        v-if="reactiveTags.length === 0"
        class="text-sm italic text-gray-500"
      >
        선택된 필터가 없습니다
      </span>
      <Button variant="outline" @click="resetFilters">초기화</Button>
    </div>

    <!-- 검색 영역 -->
    <div class="flex items-center gap-3">
      <div class="relative items-center w-full max-w-sm">
        <AttractionSearch
          v-model:searchDataName="searchDataName"
          @update:model-value="handleKeywordChange"
          class="transition-all focus-within:shadow-md"
        />
      </div>
      <!-- 필터 버튼 -->
      <AttractionFilter
        class="shrink-0"
        v-model:searchDataName="searchDataName"
      />
    </div>
  </nav>
</template>

<style scoped>
.badge-transition {
  @apply transition-all duration-200;
}
</style>
