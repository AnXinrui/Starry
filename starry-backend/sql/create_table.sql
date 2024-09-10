-- 创建数据库
CREATE DATABASE IF NOT EXISTS Starry CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 使用数据库
USE Starry;

-- 创建 user 表
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(30) DEFAULT '新用户' COMMENT '昵称',
                      gender TINYINT DEFAULT 0 COMMENT '性别 0：男 1：女',
                      userAccount VARCHAR(30) NULL COMMENT '账号',
                      userPassword VARCHAR(64) NOT NULL COMMENT '密码',
                      profile VARCHAR(500) NULL COMMENT '用户个人介绍',
                      avatarUrl VARCHAR(1024) DEFAULT 'https://cdn.acwing.com/media/user/profile/photo/392759_lg_0dd0693b9b.png' COMMENT '头像',
                      phone VARCHAR(30) NULL COMMENT '电话号码',
                      email VARCHAR(128) NULL COMMENT '邮箱',
                      userStatus INT DEFAULT 0 COMMENT '0: 正常',
                      rating INT DEFAULT 0 COMMENT '积分',
                      createTime DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
                      updateTime DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除(逻辑删除)',
                      userRole TINYINT DEFAULT 0 NULL COMMENT '用户角色 0：普通用户 1：管理员',
                      tags VARCHAR(1024) NULL COMMENT '标签 JSON 列表'
) COMMENT '用户表';

-- 创建 team 表
CREATE TABLE team (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
                      name VARCHAR(30) NOT NULL COMMENT '队伍名称',
                      description VARCHAR(124) NULL COMMENT '描述',
                      maxNum INT DEFAULT 5 NULL COMMENT '最大人数',
                      expireTime DATETIME NULL COMMENT '过期时间',
                      userId BIGINT NULL COMMENT '创建用户id',
                      username    varchar(50)   null comment '创建用户用户名',
                      status INT NULL COMMENT '状态 [0: 公开， 1：加密]',
                      password VARCHAR(30) NULL COMMENT '密码',
                      createTime DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
                      updateTime DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT '队伍表';

-- 创建 user_team 表
CREATE TABLE user_team (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                           userId BIGINT NULL COMMENT '用户id',
                           teamId BIGINT NULL COMMENT '队伍id',
                           expireTime DATETIME NULL COMMENT '过期时间',
                           joinTime DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '加入时间',
                           isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT='用户队伍关系';

-- 创建 blog 表
CREATE TABLE blog (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                      user_id BIGINT NOT NULL COMMENT '用户id',
                      images VARCHAR(300) NOT NULL COMMENT '图片',
                      content VARCHAR(300) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '探店的文字描述',
                      liked INT DEFAULT 0 NULL COMMENT '点赞数量',
                      comments INT NULL COMMENT '评论数量'
) COLLATE = utf8mb4_general_ci COMMENT='博客';

-- 创建 blog_comments 表
CREATE TABLE blog_comments (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                               user_id BIGINT NOT NULL COMMENT '用户id',
                               blog_id BIGINT NOT NULL COMMENT '博客id',
                               content VARCHAR(255) NOT NULL COMMENT '回复的内容'
) COLLATE = utf8mb4_general_ci COMMENT='博客评论';
