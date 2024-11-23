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
import { computed, ref } from 'vue'
import abogApi from '@/api/abog'

const props = defineProps({
  data: Object,
})

const BASE_URL = import.meta.env.VITE_API_BASE_URL

const abogData = computed(() => props.data.abog)
const attractionData = computed(() => props.data.attraction)
const userData = computed(() => props.data.user)

const {
  id,
  title: abogTitle,
  content,
  likeCount,
  commentCount,
  createdAt,
  updatedAt,
  images,
} = abogData.value

const { id: userId, nickname, profileImageUrl } = userData.value

const { id: attractionId, title: attractionTitle } = attractionData.value

const isOpenComment = ref(false)

// 좋아요
const isLiked = ref(props.data.abog.isLiked)
const likeCounted = computed(() => abogData.value.likeCount)

const handleOpenComment = () => {
  isOpenComment.value = !isOpenComment.value
}

const handleLike = async () => {
  const { data, error } = await abogApi.toggleAbogLike(abogData.value.id)

  if (error) {
    alert(error)
    return
  }

  isLiked.value = !isLiked.value
  isLiked.value = data.isLiked
  abogData.value.likeCount += data.isLiked ? 1 : -1
}
</script>

<template>
  <li class="flex flex-col items-center gap-4 xl:flex-row">
    <div
      class="flex flex-col items-center justify-start w-screen max-w-6xl min-w-80"
    >
      <!-- header -->
      <div class="flex flex-col justify-center h-full max-w-xl gap-2">
        <div class="relative flex items-center justify-between gap-2 px-2">
          <!-- user 정보 -->
          <Avatar class="size-10">
            <AvatarImage
              :src="profileImageUrl || `/src/assets/mainPage_image.jpg`"
              alt="avatar"
            />
          </Avatar>
          <div class="flex items-center justify-start w-full gap-5">
            <div class="flex items-baseline justify-between flex-1">
              <div>
                <span class="text-gray-600 text-md">{{ nickname }}</span>
                <!-- 장소 마크 -->
                <div class="flex items-center gap-3">
                  <i class="text-xs text-gray-500 pi pi-map-marker"></i>
                  <span class="text-xs text-gray-600">{{
                    attractionTitle
                  }}</span>
                </div>
              </div>
              <span class="text-sm text-gray-500 text-end basis-1/3">{{
                createdAt
              }}</span>
            </div>
          </div>
        </div>

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
                      class="flex items-center justify-center p-0 overflow-hidden rounded-md min-w-[450px] md:w-[500px] aspect-square"
                    >
                      <img
                        class="object-cover w-full h-full col-start-1 col-end-3 row-start-1 row-end-3"
                        :src="
                          `${BASE_URL}${image}` ||
                          '/src/assets/default_image.png'
                        "
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
            <div class="flex items-center justify-between w-full gap-2">
              <div class="flex items-center gap-2">
                <i
                  @click="handleLike"
                  class="text-2xl text-gray-500 cursor-pointer hover:text-red-500 pi"
                  :class="{
                    'text-red-500': isLiked,
                    'pi-heart-fill': isLiked,
                    'pi-heart': !isLiked,
                  }"
                ></i>
                <span class="text-gray-600 text-md">{{ likeCounted }}</span>
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
          <!-- 제목 -->
          <h1 class="text-lg font-bold">{{ `" ${abogTitle} "` }}</h1>
          <!-- 내용 -->
          <p class="text-sm text-gray-600">{{ content }}</p>
          <!-- 댓글 -->
          <div
            class="flex items-center justify-start py-2 border-b cursor-pointer"
            @click="handleOpenComment"
          >
            <span class="hover:font-bold">{{
              isOpenComment ? '댓글 닫기' : '댓글 보기...'
            }}</span>
          </div>
        </div>
      </div>
    </div>
    <!-- 댓글 리스트 -->
    <Transition
      enter-active-class="transition duration-300 ease-out"
      enter-from-class="transform -translate-x-full opacity-0"
      enter-to-class="transform translate-x-0 opacity-100"
      leave-active-class="transition duration-200 ease-in"
      leave-from-class="transform translate-x-0 opacity-100"
      leave-to-class="transform -translate-x-full opacity-0"
    >
      <AbogCommentList
        v-if="isOpenComment"
        :abogId="id"
        :userId="userId"
        @handleOpenComment="handleOpenComment"
      />
    </Transition>
  </li>
</template>

<style scoped>
.v-enter-active,
.v-leave-active {
  overflow: hidden;
}
</style>
