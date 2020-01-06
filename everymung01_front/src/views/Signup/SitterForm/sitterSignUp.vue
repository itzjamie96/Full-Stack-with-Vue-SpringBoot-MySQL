<template>
    <v-container class="text-center">
      <div></div>
      <h1 class="text-center">펫시터 지원서</h1>
      <v-card class="ml-12 pl-4" max-width="250" outlined>
    <v-radio-group v-model="SitterVO.sittingNo" row>
      <v-radio label="하루 시터" value="1"></v-radio>
      <v-radio label="위탁 시터" value="2"></v-radio>
    </v-radio-group>
    </v-card><br><br>
    <v-card max-width="800" outlined>
      <b >펫시터가 하는일? 영상으로 확인하기</b>
      <LazyYoutubeVideo :url="url" :previewImageSize="previewImageSize"/>
    </v-card>
    <br><br>
     <v-simple-table>
      <thead>
        <tr>
          <th class="text-center display-1 indigo lighten-5" >모집요강</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td v-html="data0"></td>
        </tr>
      </tbody>
  </v-simple-table>
  <br><br>
  <v-card outlined>
      <h1>최종 동의서</h1><br><br><br>
      <b>{{data1}}</b><br>
      <b>{{data2}}</b><br>
      <b>{{data3}}</b><br>
      <b>{{data4}}</b>
      <v-switch
      v-model="Agree"
      :label="`${AgreeM}`"
    ></v-switch>
    </v-card><br><br>
    <v-row>
      <v-col>
    <v-card max-width="150" disabled outlined>1/3 페이지</v-card>
    <v-progress-linear value="30"></v-progress-linear>
    <v-btn 
    :disabled="!Agree" 
    @click="dialog=true" 
    class="mb-12 mt-5" 
    large 
    outlined>다음</v-btn>
    </v-col></v-row>
<!-- dialog start -->
     <v-row justify="center">
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <v-card>
        <v-toolbar dark color="primary">
          <v-btn dark @click="dialog = false">
            뒤로
          </v-btn>
          <v-spacer></v-spacer>
          <v-toolbar-title>펫시터 지원서</v-toolbar-title>
          <v-spacer></v-spacer>
          
        </v-toolbar>
        <v-row justify="center">
        <v-col lg="8">
          <h4>*필수항목을 꼭 입력해주세요</h4><br>
       <h1>지원자 정보</h1><br>
          성별 : 
          <v-radio-group v-model="SitterVO.sitterGender" row>
      <v-radio label="남자" :value="true"></v-radio>
      <v-radio label="여자" :value="false"></v-radio>
    </v-radio-group>
        
            <v-text-field
            @keydown="OpenBtnEmail(SitterVO.sitterEmail)"
            v-model="SitterVO.sitterEmail"
            label="이메일"
            :rules="emailRules"
            required
          ></v-text-field>
          
            <v-text-field
            @keydown="OpenBtnPassword(SitterVO.sitterPw)"
            v-model="SitterVO.sitterPw"
            label="사용할 패스워드"
            type="password"
            :rules="PasswordRules"
            required
          ></v-text-field>
          
            <v-text-field
            @keydown="OpenBtnName(SitterVO.sitterName)"
            v-model="SitterVO.sitterName"
            :rules="nameRules"
            label="이름"
            required
          ></v-text-field>
          
            <v-text-field
            v-model="SitterVO.sitterAge"
            label="나이"
            type="number"
            required
          ></v-text-field>
          
            <v-text-field
            @keydown="OpenBtnPhone(SitterVO.sitterPhone)"
            v-model="SitterVO.sitterPhone"
            :rules="PhoneRules"
            label="핸드폰번호"
            required
          ></v-text-field>

          <VueDaumPostcode 
          style="height: 200px; overflow: scroll;"
          @complete="SitterVO.sitterAddress = $event.roadAddress"
          />

            <v-text-field
            :disabled="SitterVO.sitterAddress === null"
            v-model="SitterVO.sitterAddress"
            label="상세주소(건물명,층)"
            required
          ></v-text-field>
          <br>
          <v-card max-width="150" disabled outlined>2/3 페이지</v-card>
          <v-progress-linear value="60"></v-progress-linear>
          <br>
          <p>*활동가능한 요일을 모두 선택해주세요.(*돌봄 시간은 10시~21시 사이에 이뤄집니다)</p>
          <v-row justify="space-around">
            <v-checkbox v-model="checkWorking.mon" class="mx-2" label="월요일"></v-checkbox>
            <v-checkbox v-model="checkWorking.tue" class="mx-2" label="화요일"></v-checkbox>
            <v-checkbox v-model="checkWorking.wed" class="mx-2" label="수요일"></v-checkbox>
            <v-checkbox v-model="checkWorking.thu" class="mx-2" label="목요일"></v-checkbox>
            <v-checkbox v-model="checkWorking.fri" class="mx-2" label="금요일"></v-checkbox>
            <v-checkbox v-model="checkWorking.sat" class="mx-2" label="토요일"></v-checkbox>
            <v-checkbox v-model="checkWorking.sun" class="mx-2" label="일요일"></v-checkbox>
          </v-row>
          
          <v-textarea
          label="*반려견 관련 업종에서 활동한 경험이 있다면 소개해주세요."
          auto-grow
          outlined
          v-model="SitterVO.profile"
          rows="4"
          row-height="45"
        ></v-textarea>
        <br>
        <v-card>
        <p>신원확인은 필수입니다.신분증 사본을 반드시 첨부해주세요</p>
        <p>보내주신 신분증과 저격증은 3개원 보관 후 폐기됩니다.</p>
        <v-file-input v-model="img1" label="신분증 제출"></v-file-input>
        <v-file-input v-model="img2" label="자격증 제출"></v-file-input>
        </v-card><br>
        <v-card max-width="150" disabled outlined>3/3 페이지</v-card>
        <v-progress-linear value="100"></v-progress-linear>
          <br>
        <v-row justify="center">
          <h1>{{msg}}</h1>
        <v-btn 
        :disabled="Trigger.Name===false||Trigger.Phone===false||Trigger.Email===false||Trigger.password===false
        ||SitterVO.sitterAge===null||SitterVO.sitterAddress===null||img1===null||img2===null"
        @click="submit($router)"
        depressed 
        large
        >
         제출</v-btn>
        </v-row>
  </v-col>
  </v-row>
      </v-card>
  
    </v-dialog>
  </v-row>

<!-- dialog end -->
  </v-container>
</template>
<script>
import LazyYoutubeVideo from "vue-lazy-youtube-video";
import axios from 'axios'
import * as FormData from 'form-data'
import { VueDaumPostcode } from "vue-daum-postcode"
const baseURL = 'http://localhost:1234'
export default {
  components: {
    LazyYoutubeVideo, VueDaumPostcode
  },
  
  data(){
    return{
      Trigger:{
        Name:false,
        Phone:false,
        Email : false,
        password : false,
      },
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
      SitterVO:{
        sittingNo:"1",
        sitterGender:true,
        sitterEmail:'',
        sitterPw:'',
        sitterName:'',
        sitterAge:'',
        sitterPhone:'',
        sitterAddress:null,
        possibleDay:'',
      profile:'',
      },
      //자기소개
      //
      //체크박스(근무요일)
      checkWorking:{mon:false,tue:false,wed:false,thu:false,fri:false,sat:false,sun:false},
      //시터 종류
      //
      //동영상
       url: "https://www.youtube.com/watch?v=ihE8Wpa_Jis",
          previewImageSize: "maxresdefault",
      //
      //모집요강
      data0 : '93년 이전 출생자(만 26세 이상) / 학력,성별 무관(여성 우대)/<br> 자차 이동 가능자 우대/반려동물 반려경험 1년 이상 / 서울 ,경기 인천 거주자',
      //
      //최종동의서
      Agree:false,
      data1 : '1. 동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세 무궁화 삼천리 화려 강산 대한 사람 대한으로 길이 보전하세',

      data2:'2. 남산 위에 저 소나무 철갑을 두른 듯 바람 서리 불변함은 우리 기상일세 무궁화 삼천리 화려 강산 대한 사람 대한으로 길이 보전하세',

      data3:'3. 가을 하늘 공활한데 높고 구름 없이 밝은 달은 우리 가슴 일편단심일세 무궁화 삼천리 화려 강산 대한 사람 대한으로 길이 보전하세',

      data4:'4. 이 기상과 이 맘으로 충성을 다하여 괴로우나 즐거우나 나라 사랑하세 무궁화 삼천리 화려 강산 대한 사람 대한으로 길이 보전하세',

      //
        
        img1:null,
        img2:null,
        dialog: false,
        msg:'',
    }
  },
  methods:{
    submit($router){
      let trigger=null
       let sitterEmail = this.SitterVO.sitterEmail
      let formData = new FormData();
      let formData1 = new FormData();
      formData.append('file', this.img1);
      formData1.append('file', this.img2);
       let possibleD=''


       for(let ch in this.checkWorking){

         if(this.checkWorking[ch]===true)
          possibleD+="1,"
          else
          possibleD+="0,"         
                                        }
      this.SitterVO.possibleDay = possibleD


       axios.post(`${baseURL}/signupSitter`,this.SitterVO) 
       .then(res => { 
         if(res.data == "이미 존재")
         {this.msg=res.data
         console.log(res.data)
         
         }
         else{
axios.post( `${baseURL}/upload-file/identityCheck/`+sitterEmail,
  formData,
  {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
  }
).then(res => {
  axios.post( `${baseURL}/upload-file/qualificationCheck/`+sitterEmail,
  formData1,
  {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
  }
).then(res => {
  console.log(res.data)
  $router.push({name:'signin'})
}).catch(error => { 
         console.log(error)
       })  
  
}).catch(error => { 
         console.log(error)
       })

           console.log(res.data)
         }
       }) 
       .catch(error => { 
         console.log(error)
       })    
    },
    OpenBtnName(SitterName){
        let zz =String(SitterName).length
        zz<=9 ? this.Trigger.Name=true : this.Trigger.Name=false
      },
    OpenBtnPhone(SitterPhone){
        String(SitterPhone).length ===10 ? this.Trigger.Phone=true : this.Trigger.Phone=false
      },
      OpenBtnEmail(SitterEmail){
        let regex =SitterEmail.match(/.+@.+\..+/)
        regex !==null ? this.Trigger.Email=true : this.Trigger.Email=false
      },
      OpenBtnPassword(SitterPassword){
        String(SitterPassword).length>=5 ? this.Trigger.password=true : this.Trigger.password=false
      },
  }
  ,
  computed:{
    AgreeM(){
      let Ag=''
      if(this.Agree === true)
        Ag="동의"
      else
        Ag="동의하기"
      return Ag
    },
    

  
  }
}
</script>
