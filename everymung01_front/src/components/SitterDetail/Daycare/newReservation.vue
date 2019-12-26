<template>
    <div>
        
        <v-card 
                color="green"
                height="100%"
                class="mb-5"
        >
            <v-form @submit.prevent="onNewReservation" >
                <v-row justify="center">
                    <v-col cols="12" sm="10">
                        <v-select
                            v-model="usersPets"
                            :items="pets"
                            attach
                            chips
                            label="반려동물 선택"
                            multiple
                            id="usersPets"
                        ></v-select>
                    </v-col>
                </v-row>

                <v-row class="mb-3" justify="center">
                    <v-col cols="10">
                        <v-menu
                            v-model="menu1"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            min-width="290px"
                        >
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                v-model="date"
                                label="날짜를 선택하세요"
                                readonly
                                v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="date" @input="menu1 = false"></v-date-picker>
                        </v-menu>
                    </v-col>
                </v-row>

                <v-row justify="center">
                    <v-col cols="5">
                        <v-select
                            :items="time"
                            label="체크인 시간"
                            v-model="startTime"
                            id="starTime"
                        ></v-select>
                    </v-col>
                    <v-col cols="5">
                        <v-select
                            :items="time"
                            label="체크아웃 시간"
                            v-model="endTime"
                            id="endTime"
                        ></v-select>
                    </v-col>
                </v-row>

                <v-row justify="center">
                    <v-col cols="10">
                        <v-textarea
                        name="description"
                        label="Description"
                        id="description"
                        v-model="description"
                        >   
                        </v-textarea>
                    </v-col>
                    
                </v-row>
                
                <v-row justify="center" class="mb-5">
                    <v-btn 
                    :disabled="!formIsValid"
                    type="submit"
                    >예약하기</v-btn>
                </v-row>
            </v-form>

            <v-row justify="center" class="mt-5">
                <v-simple-table>
                    <template>
                   
                    <thead>
                        <tr>
                            <th class="subtitle-1 font-weight-bold">체급</th>
                            <th class="subtitle-1 font-weight-bold">시간 당 가격</th>
                        </tr>
                    </thead>
                    <thead class="text-center">
                        <tr
                            v-for="price in price"
                            :key="price.size"
                        >
                            <td>{{price.size}}</td>
                            <td>{{price.cost}} 원</td>
                        </tr>
                    </thead>
                   
                    </template>
                </v-simple-table>
            </v-row>

        </v-card>
    </div>
</template>

<script>
import axios from "axios"
import {eventBus} from '@/main.js'
import {mapState,mapActions} from "vuex"

const dt = new Date();

export default {
  data() {
    return {
        sitterInfo: [],
        date: dt.toISOString().substr(0, 10), 
        menu: false,
        modal: false,
        menu1: false,
        menu2: false,
        timeStep: '00:00',
        time: ['10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00'],
        pets: ['Foo', 'Bar', 'Fizz', 'Buzz'],
        
        userPets: [],
        description: '',
        startTime: '',
        endTime: '',
        
        price: [ 
            {
                size: '소형견',
                cost: '5,000'
            },
            {
                size: '중형견',
                cost: '6,000'
            },
            {
                size: '대형견',
                cost: '7,000'
            }
        ],
        
        
        reservation: {
            pets: '',
            date: '',
            startTime: '',
            endTime: '',
            description: ''
        },
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

        }
        
        
    }
  },
  created() {
      eventBus.$on('sitterObj', (sitterObj) => {
        //   console.log(sitterObj)
          this.sitterInfo = sitterObj
      })
      
  },
  computed: {
    formIsValid() {
      return this.description !== '' && this.userPets !== '' && this.startTime !== '' && this.endTime !== ''
    },
    minutesToZero(dt) {
        return (dt.getMinutes() < 10 ? '0 ' : '') + (dt.getMinutes() < 10 ? '0 ' : '')
    },

    ...mapState(["isLogin","userInfo"]),
   

  },
  methods: {
    onNewReservation() {

        const reserveData = {
            userPets: this.usersPets,
            date: this.date,
            startTime: this.date + " "+ this.startTime,
            endTime: this.date + " "+ this.endTime,
            description: this.description,
            userNo: this.userInfo.userNo
        }
        this.$store.dispatch('createReservation', reserveData)

        //넘길 객체에 펫 정보 추가

        this.reservation.date = this.date
        this.paymentVO.userName = this.userInfo.userName
        this.paymentVO.userAddress = this.userInfo.userAddress
        this.paymentVO.startTime = this.reservation.date + " " + this.reservation.startTime
        this.paymentVO.endTime = this.reservation.date + " " + this.reservation.endTime
        this.paymentVO.request = this.reservation.description
        this.paymentVO.sitterNo = this.sitterInfo.sitterNo,
        this.paymentVO.sittingType = this.sitterInfo.sittingType,
        this.paymentVO.sitterName = this.sitterInfo.sitterName,
        this.paymentVO.sitterPhone = this.sitterInfo.sitterPhone,
        this.paymentVO.sitterAddress = this.sitterInfo.sitterAddress,

        this.$router.push(`/paymentinfo/${this.userInfo.userNo}`) //예약확인 페이지로 수정 필요
        

    },

    

    allowedStep: m => m % 0 === 0

  }
}
</script>