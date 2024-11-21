<script setup>
import { Alert, AlertTitle, AlertDescription } from '@/components/ui/alert'
import { AlertCircle } from 'lucide-vue-next'

defineProps({
  title: {
    type: String,
    required: true,
  },
  text: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <Alert variant="destructive">
    <AlertCircle class="w-4 h-4" />
    <AlertTitle>{{ title }}</AlertTitle>
    <AlertDescription>
      {{ text }}
    </AlertDescription>
  </Alert>
</template>

<style scoped></style>
