<template>
    <div>
        <v-card
            class="mx-auto"
            max-width="500"
        >
            <v-card-text>
            <p class="headline text--primary">예약 상세 정보</p>

            <v-row justify="left">
                <v-col cols="6">
                    <p class="text--primary">맡기실 반려동물</p>
                </v-col>
                <v-col
                    v-for="pets in paymentObj.petDetailList"
                    :key="pets.petNo"
                    cols="2"
                >
                    <p>{{pets.petName}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 날짜</p>
                </v-col>
                <v-col>
                    <p>{{paymentObj.startTime | formatDate}}</p>
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
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">방문 주소</p>
                </v-col>
                <v-col>
                    <p>{{paymentObj.userAddress}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">시터 타입</p>
                </v-col>
                <v-col>
                    <p>{{paymentObj.sittingType}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">펫시터</p>
                </v-col>
                <v-col>
                    <p>{{paymentObj.sitterName}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">요청사항</p>

                </v-col>
                <v-col>
                    <p>{{paymentObj.request}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">결제금액</p>

                </v-col>
                <v-col>
                    <p>{{paymentObj.amount}}</p>
                </v-col>
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
           paymentObj: {
            //pets: '',
            startTime: '',
            endTime: '',
            request: '',
            sitterNo: '',
            sittingType: '',
            sitterName: '',
            sitterPhone: '',
            sitterAddress: '',
            paymentMethod: '',
            amount: '',
            petNo: '',
            userName: '',
            userAddress: '',
            petName: '',
            dogBreed: '',
            size: ''
        }
       }
   },

   created() {
       this.initialize()
   },

   computed: {

   },
   methods: {
       initialize() {
           
            const paymentNo = this.$route.params.paymentNo;
           
           Axios
            .get(`http://localhost:1234/showDetailPayment/${paymentNo}`)
            .then(res => {
                this.paymentObj = res.data
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })
     
       },
       parseDate() {
           const date = paymentObj.startTime
       }

   }
}
</script>