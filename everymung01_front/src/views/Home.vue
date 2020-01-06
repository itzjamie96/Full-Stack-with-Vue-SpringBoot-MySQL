<template>
  <v-container class="mx-0" fluid>
    <v-row>
      <v-carousel height="500px" >
        <v-carousel-item
          v-for="pics in pics"
          :src="pics.imageUrl" 
          :key="pics.id"  
        >
        </v-carousel-item>
      </v-carousel>
    </v-row>

    <v-row justify="center">
      <v-col cols="11">
        <services/>
      </v-col>
    </v-row>

    <v-row justify="center" >
      <v-col cols="11">
        <review/>
      </v-col>
    </v-row>



  </v-container>  
</template>

<script>
import axios from 'axios'
import Services from '@/components/main/service.vue'
import Reviews from '@/components/main/reviews.vue'


export default {

  components: {
    'services' : Services,
    'review' : Reviews
  },

  computed: {
    pics() {
      return this.$store.getters.featuredPics
    }
  },

}
</script>
