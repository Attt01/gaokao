drop table if exists `tb_user_form`;
CREATE TABLE `tb_user_form`
(
    id             bigint auto_increment not null comment '主键',
    user_id        bigint      not null default 0 comment '用户Id',
    name           varchar(20) not null default '' comment '志愿表名称',
    score          int         not null default 0 comment '用户分数',
    subject        varchar(10) not null default '' comment '用户的选课信息',
    generated_type tinyint     not null default 0 comment '生成类型，默认是手动生成',
    is_current     tinyint     not null default 0 comment '是否为当前表',
    generated_time bigint      not null default 0 comment '生成时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';

drop table if exists `tb_form_volunteer`;
CREATE TABLE `tb_form_volunteer`
(
    id             bigint auto_increment not null comment '主键',
    form_id       bigint not null default 0 comment '对应志愿表id',
    volunteer_position          tinyint not null default 0 comment '第几条志愿, 取值为1-96',
    volunteer_id  bigint not null default 0 comment '对应的志愿id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '志愿表 与 志愿的对应';


