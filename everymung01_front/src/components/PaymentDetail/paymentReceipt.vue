<template>
    <div>
        <v-card
            class="mx-auto"
            max-width="500px"
        >

        {{forPet}}
            <v-card-text>
            <p class="headline text--primary">결제 정보 확인</p>

            <v-row justify="center">
                <v-col>
                    <p class="text--primary">기본 금액</p>

                </v-col>
                <v-col>
                    <p>30,000 원</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
             <v-row justify="center">
                <v-col>
                    <p class="text--primary">기본 금액</p>
                </v-col>
            </v-row>
            <v-row justify="left" 
                v-for="price in priceObj"
                :key="price.size">

                <v-col >
                    <p>{{price.size}} X {{price.homePrice}}</p>
                </v-col>
            </v-row>
            <v-row justify="left" 
                v-for="forPet in forPet"
                :key="forPet.num">

                <v-col >
                    <p>{{forPet}}</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
                <v-col>
                    <p>{{paymentObj.startTime | formatTime}} - {{paymentObj.endTime | formatTime}}</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">결제 금액</p>

                </v-col>
                <v-col>
                    <p>가격</p>
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
        forPet() {
           return this.paymentVO.petNum
       },
       pett() {
           let pett = []
           for (let no in this.forPet) {
               pett.push(this.forPet[no])
           }
       }


   },
   methods: {
       initialize() {
            const paymentNo = this.$route.params.paymentNo

            Axios.get(`http://localhost:1234/showAllPrice/${this.forPet}`)
                .then(res => {
                this.priceObj=res.data //객체에 DB에서 받은 데이터를 넣어줌
            })
                .catch(err => {
                    // handle error
                    console.log(err);
                })

       },
       parseDate() {
           const date = paymentObj.startTime
       },
       startKakaoPay() {
           console.log(this.paymentVO)
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