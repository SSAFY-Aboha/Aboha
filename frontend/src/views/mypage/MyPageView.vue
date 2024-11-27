<script setup>
import { Button } from '@/components/ui/button'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import userAPI from '@/api/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const { userInfo } = storeToRefs(userStore)

const handleDelete = async () => {
  if (confirm('정말 탈퇴하시겠습니까?')) {
    const { error: deleteError } = await userAPI.deleteUser()
    if (deleteError) {
      alert(deleteError)
      return
    }

    alert('회원탈퇴가 완료되었습니다.')
    userAPI.logout()
    router.push({ name: 'home' })
  }
}
const activeRoute = computed(() => route.name)
</script>

<template>
  <div class="flex w-full min-h-screen bg-gray-50">
    <aside class="fixed flex flex-col h-full bg-white shadow-lg w-72">
      <!-- 로고 -->
      <div class="p-6 border-b">
        <RouterLink :to="{ name: 'home' }" class="block">
          <img class="w-32 mx-auto" src="@/assets/aboha_logo.svg" alt="logo" />
        </RouterLink>
      </div>

      <!-- 프로필 섹션 -->
      <div class="flex flex-col items-center px-6 py-8 border-b">
        <div class="relative group">
          <div
            class="overflow-hidden transition-transform rounded-full size-32 group-hover:scale-105"
          >
            <img
              class="object-cover w-full h-full"
              :src="userInfo.profileImageUrl"
              alt="프로필"
            />
          </div>
          <RouterLink
            :to="{ name: 'mypage-edit' }"
            class="absolute bottom-0 right-0 p-2 text-white transition-colors bg-gray-700 rounded-full opacity-0 group-hover:opacity-100 hover:bg-gray-800"
          >
            <i class="text-sm pi pi-pencil"></i>
          </RouterLink>
        </div>
        <h2 class="mt-4 text-xl font-bold">{{ userInfo.nickname }}</h2>
        <p class="mt-1 text-sm text-gray-500">{{ userInfo.email }}</p>
      </div>

      <!-- 네비게이션 메뉴 -->
      <nav class="flex-1 px-4 py-6">
        <RouterLink
          v-for="(item, index) in [
            {
              name: 'mypage-main',
              icon: 'pi-chart-bar',
              label: '나의 활동 내역',
            },
            {
              name: 'mypage-edit',
              icon: 'pi-user-edit',
              label: '회원정보 수정',
            },
          ]"
          :key="index"
          :to="{ name: item.name }"
          class="flex items-center px-4 py-3 mb-2 transition-colors rounded-lg gap-x-3"
          :class="
            activeRoute === item.name
              ? 'bg-primary-50 text-primary-600'
              : 'hover:bg-gray-100'
          "
        >
          <i :class="`pi ${item.icon}`"></i>
          <span class="font-medium">{{ item.label }}</span>
        </RouterLink>
      </nav>

      <!-- 하단 버튼 -->
      <div class="p-6 border-t">
        <Button
          variant="outline"
          class="w-full text-red-500 border-red-200 hover:bg-red-50"
          @click="handleDelete"
        >
          <i class="mr-2 pi pi-trash"></i>
          회원탈퇴
        </Button>
      </div>
    </aside>

    <!-- 메인 컨텐츠 -->
    <main class="flex-1 ml-72">
      <router-view></router-view>
    </main>
  </div>
</template>

<style></style>
