<script setup>
import Button from '@/components/ui/button/Button.vue'
import Input from '@/components/ui/input/Input.vue'
</script>
<template>
  <h1 class="mb-4 text-3xl font-bold">Create account</h1>
  <form @submit.prevent="" class="flex flex-col w-full gap-4">
    <div class="flex flex-col w-full gap-2">
      <label class="text-sm" for="email">Name *</label>
      <Input
        type="Name"
        id="Name"
        class="p-2 border border-gray-300 rounded-md placeholder:text-xs"
      />
      <label class="text-sm" for="email">Email *</label>
      <Input
        type="email"
        id="email"
        class="p-2 border border-gray-300 rounded-md placeholder:text-xs"
      />
      <label class="text-sm" for="password">비밀번호 *</label>
      <Input
        placeholder="8자리 이상, 영문 대소문자, 숫자, 특수문자 조합"
        type="password"
        id="password"
        class="p-2 border border-gray-300 rounded-md placeholder:text-xs"
      />
      <label class="text-sm" for="password">비밀번호 확인 *</label>
      <Input
        placeholder=""
        type="password"
        id="password-check"
        class="p-2 border border-gray-300 rounded-md placeholder:text-xs"
      />
    </div>
    <Button class="w-full">회원가입</Button>
  </form>
</template>

<style lang="scss" scoped></style>
