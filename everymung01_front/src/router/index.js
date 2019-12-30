import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import store from '../store/index'
import MypagePetInfo from '../views/Mypage/User/MyPagePetInfo'
import MyPagePetInfoNew from '../views/Mypage/User/MyPagePetInfoNew'
import MyPageSetting from '../views/Mypage/User/MyPageSetting'

Vue.use(VueRouter)

const rejectAuthUser = (to, from, next) => { //라우터 가드 선언
    if (store.state.isLogin === true) {
        //이미 로그인된 유저라 로그인 페이지로 못가게 막아야 함
        alert('이미 로그인 상태입니다.')
        next('/')
    } else {
        next()
    }
}

const onlyAuthUser = (to, from, next) => { //라우터 가드 선언
    if (store.state.isLogin === false) {
        //로그인이 안된 유저는 마이페이지를 드갈수 없게 해야함
        alert('로그인을 하지 않았습니다.')
        next('/')
    } else {
        next()
    }
}

// const Update = () =>
//     import ('../views/Board/update.vue') // 문의게시판 - 수정
const Write = () =>
    import ('../views/Board/write.vue') // 문의게시판 - 글 생성/저장 
const DaySitterList = () =>
    import ('../views/Sitters/daySitterList.vue') //하루 시터 목록보기
const DaySitterDetail = () =>
    import ('../views/Sitters/daySitterDetail.vue') //하루 시터 상세보기 - 예약

const HomeSitterList = () =>
    import ('../views/Sitters/homeSitterList.vue') //위탁 시터 목록보기
const HomeSitterDetail = () =>
    import ('../views/Sitters/homeSitterDetail.vue') //위탁 시터 상세보기 - 예약

const Board = () =>
    import ('../views/Board/board.vue') //문의게시판 목록
const Signup = () =>
    import ('../views/Signup/UserForm/userSignUp.vue')
const PaymentInfo = () =>
    import ('../views/Payment/paymentInfo.vue') //결제 정보 (결제 전)

const routes = [
  {
    path: '/admin',
    name: 'adminHome',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Home.vue')
  },
  {
    path: '/adminReservation',
    name: 'adminreservation',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Reservation.vue'),
  },
  {
    path: '/adminMember',
    name: 'adminmember',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Member.vue')
  },
  {
    path: '/adminEmployee',
    name: 'adminemployee',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Employee.vue')
  },
  {
    path: '/adminApproval',
    name: 'adminapproval',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Approval.vue')
  },
  {
    path: '/adminBoard',
    name: 'adminboard',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Board.vue')
  },
  {
    path: '/adminPayment',
    name: 'adminpayment',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Payment.vue')
  },
  {
    path: '/adminOneday',
    name: 'adminoneday',
    component: () => import('../views/Admin/reserv/Oneday.vue')
  },
  {
    path: '/adminConsignment',
    name: 'adminconsignment',
    component: () => import('../views/Admin/reserv/Consignment.vue')
  },
  {
    path: '/adminRepeated',
    name: 'adminrepeated',
    component: () => import('../views/Admin/reserv/Repeated.vue')
  },
  {
    path: '/adminMain',
    name: 'adminMain',
    component: () => import('../views/Admin/main/Admin-main.vue')
  },
{       //일반 홈
        path: '/',
        name: 'home',
        component: Home
    },
    {   //시터 지원
        path: '/sitterSignUp',
        name: 'sitterSignUp',
        component: () =>
            import ( /* webpackChunkName: "sitterSignin" */ '../views/Signup/SitterForm/sitterSignUp.vue')
    },
    {
        path: '/sitterSignUpFinish',
        name: 'sitterSignUpFinish',
        component: () =>
            import ( /* webpackChunkName: "sitterSignin" */ '../views/Signup/SitterForm/sitterSignUpFinish.vue')
    },
    {
        path: '/signin',
        name: 'signin',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Signup/UserForm/userSignIn.vue')
    },
    {
        path: '/signup',
        name: 'signup',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Signup/UserForm/userSignUp.vue')
    },
    { //유저 마이페이지
        path: '/uMyPage',
        name: 'uMyPage',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Mypage/User/userMyPage.vue')
    },
    { //유저 예약내역
        path: '/uMyPage/userReservationList',
        name: 'UserReservationList',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/User/userReservationList.vue')
    },
    { //유저 예약 - 상세 페이지
        path: '/uMyPage/userReservationDetail/:paymentNo',
        name: 'UserReservationDetail',
        component: () =>
            import ( /* webpackChunkName: "UserReservationDetail" */ '../views/Mypage/User/userReservationDetail.vue')
    },
    { //반려견 정보관리
        path: '/uMyPage/petInfo',
        name: 'myPagePetInfo',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Mypage/User/MyPagePetInfo.vue')
    },
    {
        path: '/sMyPage',
        name: 'sMyPage',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Mypage/Sitter/sitterMyPage.vue')
    },

    {
        //하루 시터 프로필 -> 스케줄 예약 포함
        path: '/daysitters/:sitterNo',
        name: 'DaySitterDetail',
        component: DaySitterDetail,
        props: true
    },
    {
        //하루 시터 목록 보기
        path: '/daysitters',
        name: 'DaySitterList',
        component: DaySitterList
    },

    {
        //위탁 시터 목록 보기
        path: '/homesitters',
        name: 'HomeSitterList',
        component: HomeSitterList
    },

    {
        //위탁 시터 프로필 -> 스케줄 예약 포함
        path: '/homesitters/:sitterNo',
        name: 'HomeSitterDetail',
        component: HomeSitterDetail,
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
    {
        path: '/write',
        name: 'Write',
        component: Write
    },
    // {
    //     path: '/update',
    //     name: 'Update',
    //     component: Update
    // },


    {
        path: '/userReservationList',
        name: 'UserReservationList',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/User/userReservationList.vue')
    },

    {
        path: '/useReservationDetail/:paymentNo',
        name: 'UserReservationDetail',
        component: () =>
            import ( /* webpackChunkName: "UserReservationDetail" */ '../views/Mypage/User/userReservationDetail.vue')
    },

    {
        //하루 시터 프로필 -> 스케줄 예약 포함 , 리뷰작성위해서 paymentNo 넘기는 path
        path: '/daysitters/:sitterNo/:paymentNo',
        name: 'DaySitterDetail',
        component: DaySitterDetail,
        props: true
    },
    {
        //마이페이지
        path:'/mypage/:userNo',
        name:'myPagePetInfo',
        component:MypagePetInfo
      },
      {
        //반려견 추가히기
        path:'/myPetDetail',
        name:'myPetDetail',
        component:MyPagePetInfoNew
      },
      //마이페이지/계정관리
      {
        path:'/uMyPage/uAccount',
        name:'myPgeSetting',
        component:MyPageSetting
      },
      
      //kakaopay 성공 시 넘어가는 페이지
      {
        path: '/kakaoPaySuccess',
        name: 'KakaoPaySuccess',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Payment/kakaoPaySuccess.vue')
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router