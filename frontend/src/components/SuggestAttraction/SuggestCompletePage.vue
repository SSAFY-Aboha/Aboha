<script setup>
import { onMounted, ref } from 'vue'
import AttractionCard from '../common/AttractionCard.vue'
import VueLoading from 'vue-loading-overlay'
import attractionsAPI from '@/api/attractions'
import { Button } from '../ui/button'

const isLoading = defineModel('isLoading')
const isTest = ref(true)
const pickedData = defineModel('pickedData')

const suggestData = ref([
  {
    id: 1,
    image: 'https://picsum.photos/300/300',
    likeCount: 156,
    title: '해운대 해수욕장',
    sidoName: '부산광역시',
    gugunName: '해운대구',
  },
  {
    id: 2,
    image: 'https://picsum.photos/300/300',
    likeCount: 142,
    title: '경복궁',
    sidoName: '서울특별시',
    gugunName: '종로구',
  },
  {
    id: 3,
    image: 'https://picsum.photos/300/300',
    likeCount: 98,
    title: '성산일출봉',
    sidoName: '제주특별자치도',
    gugunName: '서귀포시',
  },
  {
    id: 4,
    image: 'https://picsum.photos/300/300',
    likeCount: 87,
    title: '첨성대',
    sidoName: '경상북도',
    gugunName: '경주시',
  },
])

// 여기서 데이터 검색해서 가져오기
onMounted(() => {
  // 로딩 시작
  isLoading.value = true
  try {
    setTimeout(() => {}, 3000)
    // const data = await attractionsAPI.getSuggestAttraction(pickedData.value)
    // const { content } = data
    // suggestData.value = content
  } catch (error) {
    console.error(error)
  } finally {
    // 로딩 완료
    isLoading.value = false
  }
})
</script>

<template>
  <!-- 로딩 중 -->
  <div
    v-if="isLoading"
    class="flex items-center justify-center w-full h-full bg-white"
  >
    <!-- <VueLoading v-model:active="isLoading" /> -->
    <VueLoading
      v-model:active="isLoading"
      opacity="0"
      color="#6bd46b"
      width="300"
      loader="dots"
    />
  </div>
  <!-- 로딩 완료시 -->
  <div v-if="true" class="flex flex-col w-full h-full bg-white gap-28">
    <!-- <div
    v-if="!isLoading && suggestData.length > 0"
    class="w-full h-full bg-white"
  > -->
    <transition-group
      tag="ul"
      name="card-animation"
      class="grid items-center justify-center w-full grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
    >
      <AttractionCard
        v-for="(data, index) in suggestData"
        :key="data.id"
        :infoData="data"
        :style="{ animationDelay: `${index * 500}ms` }"
        class="card-item"
      />
    </transition-group>
    <div class="flex items-center justify-center w-full bg-white">
      <Button
        @click="$router.push('/trips')"
        variant="outline"
        class="w-1/4 mx-auto text-lg text-white bg-green-600 rounded-full"
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
