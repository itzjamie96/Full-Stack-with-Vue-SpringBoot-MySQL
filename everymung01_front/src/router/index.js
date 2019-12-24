import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Home.vue')
  },
  {
    path: '/reservation',
    name: 'reservation',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Reservation.vue'),
  },
  {
    path: '/member',
    name: 'member',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Member.vue')
  },
  {
    path: '/employee',
    name: 'employee',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Employee.vue')
  },
  {
    path: '/approval',
    name: 'approval',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Approval.vue')
  },
  {
    path: '/board',
    name: 'board',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Board.vue')
  },
  {
    path: '/payment',
    name: 'payment',
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/Admin-Payment.vue')
  },
  {
    path: '/oneday',
    name: 'oneday',
    component: () => import('../views/admin/reserv/Oneday.vue')
  },
  {
    path: '/consignment',
    name: 'consignment',
    component: () => import('../views/admin/reserv/Consignment.vue')
  },
  {
    path: '/repeated',
    name: 'repeated',
    component: () => import('../views/admin/reserv/Repeated.vue')
  },
  {
    path: '/adminMain',
    name: 'adminMain',
    component: () => import('../views/admin/main/Admin-main.vue')
  },


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
