<script setup>
import { computed, onMounted, ref } from 'vue'
import AttractionCard from '../common/AttractionCard.vue'
import VueLoading from 'vue-loading-overlay'
import attractionsAPI from '@/api/attractions'
import { Button } from '../ui/button'

const isLoading = defineModel('isLoading')
const pickedData = defineModel('pickedData')

const suggestData = ref([])

// 여기서 데이터 검색해서 가져오기
onMounted(async () => {
  // 로딩 시작
  isLoading.value = true

  const { status, data, error } = await attractionsAPI.getSuggestAttraction(
    pickedData.value,
  )
  console.log('추천 관광지 데이터', data)
  suggestData.value = data

  // 로딩 완료
  isLoading.value = false
})
</script>

<template>
  <!-- 로딩 중 -->
  <VueLoading
    v-model:active="isLoading"
    color="#6bd46b"
    width="300"
    loader="dots"
  />
  <!-- 로딩 완료시 -->
  <div v-if="!isLoading">
    <div v-if="suggestData?.length > 0" class="w-full h-full bg-white">
      <transition-group
        tag="ul"
        name="card-animation"
        class="grid items-center justify-center w-full grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3"
      >
        <AttractionCard
          v-for="(data, index) in suggestData"
          :key="data.id"
          :data="data"
          :style="{ animationDelay: `${index * 500}ms` }"
          class="card-item"
        />
      </transition-group>
    </div>
    <div v-else>
      <h1 class="text-2xl font-bold text-center animate-typo">
        추천 관광지가 없습니다...😅
      </h1>
    </div>
    <div class="flex items-center justify-center w-full bg-white pt-28">
      <Button
        @click="$router.push('/trips')"
        variant="outline"
        class="mx-auto text-lg text-white bg-green-600 rounded-full"
        >더 많은 여행지 보러가기</Button
      >
    </div>
  </div>
</template>

<style scoped>
.card-item {
  opacity: 0;
  animation: fadeInUp 0.6s ease forwards;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
