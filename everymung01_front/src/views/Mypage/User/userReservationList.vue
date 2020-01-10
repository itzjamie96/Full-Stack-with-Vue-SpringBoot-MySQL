<template>
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
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
          <template v-slot:item.action="{ item }" class="text-center">

            
            <!-- 예약 날짜가 지난 시점이고, 
                  reviewStatus가 false 일 때만 (review 미작성 상태) 
                  리뷰쓰러가는 버튼 보임
                  v-if="(canWrite > calEndTime(item.endTime) && item.reviewStatus == false)"
                  -->

            <!-- 똑같은 공백 만들고 싶어서...여백 만들기 -->
            <span
              v-if="(canWrite < calEndTime(item.startTime) && item.refundStatus == false 
                    && item.paymentStatus==true && item.sittingType=='daycare')"
              id="statusMsg"
            >
              아직 안함
            </span>
            <span
              v-if="(canWrite > calEndTime(item.startTime) && item.refundStatus == false 
                    && item.paymentStatus==true && item.sittingType=='daycare')"
              id="statusMsg"
            >
              이미 완료
            </span>

           
            <span
              v-if="item.sittingType=='home' && item.paymentStatus==false && canWrite < calEndTime(item.startTime)"
              id="status"
              
            >
              승인 대기
            </span>
            <span v-if="item.sittingType=='home' && item.paymentStatus==true" 
              id="status"
              >
              승인 완료
            </span>
            <span
              v-if=" item.refundStatus==true"
              id="status"
              
            >
              환불 완료
            </span>

            <button
              @click="showDetail(item)"
              type="button"
              id="click"
            >
              상세 보기
            </button>
            <span
              @click="writeReview(item)"
              id="click"
              v-if="(canWrite > calEndTime(item.endTime) && item.reviewStatus == false)"
            >
              후기 쓰기
            </span>  
            <!-- 예약 날짜가 되기 전에만 환불 가능-->
            <button
              v-if="(canWrite < calEndTime(item.startTime) && item.refundStatus == false)"
              type="button"
              id="click"
              @click="refund(item)"
            >
              환불 가능
            </button>
          </template>
        </v-data-table>
        </v-col>
    </v-row>
  </v-container>
</template>


<script>
import {mapState,mapActions} from "vuex"
import axios from "axios"
import NavBar from '@/components/userNavigation.vue'

  export default {
    data () {
      return {
        headers: [ //테이블의 헤더부분
          // {
          //   text: 'paymentNo',
          //   align: 'left',
          //   sortable: false,
          //   value: 'paymentNo',
          // },
          { text: '시팅 유형', value: 'sittingType' },
          { text: '펫시터', value: 'sitterName' },
          { text: '시작 날짜', value: 'startTime' },
          { text: '종료 날짜', value: 'endTime' },
          { text: '금액', value: 'amount' },
          // { text: '결제 상태', value: 'paymentStatus' },
          // { text: 'reviewStatus', value: 'reviewStatus' },
          // { text: 'refundStatus', value: 'refundStatus' },
          { text: '상태', value: 'action', sortable: false },
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

      canWrite(){
        let today = new Date();
        return today
      },
      
    },
    
    methods:{
      initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
      
        axios.get(`http://localhost:1234/showUserPayment/${this.userInfo.userNo}`)
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
        this.$router.push({path: '/userReservationDetail/' + paymentNo})
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
      }
      
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

#status{
    color: rgb(239, 83, 80);
    font-weight: bold;
    margin-right: 6px;
}
#statusMsg{
  color: rgba(0,0,0,0);
  margin-right: 6px;
}

</style>