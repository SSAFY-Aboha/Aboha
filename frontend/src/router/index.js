import { createRouter, createWebHistory } from 'vue-router'
import MyPageView from '@/views/MyPageView.vue'
import TripListView from '@/views/TripListView.vue'
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
      meta: { hideLayout: true },
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('@/views/SignUpView.vue'),
      meta: { hideLayout: true },
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
      // meta: { keepAlive: true },
      children: [
        {
          path: '',
          name: 'trips-main',
          component: AttractionMain,
        },
        {
          path: ':tripId',
          name: 'trips-detail',
          component: () =>
            import('@/components/Attractions/AttractionDetailContainer.vue'),
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
    {
      path: '/suggest',
      name: 'suggest',
      component: () => import('@/views/SuggestAttractionView.vue'),
      meta: { hideLayout: true },
    },
  ],
})

router.beforeEach((to, from, next) => {
  console.log('Navigating from:', from.path, 'to:', to.path)
  next()
})

export default router
