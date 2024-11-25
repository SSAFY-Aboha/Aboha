<script setup>
import { Button } from '@/components/ui/button'
</script>

<template>
  <footer class="flex flex-col w-full bg-gray-100">
    <!-- Main footer content -->
    <div class="grid grid-cols-1 gap-8 px-6 py-12 md:grid-cols-4">
      <!-- Company Info -->
      <div class="space-y-4">
        <h3 class="text-lg font-bold">Aboha</h3>
        <p class="text-sm text-gray-600">
          더 나은 세상을 만들어가는 혁신적인 솔루션
        </p>
        <div class="flex space-x-4">
          <a href="#" class="text-gray-600 hover:text-black">
            <i class="fab fa-github"></i>
          </a>
          <a href="#" class="text-gray-600 hover:text-black">
            <i class="fab fa-linkedin"></i>
          </a>
        </div>
      </div>

      <!-- Quick Links -->
      <div class="space-y-4">
        <h3 class="text-lg font-bold">바로가기</h3>
        <ul class="space-y-2">
          <li>
            <a href="/about" class="text-gray-600 hover:text-black"
              >회사 소개</a
            >
          </li>
          <li>
            <a href="/services" class="text-gray-600 hover:text-black"
              >서비스</a
            >
          </li>
          <li>
            <a href="/contact" class="text-gray-600 hover:text-black"
              >문의하기</a
            >
          </li>
          <li>
            <a href="/careers" class="text-gray-600 hover:text-black"
              >채용정보</a
            >
          </li>
        </ul>
      </div>

      <!-- Legal -->
      <div class="space-y-4">
        <h3 class="text-lg font-bold">법적 고지</h3>
        <ul class="space-y-2">
          <li>
            <a href="/privacy" class="text-gray-600 hover:text-black"
              >개인정보처리방침</a
            >
          </li>
          <li>
            <a href="/terms" class="text-gray-600 hover:text-black">이용약관</a>
          </li>
          <li>
            <a href="/cookies" class="text-gray-600 hover:text-black"
              >쿠키 정책</a
            >
          </li>
        </ul>
      </div>

      <!-- Contact -->
      <div class="space-y-4">
        <h3 class="text-lg font-bold">연락처</h3>
        <p class="text-gray-600">서울특별시 강남구</p>
        <p class="text-gray-600">이메일: contact@aboha.com</p>
        <p class="text-gray-600">전화: 02-1234-5678</p>
      </div>
    </div>

    <!-- Bottom bar -->
    <div
      class="flex items-center justify-between px-6 py-4 border-t border-gray-200"
    >
      <span class="text-sm text-gray-600">ⓒ 2024 Aboha, Inc.</span>
      <Button class="hover:text-black" to="/mypage">
        개발자에게 커피 사주기
      </Button>
      <span class="text-sm text-gray-600">SSAFY 12th</span>
    </div>
  </footer>
</template>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>
