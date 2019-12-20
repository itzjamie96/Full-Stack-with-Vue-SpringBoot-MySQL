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
                    <p>{{paymentObj.size}}&nbsp;X </p>

                </v-col>
                <v-col>
                    <p>{{paymentObj.dogBreed}}</p>
                </v-col>
            </v-row>
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
                        @click="$router.push({path: '/payment/' + paymentObj.paymentNo})"
                    >
                    결제 - 다음 창 안만들었음
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


   },
   methods: {
       initialize() {
            const paymentNo = this.$route.params.paymentNo;


        //    Axios
        //     .get(`http://localhost:1234/showDetailPayment/${paymentNo}`)
        //     .then(res => {
        //         this.paymentObj = res.data
        //         console.log(res);
        //     })
        //     .catch(err => {
        //         console.log(err);
        //     })

       },
       parseDate() {
           const date = paymentObj.startTime
       }


   }
}
</script>