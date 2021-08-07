drop table if exists `tb_user_volunteer`;
CREATE TABLE `tb_user_volunteer`
(
    id             bigint auto_increment not null comment '主键',
    user_id        bigint      not null default 0 comment '用户Id',
    form_id        int         not null default 0 comment '志愿表Id',
    name           varchar(20) not null default '' comment '志愿表名称',
    score          int         not null default 0 comment '用户分数',
    subject        varchar(10) not null default '' comment '用户的选课信息',
    volunteer_list text comment '志愿id。[2, 0, 0, 66];表示第一个志愿的id是2，第4个志愿的id是66',
    generated_type tinyint     not null default 0 comment '生成类型，默认是手动生成',
    is_current     tinyint     not null default 0 comment '是否为当前表',
    generated_time bigint      not null default 0 comment '生成时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';


drop table if exists `tb_volunteer`;
CREATE TABLE `tb_volunteer`
(
    id                         bigint auto_increment not null comment '主键',
    university_code            varchar(11) not null default '' comment '高校代码',
    name                       varchar(11) not null default '' comment '高校名称',
    province                   varchar(11) not null default '' comment '所在省份',
    city                       varchar(11) not null default '' comment '市',
    level                      varchar(11) not null default '' comment '学校档次: 985/211/一本',
    category                   varchar(11) not null default '' comment '学校分类。理工/师范/综合',


    major_name                 varchar(32) not null default '' comment '专业名称',
    major_code                 varchar(10) not null default '' comment '招生代码',
    fee                        int         not null default 0 comment '每年学费',
    recruit_number             bigint(20) not null default 0 comment '计划招生数量',
    schooling_time             tinyint(3) not null default 0 comment '学制;即毕业所需时间3年或4年',
    subject_restriction_type   tinyint(3) not null default 0 comment '选课要求类型',
    subject_restriction_detail varchar(20) not null default 0 comment '具体要求了那几科 1;4;5',

    lowest_score               int         not null default 0 comment '上一年最低分数',
    lowest_rank                int         not null default 0 comment '上一年最低位次',

--     school_id      varchar(10) default '' comment '学校id',
--     major_plan_id  varchar(10) default '' comment '专业招生计划id',
--     major_score_id varchar(40) default '' comment '专业分数id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';
