<template>
    <div>
        <v-card
            class="mx-auto mb-10"
            max-width="95%"   
            
        >
            <v-list>
                <v-list-item>
                    <router-link :to="{path:'/sitterProfilePage/' + sitterNo + '/' + sitterObj.sittingType}">
                    <v-list-item-avatar
                        size="120"
                    >
                     <v-img :src="this.profileImg"> </v-img>
                    </v-list-item-avatar>
                    </router-link>
                    <v-list-item-content>
                        <v-list-item-title class="headline mb-1">{{sitterObj.sitterName}}</v-list-item-title>
                        <v-list-item-subtitle>{{SitterAdd}}</v-list-item-subtitle>
                        <v-list-item-subtitle>{{sitterObj.profileTitle}}</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-card>

        <div class="headline mx-6">자격증 및 인증내용</div>
        
        <v-card
            class="mx-auto mb-10"
            max-width="92%"   
            elevation="1"
        >
            <v-list>
                <v-list-item >
                    <v-list-item-avatar
                        size="50"
                        tile                       
                    >
                    <i class="far fa-id-card fa-2x"></i>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title>신원 및 환경 검증 완료</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>    
            </v-list>
            <v-list>
                <v-list-item >
                    <v-list-item-avatar
                        size="50"
                        tile                       
                    >
                    <i class="fas fa-award fa-2x"></i>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title>펫시터 전문가 교육 수료</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>    
            </v-list>
        </v-card>
        
        <div class="headline mx-6">{{sitterObj.sitterName}} 펫시터 소개글</div>
        <v-card
            class="mx-auto mb-10 mt-2"
            max-width="92%"   
            elevation="1"
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
            let lsm = String(this.sitterObj.sitterAddress).slice(0,7)
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