<script setup>
const props = defineProps({
  data: Object,
})

const { id, image, likeCount, title, sidoName, gugunName } = props.data
</script>

<template>
  <li>
    <RouterLink
      :to="`/trips/${id}`"
      class="block transition-all duration-300 cursor-pointer hover:scale-95"
    >
      <div class="flex flex-col items-center justify-center gap-2">
        <div class="flex flex-col gap-2 bg-gradient rounded-xl">
          <div>
            <img
              class="min-w-[16rem] max-h-[16rem] rounded-md"
              :src="image"
              :alt="id"
            />
          </div>
          <div class="flex items-center justify-between w-full">
            <div class="flex flex-col justify-start text-xl font-bold">
              <span class="text-sm">{{ title }}</span>
              <span class="text-xs text-gray-500">{{
                `${sidoName} ${gugunName}`
              }}</span>
            </div>
            <div class="flex items-center justify-center gap-1 text-sm">
              <i class="text-2xl text-red-500 pi pi-heart icon-size"></i>
              <span>{{ likeCount }}</span>
            </div>
          </div>
        </div>
      </div>
    </RouterLink>
  </li>
</template>
