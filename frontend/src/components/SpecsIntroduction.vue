<script setup>
import { Card } from '@/components/ui/card'
import { ref } from 'vue'

const metaData = ref([
  {
    title: '등록된 리뷰',
    count: 20,
    icon: 'pi-star',
    color: 'bg-purple-50 hover:bg-purple-100',
  },
  {
    title: '이용자 수',
    count: '200K',
    icon: 'pi-users',
    color: 'bg-blue-50 hover:bg-blue-100',
  },
  {
    title: '등록된 여행지',
    count: '12,385',
    icon: 'pi-map-marker',
    color: 'bg-green-50 hover:bg-green-100',
  },
  {
    title: '등록된 여행 계획',
    count: 22,
    icon: 'pi-calendar',
    color: 'bg-yellow-50 hover:bg-yellow-100',
  },
])
</script>

<template>
  <div class="flex flex-col items-center justify-center py-10 gap-7 size-full">
    <div class="flex flex-col items-center gap-2">
      <h1 class="text-3xl font-bold font-Namum">아보하</h1>
      <span class="text-lg text-gray-500">아보하 한눈에 보기</span>
    </div>
    <div class="w-full px-4 max-w-7xl">
      <ul class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4 md:gap-6">
        <Card
          v-for="{ title, count, icon, color } in metaData"
          :key="title"
          :class="[
            'flex flex-col items-center justify-center p-6 transition-all duration-300',
            'hover:shadow-lg hover:transform hover:-translate-y-1',
            color,
          ]"
        >
          <span :class="`text-3xl pi ${icon} mb-4 `"></span>
          <span class="mb-2 text-4xl font-bold">{{ count }}</span>
          <span class="text-sm text-gray-600">{{ title }}</span>
        </Card>
      </ul>
    </div>
  </div>
</template>

<style scoped></style>
