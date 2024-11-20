<script setup>
import AbogCommentItem from '@/components/AbogBoard/AbogCommentItem.vue'
import { Button } from '@/components/ui/button'
import { CardFooter, CardHeader } from '@/components/ui/card'
import Card from '@/components/ui/card/Card.vue'
import CardContent from '@/components/ui/card/CardContent.vue'
import { Input } from '@/components/ui/input'
defineProps({
  commentList: {
    type: Array,
    required: true,
  },
})

const handleInputComment = value => {
  console.log(value)
  // 댓글 작성 로직
}

const emit = defineEmits(['handleOpenComment'])
</script>

<template>
  <Card
    class="w-full h-full max-h-[600px] relative overflow-hidden flex flex-col"
  >
    <CardHeader class="text-lg font-bold">
      <div class="flex items-center justify-between">
        <span>댓글</span>
        <Button variant="outline" class="" @click="emit('handleOpenComment')">
          <i class="pi pi-times"></i>
        </Button>
      </div>
    </CardHeader>
    <!-- 댓글 리스트 -->
    <CardContent class="flex-1 pb-[60px] overflow-auto">
      <ul class="flex flex-col gap-6">
        <AbogCommentItem
          v-for="each in commentList"
          :data="each"
          :key="each.id"
        /></ul
    ></CardContent>
    <!-- 댓글 작성하기 Form -->
    <CardFooter class="absolute bottom-0 left-0 w-full bg-white">
      <div class="flex items-center w-full gap-2">
        <Input
          @update:model-value="handleInputComment"
          placeholder="댓글을 작성해주세요. 작성 후 엔터를 누르면 등록됩니다."
        />
      </div>
    </CardFooter>
  </Card>
</template>

<style scoped></style>
