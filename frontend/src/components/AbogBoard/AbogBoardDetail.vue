<script setup>
import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Card, CardContent } from '@/components/ui/card'
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from '@/components/ui/carousel'
import { onMounted, ref } from 'vue'

const props = defineProps({
  abogId: String,
})

const data = ref({
  id: 1,
  nickname: 'nickname',
  title: '여기 다녀왔어요!',
  date: '2021. 10. 10',
  content:
    '어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구어쩌구 저쩌구',
  createdAt: '2021-10-10',
  updatedAt: '2021-10-10',
  tags: ['tag1', 'tag2', 'tag3'],
  image: '/src/assets/mainPage_image.jpg',
  like: 10,
  comment: 10,
  view: 10,
})

onMounted(() => {
  // 여기서 abogId를 이용해서 데이터를 가져올 수 있음
  console.log('mounted')
})
</script>

<template>
  <div
    class="absolute flex flex-col items-center justify-start w-screen h-full max-w-5xl -translate-x-1/2 -translate-y-1/2 min-w-80 top-1/2 left-1/2 backdrop-blur-sm"
  >
    <!-- header -->
    <div class="flex flex-col justify-center h-full max-w-md gap-2">
      <div class="relative flex items-center justify-between gap-2 px-2">
        <!-- 뒤로가기 버튼 -->
        <div class="absolute z-10 flex items-center w-full gap-4 mt-3 -left-12">
          <router-link
            :to="{ name: 'abog-main' }"
            class="p-2 py-1 hover:bg-gray-50"
          >
            <i class="text-black pi pi-arrow-left"></i>
          </router-link>
        </div>
        <!-- user 정보 -->
        <Avatar class="size-8">
          <AvatarImage src="/src/assets/mainPage_image.jpg" alt="avatar" />
        </Avatar>
        <div class="flex items-center justify-start w-full gap-5">
          <div class="flex items-center justify-between flex-1">
            <div>
              <span class="text-sm text-gray-600">{{ data.nickname }}</span>
              <!-- 장소 마크 -->
              <div class="flex items-center gap-3">
                <i class="text-xs text-gray-500 pi pi-map-marker"></i>
                <span class="text-xs text-gray-600">장소</span>
              </div>
            </div>
            <span class="text-sm text-gray-500 text-end basis-1/3">{{
              data.date
            }}</span>
          </div>
        </div>
      </div>

      <!-- 제목 -->
      <h1 class="px-2 text-xl font-bold">{{ data.title }}</h1>

      <!-- 이미지 -->
      <div class="flex items-center justify-center overflow-hidden rounded-xl">
        <Carousel v-slot="{ canScrollNext, canScrollPrev }" class="w-full">
          <CarouselContent>
            <CarouselItem v-for="(_, index) in 1" :key="index">
              <div class="p-1">
                <Card class="">
                  <CardContent
                    class="flex items-center justify-center w-full p-0 overflow-hidden rounded-md aspect-square"
                  >
                    <img
                      class="object-cover w-full h-full col-start-1 col-end-3 row-start-1 row-end-3"
                      :src="data.image"
                      alt=""
                    />
                  </CardContent>
                </Card>
              </div>
            </CarouselItem>
          </CarouselContent>
          <CarouselPrevious v-if="canScrollPrev" />
          <CarouselNext v-if="canScrollNext" />
        </Carousel>
      </div>

      <div class="flex flex-col gap-3 px-2">
        <!-- 날짜 & 태그 -->
        <div class="flex items-center">
          <div class="flex items-center justify-between w-full gap-2 px-2">
            <div class="flex items-center gap-2">
              <i
                class="text-2xl text-gray-500 cursor-pointer hover:text-red-500 pi pi-heart"
              ></i>
              <span class="text-lg text-gray-600">{{ data.like }}</span>
            </div>
          </div>
          <ul class="flex items-center justify-end w-full gap-2">
            <li v-for="(item, index) in data.tags" :key="index">
              <div class="px-3 py-1 text-xs bg-gray-200 rounded-full">
                {{ `#${item}` }}
              </div>
            </li>
          </ul>
        </div>
        <!-- 내용 -->
        <p class="text-sm text-gray-600">{{ data.content }}</p>
        <!-- 댓글 -->
        <div class="cursor-pointer">
          <span class="hover:font-bold">댓글 보기</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
