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

  console.log('pickedData', pickedData.value)

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
  <div class="w-full max-w-6xl px-4 mx-auto">
    <!-- 로딩 상태 -->
    <div
      v-if="isLoading"
      class="flex flex-col items-center justify-center py-20"
    >
      <VueLoading
        :active="isLoading"
        color="#6bd46b"
        width="300"
        loader="dots"
      />
      <p class="mt-4 text-lg text-gray-600">맞춤 여행지를 찾고 있어요...</p>
    </div>

    <!-- 결과 표시 -->
    <div v-else>
      <div v-if="suggestData?.length > 0">
        <div class="mb-8 text-center">
          <h2 class="text-2xl font-bold text-gray-800">
            {{ suggestData.length }}개의 추천 여행지를 찾았어요!
          </h2>
          <p class="mt-2 text-gray-600">
            선택하신 조건에 맞는 최적의 여행지를 추천해드립니다
          </p>
        </div>

        <transition-group
          tag="ul"
          name="card-animation"
          class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3"
        >
          <AttractionCard
            v-for="(data, index) in suggestData"
            :key="data.id"
            :data="data"
            :style="{ animationDelay: `${index * 150}ms` }"
            class="card-item"
          />
        </transition-group>
      </div>

      <div v-else class="py-20 text-center">
        <h2 class="mb-4 text-2xl font-bold text-gray-800">
          아쉽게도 추천 여행지를 찾지 못했어요 😅
        </h2>
        <p class="mb-8 text-gray-600">
          다른 조건으로 다시 시도해보시는 건 어떨까요?
        </p>
      </div>

      <div class="flex justify-center gap-4 mt-12">
        <Button
          @click="$router.push('/trips')"
          class="px-6 py-2 text-white bg-green-600 rounded-full hover:bg-green-700"
        >
          더 많은 여행지 보러가기
        </Button>
        <Button
          @click="$router.push('/')"
          variant="outline"
          class="px-6 py-2 text-green-600 rounded-full hover:text-green-700"
        >
          홈으로 돌아가기
        </Button>
      </div>
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
