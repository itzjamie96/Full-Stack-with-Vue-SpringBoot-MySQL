<template>

    <v-container fluid="" >
        <v-row class="fill-height">
            <v-col cols="3">
            <side-bar></side-bar>
            <div v-if="false">
            </div>
            </v-col>
                <v-col>
                    <v-card
                    >
                        <form>
                            <div>
                                
                                 <!-- 맨처음 보일  일반유저 프로필 사진 추가   -->
                                <v-row justify="center">
                                    <v-col cols="3">
                                         <div class="image-preview"  v-if="trig" >
                                            <v-img :src="lsm"
                                            height="100" width="100" class="preview"  >
                                            </v-img>
                                        </div>
                                 <!-- 일반유저 프로필 사진 수정  -->     
                                        <div v-else >
                                            <v-img
                                            height="100" width="100"
                                            :src="previewUserImg"
                                            class="image-preview"
                                            >
                                            </v-img>
                                        </div>
                                        <!-- 이미지 미리 보기 -->
                                        <div class="file-upload-form" >
                                            <input type="file"
                                            @change="userImgPreview"
                                            accept="image/*"
                                            >
                                        </div>
                                    </v-col>
                                </v-row>
                            </div>
                            <!--  유저 이름 -->
                            <v-row justify="center">
                                <v-col cols="3"> 
                                <!-- :value="userInfo.userName"
                                 @input="userInfoReading" -->
                                 <v-text-field
                                 label="이름"
                                 v-model="userInfo.userName"
                                readonly
                                 >
                                 </v-text-field>
                                 <!-- <input v-model="userVo.userName" 
                                 readonly> -->

                                </v-col>
                            </v-row>
        
                            <v-row justify="center">
                                <v-col cols="3">
                                     <!-- :value="userInfo.userEmail" -->
                                     <!-- @input="userInfoReading" -->
                                    <v-text-field
                                    v-model="userInfo.userEmail"
                                    label="E-mail"
                                    readonly
                                ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row justify="center">
                                <v-col cols="3">
                                    <!-- :value="userInfo.userPw" -->
                                    <v-text-field
                                    :disabled="userInfo.userPw==='구글'||userInfo.userPw==='카카오'"
                                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :rules="[rules.required, rules.min]"
                                    :type="show1 ? 'text' : 'password'"
                                    label="패스워드"
                                    hint="최소 6자리 이상 "
                                    counter
                                    @click:append="show1 = !show1"
                                    v-model="userInfo.userPw"
                                    ></v-text-field>
                                    
                                </v-col>
                            </v-row>
          
                            <v-row justify="center">
                                <v-col cols="3">
                                      <!-- :value="userInfo.userPhone" -->
                                    <v-text-field
                                    v-model="userInfo.userPhone"
                                    label="휴대폰 번호"
                                    ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row justify="center">
                                <v-col cols="3">
                                   <!-- :value="userInfo.userAddress" -->
                                    <!-- v-model="userVo.userAddress" -->
                                    <v-text-field
                                     v-model="userInfo.userAddress"
                                    label=" 주소 "
                                    
                                    ></v-text-field>
                                </v-col>
                            </v-row>
         
                            <v-row justify="center">
                                <v-btn text color="primary" dark
                                @click="updateUserInfo"
                                >수정완료</v-btn>
                                <v-btn text color="primary"
                                @click.stop="checkDelete= true"
                                >회원탈퇴</v-btn>
                            </v-row>
                            <v-row>
                                <v-dialog v-model="checkDelete" max-width="290">
                                    <v-card>
                                        <v-card-title>
                                            정말로 회원을 탈퇴 하시 겠습니까? 
                                        </v-card-title>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn color="green darken-1" text @click="checkDelete=false" > 아니오 </v-btn>
                                            <v-btn color="green darken-1" text @click="deleteUser"> 예 </v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-dialog>
                            </v-row>
                        </form>
                    </v-card>
                </v-col> 
            </v-row>
    </v-container>
</template>


<script>
import { mapState , mapMutations}  from 'vuex'
import axios from 'axios'
import NavBar from '@/components/userNavigation.vue'
import { read } from 'fs'




export default {
    components:{
        'side-bar':NavBar
    },
    data() {
        return {
            trig:true,
            checkDelete:false,
            previewUserImg:'',       //유저 이미지 미리보기 변수 
            updateUserImg:'',            // 변경할 이미지를 담는 변수  
            userImg:'',                 // 디비에 보낼 유저 이미지 변수 새로운 추가 
            imgData:'',                // 변경하고자 하는 이미지 미리보기 변수 
            uploadUserImg:'',
            show1:false,
            userPw:'',
            rules: {
             required: value => !!value || 'Required.',
             min: v => (v && v.length >= 6) || 'Min 8 characters',
             emailMatch: () => ('The email and password you entered don\'t match'),
            },
            userVo:{
                userName:'',
                userEmail:'',
                userPw:'',
                userPhone:'',
                userNo:'',
                userAddress:'',
                userProfile:'',
                userDate:''
                    },
    }
    },
    computed:{
        ...mapState(['userInfo']),
        lsm(){
            let lsm = 'http://localhost:1234/download/'+this.userInfo.userProfile
            return lsm
        }
        },
        
    created() {
        
        
    },
    methods:{
        ...mapMutations(['logout']),
       init(){
            
            this.userVo.userName = this.userInfo.userName//
            this.userVo.userEmail = this.userInfo.userEmail//
            this.userVo.userPw = this.userInfo.userPw//
            this.userVo.userPhone = this.userInfo.userPhone//
            this.userVo.userNo = this.userInfo.userNo
            this.userVo.userAddress = this.userInfo.userAddress//
            this.userVo.userProfile ='http://localhost:1234/download/'+this.userInfo.userProfile
            this.userVo.userDate = this.userInfo.userDate    
           
        },
        userInfoReading(e){
           this.$store.commit('userInfoReading', e.target.value)
           console.log('유저정보')
           console.log(e)
        },
        updateUserInfo(){
            this.init()
            if(this.trig){  // 유저 이미지 새로 추가 할 경우 
                let formData = new FormData()
                formData.append('file',this.userImg)
                 axios.post('http://localhost:1234/updateUser', this.userVo)
                      .then(response =>{
                        console.log('update 성공')
                        console.log(response.data)

                        axios.post('http://localhost:1234/upload-userImg/'+this.userVo.userEmail+'/'
                                    +this.userVo.userNo, formData,{
                                        headers:{
                                            'Content-Type' : 'multipart/form-data'
                                        }
                                    })
                                    .then(response =>{
                                        console.log('새로운 이미지 추가 성공')
                                     })
                                   .catch(error =>{
                                        console.log('새로운 이미지 추가 실패 ')
                                     })
                       })
                      .catch(error =>{
                                 console.log('update 에러 ')
                                 console.log(error)
                       })
                       
                       if(this.userImg != null){
                           this.userImg = ''
                       }

            }else {
                //기존 유저 이미지 삭제 
                let formData = new FormData()
                formData.append('file', this.userImg)

                axios.post('http://localhost:1234/deleteUserImg/'+this.userVo.userNo)
                     .then(response =>{
                         console.log('기존 이미지 삭제 성공')

                         axios.post('http://localhost:1234/upload-userImg/'+this.userVo.userEmail+'/'
                         +this.userVo.userNo, formData,{
                             headers:{
                                 'Content-Type':'multipart/form-data'
                             }
                            })
                            .then(response =>{
                                this.userInfo.userProfile=String(response.data.name)
                                    
                                axios.post('http://localhost:1234/updateUser',this.userVo)
                                .then(response =>{
                                    console.log('유저 이미지 성공 후 나머지 수정 성공')
                                })
                                .catch(error =>{
                                    console.log('유저 이미지 성공 후 나머지 수정 실패')
                                })
                            })
                            .catch(error =>{
                                console.log('유저이미지 수정 실패 ')
                            })
                     })
                     .catch(error =>{
                         console.log('기존 이미지 삭제 실패')

                     })
            }
            // window.location.href='http://localhost:8080/uMyPage/uAccount'

            
        },
        deleteUser(){
            this.init()
            axios.post('http://localhost:1234/deleteUser/'+this.userVo.userNo)
            .then(response =>{
                console.log('delete 성공')
                console.log(response)

            })
            .catch(error =>{
                console.log('delete 에러 ')
                console.log(error)
            })
            this.checkDelete = false
            this.logout()
            this.$router.push('/')


        },
         userImgPreview(event){
             this.init()
            //console.log(event)
            //계정관리에서 이미지 insert 
                this.userImg = event.target.files[0]
                var input = event.target;
                if(input.files && input.files[0]){
                    var reader = new FileReader();
                 reader.onload = (e) =>{
                     this.previewUserImg = e.target.result;
                 }
                 reader.readAsDataURL(input.files[0])
             }
             console.log('이미지 추가 및 수정  ')
             console.log(this.trig)
             this.trig=false
             
           
        }
    },
    
}
</script>

<style  scoped>
  .file-upload-form, .image-preview {
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    padding: 20px;
   }
    img.preview {
    width: 200px;
    background-color: white;
    border: 1px solid #DDD;
    padding: 5px;
}

</style>


