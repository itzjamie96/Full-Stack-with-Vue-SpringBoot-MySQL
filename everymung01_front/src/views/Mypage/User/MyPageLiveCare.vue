<template>
  <v-container fluid="" class="fill-height">
      <v-row class="fill-height">
          <v-col cols="3">
              <side-bar></side-bar>
          </v-col>

          <v-col >
              <!-- 실시간 영상 보기  -->
              <v-row>
                  <v-card
                  width="100%"
                  outlined
                  
                  >
                      실시간 영상보기 
                  </v-card>
              </v-row>
          </v-col>
      </v-row>
  </v-container>

   
   
    
</template>

<script>
import NavBar  from '@/components/userNavigation.vue'


export default {
    data(){
        return
    },
    components:{
        'side-bar':NavBar
    }
    
}
</script>