<script setup>
import { onMounted, ref } from 'vue'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '../../ui/select'

import attractionsAPI from '@/api/attractions'

const sidoCode = defineModel('sidoCode')
const gugunCode = defineModel('gugunCode')
const searchDataName = defineModel('searchDataName')

const sidoList = ref([])
const gugunList = ref([])

onMounted(async () => {
  // 시도 조회
  const { data } = await attractionsAPI.getSido()
  sidoList.value = data
})

const handleSidoChange = async sidoCode => {
  // 구군 조회
  const { data } = await attractionsAPI.getGugun(sidoCode)
  gugunList.value = data

  // 이름 저장
  searchDataName.value.tags.sidoCode.code = sidoCode
  searchDataName.value.tags.sidoCode.name = sidoList.value.find(
    each => each.code === +sidoCode,
  ).name
}

// 구군 변경 시 이름 저장
const handleGugunChange = gugunCode => {
  searchDataName.value.tags.gugunCode.code = gugunCode
  searchDataName.value.tags.gugunCode.name = gugunList.value.find(
    each => each.code === +gugunCode,
  ).name
}
</script>
<template>
  <h1 class="text-lg font-bold">지역</h1>
  <div class="flex items-center gap-3">
    <!-- ? 시/도 -->
    <Select v-model="sidoCode" @update:model-value="handleSidoChange">
      <SelectTrigger class="w-full">
        <SelectValue placeholder="시/도 선택" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectItem
            v-for="{ code, name } in sidoList"
            :key="code"
            :value="code.toString()"
          >
            {{ name }}
          </SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
    <!-- ? 구/군 -->
    <Select v-model="gugunCode" @update:model-value="handleGugunChange">
      <SelectTrigger class="w-full">
        <SelectValue placeholder="구/군 선택" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectItem
            v-for="{ code, name } in gugunList"
            :key="code"
            :value="code.toString()"
          >
            {{ name }}
          </SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  </div>
</template>

<style lang="scss" scoped></style>
