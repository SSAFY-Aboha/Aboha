<script setup lang="ts">
import SignupForm from '@/components/Signup/SignupForm.vue'
</script>

<template>
  <section class="flex items-center justify-center h-full">
    <div
      class="px-10 py-12 border shadow bg-gradient min-w-[28rem] md:max-h-3/6 lg:max-h-4/6 rounded-xl"
    >
      <div
        class="flex flex-col items-center justify-center w-full h-full gap-3"
      >
        <SignupForm />
      </div>
    </div>
  </section>
</template>

<style></style>
