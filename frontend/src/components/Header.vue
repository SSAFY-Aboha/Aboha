<script setup>
import { RouterLink } from 'vue-router'
import { ref } from 'vue'
import { Button, Toast } from 'primevue'
import { useToast } from 'primevue/usetoast'

const isLoggedIn = ref(false)
const toast = useToast()

const show = () => {
  toast.add({
    severity: 'secondary',
    summary: '로그인 경고',
    detail: '로그인 후 이용해주세요.',
    life: 1000,
  })
}
</script>

<template>
  <Toast position="center" />
  <header class="flex items-center justify-between gap-10 px-12 py-4">
    <div class="cursor-pointer logo" @click="show()">
      <img src="" alt="logo" />
    </div>
    <nav class="flex items-center justify-start flex-1 gap-5 text-gray-500">
      <RouterLink class="hover:text-black" to="/mypage" @click="show()">
        여행 전체 리스트
      </RouterLink>
      <RouterLink class="hover:text-black" to="/mypage" @click="show()">
        여행 전체 리스트
      </RouterLink>
    </nav>

    <div class="flex items-center gap-4">
      <!-- 로그인 상태라면 로그아웃 버튼 표시 -->
      <template v-if="isLoggedIn">
        <button>로그아웃</button>
        <RouterLink to="/mypage">마이페이지</RouterLink>
      </template>
      <template v-else>
        <RouterLink class="text-sm" to="/about">회원가입</RouterLink>
        <RouterLink to="/login">
          <Button class="text-sm" label="로그인" severity="secondary" rounded />
        </RouterLink>
      </template>
    </div>
  </header>
</template>

<style scoped></style>
