<template>
  <v-container class="pt-12 px-0">
    <v-row class="justify-center">
        <v-col cols="3"> 
          <side-bar/>   
        </v-col>

        <v-col cols="9" class="">
            <div>
                <v-card
                    class="mx-auto"
                    max-width="100%"
                    tile=""
                >
                    <v-card-text>
                    <p id="detailTitle" class="text-center">예약 상세 정보</p>

                    <v-divider class="my-4"></v-divider>

                    <v-row class="justify-left pl-9">
                        <v-col cols="5">
                            <p id="context">맡기실 반려동물</p>
                        </v-col>
                        <v-col
                            v-for="pets in paymentObj.petDetailList"
                            :key="pets.petNo"
                            cols="2"
                            class="pl-6"
                        >
                            <p id="request">{{pets.petName}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">예약 날짜</p>
                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.startTime | formatDate}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">예약 시간</p>
                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.startTime | formatTime}} - {{paymentObj.endTime | formatTime}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">방문 주소</p>
                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.userAddress}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">시터 타입</p>
                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.sittingType}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">펫시터</p>
                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.sitterName}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">요청사항</p>

                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.request}}</p>
                        </v-col>
                    </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">결제금액</p>

                        </v-col>
                        <v-col cols="6">
                            <p id="request">{{paymentObj.amount}} 원</p>
                        </v-col>
                    </v-row>

                    </v-card-text>
                    
                </v-card>
    </div>
        </v-col>
    </v-row>
  </v-container>
</template>


<script>
import NavBar from '@/components/sitterNavigation.vue'
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

    components: {
    'side-bar' : NavBar
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

<style>

#detailTitle{
    font-size: 19pt;
    font-weight: bold;
    color: rgb(239, 83, 80);
    font-family: 'HangeulNuri-Bold';  
}

#context{
    font-size: 13pt;
    font-family: 'Noto Sans KR', sans-serif;
    /* font-weight: bold; */
    color: black;
}
#request{
    font-size: 13pt;
    font-family: 'Noto Sans KR', sans-serif;
    /* font-weight: bold; */
    /* color: black; */
}
</style>