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
                    <v-card
                    class="mx-auto mb-10 pl-12"
                    max-width="95%"
                    >
                       

                        <GmapMap style="width: 600px; height: 300px;" :zoom="14" :center="lsm">
                        <gmap-circle ref="circle" :radius="1000" :center='lsm' :draggable='true' :editable='true' >
                            </gmap-circle>
                        </GmapMap>
                    </v-card>
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
    data() {
    return {
      markers: [],
      place: null,
      lsm:{lat:37.496361445796694,lng: 127.05750504049766}
    }
  },
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
       
   },
   methods:{},
   created() {
       this.$http.get('http://localhost:1234/showSitterDetail/'+this.$route.params.sitterNo) 
              .then(res => { 
                  this.lsm.lat=res.data.lat
                  this.lsm.lng=res.data.lng
                }) 
              .catch(err => { 
                console.log(err)

              });
   },
}


</script>
