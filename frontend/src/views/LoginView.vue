<script setup lang="ts">
import LoginForm from '@/components/Login/LoginForm.vue'
import LoginHeader from '@/components/Login/LoginHeader.vue'
import SocialLoginBox from '@/components/Login/SocialLoginBox.vue'
import { Separator } from '@/components/ui/separator'
</script>

<template>
  <section class="flex items-center justify-center h-full">
    <div
      @click="$router.push('/')"
      class="absolute text-sm text-left text-gray-500 cursor-pointer top-20 left-20 pb-7 hover:text-black"
    >
      <i class="pi pi-home"></i>
      <span class="ml-2">홈으로 돌아가기</span>
    </div>
    <div
      class="px-10 py-12 border shadow bg-gradient min-w-[28rem] md:max-h-3/6 lg:max-h-4/6 rounded-xl"
    >
      <div
        class="flex flex-col items-center justify-center w-full h-full gap-7"
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
        <Separator label="or" />
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
