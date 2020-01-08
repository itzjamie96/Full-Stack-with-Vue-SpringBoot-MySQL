<template>
    <div>
        
        <v-card 
                color="green"
                height="100%"
                class="mb-5"
        >
        {{usersPets}}
            <v-form @submit.prevent="onNewReservation" >
                <v-row justify="center">
                    <v-col cols="12" sm="10">
                        <v-select 
                            v-model="usersPets"
                            v-bind:items="petList"
                            multiple
                            item-text="petName"
                            item-value="petList"
                            attach
                            chips
                            label="반려동물 선택"
                            id="usersPets"
                            return-object
                            :clearable="true"
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
                            <v-date-picker v-model="date" @input="menu1 = false" :min="date"></v-date-picker>
                        </v-menu>
                    </v-col>
                </v-row>

                <v-row justify="center">
                    <v-col cols="5">
                        <v-select
                            :items="checkInTime"
                            label="체크인 시간"
                            v-model="startTime"
                            id="starTime"
                        ></v-select>
                    </v-col>
                    <v-col cols="5">
                        <v-select
                            :items="selectableTime"
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
import { log } from 'util';

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
        petList: [],
        usersPets: [],
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

       
        
        
    }
  },
  created() {
      eventBus.$on('sitterObj', (sitterObj) => {
        //   console.log(sitterObj)
          this.sitterInfo = sitterObj
      }),
      this.initialize()
      
  },
  computed: {
    formIsValid() {
      return this.description !== '' && this.userPets !== null && this.startTime !== '' && this.endTime !== ''
    },
    minutesToZero(dt) {
        return (dt.getMinutes() < 10 ? '0 ' : '') + (dt.getMinutes() < 10 ? '0 ' : '')
    },

    ...mapState(["isLogin","userInfo"]),

    petNo() {
        return this.$route.params.petNo
    },
    selectableTime() {
        let times = []
        let a = 0
        for(let i = 0, length = this.time.length; i<length; i++){
            if(this.time[i]>this.startTime){   
                times[a] = this.time[i];
                a++;
            }
        }
        return times
    },
    checkInTime() {
        if(this.date=== dt.toISOString().substr(0, 10)) {
            let checkInTimes = []
            let nowHour = dt.getHours() + 1
            let a = 0
            for(let i = 0, length = this.time.length; i<length; i++) {
                if(this.time[i] > nowHour.toString()) {
                    checkInTimes[a] = this.time[i];
                    a++;
                }
            }
            return checkInTimes
        } else return this.time
    }
   
  },
  methods: {
      
      initialize() {
           const petNo = this.$route.params.petNo
           
           
           axios
            .get(`http://localhost:1234/showAllpets/${this.userInfo.userNo}`)
            .then(res => {
                this.petList = res.data
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })

       },
        onNewReservation() {

        const reserveData = {
            petDetailList: this.usersPets,  //List<PetInfo> 형태로 넘어갸아함.
            paymentDate: this.date,
            startTime: this.date + " "+ this.startTime,
            endTime: this.date + " "+ this.endTime,
            request: this.description,
            userNo: this.userInfo.userNo,
            userAddress: this.userInfo.userAddress,
            sitterNo: this.sitterInfo.sitterNo,
            sittingType: this.sitterInfo.sittingType,
            sitterName: this.sitterInfo.sitterName,
            sitterPhone: this.sitterInfo.sitterPhone,
            sitterAddress: this.sitterInfo.sitterAddress,
            amount: ''
            // petNo: this.petList.petNo,
            // petName: this.petList.petName,
            // size: this.petList.size,
            
            
        }
        console.log(reserveData)
        this.$store.dispatch('createReservation', reserveData)     //store에 createReservation에 payload로 보내기~
        this.$router.push(`/paymentinfoDay/${this.userInfo.userNo}`) //예약확인 페이지로 수정 필요
    },

    

    allowedStep: m => m % 0 === 0

  }
}
</script>