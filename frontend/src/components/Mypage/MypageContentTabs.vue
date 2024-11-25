<script setup>
import { ref, computed } from 'vue'
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

// 현재 선택된 탭의 데이터를 반환하는 computed 속성
const getTabData = computed(() => ({
  'my-place': likeAttractions,
  'my-review': myReviews,
  'my-abog': myAbogs,
}))

// 삭제 핸들러를 반환하는 computed 속성
const getDeleteHandler = computed(() => ({
  'my-place': deleteAttraction,
  'my-review': deleteReview,
  'my-abog': deleteAbog,
}))

const deleteAttraction = id => {
  attractionApi.toggleAttractionLike(id, null, null)
  likeAttractions.value = likeAttractions.value.filter(
    attraction => attraction.id !== id,
  )
}

const deleteAbog = id => {
  abogApi.deleteAbog(id, null, null)
  myAbogs.value = myAbogs.value.filter(abog => abog.id !== id)
}

const deleteReview = id => {
  myReviews.value = myReviews.value.filter(review => review.id !== id)
}
</script>

<template>
  <Tabs default-value="my-place" class="w-full space-y-6">
    <TabsList class="grid w-full grid-cols-3 p-1 bg-gray-100 rounded-lg">
      <TabsTrigger
        v-for="tab in [
          { value: 'my-place', label: '좋아요 한 관광지', icon: 'pi-heart' },
          { value: 'my-review', label: '내가 작성한 리뷰', icon: 'pi-comment' },
          { value: 'my-abog', label: '나의 아보그', icon: 'pi-file-edit' },
        ]"
        :key="tab.value"
        :value="tab.value"
        class="flex items-center justify-center py-3 transition-all gap-x-2 data-[state=active]:shadow-sm"
      >
        <i :class="`pi ${tab.icon}`" class="pr-2"></i>
        <span class="font-medium">{{ tab.label }}</span>
      </TabsTrigger>
    </TabsList>

    <div class="p-6 bg-white rounded-lg shadow-sm">
      <TabsContent
        v-for="content in [
          {
            value: 'my-place',
            component: LikeAttractionTable,
            title: '좋아요 한 관광지 목록',
          },
          {
            value: 'my-review',
            component: MyReviewTable,
            title: '내가 작성한 리뷰',
          },
          { value: 'my-abog', component: MyAbogTable, title: '나의 아보그' },
        ]"
        :key="content.value"
        :value="content.value"
        class="mt-0 focus-visible:outline-none"
      >
        <component
          :is="content.component"
          :title="content.title"
          :data="getTabData[content.value].value"
          @update:data="
            newValue => (getTabData[content.value].value = newValue)
          "
          v-model:isEdit="isEdit"
          @delete-handler="getDeleteHandler[content.value]"
          class="max-h-[calc(100vh-300px)] overflow-y-auto"
        />
      </TabsContent>
    </div>
  </Tabs>
</template>

<style lang="scss" scoped></style>
