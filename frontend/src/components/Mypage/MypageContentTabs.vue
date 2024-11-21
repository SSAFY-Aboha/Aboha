<script setup>
import { ref } from 'vue'
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../ui/tabs'
import attractionApi from '@/api/attractions'
import abogApi from '@/api/abog'
import LikeAttractionTable from '@/components/Mypage/LikeAttractionTable.vue'
import MyAbogTable from '@/components/Mypage/MyAbogTable.vue'
import MyReviewTable from '@/components/Mypage/MyReviewTable.vue'

const isEdit = defineModel('isEdit')

const likeAttractions = ref([
  {
    id: 1,
    image: 'https://picsum.photos/300/300',
    likeCount: 156,
    title: '해운대 해수욕장',
    sidoName: '부산광역시',
    gugunName: '해운대구',
  },
])

const myAbogs = ref([
  {
    id: 1,
    title: '해운대 해수욕장',
    createdAt: '2024-01-01',
    likeCount: 156,
    commentCount: 10,
  },
])

const myReviews = ref([
  {
    id: 1,
    attraction: {
      id: 1,
      title: '해운대 해수욕장',
    },
    rating: 5,
    comment: '해운대 해수욕장 좋아요',
    createdAt: '2024-01-01',
  },
])

const deleteAttraction = id => {
  // 1. 삭제 API 호출
  attractionApi.toggleAttractionLike(id, null, null)

  // 2. 현재 페이지 상태 업데이트
  likeAttractions.value = likeAttractions.value.filter(
    attraction => attraction.id !== id,
  )
}

const deleteAbog = id => {
  // 1. 삭제 API 호출
  abogApi.deleteAbog(id, null, null)

  // 2. 현재 페이지 상태 업데이트
  myAbogs.value = myAbogs.value.filter(abog => abog.id !== id)
}

const deleteReview = id => {
  // 1. 삭제 API 호출
  // attractionApi.deleteReview(id, null, null)

  // 2. 현재 페이지 상태 업데이트
  myReviews.value = myReviews.value.filter(review => review.id !== id)
}
</script>

<template>
  <Tabs default-value="my-place" class="flex flex-col w-full h-screen">
    <TabsList class="w-full">
      <TabsTrigger value="my-place" class="w-full font-bold text-md">
        좋아요 한 관광지
      </TabsTrigger>
      <TabsTrigger value="my-review" class="w-full font-bold text-md">
        내가 작성한 리뷰
      </TabsTrigger>
      <TabsTrigger value="my-abog" class="w-full font-bold text-md">
        나의 아보그
      </TabsTrigger>
    </TabsList>

    <TabsContent
      value="my-place"
      class="w-full p-2 overflow-y-auto border border-gray-200 rounded-lg max-h-[30rem]"
    >
      <LikeAttractionTable
        :title="'좋아요 한 관광지 목록'"
        v-model:data="likeAttractions"
        v-model:isEdit="isEdit"
        @delete-handler="deleteAttraction"
      />
    </TabsContent>
    <TabsContent
      value="my-review"
      class="w-full p-2 overflow-y-auto border border-gray-200 rounded-lg max-h-[30rem]"
    >
      <MyReviewTable
        :title="'내가 작성한 리뷰'"
        v-model:data="myReviews"
        v-model:isEdit="isEdit"
        @delete-handler="deleteReview"
      />
    </TabsContent>
    <TabsContent
      value="my-abog"
      class="w-full p-2 overflow-y-auto border border-gray-200 rounded-lg max-h-[30rem]"
    >
      <MyAbogTable
        :title="'내가 작성한 아보그'"
        v-model:data="myAbogs"
        v-model:isEdit="isEdit"
        @delete-handler="deleteAbog"
      />
    </TabsContent>
  </Tabs>
</template>

<style lang="scss" scoped></style>
