<template>
    <div>
        <v-card-title>
            <h4>{{sitterObj.sitterNo}} (PK) :</h4>
            <h4> {{sitterObj.sittingType}} (Sitter Type)</h4>
        </v-card-title>

        <v-carousel
            :show-arrows="false"
            :continuous="true"
            height="300"
             cycle
        >
            <v-carousel-item
                v-for="meetups in meetups"
                :src="meetups.imageUrl" 
                :key="meetups.id"
                @click="onLoadMeetup(meetups.id)"
            ></v-carousel-item>
        </v-carousel>         
    </div>
</template>

<script>
import Axios from 'axios'

export default {
   data() {
       return {
           sitterObj: []
       }
   },

   created() {
       this.initialize()
       
   },
   computed: {
       sitterNo() {
           return this.$route.params.sitterNo
       },
      
        meetups() {
            return this.$store.getters.featuredMeetups
    }
   },
   methods: {
       initialize() {
           const sitterNo = this.$route.params.sitterNo
           
           Axios
            .get(`http://localhost:1234/showSitterDetail/${sitterNo}`)
            .then(res => {
                this.sitterObj = res.data
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })

       }
   }
}
</script>