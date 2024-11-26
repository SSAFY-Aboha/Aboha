<script setup>
import { onMounted, ref, watch } from 'vue'
import attractionAPI from '@/api/attractions'
import { Button } from '../ui/button'
import AttractionDetailSkeleton from './AttractionDetailSkeleton.vue'
import AttractionDetail from '@/components/Attractions/AttractionDetail.vue'
import { useToast } from '@/composables/useToast'
import { getTagAIResponse } from '@/services/getTagAIService'
import { getSummarizeAIResponse } from '@/services/summarizeAIService'

const props = defineProps({
  tripId: {
    type: [String, Number],
  },
})

const isLoading = ref(false)

const attraction = ref(null)

const toast = useToast()

onMounted(async () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  })
  try {
    isLoading.value = true
    const { status, data, error } = await attractionAPI.getAttractionDetail(
      props.tripId,
    )

    if (status === 200) {
      const { title, address, description } = data
      attraction.value = { ...data }

      const message = `여행지 이름: ${title}\n여행지 주소: ${address}\n여행지 소개글: ${description}`
      const tagString = await getTagAIResponse(message)
      const tags = tagString.split(',').map(tag => tag.trim())
      const summarize = await getSummarizeAIResponse(message)
      console.log(tags)

      attraction.value = { ...data, tags }
      attraction.value = { ...data, description: summarize, tags }
    } else {
      toast.error('데이터를 불러오는데 실패했습니다.')
    }
  } catch (error) {
    console.error('Error loading trip data:', error)
    toast.error('오류가 발생했습니다. 다시 시도해주세요.')
  } finally {
    isLoading.value = false
  }
})

// 컴포넌트가 업데이트될 때도 데이터를 다시 가져오도록
watch(
  () => props.tripId,
  async newId => {
    isLoading.value = true
    const { status, data, error } =
      await attractionAPI.getAttractionDetail(newId)
    if (status === 200) {
      attraction.value = data
    }
    if (error) {
      console.error('Error loading trip data:', error)
    }
    isLoading.value = false
  },
)
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <!-- header -->
    <header class="sticky top-0 z-10 w-full bg-white shadow-sm">
      <div class="px-4 py-4 mx-auto max-w-7xl sm:px-6 lg:px-8">
        <Button
          variant="outline"
          class="flex items-center gap-2 px-4 py-2 text-gray-600 transition-all group hover:text-gray-900"
          @click="$router.push('/trips')"
        >
          <i
            class="text-lg transition-transform pi pi-angle-left group-hover:-translate-x-1"
          />
          <span class="font-medium">목록으로 돌아가기</span>
        </Button>
      </div>
    </header>

    <!-- content -->
    <main class="px-4 py-8 mx-auto max-w-7xl sm:px-6 lg:px-8">
      <div class="p-6 bg-white rounded-lg shadow-md sm:p-8">
        <div v-if="isLoading" class="space-y-4">
          <AttractionDetailSkeleton />
        </div>

        <div v-else-if="!attraction" class="py-12 text-center">
          <p class="text-gray-500">데이터를 찾을 수 없습니다.</p>
          <Button
            class="mt-4 text-primary-600 hover:text-primary-700"
            @click="$router.push('/trips')"
          >
            목록으로 돌아가기
          </Button>
        </div>

        <AttractionDetail
          v-else
          v-model:attraction="attraction"
          v-model:likeCount="attraction.likeCount"
        />
      </div>
    </main>
  </div>
</template>

<style scoped>
.group:hover .group-hover\:-translate-x-1 {
  transform: translateX(-4px);
}
</style>
