import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'
import { routes } from './routes'

Vue.use(VueResource)
Vue.use(VueRouter)
Vue.use(Vuelidate)

Vue.config.productionTip = false
Vue.http.options.root = 'http://localhost:8082/api/user'

const router = new VueRouter({
  routes,
  mode: 'history'
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
