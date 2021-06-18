// 生产环境中注释掉以下语句
import 'sysStatic/css/theme-default.scss'
import './assets/iconfont/iconfont.css'
import InfiniteLoading from 'vue-infinite-loading';
// import '../mock/index.js'

import 'babel-polyfill'
import Vue from "vue"
import ElementUI from 'element-ui'
import router from './router'
import store from './store'
import axios from './util/ajax'
import i18n from './util/i18n'
import App from './index'
import moment from 'moment'
import clipboard from 'clipboard';
import vueCron from "vue-cron";
import cronstrue from 'cronstrue/i18n'
import echarts from 'echarts'

import './components/install'
import './plugins/install'
import './assets/iconfont/iconfont'
import './util/dialogDrag'


////////////////////////////////////系统常量配置////////////////////////////////////////
//配置：前端开发
// Vue.prototype.$debug = true
// Vue.prototype.$pdf = '/static/pdf/web/viewer.html?file='
// // Vue.prototype.$office = 'http://192.168.1.221/op/view.aspx?src='
// Vue.prototype.$office = '/static/pdf/web/viewer.html?file='//'http://192.168.1.221/op/view.aspx?src='
// Vue.prototype.$linkUrlHead = 'localhost:9000'
// // Vue.prototype.$upload = 'http://localhost:8080'
// // Vue.prototype.$upload = 'http://192.168.1.37:8780/Sypglass/'
// Vue.prototype.$upload = 'http://localhost:8080'

//配置：本机开发，虚拟机联调
// Vue.prototype.$debug = true
// Vue.prototype.$pdf = '/pdf/web/viewer.html?file='
// Vue.prototype.$office = 'http://192.168.1.221/op/view.aspx?src='
// Vue.prototype.$linkUrlHead = '192.168.1.14:9000'
// Vue.prototype.$upload = 'http://192.168.1.14:8080'

//配置：ST服务器
//Vue.prototype.$debug = true
// Vue.prototype.$pdf = '/pdf/web/viewer.html?file='
// Vue.prototype.$office = 'https://view.officeapps.live.com/op/view.aspx?src='
// Vue.prototype.$linkUrlHead = 'efile.ilaer.com:8080'
// Vue.prototype.$upload = 'http://efile.ilaer.com:8080'

//配置：UAT服务器
// Vue.prototype.$debug = false
// Vue.prototype.$pdf = '/pdf/web/viewer.html?file='
// Vue.prototype.$office = '/pdf/web/viewer.html?file='//'https://view.officeapps.live.com/op/view.aspx?src='
// Vue.prototype.$linkUrlHead = '192.168.1.5'
// Vue.prototype.$upload = 'http://192.168.1.5'

//配置：生产服务器
Vue.prototype.$debug = false
Vue.prototype.$pdf = '/pdf/web/viewer.html?file='
Vue.prototype.$office = '/pdf/web/viewer.html?file='//'https://view.officeapps.live.com/op/view.aspx?src='
Vue.prototype.$linkUrlHead = '222.204.246.136'
Vue.prototype.$upload = 'http://222.204.246.136'
////////////////////////////////////系统常量配置////////////////////////////////////////

// 注册组件到Vue
Vue.prototype.$axios = axios
Vue.prototype.$moment = moment
Vue.prototype.$clipboard = clipboard;
Vue.prototype.$cronstrue = cronstrue;
Vue.prototype.$maxFileSize = 1024;  //50M
Vue.prototype.$maxImgSize = 2;  //2M
Vue.prototype.$echarts = echarts;

Vue.use(ElementUI, {
    i18n: (key, value) => i18n.t(key, value),
    vueCron
})

Vue.use(InfiniteLoading)

new Vue({
    i18n,
    axios,
    router,
    store,
    render: h => h(App)
}).$mount('#app')
