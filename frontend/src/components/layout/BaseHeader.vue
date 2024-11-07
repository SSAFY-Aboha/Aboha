<script setup>
import { RouterLink } from 'vue-router'
import { ref } from 'vue'
import { Button } from 'primevue'
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
  <!-- Header -->
  <header class="flex items-center justify-between gap-10 px-12 py-4 shadow-sm">
    <div class="cursor-pointer w-44 logo">
      <RouterLink to="/">
        <img class="object-center" src="@/assets/aboha_logo.svg" alt="logo" />
      </RouterLink>
    </div>
    <nav class="flex items-center justify-start flex-1 gap-5 text-gray-500">
      <RouterLink class="hover:text-black" to="/mypage" @click="show()">
        모든 여행 보러가기
      </RouterLink>
      <RouterLink class="hover:text-black" to="/mypage" @click="show()">
        개발자 Pick!
      </RouterLink>
      <Button class="hover:text-black" to="/mypage" @click="show()">
        개발자에게 커피 사주기
      </Button>
    </nav>

    <div class="flex items-center gap-4">
      <Button
        label="당신의 특별하루를 만들어 보세요."
        severity="secondary"
        variant="outlined"
      />

      <!-- 로그인 상태라면 로그아웃 버튼 표시 -->
      <template v-if="isLoggedIn">
        <button>로그아웃</button>
        <RouterLink to="/mypage"
          ><Button
            icon="pi pi-user"
            severity="info"
            rounded
            variant="outlined"
            aria-label="User"
        /></RouterLink>
      </template>
      <template v-else>
        <RouterLink class="" to="/about">회원가입</RouterLink>
        <RouterLink to="/login">
          <Button class="text-sm" label="로그인" severity="" />
        </RouterLink>
      </template>
    </div>
  </header>
</template>

<style scoped></style>
