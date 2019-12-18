<template>
  <v-container class="text-center">
        <v-carousel style="cursor:pointer">
          <v-carousel-item
            v-for="meetups in meetups"
            :src="meetups.imageUrl" 
            :key="meetups.id"
            @click="onLoadMeetup(meetups.id)" 
          >
            <div class="title">
              {{meetups.title}}
            </div>
          </v-carousel-item>
        </v-carousel>
  </v-container>  
</template>

<script>
export default {
  computed: {
    meetups() {
      return this.$store.getters.featuredMeetups
    }
  },

  methods: {
    onLoadMeetup(id) {
      this.$router.push('/sitters/'+id)
    }
  }
}
</script>

<style>
  .title{
    color: aliceblue;
    position: absolute;
    bottom: 50px;
    display: inline-block;
  }
</style>