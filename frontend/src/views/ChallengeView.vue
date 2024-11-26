<script setup>
import { Button } from '@/components/ui/button'
import { getChallengeMissions } from '@/services/getChallengeMissions'
import { onMounted, ref } from 'vue'

onMounted(async () => {
  // AI로 챌린지 데이터 불러오기
  const response = await getChallengeMissions()
  dailyMission.value.missions = response

  console.log(dailyMission.value.missions)
})

const dailyMission = ref({
  title: '오늘의 행복 찾기 챌린지',
  description: '일상 속 작은 행복을 발견하고 공유해보세요',
  missions: [
    {
      emoji: '☀️',
      title: '아침 햇살 인사',
      description: '창문을 열고 3분간 햇살을 느껴보세요',
      tip: '따뜻한 차 한잔과 함께하면 더욱 좋아요!',
    },
    {
      emoji: '📸',
      title: '오늘의 풍경 한 컷',
      description: '평소에 지나쳤던 주변의 아름다운 순간을 카메라에 담아보세요',
      tip: '하늘, 꽃, 건물 등 무엇이든 좋아요',
    },
    {
      emoji: '🌿',
      title: '잠시, 산책',
      description: '10분 동안 걸으며 주변을 둘러보세요',
      tip: '점심시간이나 퇴근 후에 가볍게 시작해보세요',
    },
  ],
})
</script>

<template>
  <div
    class="flex flex-col items-center justify-center h-screen max-w-6xl gap-8 py-12 mx-auto"
  >
    <!-- 날짜 -->
    <div
      class="flex flex-col items-center gap-2 pb-4 border-b-2 border-green-500"
    >
      <p class="text-4xl font-medium text-gray-500">
        {{
          new Date().toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            weekday: 'long',
          })
        }}
      </p>
    </div>
    <!-- 챌린지 소개 -->
    <div class="flex flex-col items-center gap-4">
      <h1 class="text-3xl font-bold font-Namum">{{ dailyMission.title }}</h1>
      <p class="max-w-2xl text-center text-gray-600">
        {{ dailyMission.description }}
      </p>
    </div>

    <!-- 미션 카드 섹션 -->
    <div class="grid w-full grid-cols-1 gap-6 px-4 md:grid-cols-3">
      <div
        v-for="mission in dailyMission.missions"
        :key="mission.title"
        class="flex flex-col items-center p-8 transition-all duration-300 bg-white rounded-lg shadow-sm hover:shadow-md"
      >
        <span class="mb-4 text-5xl">{{ mission.emoji }}</span>
        <h3 class="mb-3 text-xl font-semibold">{{ mission.title }}</h3>
        <p class="mb-4 text-center text-gray-600">{{ mission.description }}</p>
        <p class="text-sm text-gray-500">💡 {{ mission.tip }}</p>
      </div>
    </div>

    <!-- 인증 안내 섹션 -->
    <div class="flex flex-col items-center gap-4 mt-8">
      <h2 class="text-2xl font-semibold text-center">
        미션 인증하고 특별한 순간 공유하기
      </h2>
      <p class="text-center text-gray-600">
        오늘의 미션을 완료하셨나요?<br />
        아보그에 인증하고 다른 사람들과 행복한 순간을 나눠보세요!
      </p>
      <RouterLink to="/abog">
        <Button
          class="px-6 py-3 mt-4 text-white transition-colors bg-green-500 rounded-lg hover:bg-green-600"
        >
          인증하러 가기
        </Button>
      </RouterLink>
    </div>
  </div>
</template>
