<script setup>
import MypageContentTabs from '@/components/Mypage/MypageContentTabs.vue'
import { Button } from '@/components/ui/button'
import { Card, CardHeader } from '@/components/ui/card'
import CardContent from '@/components/ui/card/CardContent.vue'
import useUserStore from '@/stores/user'
import { storeToRefs } from 'pinia'
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import userAPI from '@/api/user'

const route = useRoute()
const isEdit = ref(false)
const userActivityStats = ref([])

onMounted(async () => {
  const { status, data, error } = await userAPI.getUserInfo()

  if (error) {
    console.error(error)
    return
  }

  userActivityStats.value = data
  console.log('userActivityStats', userActivityStats.value)
})

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

const activityStats = computed(() => {
  return [
    {
      title: '좋아요한 관광지',
      value: userActivityStats.value.likeCount,
      icon: 'pi-heart',
      color: 'text-red-500',
      increaseRate: 9, // 추후 구현 예정
      // link: { name: 'mypage-likes' },
    },
    {
      title: '작성한 리뷰',
      value: userActivityStats.value.reviewCount,
      icon: 'pi-comments',
      color: 'text-blue-500',
      increaseRate: 7, // 추후 구현 예정
      // link: { name: 'mypage-reviews' },
    },
    {
      title: '작성한 아보그',
      value: userActivityStats.value.abogCount,
      icon: 'pi-file-edit',
      color: 'text-green-500',
      increaseRate: 8, // 추후 구현 예정
      link: { name: 'mypage-abogs' },
    },
    {
      title: '저장한 여행일정',
      value: 10,
      icon: 'pi-map',
      color: 'text-purple-500',
      increaseRate: 10, // 추후 구현 예정
      // link: { name: 'mypage-trips' },
    },
  ]
})
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
        <div class="flex justify-around w-full gap-4">
          <div
            v-for="stat in activityStats"
            :key="stat.title"
            class="w-full max-w-64"
          >
            <Card class="transition-all hover:shadow-md hover:-translate-y-1">
              <CardHeader class="pb-2">
                <div class="flex items-center justify-between">
                  <h3 class="text-sm font-medium text-gray-600">
                    {{ stat.title }}
                  </h3>
                  <i :class="`pi ${stat.icon} ${stat.color}`"></i>
                </div>
              </CardHeader>
              <CardContent>
                <div class="flex flex-col items-center">
                  <span class="text-3xl font-bold">{{ stat.value }}</span>
                  <div class="mt-2 text-sm text-gray-500">
                    지난달 대비
                    <span class="font-medium text-green-500"
                      >+{{ stat.increaseRate }}%</span
                    >
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
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

.card-hover-effect {
  transition: all 0.3s ease;
}

.card-hover-effect:hover {
  transform: translateY(-4px);
  box-shadow:
    0 4px 6px -1px rgb(0 0 0 / 0.1),
    0 2px 4px -2px rgb(0 0 0 / 0.1);
}
</style>
