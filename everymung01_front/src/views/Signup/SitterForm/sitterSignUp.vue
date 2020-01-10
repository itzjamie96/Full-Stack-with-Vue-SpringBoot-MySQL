<template>
  <v-container>
    <v-row class="justify-center">
      <v-col cols="10">
        <v-card class="">
          <p  class="text-center pt-3" id="signTitle">펫시터 지원서</p>
          <v-divider class="mx-12"></v-divider>

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
          <v-divider class="mx-12 mt-12"></v-divider>
          <v-row class="justify-center mt-2 pt-3" id="signTitle">
            <p  class="text-center" id="caution">지원 전 확인 사항</p>
          </v-row>
          <v-divider class="mx-12 mb-6"></v-divider>

          <v-row class="justify-center mt-3">
            <!-- <p class="text-left" v-html="data0"></p> -->
            <pre>
              1. 지원 자격 요건

              - 현재 서울/경기/인천/부산/대전/세종/대구 에 거주하시는 분들로 모집하고 있습니다. 
                (그 외 지역도 확장예정중입니다. 다른 지역으로 지원하시는 경우, 추후 모집시에 연락드립니다.)
              - 반려동물을 반려해본 경험 보유 (강아지 또는 고양이)
              - 책임 의식을 가지고 열심히 하실 분 (근무 가능일수가 한달 20일 이상)
              - 28세 이상 (93년 출생자 이상)


              2. 우대사항

              - 반려견/반려묘 둘 다 반려해본 경험
              - 반려동물 관련 자격증 소지자, 관련학과 전공
              - 애견카페, 유치원 등 반려동물 관련 업무를 한 경력
              - 위탁 또는 방문 펫시터 유경험자 (개인/타업체)
              - 반려동물 10년이상 키워본 경험


              3. 펫시터 활동 절차 * 5단계 절차 이후 펫시팅 활동 가능.
                
              1) 1차 (서류심사/전화인터뷰) : 서류심사 합격 여부는 합격자에 한해서 개별 연락드리며, 
                  전화로 지원서 내용에 대한 간단한 인터뷰를 진행하게 됩니다.
              2) 2차 (인적성검사) : 전화 인터뷰 이후에 온라인으로 인적성검사를 진행합니다.
              3) 3차 (대면면접) : 2차까지 진행된 지원자에 한 해 면접이 진행됩니다.
              4) 4차 계약서/서약서 작성 : 본인인증 및 서약서를 통해 펫시터 인증 단계가 이루어 집니다. 
              5) 5차 오프라인교육 : 평일 5일동안 오프라인 교육이 이루어 집니다. (이론+실습)
              6) 펫시터 활동 시작 : 교육 수료가 완료되면 펫시터 등록절차를 거쳐 펫시팅 활동을 시작합니다. 
            </pre>
          </v-row>

          <v-divider class="mx-12 mt-12"></v-divider>
          <v-row class="justify-center mt-2 pt-3" id="signTitle">
            <p  class="text-center" id="caution">지원할 펫시터 유형</p>
          </v-row>
          <v-divider class="mx-12 mb-6"></v-divider>

          <v-row class="justify-center mt-3">
            <pre>
              1) 하루 시터
              - 직접 고객의 집에 방문하여 방물 돌봄과 산책을 진행하는 펫시터

              2) 위탁 시터
              - 펫시터의 집에서 장기간 (최소 1박 2일) 돌봄을 진행하는 펫시터
            </pre>
          </v-row>



        <v-row class="justify-center mt-3">
            <v-card class="px-4 mb-4">
              
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
        <v-row class="justify-center">
          <v-col cols="11">
            <v-card max-width="150" disabled outlined >1/2 페이지</v-card>
            <v-progress-linear value="50"></v-progress-linear>
            <v-btn 
            :disabled="!Agree" 
            @click="dialog=true" 
            class="mb-12 mt-5" 
            large 
            outlined>다음</v-btn>
            </v-col></v-row>
              </v-card>
            </v-col>
          </v-row>


  <!-- dialog start -->

      <v-row class="justify-center">
      <v-dialog v-model="dialog" transition="dialog-bottom-transition">
        <v-card>
          <v-toolbar dark color="deep-purple accent-1">
            <v-btn light="" @click="dialog = false">
              뒤로
            </v-btn>
            <v-spacer></v-spacer>
            <v-toolbar-title>펫시터 지원서</v-toolbar-title>
            <v-spacer></v-spacer>
            
          </v-toolbar>
          <v-row class="justify-center">
          <v-col cols="8">
            <v-row class="justify-center">
            <v-col cols="6">
              <h4>*필수항목을 꼭 입력해주세요</h4><br>
              <h1>지원자 정보</h1><br>
                성별 : 
                <v-radio-group v-model="SitterVO.sitterGender" row>
                  <v-radio label="남자" :value="true"></v-radio>
                  <v-radio label="여자" :value="false"></v-radio>
                </v-radio-group>

            </v-col>

            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">
                <v-text-field
                @keydown="OpenBtnEmail(SitterVO.sitterEmail)"
                v-model="SitterVO.sitterEmail"
                label="이메일"
                :rules="emailRules"
                required
              ></v-text-field>

              </v-col>

            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">
                              <v-text-field
              @keydown="OpenBtnPassword(SitterVO.sitterPw)"
              v-model="SitterVO.sitterPw"
              label="사용할 패스워드"
              type="password"
              :rules="PasswordRules"
              required
            ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">
              <v-text-field
              @keydown="OpenBtnName(SitterVO.sitterName)"
              v-model="SitterVO.sitterName"
              :rules="nameRules"
              label="이름"
              required
            ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">
              <v-text-field
              v-model="SitterVO.sitterAge"
              label="나이 (23세 이하 지원 불가)"
              type="number"
              required
            ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">
              <v-text-field
              @keydown="OpenBtnPhone(SitterVO.sitterPhone)"
              v-model="SitterVO.sitterPhone"
              :rules="PhoneRules"
              label="휴대폰 번호"
              required
            ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">

              <label class="">
                주소 입력 (예시: 서울시 **구 **동)
                <br/>

              <GmapAutocomplete 
                @place_changed="setPlace"
                id="gmapAdd"
                class="mr-4 mt-3">
              </GmapAutocomplete>

              <button @click="usePlace" id="search-btn">선택</button>
            </label>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="7">

            <GmapMap class="ml-10" style="width: 550px; height: 400px;" :zoom="13" :center="{lat:SitterVO.lat,lng:SitterVO.lng}">
              <gmap-circle ref="circle" 
                :radius="1000" 
                :center='{lat:SitterVO.lat,lng:SitterVO.lng}' 
                :draggable='true' 
                :editable='true' >
              </gmap-circle>
            </GmapMap>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="6">
            <v-text-field
            :disabled="SitterVO.sitterAddress === null"
            v-model="SitterVO.sitterAddress"
            label="상세주소(건물명,층)"
            required
          ></v-text-field>
              </v-col>
            </v-row>
            <v-row class="justify-center">
              <v-col cols="7">
            <v-divider class="mt-12"></v-divider>
                      <v-row class="justify-center mt-2 pt-3" id="signTitle">
                        <p  class="text-center" id="caution">신원 확인</p>
                      </v-row>
                      <v-divider class="mb-6"></v-divider>

              </v-col>
            </v-row>
            <v-row class="justify-center mt-6">
              <v-col cols="6">
                <p>신원확인은 필수입니다.신분증 사본을 반드시 첨부해주세요</p>
                <p>보내주신 신분증과 저격증은 3개원 보관 후 폐기됩니다.</p>
                <v-file-input v-model="img1" label="신분증 제출"></v-file-input>
                <v-file-input v-model="img2" label="자격증 제출"></v-file-input>
              </v-col>
            </v-row>

            <v-row class="justify-center mt-6">
              <v-col cols="6">
          <v-card max-width="150" disabled outlined>2/2 페이지</v-card>
          <v-progress-linear value="100"></v-progress-linear>
              </v-col>
            </v-row>

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
        lng:127.05750504049766,
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
#signTitle{
    font-size: 25pt;
    font-family: 'HangeulNuri-Bold'; 

}
.thead{
    font-size: 17pt;
    font-family: 'HangeulNuri-Bold'; 

}
#vidTitle{
    font-size: 17pt;
    font-family: 'HangeulNuri-Bold';
    

}
#caution{
    font-size: 18pt;
    font-family: 'HangeulNuri-Bold'; 
}
pre{
  font-size: 12pt;
  font-family: 'ON-IGothic'; 
}

#gmapAdd{
  width: 400px;
  height: 30px;
  background-color: rgba(170, 172, 154, 0.1);
  box-sizing: border-box;

}

#search-btn{
  background-color: rgba(170, 172, 154, 0.3);
  padding: 2px 10px 2px 8px;
}

</style>