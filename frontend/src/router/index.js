import { createRouter, createWebHistory } from 'vue-router'
import useUserStore from '@/stores/user'

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
      component: () => import('@/views/TripListView.vue'),
      redirect: { name: 'trips-list' },
      // meta: { keepAlive: true },
      children: [
        {
          path: '',
          name: 'trips-list',
          component: () => import('@/views/AttractionListView.vue'),
        },
        {
          path: ':tripId',
          name: 'trips-detail',
          component: () =>
            import('@/components/Attractions/AttractionDetailContainer.vue'),
          props: true,
        },
        {
          path: 'map',
          name: 'trips-map',
          component: () =>
            import('@/views/AttractionMap/AttractoinMapView.vue'),
          meta: { hideLayout: true },
        },
      ],
    },
    {
      path: '/abog',
      name: 'abog',
      component: () => import('@/views/AbogBoardView.vue'),
      redirect: { name: 'abog-main' },
      meta: { requireAuth: true },
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
          meta: { requireAuth: true },
        },
      ],
    },
    {
      path: '/suggest',
      name: 'suggest',
      component: () => import('@/views/SuggestAttractionView.vue'),
      meta: { hideLayout: true },
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: () => import('@/views/NotFoundView.vue'),
      meta: { hideLayout: true },
    },
    {
      path: '/challenge',
      name: 'challenge',
      component: () => import('@/views/ChallengeView.vue'),
      meta: { hideLayout: true },
    },
  ],
})

router.beforeEach(async (to, from, next) => {
  // 네비게이션 로그 출력
  console.log('Navigating from:', from.path, 'to:', to.path)

  const userStore = useUserStore()
  // 인증 상태 가져오기

  // requireAuth가 true인 라우트에 대해서만 로그인 체크
  if (to.meta.requireAuth) {
    await userStore.initializeAuth()

    console.log('현재 인증 상태', userStore.isAuthenticated)
    if (!userStore.isAuthenticated) {
      alert('로그인 후 이용해주세요.')
      next({ name: 'login' })
      return
    }
  }
  // requireAuth가 없거나, 로그인이 되어있는 경우 정상적으로 라우팅
  next()
})

export default router
