<template>
    <div>
       <form>
           <v-row justify="center">
               <v-col cols="3"> 
                  <v-text-field
                  v-model="userName"
                 dense
                 label="이름"
                >
                </v-text-field>
               </v-col>
           </v-row>
        
          <v-row justify="center">
              <v-col cols="3">
                <v-text-field
                 v-model="userEmail"
                 label="E-mail"
                ></v-text-field>
              </v-col>
          </v-row>

          <v-row justify="center">
              <v-col cols="3">
                  <v-text-field
            v-model="userPw"
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
                 v-model="userPhone"
                 label="휴대폰 번호"
                ></v-text-field>
              </v-col>
          </v-row>

           <v-row justify="center">
              <v-col cols="3">
                  <v-text-field
                 v-model="userAddress"
                 label=" 주소 "
                ></v-text-field>
              </v-col>
          </v-row>
         
         <v-row justify="center">
             <v-btn>수정완료</v-btn>
             <v-btn>회원탈퇴</v-btn>
         </v-row>
       

       
          
         

       </form>
    </div>
</template>





<script>
export default {
    
    data(){
        return{
            show1:false,
            userPw:'',
            rules: {
             required: value => !!value || 'Required.',
             min: v => v.length >= 8 || 'Min 8 characters',
             emailMatch: () => ('The email and password you entered don\'t match'),
        },



        }
    }
}
</script>


