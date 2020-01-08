<template>
    <div>
        <div class="headline mx-6">펫시터 후기 {{reviewList.length}} 개</div>
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
                v-for="review in reviews"
                :key="review.id"
            >
            <v-list-item three-line>

            <v-list-item-content class="mx-auto">
                <p class="font-weight-bold">{{review.userName}}</p>
                <p class="subtitle-1 ">{{review.reviewDate| formatDate}}</p> 
                <v-rating
                      v-model="review.stars"
                      background-color="orange lighten-3"
                      color="orange"
                      readonly
                ></v-rating>
                <p class="subtitle-1 ">{{review.reviewContent}}</p>
            </v-list-item-content>

            </v-list-item>
            </v-card>
              <v-btn 
                @click="appendReviews()"
                :disabled="this.dataFull==true"
                >후기 더보기 ({{reviews.length}} / {{totalReviews}}) </v-btn>
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

                   <v-rating
                      v-model="reviewVO.stars"
                      background-color="orange lighten-3"
                      color="orange"
                    ></v-rating>

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
        reviewList: [ //리뷰리스트
        ],
        sitterInfo: [],
        reviewVO: {
            paymentNo: '',
            reviewContent: '',
            stars: null,
            reviewDate: ''
        },
        rating: null,
        writeCheck: 1,
        reviews: [], //출력할 리뷰 리스트
        countReviews: 3,
        dataFull: false, 
        totalReviews: 0,
      }
    },
    created(){ 
      this.initialize()
    },

    computed: {
      canWrite(){  
        //마이페이지 예약내역에서 pamentNo 넘겨받아 후기작성창 출력 조건 체크 
        return this.paramPaymentNo > 0 && this.writeCheck ==1
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
            console.log(res)
            let data = []
            this.reviewList=res.data //객체에 DB에서 받은 데이터를 넣어줌
            if(this.reviewList.length<=3){
              for(let i=0; i<this.reviewList.length; i++){
                  data.push(res.data[i])
                }
              this.dataFull = true
            }
            else{
                for(let i=0; i<this.countReviews; i++){
                  data.push(res.data[i])
                }
            }
            this.reviews = data
            this.totalReviews = this.reviewList.length
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
            this.writeCheck++;
            this.$router.push({path:'/daysitters/' + this.sitterInfo.sitterNo})
          })
          .catch(err => {
            console.log(err);
          })
      },

      appendReviews() {
        if(this.countReviews < this.totalReviews) {
          let nowCount = this.countReviews
          this.countReviews += 3

          const append = this.reviewList.slice(nowCount,this.countReviews)
          this.reviews = this.reviews.concat(append)
          if(this.countReviews>=this.totalReviews){
            this.dataFull = true
          }
        }else {
          this.dataFull = true
        }
      }



    }
}

</script>