<script setup>
import { Image } from 'primevue'
</script>

<template>
  <div class="flex flex-col items-center justify-start gap-5 py-20 size-full">
    <div class="">
      <h1 class="text-4xl">아보하 스펙</h1>
    </div>
    <div class="w-5/6">
      <ul
        class="flex items-center justify-around gap-5 px-10 overflow-scroll basis-4/5 shrink-0"
      >
        <li v-for="(item, index) in 10" :key="index">
          <Image
            imageClass="min-w-[240px] max-h-[240px] rounded-md"
            src="/src/assets/mainPage_image.jpg"
            alt="Image"
          />
        </li>
      </ul>
    </div>
  </div>
</template>
