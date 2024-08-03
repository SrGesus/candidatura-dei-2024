import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MaterialView from '@/views/materials/MaterialView.vue'
import CandidateView from '@/views/candidates/CandidateView.vue'
import StudentshipView from '@/views/studentships/StudentshipView.vue'
import StudentshipEnrollmentsView from '@/views/studentships/StudentshipEnrollmentsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: HomeView
    },
    {
      path: '/contact',
      name: 'contact',
      component: HomeView
    },
    {
      path: '/materials',
      name: 'materials',
      component: MaterialView
    },
    {
      path: '/candidates',
      name: 'candidates',
      component: CandidateView
    },
    {
      path: '/register',
      name: 'register',
      component: HomeView
    },
    {
      path: '/profile',
      name: 'profile',
      component: HomeView
    },
    {
      path: '/studentships',
      name: 'studentships',
      component: StudentshipView
    },
    {
      path: '/studentships/:id',
      name: 'studentship',
      component: StudentshipEnrollmentsView,
      props: route => ({ id: Number(route.params.id) })
    },
  ]
})

export default router
