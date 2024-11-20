<script setup>
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectGroup,
  SelectItem,
} from '@/components/ui/select'
import { inject, ref } from 'vue'

const searchParams = inject('searchParams')

const sortMeta = ref([
  { key: 'NEW', value: '최근 등록순' },
  { key: 'POPULAR', value: '인기순' },
  { key: 'VIEW', value: '조회순' },
  { key: 'REVIEW', value: '리뷰순' },
])

const handleSortChange = sortKey => {
  // 정렬 로직 작성
  searchParams.value.sort = sortKey
}
</script>

<template>
  <div class="flex items-center gap-3">
    <Select @update:model-value="handleSortChange">
      <SelectTrigger class="w-full">
        <SelectValue placeholder="정렬 선택" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup class="text-center">
          <SelectItem
            v-for="each in sortMeta"
            :key="each.key"
            :value="each.key"
          >
            {{ each.value }}
          </SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  </div>
</template>

<style lang="scss" scoped></style>
