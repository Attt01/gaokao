drop table if exists `tb_university`;
CREATE TABLE `tb_university`
(
    id               bigint auto_increment not null comment '主键',
    university_code  varchar(11) not null default '' comment '高校代码',
    name             varchar(11) not null default '' comment '高校名称',
    category         varchar(11) not null default '' comment '学校分类。理工/师范/综合',
    province         varchar(11) not null default '' comment '所在省份',
    city             varchar(11) not null default '' comment '市',
    zone             varchar(11) not null default '' comment '区/县',
    address          varchar(11) not null default '' comment '详细地址',
    official_website varchar(60) not null default '' comment '官方网址',
    official_phone   varchar(60) not null default '' comment '官方电话: 可能有多个，以逗号分割',
    official_email   varchar(60) not null default '' comment '官方邮箱',
    level            varchar(11) not null default '' comment '学校档次: 985/211/一本',
    popularity_value bigint(20) not null default 0 comment '人气值',
    status           tinyint(3) not null default 0 comment '状态 0为显示 1为不显示',
    create_time      bigint(20) not null default 0 comment '注册时间',
    creator          varchar(32) not null default 0 comment '创建者',
    update_time      bigint(20) not null default 0 comment '更新时间',
    updater          varchar(32) not null default 0 comment '更新者',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '高校信息表';



drop table if exists `tb_university_detail`;
CREATE TABLE `tb_university_detail`
(
    id                        bigint auto_increment not null comment '主键',
    information               text comment '学校详细介绍',
    study_score               double       not null default 0 comment '学习指数',
    environment_score         double       not null default 0 comment '生活指数',
    employment_score          double       not null default 0 comment '就业指数',
    average_score             double       not null default 0 comment '综合评分',
    thumbnail                 varchar(200) not null default '' comment '学校缩略图//存在本地',
    phd_first_level_point     bigint(20) not null default 0 comment '一级博士点',
    phd_second_level_point    bigint(20) not null default 0 comment '二级博士点',
    master_first_level_point  bigint(20) not null default 0 comment '一级硕士点',
    master_second_level_point bigint(20) not null default 0 comment '二级硕士点',
    national_key_disciplines  bigint(20) not null default 0 comment '国家重点学科数量',
    national_key_laboratories bigint(20) not null default 0 comment '国家重点实验室',
    university_create_time    bigint(20) not null default 0 comment '建校时间',
    area_covered              bigint(20) not null default 0 comment '占地面积',
    boy_rate                  double       not null default 0 comment '男生占比，女生占比可以计算',
    employment_rate           double       not null default 0 comment '就业率',
    domestic_enrollment_rate  double       not null default 0 comment '国内升学率',
    abroad_rate               double       not null default 0 comment '出国率',
    create_time               bigint(20) not null default 0 comment '注册时间',
    creator                   varchar(32)  not null default 0 comment '创建者',
    update_time               bigint(20) not null default 0 comment '更新时间',
    updater                   varchar(32)  not null default 0 comment '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '高校详细信息表';

drop table if exists `tb_university_category`;
CREATE TABLE `tb_university_category`
(
    id            bigint auto_increment not null comment '主键',
    university_id bigint(20) not null comment '高校id',
    name          varchar(32) not null comment '大类名称',
    create_time   bigint(20) not null default 0 comment '注册时间',
    creator       varchar(32) not null default 0 comment '创建者',
    update_time   bigint(20) not null default 0 comment '更新时间',
    updater       varchar(32) not null default 0 comment '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '高校大类表';


drop table if exists `tb_university_major`;
CREATE TABLE `tb_university_major`
(
    id            bigint auto_increment not null comment '主键',
    category_id   bigint(20) not null default 0 comment '所属类别id',
    university_id bigint(20) not null default 0 comment '高校id',
    name          varchar(32) not null default 0 comment '专业名称',
    detail        text comment '详细信息',
    create_time   bigint(20) not null default 0 comment '注册时间',
    creator       varchar(32) not null default 0 comment '创建者',
    update_time   bigint(20) not null default 0 comment '更新时间',
    updater       varchar(32) not null default 0 comment '更新者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '高校专业表';

