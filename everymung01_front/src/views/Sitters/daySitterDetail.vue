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
