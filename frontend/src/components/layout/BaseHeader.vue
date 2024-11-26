<script setup>
import { RouterLink } from 'vue-router'
import { Button } from '@/components/ui/button'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { Avatar, AvatarImage } from '../ui/avatar'
import { onMounted, onUnmounted, ref } from 'vue'
import AvatarFallback from '@/components/ui/avatar/AvatarFallback.vue'
import { StarIcon, UserIcon } from 'lucide-vue-next'
import { Skeleton } from '@/components/ui/skeleton'
import AvatarIcon from '../common/AvatarIcon.vue'

const userStore = useUserStore()
const isLoading = ref(true)

const { isAuthenticated, userInfo } = storeToRefs(userStore)

const isVisible = ref(true)
const lastScrollPosition = ref(0)
const SCROLL_THRESHOLD = 50

const handleScroll = () => {
  const currentScrollPosition = window.scrollY

  // 스크롤이 맨 위에 있으면 항상 보이기
  if (currentScrollPosition < SCROLL_THRESHOLD) {
    isVisible.value = true
    return
  }

  // 이전 스크롤 위치와 비교하여 방향 확인
  isVisible.value =
    currentScrollPosition < lastScrollPosition.value ||
    currentScrollPosition < SCROLL_THRESHOLD

  lastScrollPosition.value = currentScrollPosition
}

// 로그인 상태 확인
onMounted(async () => {
  isLoading.value = true
  await userStore.initializeAuth()
  isLoading.value = false
  window.addEventListener('scroll', handleScroll)
})

// 컴포넌트 언마운트시 이벤트 리스너 제거
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

const handleLogout = async () => {
  await userStore.logout()
  window.location.href = '/'
}
</script>

<template>
  <header
    class="sticky top-0 z-50 w-full border-b bg-white/95 backdrop-blur supports-[backdrop-filter]:bg-white/60 transition-all duration-300"
    :class="{ '-translate-y-full': !isVisible }"
  >
    <div class="container flex items-center justify-between h-16 px-4 md:px-8">
      <!-- Logo -->
      <div class="flex items-center">
        <RouterLink to="/" replace class="transition-transform hover:scale-105">
          <img class="w-32 md:w-36" src="@/assets/aboha_logo.svg" alt="logo" />
        </RouterLink>
      </div>

      <!-- Navigation -->
      <nav class="items-center hidden space-x-6 md:flex">
        <RouterLink
          v-for="item in [
            { to: '/trips', label: '모든 여행 보러가기' },
            { to: { name: 'trips-map' }, label: '지도에서 찾아보기' },
            { to: { name: 'abog' }, label: '아보하 Log' },
          ]"
          :key="item.label"
          :to="item.to"
          class="text-sm font-medium text-gray-500 transition-colors hover:text-black"
        >
          {{ item.label }}
        </RouterLink>

        <Button variant="outline" class="text-sm font-medium">
          <RouterLink
            class="flex items-center gap-2"
            :to="{ name: 'challenge' }"
          >
            <StarIcon class="w-4 h-4 text-yellow-500" />
            <span>오늘의 챌린지</span>
            <StarIcon class="w-4 h-4 text-yellow-500" />
          </RouterLink>
        </Button>

        <Button variant="outline" class="text-sm font-medium">
          <RouterLink class="flex items-center gap-2" :to="{ name: 'suggest' }">
            <span>🍀</span>
            <span>아보하 추천받기</span>
            <span>🍀</span>
          </RouterLink>
        </Button>
      </nav>

      <!-- Auth Section -->
      <div class="flex items-center space-x-4">
        <template v-if="isLoading">
          <Skeleton class="h-9 w-[100px]" />
          <Skeleton class="w-10 h-10 rounded-full" />
        </template>

        <template v-else>
          <Button
            v-if="!isAuthenticated"
            variant="ghost"
            class="hidden text-sm md:inline-flex"
          >
            <RouterLink to="/signup">회원가입</RouterLink>
          </Button>

          <Button
            v-if="!isAuthenticated"
            class="text-sm bg-green-500 hover:bg-green-600"
          >
            <RouterLink to="/login">로그인</RouterLink>
          </Button>

          <template v-else>
            <Button
              variant="ghost"
              size="sm"
              @click="handleLogout"
              class="text-sm"
            >
              로그아웃
            </Button>

            <RouterLink :to="`/mypage/${userInfo?.email}`" class="relative">
              <AvatarIcon
                :customClass="`transition-all size-10 ring-2 ring-offset-2 ring-green-500/10 hover:ring-green-500/20`"
                :src="userInfo?.profileImageUrl"
              />
            </RouterLink>
          </template>
        </template>
      </div>
    </div>
  </header>
</template>

<style scoped>
.router-link-active {
  @apply text-black font-medium;
}
</style>
