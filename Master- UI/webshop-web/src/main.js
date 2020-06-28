import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import {routes} from './routes'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueResource from 'vue-resource'

Vue.use(VueResource);
Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

Vue.http.options.root = 'https://localhost:8083/api/';

Vue.http.interceptors.push((request, next) => {
  if(localStorage.getItem('jwtToken') != null) {
    request.headers['Authorization'] = 'Bearer: ' + localStorage.getItem('jwtToken');
  }
  next();
})

const router = new VueRouter({
  routes
})

export const eventBus = new Vue();


new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
