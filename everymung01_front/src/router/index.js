import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import store from '../store/index'
import MypagePetInfo from '../views/Mypage/User/MyPagePetInfo'
import MyPagePetInfoNew from '../views/Mypage/User/MyPagePetInfoNew'
import MyPageSetting from '../views/Mypage/User/MyPageSetting'
import MyPageLiveCare from '../views/Mypage/User/MyPageLiveCare'
import UserPetDetail from '../views/Mypage/User/UserPetDetail'

Vue.use(VueRouter)
//어드민 라우터 가드
const adminRouteG = (to,from,next) => {
    localStorage.getItem('email')!==null&&localStorage.getItem('role')!=='Admin'?next('/'):next()
}
const adminRouteMG = (to,from,next) => {
    localStorage.getItem('email')!==null&&localStorage.getItem('role')==='Admin'?next('/admin'):next()
}

//어드민 라우터 가드 끝

const rejectAuthUser = (to, from, next) => { //라우터 가드 선언
    localStorage.getItem('email')!==null&&localStorage.getItem('role')!==null?next('/'):next()
        //이미 로그인된 유저라 로그인 페이지로 못가게 막아야 함
}

const UserMyPageG = (to, from, next) => { //라우터 가드 선언
    localStorage.getItem('email')===null&&localStorage.getItem('role')===null?next('/signin'):
    localStorage.getItem('email')!==null&&localStorage.getItem('role')!=='User'?next('/'):next()
        //로그인이 안된 유저는 마이페이지를 드갈수 없게 해야함
}
const SitterMyPageG = (to, from, next) => { //라우터 가드 선언
    localStorage.getItem('email')===null&&localStorage.getItem('role')===null?next('/signin'):
    localStorage.getItem('email')!==null&&localStorage.getItem('role')!=='Sitter'?next('/'):next()
        //로그인이 안된 유저는 마이페이지를 드갈수 없게 해야함
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
const PaymentInfoDay = () =>
    import ('../views/Payment/paymentInfoDay.vue') //결제 정보 (결제 전)
const PaymentInfoHome = () =>
    import ('../views/Payment/paymentInfoHome.vue') //결제 정보 (결제 전)

const routes = [
  {
    path: '/admin',
    name: 'adminHome',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Home.vue'),
  },
  {
    path: '/adminReservation',
    name: 'adminreservation',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Reservation.vue'),
  },
  {
    path: '/adminMember',
    name: 'adminmember',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Member.vue'),
  },
  {
    path: '/adminEmployee',
    name: 'adminemployee',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Employee.vue'),
  },
  {
    path: '/adminApproval',
    name: 'adminapproval',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Approval.vue'),
  },
  {
    path: '/adminBoard',
    name: 'adminboard',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Board.vue'),
  },
  {
    path: '/adminPayment',
    name: 'adminpayment',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin/Admin-Payment.vue'),
  },
  {
    path: '/adminOneday',
    name: 'adminoneday',
    component: () => import('../views/Admin/reserv/Oneday.vue'),
  },
  {
    path: '/adminDaycare',
    name: 'admindaycare',
    component: () => import('../views/Admin/reserv/Daycare.vue'),
  },
  {
    path: '/adminMain',
    name: 'adminMain',
    component: () => import('../components/Admin-main.vue'),
  },
{       //일반 홈
        path: '/',
        name: 'home',
        component: Home,
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
        import ( /* webpackChunkName: "Login" */ '../views/Signup/UserForm/userSignIn.vue'),
    },
    {
        path: '/signup',
        name: 'signup',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Signup/UserForm/userSignUp.vue'),
    },
    { //유저 마이페이지
        path: '/uMyPage',
        name: 'uMyPage',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Mypage/User/userMyPage.vue'),
    },
    { //유저 예약내역
        path: '/uMyPage/userReservationList',
        name: 'UserReservationList',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/User/userReservationList.vue'),
    },
    { //유저 예약 - 상세 페이지
        path: '/uMyPage/userReservationDetail/:paymentNo',
        name: 'UserReservationDetail',
        component: () =>
            import ( /* webpackChunkName: "UserReservationDetail" */ '../views/Mypage/User/userReservationDetail.vue'),
    },
    { //반려견 정보관리
        path: '/uMyPage/petInfo',
        name: 'myPagePetInfo',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Mypage/User/MyPagePetInfo.vue'),
    },
    { //유저 실시간 케어 보기
        path: '/uMyPage/live',
        name: 'userLive',
        component: () =>
            import ( /* webpackChunkName: "Login" */ '../views/Mypage/User/userLive.vue'),
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
        component: DaySitterList,
    },

    {
        //위탁 시터 목록 보기
        path: '/homesitters',
        name: 'HomeSitterList',
        component: HomeSitterList,
    },

    {
        //위탁 시터 프로필 -> 스케줄 예약 포함
        path: '/homesitters/:sitterNo',
        name: 'HomeSitterDetail',
        component: HomeSitterDetail,
        props: true
    },
    {
        //결제 전 정보 보기 하루
        path: '/paymentinfoDay/:paymentNo',
        name: 'PaymentInfoDay',
        component: PaymentInfoDay
    },
    {
        //결제 전 정보 보기 하루
        path: '/paymentinfoHome/:paymentNo',
        name: 'PaymentInfoHome',
        component: PaymentInfoHome
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
        component: Write,
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
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/User/userReservationList.vue'),
    },

    {
        path: '/userReservationDetail/:paymentNo',
        name: 'UserReservationDetail',
        component: () =>
            import ( /* webpackChunkName: "UserReservationDetail" */ '../views/Mypage/User/userReservationDetail.vue'),
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
        component:MypagePetInfo,
      },
      {
        //반려견 추가히기
        path:'/myPetDetail',
        name:'myPetDetail',
        component:MyPagePetInfoNew,
      },
      //마이페이지/계정관리
      {
        path:'/uMyPage/uAccount',
        name:'myPgeSetting',
        component:MyPageSetting,
      },
      
      //kakaopay 성공 시 넘어가는 페이지
      {
        path: '/kakaoPaySuccess',
        name: 'KakaoPaySuccess',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Payment/kakaoPaySuccess.vue'),
    },
    { //시터 예약내역
        path: '/sMyPage/sitterReservationList',
        name: 'SitterReservationList',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/Sitter/sitterReservationList.vue'),
    },
    { //시터 프로필 등록, 수정
        path: '/sMyPage/sitterProfile',
        name: 'SitterProfile',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/Sitter/sitterProfile.vue'),
    },
    { //시터 실시간 케어 송출
        path: '/sMyPage/live',
        name: 'sitterLive',
        component: () =>
            import ( /* webpackChunkName: "UserReservationList" */ '../views/Mypage/Sitter/sitterLive.vue'),
    },
    // 마이페이지 / 실시간 케어보기 
    {
        path: '/uMyPage/uLiveCare',
        name:'MyPageLiveCare',
        component:MyPageLiveCare

    },
    // 마이페이지 / 반려견정보관리 / 상세보기 
    {
        path:'/uMyPage/uPetDetailView',
        name:'UserPetDetail',
        component:UserPetDetail

    },
    { //시터 개인정보 등록, 수정
        path: '/sMyPage/sitterMyPageSetting',
        name: 'SitterMyPageSetting',
        component: () =>
            import ( /* webpackChunkName: "SitterMyPageSetting" */ '../views/Mypage/Sitter/sitterMyPageSetting.vue')
    },
    { //시터 예약 내역 - 상세 페이지
        path: '/sMyPage/sitterReservationDetail/:paymentNo',
        name: 'SitterReservationDetail',
        component: () =>
            import ( /* webpackChunkName: "SitterReservationDetail" */ '../views/Mypage/Sitter/sitterReservationDetail.vue')
    },
    { //시터 프로필 페이지
        path: '/sitterProfilePage/:sitterNo/:sittingType',
        name: 'SitterProfilePage',
        component: () =>
            import ( /* webpackChunkName: "SitterProfilePage" */ '../views/Sitters/sitterProfilePage.vue')
    },
   

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router