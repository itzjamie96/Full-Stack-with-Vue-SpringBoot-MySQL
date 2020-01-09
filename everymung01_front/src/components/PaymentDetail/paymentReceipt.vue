<template>
    <div>
        <v-card
            class="mx-auto mt-12"
            max-width="90%"
        >
            <v-card-text>
            <p id="detailTitle" class="text-center py-3">결제 정보 확인</p>
            <v-divider class="my-4 mx-8"></v-divider>
            
            <v-row class="justify-center"
                v-for="pets in paymentVO.petDetailList"
                :key="pets.petNo">
                
            <p id="request">{{pets.size}} X {{pets.dayPrice}} 원</p>
                
            </v-row>

            <v-divider class="my-4 mx-12"></v-divider>
            <v-row class="justify-center">                
            <p id="request">{{paymentVO.startTime | formatTime}} - {{paymentVO.endTime | formatTime}} : 총 {{lsm}}시간</p>

            </v-row>
            <v-divider class="my-4 mx-8"></v-divider>
            <v-row class="justify-center"> 
                    <p id="request"> 결제 금액: {{total}} 원</p>
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