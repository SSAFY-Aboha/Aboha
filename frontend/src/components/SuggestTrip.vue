<script setup>
import { computed, onMounted, ref } from 'vue'
import AttractionCard from './common/AttractionCard.vue'
import attractionAPI from '@/api/attractions'
import { Star } from 'lucide-vue-next'

// 여기서 데이터 검색해서 가져오기
const attractions = ref([])

const sliceAttractions = computed(() => attractions.value.slice(0, 10))

const isLoading = ref(true)
const error = ref(null)

const containerRef = ref(null)
const isDragging = ref(false)
const startX = ref(0)
const scrollLeft = ref(0)

// 복제된 여행지 데이터: 무한 슬라이드 효과를 위해 데이터를 복제
const duplicateAttractions = computed(() => {
  return [...sliceAttractions.value, ...sliceAttractions.value]
})

// 드래그 시작 이벤트 : 드래그 중이던 상태를 시작
const startDragging = e => {
  isDragging.value = true
  startX.value = e.pageX - containerRef.value.offsetLeft
  scrollLeft.value = containerRef.value.scrollLeft
}

// 드래그 종료 이벤트 : 드래그 중이던 상태를 종료
const stopDragging = () => {
  isDragging.value = false
}

// 드래그 이벤트 : 드래그 중이던 상태에서 마우스 움직임에 따른 스크롤 이벤트
const onDrag = e => {
  if (!isDragging.value) return
  e.preventDefault()
  const x = e.pageX - containerRef.value.offsetLeft
  const walk = (x - startX.value) * 2
  containerRef.value.scrollLeft = scrollLeft.value - walk
}

onMounted(async () => {
  try {
    const {
      status,
      data,
      error: apiError,
    } = await attractionAPI.getAttractions({
      sort: 'POPULAR',
    })
    if (status === 200) {
      attractions.value = data.content
    } else {
      error.value = apiError
    }
  } catch (e) {
    error.value = e
  } finally {
    isLoading.value = false
  }
})
</script>

<template>
  <div class="suggest-trip">
    <div
      class="flex flex-col items-center justify-center gap-12 py-12 size-full"
    >
      <div class="flex flex-col items-center gap-2">
        <h1 class="flex items-center gap-2 text-3xl font-bold font-Namum">
          <Star class="text-4xl text-yellow-500" />
          <span>요즘 떠오르는 장소</span>
          <Star class="text-4xl text-yellow-500" />
        </h1>
      </div>

      <div v-if="isLoading" class="loading-spinner">
        <!-- Add your loading component here -->
      </div>

      <div v-else-if="error" class="error-message">
        데이터를 불러오는데 실패했습니다. 다시 시도해주세요.
      </div>

      <div
        ref="containerRef"
        class="relative w-full mx-auto overflow-hidden ul-wrapper"
        @mousedown="startDragging"
        @mouseleave="stopDragging"
        @mouseup="stopDragging"
        @mousemove="onDrag"
      >
        <ul class="flex items-center gap-5 px-10 carousel">
          <AttractionCard
            v-for="each in duplicateAttractions"
            :key="each.id"
            :data="each"
          />
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ul-wrapper {
  -webkit-overflow-scrolling: touch;
  scroll-behavior: smooth;
}

.carousel {
  position: relative;
  white-space: nowrap;
  animation: carousel 30s linear infinite;
}

@keyframes carousel {
  from {
    transform: translateX(0);
  }
  to {
    transform: translateX(-50%);
  }
}

.carousel:hover {
  animation-play-state: paused;
}

@media (max-width: 768px) {
  .carousel {
    animation: none;
  }
}
</style>
