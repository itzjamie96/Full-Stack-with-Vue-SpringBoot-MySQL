<template>
 <v-app>
    <every-header/>
    <v-btn color="white" @click="$router.push({name : 'sitterSignUp'})" depressed>시터지원폼</v-btn>
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
