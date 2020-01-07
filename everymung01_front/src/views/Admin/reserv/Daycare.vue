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
                <v-text-field label="Name"  v-model="home.userName" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Email" v-model="home.userEmail" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Password" v-model="home.userPw"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone" v-model="home.userPhone" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Address" v-model="home.userAddress" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Date" v-model="home.startTime" readonly></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-text v-if="deleteAlert">
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
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click.native="deleteAlert=true">삭제</v-btn>
          <v-btn color="blue darken-1" text @click.native="updateAlert=true">수정</v-btn>
        </v-card-actions>
        
        
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
       deleteAlert: false,
       updateAlert: false,
       home:{
       userName :'',
       userEmail:'',
       userPhone:'',
       userAddress:'',
       paymentNo:'',
       startTime:'',
       endTime:'',
       sitterName:'',
       sitterPhone:'',
       sittingType:'',
       },
      columns: [
        {
          label: 'payNo',
          field: 'paymentNo',
        },
        {
          label: 'User',
          field: 'userName',
          width: '80px',
        },
        {
          label: 'Email',
          field: 'userEmail',
        },
        {
          label: 'Phone',
          field: 'userPhone',
        },
        {
          label: 'Address',
          field: 'userAddress',
        },
        {
          label: '예약날짜',
          field: 'startTime',
        },
        {
          label: '예약시간',
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

    this.$http.get(`http://localhost:1234/test`)
        .then(res => {
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
     this.home.userName = params.row.userName
     this.home.userName = params.row.userName
     this.home.userEmail = params.row.userEmail
     this.home.userPw = params.row.userPw
     this.home.userPhone = params.row.userPhone
     this.home.userAddress = params.row.userAddress
     this.home.userProfile = params.row.userProfile
     this.home.startTime = params.row.startTime +"~"+params.row.endTime.split(" ")[1]
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
        alert(err+"\n"+"Daycare(delet) 에러")
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
                alert(err+"\n"+"Daycare(update) 에러")

              });
  },
    },

 
}
</script>