import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const SitterList = () => import('../views/Sitters/sitterList.vue')
const NewReservation = () => import('../views/Reservation/newReservation.vue')
const Board = () => import('../views/Board/board.vue')
const Signup = () => import('../views/Signup/UserForm/userSignUp.vue')
const SitterDetail = () => import('../views/Sitters/sitterDetail.vue')

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },

  {
    path: '/sitters',
    name: 'SitterList',
    component: SitterList
    /*,
    children: [
      {
        path: '/new',
        name: 'CreateMeetup',
        component: CreateMeetup
      },
      {
        path: ':id',
        name: 'Meetup',
        component: Meetup
      }
    ]
    */
  },
  
  {
    path: '/sitters/new',
    name: 'NewReservation',
    component: NewReservation
  },
  {
    path: '/sitters/:sitterNo',
    name: 'SitterDetail',
    component: SitterDetail,
    props: true
  },
  
  {
    path: '/board',
    name: 'Board',
    component: Board
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
