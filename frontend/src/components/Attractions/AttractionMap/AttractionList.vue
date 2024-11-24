<script setup>
import { inject, ref } from 'vue'
import { useInfiniteScroll } from '@/composables/useInfiniteScroll'
import attractionAPI from '@/api/attractions'
import AttractionSkeleton from '@/components/Attractions/AttractionSkeleton.vue'
import AttractionHasMore from '../../common/DataHasMore.vue'
import defaultImage from '@/assets/default_image.png'
import RatingStar from '@/components/common/RatingStar.vue'
import { Skeleton } from '@/components/ui/skeleton'

const attractionList = defineModel('attractionList')
const isLoading = defineModel('isLoading')

const searchParams = inject('searchParams')
const pageNo = inject('pageNo')
const hasMore = inject('hasMore')
const setCenter = inject('setCenter')
const setOneMarker = inject('setOneMarker')
const observerTarget = ref(null) // 무한 스크롤 타겟

const handleGetAttraction = async () => {
  // 로딩 시작
  isLoading.value = true
  const { status, data, error } = await attractionAPI.getAttractions(
    { ...searchParams.value, page: pageNo.value },
    () => console.log('성공'),
    res => console.log('조회 실패', res),
  )
  if (status === 200) {
    const {
      content,
      hasNext,
      pageNumber,
      totalElements,
      lastId,
      lastSortValue,
    } = data

    if (content && content.length > 0) {
      attractionList.value = [...attractionList.value, ...content]
    } else {
      hasMore.value = false
    }

    searchParams.value.lastId = lastId
    searchParams.value.lastSortValue = lastSortValue

    hasNext && (pageNo.value = pageNumber + 1) // 다음 페이지 존재 시 페이지 번호 업데이트
    !hasNext && (hasMore.value = false) // 다음 페이지 없을 시 더 가져올 데이터 없음 처리
  }

  // map 중앙 위치 업데이트
  // mapCenter.value = {
  //   lat: attractionList.value[0].latitude,
  //   lng: attractionList.value[0].longitude,
  // }
  // 로딩 완료
  isLoading.value = false
}

// 무한 스크롤
useInfiniteScroll(observerTarget, () => {
  hasMore.value && handleGetAttraction() // 더 가져올 데이터가 있는 경우 관광지 조회
})

const onClickAttraction = async id => {
  const { data, error, status } = await attractionAPI.getAttractionDetail(id)

  const { latitude, longitude } = data
  setCenter(latitude, longitude)
  setOneMarker(latitude, longitude)
}
</script>

<template>
  <ul class="flex flex-col h-full overflow-y-auto gap-y-2">
    <li
      @click="onClickAttraction(attraction.id)"
      v-for="attraction in attractionList"
      :key="attraction.id"
      class="px-2 py-2 transition-all border-b border-gray-200 cursor-pointer hover:bg-gray-100"
    >
      <div class="flex h-full gap-x-2">
        <div class="w-20 h-20 overflow-hidden bg-slate-200 basis-1/3">
          <img
            class="object-cover"
            :src="attraction.image || defaultImage"
            alt="이미지"
            loading="lazy"
          />
        </div>
        <div class="flex flex-col justify-between w-full h-full">
          <div class="flex flex-col">
            <div class="flex items-center justify-between w-full">
              <h1 class="text-lg font-bold">{{ attraction.title }}</h1>
            </div>
            <span class="text-sm text-gray-500">
              {{ `${attraction.sidoName} ${attraction.gugunName}` }}
            </span>
          </div>
          <!-- 별점 -->
          <RatingStar :rating="5" />
        </div>
      </div>
    </li>

    <!-- 스켈레톤 및 관찰 대상 요소, isLoading 상태일 때 로드 중 표시 -->
    <div ref="observerTarget" class="w-full">
      <div v-if="isLoading">
        <div class="flex flex-col gap-3">
          <Skeleton
            v-for="each in 4"
            :key="each"
            class="w-full h-24 rounded-xl"
          />
        </div>
      </div>

      <!-- 더 이상 로드할 데이터가 없는 경우 -->
      <div v-if="!hasMore && !isLoading">
        <AttractionHasMore />
      </div>
    </div>
  </ul>
</template>

<style scoped></style>
