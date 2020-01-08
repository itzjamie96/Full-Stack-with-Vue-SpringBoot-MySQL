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
                <v-text-field label="Name"  v-model="sitter.sitterName" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Email" v-model="sitter.sitterEmail" ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Password" v-model="sitter.sitterPw"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone" v-model="sitter.sitterPhone" ></v-text-field>
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
              <v-col cols="12">
                <v-text-field label="Status" v-model="sitter.approvalStatus" ></v-text-field>
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
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click.native="deleteAlert=true">거절</v-btn>
          <v-btn color="blue darken-1"  text @click="dialog2 = true">지원서보기</v-btn>
          <v-dialog v-model="dialog2" fullscreen hide-overlay transition="dialog-bottom-transition">
          <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click="dialog2 = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
         
          <v-toolbar-title>Settings</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn dark text @click.native="deleteAlert=true">거절</v-btn>
            <v-btn dark text @click.native="updateAlert=true">승인</v-btn>
            
          </v-toolbar-items>
        </v-toolbar>
        <v-card-text v-if="deleteAlert">
            <v-alert v-model="deleteAlert" type="warning">
              <h4>정말 거절 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="delet(sitter.sitterNo)">확인</v-btn>
              <v-btn color="secondary" @click="deleteAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text>
          <v-card-text v-if="updateAlert">
            <v-alert v-model="updateAlert" type="warning">
              <h4>정말 승인 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="update(sitter.approvalStatus=true)">확인</v-btn>
              <v-btn color="secondary" @click="updateAlert=false">취소</v-btn>
            </v-alert>
          </v-card-text>
        <v-divider></v-divider>
          
          <v-spacer></v-spacer>
        <v-row justify="center">
        <v-col lg="8">
          <h4>*필수항목을 꼭 입력해주세요</h4><br>
          <h1>지원자 정보</h1><br>
            <v-radio-group v-model="sitter.sitterGender" row>
              성별 :
              <v-radio label="남자" :value="true"></v-radio>
              <v-radio label="여자" :value="false"></v-radio>
            </v-radio-group>
            
            <v-text-field
            v-model="sitter.sitterEmail"
            label="이메일"
            required
          ></v-text-field>
          
            <v-text-field
            v-model="sitter.sitterPw"
            label="사용할 패스워드"
            required
          ></v-text-field>
          
            <v-text-field
            v-model="sitter.sitterName"
            label="이름"
            required
          ></v-text-field>
          
            <v-text-field
            v-model="sitter.sitterAge"
            label="나이"
            required
          ></v-text-field>
          
            <v-text-field
            v-model="sitter.sitterPhone"
            label="핸드폰번호"
            required
          ></v-text-field>
          
            <v-text-field
            v-model="sitter.sitterAddress"
            label="거주지역"
            required
          ></v-text-field>
          <br>
          <br>
          <p>*활동가능한 요일을 모두 선택해주세요.(*돌봄 시간은 10시~21시 사이에 이뤄집니다)</p>
          <v-row justify="space-around" >
            <v-checkbox v-model="sitter.possibleDay[0]" class="mx-2" label="월요일" true-value=1 false-value=0></v-checkbox>
            <v-checkbox v-model="sitter.possibleDay[1]" class="mx-2" label="화요일" true-value=1 false-value=0></v-checkbox>
            <v-checkbox v-model="sitter.possibleDay[2]" class="mx-2" label="수요일" true-value=1 false-value=0></v-checkbox>
            <v-checkbox v-model="sitter.possibleDay[3]" class="mx-2" label="목요일" true-value=1 false-value=0></v-checkbox>
            <v-checkbox v-model="sitter.possibleDay[4]" class="mx-2" label="금요일" true-value=1 false-value=0></v-checkbox>
            <v-checkbox v-model="sitter.possibleDay[5]" class="mx-2" label="토요일" true-value=1 false-value=0></v-checkbox>
            <v-checkbox v-model="sitter.possibleDay[6]" class="mx-2" label="일요일" true-value=1 false-value=0></v-checkbox>
          </v-row>
          
          <v-textarea
          label="*반려견 관련 업종에서 활동한 경험이 있다면 소개해주세요."
          auto-grow
          outlined
          v-model="sitter.profile"
          rows="4"
          row-height="45"
        ></v-textarea>
        <br>
        <div>{{img1}}<br>{{img2}}</div>
        <v-card>
        <p>신원확인은 필수입니다.신분증 사본을 반드시 첨부해주세요</p>
        <p>보내주신 신분증과 저격증은 3개원 보관 후 폐기됩니다.</p>
        <v-row>
          <v-col>
        신분증
        <v-img :src="sitter.identityCheck"></v-img>
        </v-col>
          <v-col>
        자격증
        <v-img :src="sitter.qualificationCheck"></v-img>
          </v-col>
        </v-row>
        </v-card><br>
          <br>
            </v-col>
            </v-row>  
            </v-card>
          </v-dialog>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-col>
  </v-row>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'
import { eventBus } from '../../main'
import { mapState,mapActions } from 'vuex'

export default {
    components: {
  VueGoodTable,
},data(){
    return {
       dialog: false,
       dialog2: false,
       img1:null,
       img2:null,
       deleteAlert: false,
       updateAlert: false,
       sitter:{
          possibleDay:'',
          sitterNo :'',
          sittingType:'',
          sitterName:'',
          sitterEmail:'',
          sitterPw:'',
          sitterPhone:'',
          sitterGender:'',
          sitterAge:'',
          profile:'',
          sitterAddress:'',
          approvalDate:'',
          approvalStatus:'',
          identityCheck:'',
          qualificationCheck:'',
       
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
          label: '시타종류',
          field: 'sittingType',
        },
        {
          label: '가입상태',
          field: 'approvalStatus',
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
     this.sitter.sitterNo = params.row.sitterNo
     this.sitter.sittingType = params.row.sittingType
     this.sitter.sitterName = params.row.sitterName
     this.sitter.sitterEmail = params.row.sitterEmail
     this.sitter.sitterPw = params.row.sitterPw
     this.sitter.sitterPhone = params.row.sitterPhone
     this.sitter.sitterAddress = params.row.sitterAddress
     this.sitter.approvalDate = params.row.approvalDate
     this.sitter.approvalStatus = params.row.approvalStatus
     this.sitter.sitterAge = params.row.sitterAge
     this.sitter.sitterGender = params.row.sitterGender
     this.sitter.identityCheck ='http://localhost:1234/download/'+ params.row.identityCheck
     this.sitter.qualificationCheck = 'http://localhost:1234/download/'+ params.row.qualificationCheck
     this.sitter.possibleDay = params.row.possibleDay.split(',')
     this.sitter.profile = params.row.profile
  },
  selectAll(){
      this.$http.get(`http://localhost:1234/falseAllSitters`)
            .then( res =>{
              this.rows = res.data
              
            })
            .catch(err => {
              alert(err+"\n"+"Admin-Approval(selectAll) 에러")
            })
     
  },
  delet(sitterNo){
    let cnt = this.count
    this.dialog=false
    this.deleteAlert=false
    const No = sitterNo
     
      this.$http.post(`http://localhost:1234/deleteSitter/${sitterNo}`)
            .then(res =>{
              const idx = this.rows.findIndex(x => x.sitterNo === sitterNo)
              this.dialog=false
              this.rows.splice(idx, 1)
              --cnt
              this.approval(String(cnt))
      }).catch(err =>{
        alert(err+"\n"+"Admin-Approval(delet) 에러")
      })

  },
  update(){
     let cnt = this.count
     this.dialog=false
     this.updateAlert=false
     this.sitter.possibleDay=String(this.sitter.possibleDay)
     
      this.$http.post('http://localhost:1234/updateSitter',this.sitter) 
            .then(res => { 
              --cnt
              this.selectAll();
              this.approval(String(cnt))
              
            }) 
            .catch(err => { 
              alert(err+"\n"+"Admin-Approval(update) 에러")

            });
  },
  ...mapActions(['approval'])
    },
  computed:{
    ...mapState(['count']),
    
    approvalCnt(){
      return this.count
    }
  }
 
}
</script>