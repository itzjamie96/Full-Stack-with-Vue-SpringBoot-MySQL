<template>
   <v-container fluid="">
      <v-row class="fill-height">
         <v-col cols="3">
            <side-bar/>   
         </v-col>
         <v-col cols="9">
            <v-card>
               <form>
            <!-- 프로필사진 -->
                  <div>
                     <v-row justify="center">
                        <v-col cols="3" > 
                           <div class="file-upload-form" >
                           소개사진 1 :
                           <v-list-item-avatar
                             tile
                             size="100"
                            >
                             <v-img :src="this.img2"> </v-img>
                            </v-list-item-avatar>
                              <input 
                              type="file" 
                              @change="previewImage2" 
                              accept="image/*">
                            </div>
                     <div class="image-preview" v-if="imageData2.length > 0">
                        <img class="preview" :src="imageData2">
                     </div>
                        </v-col>
                     </v-row>   
                  </div>
                  <div>
                     <v-row justify="center">
                        <v-col cols="3" > 
                           <div class="file-upload-form" >
                           소개사진 2 :
                           <v-list-item-avatar
                             tile
                             size="100"
                            >
                             <v-img :src="this.img3"> </v-img>
                            </v-list-item-avatar>
                              <input 
                              type="file" 
                              @change="previewImage3" 
                              accept="image/*">
                            </div>
                     <div class="image-preview" v-if="imageData3.length > 0">
                        <img class="preview" :src="imageData3">
                     </div>
                        </v-col>
                     </v-row>   
                  </div>
                  <div>
                     <v-row justify="center">
                        <v-col cols="3" > 
                           <div class="file-upload-form" >
                           소개사진 3 :
                           <v-list-item-avatar
                             tile
                             size="100"
                            >
                             <v-img :src="this.img4"> </v-img>
                            </v-list-item-avatar>
                              <input 
                              type="file" 
                              @change="previewImage4" 
                              accept="image/*">
                            </div>
                     <div class="image-preview" v-if="imageData4.length > 0">
                        <img class="preview" :src="imageData4">
                     </div>
                        </v-col>
                     </v-row>   
                  </div>
                  <div>
                     <v-row justify="center">
                        <v-col cols="3" > 
                           <div class="file-upload-form" >
                           소개사진 4 :
                           <v-list-item-avatar
                             tile
                             size="100"
                            >
                             <v-img :src="this.img5"> </v-img>
                            </v-list-item-avatar>
                              <input 
                              type="file" 
                              @change="previewImage5" 
                              accept="image/*">
                            </div>
                     <div class="image-preview" v-if="imageData5.length > 0">
                        <img class="preview" :src="imageData5">
                     </div>
                        </v-col>
                     </v-row>   
                  </div>
            
                     <v-row justify="center" class="ml-12">
                        <v-col cols="5"> 
                           <v-text-field
                           v-model="sitterProfile.profileTitle"
                           :counter="15"
                           :rules="nameRules"
                           label="프로필 제목"
                           placeholder="프로필 제목을 입력해주세요"
                           required
                           ></v-text-field>
                        </v-col>
                        <v-col cols="1"></v-col>
                     </v-row>
      
                     <v-row justify="center" class="ml-12">
                           <v-col cols="5">
                              <p>자기소개</p>
                                 <v-textarea
                                 filled
                                 auto-grow
                                 rows="4"
                                 placeholder="자기소개를 입력해주세요"
                                 value="answer4"
                                 row-height="30" 
                                 v-model="sitterProfile.profile"
                                 ></v-textarea>
                           </v-col>
                     </v-row>
                     <v-row justify="center" class="ml-12">
                           <v-col cols="5">
                              <p>펫소개</p>
                                 <v-textarea
                                 filled
                                 auto-grow
                                 rows="4"
                                 placeholder="펫소개를 입력해주세요"
                                 value="answer4"
                                 row-height="30" 
                                 v-model="sitterProfile.petProfile"
                                 ></v-textarea>
                           </v-col>
                     </v-row>

                        <v-row justify="center" class="ml-12">
                           <v-col cols="3">
                              <v-btn @click="updateSitterProfile" 
                              >저장하기</v-btn>
                           </v-col>
                        </v-row>
               </form>
            </v-card>
         </v-col>
      </v-row>
   </v-container>
</template>



<script>
import axios from 'axios'
import NavBar from '@/components/sitterNavigation.vue'
import { mapState} from 'vuex'
import { format } from 'path'
export default {
    data(){
        return{
            valild:true,
            nameRules:[
               v => !!v || '반려견 이름은 필수 입력 사항입니다. ',
               v => (v && v.length <= 15) || ' 15 자 내외로 입력 가능합니다. '
            ],
             imageData:'',
             imageData2:'',
             imageData3:'',
             imageData4:'',
             imageData5:'',
             sitterImg1:'',
             sitterImg2:'',
             sitterImg3:'',
             sitterImg4:'',
             sitterImg5:'',
             img1 : '',
             img2 : '',
             img3 : '',
             img4 : '',
             img5 : '',
             sitterProfile:{
               sitterEmail: '',
               profileTitle:'',             
               profile:'',
               petProfile:''
            },
            menu2:false
        }
    },
    components: {
        'side-bar' : NavBar
    },
    created(){
       this.initialize()
    },
    
    computed:{
      ...mapState(['userInfo']),
    },
    methods:{
       initialize(){  
           this.sitterProfile.profileTitle = this.userInfo.profileTitle
           this.sitterProfile.profile = this.userInfo.profile
           this.sitterProfile.petProfile = this.userInfo.petProfile
           this.img1 = 'http://localhost:1234/download/' + this.userInfo.sitterImg1
           this.img2 = 'http://localhost:1234/download/' + this.userInfo.sitterImg2
           this.img3 = 'http://localhost:1234/download/' + this.userInfo.sitterImg3
           this.img4 = 'http://localhost:1234/download/' + this.userInfo.sitterImg4
           this.img5 = 'http://localhost:1234/download/' + this.userInfo.sitterImg5
       },
       updateSitterProfile(){  // 저장 후 디비 저장할 수 있도록 axios 
           this.sitterProfile.sitterEmail = this.userInfo.sitterEmail
           let email = this.userInfo.sitterEmail
           let formData1 = new FormData()
           let formData2 = new FormData()
           let formData3 = new FormData()
           let formData4 = new FormData()
           let formData5 = new FormData()
           formData1.append('file', this.sitterImg1)            
           formData2.append('file', this.sitterImg2)            
           formData3.append('file', this.sitterImg3)            
           formData4.append('file', this.sitterImg4)            
           formData5.append('file', this.sitterImg5)      
           axios.post('http://localhost:1234/updateSitterProfile', this.sitterProfile)   //이미지만 제외 후 저장 
           .then(res =>{
              console.log(res)
                 // 성공 후 바로 또 axios를 통해서 이미지만 저장 
                  axios.post('http://localhost:1234/upload-file/'+ 'sitterImg2' +'/'+ email, 
                  formData2,{    
                     headers:{
                         'Content-Type' : 'multipart/form-data'
                              }
                 }).then(res => {
                    console.log(res.data)
                    axios.post('http://localhost:1234/upload-file/'+ 'sitterImg3' +'/'+ email, 
                     formData3,{    
                        headers:{
                            'Content-Type' : 'multipart/form-data'
                                }
                 }).then(res => {
                    console.log(res.data)
                    axios.post('http://localhost:1234/upload-file/'+ 'sitterImg4' +'/'+ email, 
                     formData4,{    
                        headers:{
                            'Content-Type' : 'multipart/form-data'
                                }
                 }).then(res => {
                     console.log(res.data)
                     axios.post('http://localhost:1234/upload-file/'+ 'sitterImg5' +'/'+ email, 
                     formData5,{    
                        headers:{
                            'Content-Type' : 'multipart/form-data'
                                }
                 }).then(res => {
                     console.log(res.data)
                  //   location.reload();
                     // this.$router.push({path:'/sMyPage/sitterReservationList'})
                     window.location.reload()
                 }).catch(err => {
                     console.log(err)
                     window.location.reload()
                    })
                 }).catch(err => {
                     console.log(err)
                     window.location.reload()
                    })
                 }).catch(err => {
                     console.log(err)
                     window.location.reload()
                    })
                 }).catch(err => {
                     console.log(err)
                     window.location.reload()
                    })
           })
           .catch(err =>{
              alert('입력이 잘 되었는지 다시 확인 해 주세요')
           })
       },
      
       previewImage2(event){
            this.sitterImg2 = event.target.files[0]
            console.log(this.sitterImg2)
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    this.imageData2= e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        },
       previewImage3(event){
            this.sitterImg3 = event.target.files[0]
            console.log(this.sitterImg3)
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    this.imageData3= e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        },
       previewImage4(event){
            this.sitterImg4 = event.target.files[0]
            console.log(this.sitterImg4)
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    this.imageData4= e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        },
       previewImage5(event){
            this.sitterImg5 = event.target.files[0]
            console.log(this.sitterImg5)
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    this.imageData5= e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        },
       
    }
    
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