<script setup>
import { Image } from 'lucide-vue-next'
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-8 py-12 size-full">
    <div class="flex flex-col items-center gap-2">
      <h1 class="text-3xl font-bold font-Namum">가장 인기있는 여행지</h1>
    </div>
    <div class="relative w-full overflow-hidden ul-wrapper">
      <ul
        class="flex items-center justify-around gap-5 px-10 carousel shrink-0"
      >
        <li
          v-for="(item, index) in 10"
          :key="index"
          class="relative z-10 transition-all duration-300 cursor-pointer hover:scale-95"
        >
          <RouterLink :to="`/trips/${index}`">
            <div class="flex flex-col items-center justify-center gap-2">
              <div class="flex flex-col gap-2 bg-gradient rounded-xl">
                <div>
                  <img
                    class="min-w-[17rem] max-h-[17rem] rounded-md"
                    src="/src/assets/mainPage_image.jpg"
                    alt="Image"
                  />
                </div>
                <div class="flex items-center justify-between w-full">
                  <div class="flex flex-col justify-start text-xl font-bold">
                    <span class="text-sm">{{ '여행지 이름' }}</span>
                    <span class="text-xs text-gray-500">{{
                      '간단한 지역'
                    }}</span>
                  </div>
                  <div class="flex items-center justify-center gap-1 text-sm">
                    <i class="text-2xl text-red-500 pi pi-heart icon-size"></i>
                    <span>123</span>
                  </div>
                </div>
              </div>
            </div>
          </RouterLink>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.carousel {
  position: relative;
  white-space: nowrap;
  animation: carousel 20s linear infinite;
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
