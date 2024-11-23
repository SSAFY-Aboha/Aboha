<script setup>
import { computed } from 'vue'
import { Button } from '../ui/button'

const props = defineProps({
  data: Object,
  isEdit: {
    type: Boolean,
    required: false,
    default: false,
  },
})

const isEditState = computed(() => props.isEdit)

const emit = defineEmits(['delete-handler'])

const deleteAttraction = () => {
  emit('delete-handler', props.data.id)
}

const { id, image, likeCount, title, sidoName, gugunName } = props.data
</script>

<template>
  <li class="relative">
    <RouterLink
      :to="`/trips/${id}`"
      :class="`block transition-all duration-300 cursor-pointer ${!isEditState && 'hover:scale-95'}`"
    >
      <div class="flex flex-col items-center justify-center gap-2">
        <div class="flex flex-col gap-2 bg-gradient rounded-xl">
          <div class="flex items-center justify-center w-[19rem] h-[13rem]">
            <img
              class="object-cover w-full h-full rounded-md"
              :src="image || '/src/assets/default_image.png'"
              :alt="id"
            />
          </div>
          <div class="flex items-center justify-between w-full">
            <div class="flex flex-col justify-start text-xl font-bold">
              <span class="text-sm">{{ title }}</span>
              <span class="text-xs text-gray-500">{{
                `${sidoName} ${gugunName}`
              }}</span>
            </div>
            <div class="flex items-center justify-center gap-1 text-sm">
              <i class="text-2xl text-red-500 pi pi-heart icon-size"></i>
              <span>{{ likeCount }}</span>
            </div>
          </div>
        </div>
      </div>
    </RouterLink>
    <!-- 삭제 버튼 -->
    <button
      v-show="isEditState"
      @click="deleteAttraction"
      class="absolute p-2 py-0 bg-white rounded-full right-2 top-2 hover:bg-gray-300 hover:text-white"
    >
      <i class="font-bold text-red-500 pi pi-trash"></i>
    </button>
  </li>
</template>
