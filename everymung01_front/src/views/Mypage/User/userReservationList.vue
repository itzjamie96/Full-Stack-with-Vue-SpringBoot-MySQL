<template>
  <v-app>
    <v-data-table
    :headers="headers"
    :items="desserts"
    class="elevation-1"
  >
  <!-- 
    heatders ="headers" 는 테이블의 해더부분
    items = "desserts" 는 table에 row에 해당 
    class="elevation-1" vuetify에 지정된 클래스이므로 무시하셈
  -->
   <template v-slot:top>
      <v-toolbar flat color="white">
        <v-toolbar-title>Reservation / Payment List</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
      </v-toolbar>
    </template>
    <template v-slot:item.action="{ item }">

      <!-- v-if 확인 필요 -->
      <!-- 예약 날짜가 지난 시점에서만 리뷰쓰러가는 버튼 보임-->
      <v-icon
        v-if="(canWrite > calEndTime(item.endTime))"
        small
        class="mr-2"
        @click="writeReview(item)"
      >
        review
      </v-icon>
      
      <v-icon
        small
        class="mr-2"
        @click="showDetail(item)"
      >
        search
      </v-icon>
      <v-icon
        small
        @click="deleteItem(item)"
      >
        delete
      </v-icon>
    </template>
  </v-data-table>
  </v-app>
</template>


<script>
import {mapState,mapActions} from "vuex"
import axios from "axios"

  export default {
    data () {
      return {
        headers: [ //테이블의 헤더부분
          {
            text: 'paymentNo',
            align: 'left',
            sortable: false,
            value: 'paymentNo',
          },
          { text: 'sittingType', value: 'sittingType' },
          { text: 'sitterName', value: 'sitterName' },
          { text: 'startTime', value: 'startTime' },
          { text: 'endTime', value: 'endTime' },
          { text: 'amount', value: 'amount' },
          { text: 'paymentStatus', value: 'paymentStatus' },
          { text: 'reviewStatus', value: 'reviewStatus' },
          { text: 'refundStatus', value: 'refundStatus' },
          { text: 'Actions', value: 'action', sortable: false },
        ],
        desserts: [
          //데이터베이스에서 받은 객체들이 들어갈 객체배열
        ],
      }
    },
    
    created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
    
      this.initialize()
      
    },

   

    computed: {
       ...mapState(["isLogin","userInfo"]),

       canWrite(){
        let today = new Date();

        // let dd =today.getDate();
        // let mm =today.getMonth()+1;
        // let yyyy = today.getFullYear();
        // let Hours = today.getHours();
        // let minute = today.getMinutes();
        // let Second = today.getSeconds();

        // let ttoday = yyyy+"-"+mm+"-"+dd+" "+Hours+":"+minute+":"+Second
        return today
        }
    },
    
    methods:{
      initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
      
        axios.get(`http://localhost:1234/showUserPayment/${this.userInfo.userNo}`)
          .then(res => {
            this.desserts=res.data //table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          })
      },

      showDetail(item){
        const paymentNo = item.paymentNo
        this.$router.push({path: '/useReservationDetail/' + paymentNo})
      },

      writeReview(item){
        const sitterNo = item.sitterNo
        const paymentNo = item.paymentNo
        this.$router.push({path: '/daysitters/' + sitterNo +'/' + paymentNo})
        // => 해당 예약의 시터 상세 페이지로 가서 후기 작성.
        // 주소창에 찍히지 않게 변경해야 함.
      },

      calEndTime(endTime){  //item.endTime String 값을 Date 값으로 변환
        let edate = new Date(endTime);
        console.log(edate)
        return edate;
      }
      
    }
  }

  
</script>