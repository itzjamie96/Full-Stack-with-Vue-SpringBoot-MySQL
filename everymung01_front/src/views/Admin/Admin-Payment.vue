<template>
  <v-row justify="center">
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
                <v-text-field label="주문일"  v-model="paymenet.paymentDate" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문번호" v-model="paymenet.paymentNo" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문자명" v-model="paymenet.userName"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문방법" v-model="paymenet.paymentMethod" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문금액" v-model="paymenet.amount" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="주문상태" v-model="paymenet.paymentStatus" disabled></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click="dialog2 = true">결제내역 보기</v-btn>
        </v-card-actions>

        <v-dialog v-model="dialog2" fullscreen hide-overlay transition="dialog-bottom-transition">
          <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click="dialog2 = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
         
          <v-toolbar-title>Settings</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <!-- <v-btn dark text @click="dialog2 = false">Save</v-btn> -->
            <v-btn dark text @click.native="deleteAlert=true">거절</v-btn>
            <v-btn dark text @click.native="updateAlert=true">승인</v-btn>
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
                123
               123
            <p class="headline text--primary">예약 정보 확인</p>

            <v-row justify="center">
                <v-col>
                    <p class="text--primary">맡기실 반려동물</p>

                </v-col>
                <v-col>
                      {{paymentdetail.petName}}
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 날짜</p>

                </v-col>
                <v-col>
                    <p>123</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
                <v-col>
                    <p>123</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">방문 주소</p>

                </v-col>
                <v-col>
                    <p>123</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">펫시터</p>

                </v-col>
                <v-col>
                    <p>123</p>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">요청사항</p>

                </v-col>
                <v-col>
                    <p>123</p>
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

            <v-row justify="center">
                <v-col>
                    <p class="text--primary">기본 금액</p>

                </v-col>
                <v-col>
                    <p>30,000 원</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row justify="center" class="mt-3">
                <v-col>
                    <!-- <p>{{paymentObj.size}}&nbsp;X </p> -->

                </v-col>
                <v-col>
                    <!-- <p>{{paymentObj.dogBreed}}</p> -->
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">예약 시간</p>

                </v-col>
                <v-col>
                    <p>456</p>
                </v-col>
            </v-row>
            <v-divider></v-divider>
            <v-row justify="center">
                <v-col>
                    <p class="text--primary">결제 금액</p>

                </v-col>
                <v-col>
                    <p>가격</p>
                </v-col>
            </v-row>
            <v-row justify="center" class="mt-3">
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

export default {
    components: {
  VueGoodTable,
},data(){
    return {
       dialog: false,
       dialog2: false,
       paymenet:{
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
        userAddress:'',
        sitterName:'',
        sittingType:'',
        request:'',
        amount:'',
        size:'',
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
          label: '주문상태',
          field: 'paymentStatus',
        },
        
      ],
      rows:[],
    };
  },
  created() {
    this.selectAll();
    this.payment();
  },
  methods: {
  onRowClick(params) {
     this.dialog=true
     this.paymenet.paymentDate = params.row.paymentDate
     this.paymenet.paymentNo = params.row.paymentNo
     this.paymenet.userName = params.row.userName
     this.paymenet.paymentMethod = params.row.paymentMethod
     this.paymenet.amount = params.row.amount
     this.paymenet.paymentStatus = params.row.paymentStatus
  },
  selectAll(){
      this.$http.get(`http://localhost:1234/showAllPayment`)
          .then( res =>{
            console.log("seletAll:"+res.data)
            this.rows = res.data
          
          })
          .catch(err => {
            alert("backand(showAllPayment) 에러 확인")
          })
     
  },
  dele(userNo){
     this.dialog=false
     this.deleteAlert=false
     const No = userNo
     
      this.$http.post(`http://localhost:1234/deleteUser/${No}`).then(res =>{
        const idx = this.rows.findIndex(x => x.userNo === userNo)
        console.log(idx)
              this.dialog=false
              this.rows.splice(idx, 1)
      }).catch(err =>{
        alert("backend(delete) 에러 확인!")
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
                alert("backend(update) 에러 확인!")

              });
  },
  payment(){
    this.$http.get('http://localhost:1234/showAllDetailPayment')
              .then(res => { 
                this.rows=res.data
                console.log(this.rows)
              }) 
              .catch(err => { 
                alert("backend(update) 에러 확인!")

              });

  },
    },

 
}
</script>