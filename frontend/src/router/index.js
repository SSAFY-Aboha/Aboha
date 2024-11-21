import { createRouter, createWebHistory } from 'vue-router'
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
      path: '/mypage/:userId',
      name: 'mypage',
      component: () => import('@/views/mypage/MyPageView.vue'),
      meta: { requireAuth: true, hideLayout: true, transition: false },
      children: [
        {
          path: '',
          name: 'mypage-main',
          component: () => import('@/views/mypage/MyPageMain.vue'),
        },
        {
          path: 'edit',
          name: 'mypage-edit',
          component: () => import('@/views/mypage/MyPageEdit.vue'),
        },
      ],
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
  // 네비게이션 로그 출력
  console.log('Navigating from:', from.path, 'to:', to.path)

  // 인증 상태 가져오기
  const isLoggedIn = true
  // const isLoggedIn = store.isLoggedIn

  // 로그인 상태 확인
  if (to.meta.requireAuth && !isLoggedIn) {
    // 로그인 되어있지 않는다면, 로그인 페이지로 이동
    next({ name: 'login' })
  } else {
    // 로그인 되어있다면, 다음 단계로 이동
    next()
  }
})

export default router
