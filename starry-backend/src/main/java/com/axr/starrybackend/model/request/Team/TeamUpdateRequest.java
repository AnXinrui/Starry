package com.axr.starrybackend.model.request.Team;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamUpdateRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 队伍id
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
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expireTime;


    /**
     * 状态 [0: 公开， 1：加密]
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;
}
