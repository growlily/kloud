import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from "@/router";
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import qs from 'qs'
import process from 'process';

let server = 'localhost:8888'
if (process.env.NODE_ENV === 'production')
  server = process.env.SERVER
axios.defaults.baseURL = `http://${server}/api`
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.config.productionTip = false

Vue.use(ElementUI)

new Vue({
  render: h => h(App),
  router
}).$mount('#app')


