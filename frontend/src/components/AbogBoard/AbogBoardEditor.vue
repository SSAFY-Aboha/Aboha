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

import { ref, computed, watch } from 'vue'
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
import { useRouter } from 'vue-router'

const router = useRouter()

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
  const messages = {
    title: '제목을 입력해 주세요',
    attraction: '방문하신 장소를 선택해 주세요',
    content: '내용을 입력해 주세요',
    tags: '하나 이상의 태그를 입력해 주세요',
    imageFiles: '최소 한 장의 사진을 추가해 주세요',
  }

  for (const [field, message] of Object.entries(messages)) {
    if (field === 'tags' && inputVal.value[field].length === 0) {
      alert(message)
      return false
    }
    if (field === 'imageFiles' && inputVal.value[field].length === 0) {
      alert(message)
      return false
    }
    if (field !== 'tags' && field !== 'imageFiles' && !inputVal.value[field]) {
      alert(message)
      return false
    }
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
  isLoading.value = true
  const { status, error } = await abogApi.createAbog(formData)

  if (error) {
    alert(error)
    isLoading.value = false
    return
  }

  alert('아보하 로그가 저장되었습니다.')
  router.back()

  isLoading.value = false
}

const handleReset = () => {
  if (
    inputVal.value.title ||
    inputVal.value.content ||
    inputVal.value.tags.length ||
    inputVal.value.imageFiles.length
  ) {
    if (confirm('작성 중인 내용이 모두 삭제됩니다. 계속하시겠습니까?')) {
      inputVal.value = {
        title: '',
        attraction: '',
        content: '',
        tags: [],
        imageFiles: [],
      }
      imageURLs.value = []
      router.back()
    }
  } else {
    router.back()
  }
}

// 폼 유효성 검사를 위한 computed 속성 추가
const isFormValid = computed(() => {
  return (
    inputVal.value.title.length > 0 &&
    inputVal.value.title.length <= 100 &&
    inputVal.value.content.length > 0 &&
    inputVal.value.content.length <= 2000 &&
    inputVal.value.tags.length > 0 &&
    inputVal.value.imageFiles.length > 0 &&
    inputVal.value.attraction
  )
})

// 태그 입력 처리를 위한 watch 추가
watch(
  () => inputVal.value.tags,
  newTags => {
    if (newTags.length > 3) {
      inputVal.value.tags = newTags.slice(0, 3)
      alert('태그는 최대 3개까지만 입력 가능합니다.')
    }
  },
  { deep: true },
)
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
      <form
        @submit.prevent="handleSubmit"
        @reset="handleReset"
        class="flex flex-col w-full gap-4"
      >
        <!-- 입력 input -->
        <!-- 태그와 장소 선택 섹션 -->
        <div class="space-y-6">
          <!-- 태그 선택 -->
          <div class="flex flex-col space-y-2">
            <div class="flex items-center justify-between">
              <label class="text-lg font-bold">태그 선택</label>
              <span class="text-sm text-gray-500">
                ({{ inputVal.tags.length }}/3개)
              </span>
            </div>

            <div class="relative">
              <TagsInput
                class="min-h-[42px] transition-shadow focus-within:shadow-md rounded-lg"
                :class="{ 'border-red-500': inputVal.tags.length > 3 }"
                v-model="inputVal.tags"
              >
                <TagsInputItem
                  v-for="item in inputVal.tags"
                  :key="item"
                  :value="item"
                  class="transition-colors bg-primary/10 text-primary hover:bg-primary/20"
                >
                  <TagsInputItemText />
                  <TagsInputItemDelete class="hover:text-red-500" />
                </TagsInputItem>
                <TagsInputInput
                  :placeholder="
                    inputVal.tags.length >= 3
                      ? '태그는 최대 3개까지 가능합니다'
                      : '태그를 입력하고 Enter를 눌러주세요'
                  "
                  :disabled="inputVal.tags.length >= 3"
                />
              </TagsInput>

              <p class="mt-1 text-xs text-gray-500">
                <i class="mr-1 pi pi-info-circle"></i>
                태그는 방문하신 장소의 특징을 나타내는 키워드를 입력해주세요.
                (예: 데이트, 가족여행, 포토스팟)
              </p>
            </div>
          </div>

          <!-- 장소 선택 -->
          <div class="flex flex-col space-y-2">
            <label class="text-lg font-bold">방문 장소</label>
            <div class="relative">
              <AttractionSearchBox
                v-model:selectedAttraction="inputVal.attraction"
                class="transition-shadow focus-within:shadow-md"
              />
              <p class="mt-1 text-xs text-gray-500">
                <i class="mr-1 pi pi-map-marker"></i>
                방문하신 장소를 검색하여 선택해주세요
              </p>
            </div>
          </div>
        </div>
        <!-- 이미지 업로드 섹션 -->
        <section class="space-y-4">
          <div class="flex items-center gap-2">
            <label for="image" class="text-lg font-bold">사진 선택</label>
            <span class="text-sm text-gray-500">
              ({{ imageURLs.length }}/5장)
            </span>
          </div>

          <div class="flex flex-col gap-4 sm:flex-row">
            <!-- 드래그 앤 드롭 영역 -->
            <div class="w-full sm:w-1/3">
              <Dropzone
                ref="dropzoneRef"
                class="min-h-[200px] transition-all duration-200"
                :class="{
                  'border-primary ring-2 ring-primary/20': isOverDropZone,
                }"
              >
                <div class="flex flex-col items-center gap-2 text-gray-500">
                  <i class="text-3xl pi pi-image"></i>
                  <p class="text-sm text-center">
                    이미지를 드래그하거나<br />
                    클릭하여 업로드하세요
                  </p>
                  <p class="text-xs">(PNG, JPEG 형식만 가능)</p>
                </div>
              </Dropzone>
            </div>

            <!-- 이미지 미리보기 영역 -->
            <div class="flex-1">
              <div
                v-if="imageURLs.length === 0"
                class="flex items-center justify-center h-full min-h-[200px] border-2 border-dashed rounded-lg"
              >
                <p class="text-gray-400">업로드된 이미지가 없습니다</p>
              </div>

              <Carousel
                v-else
                v-slot="{ canScrollNext, canScrollPrev }"
                class="relative w-full"
                :opts="{
                  align: 'start',
                }"
              >
                <CarouselContent class="rounded-lg">
                  <CarouselItem
                    v-for="(img, idx) in imageURLs"
                    :key="img"
                    class="sm:basis-1/3 md:basis-1/4 lg:basis-1/5"
                  >
                    <Card
                      class="overflow-hidden transition-shadow border hover:shadow-md"
                    >
                      <CardContent class="relative p-0">
                        <!-- 이미지 -->
                        <img
                          :src="img"
                          :alt="`이미지 ${idx + 1}`"
                          class="object-cover w-full aspect-square"
                        />

                        <!-- 삭제 버튼 -->
                        <button
                          @click="handleFileDelete(idx)"
                          class="absolute top-2 right-2 p-1.5 rounded-full bg-white/80 hover:bg-red-500 hover:text-white transition-colors shadow-sm"
                        >
                          <i class="pi pi-times"></i>
                        </button>

                        <!-- 이미지 번호 -->
                        <div
                          class="absolute px-2 py-1 text-xs text-white rounded-full bottom-2 left-2 bg-black/50"
                        >
                          {{ idx + 1 }}/{{ imageURLs.length }}
                        </div>
                      </CardContent>
                    </Card>
                  </CarouselItem>
                </CarouselContent>

                <!-- 네비게이션 버튼 -->
                <CarouselPrevious
                  v-if="canScrollPrev"
                  class="left-0 -translate-x-1/2 bg-white/90 hover:bg-white"
                />
                <CarouselNext
                  v-if="canScrollNext"
                  class="right-0 translate-x-1/2 bg-white/90 hover:bg-white"
                />
              </Carousel>
            </div>
          </div>
        </section>

        <!-- 메인 콘텐츠 영역 -->
        <div class="space-y-6">
          <!-- 제목 입력 -->
          <div class="space-y-2">
            <label
              for="title"
              class="flex items-center gap-2 text-lg font-bold"
            >
              제목
              <span class="text-sm font-normal text-gray-500">
                ({{ inputVal.title.length }}/100자)
              </span>
            </label>
            <Input
              type="text"
              id="title"
              class="w-full text-lg transition-shadow focus:shadow-md"
              :class="{ 'border-red-500': inputVal.title.length > 100 }"
              placeholder="멋진 제목을 입력해주세요"
              maxlength="100"
              v-model:model-value="inputVal.title"
            />
          </div>

          <!-- 내용 입력 -->
          <div class="space-y-2">
            <label for="content" class="flex items-center justify-between">
              <span class="text-lg font-bold">내용</span>
              <span class="text-sm text-gray-500">
                ({{ inputVal.content.length }}/2000자)
              </span>
            </label>
            <Textarea
              id="content"
              class="w-full min-h-[200px] transition-shadow focus:shadow-md"
              :class="{ 'border-red-500': inputVal.content.length > 2000 }"
              placeholder="방문하신 장소 혹은 오늘의 챌린지에 대한 경험을 자유롭게 작성해주세요. :)"
              maxlength="2000"
              v-model:model-value="inputVal.content"
            />
          </div>

          <!-- 버튼 영역 -->
          <div class="flex gap-4 pt-4 border-t">
            <Button
              type="submit"
              class="flex-1 max-w-xs py-6 text-lg transition-transform btn btn-primary hover:scale-[1.02]"
              :disabled="!isFormValid"
            >
              <i class="mr-2 pi pi-check"></i>
              작성 완료
            </Button>
            <Button
              type="reset"
              class="px-6 py-6 transition-colors btn hover:bg-red-50"
            >
              <i class="mr-2 pi pi-times"></i>
              취소
            </Button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
