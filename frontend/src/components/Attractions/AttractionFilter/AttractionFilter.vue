<script setup>
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogFooter,
} from '@/components/ui/dialog'
import { Button } from '../../ui/button'
import AttractionFilterSelector from './AttractionFilterSelector.vue'
import AttractionFilterToggle from './AttractionFilterToggle.vue'
import { inject, ref, computed } from 'vue'

const isOpen = ref(false)

const onSearch = inject('handleSearch')
const searchParams = inject('searchParams')
const searchDataName = defineModel('searchDataName')

const handleSearchBtn = () => {
  isOpen.value = false
  // TODO: 검색 처리
  onSearch(searchParams.value)
}

const activeFiltersCount = computed(() => {
  let count = 0
  if (searchParams.value.sidoCode) count++
  if (searchParams.value.gugunCode) count++
  if (searchParams.value.contentTypeId) count++
  return count
})

const resetFilters = () => {
  searchParams.value = {
    sidoCode: '',
    gugunCode: '',
    contentTypeId: '',
    keyword: searchParams.value.keyword,
    sort: searchParams.value.sort,
    lastId: 0,
    lastSortValue: 0,
  }
}
</script>

<template>
  <Dialog v-model:open="isOpen">
    <DialogTrigger as-child>
      <Button
        variant="outline"
        class="flex items-center gap-2 hover:bg-gray-100"
      >
        <i class="pi pi-filter"></i>
        <span>필터</span>
        <span
          v-if="activeFiltersCount"
          class="px-2 py-0.5 text-sm bg-green-100 text-green-700 rounded-full"
        >
          {{ activeFiltersCount }}
        </span>
      </Button>
    </DialogTrigger>

    <DialogContent
      class="sm:max-w-[425px] lg:max-w-[600px] max-h-[80vh] overflow-y-auto"
    >
      <!-- Header -->
      <DialogHeader class="pb-4 border-b">
        <DialogTitle class="text-2xl font-bold">관광지 검색 필터</DialogTitle>
        <DialogDescription class="text-gray-600">
          원하는 관광지를 더 자세히 찾아보세요
        </DialogDescription>
      </DialogHeader>

      <!-- 메인 내용 -->
      <div class="flex flex-col gap-6 py-4">
        <section class="flex flex-col gap-4">
          <h3 class="text-lg font-semibold">지역 선택</h3>
          <AttractionFilterSelector
            v-model:searchDataName="searchDataName"
            v-model:sidoCode="searchParams.sidoCode"
            v-model:gugunCode="searchParams.gugunCode"
          />
        </section>

        <div class="w-full h-px bg-gray-200"></div>

        <section class="flex flex-col gap-4">
          <h3 class="text-lg font-semibold">테마 선택</h3>
          <AttractionFilterToggle
            v-model:searchDataName="searchDataName"
            v-model:contentTypeId="searchParams.contentTypeId"
          />
        </section>
      </div>

      <!-- Footer -->
      <DialogFooter class="flex gap-3 pt-4 border-t">
        <Button
          variant="outline"
          @click="resetFilters"
          class="hover:bg-gray-100"
        >
          초기화
        </Button>
        <Button
          type="submit"
          @click="handleSearchBtn"
          class="bg-green-600 hover:bg-green-700"
        >
          적용하기
        </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
