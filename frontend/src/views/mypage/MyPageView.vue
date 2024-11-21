<script setup>
import { Button } from '@/components/ui/button'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'

const route = useRoute()
const userStore = useUserStore()

const { userInfo } = storeToRefs(userStore)

console.log('userInfo', userInfo.value)
</script>

<template>
  <div class="flex w-full h-screen overflow-hidden">
    <aside class="flex flex-col items-center w-1/4 h-full py-3 shadow-md">
      <div>
        <RouterLink :to="{ name: 'home' }">
          <img
            class="object-center w-36"
            src="@/assets/aboha_logo.svg"
            alt="logo"
          />
        </RouterLink>
      </div>
      <div class="flex flex-col justify-between w-full h-full">
        <div class="flex flex-col items-center w-full py-10 gap-y-8">
          <!-- 프로필 이미지 -->
          <div class="overflow-hidden rounded-full size-36">
            <img
              class="object-center"
              :src="
                userInfo.profileImageUrl || '/src/assets/default_profile.png'
              "
              alt="logo"
            />
          </div>
          <!-- 나의 활동 내역 -->
          <RouterLink
            :to="{ name: 'mypage-main' }"
            class="w-full py-4 text-center transition-all border border-white cursor-pointer hover:border-gray-300"
          >
            <div class="text-xl font-bold font-Namum">나의 활동 내역</div>
          </RouterLink>
          <!-- 회원정보 수정 -->
          <RouterLink
            :to="{ name: 'mypage-edit' }"
            class="w-full py-4 text-center transition-all border border-white cursor-pointer hover:border-gray-300"
          >
            <div class="text-xl font-bold font-Namum">회원정보 수정</div>
          </RouterLink>
        </div>
        <div class="flex justify-center w-full py-4">
          <Button @click="handleDelete" variant="outline">회원탈퇴</Button>
        </div>
      </div>
    </aside>
    <main class="w-full h-full">
      <router-view></router-view>
    </main>
  </div>
</template>

<style></style>
