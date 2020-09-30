import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from "@/router";
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import qs from 'qs'

axios.defaults.baseURL = `http://10.251.253.65:8888/api`
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.config.productionTip = false

Vue.use(ElementUI)

new Vue({
  render: h => h(App),
  router
}).$mount('#app')


