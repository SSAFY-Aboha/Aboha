<script setup>
import Dialog from 'primevue/dialog'

defineModel(['visible'])
</script>

<template>
  <Dialog class="w-3/4 h-4/5" @update:visible="visible" modal header="">
    <div>여기에 여행지 추천</div>
  </Dialog>
</template>

<style scoped></style>
