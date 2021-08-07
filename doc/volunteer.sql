drop table if exists `tb_user_volunteer`;
CREATE TABLE `tb_user_volunteer`
(
    id             bigint auto_increment not null comment '主键',
    user_id        bigint  not null default 0 comment '用户Id',
    form_id        int     not null default 0 comment '志愿表Id',
    volunteer_list text comment '志愿id。1,2;4,66;表示第一个志愿的id是2，第4个志愿的id是66',
    generated_type tinyint not null default 0 comment '生成类型，默认是手动生成',
    is_current     tinyint not null default 0 comment '是否为当前表',
    generated_time bigint  not null default 0 comment '生成时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';


drop table if exists `tb_volunteer`;
CREATE TABLE `tb_volunteer`
(
    id             bigint auto_increment not null comment '主键',
    school_id      varchar(10) default '' comment '学校id',
    major_plan_id  varchar(10) default '' comment '专业招生计划id',
    major_score_id varchar(40) default '' comment '专业分数id',
    code           varchar(10) default '' comment '招生代码',
        PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';
