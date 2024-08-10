import request from '@/utils/request';
import {showFailToast} from "vant";
import {useUserStore} from "../store/userStore";
import router from "@/config/router";



interface LoginParams {
    userAccount: string;
    userPassword: string;
}

export const loginAPI = ({ userAccount, userPassword }: LoginParams) => {
    return request({
        url: '/user/login',
        method: 'POST',
        data: {
            userAccount,
            userPassword,
        }
    })
}

interface RegisterParams {
    userAccount: string;
    userPassword: string;
    confirmPassword: string;

}

export const registerAPI = ({ userAccount, userPassword, confirmPassword }: RegisterParams) => {
    return request({
        url: '/user/register',
        method: 'POST',
        data: {
            userAccount,
            userPassword,
            confirmPassword,
        }
    })
}

interface UserResponse {
    code: number;
    data: any; // 具体类型根据你的实际返回数据结构定义
    [key: string]: any; // 如果有其他字段
}

export const getCurrentUser = async (): Promise<UserResponse | null> => {
    const res: UserResponse = await request.get('/user/current');
    if (res.code === 0) {
        return res;
    } else {
        showFailToast('获取用户信息失败！')
    }
    return null;
    // const userStore = useUserStore();
    // await useUserStore();
    // if (!userStore.user) {
    //     router.push('/user/login');
    //     return;
    // }
};

