<script setup>
import { Badge } from '@/components/ui/badge'
import { RouterLink } from 'vue-router'
import defaultImage from '@/assets/default_image.png'

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
    class="group transition-all duration-300 hover:translate-y-[-4px]"
  >
    <li
      class="flex flex-col items-start w-full gap-3 p-3 transition-shadow duration-300 bg-white border rounded-xl hover:shadow-lg"
    >
      <!-- 이미지 컨테이너 -->
      <div
        class="relative w-full overflow-hidden border-gray-100 h-52 rounded-xl group-hover:shadow-md"
      >
        <img
          class="object-cover w-full h-full transition-transform duration-300 group-hover:scale-110"
          :src="image || defaultImage"
          :alt="title"
          loading="lazy"
        />
        <!-- 좋아요 버튼 -->
        <div
          class="absolute flex items-center gap-1 px-3 py-1.5 bg-white/90 rounded-full bottom-3 right-3"
        >
          <i
            class="text-red-500 transition-transform cursor-pointer pi pi-heart hover:scale-110"
          ></i>
          <span class="text-sm font-medium">{{ likeCount }}</span>
        </div>
      </div>

      <!-- 컨텐츠 -->
      <div class="flex flex-col justify-start w-full gap-3 p-2">
        <h2 class="text-lg font-bold line-clamp-1">{{ title }}</h2>
        <div class="flex items-center gap-2 text-sm text-gray-600">
          <i class="pi pi-map-marker"></i>
          <span>{{ sidoName }} {{ gugunName }}</span>
        </div>
        <div class="flex flex-wrap gap-2">
          <Badge
            v-for="(item, index) in tags"
            :key="index"
            class="px-2.5 py-1 text-sm bg-gray-100 text-gray-700 hover:bg-gray-200 transition-colors"
          >
            {{ item }}
          </Badge>
        </div>
      </div>
    </li>
  </RouterLink>
</template>

<style scoped></style>
