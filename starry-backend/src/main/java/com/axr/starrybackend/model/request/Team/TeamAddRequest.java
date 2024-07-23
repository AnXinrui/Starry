package com.axr.starrybackend.model.request.Team;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TeamAddRequest implements Serializable {
    private static final long serialVersionUID = 1L;
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
