import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router/index'
import Axios from 'axios'

Vue.use(Vuex)

const baseURL = 'http://localhost:1234'

export default new Vuex.Store({
    state: {
        userInfo: {},
        //채팅
        me: {},
        history: [],
        //로그인 
        trigger: true,
        role: 'User',
        isLogin: false,
        isLoginError: false,
        sitterApp: false,
        //로그인 끝
        //admin 승인대기 count
        count:'0',
        //admin 미답변 count
        boardcount:'0',

        //메인페이지 사진 박아놓은 예시
        mainPics: [{
                imageUrl: 'https://images.unsplash.com/photo-1534361960057-19889db9621e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80',
                id: '1',
            },
            {
                imageUrl: 'https://images.pexels.com/photos/3361692/pexels-photo-3361692.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260',
                id: '2',
            },
            {
                imageUrl: 'https://images.pexels.com/photos/3397935/pexels-photo-3397935.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260',
                id: '3',
            },
            {
                imageUrl: 'https://images.pexels.com/photos/2737393/pexels-photo-2737393.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260',
                id: '4',
            },
        ],

        //예약 정보 넘기기
        reservationList: [],

        //반려견 번호 넘기기
        forPet: [],

        areaList: [ //서울 구 리스트 
            {name: ''},
            { name: '종로구' }, { name: '중구' }, { name: '용산구' }, { name: '성동구' }, { name: '광진구' },
            { name: '동대문구' }, { name: '중랑구' }, { name: '성북구' }, { name: '강북구' }, { name: '도봉구' },
            { name: '노원구' }, { name: '은평구' }, { name: '서대문구' }, { name: '마포구' }, { name: '양천구' },
            { name: '강서구' }, { name: '구로구' }, { name: '금천구' }, { name: '영등포구' }, { name: '동작구' },
            { name: '관악구' }, { name: '서초구' }, { name: '강남구' }, { name: '송파구' }, { name: '강동구' }
        ],


    },

    mutations: {


        //채팅 관련
        setMe(state, { me }) {
            state.me = me;
        },
        addHistory(state, { history }) {
            history.forEach(element => {
                state.history.push(element.entry);
            });
        },
        ////시터 승인 대기 상태에서 로긴 거절
        sitterAp(state) {
            state.isLogin = false
            state.isLoginError = false
            state.sitterApp = true
        },
        //트리거 관리자 페이지 / main페이지 헤더 구별
        triggerToggle(state, payload) {
            state.trigger = payload
        },
        //role 결정하기
        roles(state, payload) {
            state.role = payload
        },
        //로그인이 성공했을때
        loginSuccess(state, payload) {
            state.isLogin = true
            state.isLoginError = false
            state.sitterApp = false
            state.userInfo = payload

        },
        //로그인이 실패했을때
        loginError(state) {
            state.isLogin = false
            state.sitterApp = false
            state.isLoginError = true
        },
        logout(state) { //$store.commit('logout')으로 접근할 수 있음
            state.isLogin = false
            state.isLoginError = false
            state.sitterApp = false
            state.userInfo = null
            localStorage.removeItem("email")
            localStorage.removeItem("role")

        },
        //예약 정보 넘기기//mutation
        setReservation(state, payload) {
            state.reservationList = payload
        },
        createReservation(state, payload) {
            state.reservationList = [],
                state.reservationList.push(payload)
        },
        //forPet
        setPetInfo(state, payload) {
            state.forPet = payload
        },
        createPetInfo(state, payload) {
            state.forPet = [],
                state.forPet.push(payload)
        },

        //마이페이지 중 계정관리 정보 받기 
        userInfoReading(state, payload) {
            state.userInfo = payload
        },

        //관리자 승인대기(미승인) 정보 받기
        approvalCount(state, payload) {
            state.count = payload
        },

        //관리자 게시판(미답변) 정보 받기
        boardCnt(state, payload) {
            state.boardcount = payload
        }

    },
    actions: {
        //헤더 바꾸기
        TriggerTO({ commit }, toggleDate) {
            commit('triggerToggle', toggleDate)
        },

        //롤 바꾸기
        _roles({ commit }, login_role) {
            commit('roles', login_role)

        },

    //로그인 시도
    login({state,commit},loginobj){
      localStorage.setItem("role",state.role)
      let role = localStorage.getItem("role")
      

      if(role === 'User'){
        Axios.post(`${baseURL}/signinUser`,loginobj).then(res=> {
           if(res.data.userEmail != null){
                localStorage.setItem("email",loginobj.email)
                  commit('loginSuccess',res.data)
                  router.push({name:'UserReservationList'})
                    //로그인 성공 시 마이페이지로 이동시켜 줌
           }
           else{
            commit('loginError')
           }
           
        }).catch(err => {
          console.log(err)
          commit('loginError')
        })
      }else if (role === 'Sitter'){
        Axios.post(`${baseURL}/signinSitter`,loginobj).then(res=> {
          if(res.data.sitterEmail != null && res.data.approvalStatus === true){
            
            localStorage.setItem("email",loginobj.email)
                    commit('loginSuccess',res.data)
                    
                    router.push({name:'SitterReservationList'})
          }
          else if(res.data.sitterEmail != null && res.data.approvalStatus === false){
           commit('sitterAp')
          }
          else{
            commit('loginError')
          }
       }).catch(err => {
         console.log(err)
         commit('loginError')
       })
      }else if (role === 'Admin'){
        Axios.post(`${baseURL}/signinAdmin`,loginobj).then(res=> {
          if(res.data.adminId != null){
            localStorage.setItem("email",loginobj.email)
               commit('loginSuccess',res.data.adminId)
               commit('triggerToggle',false)
               router.push({name:'adminHome'})
          }
          else{
           commit('loginError')
          }
       }).catch(err => {
         console.log(err)
         commit('loginError')
       })
      }else {
        commit('loginError')
      }
      // Axios.post(`${baseURL}/signin`+role,loginobj) 
      // .then(res => { 
      //   console.log("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
      //   console.log(res.data)
      //   if(res.data.userEmail != null){
      //     console.log(res.data.userEmail)
      //     localStorage.setItem("email",loginobj.email)
            
            
      //       commit('loginSuccess',res.data)
      //       router.push({name:'uMyPage'})
      //         //로그인 성공 시 마이페이지로 이동시켜 줌
      //       }
      //       else if(res.data.sitterEmail != null){
      //         localStorage.setItem("email",loginobj.email)
      //         commit('loginSuccess',res.data)
      //         router.push({name:'sMyPage'})
      //       }
      //       else if(res.data.adminId !== null){
      //         localStorage.setItem("email",loginobj.email)
      //         commit('loginSuccess',res.data.adminId)
      //         commit('triggerToggle',false)
      //         router.push({name:'adminHome'})
      //       }
      //       else{
      //         commit('loginError')
      //       }
      //     }) 
      //     .catch(error => { 
      //       console.log(error)
      //       commit('loginError')
      //     })


            if (role === 'User') {
                Axios.post(`${baseURL}/signinUser`, loginobj).then(res => {
                    if (res.data.userEmail != null) {
                        localStorage.setItem("email", loginobj.email)
                        commit('loginSuccess', res.data)
                        router.push({ name: 'UserReservationList' })
                            //로그인 성공 시 마이페이지로 이동시켜 줌
                    } else {
                        commit('loginError')
                    }

                }).catch(err => {
                    console.log(err)
                    commit('loginError')
                })
            } else if (role === 'Sitter') {
                Axios.post(`${baseURL}/signinSitter`, loginobj).then(res => {
                    if (res.data.sitterEmail != null && res.data.approvalStatus === true) {

                        localStorage.setItem("email", loginobj.email)
                        commit('loginSuccess', res.data)

                        router.push({ name: 'SitterReservationList' })
                    } else if (res.data.sitterEmail != null && res.data.approvalStatus === false) {
                        commit('sitterAp')
                    } else {
                        commit('loginError')
                    }
                }).catch(err => {
                    console.log(err)
                    commit('loginError')
                })
            } else if (role === 'Admin') {
                Axios.post(`${baseURL}/signinAdmin`, loginobj).then(res => {
                    if (res.data.adminId != null) {
                        localStorage.setItem("email", loginobj.email)
                        commit('loginSuccess', res.data.adminId)
                        commit('triggerToggle', false)
                        router.push({ name: 'adminHome' })
                    } else {
                        commit('loginError')
                    }
                }).catch(err => {
                    console.log(err)
                    commit('loginError')
                })
            } else {
                commit('loginError')
            }
            // Axios.post(`${baseURL}/signin`+role,loginobj) 
            // .then(res => { 
            //   console.log("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
            //   console.log(res.data)
            //   if(res.data.userEmail != null){
            //     console.log(res.data.userEmail)
            //     localStorage.setItem("email",loginobj.email)


            //       commit('loginSuccess',res.data)
            //       router.push({name:'uMyPage'})
            //         //로그인 성공 시 마이페이지로 이동시켜 줌
            //       }
            //       else if(res.data.sitterEmail != null){
            //         localStorage.setItem("email",loginobj.email)
            //         commit('loginSuccess',res.data)
            //         router.push({name:'sMyPage'})
            //       }
            //       else if(res.data.adminId !== null){
            //         localStorage.setItem("email",loginobj.email)
            //         commit('loginSuccess',res.data.adminId)
            //         commit('triggerToggle',false)
            //         router.push({name:'adminHome'})
            //       }
            //       else{
            //         commit('loginError')
            //       }
            //     }) 
            //     .catch(error => { 
            //       console.log(error)
            //       commit('loginError')
            //     })


        },
        lsm({ state, commit }) {
            let password = '1234'
            let email = localStorage.getItem("email")
            let role = localStorage.getItem("role")
            if (role === 'Admin' && email !== null) {
                commit('triggerToggle', false)
            }

            if (email != null) {
                Axios.post(`${baseURL}/refresh` + role, { email, password })
                    .then(res => {


                        commit('loginSuccess', res.data)
                        commit('roles', role)
                    })
                    .catch(error => {
                        console.log(error)
                        commit('loginError')
                    })
            }
        },
        logout({ commit }) { //$store.dispatch('logout')으로 접근할 수 있음
            commit('logout')
            commit('triggerToggle', true)
            router.push({ name: 'home' })
        },

        //payload 에서 필요한 부분만 넘기기
        createReservation({ commit }, payload) {

            commit('createReservation', payload)
        },

        createPetInfo({ commit }, payload) {
            commit('forPet', payload)
        },
        
        approval({ commit }, payload) {

            commit('approvalCount', payload)
        },

        boardInfo({ commit }, payload) {
            commit('boardCnt', payload)
        },


    },
    getters: {
        mainPics(state) { //사진 sort하기
            return state.mainPics.sort((picA, picB) => {
                return picA.id > picB.id
            })
        },

        featuredPics(state, getters) { //메인에 걸 사진들만 
            return getters.mainPics.slice(0, 5)
        },
        getMyUuid: (state) => state.me.uuid,
        getHistoryMsgs: (state) => state.history,
    }
})