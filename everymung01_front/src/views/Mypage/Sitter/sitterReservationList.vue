<template>
<div>
<div v-if="this.userInfo.sittingType==='daycare'">
  <v-container class="pt-12 px-0">
    <v-row class="justify-center">
        <v-col cols="3"> 
          <side-bar/>   
        </v-col>

        <v-col cols="9" class="">
          <v-data-table
            :headers="headers"
            :items="paylist"
            class="elevation-1"
        >

        <template v-slot:top>
            <v-toolbar flat color="white">
              <v-toolbar-title>예약 내역</v-toolbar-title>
              <v-divider
                class="mx-4"
                inset
                vertical
              ></v-divider>
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
          <template v-slot:item.action="{ item }">
            <!-- 유저가 환불 했을 때 예약 취소된 상태-->
            <v-icon
              v-if="item.paymentStatus==true && item.refundStatus==true"
              small
              class="mr-2"
            >
              [예약취소]
            </v-icon>
            <v-icon
              small
              class="mr-2"
              @click="showDetail(item)"
            >
              search
            </v-icon>
          </template>
        </v-data-table>
        </v-col>
    </v-row>
  </v-container>
</div>

<div v-if="this.userInfo.sittingType==='home'">
  <v-container class="pt-12 px-0">
    <v-row class="justify-center">
        <v-col cols="3"> 
          <side-bar/>   
        </v-col>

        <v-col cols="9" class="">
          <v-data-table
            :headers="headers"
            :items="paylist"
            class="elevation-1"
        >
        <template v-slot:top>
            <v-toolbar flat color="white">
              <p id="reserveTitle" class="mt-3">예약 내역</p>
              <v-divider
                class="mx-4"
                inset
                vertical
              ></v-divider>
              <span id="calendar-btn" @click="$router.push({path: '/sMyPage/calendar'})">캘린더보기</span>
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
          <template v-slot:item.action="{ item }" class="text-center">

            <!-- 유저가 환불 했을 때 예약 취소된 상태-->
            <!-- 상태 메세지 -->
            <span
              v-if="item.paymentStatus==true && item.refundStatus==true"
              id="statusMsg"
            >
              예약 취소
            </span>
            <span
              v-if="item.paymentStatus==true && item.refundStatus==false"
              id="statusMsg"              
            >
              승인 완료
            </span>
            <span
              v-if="item.paymentStatus==false && item.refundStatus==true"
              id="statusMsg" 
            >
              환불 완료
            </span>
            <!-- 버튼 -->
            <button
              @click="showDetail(item)"
              type="button"
              id="click"
            >
              상세 보기
            </button>
            <span
              v-if="item.paymentStatus==false && item.refundStatus==false"
              id="click"
              @click="approval(item)"
            >
              승인
            </span>
            <span
              v-if="item.paymentStatus==false && item.refundStatus==false"
              id="click"
              @click="refund(item)"
            >
              [거절]
            </span>

          </template>
        </v-data-table>
        </v-col>
    </v-row>
  </v-container>
</div>

</div>

</template>


<script>
import {mapState,mapActions} from "vuex"
import axios from "axios"
import NavBar from '@/components/sitterNavigation.vue'

  export default {
    data () {
      return {
        headers: [ //테이블의 헤더부분
          { text: '예약 번호', value: 'paymentNo' },
          { text: '예약자', value: 'userName' },
          { text: '예약 날짜', value: 'startTime' },
          { text: '예약 날짜', value: 'endTime' },
          { text: '금액', value: 'amount' },
          // { text: '결제 상태', value: 'paymentStatus' },
          // { text: 'reviewStatus', value: 'reviewStatus' },
          // { text: 'refundStatus', value: 'refundStatus' },
          { text: 'Actions', value: 'action', sortable: false },
        ],
        paylist: [
          //데이터베이스에서 받은 객체들이 들어갈 객체배열
        ],
      }
    },
    
    components: {
    'side-bar' : NavBar
    },

    created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
    
      this.initialize()
      
    },

   

    computed: {
       ...mapState(["isLogin","userInfo"]),

    },
    
    methods:{
      initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
        console.log(this.userInfo.sitterNo)
        axios.get(`http://localhost:1234/showSitterPayment/${this.userInfo.sitterNo}`)
          .then(res => {
            this.paylist=res.data //table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          })
      },

      showDetail(item){
        const paymentNo = item.paymentNo
        this.$router.push({path: '/sMyPage/sitterReservationDetail/' + paymentNo})
      },

      approval(item){
        const paymentNo = item.paymentNo
        console.log(paymentNo)
        axios.get(`http://localhost:1234/updatePaymentStatus/${paymentNo}`)
          .then(res => {
            console.log(res)
            // window.location.reload()
            this.$router.push({path: '/sMyPage/calendar'})
          })
          .catch(err => {
            console.log(err)
          })
      },

      refund(item) {
        axios
          .post('http://localhost:1234/refund',item)
          .then(res => {
            console.log(res);
            window.location.reload()
          })
          .catch(err => {
            console.log(err);
          })
      },
      
    }
  }

  
</script>

<style>

#reserveTitle{
    font-size: 19pt;
    font-weight: bold;
    color: rgb(239, 83, 80);
    font-family: 'HangeulNuri-Bold';  
}
#calendar-btn{
  background-color: rgba(0,0,0,0);
  color: black;
  padding: 1px 0 1px 0;
  font-size: 15pt;
  /* font-family: 'HangeulNuri-Bold';  */
  cursor: pointer;
  transition-duration: 0.4s;
  border-radius: 4px;
}
#calendar-btn:hover{
  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.19),0 17px 50px 0 rgba(0,0,0,0.1);

}
.v-data-table th{
  font-size: 0.9em;
}

#click{
  font-size: 10.5pt;
  padding: 3px 8px 3px 8px;
  transition-duration: 0.4s;
  border-radius: 4px;
  font-weight: bold;
  margin: 0 6px 0 6px;
  cursor: pointer;
  background-color: rgba(0,0,0,0.05);

}
#click:hover{
  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.23),0 17px 50px 0 rgba(0,0,0,0.19);

}

#statusMsg{
    color: rgb(239, 83, 80);
    font-weight: bold;
    margin-right: 6px;
}



</style>