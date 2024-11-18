<script setup>
import { Badge } from '@/components/ui/badge'
import { RouterLink } from 'vue-router'

const data = defineProps({
  data: Object,
})

const {
  id,
  title,
  sidoName,
  // sidoCode,
  // gugunCode,
  gugunName,
  // date,
  tags,
  image,
  likeCount,
} = data.data
</script>

<template>
  <RouterLink
    :to="{ name: 'trips-detail', params: { tripId: id } }"
    class="transition-all hover:scale-95"
  >
    <li
      class="max-w-72 min-w-60 flex flex-col items-start w-full gap-3 max-h-[40rem]"
    >
      <!-- 이미지 -->
      <div
        class="w-full overflow-hidden border border-gray-100 shadow-sm h-52 rounded-xl"
      >
        <img class="object-cover w-full h-full" :src="image" alt="" />
      </div>

      <!-- 제목 & 내용 -->
      <div class="flex flex-col justify-start w-full gap-3">
        <h2 class="font-bold">{{ title }}</h2>
        <div
          class="flex items-baseline justify-between text-ellipsis line-clamp-2"
        >
          <div class="flex items-center gap-2 text-sm text-gray-600">
            <i class="pi pi-map-marker"></i>
            <span>{{ sidoName }}</span>
            <span>{{ gugunName }}</span>
          </div>
          <div class="flex items-center justify-end gap-2">
            <i
              class="overflow-hidden text-red-500 cursor-pointer pi pi-heart hover:font-bold"
            >
            </i>
            <span>{{ likeCount }}</span>
          </div>
        </div>
        <div class="flex gap-2">
          <Badge
            class="text-black bg-gray-100 hover:bg-gray-100"
            v-for="(item, index) in tags"
            :key="index"
            :text="item"
            >{{ item }}</Badge
          >
        </div>
      </div>
    </li>
  </RouterLink>
</template>

<style scoped></style>
