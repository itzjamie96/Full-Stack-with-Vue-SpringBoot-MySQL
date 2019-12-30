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
                            v-bind:items="petList"
                            multiple
                            item-text="petName"
                            item-value="petList"
                            attach
                            chips
                            label="반려동물 선택"
                            id="usersPets"
                            return-object
                        ></v-select>
                    </v-col>
                </v-row>

                <v-row class="mb-3" justify="center">
                    <v-col cols="5">
                        <v-menu
                            v-model="menu1"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            min-width="290px"
                        >
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                v-model="date1"
                                label="체크인 날짜를 선택하세요"
                                readonly
                                v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="date1" @input="menu1 = false"></v-date-picker>
                        </v-menu>
                    </v-col>
                    <v-col cols="5">
                        <v-menu
                            v-model="menu2"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            min-width="290px"
                        >
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                v-model="date2"
                                label="체크아웃 날짜를 선택하세요"
                                readonly
                                v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="date2" @input="menu2 = false"></v-date-picker>
                        </v-menu>
                    </v-col>
                </v-row>

                <v-row justify="center">
                    <v-col cols="5">
                        <v-select
                            :items="time"
                            label="체크인 시간"
                        ></v-select>
                    </v-col>
                    <v-col cols="5">
                        <v-select
                            :items="time"
                            label="체크아웃 시간"
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

const dt = new Date();

export default {
  data() {
    return {
        date: dt.toISOString().substr(0, 10), 
        date1: dt.toISOString().substr(0, 10), 
        date2: dt.toISOString().substr(0, 10), 
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
        ]
    }
  },
  computed: {
    formIsValid() {
      return this.title !== '' && this.location !== ''
      && this.imageUrl !=='' && this.description !== ''
    },
    minutesToZero(dt) {
        return (dt.getMinutes() < 10 ? '0 ' : '') + (dt.getMinutes() < 10 ? '0 ' : '')
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
            startDate: this.date1,
            endDate: this.date2,
            startTime: this.date1 + " "+ this.startTime,
            endTime: this.date2 + " "+ this.endTime,
            request: this.description,
            userNo: this.userInfo.userNo,
            userAddress: this.userInfo.userAddress,
            sitterNo: this.sitterInfo.sitterNo,
            sittingType: this.sitterInfo.sittingType,
            sitterName: this.sitterInfo.sitterName,
            sitterPhone: this.sitterInfo.sitterPhone,
            sitterAddress: this.sitterInfo.sitterAddress,
            // petNo: this.petList.petNo,
            // petName: this.petList.petName,
            // size: this.petList.size,
            
            
        }
        console.log(reserveData)
        this.$store.dispatch('createReservation', reserveData)     //store에 createReservation에 payload로 보내기~
        this.$router.push(`/paymentinfo/${this.userInfo.userNo}`) //예약확인 페이지로 수정 필요
    },

    allowedStep: m => m % 0 === 0

  }
}
</script>