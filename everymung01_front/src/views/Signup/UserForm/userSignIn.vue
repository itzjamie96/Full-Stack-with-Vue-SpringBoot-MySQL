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
                <v-text-field label="phone-number" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field label="address" required></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click.native="dialogId = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click.native="dialogId = false">Save</v-btn>
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
                <v-text-field label="Email" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field label="phone-number" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field label="address" required></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click.native="dialogPw = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click.native="dialogPw = false">Save</v-btn>
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
    <v-btn color="white" @click="id" depressed>아이디 찾기</v-btn>
    <v-divider vertical class="my-2"></v-divider>
    <v-btn color="white" @click="bm" depressed>비밀번호 찾기</v-btn>
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
import {mapState,mapActions} from "vuex"
import axios from "axios"
export default {
    data() {
        return {
          passwordRules: [
        v => !!v || 'E-mail is required'
      ],
          emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+/.test(v) || 'E-mail must be valid',
      ],
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
           this.dialogId=true
        },
        bm(){
            this.dialogPw=true
        },


    }
}
</script>