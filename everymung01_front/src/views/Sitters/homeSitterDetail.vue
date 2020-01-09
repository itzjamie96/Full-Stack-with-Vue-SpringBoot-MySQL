<template>
    <v-container>
        <v-card>
            <v-row class="justify-center">
                <v-col cols="12" id="img">
                    <sitter-img/>         
                </v-col>
            </v-row>
            <v-row class="mt-9 justify-center">
                <v-col cols="5">
                    <sitter-profile/> 
                    
                </v-col>
                <v-col cols="1"></v-col>
                <v-col cols="5 justify-center">
                    <new-reservation/>
                    
                    <div class="ml-9 mt-8" >
                    <GmapMap style="width: 421.75px; height: 300px;" :zoom="13" :center="lsm">
                        <gmap-circle ref="circle" :radius="800" :center='lsm' :draggable='false' :editable='false' >
                            </gmap-circle>
                        </GmapMap>
                    </div>
                </v-col>
            </v-row>
            
            <v-row class="justify-center">
                <v-col cols="5">
                    <review/>
                </v-col>
                <v-col cols="1"></v-col>
                <v-col cols="5"></v-col>
            </v-row>
        </v-card>
    
    </v-container>
</template>

<script>

import SitterProfile from '@/components/SitterDetail/Daycare/sitterProfile.vue'
import SitterImg from '@/components/SitterDetail/Daycare/sitterImg'
import NewReservation from '@/components/SitterDetail/Homecare/newReservation.vue'
import Review from '@/components/SitterDetail/Homecare/review.vue'


export default {

   components: {
       'sitter-profile': SitterProfile,
       'sitter-img' : SitterImg,
       'new-reservation' : NewReservation,
       'review' : Review
   },
   data(){
       return{
           lsm:{lat:37.496361445796694,lng: 127.05750504049766},
       }
   },
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

<style>
#img{
    padding-top: 0;
}

</style>