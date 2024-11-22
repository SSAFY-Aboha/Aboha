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
import VueLoading from 'vue-loading-overlay'
import abogApi from '@/api/abog'

import { ref } from 'vue'
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from '../ui/carousel'
import { Card, CardContent } from '../ui/card'
import Dropzone from '../common/Dropzone.vue'
import AttractionSearchBox from './AttractionSearchBox.vue'

const dropzoneRef = ref(null)
const isLoading = ref(false)

const inputVal = ref({
  title: '',
  attraction: '',
  content: '',
  tags: [],
  imageFiles: [],
})

const imageURLs = ref([])

const onDropFile = (files = []) => {
  const fileList = Array.from(files)
  fileList.forEach(file => {
    // 이미지 파일인지 확인
    if (!file.type.includes('image')) {
      alert('이미지 파일만 추가할 수 있습니다.')
      return
    }

    // 이미지 최대 5장 추가
    if (inputVal.value.imageFiles.length >= 5) {
      alert('이미지는 최대 5장까지 추가할 수 있습니다.')
      return
    }

    // png, jpeg 확인
    if (!file.type.includes('image/png') && !file.type.includes('image/jpeg')) {
      alert('이미지 파일 형식은 png, jpeg만 추가할 수 있습니다.')
      return
    }

    // 파일 URL 생성 후 배열에 추가
    const fileURL = URL.createObjectURL(file)

    inputVal.value.imageFiles.push(file)
    imageURLs.value.push(fileURL)
  })
}

const { isOverDropZone } = useDropZone(dropzoneRef, {
  dataTypes: ['image/png', 'image/jpeg'],
  onDrop: onDropFile,
  multiple: true,
  preventDefaultForUnhandled: false,
})

const handleFileDelete = idx => {
  inputVal.value.imageFiles.splice(idx, 1)
  imageURLs.value.splice(idx, 1)
  console.log(imageURLs)
}

const checkInput = () => {
  if (inputVal.value.title === '') {
    alert('제목을 입력해주세요. 🤣')
    return false
  } else if (inputVal.value.attraction === '') {
    alert('장소를 선택해주세요. 🤣')
    return false
  } else if (inputVal.value.content === '') {
    alert('내용을 입력해주세요. 🤣')
    return false
  } else if (inputVal.value.tags.length === 0) {
    alert('태그를 입력해주세요. 🤣')
    return false
  } else if (inputVal.value.imageFiles.length === 0) {
    alert('사진을 추가해주세요. 🤣')
    return false
  }
  return true
}

const handleSubmit = async () => {
  // 내용이 빠지지 않았는지 확인
  if (!checkInput()) return

  // formData 생성
  const formData = new FormData()
  formData.append('title', inputVal.value.title)
  formData.append('attraction', inputVal.value.attraction)
  formData.append('content', inputVal.value.content)
  formData.append('tags', inputVal.value.tags)
  inputVal.value.imageFiles.forEach(file => {
    formData.append('imageFiles', file)
  })

  // api 호출
  try {
    isLoading.value = true
    const res = await abogApi.createAbog(formData)
    alert('아보하 로그가 저장되었습니다.')
  } catch (error) {
    console.error(error)
    alert('아보하 로그 저장에 실패하였습니다.', error.response)
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <!-- 로딩 중 -->
  <VueLoading
    v-model:active="isLoading"
    color="#6bd46b"
    :width="100"
    loader="dots"
  />
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
      <form @submit.prevent="handleSubmit" class="flex flex-col w-full gap-4">
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
            <AttractionSearchBox
              v-model:selectedAttraction="inputVal.attraction"
            />
            <!-- <ComboBox
              class="w-full"
              v-model:model-value="inputVal.attraction"
            /> -->
          </div>
        </div>
        <label for="image" class="text-lg font-bold">
          <span>사진 선택</span>
          <span class="pl-3 text-sm italic text-gray-500">(최대 5장)</span>
        </label>
        <!-- 드로그앤 드롭 -->
        <section class="flex items-center justify-center gap-3">
          <Dropzone ref="dropzoneRef" />
          <div class="flex flex-1 w-full gap-3">
            <Carousel
              v-slot="{ canScrollNext, canScrollPrev }"
              class="w-full max-w-2xl px-2 py-1"
              :opts="{
                align: 'start',
              }"
            >
              <CarouselContent class="w-full -ml-1 rounded-lg">
                <CarouselItem
                  v-for="(img, idx) in imageURLs"
                  :key="img"
                  class="pl-1 md:basis-2/6"
                >
                  <div class="p-1">
                    <Card>
                      <CardContent
                        class="relative flex items-center justify-center p-6 aspect-square"
                      >
                        <img :src="img" alt="이미지" />
                        <button class="hover:text-red-500">
                          <i
                            @click="handleFileDelete(idx)"
                            class="absolute top-0 right-0 p-2 px-1 pi pi-times-circle"
                          ></i>
                        </button>
                      </CardContent>
                    </Card>
                  </div>
                </CarouselItem>
              </CarouselContent>
              <CarouselPrevious class="left-2" v-if="canScrollPrev" />
              <CarouselNext class="right-2" v-if="canScrollNext" />
            </Carousel>
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
