<script setup>
import { Card } from '../ui/card'

defineProps({
  title: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <div class="flex flex-col items-start justify-center w-full gap-y-4">
    <h1 class="text-2xl font-bold font-Namum">{{ title }}</h1>
    <Card class="w-full px-4 py-5 bg-white">
      <ul class="flex items-center gap-3 overflow-x-auto">
        <li
          v-for="n in 5"
          :key="n"
          class="bg-gray-100 rounded-md size-56 shrink-0"
        >
          1
        </li>
      </ul>
    </Card>
  </div>
</template>

<style lang="scss" scoped></style>
