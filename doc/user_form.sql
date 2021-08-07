drop table if exists `tb_user_form`;
CREATE TABLE `tb_user_form`
(
    id                      bigint               not null auto_increment comment '主键',
    user_id                 bigint               not null default 0 comment '用户Id',
    form_id                 int                  not null default 0 comment '志愿表Id',
    item_id                 int                  not null default 1 comment '表项id，1-96',
    user_name               varchar(40)                   default '' comment '用户姓名',
    admission_num           varchar(14)                   default '' comment '准考证号',
    school_id               varchar(10)                   default '' comment '学校代码',
    school_name             varchar(40)                   default '' comment '学校名字',
    major_id                varchar(10)                   default '' comment '专业代码',
    major_name              varchar(40)                   default '' comment '专业名字',
    level                   varchar(4)                    default '' comment '本科/专科',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';