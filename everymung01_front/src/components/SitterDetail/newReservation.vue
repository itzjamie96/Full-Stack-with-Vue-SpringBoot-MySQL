<template>
    <div>
        {{this.sitterInfo}}

        {{this.sitterInfo.sitterNo}}
        <v-card 
                color="green"
                height="100%"
                class="mb-5"
        >
            <v-form @submit.prevent="onNewReservation" >
                <v-row justify="center">
                    <v-col cols="12" sm="10">
                        <v-select
                            v-model="reservation.pets"
                            :items="pets"
                            attach
                            chips
                            label="반려동물 선택"
                            multiple
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
                            v-model="reservation.startTime"
                        ></v-select>
                    </v-col>
                    <v-col cols="5">
                        <v-select
                            :items="time"
                            label="체크아웃 시간"
                            v-model="reservation.endTime"
                        ></v-select>
                    </v-col>
                </v-row>

                <v-row justify="center">
                    <v-col cols="10">
                        <v-textarea
                        name="description"
                        label="Description"
                        id="description"
                        v-model="reservation.description"
                        >   
                        </v-textarea>
                    </v-col>
                    
                </v-row>
                
                <v-row justify="center" class="mb-5">
                    <v-btn 
                    
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

const dt = new Date();

export default {
  data() {
    return {
        sitterInfo: [],
        date: dt.toISOString().substr(0, 10), 
        value: '',
        description: '',
        menu: false,
        modal: false,
        menu1: false,
        menu2: false,
        timeStep: '00:00',
        pets: ['Foo', 'Bar', 'Fizz', 'Buzz'],
        time: ['10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00'],
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
            paymentMethod: 'kakao',
            amount: 56555,
            petNo: 1,
            userName: 'sss',
            userAddress: 'zzzz',
            petName: 'tttt',
            dogBreed: 'mal',
            size: 'small'

        }

    }
  },
  created() {
      eventBus.$on('sitterObj', (sitterObj) => {
          console.log(sitterObj)
          this.sitterInfo = sitterObj
      }) 
  },
  computed: {
    formIsValid() {
      return this.title !== '' && this.location !== ''
      && this.imageUrl !=='' && this.description !== ''
    },
    minutesToZero(dt) {
        return (dt.getMinutes() < 10 ? '0 ' : '') + (dt.getMinutes() < 10 ? '0 ' : '')
    },
   

  },
  methods: {
    onNewReservation() {
    //   if (!this.formIsValid) {
    //     return
    //   }
    //   const meetupData = {
        
    //     date: this.date,
    //     startTime: this.startTime,
    //     endTime: this.endTime,
    //     description: this.description,   
    //   }
    //   this.$store.dispatch('createMeetup', meetupData)

        console.log(this.date)
        //넘길 객체에 펫 정보 추가
        this.reservation.date = this.date
        this.paymentVO.startTime = this.reservation.date + " " + this.reservation.startTime
        this.paymentVO.endTime = this.reservation.date + " " + this.reservation.endTime
        this.paymentVO.request = this.reservation.description
        this.paymentVO.sitterNo = this.sitterInfo.sitterNo,
        this.paymentVO.sittingType = this.sitterInfo.sittingType,
        this.paymentVO.sitterName = this.sitterInfo.sitterName,
        this.paymentVO.sitterPhone = this.sitterInfo.sitterPhone,
        this.paymentVO.sitterAddress = this.sitterInfo.sitterPhone,

        console.log(this.reservation)
        console.log(this.paymentVO)
        
         axios
            .post('http://localhost:1234/addPayment', this.paymentVO)
            .then(res => {
                console.log(res)
            })
            .catch(err => {
                console.log(err)
            })
        this.$router.push('/meetups') //예약확인 페이지로 수정 필요

    },

    allowedStep: m => m % 0 === 0

  }
}
</script>