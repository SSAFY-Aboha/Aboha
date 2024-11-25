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
  <div class="flex flex-col w-full space-y-4">
    <div class="flex items-center justify-between">
      <h2 class="text-xl font-bold text-gray-900">{{ title }}</h2>
      <Button
        variant="ghost"
        size="sm"
        class="text-gray-500 hover:text-gray-700"
      >
        <i class="pi pi-arrow-right"></i>
      </Button>
    </div>

    <Card class="overflow-hidden bg-white">
      <div class="p-4">
        <ul class="flex gap-4 pb-4 overflow-x-auto snap-x">
          <li
            v-for="n in 5"
            :key="n"
            class="relative flex-shrink-0 overflow-hidden transition-transform rounded-lg snap-start hover:scale-105 size-56 group"
          >
            <div
              class="absolute inset-0 bg-gray-900/20 group-hover:bg-gray-900/30"
            ></div>
            <img
              :src="`https://picsum.photos/seed/${n}/300/300`"
              class="object-cover w-full h-full"
              alt="컨텐츠 이미지"
            />
            <div class="absolute bottom-0 w-full p-4 text-white">
              <h3 class="font-medium">컨텐츠 제목 {{ n }}</h3>
            </div>
          </li>
        </ul>
      </div>
    </Card>
  </div>
</template>

<style lang="scss" scoped></style>
