<template>
    <div>
        <v-card
            class="mx-auto"
            max-width="344"
            v-for="loadedReserve in loadedReserve"
            :key="loadedReserve.userNo"
        >
            <v-card-text>

            <p class="headline text--primary">예약 정보 확인</p>

            <v-row justify="center">
                <v-col>
                    <p class="text--primary">맡기실 반려동물</p>

                </v-col>
                <v-col>
                    <p>{{loadedReserve.usersPets}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 날짜</p>

                </v-col>
                <v-col>
                    <p>{{loadedReserve.startTime}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
               
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">방문 주소</p>

                </v-col>

            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">펫시터</p>

                </v-col>

            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">요청사항</p>

                </v-col>
                <v-col>
                    <p>{{this.loadedReserve.description}}</p>
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
           paymentVO: {
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
        },
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
       paymentNo() {
           return this.$route.params.paymentNo
       },

       loadedReserve() {
           return this.$store.getters.loadedReserve
       }


   },
   methods: {
       initialize() {
            // const paymentNo = this.$route.params.paymentNo;
       

            this.paymentObj.request = this.paymentVO.request
            console.log('request')
            console.log(this.paymentObj.request)
           
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