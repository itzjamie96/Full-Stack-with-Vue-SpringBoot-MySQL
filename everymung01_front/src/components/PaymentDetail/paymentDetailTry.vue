<template>
    <div>
                <v-card
                    class="mx-auto mt-12"
                    max-width="90%"
                >
                    <v-card-text>
                    <p id="detailTitle" class="text-center py-3">예약 정보 확인</p>
                    <v-divider class="my-4 mx-8"></v-divider>

            <v-row class="justify-left pl-6">
                <v-col cols="6">
                    <p id="context">맡기실 반려동물</p>

                </v-col>
                <v-col
                    v-for="pets in paymentVO.petDetailList"
                    :key="pets.petNo"
                    cols="2"
                    class=""
                >
                <p id="request">{{pets.petName}}</p>

                </v-col>
            </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">예약 날짜</p>
                        </v-col>
                        <v-col cols="6">
                   <p id="request">{{paymentVO.startTime | formatDate}}</p>
                </v-col>
            </v-row>
<v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">예약 날짜</p>
                        </v-col>
                        <v-col cols="6">
                    <p id="request">{{paymentVO.startTime| formatTime}} - {{paymentVO.endTime| formatTime}}</p>
                </v-col>
               
            </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">방문 주소</p>
                        </v-col>
                        <v-col cols="6">
                    <p id="request">{{paymentVO.userAddress}}</p>
                </v-col>

            </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">펫시터</p>
                        </v-col>
                        <v-col cols="6">
                    <p id="request">{{paymentVO.sitterName}}</p>
                </v-col>

            </v-row>
                    <v-row class="justify-center">
                        <v-col cols="5">
                            <p id="context">요청사항</p>

                        </v-col>
                        <v-col cols="6">
                   <p id="request">{{paymentVO.request}}</p>
                </v-col>
            </v-row>

            </v-card-text>
            
        </v-card>
    </div>
</template>

<script>
import Axios from 'axios'
import {mapState,mapActions,mapGetters} from "vuex"

export default {

   computed: {
       
       paymentVO() {
           return this.$store.state.reservationList[0]
       },
       
       petVO() {
           return this.paymentVO
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