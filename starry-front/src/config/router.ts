import IndexPage from "@/pages/IndexPage.vue";
import UserLoginPage from "@/pages/user/UserLoginPage.vue";
import UserPage from "@/pages/user/UserPage.vue";
import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";
import TestPage from "@/pages/TestPage.vue";
import UserUpdatePage from "@/pages/user/UserUpdatePage.vue";
import UserUpdateEditPage from "@/pages/user/UserUpdateEditPage.vue";
import UserTagsUpdatePage from "@/pages/user/UserTagsUpdatePage.vue";
import UserRegisterPage from "@/pages/user/UserRegisterPage.vue";
import SearchUserPage from "@/pages/user/SearchUserPage.vue";
import FindPage from "../pages/FindPage.vue";
import UserRecommend from "../components/UserRecommend.vue";
import TeamFind from "../components/TeamFind.vue";

const routes: Array<RouteRecordRaw>  = [
    { path: '/', component: IndexPage },
    { path: '/user', component: UserPage },
    { path: '/user/login', component: UserLoginPage },
    { path: '/user/register', component: UserRegisterPage },
    { path: '/user/update', component: UserUpdatePage },
    { path: '/user/update/edit', component: UserUpdateEditPage },
    { path: '/user/tags', component: UserTagsUpdatePage },
    { path: '/user/search', component: SearchUserPage },
    {
        path: '/find',
        component: FindPage,
        children: [
            {
                // 当 /user/:id/profile 匹配成功
                // UserProfile 将被渲染到 User 的 <router-view> 内部
                path: '/find',
                component: UserRecommend,
            },
            {
                // 当 /user/:id/posts 匹配成功
                // UserPosts 将被渲染到 User 的 <router-view> 内部
                path: '/team-find',
                component: TeamFind,
            },
        ],
    },
    { path: '/test', component: TestPage },
]


const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;