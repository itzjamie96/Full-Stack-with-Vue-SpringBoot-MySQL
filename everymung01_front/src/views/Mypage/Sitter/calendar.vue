<template>
<div v-if="this.userInfo.sittingType==='home'">
<v-container fluid="" class="fill-height">
  <v-row class="fill-height">
    <v-col cols="3">
      <side-bar/>   
    </v-col>

    <v-col cols="9">
      <router-link :to="{path:'/sMyPage/sitterReservationList'}" exact><h3>리스트보기</h3></router-link>
      <h3 @click="$router.push({path: '/sMyPage/sitterReservationList'})">리스트보기</h3>
      <v-btn @click="$router.push({path: '/sMyPage/sitterReservationList'})">리스트보기</v-btn>
      <full-calendar :events="schedule" locale="ko"
        @eventClick="showDetail"
      >
      </full-calendar> 
    </v-col>
  </v-row>

</v-container>
</div>
</template>

<script>
import {mapState,mapActions} from "vuex"
import axios from "axios"
import NavBar from '@/components/sitterNavigation.vue'

export default {

  data () {
	  return {
      schedule : [ ],
      paylist: [],
      no: null
	  }
  },
  components : {
  'full-calendar': require('vue-fullcalendar')	,
  'side-bar' : NavBar
  },

      created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
    
      this.initialize()
      
    },

        computed: {
       ...mapState(["isLogin","userInfo"]),



    },

    methods : {

    'eventClick' (event, jsEvent, pos) {
       console.log('eventClick', event, jsEvent, pos)
        this.$router.push({path: '/'})

    },
    initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
        console.log(this.userInfo.sitterNo)
        axios.get(`http://localhost:1234/showPaymentCalendar/${this.userInfo.sitterNo}`)
          .then(res => {
            this.paylist=res.data //table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌
            console.log(res.data);
            for(let i in res.data){
              let range = {title:'',start:'',end:'',t:null}
              range.title=res.data[i].startTime.split(" ")[1].slice(0,5) + "~" + res.data[i].endTime.split(" ")[1].slice(0,5)
              range.start=res.data[i].startTime.split(" ")[0]
              range.end=res.data[i].endTime.split(" ")[0]
              this.no = res.data[i].paymentNo
              range.t = res.data[i].paymentNo
              console.log(range.t)
              this.schedule.push(range)
            }
          })
          .catch(err => {
            console.log(err);
          })
      },
      
      showDetail(payment){
        this.$router.push({path: '/sMyPage/sitterReservationDetail/' + payment.t})
      },

  },
}
</script>