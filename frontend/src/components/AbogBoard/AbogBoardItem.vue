<script setup>
import AbogCommentList from '@/components/AbogBoard/AbogCommentList.vue'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
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
import { UserIcon } from 'lucide-vue-next'
import { Badge } from '../ui/badge'
import AvatarIcon from '../common/AvatarIcon.vue'

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
  tags,
} = abogData.value

const { id: userId, nickname, profileImageUrl } = userData.value

const { id: attractionId, title: attractionTitle } = attractionData.value

const isOpenComment = ref(false)

// 좋아요
const isLiked = ref(props.data.isLiked)
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
  <article class="w-full p-4">
    <!-- 헤더 영역 -->
    <div class="flex items-center gap-3 mb-4">
      <AvatarIcon :src="profileImageUrl" />

      <div class="flex-1">
        <div class="flex items-center justify-between">
          <span class="font-medium text-gray-900">{{ nickname }}</span>
          <span class="text-sm text-gray-500">{{ createdAt }}</span>
        </div>
        <div class="flex items-center text-sm text-gray-500">
          <i class="mr-1 pi pi-map-marker"></i>
          <span>{{ attractionTitle }}</span>
        </div>
      </div>
    </div>

    <!-- 이미지 캐러셀 -->
    <div class="mb-4 overflow-hidden rounded-lg">
      <Carousel class="w-full" v-slot="{ canScrollNext, canScrollPrev }">
        <CarouselContent>
          <CarouselItem v-for="image in images" :key="image">
            <div class="aspect-square">
              <img
                :src="`${BASE_URL}${image}`"
                alt=""
                class="object-cover w-full h-full"
              />
            </div>
          </CarouselItem>
        </CarouselContent>
        <CarouselPrevious v-if="canScrollPrev" />
        <CarouselNext v-if="canScrollNext" />
      </Carousel>
    </div>

    <!-- 컨텐츠 영역 -->
    <div class="space-y-3">
      <div class="flex items-center justify-between">
        <div class="flex items-center gap-2">
          <button @click="handleLike" class="flex items-center gap-1">
            <i
              class="text-2xl pi"
              :class="{
                'text-red-500': isLiked,
                'pi-heart-fill': isLiked,
                'pi-heart': !isLiked,
              }"
            ></i>
            <span>{{ likeCounted }}</span>
          </button>
        </div>
        <!-- 태그 영역 -->
        <div class="flex items-center gap-3">
          <Badge
            variant="outline"
            v-for="tag in tags"
            :key="tag"
            class="text-sm text-gray-500 bg-gray-100"
            ># {{ tag }}</Badge
          >
        </div>
      </div>

      <h2 class="text-lg font-bold">{{ abogTitle }}</h2>
      <p class="text-gray-600">{{ content }}</p>

      <!-- 댓글 섹션 -->
      <div class="pt-3">
        <button
          @click="handleOpenComment"
          class="text-gray-600 hover:text-gray-900"
        >
          {{ isOpenComment ? '댓글 닫기' : '댓글 보기...' }}
        </button>

        <Transition
          enter-active-class="transition-all duration-300 ease-out"
          enter-from-class="opacity-0 max-h-0"
          enter-to-class="opacity-100 max-h-[1000px]"
          leave-active-class="transition-all duration-200 ease-in"
          leave-from-class="opacity-100 max-h-[1000px]"
          leave-to-class="opacity-0 max-h-0"
        >
          <div v-if="isOpenComment" class="mt-4">
            <AbogCommentList
              :abogId="id"
              :userId="userId"
              @handleOpenComment="handleOpenComment"
            />
          </div>
        </Transition>
      </div>
    </div>
  </article>
</template>

<style scoped>
.v-enter-active,
.v-leave-active {
  overflow: hidden;
}
</style>
