import IndexPage from "@/pages/IndexPage.vue";
import UserLoginPage from "@/pages/user/UserLoginPage.vue";
import UserPage from "@/pages/user/UserPage.vue";
import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";
import UserUpdatePage from "@/pages/user/UserUpdatePage.vue";
import UserUpdateEditPage from "@/pages/user/UserUpdateEditPage.vue";
import UserTagsUpdatePage from "@/pages/user/UserTagsUpdatePage.vue";
import UserRegisterPage from "@/pages/user/UserRegisterPage.vue";
import SearchUserPage from "@/pages/user/SearchUserPage.vue";
import FindPage from "../pages/FindPage.vue";
import UserRecommend from "../components/UserRecommend.vue";
import TeamFind from "../components/TeamFind.vue";
import TeamAddPage from "../pages/Team/TeamAddPage.vue";
import UserTeamPage from "../pages/Team/UserTeamPage.vue";
import CreatedTeams from "../components/CreatedTeams.vue";
import JoinedTeams from "../components/JoinedTeams.vue";
import ModoroPage from "../pages/others/ModoroPage.vue";
import TestPage from "../pages/TestPage.vue";
import UserBlogPage from "../pages/user/UserBlogPage.vue";
import TestLogin from "../pages/TestLogin.vue";


const routes: Array<RouteRecordRaw>  = [
    { path: '/', component: IndexPage },
    { path: '/user', component: UserPage },
    { path: '/user/login', component: UserLoginPage },
    { path: '/user/register', component: UserRegisterPage },
    { path: '/user/update', component: UserUpdatePage },
    { path: '/user/update/edit', component: UserUpdateEditPage },
    { path: '/user/tags', component: UserTagsUpdatePage },
    { path: '/user/search', component: SearchUserPage },
    { path: '/user/blog', component: UserBlogPage },
    {
        path: '/find',
        component: FindPage,
        children: [
            {
                // 当 /user/:id/profile 匹配成功
                // UserProfile 将被渲染到 User 的 <router-view> 内部
                path: '',
                component: UserRecommend,
            },
            {
                // 当 /user/:id/posts 匹配成功
                // UserPosts 将被渲染到 User 的 <router-view> 内部
                path: 'team-find',
                component: TeamFind,
            },
        ],
    },
    { path: '/team/add', component: TeamAddPage },
    { path: '/team/user-team', component: UserTeamPage,
        children: [
            { path: '/team/created-teams', component: CreatedTeams },
            { path: '/team/joined-teams', component: JoinedTeams },
        ],
    },

    { path: '/modoro', component: ModoroPage },
    { path: '/test', component: TestPage },
    { path: '/testLogin', component: TestLogin },
]


const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;