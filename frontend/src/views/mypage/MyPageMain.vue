<script setup>
import MypageContentTabs from '@/components/Mypage/MypageContentTabs.vue'
import { Button } from '@/components/ui/button'
import { Card, CardHeader } from '@/components/ui/card'
import CardContent from '@/components/ui/card/CardContent.vue'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isEdit = ref(false)

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)
</script>

<template>
  <Transition name="fade" mode="out-in" appear>
    <div class="flex flex-col w-full h-full max-w-6xl mx-auto min-w-[900px]">
      <!-- header -->
      <header class="flex flex-col items-start justify-center py-5 gap-y-8">
        <div class="w-full px-8">
          <h1 class="text-3xl font-bold font-Namum">
            안녕하세요. {{ userInfo.nickname }}님
          </h1>
        </div>
        <div class="flex justify-around w-full gap-x-4">
          <Card
            v-for="item in 3"
            :key="item"
            class="w-full text-xl font-bold text-center bg-gray-50 font-Namum max-w-52"
          >
            <CardHeader>좋아요한 관광지 수</CardHeader>
            <CardContent>0</CardContent>
          </Card>
        </div>
      </header>
      <!-- 관광지, 아보그 활동 내역 -->
      <main
        class="flex flex-col items-center justify-start flex-1 px-8 py-5 gap-y-10"
      >
        <div class="flex items-center justify-between w-full">
          <h1 class="text-2xl font-bold basis-3/4 font-Namum">
            나의 활동 내역
          </h1>
          <div class="flex items-center justify-end w-full justify-items-end">
            <Button
              :class="`${isEdit && 'bg-green-200 hover:bg-green-300'}`"
              variant="outline"
              @click="isEdit = !isEdit"
            >
              <i class="pi pi-cog"></i>
              <span v-show="!isEdit">수정하기</span>
              <span v-show="isEdit">완료</span>
            </Button>
          </div>
        </div>
        <MypageContentTabs v-model:isEdit="isEdit" />
      </main>
    </div>
  </Transition>
</template>

<style scoped>
/* .fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
} */
</style>
