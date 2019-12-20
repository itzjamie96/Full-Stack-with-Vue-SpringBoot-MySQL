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
                v-model="userName"
            label="이름"
          ></v-text-field>
        <v-text-field
                v-model="userAddress"
            label="주소"
          ></v-text-field>
        <v-text-field
                v-model="userPhone"
            label="핸드폰"
          ></v-text-field>
                <v-text-field
                v-model="userEmail"
                type="email"
            label="이메일"
          ></v-text-field>
                <v-text-field
                v-model="userPw"
                type="password"
            label="패스워드"
          ></v-text-field>
                <v-text-field
                v-model="userPwRepeat"
                type="password"
            label="패스워드 확인"
          ></v-text-field>
          <b>{{checkP}}</b>
          <v-btn
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
    <div class="text-xs-center pl-5 ml-5 pb-5">
    <v-btn outlined fab color="success" class="ml-5">naver</v-btn>
    <v-btn outlined fab color="warning" class="mx-12">kakao</v-btn>
    <v-btn outlined fab color="info" >google</v-btn>
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
import axios from "axios"

const baseURL = 'http://localhost:1234'
export default {
    data() {
        return {
            userAddress:null,
            userPhone:null,
            userName : null,
            userEmail :null,
            userPw : null,
            userPwRepeat : null,
            isSignUp : false,
            isSignUpError: false,
            message:"",
            
            
        }
    },
    computed: {
      checkP(){
          if(this.userPw !==null&&this.userPwRepeat !==null&&this.userPw===this.userPwRepeat)
            this.message="비밀번호가 같습니다."
        else if(this.userPw !== this.userPwRepeat)
            this.message="비밀번호가 다릅니다"
            else
            this.message=""
        return this.message
      }
        

    },
    methods:{
        
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
             this.isSignUp=true
             this.userName=''
             this.userEmail=''
             this.userPw=''
             this.userAddress=''
             this.userPhone=''
             this.userPwRepeat=''
         }else
            this.isSignUp=false
            this.isSignUpError=true
       }) 
       .catch(error => { 
         console.log(error)
       })
      }

    }
}
</script>