import Vue from 'vue'
import store from './store'
import axios from 'axios'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import '@babel/polyfill'
import '@fortawesome/fontawesome-free/css/all.css'
import moment from 'moment'
import VueLocalStorage from 'vue-localstorage'
import PubNubVue from 'pubnub-vue';
import '@mdi/font/css/materialdesignicons.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import fullCalendar from 'vue-fullcalendar'
import * as VueGoogleMaps from "vue2-google-maps";


Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyAJ9a1JG4Adnk_2xnf51QdCx_JKi_5L60g",
    libraries: "places" // necessary for places input
  }
});
Vue.component('full-calendar', fullCalendar)

var infiniteScroll = require('vue-infinite-scroll'); 
Vue.use(infiniteScroll)

const publish_Key = 'pub-c-c191e7ba-affc-4757-a3be-82fc09a96c50';
const subscribe_Key = 'sub-c-04c7ffc2-2b82-11ea-a5fd-f6d34a0dd71d';

const myUuid = fourCharID();
const me = {
    uuid: myUuid,
};

try {
    if (!publish_Key || !subscribe_Key) {
        throw 'PubNub Keys are missing.';
    }
} catch (err) {
    console.log(err);
}

// Initialize the PubNub client API
Vue.use(PubNubVue, {
    subscribeKey: subscribe_Key,
    publishKey: publish_Key,
    ssl: true
}, store);

// Execute when the Vue instance is created
function created() {
    this.$store.commit('setMe', { me });
}

/**
 * Get a new 4 character ID. It is recommended to use a standard 128-bit UUID
 *     in production apps instead.
 *
 * @return {string} A unique ID for each user.  */

function fourCharID() {
    const maxLength = 4;
    const possible = 'abcdef0123456789';
    let text = '';

    for (let i = 0; i < maxLength; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }

    return text;
}

Vue.prototype.$http = axios;

Vue.use(VueLocalStorage)
Vue.config.productionTip = false
Vue.use(require('vue-moment'))

export const eventBus = new Vue()

Vue.filter('formatDate', function(value) {
        if (value) {
            return moment(String(value)).format('YYYY년 MM월 DD일')
        }
    }),
    Vue.filter('formatTime', function(value) {
        if (value) {
            return moment(String(value)).format('HH:mm')
        }
    }),
    Vue.use(vuetify, {
        iconfont: 'md'
    }),

    new Vue({
        router,
        store,
        vuetify,
        created,
        beforeCreate() {
            this.$store.dispatch("lsm")
        },
        render: h => h(App),
    }).$mount('#app')