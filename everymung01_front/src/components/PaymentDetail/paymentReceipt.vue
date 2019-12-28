<template>
    <div>
        <v-card
            class="mx-auto"
            max-width="344"
        >
        
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
            <v-row justify="center" class="mt-3">
                <v-col>
                    <p>{{paymentVO.size}}&nbsp;X </p>

                </v-col>
                <v-col>   
                    <p>{{paymentVO.dogBreed}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
                <v-col>
                    <p>{{paymentVO.startTime | formatTime}} - {{paymentVO.endTime | formatTime}}</p>
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

export default {

   data() {
       return {
           paymentObj: [],
       }
   },

   created() {
        this.initialize()
   },
   computed: {
       paymentNo() {
           return this.$route.params.paymentNo
       },
       paymentVO() {
           return this.$store.state.reservationList[0]
       },


   },
   methods: {
       initialize() {
            const paymentNo = this.$route.params.paymentNo;

       },
       parseDate() {
           const date = paymentVO.startTime
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