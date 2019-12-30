<template>
    <div>
        <div class="headline mx-6">펫시터 후기 {{reivewList.length}} 개</div>
        
        <v-card
            class="mx-auto mb-10"
            max-width="92%"   
            elevation="1"
        >
        <v-container>
            <v-card
                class="mb-4 mt-2 mx-auto"
                max-width="85%"
                outlined
                v-for="review in reivewList"
                :key="review.id"
            >
            <v-list-item three-line>

            <v-list-item-content class="mx-auto">
                <p class="font-weight-bold">{{review.userName}}</p>
                <p class="font-weight-bold ">{{review.reviewDate | formatDate}}</p> 
                <p class="font-weight-bold">{{review.stars}}</p>
                <p class="font-weight-bold">{{review.reviewContent}}</p>
            </v-list-item-content>

            </v-list-item>
            </v-card>
        </v-container>

        <!-- 예약한 사람만 후기 작성창 볼 수 있음 -->
         <v-card 
                v-if="canWrite"
                color="green"
                height="100%"
                class="mb-5"
        >
            <v-form @submit.prevent="addReview" >

                <v-row justify="center">
                    <v-col cols="10">
                        <v-textarea
                        name="reviewContent"
                        label="후기를 입력해주세요."
                        id="reviewContent"
                        v-model="reviewVO.reviewContent"
                        >   
                        </v-textarea>
                    </v-col>
                </v-row>
                <v-row justify="center" class="mb-5">
                    <v-btn 
                    type="submit"
                    >등록</v-btn>
                </v-row>
            </v-form>
        </v-card>
        

        </v-card>
    </div>
</template>

<script>
import axios from "axios" 
import {eventBus} from '@/main.js'

export default {
    data () {
      return {
        paramPaymentNo: 0,
        reivewList: [ //데이터베이스에서 받은 객체들이 들어갈 객체배열
        ],
        sitterInfo: [],
        reviewVO: {
            paymentNo: '',
            reviewContent: '',
            stars: '',
            reviewDate: ''
        },
      }
    },
    created(){ 
      this.initialize()
    },

    computed: {
      canWrite(){  
        //마이페이지 예약내역에서 pamentNo 넘겨받아 후기작성창 출력 조건 체크 
        return this.paramPaymentNo > 0
      }
    },

    methods:{
      initialize(){
      this.paramPaymentNo = this.$route.params.paymentNo;
      this.reviewVO.paymentNo = this.paramPaymentNo  //reviewVO에 paymentNo 넣음

      eventBus.$on('sitterObj', (sitterObj) => {
          this.sitterInfo = sitterObj
          const sitterNo = this.sitterInfo.sitterNo

        this.showList()
      }) 

      },

      showList(){ //후기 리스트 불러오는 axios 따로 뺌
        axios.get(`http://localhost:1234/showSitterReview/${this.sitterInfo.sitterNo}`)
          .then(res => {
            this.reivewList=res.data //객체에 DB에서 받은 데이터를 넣어줌
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          })
      },

       addReview() { //후기 등록
         axios.post('http://localhost:1234/addReview', this.reviewVO)
          .then(res => {
            console.log(res);
            this.showList();
          })
          .catch(err => {
            console.log(err);
          })

    },



    }
}

</script>