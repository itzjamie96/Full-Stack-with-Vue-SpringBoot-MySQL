<template>
<v-container>
    <!-- 카카오 페이 성공 시 넘어오는 페이지 -->
    <v-row><v-col></v-col></v-row>
    <v-row><v-col></v-col></v-row>
    <v-row><v-col></v-col></v-row>
    <v-row><v-col></v-col></v-row>
    <v-card
        class="mx-auto mt-12 pa-3"
        max-width="400px">

    <v-row class="justify-center mb-5">
        <h1>결제가 완료되었습니다</h1>
    </v-row>

    <v-row class="justify-center pb-3">
        <router-link :to="{name:'UserReservationList'}" exact>예약 내역에서 확인하기</router-link>
    </v-row>
    </v-card>
        <!-- kakao pay success 컨트롤러에서 넘어오는 리턴 값--> 
        <!-- <h2>{{this.kakao}}</h2> -->
</v-container>
</template>

<script>
import axios from "axios"

export default {
    data() {
        return{
            kakao : '',
            paymentObj: []
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
        petVO() {
           return this.paymentVO.petDetailList
       }


   },
    methods: {

    initialize(){
        const kakaoUrl = this.$route.query.pg_token
        console.log(kakaoUrl)
        axios.get(`http://localhost:1234/kakaoPaySuccesss?pg_token=${kakaoUrl}`)
                .then(res => {
                    this.kakao = res.data
                    console.log(res);
                })
                .catch(err => {
                    console.log(err);
                })

        const paymentNo = this.$route.params.paymentNo;


        }
    }

}
</script>