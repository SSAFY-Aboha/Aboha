import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: AboutView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: AboutView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: AboutView,
    },
    {
      path: '/trips',
      name: 'trips',
      component: HomeView,
    },
  ],
})

export default router
