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
                <v-text-field label="Name"  v-model="user.userName" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Email" v-model="user.userEmail" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Password" v-model="user.userPw"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone" v-model="user.userPhone" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Address" v-model="user.userAddress" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Date" v-model="user.userDate" disabled></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-text v-if="deleteAlert">
            <v-alert v-model="deleteAlert" type="warning">
              <h4>정말 삭제 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="dele(user.userNo)">확인</v-btn>
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
       user:{
       userNo :'',
       userEmail:'',
       userName:'',
       userPw:'',
       userPhone:'',
       userAddress:'',
       userProfile:'',
       userDate:''
       },
      columns: [
        {
          label: 'Name',
          field: 'userName',
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
          label: 'Date',
          field: 'userDate',
        },
        
      ],
      rows:[],
    };
  },
  created() {
    this.selectAll();
  },
  methods: {
  onRowClick(params) {
     this.dialog=true
     this.user.userNo = params.row.userNo
     this.user.userName = params.row.userName
     this.user.userEmail = params.row.userEmail
     this.user.userPw = params.row.userPw
     this.user.userPhone = params.row.userPhone
     this.user.userAddress = params.row.userAddress
     this.user.userProfile = params.row.userProfile
     this.user.userDate = params.row.userDate
  },
  selectAll(){
      this.$axios.get(`http://localhost:1234/userlist`)
          .then( res =>{
            this.rows = res.data
          })
          .catch(err => {
            alert("backand(showAllUsers) 에러 확인")
          })
     
  },
  dele(userNo){
     this.dialog=false
     this.deleteAlert=false
     const No = userNo
     
      this.$axios.post(`http://localhost:1234/deleteUser/${No}`).then(res =>{
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
     this.$axios.post('http://localhost:1234/updateUser',this.user) 
              .then(res => { 
                this.selectAll();
              }) 
              .catch(err => { 
                alert("backend(update) 에러 확인!")

              });
  },
    },

 
}
</script>