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
