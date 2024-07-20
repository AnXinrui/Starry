import { defineStore } from 'pinia';
import { ref } from 'vue';
import request from '../utils/http';
import {UserType} from "../modules/user";

interface UserResponse {
    code: number;
    data: any; // 具体类型根据你的实际返回数据结构定义
    [key: string]: any; // 如果有其他字段
}

export const useUserStore = defineStore('user', () => {
    const user = ref<UserType | null>(null);

    // 获取当前用户
    const getCurrentUser = async (): Promise<UserResponse | null> => {
        // 如果用户已存在，直接返回
        if (user.value) {
            return;
        }

        // 用户不存在时，发起请求获取用户信息
        const res = await request.get('/user/current');
        //@ts-ignore
        if (res.code === 0) {
            user.value = res.data;
        }
    };

    return {
        user,
        getCurrentUser
    };
});
