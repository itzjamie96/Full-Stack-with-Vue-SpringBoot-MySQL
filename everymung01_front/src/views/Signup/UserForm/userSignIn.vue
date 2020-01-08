<template>
<v-app>
    <v-container fill-height style="max-width:450px;">
        <v-layout align-center row wrap>
            <v-flex xs12>
              <v-btn-toggle
                v-model="$store.state.role"
                tile
                color="deep-purple accent-3"
                group
                id="togg"
              >
                <v-btn @click="_roles('User')" value="User" id="reg">
                  일반
                </v-btn>
                <v-divider vertical inset></v-divider>
                <v-btn @click="_roles('Sitter')" value="Sitter" id="sit">
                  시터
                </v-btn>
                <v-divider vertical inset></v-divider>
                <v-btn @click="_roles('Admin')" value="Admin" id="ad">
                  운영자
                </v-btn>
              </v-btn-toggle>
                <v-alert
                :value="sitterApp"
                type="error"
                >
                승인 대기 상태입니다
                </v-alert>
                <v-alert
                :value="isLoginError"
                type="error"
                >
                이메일과 비밀번호를 확인하세요
                </v-alert>
                <v-alert
                class="mb-3"
                :value="isLogin"
                type="success"
                >
                로그인 완료
                </v-alert>
            <v-card>
<!-- id 찾기-->
<v-layout row justify-center>
    <v-dialog v-model="dialogId" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">아이디 찾기</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field v-model="searchIdVO.name" label="이름" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field v-model="searchIdVO.phone" label="phone -빼고 입력해주세요" required></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
        <h3>찾으시는 아이디 : {{searchIdRes}}</h3>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click.native="dialogId = false,searchIdRes=''">뒤로</v-btn>
          <v-btn color="blue darken-1" text @click="id">찾기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
<!--비번 찾기 -->
<v-layout row justify-center>
    <v-dialog v-model="dialogPw" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">비밀번호 찾기</span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
             
              <v-flex xs12>
                <v-text-field v-model="searchPwVO.email"
                type="english"
                 label="Email" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field v-model="searchPwVO.phone" label="phone -빼고 입력해주세요" required></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <h3>비밀번호 : {{searchPwRes}}</h3>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click.native="dialogPw = false,searchPwRes=''">뒤로</v-btn>
          <v-btn color="blue darken-1" text @click="bm">찾기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>



<!-- -->
 <v-toolbar flat>
  <v-toolbar-title primary-title class="layout justify-center">
    <div>
      <div class="d-inline">
        <i class="fas fa-paw fa-1x"></i>
    </div>
    <div class="d-inline" id="everymung">에브리멍</div>
    </div>                  
  </v-toolbar-title>
</v-toolbar>

<v-row class="justify-center">
  <v-col cols="8">
    <v-text-field
      v-model="email"
      :rules="emailRules"
      label="이메일 입력"
      required
      color="black"
    ></v-text-field>
  </v-col>
</v-row>
<v-row class="justify-center">
  <v-col cols="8">
    <v-text-field
      v-model="password"
      type="password"
      :rules="passwordRules"
      label="패스워드 입력"
      required
      color="black"
    ></v-text-field>
<!-- :disabled="email.match(/.+@.+\..+/)===null || String(password).length<6" -->
  </v-col>
</v-row>

<v-row class="justify-center">
  <v-col cols="8">
          <v-btn 
          large
          block
          @click="login({email:email,
          password:password})"
          >로그인</v-btn>

  </v-col>
</v-row>

            <v-toolbar flat v-if="role !=='Admin'">
                <v-toolbar-title primary-title class="layout justify-center">
                    <v-divider class="mt-3"></v-divider>
                    <pre id="easy">   간편하게 시작하기   </pre>
                    <v-divider class="mt-3"></v-divider>
                    </v-toolbar-title>
            </v-toolbar>
            <v-row class="justify-center">
              <div v-if="role !=='Admin'">
                <v-row class="mb-3">
                  <KakaoLogin
                    api-key="f64eb8bdf0d591a97100dd290c91ee26"
                    :on-success=onSuccess
                    :on-failure=onFailure
                    />

                </v-row>
                <v-row class="mb-3">
                   <GoogleLogin :params="params" :renderParams="renderParams" :onSuccess="onSuccessG" :onFailure="onFailure"></GoogleLogin>  
                </v-row>
              </div>
            </v-row>
           <v-divider class="mx-5"></v-divider>
<div class="layout justify-center" v-if="role !=='Admin'" id="test">
    <v-btn @click="dialogId=true" depressed id="id" text>아이디 찾기</v-btn>
    <v-divider vertical class="my-2"></v-divider>
    <v-btn text @click="dialogPw=true" depressed id="pw">비밀번호 찾기</v-btn>
    <v-divider vertical class="my-2"></v-divider>
    <v-btn text @click="$router.push({name : 'signup'})" depressed id="signup">회원가입</v-btn>
</div>

          </v-card>
            </v-flex>
        </v-layout>
        
    </v-container>
    </v-app>
</template>

<script>
// 이걸 가져다 써야 로그인 연동이 된다 
import KakaoLogin from 'vue-kakao-login'
import GoogleLogin from 'vue-google-login'
import {mapState,mapActions} from "vuex"
import axios from "axios"
const baseURL = 'http://localhost:1234'
export default {
  components: {
    KakaoLogin,GoogleLogin
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
          passwordRules: [
        v => !!v || 'password is required',
        v => (v && v.length >= 6) || '비밀번호는 6자리 이상입니다',
      ],
          emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+/.test(v) || 'E-mail must be valid',
      ],
          searchPwRes:''
          ,
          searchPwVO:{
            email:'',
            phone:''
          }
          ,
          searchIdRes:''
          ,
          searchIdVO:{
            name:'',
        
        
          },
          email:'',
          password:'',
          dialogId:false,
          dialogPw:false,

        }
    },
    computed: {
        ...mapState(["isLogin","userInfo","isLoginError","role","sitterApp"]),
    
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
        ...mapActions(['login','_roles']),
        id(){
           axios.post(`${baseURL}/searchId`+this.role,this.searchIdVO) 
       .then(res => { 
         this.searchIdRes=res.data
         this.searchIdVO.name=''
         this.searchIdVO.phone=''
       }) 
       .catch(error => { 
         console.log(error)
       })
        },
        bm(){
          console.log(this.role)
            axios.post(`${baseURL}/searchPw`+this.role,this.searchPwVO) 
       .then(res => { 
         this.searchPwRes=res.data
         this.searchPwVO.email=''
         this.searchPwVO.phone=''
       }) 
       .catch(error => { 
         console.log(error)
       })
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
         
            
          axios.post(`${baseURL}/signup`,UsersVO).then(resp => {
          
          this_.login({email:UsersVO.userEmail,password:UsersVO.userPw})
           
          })
             
           }
         })


       
           
         


      },
    onFailure(data){
      
  console.log("failure")
    }
      
    }
}
</script>

<style>

@import url('https://fonts.googleapis.com/css?family=Gugi|Jua|Single+Day&display=swap');
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');

#reg, #sit, #ad{
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: bold;
}

#everymung {
    text-decoration: none;
    font-size: 18pt;
    font-family: 'Gugi', cursive;
}
#easy{
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: bold;
}

</style>
