drop table if exists `tb_minimum_score`;
CREATE TABLE `tb_minimum_score`
(
    id bigint auto_increment not null comment '主键',
    university_id bigint(20) not null default 0 comment '对应大学id',
    year int not null default 0 comment '年份',
    batch varchar(11) not null default 0 comment '录取批次',
    type int not null default 0 comment '招生类型',
    lowest_score int not null default 0 comment '最低分数',
    lowest_rank int not null default 0 comment '最低位次',
    standard_lowest_score int not null default 0 comment '省控线',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '不同批次最低分表';

drop table if exists `tb_recruit_plan`;
CREATE TABLE `tb_recruit_plan`
(
    id bigint auto_increment not null comment '主键',
    year int not null default 0 comment '年份',
    university_id bigint(20) not null default 0 comment '对应高校id',
    major_name varchar(32) not null default '' comment '专业名称',
    subject_category varchar(32) not null default '' comment '学科门类',
    recruit_number bigint(20) not null default 0 comment '计划招生数量',
    schooling_time tinyint(3) not null default 0 comment '学制;即毕业所需时间3年或4年',
    subject_restriction_type tinyint(3) not null default 0 comment '选课要求类型',
    subject_restriction_detail varchar(20) not null default 0 comment '具体要求了那几科 1;4;5',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4 comment '招生计划表';

drop table if exists `tb_recruit_score`;
CREATE TABLE `tb_recruit_score`
(
    id bigint auto_increment not null comment '主键',
    year int not null default 0 comment '年份',
    university_id bigint(20) not null default 0 comment '对应高校id',
    major_name varchar(32) not null default '' comment '专业名称',
    batch int not null default 0 comment '录取批次',
    average_score int not null default 0 comment '平均分',
    lowest_score int not null default 0 comment '最低分',
    lowest_rank int not null default 0 comment '最低位次',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4 comment '专业分数线表';