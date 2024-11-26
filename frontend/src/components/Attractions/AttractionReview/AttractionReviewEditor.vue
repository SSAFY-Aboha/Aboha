<script setup>
import { Button } from '@/components/ui/button'
import {
  DialogFooter,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  Dialog,
} from '@/components/ui/dialog'

import attractionAPI from '@/api/attractions'
import { ref } from 'vue'
import RatingStarForm from '@/components/Attractions/AttractionReview/RatingStarForm.vue'
import { Textarea } from '@/components/ui/textarea'

const reviews = defineModel('reviews')
const props = defineProps({
  attractionId: Number,
})

const reviewData = ref({
  attractionId: props.attractionId,
  rating: 0,
  content: '',
})

const isOpen = ref(false)

// 후기 등록
const handleSubmit = async () => {
  if (!handleCheck()) return

  // 후기 등록
  const { error: postError, data: postData } =
    await attractionAPI.postAttractionReview(reviewData.value)

  if (postError) {
    console.log(postError)
    isOpen.value = false
    return
  }

  /**
   *
// Spring boot 에서 createReview 에서 return 값 변경
Integer reviewId = reviewService.createReview(userResponse.id(), request);
ReviewResponse response = reviewService.getReview(reviewId);

return ResponseEntity.ok().body(response);
   */
  reviews.value.data = [...reviews.value.data, postData]

  // 후기 등록 후 초기화
  reviewData.value = {
    rating: 0,
    content: '',
  }
  alert('후기가 등록되었습니다.')
  isOpen.value = false
  // 후기 등록 후 새로고침
  // window.location.reload()
}

// 후기 작성 가능 여부 체크
const handleCheck = () => {
  if (reviewData.value.rating === 0) {
    alert('평점을 선택해주세요.')
    return false
  } else if (reviewData.value.content === '') {
    alert('후기를 작성해주세요.')
    return false
  } else {
    isOpen.value = true
    return true
  }
}

// 이미 작성한 후기 체크
const alreadyReviewCheck = () => {
  //
}
</script>

<template>
  <Dialog v-model:open="isOpen">
    <DialogTrigger as-child>
      <Button variant="outline" @click="alreadyReviewCheck">
        후기 작성하기
      </Button>
    </DialogTrigger>
    <DialogContent class="max-w-3xl">
      <DialogHeader>
        <DialogTitle class="text-2xl font-bold">후기 작성하기</DialogTitle>
        <DialogDescription>
          여행에 대한 후기를 작성해보세요!
        </DialogDescription>
      </DialogHeader>
      <!-- 평점 -->
      <div class="flex items-center gap-2">
        <span class="text-lg font-bold">평점</span>
        <RatingStarForm v-model:rating="reviewData.rating" />
      </div>
      <!-- 후기 내용 -->
      <div class="flex flex-col items-start gap-2">
        <span class="text-lg font-bold">후기 내용</span>
        <Textarea
          v-model="reviewData.content"
          placeholder="후기를 작성해주세요."
        />
      </div>
      <DialogFooter>
        <Button @click="handleSubmit"> 등록하기 </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>

<style scoped></style>
