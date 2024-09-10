package com.axr.starrybackend.service.impl;

import com.axr.starrybackend.common.ErrorCode;
import com.axr.starrybackend.exception.BusinessException;
import com.axr.starrybackend.mapper.UserMapper;
import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.request.User.UserLoginRequest;
import com.axr.starrybackend.model.request.User.UserRegisterRequest;
import com.axr.starrybackend.model.vo.User.UserVO;
import com.axr.starrybackend.service.UserService;
import com.axr.starrybackend.utils.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.axr.starrybackend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author axr
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-07-17 21:41:20
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 加密盐
     */
    public static final String SALT = "STARRY_BACKEND_2024";

    @Override
    public Long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getUserPassword();
        String confirmPassword = userRegisterRequest.getConfirmPassword();
        if (StringUtils.isAnyBlank(userAccount, password, confirmPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容不能为空哦~");
        }
        if (userAccount.length() < 3) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度不能小于3位！");
        } else if (userAccount.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度不能大于30位！");
        }
        if (password.length() < 6 || confirmPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于6位！");
        } else if (password.length() > 50 || confirmPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能大于20位！");
        }
        // 账号不包含特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含特殊字符哦~");
        }
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致哦~");
        }

        // 判断账号是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该账号已存在，请重新输入！");
        }

        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);

        boolean result = this.save(user);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，请稍后再试~");
        }
        return user.getId();
    }

    @Override
    public UserVO doLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (userAccount.length() < 3 || userAccount.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度不符合要求！");
        }
        if (userPassword.length() < 6 || userPassword.length() > 20) {
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不符合要求！");
    }
        // 账号不包含特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含特殊字符哦~");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误!");
        }

        UserVO safetyUser = this.getSafetyUser(user);

        // 将用户信息存入 httpsession
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    /**
     * 脱敏
     * @param user
     * @return
     */
    @Override
    public UserVO getSafetyUser(User user) {
        if (user == null) {
            return null;
        }
        UserVO safetyUser = new UserVO();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setGender(user.getGender());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setProfile(user.getProfile());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setRating(user.getRating());
        safetyUser.setCreateTime(LocalDateTime.now());
        safetyUser.setUpdateTime(LocalDateTime.now());
        safetyUser.setTags(user.getTags());
        return safetyUser;
    }

    @Override
    public boolean updateUser(User user, User loginUser, HttpServletRequest request) {
        Long userId = user.getId();
        if (!userId.equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean result = this.updateById(user);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败，请稍后再试~");
        }
        user = this.getById(userId);
        UserVO safetyUser = getSafetyUser(user);
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return result;
    }

    /**
     * 获取登录用户
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        UserVO userVO = (UserVO) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userVO == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        return user;
    }

    /**
     * 根据关键词搜索用户（列表）
     * @param keyword
     * @return
     */
    @Override
    public List<UserVO> searchUsers(String keyword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(User::getUsername, keyword)
                .or()
                .like(User::getTags, keyword)
                .or()
                .like(User::getProfile, keyword);
        List<User> list = this.list(queryWrapper);
        return list.stream().map(this::getSafetyUser).toList();
    }

    /**
     * 用户推荐
     *
     * @param currentUser
     * @return
     */
    @Override
    public List<UserVO> recommendUsers(User currentUser) {
//        if (StringUtils.isEmpty(currentUser.getTags())) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户标签为空~");
//        }

        Set<String> currentUserTags = Algorithm.parseTags(currentUser.getTags());

        // 获取所有用户（排除当前用户）
        List<User> allUsers = list(new QueryWrapper<User>().ne("id", currentUser.getId()));

        // 计算每个用户与当前用户的标签相似度
        Map<User, Integer> userSimilarityMap = new HashMap<>();
        for (User user : allUsers) {
            Set<String> userTags = Algorithm.parseTags(user.getTags());
            int similarity = Algorithm.calculateSimilarity(currentUserTags, userTags);
            userSimilarityMap.put(user, similarity);
        }

        // 排序并取前10
        List<User> userList = userSimilarityMap.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        List<UserVO> list = userList.stream().map(this::getSafetyUser).toList();
        return list;
    }
}




