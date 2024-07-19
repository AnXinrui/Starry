package com.axr.starrybackend.model.vo.User;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserVO implements Serializable {
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
     * 标签 JSON 列表
     */
    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
