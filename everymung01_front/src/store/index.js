import Vue from 'vue'
import Vuex from 'vuex'
// import * as firebase from 'firebase'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    
    loadedMeetups: [  
        {
          imageUrl: 'https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60', 
          id: 'aaa', 
          title: 'Meetup in city',
          date: '2019-12-17',
          time: '15:35',
          location: 'New York',
          description:'All in NewYork'
        },
        {
          imageUrl: 'https://images.unsplash.com/photo-1448375240586-882707db888b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60', 
          id: 'bbb', 
          title: 'Meetup in forest',
          date: '2019-12-19',
          time: '14:00',
          location: 'Forest',
          description: 'Forest!'
        }
    ],
    user: {
      id:'abc',
      registeredMeetups: [
        'aaa'
      ]
    }
    
  },
  mutations: {
    createMeetup(state, payload) {
      state.loadedMeetups.push(payload)
    },
    createReservation(state, payload) {
      
    }
  },
  actions: {  //reach out to firebase and store it
    createMeetup({commit}, payload) {
      const meetup = {    //mapping to an object: payload might have properties that i may not need
        title: payload.title,
        location: payload.location,
        imageUrl: payload.imageUrl,
        description: payload.description,
        date: payload.date,
        time: payload.time,
        id: '111'
      }
      commit('createMeetup', meetup)
      
    }
  },
  getters: {
    loadedMeetups (state) { //all meetups
      return state.loadedMeetups.sort((meetupA, meetupB) => {
        return meetupA.date > meetupB.date
      }) 
    },
    loadedMeetup (state) {   // one meetup
      return (meetupId) => {
        return state.loadedMeetups.find((meetup) => {
          return meetup.id === meetupId
        })
      }
    },
    featuredMeetups(state, getters) {   //fetching some chosen meetups from the loaded Meetups
      return getters.loadedMeetups.slice(0,5)
    }
  }
})
