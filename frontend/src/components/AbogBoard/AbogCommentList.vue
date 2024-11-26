<script setup>
import AbogCommentItem from '@/components/AbogBoard/AbogCommentItem.vue'
import { Button } from '@/components/ui/button'
import { CardFooter, CardHeader } from '@/components/ui/card'
import Card from '@/components/ui/card/Card.vue'
import CardContent from '@/components/ui/card/CardContent.vue'
import { Input } from '@/components/ui/input'
import abogApi from '@/api/abog'
import { onMounted, ref } from 'vue'

const props = defineProps({
  abogId: {
    type: Number,
    required: true,
  },
  userId: {
    type: Number,
    required: true,
  },
})

const commentList = ref([])

const comment = ref({
  userId: props.userId,
  content: '',
})

// 댓글
onMounted(async () => {
  const { data, error } = await abogApi.getAbogComments(props.abogId)
  if (error) {
    console.error(error)
    return
  }
  commentList.value = data
})

const handleAddComment = async () => {
  console.log('comment', comment.value)
  // 댓글 작성 로직
  const { data, error } = await abogApi.addAbogComment(
    props.abogId,
    comment.value.content,
  )
  if (error) {
    alert(error)
    return
  }

  comment.value.content = ''

  const { data: newData, error: newError } = await abogApi.getAbogComments(
    props.abogId,
  )
  if (newError) {
    console.error(newError)
    return
  }
  commentList.value = newData

  // 댓글 작성 후 스크롤 맨 아래로 이동
  const commentListElement = document.querySelector('.comment-list')
  if (commentListElement) {
    commentListElement.scrollTop = commentListElement.scrollHeight
  }
}

const handleDeleteComment = async commentId => {
  if (!confirm('댓글을 삭제하시겠습니까?')) {
    return
  }

  const { error } = await abogApi.deleteAbogComment(commentId)
  if (error) {
    alert(error)
    return
  }
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
      <ul class="flex flex-col gap-6 comment-list">
        <AbogCommentItem
          v-for="each in commentList"
          :data="each"
          :key="each.id"
          @handleDeleteComment="handleDeleteComment"
        /></ul
    ></CardContent>
    <!-- 댓글 작성하기 Form -->
    <CardFooter class="absolute bottom-0 left-0 w-full bg-white">
      <div class="flex items-center w-full gap-2">
        <Input
          @keyup.enter="handleAddComment"
          v-model="comment.content"
          placeholder="댓글을 작성해주세요. 작성 후 엔터를 누르면 등록됩니다."
        />
      </div>
    </CardFooter>
  </Card>
</template>

<style scoped></style>
