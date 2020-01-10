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
          <span>예약자</span>
            <v-row>
              <v-col cols="12">
                <v-text-field label="No"  v-model="day.paymentNo" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Name"  v-model="day.userName" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Email" v-model="day.userEmail" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone" v-model="day.userPhone" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="상세주소(건물명,층)" v-model="day.userAddress" ></v-text-field>
              </v-col>
             <!--  <v-col cols="12">
                <VueDaumPostcode
                  style="overflow: scroll; height: 300px"
                  @complete="day.userAddress = $event.roadAddress"
                />
              </v-col>
              <v-col cols="12">
                <v-text-field
                  :disabled="day.userAddress === null"
                  v-model="day.userAddress"
                  label="상세주소(건물명,층)"
                  required
                  color="black"
                >
                </v-text-field>
              </v-col> -->
              <span class="ml-3">시터</span>
              <v-col cols="12">
                <v-text-field label="SitterName" v-model="day.sitterName" readonly></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone" v-model="day.sitterPhone" readonly></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Email" v-model="day.sitterEmail" readonly></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Type" v-model="day.sittingType" readonly></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <!-- <v-card-text v-if="deleteAlert">
            <v-alert v-model="deleteAlert" type="warning">
              <h4>정말 삭제 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="delet(user.userNo)">확인</v-btn>
              <v-btn color="secondary" @click="deleteAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text>
        <v-card-text v-if="updateAlert">
            <v-alert v-model="updateAlert" type="warning">
              <h4>정말 수정 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="update()">확인</v-btn>
              <v-btn color="secondary" @click="updateAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text> -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <!-- <v-btn color="blue darken-1" text @click.native="deleteAlert=true">삭제</v-btn>
          <v-btn color="blue darken-1" text @click.native="updateAlert=true">수정</v-btn> -->
        </v-card-actions>
        
        
      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'
/* import { VueDaumPostcode } from "vue-daum-postcode" */

export default {
    components: {
  VueGoodTable,
  /* VueDaumPostcode, */
},data(){
    return {
       dialog: false,
       deleteAlert: false,
       updateAlert: false,
       day:{
       userName :'',
       userEmail:'',
       userPhone:'',
       userAddress:'',
       paymentNo:'',
       startTime:'',
       endTime:'',
       sitterName:'',
       sitterEmail:'',
       sitterPhone:'',
       sittingType:'',
       },
      columns: [
        /* {
          label: 'payNo',
          field: 'paymentNo',
        }, */
        {
          label: '이름',
          field: 'userName',
          width: '80px',
        },
        {
          label: '이메일',
          field: 'userEmail',
        },
        {
          label: '휴대번호',
          field: 'userPhone',
        },
        {
          label: '주소',
          field: 'userAddress',
        },
        {
          label: '체크인',
          field: 'startTime',
        },
        {
          label: '체크아웃',
          field: 'endTime',
        },
        {
          label: '시터',
          field: 'sitterName',
          width: '80px',
        },
        {
          label: '시터번호',
          field: 'sitterPhone',
        },
        {
          label: '타입',
          field: 'sittingType',
        },
        
      ],
      rows:[],
    };
  },
  created() {

    this.$http.get(`http://localhost:1234/countPay`)
        .then(res => {
                console.log(res.data)
                for(let sitterT in res.data){
                  if(res.data[sitterT].sittingType==="daycare")
                    this.rows.push(res.data[sitterT])
                  }
                })
                .catch(err => { 
                alert(err+"\n"+"Daycare(created) 에러")
              });
              

  },
  methods: {
  onRowClick(params) {
     this.dialog=true
     this.day.paymentNo = params.row.paymentNo
     this.day.userName = params.row.userName
     this.day.userEmail = params.row.userEmail
     this.day.userPw = params.row.userPw
     this.day.userPhone = params.row.userPhone
     this.day.userAddress = params.row.userAddress
     this.day.userProfile = params.row.userProfile
     this.day.startTime = params.row.startTime +"~"+params.row.endTime.split(" ")[1]
     this.day.sitterName = params.row.sitterName
     this.day.sitterPhone = params.row.sitterPhone
     this.day.sitterEmail = params.row.sitterEmail
     this.day.sittingType = params.row.sittingType

  },
  
    },

 
}
</script>