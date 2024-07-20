package com.axr.starrybackend.model.request.User;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {
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
     * 标签 JSON 列表
     */
    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
