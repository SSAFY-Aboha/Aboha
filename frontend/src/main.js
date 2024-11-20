import './assets/index.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import 'primeicons/primeicons.css'

import { useKakao } from 'vue3-kakao-maps/@utils'
import { LoadingPlugin } from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/css/index.css'

// kakao map 초기화
useKakao(import.meta.env.VITE_KAKAO_API_KEY)

const app = createApp(App)

// pinia
app.use(createPinia())
// router
app.use(router)
// loading plugin
app.use(LoadingPlugin)

app.mount('#app')
