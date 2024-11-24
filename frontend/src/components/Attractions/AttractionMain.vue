<script setup></script>

<template>
  <main class="relative flex flex-col h-full gap-4">
    <!-- 뷰 모드에 따른 조건부 렌더링 -->
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </main>
</template>

<style scoped></style>
