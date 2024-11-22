<script setup>
import { ref } from 'vue'
import AttractionCard from '../common/AttractionCard.vue'
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../ui/tabs'
import attractionApi from '@/api/attractions'
import abogApi from '@/api/abog'

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

const myAbogs = ref([])

const deleteAttraction = id => {
  // 1. 삭제 API 호출
  attractionApi.toggleAttractionLike(id, null, null)

  // 2. 현재 페이지 상태 업데이트
  likeAttractions.value = likeAttractions.value.filter(
    attraction => attraction.id !== id,
  )
  console.log(likeAttractions.value)
}

const deleteAbog = id => {
  // 1. 삭제 API 호출
  abogApi.deleteAbog(id, null, null)

  // 2. 현재 페이지 상태 업데이트
  myAbogs.value = myAbogs.value.filter(abog => abog.id !== id)
  console.log(myAbogs.value)
}
</script>

<template>
  <Tabs default-value="my-place" class="w-full h-screen">
    <TabsList class="w-full">
      <TabsTrigger value="my-place" class="w-full font-bold text-md">
        좋아요 한 관광지
      </TabsTrigger>
      <TabsTrigger value="my-aboge" class="w-full"> 나의 아보그 </TabsTrigger>
      <TabsTrigger value="my-plan" class="w-full"> 나의 여행 계획 </TabsTrigger>
    </TabsList>

    <TabsContent value="my-place" class="flex flex-col w-full px-1 gap-y-5">
      <ul
        class="grid grid-cols-2 gap-4 justify-items-center 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3"
      >
        <AttractionCard
          v-for="attraction in likeAttractions"
          :data="attraction"
          :key="attraction.id"
          :is-edit="isEdit"
          @delete-handler="deleteAttraction"
        />
      </ul>
    </TabsContent>
    <TabsContent value="my-aboge">
      <ul
        class="grid grid-cols-2 gap-4 justify-items-center 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3"
      >
        <!-- 아보그 카드로 변경 -->
        <!-- <AttractionCard
          v-for="abog in myAbogs"
          :data="abog"
          :key="abog.id"
          :is-edit="isEdit"
          @delete-handler="deleteAbog"
        /> -->
      </ul>
    </TabsContent>
    <TabsContent value="my-plan">
      <div class="flex items-center justify-center w-full h-full">
        <h1 class="text-2xl font-bold">아직 준비중인 기능입니다.</h1>
      </div>
    </TabsContent>
  </Tabs>
</template>

<style lang="scss" scoped></style>
