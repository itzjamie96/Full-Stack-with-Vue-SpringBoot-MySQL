import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import '@babel/polyfill'
import '@fortawesome/fontawesome-free/css/all.css'
import VueLocalStorage from 'vue-localstorage'


Vue.use(VueLocalStorage)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  beforeCreate() {
    this.$store.dispatch("lsm")
  },
  render: h => h(App)
}).$mount('#app')


