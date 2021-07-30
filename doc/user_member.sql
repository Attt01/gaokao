DROP TABLE IF EXISTS `tb_user_member`;
CREATE TABLE `tb_user_member`
(
    id              bigint auto_increment not null comment '主键',
    phone           varchar(11)  not null default '' comment '手机号',
    username        varchar(64)  not null default '' comment '用户名',
    password        varchar(64)  not null default '' comment '密码',
    nickname        varchar(32)  not null default '' comment '昵称',
    is_vip          tinyint(3)  not null default 0 comment '是否是vip，0为否，1为是',
    vip_expiration_time bigint  not null default 0 comment 'vip到期时间',
    status          tinyint(3) not null default 0 comment '用户状态, 0为正常, 1为锁定',
    score           bigint(20) not null default 0 comment '用户分数',
    province_rank   bigint(20) not null default 0 comment '全省排名',
    subject         varchar(10)  not null default 0 comment '选考科目: 1;4;6',
    wx_open_id      varchar(128) not null default '' comment '微信登录openId',
    wx_s_key        varchar(128) not null default '' comment '微信登录sKey',
    wx_session_key  varchar(128) not null default '' comment '微信登录sessionKey',
    wx_avatar_url   varchar(256) not null default '' comment '微信登录头像地址',
    last_visit_time bigint(20) not null default 0 comment '微信登录时间',
    create_time     bigint(20) not null default 0 comment '注册时间',
    update_time     bigint(20) not null default 0 comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '会员表';