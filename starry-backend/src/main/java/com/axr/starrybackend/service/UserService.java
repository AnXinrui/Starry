package com.axr.starrybackend.service;

import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.request.User.UserLoginRequest;
import com.axr.starrybackend.model.request.User.UserRegisterRequest;
import com.axr.starrybackend.model.vo.User.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author axr
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-07-17 21:41:20
*/
public interface UserService extends IService<User> {

    Long userRegister(UserRegisterRequest userRegisterRequest);

    UserVO doLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    UserVO getSafetyUser(User user);

    boolean updateUser(User user, User loginUser);

    User getLoginUser(HttpServletRequest request);

    List<UserVO> searchUsers(String keyword);

    List<UserVO> recommendUsers(User currentUser);
}
