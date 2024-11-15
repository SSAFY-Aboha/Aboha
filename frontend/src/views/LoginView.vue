<script setup lang="ts">
import LoginForm from '@/components/Login/LoginForm.vue'
import LoginHeader from '@/components/Login/LoginHeader.vue'
import SocialLoginBox from '@/components/Login/SocialLoginBox.vue'
</script>

<template>
  <section class="flex items-center justify-center h-full">
    <div
      class="px-10 py-12 border shadow bg-gradient min-w-[28rem] md:max-h-3/6 lg:max-h-4/6 rounded-xl"
    >
      <div
        class="flex flex-col items-center justify-center w-full h-full gap-3"
      >
        <!-- 로그인 헤더 -->
        <LoginHeader />
        <!-- 로그인 Form -->
        <LoginForm />
        <div class="text-sm text-center">
          <span class="mr-2 italic">아직 아보하 회원이 아니신가요?</span>
          <router-link class="underline hover:font-bold" to="/signup"
            >가입하러가기</router-link
          >
        </div>
        <div class="w-full h-[1px] bg-gray-300 my-4"></div>
        <!-- 소셜 로그인 버튼 박스 -->
        <SocialLoginBox />
      </div>
    </div>
  </section>
</template>

<style>
.bg-gradient {
  background: linear-gradient(
    to bottom,
    rgb(255 255 255 / 21%),
    rgb(0 0 0 / 2%)
  );
}
</style>
