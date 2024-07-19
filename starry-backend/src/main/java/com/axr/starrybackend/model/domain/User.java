package com.axr.starrybackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@Data
@TableName(value ="user")
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 性别 0：男 1：女
     */
    private Integer gender;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户个人介绍
     */
    private String profile;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0: 正常
     */
    private Integer userStatus;

    /**
     * 积分
     */
    private Integer rating;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除(逻辑删除)
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0：普通用户 1：管理员
     */
    private Integer userRole;

    /**
     * 标签 JSON 列表
     */
    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}