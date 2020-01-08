<template>
<div>
<div v-if="this.userInfo.sittingType==='daycare'">
  <v-container fluid="" class="fill-height">
    <v-row class="fill-height">
        <v-col cols="3">
          <side-bar/>   
        </v-col>

        <v-col cols="9">
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
<v-container fluid="" class="fill-height">
    <v-row class="fill-height">
        <v-col cols="3">
          <side-bar/>   
        </v-col>
        <v-col cols="9">
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
              <router-link :to="{path:'/sMyPage/calendar'}" exact><h3>캘린더보기</h3></router-link>
              <h3 @click="$router.push({path: '/sMyPage/calendar'})">캘린더보기</h3>
              <v-btn @click="$router.push({path: '/sMyPage/calendar'})">캘린더보기</v-btn>
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
          <template v-slot:item.action="{ item }">

            
            <v-icon
              v-if="item.paymentStatus==false && item.refundStatus==false"
              small
              class="mr-2"
              @click="approval(item)"
            >
              [승인]
            </v-icon>
            <v-icon
              v-if="item.paymentStatus==false && item.refundStatus==false"
              small
              class="mr-2"
              @click="refund(item)"
            >
              [거절]
            </v-icon>
            <v-icon
              v-if="item.paymentStatus==true && item.refundStatus==false"
              small
              class="mr-2"
            >
              [승인완료]
            </v-icon>
            <v-icon
              v-if="item.paymentStatus==false && item.refundStatus==true"
              small
              class="mr-2"
            >
              [환불완료]
            </v-icon>
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