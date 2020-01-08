<template>

    <v-container fluid="" >
        <v-row class="fill-height">
            <v-col cols="3">
            <side-bar></side-bar>
            </v-col>
                <v-col cols="9">
                    <v-card>
                        <form>
                        <div>
                        <v-row justify="center">
                        <v-col cols="3" > 
                           <div class="file-upload-form" >
                            프로필 사진 :
                            <v-list-item-avatar
                             tile
                             size="100"
                            >
                             <v-img :src="this.img"> </v-img>
                            </v-list-item-avatar>
                              <input 
                              type="file" 
                              @change="previewImage" 
                              accept="image/*">
                            </div>
                     <div class="image-preview" v-if="imageData.length > 0">
                        <img class="preview" :src="imageData">
                     </div>
                        </v-col>
                        </v-row>   
                        </div>
                            <v-row justify="center">
                                
                                <v-col cols="3"> 
                                 <v-text-field
                                 label="이름"
                                 v-model="sitterInfo.sitterName"
                                 @input="userInfoReading"
                                 >
                                 </v-text-field>
                                </v-col>
                            </v-row>
        
                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    v-model="sitterInfo.sitterEmail"
                                    label="E-mail"
                                    @input="userInfoReading"
                                ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    v-model="sitterInfo.sitterPw"
                                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :rules="[rules.required, rules.min]"
                                    :type="show1 ? 'text' : 'password'"
                                    label="패스워드"
                                    hint="최소 8자리 이상 "
                                    counter
                                    @click:append="show1 = !show1"
                                    ></v-text-field>
                                </v-col>
                            </v-row>
          
                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    v-model="sitterInfo.sitterPhone"
                                    label="휴대폰 번호"
                                    ></v-text-field>
                                </v-col>
                            </v-row>

                            <v-row justify="center">
                                <v-col cols="3">
                                    <v-text-field
                                    :disabled="userInfo.sittingNo===2"
                                    v-model="sitterInfo.sitterAddress"
                                    label=" 주소 "
                                    ></v-text-field>
                                <v-btn
                                v-if="userInfo.sittingNo===2"
                                @click="upHomeAdd"
                                >주소 수정</v-btn>
                                </v-col>
                            </v-row>
         
                            <v-row justify="center">
                                <v-btn @click="updateSitterInfo">수정</v-btn>
                            </v-row>
                        </form>
                    </v-card>
                </v-col> 
            </v-row>
             <v-dialog
                v-model="homeSitterAdd"
                max-width= 800
                max-height= 500
                >
                <v-card>
                    <div class="ml-12">
                    <v-card-title class="headline">홈 시터 주소 수정</v-card-title>
                    <GmapMap style="width: 600px; height: 300px;" :zoom="14" :center="{lat:sitterInfo.lat,lng:sitterInfo.lng}">
                    <gmap-circle ref="circle" :radius="800" :center='{lat:sitterInfo.lat,lng:sitterInfo.lng}' :draggable='true' :editable='true' >
                        </gmap-circle>
                    </GmapMap>
                    <label>
                        시/구/동 주소 입력:
                        <GmapAutocomplete @place_changed="setPlace" id="lsm" class="mx-4">
                        </GmapAutocomplete>
                        <v-btn class="mx-12" text @click="usePlace" outlined>주소 입력</v-btn>
                        </label>
                    </div>
                    <v-row justify="center">
                                <v-col cols="8">
                                    <v-text-field
                                    :disabled="true"
                                    v-model="sitterInfo.sitterAddress"
                                    label=" 주소 "
                                    ></v-text-field>
                                </v-col>
                            </v-row>
                            <v-row justify="center">
                                <v-col cols="8">
                                    <v-text-field
                                    v-model="sitterInfo.sitterAddress"
                                    label=" 상세 주소 "
                                    ></v-text-field>
                                </v-col>
                            </v-row>
                    <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="green darken-1"
                        text
                        @click="homeSitterAdd = false"
                    >
                        Disagree
                    </v-btn>

                    <v-btn
                        color="green darken-1"
                        text
                        @click="homeSitterAdd = false"
                    >
                        Agree
                    </v-btn>
                    </v-card-actions>
                </v-card>
                </v-dialog>
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
            ///
            place:null,
            homeSitterAdd:false,
            /////
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
                sitterAddress:'',
                lat:37.496361445796694,
                lng:127.05750504049766
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
        upHomeAdd(){
        this.homeSitterAdd =true
        
        },
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
        setPlace(place) {
      this.place=place
      
    },
    usePlace(place) {
      this.sitterInfo.sitterAddress = this.place.formatted_address
      if (this.place) {
        this.sitterInfo.lat=this.place.geometry.location.lat()
        this.sitterInfo.lng=this.place.geometry.location.lng()
        this.place = null;
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
    #lsm{
        width: 280px
    }
</style>

