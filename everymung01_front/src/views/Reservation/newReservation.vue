<template>
<v-container>
  <v-row class="d-flex pa-2">
    <h4>Create a new Meetup</h4>
  </v-row>
  <v-row class="d-flex pa-2">
    <v-col cols="18">
      <form @submit.prevent="onCreateMeetup">
        <v-row class="mb-3">
            <v-text-field
              name="title"
              label="Title"
              id="title"
              v-model="title"
            ></v-text-field>
        </v-row>
        <v-row class="mb-3">
            <v-text-field
              name="location"
              label="Location"
              id="location"
              v-model="location"
            ></v-text-field>
        </v-row>
        <v-row class="mb-3">
            <v-text-field
              name="imageUrl"
              label="Image Url"
              id="imageUrl"
              v-model="imageUrl"
            ></v-text-field>
        </v-row>
        <v-row class="mb-3">
          <img :src="imageUrl" height="300">
        </v-row>
        <v-row class="mb-3">
          <v-textarea
              name="description"
              label="Description"
              id="description"
              v-model="description"
          >   
          </v-textarea>
        </v-row>
        <v-row>
          <h4>Choose a Date & Time</h4>
        </v-row>
        <v-row class="mb-3">
          <v-date-picker v-model="date"></v-date-picker>
          <p>{{date}}</p>
        </v-row>
        <v-row class="mb-3">
          <v-time-picker v-model="time"></v-time-picker>
          <p>{{time}}</p>
        </v-row>
        <v-row>
          <v-btn 
          :disabled="!formIsValid"
          type="submit"
          >Create Meetup</v-btn>
        </v-row>
      </form>

    </v-col>
  </v-row>
</v-container>
</template>

<script>
export default {
  data() {
    return {
      title: '',
      location: '',
      imageUrl: '',
      description: '',
      date: new Date().toISOString().substr(0, 10), 
      time: new Date().getHours() + ':' + new Date().getMinutes()

    }
  },
  computed: {
    formIsValid() {
      return this.title !== '' && this.location !== ''
      && this.imageUrl !=='' && this.description !== ''
    }

  },
  methods: {
    onCreateMeetup() {
      if (!this.formIsValid) {
        console.log('test')
        return
      }
      const meetupData = {
        title: this.title,
        location: this.location,
        imageUrl: this.imageUrl,
        description: this.description,
        date: this.date,
        time: this.time
      }
      this.$store.dispatch('createMeetup', meetupData)
      console.log('reserve')
      console.log(meetupData)
      this.$router.push('/meetups')
    }
  }
}
</script>