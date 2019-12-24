<template>
 <v-app>
    <every-header/>
    <router-view></router-view>
 </v-app>
</template>

<script>
import Header from '@/components/header.vue'

export default {
  
  components: {
    'every-header': Header
  },

  data() {
    return {

    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');

#app {
  font-family: 'Noto Sans KR', sans-serif;
}


</style>