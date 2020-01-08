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
                    <new-reservation/>
                    <GmapMap style="width: 600px; height: 300px;" :zoom="14" :center="lsm">
                        <gmap-circle ref="circle" :radius="1000" :center='lsm' :draggable='true' :editable='true' >
                            </gmap-circle>
                        </GmapMap>
                </v-col>
            </v-row>
            
           <v-row>
                <v-col cols="6">
                    <review/>
                </v-col>
            </v-row>
        </v-card>
    
    </v-container>
</template>

<script>

import SitterProfile from '@/components/SitterDetail/Homecare/sitterProfile.vue'
import SitterImg from '@/components/SitterDetail/Homecare/sitterImg'
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
