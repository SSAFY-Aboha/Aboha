<script setup>
import { computed, ref, watch } from 'vue'
import AttractionSearchInput from './AttractionSearchInput.vue'
import attractionAPI from '@/api/attractions'
import { Button } from '../ui/button'

const selectedAttraction = defineModel('selectedAttraction')

const attractionList = ref([])
const selectedData = ref('') // 검색어 입력 값

const isOpen = ref(false)

const handleKeywordChange = async value => {
  // 검색어 변경 시 호출

  if (!value.trim()) {
    // 검색어가 비어있으면 목록 초기화
    attractionList.value = []
    return
  }

  try {
    const data = await attractionAPI.getAttractionName({ keyword: value })
    attractionList.value = data
    isOpen.value = true
  } catch (error) {
    console.error(error)
    attractionList.value = []
  }
}

const handleSelectAttraction = (id, title) => {
  selectedAttraction.value = id
  selectedData.value = title
  isOpen.value = false
}

const handleFocusout = () => {
  isOpen.value = false
}

const handleOpen = () => {
  isOpen.value = !isOpen.value
}
</script>

<template>
  <div class="relative w-full">
    <Button
      @click.prevent="handleOpen"
      class="flex items-center justify-start w-full gap-2"
      variant="outline"
    >
      <i class="pi pi-map-marker"></i>
      {{ selectedData || '장소 선택하기...' }}
    </Button>
    <div v-show="isOpen" class="absolute top-0 w-full">
      <div class="relative">
        <AttractionSearchInput
          @update:model-value="handleKeywordChange"
          @focusout="handleFocusout"
        />
        <div
          class="absolute z-50 w-full h-56 p-2 bg-white rounded-md shadow-md top-11"
        >
          <ul class="flex flex-col h-full gap-4 overflow-auto scrollbar-hide">
            <p class="text-center" v-if="attractionList.length === 0">
              검색 결과가 없습니다.
            </p>
            <li
              v-for="each in attractionList"
              :key="each.id"
              class="flex items-center w-full h-10 gap-3 px-3 py-3 transition-all rounded-md cursor-pointer hover:bg-gray-100"
              @click="handleSelectAttraction(each.id, each.title)"
            >
              <i class="pi pi-map-marker"></i>
              <span>{{ each.title }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.scrollbar-hide::-webkit-scrollbar {
  width: 10px;
}

.scrollbar-hide::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 10px;
}
</style>
