import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import MyPageView from '@/views/MyPageView.vue'
import TripListView from '@/views/TripListView.vue'
import AbogBoardView from '@/views/AbogBoardView.vue'
import AbogBoardEditor from '@/components/AbogBoard/AbogBoardEditor.vue'
import AbogBoardMain from '@/components/AbogBoard/AbogBoardMain.vue'
import AbogBoardDetail from '@/components/AbogBoard/AbogBoardDetail.vue'

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
      component: LoginView,
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
    },
    {
      path: '/trips/:tripId',
      name: 'trips-detail',
      component: TripListView,
    },
    {
      path: '/abog',
      name: 'abog',
      component: AbogBoardView,
      redirect: { name: 'abog-main' },
      children: [
        {
          path: '/abog/main',
          name: 'abog-main',
          component: AbogBoardMain,
        },
        {
          path: '/abog/edit',
          name: 'abog-edit',
          component: AbogBoardEditor,
        },
        {
          path: ':abogId',
          name: 'abog-detail',
          component: AbogBoardDetail,
          props: true,
        },
      ],
    },
  ],
})

export default router
