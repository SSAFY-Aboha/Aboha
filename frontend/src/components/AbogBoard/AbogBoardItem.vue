<script setup>
import AbogCommentList from '@/components/AbogBoard/AbogCommentList.vue'
import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Card, CardContent } from '@/components/ui/card'
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from '@/components/ui/carousel'
import { computed, onMounted, ref } from 'vue'
import abogApi from '@/api/abog'

const props = defineProps({
  data: Object,
})

const abogData = computed(() => props.data.abog)
const attractionData = computed(() => props.data.attraction)
const userData = computed(() => props.data.user)

const isOpenComment = ref(false)
const commentList = ref([])

onMounted(async () => {
  try {
    const data = await abogApi.getAbogComments(abogData.value.id)
    commentList.value = data
  } catch (error) {
    console.log('댓글 ', error)
  }
})

const handleOpenComment = () => {
  isOpenComment.value = !isOpenComment.value
}

const {
  id,
  title: abogTitle,
  content,
  likeCount,
  commentCount,
  createdAt,
  images,
} = abogData.value

const { id: userId, nickname, profileImageUrl } = userData.value

const { id: attractionId, title: attractionTitle } = attractionData.value
</script>

<template>
  <li class="flex items-center gap-4">
    <div
      class="flex flex-col items-center justify-start w-screen max-w-6xl min-w-80"
    >
      <!-- header -->
      <div class="flex flex-col justify-center h-full max-w-xl gap-2">
        <div class="relative flex items-center justify-between gap-2 px-2">
          <!-- user 정보 -->
          <Avatar class="size-8">
            <AvatarImage
              :src="profileImageUrl || `/src/assets/mainPage_image.jpg`"
              alt="avatar"
            />
          </Avatar>
          <div class="flex items-center justify-start w-full gap-5">
            <div class="flex items-center justify-between flex-1">
              <div>
                <span class="text-sm text-gray-600">{{ nickname }}</span>
                <!-- 장소 마크 -->
                <div class="flex items-center gap-3">
                  <i class="text-xs text-gray-500 pi pi-map-marker"></i>
                  <span class="text-xs text-gray-600">{{
                    attractionTitle
                  }}</span>
                </div>
              </div>
              <span class="text-sm text-gray-500 text-end basis-1/3">{{
                date
              }}</span>
            </div>
          </div>
        </div>

        <!-- 제목 -->
        <h1 class="px-2 text-xl font-bold">{{ abogTitle }}</h1>

        <!-- 이미지 -->
        <div
          class="flex items-center justify-center overflow-hidden rounded-xl"
        >
          <Carousel v-slot="{ canScrollNext, canScrollPrev }" class="w-full">
            <CarouselContent>
              <CarouselItem v-for="image in images" :key="image">
                <div class="p-1">
                  <Card class="">
                    <CardContent
                      class="flex items-center justify-center w-full p-0 overflow-hidden rounded-md aspect-square"
                    >
                      <img
                        class="object-cover w-full h-full col-start-1 col-end-3 row-start-1 row-end-3"
                        :src="image || '/src/assets/default_image.png'"
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
                <span class="text-lg text-gray-600">{{ likeCount }}</span>
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
          <div class="pb-2 border-b cursor-pointer" @click="handleOpenComment">
            <span class="hover:font-bold">댓글 보기</span>
          </div>
        </div>
      </div>
    </div>
    <!-- 댓글 리스트 -->
    <AbogCommentList
      v-if="isOpenComment"
      :commentList="commentList"
      @handleOpenComment="handleOpenComment"
    />
  </li>
</template>

<style scoped></style>
