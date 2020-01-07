<template>
    <v-container fluid>
        <v-card>
            <v-row>
                <v-col cols="12">
                    <sitter-img/>         
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="6">
                    <sitter-profile/> 
                </v-col>
                <v-col cols="6">
                    
                    <router-link :to="{path:'/daysitters/' + sitterNo}" v-if="this.sittingType==='daycare' && isLogin===true && role==='User'">
                        <v-card>
                        데이시터예약하러가기
                        </v-card>
                    </router-link>
                    <router-link :to="{path:'/home/' + sitterNo}" v-if="this.sittingType==='home' && isLogin===true && role==='User'">
                        <v-card>
                        홈시터예약하러가기
                        </v-card>
                    </router-link>
                    
                    <review/>
                </v-col>
            </v-row>
                
        </v-card>
    
    </v-container>
</template>

<script>

import SitterProfile from '@/components/SitterDetail/Daycare/sitterProfile.vue'
import SitterImg from '@/components/SitterDetail/Daycare/sitterImg'
import Review from '@/components/SitterDetail/Daycare/review.vue'
import { mapState }  from 'vuex'


export default {

   components: {
       'sitter-profile': SitterProfile,
       'sitter-img' : SitterImg,
       'review' : Review
   },
   computed: {
       sitterNo() {
           return this.$route.params.sitterNo
       },
       sittingType() {
           return this.$route.params.sittingType
       },
       ...mapState(['userInfo']),
       ...mapState(['isLogin','role'])
       
   }
}

</script>
