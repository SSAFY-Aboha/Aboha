<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <keep-alive>
    <router-view v-slot="{ Component }">
      <component :is="Component" />
    </router-view>
  </keep-alive>
</template>

<style></style>
