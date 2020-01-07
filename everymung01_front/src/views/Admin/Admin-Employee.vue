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
                <v-text-field label="Name"  v-model="sitter.sitterName" :rules="nameRules"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Email" v-model="sitter.sitterEmail" :rules="emailRules"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Password" v-model="sitter.sitterPw"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone" v-model="sitter.sitterPhone" :rules="PhoneRules" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Address" v-model="sitter.sitterAddress" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Type" v-model="sitter.sittingType" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Date" v-model="sitter.approvalDate" disabled></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-text v-if="deleteAlert">
            <v-alert v-model="deleteAlert" type="warning">
              <h4>정말 삭제 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="delet(sitter.sitterNo)">확인</v-btn>
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
       sitter:{
       sitterNo :'',
       sitterAge:'',
       sittingType:'',
       sitterName:'',
       sitterEmail:'',
       sitterPw:'',
       sitterPhone:'',
       sitterAddress:'',
       approvalDate:'',
       approvalStatus:true,
       },
      columns: [
        {
          label: '이름',
          field: 'sitterName',
        },
        {
          label: '나이',
          field: 'sitterAge',
        },
        {
          label: '이메일',
          field: 'sitterEmail',
        },
        {
          label: '휴대번호',
          field: 'sitterPhone',
        },
        {
          label: '주소',
          field: 'sitterAddress',
        },
        {
          label: '날짜',
          field: 'approvalDate',
        },
        {
          label: '시터종류',
          field: 'sittingType',
        },
        
      ],
      rows:[],
      emailRules: [
                  v => !!v || 'E-mail is required',
                  v => /.+@.+\..+/.test(v) || '이메일 형식에 맞지 않습니다.',
                  ],
      PhoneRules: [
                  v => !!v || 'Phone is required',
                  v=>/^(?:(010\d{4})|(01[1|6|7|8|9]\d{3,4}))(\d{4})$/.test(v) || '-빼주세요',
                  ],
      nameRules: [
                  v => !!v || 'Name is required',
                  v => (v && v.length <= 10) || '이름은 10글자 이하',
                  ],
      PasswordRules: [
                  v => !!v || 'password is required',
                  v => (v && v.length >= 6) || '비밀번호는 6자리 이상으로 해주세요',
                  ],
    };
  },
  created() {
    this.selectAll();
  },
  methods: {
  onRowClick(params) {
     this.dialog=true
     this.sitter.sitterNo = params.row.sitterNo
     this.sitter.sittingType = params.row.sittingType
     this.sitter.sitterName = params.row.sitterName
     this.sitter.sitterAge = params.row.sitterAge
     this.sitter.sitterEmail = params.row.sitterEmail
     this.sitter.sitterPw = params.row.sitterPw
     this.sitter.sitterPhone = params.row.sitterPhone
     this.sitter.sitterAddress = params.row.sitterAddress
     this.sitter.approvalDate = params.row.approvalDate
  },
  selectAll(){
      this.$http.get(`http://localhost:1234/showAllSitters`)
          .then( res =>{
            this.rows = res.data
            console.log(this.rows)
          })
          .catch(err => {
            alert(err+"\n"+"Admin-Employee(selectAll) 에러")
          })
     
  },
  delet(sitterNo){
     this.dialog=false
     this.deleteAlert=false
     const No = sitterNo
     
      this.$http.post(`http://localhost:1234/deleteSitter/${No}`).then(res =>{
        const idx = this.rows.findIndex(x => x.sitterNo === sitterNo)
        console.log(idx)
              this.dialog=false
              this.rows.splice(idx, 1)
      }).catch(err =>{
        alert(err+"\n"+"Admin-Employee(delet) 에러")
      })

  },
  update(){
     this.dialog=false
     this.updateAlert=false
     this.$http.post('http://localhost:1234/updateSitter',this.sitter) 
              .then(res => { 
                this.selectAll();
              }) 
              .catch(err => { 
                alert(err+"\n"+"Admin-Employee(update) 에러")

              });
  },
    },

 
}
</script>