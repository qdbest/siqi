// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// 引入element-ui及css文件
import ElementUi from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// 引入vuex
import Vuex from 'vuex'
// import store from './store'
// use
Vue.use(ElementUi, { size: 'small' })
Vue.use(Vuex)
// 引入axios
import axios from 'axios'
// 挂载axios
Vue.prototype.$axios = axios

import './assets/icon/iconfont.css'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
