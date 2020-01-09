<template>
    <v-container >
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
import NewReservation from '@/components/SitterDetail/Daycare/newReservation.vue'
import Review from '@/components/SitterDetail/Daycare/review.vue'


export default {

   components: {
       'sitter-profile': SitterProfile,
       'sitter-img' : SitterImg,
       'new-reservation' : NewReservation,
       'review' : Review
   }
}

</script>

<style>
#img{
    padding-top: 0;
}
</style>
