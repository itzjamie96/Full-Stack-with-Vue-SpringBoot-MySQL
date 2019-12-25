import Vue from 'vue'
import axios from 'axios'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import '@babel/polyfill'
import '@fortawesome/fontawesome-free/css/all.css'
import moment from 'moment'
import VueLocalStorage from 'vue-localstorage'



Vue.prototype.$http = axios;

Vue.use(VueLocalStorage)
Vue.config.productionTip = false

export const eventBus = new Vue()

Vue.filter('formatDate', function(value) {
  if (value) {
    return moment(String(value)).format('YYYY년 MM월 DD일')
  }
}),
Vue.filter('formatTime', function(value) {
  if (value) {
    return moment(String(value)).format('hh:mm')
  }
}),

new Vue({
  router,
  store,
  vuetify,
  beforeCreate() {
    this.$store.dispatch("lsm")
  },
  render: h => h(App)
}).$mount('#app')


