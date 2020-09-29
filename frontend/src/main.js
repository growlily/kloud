import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from "@/router";
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import qs from 'qs'

axios.defaults.baseURL="http://localhost:8888/api"
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.config.productionTip = false

Vue.use(ElementUI)

new Vue({
  render: h => h(App),
  router
}).$mount('#app')

router.beforeEach((to, from, next) => {
  if(to.path === '/course') {
    // if(window.sessionStorage.getItem('user') == null) {
    //   next({path: '/login'})
    // }
    // console.log(window.sessionStorage.getItem('userType'))
    // if(window.sessionStorage.getItem('userType') === '2') {
    //   next({path: '/user', query: to.fullPath})
    // }
    if(window.sessionStorage.getItem('userType') === '2') next({name: 'user'})
    else next()
  }
  else next()
})

