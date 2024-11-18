<script setup>
const data = defineProps({
  data: Object,
})

const { id, title, content, date, tags, image } = data.data
</script>

<template>
  <router-link
    :to="{ name: 'trips-detail', params: { tripId: id } }"
    class="transition-all hover:scale-95"
  >
    <li
      class="max-w-72 min-w-60 flex flex-col items-start w-full gap-4 max-h-[40rem]"
    >
      <!-- 이미지 -->
      <div
        class="w-full overflow-hidden border border-gray-100 shadow-sm h-52 rounded-xl"
      >
        <img class="object-cover w-full h-full" :src="image" alt="" />
      </div>
      <!-- 날짜 & 태그 -->
      <div class="flex items-center w-full gap-5 text-xs text-gray-600">
        <span class="basis-2/5">{{ date }}</span>
        <ul class="flex items-center justify-between w-full">
          <li v-for="(item, index) in tags" :key="index">
            <div class="px-3 py-1 bg-gray-200 rounded-full">
              {{ `#${item}` }}
            </div>
          </li>
        </ul>
      </div>
      <!-- 제목 & 내용 -->
      <div class="flex flex-col justify-start gap-4">
        <h2 class="font-bold">{{ title }}</h2>
        <p class="text-ellipsis line-clamp-2">
          {{ content }}
        </p>
      </div>
    </li>
  </router-link>
</template>

<style scoped></style>
