package com.axr.starrybackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 博客评论
 * @TableName blog_comments
 */
@TableName(value ="blog_comments")
@Data
public class BlogComments implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 博客id
     */
    private Long blog_id;

    /**
     * 回复的内容
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}