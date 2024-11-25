<script setup>
import { ref, watch } from 'vue'
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps'

defineProps({
  mapData: {
    type: Object,
    required: true,
  },
})

const map = ref(null)

const onLoadKakaoMap = mapRef => {
  map.value = mapRef
}

watch(
  () => map.value,
  () => {
    console.log(map.value?.setZoomable(false))
  },
)
</script>

<template>
  <KakaoMap
    width="100%"
    :lat="mapData?.lat"
    :lng="mapData?.lng"
    @onLoadKakaoMap="onLoadKakaoMap"
  >
    <KakaoMapMarker :lat="mapData?.lat" :lng="mapData?.lng" :clickable="true" />
  </KakaoMap>
</template>

<style lang="scss" scoped></style>
