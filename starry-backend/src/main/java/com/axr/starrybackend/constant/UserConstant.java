package com.axr.starrybackend.constant;

/**
 * 用户常量
 *
 * @author axr
 */
public interface UserConstant {
    /**
     * 用户登录态
     */
    String USER_LOGIN_STATE = "userLoginState";

    // ------------------ 权限 ------------------
    /**
     * 管理员
     */
    int ADMIN_ROLE = 1;
    /**
     * 普通用户
     */
    int DEFAULT_ROLE = 0;
}