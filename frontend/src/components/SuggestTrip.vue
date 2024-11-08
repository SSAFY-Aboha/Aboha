<script setup>
import { Image } from 'primevue'
</script>

<template>
  <div class="flex flex-col items-center justify-start gap-8 py-10 size-full">
    <div class="flex flex-col items-center gap-2">
      <h1 class="text-5xl font-bold font-Namum">가장 인기있는 여행지</h1>
    </div>
    <div class="w-5/6">
      <ul
        class="flex items-center justify-around gap-5 px-10 carousel basis-4/5 shrink-0"
      >
        <li v-for="(item, index) in 10" :key="index" class="carousel-item">
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

<style scoped>
.carousel {
  /* position: relative; */
  white-space: nowrap;
  animation: carousel 20s linear infinite;
}

.carousel-item {
  /* display: inline-block; */
}

@keyframes carousel {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}

/* 선택사항: 마우스 호버시 애니메이션 일시정지 */
.carousel:hover {
  animation-play-state: paused;
}
</style>
