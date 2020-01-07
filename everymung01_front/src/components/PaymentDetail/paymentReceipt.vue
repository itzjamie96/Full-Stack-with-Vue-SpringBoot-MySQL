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
                    <p class="text--primary">기본 금액</p>
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
                    <p>{{pets.size}} X {{pets.dayPrice}} 원</p>
                </v-col>
            </v-row>

            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
                <v-col>
                    <p>{{paymentVO.startTime | formatTime}} - {{paymentVO.endTime | formatTime}} : 총 {{lsm}}시간</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">결제 금액</p>
                </v-col>
                <v-col>
                    <p>{{total}} 원</p>
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
       lsm(){
           return Number(this.paymentVO.endTime.split(" ")[1].split(":")[0])-Number(this.paymentVO.startTime.split(" ")[1].split(":")[0])
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
                t += this.paymentVO.petDetailList[no].dayPrice
            }
            return t * (Number(this.paymentVO.endTime.split(" ")[1].split(":")[0])-Number(this.paymentVO.startTime.split(" ")[1].split(":")[0]))
        }


       /*
        forPet() {
           return this.paymentVO.petNum
       },
       pett() {
           let pett = []
           for (let no in this.forPet) {
               pett.push(this.forPet[no])
           }
       }*/


   },
   methods: {
       initialize() {
       },

       startKakaoPay() {
           this.paymentVO.amount = this.total
           Axios
            .post('http://localhost:1234/kakaoPay',this.paymentVO)
            .then(res => {
                console.log(res);
                window.location.replace(res.data)
            })
            .catch(err => {
                console.log(err);
            })
       }


   }
}
</script>