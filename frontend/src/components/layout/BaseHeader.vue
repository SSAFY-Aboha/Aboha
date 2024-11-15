<script setup>
import { RouterLink } from 'vue-router'
import { ref } from 'vue'
import { Button } from '@/components/ui/button'

const isLoggedIn = ref(false)
const visible = ref(false)
</script>

<template>
  <!-- Header -->
  <header class="flex items-center justify-between gap-10 px-12 py-4 shadow-sm">
    <div class="cursor-pointer w-44 logo">
      <RouterLink to="/">
        <img class="object-center" src="@/assets/aboha_logo.svg" alt="logo" />
      </RouterLink>
    </div>
    <nav class="flex items-center justify-start flex-1 gap-5 text-gray-500">
      <RouterLink class="hover:text-black" to="/trips">
        모든 여행 보러가기
      </RouterLink>
      <RouterLink class="hover:text-black" to="/"> 개발자 Pick! </RouterLink>
      <RouterLink class="hover:text-black" :to="{ name: 'abog' }">
        아보하 Log
      </RouterLink>
      <Button class="text-black bg-white hover:bg-gray-50">
        아보하 추천받기
      </Button>
    </nav>

    <div class="flex items-center gap-4">
      <!-- 클릭시 SurveyModal 띄우기 -->
      <Button
        label="당신의 특별하루를 만들어 보세요."
        severity="secondary"
        variant="outlined"
        @click="visible = true"
      />

      <!-- 로그인 상태라면 로그아웃 버튼 표시 -->
      <template v-if="isLoggedIn">
        <button class="px-3 py-2 rounded-xl hover:bg-gray-100">로그아웃</button>
        <RouterLink to="/mypage">
          <Button class="bg-transparent rounded-full hover:bg-gray-100">
            <i class="text-black pi pi-user"></i> </Button
        ></RouterLink>
      </template>
      <template v-else>
        <RouterLink class="" to="/signup">회원가입</RouterLink>
        <RouterLink to="/login">
          <Button class="text-sm bg-green-500">로그인</Button>
        </RouterLink>
      </template>
    </div>
  </header>
  <!-- SurveyModal -->
  <!-- <SurveyModal v-model:visible="visible" /> -->
</template>

<style scoped></style>
