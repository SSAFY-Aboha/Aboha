<script setup>
import MainIntroduction from '@/components/MainIntroduction.vue'
import SpecsIntroduction from '@/components/SpecsIntroduction.vue'
import SuggestTrip from '@/components/SuggestTrip.vue'
</script>

<template>
  <div class="home">
    <section
      class="flex flex-col w-full items-center justify-center h-[calc(100vh-5rem)]"
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
          <pre class="max-w-2xl leading-relaxed text-center text-gray-600">
    매일 반복되는 일상 속에서도 특별한 순간들이 있습니다.
    커피 한 잔의 여유, 퇴근길의 노을, 주말의 게으른 브런치...
    당신의 평범한 하루를 특별하게 만들어줄 작은 행복들을 찾아보세요.
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
          <h1 class="text-3xl font-bold font-Namum">당신을 위한 맞춤 여행지</h1>
          <p class="max-w-2xl text-center text-gray-600">
            지금 당신의 기분에 딱 맞는 여행지를 추천해드립니다. <br />혼자만의
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

    <!-- 추천 여행 계획 컨텐츠 -->
    <section
      class="flex flex-col items-center justify-center w-full py-6 bg-gray-50"
    >
      <div
        class="flex flex-col items-center justify-center max-w-screen-xl gap-8 py-12 mx-auto"
      >
        <div class="flex flex-col items-center gap-4">
          <h1 class="text-3xl font-bold font-Namum">나만의 완벽한 여행 계획</h1>
          <p class="max-w-2xl text-center text-gray-600">
            하루의 특별한 순간을 위한 맞춤형 여행 계획을 제안합니다. <br />
            시간대별로 최적화된 일정으로 당신의 하루를 채워보세요.
          </p>
        </div>
        <div class="grid w-full grid-cols-1 gap-6 px-4 md:grid-cols-3">
          <div class="p-6 bg-white rounded-lg shadow-sm">
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl">🌅</span>
              <h3 class="font-semibold">아침 일정</h3>
            </div>
            <ul class="space-y-2 text-gray-600">
              <li>• 일출 명소 산책</li>
              <li>• 현지 조식 체험</li>
              <li>• 시장 구경</li>
            </ul>
          </div>
          <div class="p-6 bg-white rounded-lg shadow-sm">
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl">☀️</span>
              <h3 class="font-semibold">오후 일정</h3>
            </div>
            <ul class="space-y-2 text-gray-600">
              <li>• 로컬 맛집 탐방</li>
              <li>• 문화재 관람</li>
              <li>• 카페 투어</li>
            </ul>
          </div>
          <div class="p-6 bg-white rounded-lg shadow-sm">
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl">🌙</span>
              <h3 class="font-semibold">저녁 일정</h3>
            </div>
            <ul class="space-y-2 text-gray-600">
              <li>• 야경 포인트</li>
              <li>• 로컬 펍 체험</li>
              <li>• 야시장 탐방</li>
            </ul>
          </div>
        </div>
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
              src="/src/assets/default_image.png"
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
              src="/src/assets/default_image.png"
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
              src="/src/assets/default_image.png"
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
    <section class="flex flex-col items-center justify-center w-full py-6">
      <SuggestTrip />
    </section>

    <!-- 아보아 스펙 컨텐츠 -->
    <section class="flex flex-col items-center justify-center w-full py-6">
      <SpecsIntroduction />
    </section>
  </div>
</template>
