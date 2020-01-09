<template>
    <div>
        <v-card
            class="ml-3 mb-10"
            max-width="96%"   
            elevation="0"
        >
            <v-list>
                <v-list-item>
                    <router-link :to="{path:'/sitterProfilePage/' + sitterNo + '/' + sitterObj.sittingType}">
                    <v-list-item-avatar
                        size="120"
                        color=""
                    >
                     <v-img :src="this.profileImg"> </v-img>
                    </v-list-item-avatar>
                    </router-link>
                    <v-list-item-content>
                        <v-list-item-title class="mb-3" id="sit">{{sitterObj.sitterName}}</v-list-item-title>
                        <v-list-item-title class="mb-3">{{sitterObj.sitterAddress.slice(0,7)}}</v-list-item-title>
                        <v-list-item-subtitle>{{sitterObj.profileTitle}}</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-card>
        <v-divider class="mx-6"></v-divider>
        <div class="ml-9 mt-2" id="certificate">자격증 및 인증내용</div>
        
        <v-card
            class="mx-auto mb-10"
            max-width="91%"   
            elevation="0"
        >
            <v-list>
                <v-list-item >
                    <v-list-item-avatar
                        size="50"
                        tile                       
                    >
                    <i class="far fa-id-card fa-3x"></i>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title id="text">신원 및 환경 검증 완료</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>    
            </v-list>
            <v-list>
                <v-list-item >
                    <v-list-item-avatar
                        size="50"
                        tile                       
                    >
                    <i class="fas fa-award fa-3x"></i>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title id="text">펫시터 전문가 교육 수료</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>    
            </v-list>
        </v-card>
        <v-divider class="mx-6"></v-divider>
        <div class="ml-9 mt-2" id="name">{{sitterObj.sitterName}} 펫시터 소개글</div>
        <v-card
            class="ml-9 mb-10 mt-2"
            max-width="91%"   
            elevation="0"
        >
            <div>{{sitterObj.profile}}</div>
        </v-card>
    </div>
</template>

<script>
import Axios from 'axios'
import {eventBus} from '@/main.js'

export default {
   data() {
       return {
           sitterObj: [],
           profileImg : ''
       }
   },

   created() {
       this.initialize()
       
       
   },
   computed: {
       sitterNo() {
           return this.$route.params.sitterNo
       },
        SitterAdd(){
            let lsm = String(this.sitterObj.sitterAddress).slice(5,15)
            return lsm
        }
   },
   methods: {
       initialize() {
           const sitterNo = this.$route.params.sitterNo
           
           
           Axios
            .get(`http://localhost:1234/showSitterDetail/${sitterNo}`)
            .then(res => {
                this.sitterObj = res.data
                eventBus.$emit("sitterObj",this.sitterObj)
                //console.log(res);
                 this.profileImg = 'http://localhost:1234/download/' + this.sitterObj.sitterImg1

            })
            .catch(err => {
                console.log(err);
            })
       },
   }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Gugi|Jua|Single+Day&display=swap');
@font-face { 
  font-family: 'S-CoreDream-8Heavy'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-8Heavy.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; 
}
@font-face { 
  font-family: 'HangeulNuri-Bold'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_three@1.0/HangeulNuri-Bold.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; }

#certificate, #name{
    font-size: 19pt;
    font-weight: bold;
    color: rgb(239, 83, 80);
    font-family: 'HangeulNuri-Bold'; 
    
}
#sit{
    font-size: 19pt;
    font-weight: bold;
    font-family: 'HangeulNuri-Bold'; 
}
/* .fa-id-card, .fa-award{
    color: rgb(239, 83, 80);
} */
#text{
    font-size: 13pt;
}
</style>