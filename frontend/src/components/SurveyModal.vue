<script setup>
import { InputText } from 'primevue'
import Dialog from 'primevue/dialog'

const visible = defineModel(['visible'])
</script>

<template>
  <Dialog
    @update:visible="visible"
    modal
    header="로그인"
    :style="{ width: '40rem' }"
  >
    <div class="flex items-center gap-4 mb-4">
      <label for="username" class="w-24 font-semibold">Username</label>
      <InputText id="username" class="flex-auto" autocomplete="off" />
    </div>
    <div class="flex items-center gap-4 mb-8">
      <label for="email" class="w-24 font-semibold">Email</label>
      <InputText id="email" class="flex-auto" autocomplete="off" />
    </div>
    <div class="flex justify-end gap-2">
      <Button
        type="button"
        label="Cancel"
        severity="secondary"
        @click="visibleToggle"
      ></Button>
      <Button type="button" label="Save"></Button>
    </div>
  </Dialog>
</template>

<style scoped></style>
