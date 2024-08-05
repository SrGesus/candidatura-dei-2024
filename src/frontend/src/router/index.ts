import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CandidateView from '@/views/candidates/CandidateView.vue'
import CandidateEnrollmentsView from '@/views/enrollments/CandidateEnrollmentsView.vue'
import StudentshipView from '@/views/studentships/StudentshipView.vue'
import StudentshipEnrollmentsView from '@/views/enrollments/StudentshipEnrollmentsView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/candidates',
      name: 'candidates',
      component: CandidateView
    },
    {
      path: '/candidate/:istId',
      name: 'candidate',
      component: CandidateEnrollmentsView,
      props: route => ({ istId: Number(route.params.istId) })
    },
    {
      path: '/studentships',
      name: 'studentships',
      component: StudentshipView
    },
    {
      path: '/studentship/:id',
      name: 'studentship',
      component: StudentshipEnrollmentsView,
      props: route => ({ id: Number(route.params.id) })
    }

  ]
})

export default router
