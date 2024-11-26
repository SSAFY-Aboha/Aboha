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

const likeList = defineModel('data', { type: Array })
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
        <TableHead class="text-center">관광지 명</TableHead>
        <TableHead class="text-center">지역</TableHead>
        <TableHead class="text-right"> 바로가기 </TableHead>
        <TableHead class="w-[70px] text-right">{{
          isEdit ? '삭제' : ''
        }}</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="(each, index) in likeList" :key="each.likedId">
        <TableCell class="font-medium">
          {{ index + 1 }}
        </TableCell>
        <TableCell class="text-center">{{ each.title }}</TableCell>
        <TableCell class="text-center">
          {{ each.address }}
        </TableCell>
        <TableCell class="text-right">
          <RouterLink :to="`/trips/${each.attractionId}`">바로가기</RouterLink>
        </TableCell>
        <TableCell v-show="isEdit" class="text-right hover:text-red-400">
          <button
            variant="outline"
            size="icon"
            @click="deleteHandler(each.attractionId)"
          >
            <i class="pi pi-trash"></i>
          </button>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>

<style scoped></style>
