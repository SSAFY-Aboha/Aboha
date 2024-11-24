import { ref } from 'vue'

const toasts = ref([])

export function useToast() {
  const add = (message, type = 'default', duration = 3000) => {
    const id = Date.now()
    toasts.value.push({ id, message, type })

    setTimeout(() => {
      remove(id)
    }, duration)
  }

  const remove = id => {
    toasts.value = toasts.value.filter(toast => toast.id !== id)
  }

  return {
    toasts,
    error: message => add(message, 'error'),
    success: message => add(message, 'success'),
    info: message => add(message, 'info'),
    warning: message => add(message, 'warning'),
  }
}
