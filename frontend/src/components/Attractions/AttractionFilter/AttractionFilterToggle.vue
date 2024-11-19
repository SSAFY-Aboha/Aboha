<script setup>
import { onMounted, ref } from 'vue'
import { Toggle } from '../../ui/toggle'
defineProps({ title: String })
import attractionAPI from '@/api/attractions'
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'

const contentTypeId = defineModel('contentTypeId')
const contentTypeList = ref([])

onMounted(() => {
  attractionAPI.getContentType(data => (contentTypeList.value = data))
})
</script>

<template>
  <h1 class="text-lg font-bold">{{ title }}</h1>
  <ToggleGroup
    class="flex flex-wrap items-center gap-2 justify-stretch"
    v-model="contentTypeId"
    type="single"
  >
    <ToggleGroupItem
      v-for="{ id, name } in contentTypeList"
      :value="id.toString()"
      :key="id"
      class="py-6 border rounded-full max-w-26 data-[state=on]:bg-green-300 data-[state=on]:text-black data-[state=on]:font-bold"
      >{{ name }}
    </ToggleGroupItem>
  </ToggleGroup>
</template>

<style lang="scss" scoped></style>
