import IndexPage from "../pages/IndexPage.vue";
import UserLoginPage from "../pages/user/UserLoginPage.vue";
import UserPage from "../pages/user/UserPage.vue";
import {createRouter, createWebHistory} from "vue-router";
import TestPage from "../pages/TestPage.vue";
import UserUpdatePage from "../pages/user/UserUpdatePage.vue";
import UserUpdateEditPage from "../pages/user/UserUpdateEditPage.vue";

const routes = [
    { path: '/', component: IndexPage },
    { path: '/user', component: UserPage },
    { path: '/user/login', component: UserLoginPage },
    { path: '/user/update', component: UserUpdatePage },
    { path: '/user/update/edit', component: UserUpdateEditPage },
    { path: '/test', component: TestPage },
]


const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;