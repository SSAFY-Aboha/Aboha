<script setup>
import { useRouter } from 'vue-router'
import { Button } from '@/components/ui/button'

const router = useRouter()

const goToHome = () => {
  router.push('/')
}
</script>

<template>
  <div
    class="flex flex-col items-center justify-center min-h-screen p-4 space-y-8 text-center"
  >
    <!-- 로고 -->
    <img
      src="@/assets/aboha_logo.svg"
      alt="Aboha Logo"
      class="w-48 h-auto mb-4 transition-transform hover:scale-105"
    />

    <!-- 404 텍스트 -->
    <div class="space-y-4">
      <h1 class="font-bold text-8xl text-primary/80">404</h1>
      <p class="text-2xl font-medium text-gray-600">
        페이지를 찾을 수 없습니다
      </p>
      <p class="text-gray-500">
        요청하신 페이지가 삭제되었거나 잘못된 경로입니다
      </p>
    </div>

    <!-- 홈으로 가기 버튼 -->
    <Button
      @click="goToHome"
      class="px-8 py-6 mt-8 text-lg transition-all hover:scale-105"
    >
      <i class="mr-2 pi pi-home"></i>
      메인으로 돌아가기
    </Button>
  </div>
</template>

<style scoped>
.text-primary {
  color: #39b54a;
}

/* 애니메이션 효과 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.flex {
  animation: fadeIn 0.5s ease-out;
}

/* 버튼 호버 효과 */
.button {
  transition: all 0.3s ease;
}

.button:hover {
  box-shadow: 0 4px 12px rgba(57, 181, 74, 0.2);
}
</style>
