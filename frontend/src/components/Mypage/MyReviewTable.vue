<script setup>
import {
  Table,
  TableCaption,
  TableHeader,
  TableBody,
  TableRow,
  TableHead,
  TableCell,
} from '@/components/ui/table'
import { ref, onMounted } from 'vue'
import userAPI from '@/api/user'

defineProps({
  title: {
    type: String,
    required: true,
  },
})

const isEdit = defineModel('isEdit', { type: Boolean })

const reviews = ref([
  {
    attractionId: 0,
    title: '',
    reviewId: 0,
    createdAt: '',
    content: '',
    rating: 0,
  },
])

onMounted(async () => {
  const { status, data, error } = await userAPI.getUserReviews()
  if (error) {
    console.error(error)
    return
  }
  reviews.value = data.content
})

const emit = defineEmits('delete-handler')

const deleteHandler = id => {
  if (confirm('삭제하시겠습니까?')) {
    emit('delete-handler', id)
  }
}
</script>

<template>
  <Table>
    <TableCaption>{{ title }}</TableCaption>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[60px] text-center"> 번호 </TableHead>
        <TableHead class="text-center">관광지 명</TableHead>
        <TableHead class="text-center">작성일</TableHead>
        <TableHead class="text-center"> 내용 </TableHead>
        <TableHead class="text-right"> 별점 </TableHead>
        <TableHead class="w-[70px] text-right">{{
          isEdit ? '삭제' : ''
        }}</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="(each, index) in reviews" :key="each.reviewId">
        <TableCell class="font-medium text-center">
          {{ index + 1 }}
        </TableCell>
        <TableCell class="text-center">{{ each.title }}</TableCell>
        <TableCell class="text-center">
          {{ each.createdAt }}
        </TableCell>
        <TableCell class="text-center">
          {{ each.content }}
        </TableCell>
        <TableCell class="text-right">
          {{ each.rating }}
        </TableCell>
        <TableCell v-show="isEdit" class="text-right hover:text-red-400">
          <button
            variant="outline"
            size="icon"
            @click="deleteHandler(each.reviewId)"
          >
            <i class="pi pi-trash"></i>
          </button>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>

<style scoped></style>
