<template>
  <v-container class="mx-0" fluid>
        <v-carousel height="500px" >
          <v-carousel-item
            v-for="pics in pics"
            :src="pics.imageUrl" 
            :key="pics.id"
            
          >
          </v-carousel-item>
        </v-carousel>



  </v-container>  
</template>

<script>
export default {
  computed: {
    pics() {
      return this.$store.getters.featuredPics
    }
  },

}
</script>
