<script setup>
import Button from '@/components/ui/button/Button.vue'
import Input from '@/components/ui/input/Input.vue'
</script>
<template>
  <form class="w-full" @submit.prevent="">
    <div class="flex flex-col flex-1 gap-2">
      <div class="flex flex-col gap-4">
        <Input class="bg-gray-100" type="email" placeholder="Email" />
        <Input class="bg-gray-100" type="password" placeholder="Password" />
      </div>
      <router-link to="#" class="self-end text-sm hover:underline"
        >비빌번호 찾기</router-link
      >
      <div class="">
        <Button class="w-full">로그인</Button>
      </div>
    </div>
  </form>
</template>

<style lang="scss" scoped></style>
