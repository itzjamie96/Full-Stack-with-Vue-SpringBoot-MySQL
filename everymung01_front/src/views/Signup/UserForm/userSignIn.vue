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
        >
          <v-btn @click="_roles('User')" value="User">
            일반
          </v-btn>

          <v-btn @click="_roles('Sitter')" value="Sitter">
            시터
          </v-btn>

          <v-btn @click="_roles('Admin')" value="Admin">
            운영자
          </v-btn>
        </v-btn-toggle>
                <v-alert
                :value="isLoginError"
                type="error"
                >
                아이디와 비번을 확인하세요
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
                <v-text-field v-model="searchPwVO.email" label="Email" required></v-text-field>
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
        
    <v-toolbar-title primary-title class="layout justify-center"><i class="fas fa-paw"></i><b>에브리멍</b></v-toolbar-title>
    </v-toolbar>
    
    <div class="pa-3">
                <v-text-field
                v-model="email"
                :rules="emailRules"
            label="이메일 입력"
            required
          ></v-text-field>
                <v-text-field
                v-model="password"
                type="password"
                :rules="passwordRules"
            label="패스워드 입력"
            required
          ></v-text-field>
          <v-btn
          depressed 
          large
          block
          color="primary"
          @click="login({email:email,
          password:password})"
          >로그인</v-btn>
          
    </div>

<v-toolbar flat v-if="role !=='Admin'">
    <v-toolbar-title primary-title class="layout justify-center">
        <v-divider class="mt-3"></v-divider>
        <pre>   간편하게 시작하기   </pre>
        <v-divider class="mt-3"></v-divider>
        </v-toolbar-title>
    </v-toolbar>
    <div class="text-xs-center pl-5 ml-5 pb-5" v-if="role !=='Admin'">
    <v-btn outlined fab color="success" class="ml-5">naver</v-btn>
    <v-btn outlined fab color="warning" class="mx-12">kakao</v-btn>
    <v-btn outlined fab color="info" >google</v-btn>
    </div>
           <v-divider class="mx-5"></v-divider>
<div class="layout justify-center" v-if="role !=='Admin'">
    <v-btn color="white" @click="dialogId=true" depressed>아이디 찾기</v-btn>
    <v-divider vertical class="my-2"></v-divider>
    <v-btn color="white" @click="dialogPw=true" depressed>비밀번호 찾기</v-btn>
    <v-divider vertical class="my-2"></v-divider>
    <v-btn color="white" @click="$router.push({name : 'signup'})" depressed>회원가입</v-btn>
</div>

          </v-card>
            </v-flex>
        </v-layout>
        
    </v-container>
    </v-app>
</template>

<script>
// 이걸 가져다 써야 로그인 연동이 된다 
import {mapState,mapActions} from "vuex"
import axios from "axios"
const baseURL = 'http://localhost:1234'
export default {
    data() {
        return {
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
            phone:''
          },
          email:'',
          password:'',
          dialogId:false,
          dialogPw:false,

        }
    },
    computed: {
        ...mapState(["isLogin","userInfo","isLoginError","role"]),
    
    },
    methods:{
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


    }
}
</script>