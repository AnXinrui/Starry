import './style.css'
import 'vant/es/toast/style'
import 'vant/es/dialog/style'

import App from './App.vue'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from "./config/router";

const pinia = createPinia()

createApp(App)
    .use(router)
    .use(pinia)
    .mount('#app')
