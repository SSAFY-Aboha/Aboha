import './assets/main.css'
import 'primeicons/primeicons.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import PrimeVue from 'primevue/config'
import Aura from '@primevue/themes/aura'
import ToastService from 'primevue/toastservice'

import App from './App.vue'
import router from './router'

const app = createApp(App)

// pinia
app.use(createPinia())
// router
app.use(router)
// prime
app.use(PrimeVue, {
  theme: {
    preset: Aura,
  },
})
app.use(ToastService)
app.mount('#app')
