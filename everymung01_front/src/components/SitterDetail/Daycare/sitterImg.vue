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
                v-for="sitterImg in sitterImgs"
                :src="sitterImg.img" 
                :key="sitterImg.id"
            ></v-carousel-item>
        </v-carousel>         
    </div>
</template>

<script>
import Axios from 'axios'

export default {
   data() {
       return {
           sitterObj: [],
           sitterImgs: [
               {img:''},
               {img:''},
               {img:''},
               {img:''},
           ]
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
            return this.$store.getters.featuredPics
    }
   },
   methods: {
       initialize() {
           const sitterNo = this.$route.params.sitterNo
           
           Axios
            .get(`http://localhost:1234/showSitterDetail/${sitterNo}`)
            .then(res => {
                this.sitterObj = res.data
                this.sitterImgs[0].img = 'http://localhost:1234/download/' +this.sitterObj.sitterImg2
                this.sitterImgs[1].img = 'http://localhost:1234/download/' +this.sitterObj.sitterImg3
                this.sitterImgs[2].img = 'http://localhost:1234/download/' +this.sitterObj.sitterImg4
                this.sitterImgs[3].img = 'http://localhost:1234/download/' +this.sitterObj.sitterImg5
            })
            .catch(err => {
                console.log(err);
            })

       }
   }
}
</script>