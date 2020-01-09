<template>
 <v-app>
    <v-app v-if="trigger === false">
      <Admin></Admin>
    </v-app>
    <v-app v-else>
    <every-header
    />
    <router-view
    ></router-view>
    </v-app>
 </v-app>
 
</template>

<script>
import {mapState,mapActions} from "vuex"
import Header from '@/components/header.vue'
import AdminMain from '@/components/Admin-main.vue'

export default {
  
  components: {
    'every-header': Header,
    'Admin':AdminMain
  },

  data() {
    return {
      
    }
  },
  computed:{
     ...mapState(["trigger"]),
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');

@import url('https://fonts.googleapis.com/css?family=Gugi|Jua|Single+Day&display=swap');

@font-face { 
  font-family: 'HangeulNuri-Bold'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_three@1.0/HangeulNuri-Bold.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; }


@font-face { 
  font-family: 'ON-IGothic'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_eleven@1.0/ON-IGothic.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; }

#app {
  font-family: 'Noto Sans KR', sans-serif;
}


</style>