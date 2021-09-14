drop table if exists `tb_user_star`;
CREATE TABLE `tb_user_star`
(
    id             bigint auto_increment not null comment '主键',
    user_id        bigint      not null default 0 comment '用户Id',
    stars          varchar(100)      not null default '' comment '用户收藏的志愿id列表，格式为[1,2,3]',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4 comment '用户收藏的志愿表';