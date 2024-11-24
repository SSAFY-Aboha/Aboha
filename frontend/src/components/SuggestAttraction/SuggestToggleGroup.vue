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
    class="grid w-full max-w-3xl grid-cols-2 gap-3 mx-auto sm:grid-cols-3 md:grid-cols-4"
    :type="type"
    @update:model-value="handleToggleGroupChange"
  >
    <TransitionGroup name="fade" appear>
      <ToggleGroupItem
        v-for="each in infoData"
        :key="each.code ?? each.id"
        :value="
          type == 'multiple' ? each.name : (each.code ?? each.id).toString()
        "
        class="relative flex items-center justify-center p-4 overflow-hidden text-sm font-medium transition-all rounded-lg cursor-pointer group hover:bg-green-50"
        :class="[
          'data-[state=on]:bg-green-100 data-[state=on]:text-green-800',
          'data-[state=on]:ring-2 data-[state=on]:ring-green-600/50',
        ]"
      >
        <span class="relative z-10">{{ each.name }}</span>
        <div
          class="absolute inset-0 transition-transform duration-300 bg-green-100 opacity-0 group-hover:opacity-100"
        />
      </ToggleGroupItem>
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
