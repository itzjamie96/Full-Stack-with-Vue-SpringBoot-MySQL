<template>
    <div>
        <v-card
            class="mx-auto"
            max-width="344"
        >
            <v-card-text>

                {{
                   paymentVO.usersPets.length     
                }}

            <p class="headline text--primary">예약 정보 확인</p>

            <v-row justify="center">
                <v-col>
                    <p class="text--primary">맡기실 반려동물</p>

                </v-col>
                <v-col>
                    <p>{{String(paymentVO.usersPets)}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 날짜</p>

                </v-col>
                <v-col>
                   <p>{{paymentVO.date}}</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>
                </v-col>
                <v-col>
                    <p>{{paymentVO.startTime| formatTime}} - {{paymentVO.endTime| formatTime}}</p>
                </v-col>
               
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">방문 주소</p>

                </v-col>
                <v-col>
                    <p>{{paymentVO.userAddress}}</p>
                </v-col>

            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">펫시터</p>
                </v-col>
                <v-col>
                    <p>{{paymentVO.sitterName}}</p>
                </v-col>

            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">요청사항</p>
                </v-col>
                <v-col>
                   <p>{{paymentVO.description}}</p>
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

   }
}
</script>