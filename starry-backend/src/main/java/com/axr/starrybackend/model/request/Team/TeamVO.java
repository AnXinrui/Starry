package com.axr.starrybackend.model.request.Team;

import com.axr.starrybackend.model.vo.User.UserVO;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TeamVO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;


    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 创建用户id
     */
    private Long userId;

    /**
     * 状态 0: 公开， 1：加密
     */
    private Integer status;

    /**
     * 创建人用户信息
     */
    // private UserVO createUser;
    private String username;
    /**
     * 是否已经加入
     */
    private Boolean hasJoin;

    /**
     * 已经加入的人数
     */
    private Integer hasJoinNum;
}
