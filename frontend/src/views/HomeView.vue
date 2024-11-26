<script setup>
import MainIntroduction from '@/components/MainIntroduction.vue'
import SpecsIntroduction from '@/components/SpecsIntroduction.vue'
import SuggestTrip from '@/components/SuggestTrip.vue'
import { Button } from '@/components/ui/button'
import { StarIcon } from 'lucide-vue-next'
</script>

<template>
  <div class="flex flex-col gap-10 home">
    <section
      class="flex flex-col w-full items-center justify-center h-[calc(100vh-4rem)]"
    >
      <MainIntroduction />
    </section>

    <!-- 일상의 순간들 컨텐츠 -->
    <section class="flex flex-col items-center justify-center w-full py-6 h-96">
      <div
        class="flex flex-col items-center justify-center gap-8 py-12 size-full"
      >
        <div class="flex flex-col items-center gap-4">
          <h1 class="text-3xl font-bold font-Namum">일상의 특별한 순간들</h1>
          <pre
            class="max-w-2xl leading-relaxed text-center text-gray-600 text-wrap text-md"
          >
    매일 반복되는 일상 속에서도 특별한 순간들이 있습니다.
    커피 한 잔의 여유, 퇴근길의 노을, 주말의 게으른 브런치...

    당신의 평범한 하루를 특별하게 만들어줄 작은 행복들을 <span class="font-bold">아보하</span>에서 찾아보세요.
          </pre>
        </div>
        <div class="grid grid-cols-1 gap-6 mt-8 md:grid-cols-3">
          <div class="flex flex-col items-center p-6 rounded-lg bg-gray-50">
            <span class="mb-4 text-4xl">☕️</span>
            <h3 class="mb-2 font-semibold">일상 속 쉼표</h3>
            <p class="text-center text-gray-600">하루 중 나를 위한 작은 휴식</p>
          </div>
          <div class="flex flex-col items-center p-6 rounded-lg bg-gray-50">
            <span class="mb-4 text-4xl">🌅</span>
            <h3 class="mb-2 font-semibold">일상 속 풍경</h3>
            <p class="text-center text-gray-600">평범한 날의 특별한 순간들</p>
          </div>
          <div class="flex flex-col items-center p-6 rounded-lg bg-gray-50">
            <span class="mb-4 text-4xl">✨</span>
            <h3 class="mb-2 font-semibold">일상 속 발견</h3>
            <p class="text-center text-gray-600">소소하지만 확실한 행복</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 추천 여행지 컨텐츠 -->
    <section class="flex flex-col items-center justify-center w-full py-6">
      <div
        class="flex flex-col items-center justify-center max-w-screen-xl gap-8 py-12 mx-auto"
      >
        <div class="flex flex-col items-center gap-4">
          <h1 class="text-3xl font-bold font-Namum">당신을 위한 맞춤 일상</h1>
          <p class="max-w-2xl text-center text-gray-600">
            지금 당신의 기분에 딱 맞는 일상을 추천해드립니다. <br />혼자만의
            여유로운 시간이 필요할 때, 활기찬 에너지가 필요할 때, <br />각자의
            순간에 어울리는 특별한 장소를 만나보세요.
          </p>
        </div>
        <div class="grid w-full grid-cols-1 gap-6 px-4 md:grid-cols-4">
          <div
            class="flex flex-col items-center p-6 transition-shadow rounded-lg bg-gray-50 hover:shadow-md"
          >
            <span class="mb-4 text-4xl">🌊</span>
            <h3 class="mb-2 font-semibold">조용한 바다</h3>
            <p class="text-center text-gray-600">혼자만의 시간이 필요할 때</p>
          </div>
          <div
            class="flex flex-col items-center p-6 transition-shadow rounded-lg bg-gray-50 hover:shadow-md"
          >
            <span class="mb-4 text-4xl">⛰️</span>
            <h3 class="mb-2 font-semibold">상쾌한 산책</h3>
            <p class="text-center text-gray-600">활기찬 에너지가 필요할 때</p>
          </div>
          <div
            class="flex flex-col items-center p-6 transition-shadow rounded-lg bg-gray-50 hover:shadow-md"
          >
            <span class="mb-4 text-4xl">🏛️</span>
            <h3 class="mb-2 font-semibold">도시 탐험</h3>
            <p class="text-center text-gray-600">새로운 발견이 필요할 때</p>
          </div>
          <div
            class="flex flex-col items-center p-6 transition-shadow rounded-lg bg-gray-50 hover:shadow-md"
          >
            <span class="mb-4 text-4xl">🍽️</span>
            <h3 class="mb-2 font-semibold">맛집 탐방</h3>
            <p class="text-center text-gray-600">
              일상의 맛있는 휴식이 필요할 때
            </p>
          </div>
        </div>
      </div>
    </section>

    <!--  데일리 챌린지 -->
    <section
      class="flex flex-col items-center justify-center w-full py-6 bg-gray-50"
    >
      <div
        class="flex flex-col items-center justify-center max-w-screen-xl gap-8 py-12 mx-auto"
      >
        <div class="flex flex-col items-center gap-4">
          <h1 class="flex items-center gap-2 text-3xl font-bold font-Namum">
            <StarIcon class="w-8 h-8 text-yellow-500" />
            <span>오늘의 챌린지</span>
            <StarIcon class="w-8 h-8 text-yellow-500" />
          </h1>
          <p class="max-w-2xl text-center text-gray-600">
            매일 새로운 여행 미션에 도전해보세요! <br />
            일상 속 작은 모험으로 특별한 추억을 만들어보세요.
          </p>
        </div>
        <div class="grid w-full grid-cols-1 gap-6 px-4 md:grid-cols-3">
          <div class="p-6 bg-white rounded-lg shadow-sm">
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl">📸</span>
              <h3 class="font-semibold">포토 챌린지</h3>
            </div>
            <div class="mb-3 text-gray-600">오늘의 미션</div>
            <p class="p-3 text-sm rounded-md bg-gray-50">
              동네에서 가장 예쁜 카페를 찾아 인생샷 남기기
            </p>
          </div>
          <div class="p-6 bg-white rounded-lg shadow-sm">
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl">🍽️</span>
              <h3 class="font-semibold">맛집 챌린지</h3>
            </div>
            <div class="mb-3 text-gray-600">오늘의 미션</div>
            <p class="p-3 text-sm rounded-md bg-gray-50">
              한 번도 가보지 않은 식당에서 새로운 메뉴 도전하기
            </p>
          </div>
          <div class="p-6 bg-white rounded-lg shadow-sm">
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl">🚶</span>
              <h3 class="font-semibold">산책 챌린지</h3>
            </div>
            <div class="mb-3 text-gray-600">오늘의 미션</div>
            <p class="p-3 text-sm rounded-md bg-gray-50">
              평소와 다른 경로로 동네 한 바퀴 돌아보기
            </p>
          </div>
        </div>
        <Button
          variant="outline"
          class="py-6 mt-6 text-lg font-medium text-white transition-all duration-300 bg-green-600 border-none rounded-xl w-80 hover:bg-green-700 hover:shadow-lg hover:scale-105 active:scale-95 focus:ring-4 focus:ring-green-200"
          @click="$router.push('/challenge')"
        >
          <div class="flex items-center justify-center gap-2">
            <i class="text-xl pi pi-star"></i>
            <span>AI로 오늘의 챌린지 추천받기</span>
          </div>
        </Button>
      </div>
    </section>

    <!-- 지역별 추천 여행지 컨텐츠 -->
    <section class="flex flex-col items-center justify-center w-full py-6">
      <div
        class="flex flex-col items-center justify-center max-w-screen-xl gap-8 py-12 mx-auto"
      >
        <div class="flex flex-col items-center gap-4">
          <h1 class="text-3xl font-bold font-Namum">지역별 숨은 명소</h1>
          <p class="max-w-2xl text-center text-gray-600">
            전국 각지에 숨어있는 특별한 장소들을 소개합니다. 관광객들이 잘
            모르는 현지인들의 아지트부터 SNS에서 핫한 장소까지, 색다른 매력이
            가득한 곳들을 만나보세요.
          </p>
        </div>
        <div
          class="grid w-full grid-cols-1 gap-6 px-4 md:grid-cols-2 lg:grid-cols-3"
        >
          <div class="relative overflow-hidden rounded-lg group">
            <img
              src="/src/assets/seoul.png"
              alt="서울"
              class="object-cover w-full h-64 transition-transform group-hover:scale-105"
            />
            <div
              class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40"
            >
              <div class="text-center text-white">
                <h3 class="mb-2 text-2xl font-bold">서울</h3>
                <p class="text-sm">도시의 낭만이 숨쉬는 골목길</p>
              </div>
            </div>
          </div>
          <div class="relative overflow-hidden rounded-lg group">
            <img
              src="/src/assets/busan.jpg"
              alt="부산"
              class="object-cover w-full h-64 transition-transform group-hover:scale-105"
            />
            <div
              class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40"
            >
              <div class="text-center text-white">
                <h3 class="mb-2 text-2xl font-bold">부산</h3>
                <p class="text-sm">바다와 도시가 만나는 곳</p>
              </div>
            </div>
          </div>
          <div class="relative overflow-hidden rounded-lg group">
            <img
              src="/src/assets/jeju.jpg"
              alt="제주"
              class="object-cover w-full h-64 transition-transform group-hover:scale-105"
            />
            <div
              class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40"
            >
              <div class="text-center text-white">
                <h3 class="mb-2 text-2xl font-bold">제주</h3>
                <p class="text-sm">자연과 문화의 특별한 조화</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 여행지 컨텐츠 -->
    <section
      class="flex flex-col items-center justify-center w-full py-6 overflow-hidden"
    >
      <SuggestTrip />
    </section>

    <!-- 아보아 스펙 컨텐츠 -->
    <section class="flex flex-col items-center justify-center w-full py-6">
      <SpecsIntroduction />
    </section>
  </div>
</template>
