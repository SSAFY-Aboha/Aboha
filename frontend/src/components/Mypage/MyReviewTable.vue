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

const data = defineModel('data', { type: Array })
const isEdit = defineModel('isEdit', { type: Boolean })

console.log(data.value)

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
      <TableRow v-for="each in data" :key="each.id">
        <TableCell class="font-medium text-center">
          {{ each.id }}
        </TableCell>
        <TableCell class="text-center">{{ each.attraction.title }}</TableCell>
        <TableCell class="text-center">
          {{ each.createdAt }}
        </TableCell>
        <TableCell class="text-center">
          {{ each.comment }}
        </TableCell>
        <TableCell class="text-right">
          {{ each.rating }}
        </TableCell>
        <TableCell v-show="isEdit" class="text-right hover:text-red-400">
          <button variant="outline" size="icon" @click="deleteHandler(each.id)">
            <i class="pi pi-trash"></i>
          </button>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>

<style scoped></style>
