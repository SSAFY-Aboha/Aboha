import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import MyPageView from '@/views/MyPageView.vue'
import TripListView from '@/views/TripListView.vue'
import AbogBoardView from '@/views/AbogBoardView.vue'
import AbogBoardEditor from '@/components/AbogBoard/AbogBoardEditor.vue'
import AbogBoardMain from '@/components/AbogBoard/AbogBoardMain.vue'
import AttractionDetail from '@/components/Attractions/AttractionDetail.vue'
import AttractionMain from '@/components/Attractions/AttractionMain.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView,
    },
    {
      path: '/trips',
      name: 'trips',
      component: TripListView,
      redirect: { name: 'trips-main' },
      meta: { keepAlive: true },
      children: [
        {
          path: '',
          name: 'trips-main',
          component: AttractionMain,
        },
        {
          path: ':tripId',
          name: 'trips-detail',
          component: AttractionDetail,
          props: true,
        },
      ],
    },
    {
      path: '/abog',
      name: 'abog',
      component: () => import('@/views/AbogBoardView.vue'),
      redirect: { name: 'abog-main' },
      children: [
        {
          path: '',
          name: 'abog-main',
          component: () => import('@/components/AbogBoard/AbogBoardMain.vue'),
        },
        {
          path: 'edit',
          name: 'abog-edit',
          component: () => import('@/components/AbogBoard/AbogBoardEditor.vue'),
        },
      ],
    },
  ],
})

router.beforeEach((to, from, next) => {
  console.log('Navigating from:', from.path, 'to:', to.path)
  next()
})

export default router
