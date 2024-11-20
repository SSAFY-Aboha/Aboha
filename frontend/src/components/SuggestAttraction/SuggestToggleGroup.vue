<script setup>
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'

const props = defineProps({
  type: {
    type: String,
    default: 'single',
  },
  infoData: {
    type: Object,
    required: true,
  },
})

const emit = defineEmits(['update:modelValue'])

const handleToggleGroupChange = value => {
  emit('update:modelValue', value)
}
</script>

<template>
  <ToggleGroup
    class="flex flex-wrap justify-center w-full gap-8"
    :type="type"
    @update:model-value="handleToggleGroupChange"
  >
    <TransitionGroup name="fade" appear>
      <ToggleGroupItem
        class="px-5 py-3 rounded-full text-lg fade-item data-[state=on]:bg-gray-700 data-[state=on]:text-white data-[state=on]:hover:bg-gray-600 transition-colors hover:bg-gray-400 hover:text-white"
        variant="outline"
        v-for="each in infoData"
        :key="each.code ?? each.id"
        :value="(each.code ?? each.id).toString()"
        :aria-label="each.name"
        >{{ each.name }}</ToggleGroupItem
      >
    </TransitionGroup>
  </ToggleGroup>
</template>

<style scoped>
.fade-enter-active,
.fade-appear-active {
  transition: all 0.3s ease;
}

.fade-enter-from,
.fade-appear-from {
  opacity: 0;
  transform: translateY(10px);
}

.fade-enter-to,
.fade-appear-to {
  opacity: 1;
  transform: translateY(0);
}

/* 아이템들이 순차적으로 나타나도록 설정 */
.fade-item {
  transition-delay: calc(
    var(--el-transition-duration, 0.2s) * var(--el-transition-index, 0)
  );
}
</style>
