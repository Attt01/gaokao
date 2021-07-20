drop table if exists `tb_sys_user`;
CREATE TABLE `tb_sys_user`
(
    id          bigint auto_increment not null comment '主键',
    corp        bigint(20)            not null default 0 comment '所属公司、商家',
    username    varchar(11)           not null default '' comment '用户名',
    phone       varchar(11)           not null default '' comment '手机号',
    password    varchar(64)           not null default '' comment '密码',
    `status`    tinyint(3)            not null default 0 comment '状态',
    create_time bigint(20)            not null default 0 comment '注册时间',
    creator     varchar(32)           not null default 0 comment '创建者',
    update_time bigint(20)            not null default 0 comment '更新时间',
    updater     varchar(32)           not null default 0 comment '更新者',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_corp` (`corp`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '系统用户表';


drop table if exists tb_sys_role;
CREATE TABLE `tb_sys_role`
(
    id          bigint auto_increment not null comment '主键',
    corp        bigint(20)            not null default 0 comment '所属公司、商家',
    name        varchar(32)           not null default '' comment '角色名称',
    `status`    tinyint(3)            not null default 0 comment '状态',
    create_time bigint(20)            not null default 0 comment '创建时间',
    creator     varchar(32)           not null default 0 comment '创建者',
    update_time bigint(20)            not null default 0 comment '更新时间',
    updater     varchar(32)           not null default 0 comment '更新者',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_corp_name` (`corp`, `name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '系统角色表';


drop table if exists tb_sys_perm;
CREATE TABLE `tb_sys_perm`
(
    id          bigint      not null comment '主键',
    pid         bigint      not null default 0 comment '父权限Id',
    code        varchar(20) not null default '' comment '权限编码',
    name        varchar(32) not null default '' comment '权限名称',
    create_time bigint(20)  not null default 0 comment '创建时间',
    creator     varchar(32) not null default 0 comment '创建者',
    update_time bigint(20)  not null default 0 comment '更新时间',
    updater     varchar(32) not null default 0 comment '更新者',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`),
    key `idx_pid` (`pid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '系统权限表';

drop table if exists `tb_sys_role_perm`;
CREATE TABLE `tb_sys_role_perm`
(
    id      bigint auto_increment not null comment '主键',
    role_id bigint(20)            not null default 0 comment '角色Id',
    perm_id bigint(20)            not null default 0 comment '权限Id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_perm` (`role_id`, `perm_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '角色权限表';

drop table if exists `tb_sys_user_role`;
CREATE TABLE `tb_sys_user_role`
(
    id      bigint auto_increment not null comment '主键',
    user_id bigint(20)            not null default 0 comment '用户Id',
    role_id bigint(20)            not null default 0 comment '角色Id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户角色表';