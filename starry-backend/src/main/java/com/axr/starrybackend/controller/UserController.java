package com.axr.starrybackend.controller;

import com.axr.starrybackend.common.BaseResponse;
import com.axr.starrybackend.common.ErrorCode;
import com.axr.starrybackend.common.ResultUtils;
import com.axr.starrybackend.exception.BusinessException;
import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.request.User.UserLoginRequest;
import com.axr.starrybackend.model.request.User.UserRegisterRequest;
import com.axr.starrybackend.model.request.User.UserUpdateRequest;
import com.axr.starrybackend.model.vo.User.UserVO;
import com.axr.starrybackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.axr.starrybackend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author axr
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", originPatterns = "*", allowCredentials = "true")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 用户注册
     * @param userRegisterRequest
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long result = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        log.info("用户登录: 账户{}", userLoginRequest.getUserAccount());
        UserVO user = userService.doLogin(userLoginRequest, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Long> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "未登录");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(1L);
    }

    @GetMapping("/current")
    public BaseResponse<UserVO> getCurrentUser(HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVO userVO = (UserVO) userObject;
        if (userVO == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        // TODO 校验用户是否合法
        Long userId = userVO.getId();
        User user = userService.getById(userId);
        userVO = userService.getSafetyUser(user);
        return ResultUtils.success(userVO);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        if (userUpdateRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateUser(user, loginUser, request);
        return ResultUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<UserVO>> searchUsers(@RequestParam String keyword) {
        if (StringUtils.isBlank(keyword.trim())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "搜索关键词不能为空");
        }
        return ResultUtils.success(userService.searchUsers(keyword));
    }

    @GetMapping("/recommend")
    public BaseResponse<List<UserVO>> recommendUsers(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        // 如果有缓存， 直接读取缓存
        String redisKey = String.format("starry:user:recommend:%s", loginUser.getId());
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<UserVO> userVOList = (List<UserVO>) redisTemplate.opsForValue().get(redisKey);
        if (userVOList != null) {
            return ResultUtils.success(userVOList);
        }
        userVOList = userService.recommendUsers(loginUser);
        try {
            valueOperations.set(redisKey, userVOList, 6, TimeUnit.HOURS); // 内存淘汰
        } catch (Exception e) {
            log.error("redis key error: {}", e);
        }
        return ResultUtils.success(userVOList);
    }
}
