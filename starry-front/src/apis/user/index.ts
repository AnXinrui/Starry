import {loginFormData} from "./type";
import request from "@/utils/request";
import {BaseResponse} from '../BaseResponse';
import {UserType} from './type'

enum API {
    LOGIN_URL = '/user/login',
    USERINFO_URL = '/user/current',
    LOGOUT_URL = '/user/logout',
}

// 登录接口
export const reqLogin = (data: loginFormData) =>
    request.post<any, BaseResponse>(API.LOGIN_URL, data);

export const reqUserInfo = () =>
    request.get<any, BaseResponse>(API.USERINFO_URL)

export const reqLogout = () =>
    request.post<any, BaseResponse>(API.LOGOUT_URL)