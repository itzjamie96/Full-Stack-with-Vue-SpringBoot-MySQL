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
