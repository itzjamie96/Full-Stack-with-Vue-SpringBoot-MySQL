<template>
<div v-if="this.userInfo.sittingType==='home'">
  <v-container class="pt-12 px-0">
    <v-row class="justify-center">
        <v-col cols="3"> 
          <side-bar/>   
        </v-col>

    <v-col cols="9" >
      <v-card id="cal"
        tile
        elevation="1"
        > 
        <span id="list-btn" @click="$router.push({path: '/sMyPage/sitterReservationList'})">리스트보기</span>
        <full-calendar :events="schedule" locale="ko"
          @eventClick="showDetail"
          id="calendar"
        >
        </full-calendar> 
        <v-row>
          <v-col cols="12"></v-col>
        </v-row>

      </v-card>
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
<style>
#list-btn{
  background-color: rgba(0,0,0,0);
  color: black;
  padding: 1px 0 1px 0;
  font-size: 15pt;
  /* font-family: 'HangeulNuri-Bold';  */
  cursor: pointer;
  transition-duration: 0.4s;
  border-radius: 4px;
  margin-left: 36em;
}
#list-btn:hover{
  box-shadow: 0 6px 10px 0 rgba(0,0,0,0.19),0 17px 50px 0 rgba(0,0,0,0.1);

}
#cal{
  background-color: white;
  margin-top: 0px;
}
.comp-full-calendar {
    padding: 0 20px;
    background: #fff;
    max-width: 800px;
    margin: 0 auto;
}
.full-calendar-body {
    margin-top: 0px;
}
.full-calendar-body .dates .dates-events .events-week .events-day {
    cursor: unset;
}
.full-calendar-body .dates .dates-events .events-week .events-day .event-box .event-item {

    background-color: rgb(253, 231, 169,0.8);

    color: rgba(0, 0, 0);

}
</style>