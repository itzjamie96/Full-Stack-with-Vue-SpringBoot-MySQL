<template>
    <div>
        <v-card
            class="mx-auto"
            max-width="500px"
        >

            <v-card-text>
            <p class="headline text--primary">결제 정보 확인</p>

            <v-row justify="center">
                <v-col>
                    <p class="text--primary">서비스 금액</p>

                </v-col>
                <v-col>
                    <p>15,000 원</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            
             <v-row justify="center"
                v-for="pets in paymentVO.petDetailList"
                :key="pets.petNo">
                <v-col>
                    <p class="text--primary"></p>
                </v-col>
                <v-col>
                    <p>{{pets.size}} X {{pets.homePrice}} 원</p>
                </v-col>
            </v-row>

            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 날짜</p>

                </v-col>
                <v-col>
                    <p>{{paymentVO.startTime | formatDate}} - {{paymentVO.endTime | formatDate}}</p>
                    <p>{{days}}</p>


              
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">결제 금액</p>
                </v-col>
                <v-col>
                    <p>{{totalDays}} 원</p>
                </v-col>
            </v-row>
            <v-row justify="center" class="mt-3">
                <v-card-actions>
                    <v-btn
                        large
                        @click="startKakaoPay"
                    >
                    결제하기
                    </v-btn>
                </v-card-actions>
            </v-row>


            </v-card-text>
            
        </v-card>
    </div>
</template>

<script>
import Axios from 'axios'
import {mapState,mapActions,mapGetters} from "vuex"
import moment from 'moment'

export default {

   data() {
       return {
           paymentObj: [],
           test: null,
           priceObj: [],
       }
       
   },

   created() {
        this.initialize(), 
        this.test = paymentVO.petDetailList

   },
   computed: {

       days() {
           let endYear = Number(this.paymentVO.endDate.split(" ")[0].split("-")[0])
           let endMonth = Number(this.paymentVO.endDate.split(" ")[0].split("-")[1])
           let endDay =  Number(this.paymentVO.endDate.split(" ")[0].split("-")[2])

           let startYear = Number(this.paymentVO.startDate.split(" ")[0].split("-")[0])
           let startMonth = Number(this.paymentVO.startDate.split(" ")[0].split("-")[1])
           let startDay =  Number(this.paymentVO.startDate.split(" ")[0].split("-")[2])

            let end = moment([endYear, endMonth, endDay])
            let start = moment([startYear, startMonth, startDay])     

            let difference = end.diff(start,'days')

           return difference
       },
       paymentNo() {
           return this.$route.params.paymentNo
       },
       paymentVO() {
           return this.$store.state.reservationList[0]
       },
        petVO() {
           return this.paymentVO.petDetailList
       },
       ...mapState(["isLogin","userInfo"]),
        
        total() {
            let t = 0
            for (let no in this.paymentVO.petDetailList) {
                t += this.paymentVO.petDetailList[no].homePrice
            }
            return t * (Number(this.paymentVO.endTime.split(" ")[1].split(":")[0])-Number(this.paymentVO.startTime.split(" ")[1].split(":")[0]))
        },
        totalDays() {
            let d = 0
            for (let no in this.paymentVO.petDetailList) {
                d += this.paymentVO.petDetailList[no].homePrice
            }
            return d * this.days
        }




   },
   methods: {
       initialize() {
            
       },

       startKakaoPay() {
           console.log(this.paymentVO)
           this.paymentVO.amount = this.totalDays
           Axios
            .post('http://localhost:1234/kakaoPay',this.paymentVO)
            .then(res => {
                console.log(res);
                window.open(res.data)
            })
            .catch(err => {
                console.log(err);
            })
       }


   }
}
</script>