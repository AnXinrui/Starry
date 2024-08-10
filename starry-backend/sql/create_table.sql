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
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment'更新时间',
    isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除(逻辑删除)',
    userRole TINYINT DEFAULT 0 NULL COMMENT '用户角色 0：普通用户 1：管理员',
    tags VARCHAR(1024) NULL COMMENT '标签 JSON 列表'
) COMMENT '用户表';



create table team
(
    id          bigint auto_increment           primary key comment 'ID',
    name        varchar(30)                       not null comment '队伍名称',
    description varchar(124)                      null comment '描述',
    maxNum      int      default 5                 null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint                             null comment '创建用户id',
    status      int                                null comment '状态 [0: 公开， 1：加密]',
    password    varchar(30)                       null comment '密码',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment'更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)   comment '队伍表';



CREATE TABLE user_team (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    userId BIGINT NULL COMMENT '用户id',
    teamId BIGINT NULL COMMENT '队伍id',
    expireTime DATETIME NULL COMMENT '过期时间',
    joinTime DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '加入时间',
    isDelete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT='用户队伍关系';


create table blog
(
    id          bigint unsigned auto_increment comment '主键'
        primary key,
    shop_id     bigint                                   not null comment '商户id',
    user_id     bigint unsigned                          not null comment '用户id',
    title       varchar(255) collate utf8mb4_unicode_ci  not null comment '标题',
    images      varchar(2048)                            not null comment '探店的照片，最多9张，多张以","隔开',
    content     varchar(2048) collate utf8mb4_unicode_ci not null comment '探店的文字描述',
    liked       int unsigned default '0'                 null comment '点赞数量',
    comments    int unsigned                             null comment '评论数量',
    create_time timestamp    default CURRENT_TIMESTAMP   not null comment '创建时间',
    update_time timestamp    default CURRENT_TIMESTAMP   not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    collate = utf8mb4_general_ci;