<template>
    <v-container fill-height style="max-width:450px;">
        <v-layout align-center row wrap>
            <v-flex xs12>
                <v-alert
                :value="isSignUpError"
                type="error"
                >
                이미 존재하는 이메일입니다.
                </v-alert>
                <v-alert
                class="mb-3"
                :value="isSignUp"
                type="success"
                >
                회원가입 완료
                </v-alert>
            <v-card>

    <v-toolbar flat>
    <v-toolbar-title primary-title class="layout justify-center"><i class="fas fa-paw"></i><b>에브리멍 회원가입</b></v-toolbar-title>
    </v-toolbar>
    <div class="pa-3">
        <v-text-field
        @keydown="OpenBtnName(userName)"
                v-model="userName"
                :rules="nameRules"
            label="이름"
            required
          ></v-text-field>
        
          <VueDaumPostcode 
          style="height: 200px; overflow: scroll;"
          @complete="userAddress = $event.roadAddress"
          />
          <v-text-field
          :disabled="userAddress === null"
             v-model="userAddress"
            label="상세주소(건물명,층)"
            required
          ></v-text-field>

        <v-text-field
        @keydown="OpenBtnPhone(userPhone)"
                v-model="userPhone"
                :rules="PhoneRules"
            label="핸드폰"
          ></v-text-field>
                <v-text-field
                @keydown="OpenBtnEmail(userEmail)"
                v-model="userEmail"
                :rules="emailRules"
            label="이메일"
            required
          ></v-text-field>
                <v-text-field
                v-model="userPw"
                :rules="PasswordRules"
                type="password"
            label="패스워드"
            required
          ></v-text-field>
                <v-text-field
                v-model="userPwRepeat"
                type="password"
            label="패스워드 확인"
          ></v-text-field>
          <b>{{checkP}}</b>
          <v-btn
          :disabled="userPw ===null || userPw !== userPwRepeat || Trigger.Name===false||Trigger.Phone===false||Trigger.Email===false||
          Trigger.password===false||userAddress===null"
          depressed 
          large
          block
          color="primary"
          @click="signup()"
          >회원가입</v-btn>
          
    </div>

<v-toolbar flat>
    <v-toolbar-title primary-title class="layout justify-center">
        <v-divider class="mt-3"></v-divider>
        <pre>   간편하게 시작하기   </pre>
        <v-divider class="mt-3"></v-divider>
        </v-toolbar-title>
    </v-toolbar>
    <div class="text-xs-center pl-5 ml-5 pb-5" v-if="role !=='Admin'">
    <KakaoLogin
      api-key="f64eb8bdf0d591a97100dd290c91ee26"
      :on-success=onSuccess
      :on-failure=onFailure
      />
    <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccessG" :onFailure="onFailure"></GoogleLogin>
    </div>
           <v-divider class="mx-5"></v-divider>
<div class="layout justify-center">
    <b class="mt-2">이미 계정이 있다면 ? </b>
    <v-btn color="white"  router :to="{name:'signin'}" depressed> 로그인 </v-btn>
    
</div>

          </v-card>
            </v-flex>
        </v-layout>
        
    </v-container>
</template>


<script>
import KakaoLogin from 'vue-kakao-login'
import GoogleLogin from 'vue-google-login'
import axios from "axios"
import {mapState,mapActions} from "vuex"
import router from '../../../router/index'
import { VueDaumPostcode } from "vue-daum-postcode"
const baseURL = 'http://localhost:1234'
export default {
  components:{
   VueDaumPostcode,KakaoLogin,GoogleLogin
  },
    data() {
        return {
           params: {
                    client_id: "850641622081-mcbv8nhhuue0gae6d23jsujgs0drijrn.apps.googleusercontent.com"
                },
                // only needed if you want to render the button with the google ui
                renderParams: {
                    width: 300,
                    height: 40,
                    longtitle: true
                },
          Trigger:{
            Name : false,
            Phone : false,
            Email : false,
            password : false,
          },
            userAddress:null,
            userPhone:'',
            userName : '',
            userEmail :'',
            userPw : null,
            userPwRepeat : null,
            isSignUp : false,
            isSignUpError: false,
            message:"",
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
            

        }
    },
    computed: {
      ...mapState(["isLogin","userInfo","isLoginError","role","sitterApp"]),
      checkP(){
          if(this.userPw !==null&&this.userPwRepeat !==null&&this.userPw===this.userPwRepeat)
            {this.message="비밀번호가 같습니다."
            this.Trigger.password =true}
        else if(this.userPw !== this.userPwRepeat)
            this.message="비밀번호가 다릅니다"
            else
            this.message=""
        return this.message
      },
      

    },
    methods:{
      onSuccessG(googleUser){
        let this_=this;
        let UsersVO={
                        userEmail:'',
                        userName:'구글 :',
                        userPw:"구글",
                        userPhone:"구글",
                        userAddress:"구글"
                    }
        UsersVO.userEmail=googleUser.w3.U3
        UsersVO.userName=googleUser.w3.ig

        axios.post(`${baseURL}/signup`,UsersVO).then(resp => {
            console.log(resp)
          this_.login({email:UsersVO.userEmail,password:UsersVO.userPw})
           })

      },
      onFailureG(googleUser){
        console.log(googleUser)
      },
      onSuccess(data){
        let this_=this;
        //this.login({email:UsersVO.userEmail,password:UsersVO.userPw})
        let UsersVO={
                        userEmail:'',
                        userName:'',
                        userPw:"카카오",
                        userPhone:"카카오",
                        userAddress:"카카오"
                    }
 Kakao.API.request({

       url: '/v1/user/me',

       success: function(res) {
         UsersVO.userEmail=res.kaccount_email
         UsersVO.userName="카카오:"+res.properties.nickname
         console.log(res); //<---- kakao.api.request 에서 불러온 결과값 json형태로 출력
            
          axios.post(`${baseURL}/signup`,UsersVO).then(resp => {
            console.log(resp)
          this_.login({email:UsersVO.userEmail,password:UsersVO.userPw})
          })
           }
         })
      },
      onFailure(data){
      console.log(data)
  console.log("failure")
    },
        ...mapActions(['login','_roles']),
        signup(){
            let UsersVO = {userName:'',userEmail:'',userPw:'',userAddress:'',userPhone:''}
            UsersVO.userName=this.userName
            UsersVO.userEmail=this.userEmail
            UsersVO.userPw=this.userPw
            UsersVO.userAddress=this.userAddress
            UsersVO.userPhone=this.userPhone
            console.log(UsersVO)
          axios.post(`${baseURL}/signup`,UsersVO) 
       .then(res => { 
         console.log(res.data)
         if(res.data == "회원가입 완료"){
             this.isSignUpError=false
             this.isSignUp=true
             this.userName=''
             this.userEmail=''
             this.userPw=''
             this.userAddress=''
             this.userPhone=''
             this.userPwRepeat=''
             router.push({name:'signin'})
         }else
            this.isSignUp=false
            this.isSignUpError=true
       }) 
       .catch(error => { 
         console.log(error)
       })
      },
      OpenBtnName(userName){
        let zz =String(userName).length
        zz<=9 ? this.Trigger.Name=true : this.Trigger.Name=false
      },
      OpenBtnPhone(userPhone){
        String(userPhone).length ===10 ? this.Trigger.Phone=true : this.Trigger.Phone=false
      },
      OpenBtnEmail(userEmail){
        let regex =userEmail.match(/.+@.+\..+/)
        regex !==null ? this.Trigger.Email=true : this.Trigger.Email=false
      }

    }
}
</script>