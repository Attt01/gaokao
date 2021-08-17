drop table if exists `tb_volunteer_form`;
CREATE TABLE `tb_volunteer_form`
(
    id             bigint auto_increment not null comment '主键',
    user_id        bigint      not null default 0 comment '用户Id',
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

/*
  id userId formId volunteerId index
 */

drop table if exists `tb_volunteer`;
CREATE TABLE `tb_volunteer`
(
    id                                bigint auto_increment not null comment '主键',
    name                              varchar(11)  not null default '' comment '高校名称',
    university_code                   varchar(11)  not null default '' comment '高校代码',
    province                          varchar(20)  not null default '' comment '所在省份',
    address                           varchar(101) not null default '' comment '详细地址',
    is_undergraduate_school           tinyint(3) not null default 0 comment '是否是本科; 1表示是本科',
    is_junior_school                  tinyint(3) not null default 0 comment '是否是专科(高职); 1表示是，0表示不是',
    is_985                            tinyint(3) not null default 0 comment '是否是985; 1表示是',
    is_211                            tinyint(3) not null default 0 comment '是否是211',
    is_public                         tinyint(3) not null default 0 comment '是否是公办, 1表示是',
    is_private                        tinyint(3) not null default 0 comment '是否是民办, 1表示是',
    category                          varchar(11)  not null default '' comment '学校分类。理工/师范/综合',
    pic_link                          VARCHAR(601) not null default '' COMMENT '校徽链接',

    employment_rate                   VARCHAR(255) not null default 0 COMMENT '就业率',
    abroad_rate                       VARCHAR(255) not null default 0 COMMENT '出国率',
    further_rate                      VARCHAR(255) not null default 0 COMMENT '考研率',


    lowest_score_one                  int          not null default 0 COMMENT '一段录取最低分',
    lowest_position_one               int          not null default 0 COMMENT '一段录取最低位次',
    professional_name_one             VARCHAR(255) COMMENT '一段专业名称',
    subject_restriction_type_one      tinyint(3) not null default 0 comment '选课要求类型',
    subject_restriction_detail_one    varchar(20)  not null default 0 comment '具体要求了那几科 1;4;5',
    major_code_one                    varchar(10)  not null default '' comment '招生代码',
    score_one                         int          not null default 0 COMMENT '专业最低分',
    position_one                      bigint(20) not null default 0 COMMENT '专业最低位次',
    enrollment_one                    int          not null default 0 COMMENT '录取人数',
    time_one                          tinyint(3) not null default 0 COMMENT '学制即学习时间',
    fee_one                           int          not null default 0 COMMENT '学费',


    lowest_score_two                  int          not null default 0 COMMENT '二段录取最低分',
    lowest_position_two               int          not null default 0 COMMENT '二段录取最低位次',
    professional_name_two             VARCHAR(255) COMMENT '二段专业名称',
    subject_restriction_type_two      tinyint(3) not null default 0 comment '选课要求类型',
    subject_restriction_detail_two    varchar(20)  not null default 0 comment '具体要求了那几科 1;4;5',
    major_code_two                    varchar(10)  not null default '' comment '招生代码',
    score_two                         int          not null default 0 COMMENT '专业最低分',
    position_two                      bigint(20) not null default 0 COMMENT '专业最低位次',
    enrollment_two                    int          not null default 0 COMMENT '录取人数',
    time_two                          tinyint(3) not null default 0 COMMENT '学制即学习时间',
    fee_two                           int          not null default 0 COMMENT '学费',


    double_first_class_subject_number int          not null default 0 comment '双一流学科数',
    country_specific_subject_number   int          not null default 0 comment '国家特色专业数',
    master_point                      int          not null default 0 comment '硕士点',
    doctor_point                      int          not null default 0 comment '博士点',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';
