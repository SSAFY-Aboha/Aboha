<script setup>
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogFooter,
} from '@/components/ui/dialog'
import { Button } from '../../ui/button'
import AttractionFilterSelector from './AttractionFilterSelector.vue'
import AttractionFilterToggle from './AttractionFilterToggle.vue'
import { inject, ref } from 'vue'

const isOpen = ref(false)

const onSearch = inject('handleSearch')
const searchData = inject('searchData')

const handleSearch = () => {
  isOpen.value = false
  // TODO: 검색 처리
  onSearch(searchData.value)
}
</script>

<template>
  <Dialog v-model:open="isOpen">
    <DialogTrigger as-child>
      <Button variant="outline"> 필터 </Button>
    </DialogTrigger>
    <DialogContent class="sm:max-w-[425px] lg:max-w-[600px] overflow-y-auto">
      <!-- Header -->
      <DialogHeader>
        <DialogTitle class="text-xl font-bold">관광지 검색 필터</DialogTitle>
        <DialogDescription class="italic">
          원하는 관광지를 더 자세히 찾아보세요.
        </DialogDescription>
      </DialogHeader>
      <!-- 메인 내용 -->
      <main class="flex flex-col gap-6">
        <section class="flex flex-col gap-3">
          <!-- ! 지역 필터 -->
          <AttractionFilterSelector
            v-model:sidoCode="searchData.sidoCode"
            v-model:gugunCode="searchData.gugunCode"
          />
        </section>
        <!-- ! 주제 선택 -->
        <section class="flex flex-col gap-3">
          <AttractionFilterToggle
            title="테마"
            v-model:contentTypeId="searchData.contentTypeId"
          />
        </section>
      </main>
      <DialogFooter>
        <Button type="submit" @click="handleSearch"> 검색하기 </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
