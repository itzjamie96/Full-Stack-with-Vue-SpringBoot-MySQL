import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router/index'
import Axios from 'axios'

Vue.use(Vuex)

const baseURL = 'http://localhost:1234'

export default new Vuex.Store({
  state: {
    //로그인 
    trigger:true,
  role:'User',
  userInfo:null,
  isLogin : false,
  isLoginError: false,
    //로그인 끝

    //메인페이지 사진 박아놓은 예시
    loadedMeetups: [  
        {
          imageUrl: 'https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60', 
          id: 'aaa', 
          title: 'Meetup in city',
          date: '2019-12-17',
          time: '15:35',
          location: 'New York',
          description:'All in NewYork'
        },
        {
          imageUrl: 'https://images.unsplash.com/photo-1448375240586-882707db888b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60', 
          id: 'bbb', 
          title: 'Meetup in forest',
          date: '2019-12-19',
          time: '14:00',
          location: 'Forest',
          description: 'Forest!'
        }
    ],
    user: {
      id:'abc',
      registeredMeetups: [
        'aaa'
      ]
    }
    
  },
  mutations: {
    //트리거 관리자 페이지 / main페이지 헤더 구별
    triggerToggle(state,payload){
      state.trigger=payload
    },
    //role 결정하기
    roles(state,payload){
      state.role=payload
    },
    //로그인이 성공했을때
    loginSuccess(state,payload){
      state.isLogin=true
      state.isLoginError=false
      state.userInfo=payload
      
    },
    //로그인이 실패했을때
    loginError(state){
      state.isLogin=false
      state.isLoginError=true
    },
    logout(state){ //$store.commit('logout')으로 접근할 수 있음
      state.isLogin=false
      state.isLoginError=false
      state.userInfo=null
      localStorage.removeItem("email")
      localStorage.removeItem("role")

    },
    //로그인끝

    createMeetup(state, payload) {
      state.loadedMeetups.push(payload)
    },
    createReservation(state, payload) {
      
    }
  },
  actions: {  //reach out to firebase and store it
//헤더 바꾸기
 TriggerTO({commit},toggleDate){
commit('triggerToggle',toggleDate)
}, 

    //롤 바꾸기
_roles({commit},login_role){
commit('roles',login_role)

},

//로그인 시도
login({state,commit},loginobj){
  localStorage.setItem("role",state.role)
  let role = localStorage.getItem("role")
  
  //전체 유저에서 해당 이메일로 유저를 찾는다.
  //그 유저의 비밀번호와 입력된 비빌번호를 비교한다.
  Axios.post(`${baseURL}/signin`+role,loginobj) 
  .then(res => { 
    console.log(res.config.data)
    if(res.data.userEmail != null){
      console.log(res.data.userEmail)
      localStorage.setItem("email",loginobj.email)
         
         
         commit('loginSuccess',res.data)
         router.push({name:'uMyPage'})
          //로그인 성공 시 마이페이지로 이동시켜 줌
         }
         else if(res.data.sitterEmail != null){
          localStorage.setItem("email",loginobj.email)
          commit('loginSuccess',res.data)
          router.push({name:'sMyPage'})
         }
         else if(res.config.data[0] != null){
          localStorage.setItem("email",loginobj.email)
          commit('loginSuccess',res.config.data)
          commit('triggerToggle',false)
          router.push({name:'adminMain'})
         }
         else{
          commit('loginError')
         }
       }) 
       .catch(error => { 
         console.log(error)
         commit('loginError')
       })


},
lsm({state,commit}){
  let password = '1234'
  let email =localStorage.getItem("email")
  let role = localStorage.getItem("role")
    if(role === 'Admin'){
     commit('triggerToggle',false)
    }
  console.log(email)
  if(email != null){
  Axios.post(`${baseURL}/refresh`+role,{email,password}) 
       .then(res => { 
         console.log(res.data)
         
         commit('loginSuccess',res.data)
         commit('roles',role)
       }) 
       .catch(error => { 
         console.log(error)
         commit('loginError')
       })
      }
},
logout({commit}){//$store.dispatch('logout')으로 접근할 수 있음
 commit('logout')
 commit('triggerToggle',true)
 router.push({name:'home'})
}

    ,
    createMeetup({commit}, payload) {
      const meetup = {    //mapping to an object: payload might have properties that i may not need
        title: payload.title,
        location: payload.location,
        imageUrl: payload.imageUrl,
        description: payload.description,
        date: payload.date,
        time: payload.time,
        id: '111'
      }
      commit('createMeetup', meetup)
      
    }
  },
  getters: {
    loadedMeetups (state) { //all meetups
      return state.loadedMeetups.sort((meetupA, meetupB) => {
        return meetupA.date > meetupB.date
      }) 
    },
    loadedMeetup (state) {   // one meetup
      return (meetupId) => {
        return state.loadedMeetups.find((meetup) => {
          return meetup.id === meetupId
        })
      }
    },
    featuredMeetups(state, getters) {   //fetching some chosen meetups from the loaded Meetups
      return getters.loadedMeetups.slice(0,5)
    }
  }
})
