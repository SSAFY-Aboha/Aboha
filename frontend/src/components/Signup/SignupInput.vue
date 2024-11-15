<script setup>
defineProps({
  metaData: Object,
})
const inputValue = defineModel('inputValue')
</script>

<template>
  <div class="flex flex-col w-full gap-5">
    <div class="floating-input-container">
      <input
        :type="metaData.type"
        :id="metaData.id"
        class="floating-input"
        placeholder=" "
        v-model="inputValue[metaData.id]"
      />
      <label for="Email" class="floating-label">
        {{ metaData.text }} <span class="text-red-300">*</span>
      </label>
    </div>
  </div>
</template>

<style scoped>
.floating-input-container {
  position: relative;
}

.floating-input {
  width: 100%;
  padding: 0.75rem;
  background: transparent;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  appearance: none;
}

.floating-input:focus {
  outline: none;
  border-color: #84ce99;
}

.floating-label {
  position: absolute;
  left: 0.5rem;
  padding: 0 0.5rem;
  background: white;
  color: #6b7280;
  font-size: 0.875rem;
  transition: all 0.3s ease;
  pointer-events: none;

  /* 기본 위치 (input이 비어있을 때) */
  top: 50%;
  transform: translateY(-50%);
}

/* input이 focus되거나 값이 있을 때 label 위치 */
.floating-input:focus ~ .floating-label,
.floating-input:not(:placeholder-shown) ~ .floating-label {
  top: 0;
  transform: translateY(-50%) scale(0.85);
  color: #6b7280;
}

/* input이 focus될 때 label 스타일 */
.floating-input:focus ~ .floating-label {
  color: #84ce99;
}

/* 유효성 검사 오류시  */
.input-error {
  border-color: red;
}
</style>
