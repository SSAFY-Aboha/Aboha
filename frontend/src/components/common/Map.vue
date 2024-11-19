<script setup>
import { ref } from 'vue'
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps'

defineProps({
  mapData: {
    type: Object,
    required: true,
  },
})
</script>

<template>
  <KakaoMap width="100%" :lat="mapData.lat" :lng="mapData.lng">
    <KakaoMapMarker :lat="mapData.lat" :lng="mapData.lng" :clickable="true" />
  </KakaoMap>
</template>

<style lang="scss" scoped></style>
