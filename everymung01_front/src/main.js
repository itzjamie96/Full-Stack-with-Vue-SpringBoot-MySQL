import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import '@babel/polyfill'
import '@fortawesome/fontawesome-free/css/all.css'
import moment from 'moment'


Vue.config.productionTip = false


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
  render: h => h(App)
}).$mount('#app')


