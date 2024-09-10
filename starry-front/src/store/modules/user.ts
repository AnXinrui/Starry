import {defineStore} from "pinia";
import {UserState} from "../types/type";
import {loginFormData} from "@/apis/user/type";
import {BaseResponse} from "@/apis/BaseResponse";
import {reqLogin} from "@/apis/user/index";
import {reqUserInfo} from "@/apis/user/index";
import {reqLogout} from "@/apis/user/index";


const useUserStore = defineStore('user', {
        state: () => {
            return {
                user: null as UserState | null,
            }
        },
        actions: {
            // 登录
            async userLogin(data: loginFormData) {
                const result: BaseResponse = await reqLogin(data);
                if (result.code == 0) {
                    return 'ok';
                } else {
                    return Promise.reject(new Error(result.description));
                }
            },

            // 获取当前用户信息
            async getCurrentUserInfo() {
                const result: BaseResponse = await reqUserInfo();
                if (result.code == 0) {
                    this.user = result.data;
                    return 'ok';
                }
                else {
                    return Promise.reject(new Error(result.description))
                }
            },

            // 注销
            async userLogout() {
                const result: BaseResponse = await reqLogout();
                if (result.code == 0) {
                    this.user = null;
                    return 'ok';
                }
                else {
                    return Promise.reject(new Error(result.description))
                }
            }
        }
    }
)

export default useUserStore;