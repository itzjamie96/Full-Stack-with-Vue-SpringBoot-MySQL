import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import store from '../store/index'
Vue.use(VueRouter)

const rejectAuthUser = (to,from,next) =>{ //라우터 가드 선언
  if(store.state.isLogin ===true){
    //이미 로그인된 유저라 로그인 페이지로 못가게 막아야 함
    alert('이미 로그인 상태입니다.')
    next('/')
  }else{
    next()
  }
}

const onlyAuthUser = (to,from,next) =>{ //라우터 가드 선언
  if(store.state.isLogin ===false){
    //로그인이 안된 유저는 마이페이지를 드갈수 없게 해야함
    alert('로그인을 하지 않았습니다.')
    next('/')
  }else{
    next()
  }
}


const SitterList = () => import('../views/Sitters/sitterList.vue')      //시터 목록보기
const Board = () => import('../views/Board/board.vue')                  //문의게시판 목록
const Signup = () => import('../views/Signup/UserForm/userSignUp.vue')    
const SitterDetail = () => import('../views/Sitters/sitterDetail.vue')  //시터 상세보기 - 예약
const PaymentInfo = () => import('../views/Payment/paymentInfo.vue')    //결제 정보 (결제 전)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/signin',
    name: 'signin',
    component: () => import (/* webpackChunkName: "Login" */ '../views/Signup/UserForm/userSignIn.vue')
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import (/* webpackChunkName: "Login" */ '../views/Signup/UserForm/userSignUp.vue')
  },
  {
    path: '/uMyPage',
    name: 'uMyPage',
    component: () => import (/* webpackChunkName: "Login" */ '../views/Mypage/User/userMyPage.vue')
  },
  {
    path: '/sMyPage',
    name: 'sMyPage',
    component: () => import (/* webpackChunkName: "Login" */ '../views/Mypage/Sitter/sitterMyPage.vue')
  },

  {
    //시터 목록 보기
    path: '/sitters',
    name: 'SitterList',
    component: SitterList
  },

  {
    //시터 프로필 -> 스케줄 예약 포함
    path: '/sitters/:sitterNo',
    name: 'SitterDetail',
    component: SitterDetail,
    props: true
  },
  {
    //결제 전 정보 보기
    path: '/paymentinfo/:paymentNo',
    name: 'PaymentInfo',
    component: PaymentInfo
  },
  
  {
    //문의게시판
    path: '/board',
    name: 'Board',
    component: Board
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
