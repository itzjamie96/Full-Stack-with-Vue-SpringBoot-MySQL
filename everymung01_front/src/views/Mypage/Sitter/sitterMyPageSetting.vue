<template>

  <v-container class="pt-12 px-0">
    <v-row class="justify-center">
        <v-col cols="3"> 
          <side-bar/>   
        </v-col>
        
        <v-col cols="9">
            <v-card>
                <form>
                    <v-row class="justify-center">
                        <v-col cols="9" class="text-center" > 
                           <div class="file-upload-form" >
                            <p id="profileTitle">프로필 사진</p>
                            <v-list-item-avatar
                             size="200"
                            >
                             <v-img :src="this.img"> </v-img>
                            </v-list-item-avatar>
                            <div>
                                <input 
                                type="file" 
                                @change="previewImage" 
                                class="ml-12 pl-12"
                                accept="image/*">

                            </div>
                        </div>
                     <div class="image-preview" v-if="imageData.length > 0">
                        <img class="preview" :src="imageData">
                     </div>
                        </v-col>
                        </v-row>   
                    <v-divider class="mx-12 my-6"></v-divider>


                            <v-row class="justify-center">
                                <v-col cols="4" > 
                                 <v-text-field
                                 label="이름"
                                 v-model="sitterInfo.sitterName"
                                 @input="userInfoReading"
                                 >
                                 </v-text-field>
                                </v-col>
                            </v-row>
        
                            <v-row class="justify-center">
                                <v-col cols="4">
                                    <v-text-field
                                    v-model="sitterInfo.sitterEmail"
                                    label="E-mail"
                                    @input="userInfoReading"
                                ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row class="justify-center">
                                <v-col cols="4">
                                    <v-text-field
                                    v-model="sitterInfo.sitterPw"
                                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :rules="[rules.required, rules.min]"
                                    :type="show1 ? 'text' : 'password'"
                                    label="비밀번호"
                                    hint="최소 6자리 이상 "
                                    counter
                                    @click:append="show1 = !show1"
                                    ></v-text-field>
                                </v-col>
                            </v-row>
          
                            <v-row class="justify-center">
                                <v-col cols="4">
                                    <v-text-field
                                    v-model="sitterInfo.sitterPhone"
                                    label="휴대폰 번호"
                                    ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row class="justify-center">
                                <v-col cols="4">
                                    <v-text-field
                                    v-model="sitterInfo.sitterAddress"
                                    label=" 주소 "
                                    ></v-text-field>
                                </v-col>
                            </v-row>
         
                            <v-row class="justify-center">
                                <v-btn @click="updateSitterInfo">수정</v-btn>
                            </v-row>
                            <v-row><v-col></v-col></v-row>
                        </form>
                    </v-card>
                </v-col> 
            </v-row>
    </v-container>
</template>


<script>
import NavBar from '@/components/sitterNavigation.vue'
import { mapState }  from 'vuex'
import { userInfo } from 'os'
import axios from 'axios'




export default {
    
    data(){
        return{
            show1:false,
            rules: {
             required: value => !!value || 'Required.',
             min: v => (v&& v.length >= 8) || 'Min 8 characters',
             emailMatch: () => ('The email and password you entered don\'t match'),
            },
            sitterInfo:{
                sitterName:'',
                sitterEmail:'',
                sitterPw:'',
                sitterPhone:'',
                sitterAddress:''
            },
            imageData:'',
            img:'',
            sitterImg:''

        }
    },
    computed:{
        ...mapState(['userInfo'])
    },
    created(){
        this.initialize()
    },
    components:{
        'side-bar':NavBar
    },

    methods:{
        userInfoReading(e){
           this.$store.commit('userInfoReading', e.target.value)
           console.log(e)
        },
        initialize(){
            this.sitterInfo.sitterName = this.userInfo.sitterName
            this.sitterInfo.sitterEmail = this.userInfo.sitterEmail
            this.sitterInfo.sitterPw = this.userInfo.sitterPw
            this.sitterInfo.sitterPhone = this.userInfo.sitterPhone
            this.sitterInfo.sitterAddress = this.userInfo.sitterAddress
            this.img = 'http://localhost:1234/download/' + this.userInfo.sitterImg1
        },
        updateSitterInfo(){
            let formData = new FormData()
            formData.append('file', this.sitterImg)
            console.log("sitterImg : " + this.sitterImg)

            axios.post('http://localhost:1234/updateSitterInfo', this.sitterInfo)
                .then(res => {
                    console.log(res)
                    console.log(this.sitterInfo.sitterEmail)
                    axios.post('http://localhost:1234/upload-file/'+ 'sitterImg1' +'/'+ this.sitterInfo.sitterEmail, 
                    formData,{    
                        headers:{
                            'Content-Type' : 'multipart/form-data'
                                }
                    }).then(res => {
                        console.log(res)
                        // this.$router.push({path:'/sMyPage/sitterReservationList'})
                        window.location.reload()
                    }).catch(err => {
                        console.log(err)
                    })
                }).catch(err => {
                    console.log(err)
                })
            
        },
        previewImage(event){
            this.sitterImg = event.target.files[0]
            console.log(this.sitterImg)
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    this.imageData= e.target.result;
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
#profileTitle{
    font-size: 18pt;
    font-family: 'HangeulNuri-Bold'; 
}
</style>

