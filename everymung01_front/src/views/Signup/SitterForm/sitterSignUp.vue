<template>
  <v-container class="text-center">

      <v-row class="justify-center pb-3 pt-3" id="signTitle">
        <h1  class="text-center">펫시터 지원서</h1>
      </v-row>
      
      <v-card class="pt-1 pb-4">
        <v-row class="justify-center mt-3" id="vidTitle">
          <v-col cols="12" md="6">
            <v-card outlined="">
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>
                    펫시터의 하루를 지금 영상으로 확인하세요! <i class="far fa-hand-point-down"></i>
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
              <LazyYoutubeVideo :url="url" :previewImageSize="previewImageSize"/>
            </v-card>
          </v-col>
        </v-row>
      </v-card>
      <br>
      <v-card>
        <v-row class="justify-center mt-3" id="signTitle">
          <h1  class="text-center">지원 전 확인 사항</h1>
        </v-row>
        <v-row class="justify-center mt-3">
          <p class="text-left" v-html="data0"></p>
        </v-row>
      <v-row class="justify-center mt-3">
          <v-card class="px-4" max-width="500" outlined>
            
            <v-radio-group v-model="SitterVO.sittingNo" row>
              <v-radio label="하루 시터" value="1"></v-radio>
              <v-radio label="위탁 시터" value="2"></v-radio>
            </v-radio-group>
          </v-card>
      </v-row>        
      <v-row class="justify-right">
        <div class="ml-12">
          <v-switch
            v-model="Agree"
            :label="`${AgreeM}`"
          ></v-switch>

        </div>
      </v-row>
      </v-card>
      <!-- <v-simple-table>
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
    <v-row class="justify-center mt-3">
      <v-card >
          <h1>최종 동의서</h1><br><br><br>
          <b>{{data1}}</b><br>
          <b>{{data2}}</b><br>
          <b>{{data3}}</b><br>
          <b>{{data4}}</b>
        </v-card><br><br> -->

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
      <v-dialog v-model="dialog" transition="dialog-bottom-transition">
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

          <!-- <VueDaumPostcode 
          style="height: 200px; overflow: scroll;"
          @complete="SitterVO.sitterAddress = $event.roadAddress"
          />
 -->
    
            <label class="ml-12">
      시/구/동 입력
      <GmapAutocomplete 
      @place_changed="setPlace">
      </GmapAutocomplete>
      <button @click="usePlace">Add</button>
    </label>
    <br/>
    <GmapMap style="width: 800px; height: 400px;" :zoom="14" :center="{lat:SitterVO.lat,lng:SitterVO.lng}">
      <gmap-circle ref="circle" :radius="1000" :center='{lat:SitterVO.lat,lng:SitterVO.lng}' :draggable='true' :editable='true' >
                            </gmap-circle>
    </GmapMap>
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
import {mapState} from "vuex"
const baseURL = 'http://localhost:1234'
export default {
  components: {
    LazyYoutubeVideo, VueDaumPostcode
  },
  
  data(){
    return{
/////////////////////////////////////////////////////구글맵
      place: null,
      lsm:{lat:37.496361445796694,lng: 127.05750504049766},
/////////////////////////////////////////////////////구글맵
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
        lat:37.496361445796694,
        lng: 127.05750504049766,
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
      data0 : 
      ' 1. 지원 자격 요건<br> - 현재 서울시 거주<br> - 만 26세 이상 (96년도 이전 출생자)<br> - 학력, 성별 무관<br> - 반려견을 반려해본 경험 1년 이상<br><br>2. 우대사항',
      
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
    setPlace(place) {
      this.place=place
      
    },
    usePlace(place) {
      this.SitterVO.sitterAddress = this.place.formatted_address
      if (this.place) {
        this.SitterVO.lat=this.place.geometry.location.lat()
        this.SitterVO.lng=this.place.geometry.location.lng()
        this.place = null;
      }
    },
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
         {this.msg=res.data}
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
  $router.push({name:'sitterSignUpFinish'})
}).catch(error => { 
         console.log(error)
       })  
  
}).catch(error => { 
         console.log(error)
       })

          
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
    ...mapState(["isLogin","userInfo","isLoginError","role","sitterApp"]),
    AgreeM(){
      let Ag=''
      if(this.Agree === true)
        Ag="위 사항 확인 완료"
      else
        Ag="위 사항을 모두 확인했습니다"
      return Ag
    },
    ...mapState(["isLogin","userInfo","isLoginError","role","sitterApp"])

  
  },
  created() {
        if(this.isLogin===true && this.role==='Sitter'){
          alert("이미 지원하셨습니다")
          this.$router.push('/')
        }
        
      },
}
</script>

<style>
@font-face { 
  font-family: 'S-CoreDream-8Heavy'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-8Heavy.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; 
}
@font-face { 
  font-family: 'HangeulNuri-Bold'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_three@1.0/HangeulNuri-Bold.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; }

#signTitle{
    font-size: 17pt;
    font-family: 'S-CoreDream-8Heavy'; 

}
.thead{
    font-size: 17pt;
    font-family: 'S-CoreDream-8Heavy'; 

}
#vidTitle{
    font-size: 17pt;
    font-family: 'HangeulNuri-Bold';
    

}


</style>