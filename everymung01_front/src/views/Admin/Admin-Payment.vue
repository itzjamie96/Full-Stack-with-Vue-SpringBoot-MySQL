<template>
  <v-row >
     <v-col>
    <vue-good-table
    :columns="columns"
    :rows="rows"
    @on-row-click="onRowClick"
    max-height="500px"
    :line-numbers="true"
    :search-options="{
    enabled: true,
    }"
>  </vue-good-table>

<v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">상세정보</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="주문일"  v-model="payment.paymentDate" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문번호" v-model="payment.paymentNo" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문자명" v-model="payment.userName"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문방법" v-model="payment.paymentMethod" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문금액" v-model="payment.amount" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문상태" v-model="payment.paymentStatus" disabled></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click="paymentDetail(payment.paymentNo),dialog2 = true">결제내역 보기</v-btn>
        </v-card-actions>

        <v-dialog v-model="dialog2" fullscreen hide-overlay transition="dialog-bottom-transition">
          <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click="dialog2 = false">
            <i class="fas fa-times"></i>
          </v-btn>
         
          <v-toolbar-title>Settings</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <!-- <v-btn dark text @click.native="deleteAlert=true">거절</v-btn>
            <v-btn dark text @click.native="updateAlert=true">승인</v-btn> -->
          </v-toolbar-items>
        </v-toolbar>
 
        <v-row>
          <v-col cols="6">
            <template>
              <div>
                <v-card
                    class="mx-auto"
                    max-width="344"
                >
                  <v-card-text>
                    <p class="headline text--primary">예약 정보 확인</p>

            <v-row >
                <v-col>
                    <p class="text--primary">맡기실 반려동물</p>

                </v-col>
                <v-col>
                      {{paymentdetail.petName}}
                </v-col>
            </v-row>
            <v-row >
                <v-col>
                    <p class="text--primary">예약 날짜</p>
                </v-col>
                <v-col>
                    <p v-if="paymentdetail.sittingType === 'daycare'">{{paymentdetail.startTime | formatDate}}</p>
                    <p v-else>{{paymentdetail.startTime | formatDate}} ~ {{paymentdetail.endTime | formatDate}}</p>
                </v-col>
            </v-row>

            <v-row  v-if="paymentdetail.sittingType === 'daycare'">
                <v-col>
                    <p class="text--primary">예약 시간</p>
                </v-col>
                <v-col>
                    <p>{{paymentdetail.startTime | formatTime}} - {{paymentdetail.endTime | formatTime}}</p>
                </v-col>
            </v-row>

            <v-row  v-if="paymentdetail.sittingType === 'home'">
                <v-col>
                    <p class="text--primary">체크인 시간</p>
                </v-col>
                <v-col>
                    <p>{{paymentdetail.startTime| formatTime}}</p>
                </v-col>
            </v-row>

            <v-row  v-if="paymentdetail.sittingType === 'home'">
                <v-col>
                    <p class="text--primary">체크아웃 시간</p>
                </v-col>
                <v-col>
                    <p>{{paymentdetail.endTime| formatTime}}</p>
                </v-col>
            </v-row>

            <v-row >
                <v-col>
                    <p class="text--primary">방문 주소</p>

                </v-col>
                <v-col>
                    <p>{{paymentdetail.userAddress}}</p>
                </v-col>
            </v-row>

            <v-row >
                <v-col>
                    <p class="text--primary">펫시터</p>

                </v-col>
                <v-col>
                    <p>{{paymentdetail.sitterName}}</p>
                </v-col>
            </v-row>

            <v-row >
                <v-col>
                    <p class="text--primary">요청사항</p>

                </v-col>
                <v-col>
                    <p>{{this.paymentdetail.request}}</p>
                </v-col>
            </v-row>

            </v-card-text>
            
        </v-card>
      </div>
        </template>
        </v-col>

        <v-col cols="6">


         <template>
    <div>
        <v-card
            class="mx-auto"
            max-width="344"
        >
        
            <v-card-text>
            <p class="headline text--primary">결제 정보 확인</p>

            <v-row >
                <v-col>
                    <p class="text--primary">기본 금액</p>

                </v-col>
                <v-col>
                    <p>15,000 원</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>

            <v-row class="mt-3">
                <v-col>
                   <p>{{paymentdetail.dogBreed}}&nbsp;X </p>
                </v-col>
                <v-col>
                    <p>{{paymentdetail.size}} X {{paymentdetail.dayPrice}} 원</p>
                </v-col>
            </v-row>
            <v-row v-if="paymentdetail.sittingType === 'daycare'">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
                <v-col>
                    <p>{{paymentdetail.startTime | formatTime}} - {{paymentdetail.endTime | formatTime}} : 총 {{time}}시간</p>
                </v-col>
            </v-row>

            <v-row v-else>
                <v-col>
                    <p class="text--primary">예약 날짜</p>

                </v-col>
                <v-col>
                    <p>{{paymentdetail.startTime | formatDate}} - {{paymentdetail.endTime | formatDate}}</p>
                    {{days}} day
        
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row >
                <v-col>
                    <p class="text--primary">결제 금액</p>

                </v-col>
                <v-col>
                    <p>{{paymentdetail.amount}}</p>
                </v-col>
            </v-row>
            <v-row class="mt-3">
                <v-card-actions>
                </v-card-actions>
            </v-row>


            </v-card-text>
            
        </v-card>
    </div>
</template> 

        </v-col>
        </v-row>

          </v-card>
        </v-dialog>

      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'
import moment from 'moment'

export default {
    components: {
  VueGoodTable,
},data(){
    return {
       dialog: false,
       dialog2: false,
       payment:{
       paymentDate :'',
       paymentNo:'',
       userName:'',
       paymentMethod:'',
       amount:'',
       paymentStatus:'',
       },
       paymentdetail:{
        petName:'',
        startTime:'',
        endTime:'',
        userAddress:'',
        sitterName:'',
        sittingType:'',
        request:'',
        amount:'',
        size:'',
        dogBreed:'',
        dayPrice:'',
       },
      columns: [
        {
          label: '주문일',
          field: 'paymentDate',
        },
        {
          label: '주문번호',
          field: 'paymentNo',
        },
        {
          label: '주문자명',
          field: 'userName',
        },
        {
          label: '주문방법',
          field: 'paymentMethod',
        },
        {
          label: '주문금액',
          field: 'amount',
        },
        {
          label: '시터종류',
          field: 'sittingType',
        },
        {
          label: '주문상태',
          field: 'paymentStatus',
        },
        
      ],
      rows:[],
      time:'',
      day:'',
    };
  },
  created() {
    this.selectAll();
  },
  computed: {
       days() {
           let endYear = Number(this.paymentdetail.endTime.split(" ")[0].split("-")[0])
           let endMonth = Number(this.paymentdetail.endTime.split(" ")[0].split("-")[1])
           let endDay =  Number(this.paymentdetail.endTime.split(" ")[0].split("-")[2])
           let startYear = Number(this.paymentdetail.startTime.split(" ")[0].split("-")[0])
           let startMonth = Number(this.paymentdetail.startTime.split(" ")[0].split("-")[1])
           let startDay =  Number(this.paymentdetail.startTime.split(" ")[0].split("-")[2])
            let end = moment([endYear, endMonth, endDay])
            let start = moment([startYear, startMonth, startDay])     
            let difference = end.diff(start,'days')
           return difference
       },
  },
  methods: {
  onRowClick(params) {
     this.dialog=true
     this.payment.paymentDate = params.row.paymentDate
     this.payment.paymentNo = params.row.paymentNo
     this.payment.userName = params.row.userName
     this.payment.paymentMethod = params.row.paymentMethod
     this.payment.amount = params.row.amount
     this.payment.paymentStatus = params.row.paymentStatus
  },
  selectAll(){
      this.$http.get(`http://localhost:1234/showAllPayment`)
          .then( res =>{
            this.rows = res.data
          
          })
          .catch(err => {
            alert(err+"\n"+"Admin-Payment(selectAll) 에러")
          })
     
  },
  delet(userNo){
     this.dialog=false
     this.deleteAlert=false
     const No = userNo
     
      this.$http.post(`http://localhost:1234/deleteUser/${No}`).then(res =>{
        const idx = this.rows.findIndex(x => x.userNo === userNo)
              this.dialog=false
              this.rows.splice(idx, 1)
      }).catch(err =>{
        alert(err+"\n"+"Admin-Payment(delet) 에러")
      })

  },
  update(){
     this.dialog=false
     this.updateAlert=false
     this.$http.post('http://localhost:1234/updateUser',this.user) 
              .then(res => { 
                this.selectAll();
              }) 
              .catch(err => { 
                alert(err+"\n"+"Admin-Payment(update) 에러")

              });
  },
  paymentDetail(paymentNo){
    this.$http.get(`http://localhost:1234/showDetailPayment/${paymentNo}`)
              .then(res => { 
                this.paymentdetail = res.data
                this.time=this.paymentdetail.endTime.substr(11,2)-this.paymentdetail.startTime.substr(11,2)
              }) 
              .catch(err => { 
                alert(err+"\n"+"Admin-Payment(paymentDetail) 에러")

              });

  },
    },

 
}
</script>