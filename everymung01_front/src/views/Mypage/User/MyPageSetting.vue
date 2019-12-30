<template>

    <v-container fluid="" >
        <v-row class="fill-height">
            <v-col cols="3">
            <side-bar></side-bar>
            </v-col>
            
                <v-col>
                    <v-card>
                        <form>
                            <v-row justify="center">
                                <v-col cols="3"> 
                                 <v-text-field
                                 label="이름"
                                :value="userInfo.userName"
                                @input="userInfoReading"
                                 >
                                 </v-text-field>
                                </v-col>
                            </v-row>
        
                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    :value="userInfo.userEmail"
                                    label="E-mail"
                                    @input="userInfoReading"
                                ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    :value="userInfo.userPw"
                                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :rules="[rules.required, rules.min]"
                                    :type="show1 ? 'text' : 'password'"
                                    label="패스워드"
                                    hint="최소 8자리 이상 "
                                    counter
                                    @click:append="show1 = !show1"
                                    ></v-text-field>
                                    <v-btn>비밀번호 변경하기 </v-btn>
                                </v-col>
                            </v-row>
          
                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    :value="userInfo.userPhone"
                                    label="휴대폰 번호"
                                    ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    :value="userInfo.userAddress"
                                    label=" 주소 "
                                    ></v-text-field>
                                </v-col>
                            </v-row>
         
                            <v-row justify="center">
                                <v-btn>수정완료</v-btn>
                                <v-btn>회원탈퇴</v-btn>
                            </v-row>
                        </form>
                    </v-card>
                </v-col> 
            </v-row>
    </v-container>
</template>


<script>
import NavBar from '@/components/userNavigation.vue'
import { mapState }  from 'vuex'
import { userInfo } from 'os'




export default {
    
    data(){
        return{
            userName:'',    
            show1:false,
            userPw:'',
            rules: {
             required: value => !!value || 'Required.',
             min: v => v.length >= 8 || 'Min 8 characters',
             emailMatch: () => ('The email and password you entered don\'t match'),
        },
        }
    },
    computed:{
        ...mapState(['userInfo'])
    },
   
    components:{
        'side-bar':NavBar
    },
    methods:{
        userInfoReading(e){
           this.$store.commit('userInfoReading', e.target.value)
           console.log(e)
        }
    }
    
}
</script>


