<script setup>
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import {
  TagsInput,
  TagsInputInput,
  TagsInputItem,
  TagsInputItemDelete,
  TagsInputItemText,
} from '@/components/ui/tags-input'
import { Textarea } from '@/components/ui/textarea'
import { useDropZone } from '@vueuse/core'

import { ref } from 'vue'

const dropzoneRef = ref(null)

const onDropFile = files => {
  console.log(files)
}

const { isOverDropZone } = useDropZone(dropzoneRef, {
  dataTypes: ['image/png', 'image/jpeg'],
  onDrop: onDropFile,
  multiple: true,
})

const inputVal = ref({
  title: '',
  place: '',
  content: '',
  tags: [],
  imageFiles: [],
})

const handleFileChange = e => {
  const files = e.target.files
  inputVal.value.imageFiles = files
  console.log(inputVal.value.imageFiles)
}

const handleFileDelete = file => {
  console.log(inputVal.value.imageFiles)
}

const handleSubmit = () => {
  console.log(inputVal.value)
}
</script>

<template>
  <div class="w-full h-full">
    <div class="py-5 border-b">
      <!-- 제목 -->
      <div class="h-full max-w-4xl mx-auto">
        <h1 class="max-w-5xl pb-2 text-4xl">아보하 로그 작성하기</h1>
        <span class="italic text-gray-500"
          >아보하 로그를 작성하는 페이지입니다.</span
        >
      </div>
    </div>
    <!-- Form -->
    <div class="max-w-4xl py-3 mx-auto">
      <form @submit.prevent="handleSubmit" class="flex flex-col w-full gap-6">
        <!-- 입력 input -->
        <!-- 태그 선택 -->
        <div class="flex items-center justify-between w-full gap-4">
          <div class="flex flex-col w-full">
            <label for="title" class="text-lg font-bold">태그 선택 </label>
            <TagsInput class="px-2" v-model="inputVal.tags">
              <TagsInputItem
                v-for="item in inputVal.tags"
                :key="item"
                :value="item"
              >
                <TagsInputItemText />
                <TagsInputItemDelete />
              </TagsInputItem>
              <TagsInputInput placeholder="태그 입력 후 Enter..." />
            </TagsInput>
          </div>
          <div class="w-full">
            <label for="title" class="text-lg font-bold">장소</label>
            <Input
              type="text"
              id="place"
              class="w-full"
              placeholder="장소를 선택해주세요."
              v-model:model-value="inputVal.place"
            />
          </div>
        </div>
        <!-- 드로그앤 드롭 -->
        <section class="flex items-center justify-center gap-3">
          <div
            @dragover.prevent="isOverDropZone = true"
            @dragleave.prevent="isOverDropZone = false"
            @drop.prevent="onDropFile"
            ref="dropzoneRef"
            class="flex flex-col gap-1"
          >
            <label for="image" class="text-lg font-bold">사진 선택</label>
            <div class="border-2 border-dashed rounded-md size-56">
              <div
                :class="`${isOverDropZone ? 'bg-gray-200' : ''} flex flex-col items-center justify-center gap-2 size-full`"
              >
                <span class="text-sm">이미지를 추가해주세요.</span>
                <i class="text-2xl pi pi-plus"></i>
              </div>
            </div>
          </div>
          <div class="flex items-center justify-center flex-1 gap-3">
            <ul class="flex items-end w-full h-full gap-3 px-2 overflow-x-auto">
              <li
                v-for="n in 2"
                :key="n"
                class="flex-shrink-0 bg-gray-200 border rounded-md size-56 aspect-square"
              ></li>
            </ul>
          </div>
        </section>

        <!-- 제목 입력 -->
        <div class="flex flex-col gap-1">
          <label for="title" class="text-lg font-bold">제목</label>
          <Input
            type="text"
            id="title"
            class="w-full"
            placeholder="글의 제목을 입력해주세요."
            v-model:model-value="inputVal.title"
          />
        </div>
        <!-- content -->
        <div class="flex flex-col gap-1">
          <label for="title" class="text-lg font-bold">내용</label>
          <Textarea
            id="content"
            class="w-full placeholder-slate-300"
            placeholder="글의 내용을 입력해주세요."
            v-model:model-value="inputVal.content"
          />
        </div>
        <!-- 버튼 Box -->
        <div class="flex gap-3">
          <Button type="submit" class="btn btn-primary">저장</Button>
          <Button type="button" class="btn btn-secondary">취소</Button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
