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

defineProps({
  title: {
    type: String,
    required: true,
  },
})

const abogs = defineModel('data', { type: Array })
const isEdit = defineModel('isEdit', { type: Boolean })

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
        <TableHead class="w-[100px]"> 번호 </TableHead>
        <TableHead class="text-center">제목</TableHead>
        <TableHead class="text-center">작성일</TableHead>
        <TableHead class="text-right"> 좋아요 수 </TableHead>
        <TableHead class="text-right"> 댓글 수 </TableHead>
        <TableHead class="w-[70px] text-right">{{
          isEdit ? '삭제' : ''
        }}</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <template v-if="abogs">
        <TableRow v-for="(each, index) in abogs" :key="each.id">
          <TableCell class="font-medium">
            {{ index + 1 }}
          </TableCell>
          <TableCell class="text-center">{{ each.title }}</TableCell>
          <TableCell class="text-center">
            {{ each.createdAt }}
          </TableCell>
          <TableCell class="text-right">
            {{ each.likeCount }}
          </TableCell>
          <TableCell class="text-right">
            {{ each.commentCount }}
          </TableCell>
          <TableCell v-show="isEdit" class="text-right hover:text-red-400">
            <button
              variant="outline"
              size="icon"
              @click="deleteHandler(each.abogId)"
            >
              <i class="pi pi-trash"></i>
            </button>
          </TableCell>
        </TableRow>
      </template>
      <template v-else>
        <div>데이터가 없습니다.</div>
      </template>
    </TableBody>
  </Table>
</template>

<style scoped></style>
