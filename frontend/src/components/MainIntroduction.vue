<script setup></script>

<template>
  <div class="relative w-4/5 h-full overflow-hidden">
    <div
      v-for="n in 10"
      :key="n"
      class="clover"
      :style="{ '--delay': `${n * 2}s`, '--position': `${n * 10}%` }"
    >
      🍀
    </div>

    <div class="flex flex-col items-center w-4/5 h-full md:flex-row">
      <div class="flex items-start justify-around w-full h-[30rem]">
        <!-- 글자 공간 -->
        <div class="flex flex-col justify-around h-full mt-6">
          <div class="flex flex-col gap-8">
            <h1 class="text-6xl font-bold md:text-9xl font-Namum">아보하</h1>
            <h2
              class="pr-4 overflow-hidden text-xl italic border-r-2 md:text-3xl border-r-black animate-typing whitespace-nowrap"
              aria-label="아주 보통의 하루, 특별한 여정을 시작하다"
            >
              "아주 보통의 하루, 특별한 여정을 시작하다."
            </h2>
          </div>
          <pre
            class="pr-4 mt-8 font-sans text-lg leading-relaxed text-wrap line-clamp-3"
          >
아보하는 평범한 일상 속에서 느끼는 작은 모험과 새로운 경험을 통해,
여행을 꼭 먼 곳에서 하는 것이 아닌 일상의 일부로 즐길 수 있도록 도와줍니다.
          </pre>
        </div>
        <!-- 이미지 공간 -->
        <div
          class="bg-green-100 shadow-2xl rounded-lg shrink-0 w-[460px] h-[460px]"
        >
          <img
            class="object-cover size-full"
            src="@/assets/mainPage_image.jpg"
            alt="아보하 메인 이미지"
            loading="lazy"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.clover {
  position: absolute;
  top: -20px;
  font-size: 20px;
  animation: falling var(--duration, 15s) var(--delay, 0s) linear infinite;
  left: var(--position, 0);
  opacity: 0.7;
  transform-style: preserve-3d;
  transform: translateZ(0);
}

@keyframes falling {
  0% {
    transform: translateY(-20px) rotate(0deg);
    opacity: 0.7;
  }
  50% {
    transform: translateY(50vh) rotate(180deg);
    opacity: 0.8;
  }
  100% {
    transform: translateY(100vh) rotate(360deg);
    opacity: 0.7;
  }
}

/* 각 클로버마다 다른 애니메이션 속도와 회전을 주기 위한 개별 설정 */
.clover:nth-child(2n) {
  --duration: 13s;
  font-size: 15px;
}

.clover:nth-child(3n) {
  --duration: 17s;
  font-size: 25px;
}

.clover:nth-child(4n) {
  --duration: 22s;
  font-size: 18px;
}

.clover:nth-child(5n) {
  --duration: 19s;
  font-size: 22px;
}
</style>
