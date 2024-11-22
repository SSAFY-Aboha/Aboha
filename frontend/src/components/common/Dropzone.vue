<script setup>
import { ref } from 'vue'

const isOverDropZone = ref(false)
const dropzoneRef = ref(null)

defineExpose({
  dropzoneRef,
})
</script>

<template>
  <div
    @dragover.prevent="isOverDropZone = true"
    @dragleave.prevent="isOverDropZone = false"
    @drop.prevent="onDropFile"
    ref="dropzoneRef"
    class="flex flex-col gap-1"
  >
    <div class="border-2 border-dashed rounded-md size-52">
      <div
        :class="`${isOverDropZone ? 'bg-gray-200' : ''} flex flex-col items-center justify-center gap-2 size-full`"
      >
        <span class="text-sm font-bold">이미지를 추가해주세요.</span>
        <span class="text-sm text-gray-500">[파일 형식 png, jpeg]</span>
        <i class="text-2xl pi pi-plus"></i>
      </div>
    </div>
  </div>
</template>
