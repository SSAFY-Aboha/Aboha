<script setup>
import { ref, computed, onMounted } from 'vue'
import { Tabs, TabsList, TabsTrigger, TabsContent } from '../ui/tabs'
import attractionApi from '@/api/attractions'
import abogApi from '@/api/abog'
import userAPI from '@/api/user'
import LikeAttractionTable from '@/components/Mypage/LikeAttractionTable.vue'
import MyAbogTable from '@/components/Mypage/MyAbogTable.vue'
import MyReviewTable from '@/components/Mypage/MyReviewTable.vue'

const isEdit = defineModel('isEdit')

// 탭 데이터를 저장하는 객체
const getTabData = ref({
  'my-place': [],
  'my-review': [],
  'my-abog': [],
})

// 삭제 핸들러를 반환하는 computed 속성
const getDeleteHandler = computed(() => ({
  'my-place': deleteAttraction,
  'my-review': deleteReview,
  'my-abog': deleteAbog,
}))

// 초기 데이터 조회
onMounted(async () => {
  const { data: reviews, error: reviewsError } = await userAPI.getUserReviews()
  if (!reviewsError) {
    console.log('reviews', reviews)
    getTabData.value['my-review'] = reviews?.content
  }

  const { data: abogs, error: abogsError } = await userAPI.getUserAbogs()
  if (!abogsError) {
    console.log('abogs', abogs)
    getTabData.value['my-abog'] = abogs?.content
  }

  const { data: likes, error: likesError } = await userAPI.getUserLikes()
  if (!likesError) {
    console.log('likes', likes)
    getTabData.value['my-place'] = likes?.content
  }
})

const deleteAttraction = async id => {
  const { status, data, error } = await attractionApi.toggleAttractionLike(id)
  if (error) {
    console.error(error)
  }
}

const deleteAbog = async id => {
  console.log('deleteAbog', id)
  const { error } = await abogApi.deleteAbog(id)
  console.error(error)

  // 삭제 후 아보그 목록 조회
  const { data: abogs, error: abogsError } = await userAPI.getUserAbogs()
  if (abogsError) {
    console.error(abogsError)
  }
}

const deleteReview = async id => {
  console.log('deleteReview', id)
  const { error } = await attractionApi.deleteAttractionReview(id)
  if (error) {
    console.error(error)
  }

  // 삭제 후 리뷰 목록 조회
  const { data: reviews, error: reviewsError } = await userAPI.getUserReviews()
  if (reviewsError) {
    console.error(reviewsError)
  }
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
          v-model:data="getTabData[content.value]"
          v-model:isEdit="isEdit"
          @delete-handler="getDeleteHandler[content.value]"
          class="max-h-[calc(100vh-300px)] overflow-y-auto"
        />
      </TabsContent>
    </div>
  </Tabs>
</template>

<style lang="scss" scoped></style>
