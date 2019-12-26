<template>
  <v-container fluid="" class="fill-height"> 
      <v-row class="fill-height">
        <v-col cols="3">
          
          <side-bar/>

          
          
        </v-col>

        <v-col cols="9">
          
        </v-col>

      </v-row>
    </v-container>


</template> 

<script>
import NavBar from '@/components/userNavigation.vue'

export default {
  components: {
    'side-bar' : NavBar
  }
}
</script>

<style scoped>
.container.fill-height { flex-wrap: wrap; } .container.fill-height > .row { flex: 1 1 100%; }
</style>