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

const props = defineProps({
  data: Object,
})

const { id, nickname, title, content, date, tags, image, like } = props.data
</script>

<template>
  <li
    class="flex flex-col items-center justify-start w-screen max-w-6xl min-w-80"
  >
    <!-- header -->
    <div class="flex flex-col justify-center h-full max-w-xl gap-2">
      <div class="relative flex items-center justify-between gap-2 px-2">
        <!-- user 정보 -->
        <Avatar class="size-8">
          <AvatarImage src="/src/assets/mainPage_image.jpg" alt="avatar" />
        </Avatar>
        <div class="flex items-center justify-start w-full gap-5">
          <div class="flex items-center justify-between flex-1">
            <div>
              <span class="text-sm text-gray-600">{{ nickname }}</span>
              <!-- 장소 마크 -->
              <div class="flex items-center gap-3">
                <i class="text-xs text-gray-500 pi pi-map-marker"></i>
                <span class="text-xs text-gray-600">장소</span>
              </div>
            </div>
            <span class="text-sm text-gray-500 text-end basis-1/3">{{
              date
            }}</span>
          </div>
        </div>
      </div>

      <!-- 제목 -->
      <h1 class="px-2 text-xl font-bold">{{ title }}</h1>

      <!-- 이미지 -->
      <div class="flex items-center justify-center overflow-hidden rounded-xl">
        <Carousel v-slot="{ canScrollNext, canScrollPrev }" class="w-full">
          <CarouselContent>
            <CarouselItem v-for="(_, index) in 3" :key="index">
              <div class="p-1">
                <Card class="">
                  <CardContent
                    class="flex items-center justify-center w-full p-0 overflow-hidden rounded-md aspect-square"
                  >
                    <img
                      class="object-cover w-full h-full col-start-1 col-end-3 row-start-1 row-end-3"
                      :src="image || '../../assets/default_image.png'"
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
              <span class="text-lg text-gray-600">{{ like }}</span>
            </div>
          </div>
          <ul class="flex items-center justify-end w-full gap-2">
            <li v-for="(item, index) in tags" :key="index">
              <div class="px-3 py-1 text-xs bg-gray-200 rounded-full">
                {{ `#${item}` }}
              </div>
            </li>
          </ul>
        </div>
        <!-- 내용 -->
        <p class="text-sm text-gray-600">{{ content }}</p>
        <!-- 댓글 -->
        <div class="pb-2 border-b cursor-pointer">
          <span class="hover:font-bold">댓글 보기</span>
        </div>
      </div>
    </div>
  </li>
</template>

<style scoped></style>
