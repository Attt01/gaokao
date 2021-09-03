drop table if exists `tb_guess_rank`;
CREATE TABLE `tb_guess_rank`
(
    id                    bigint auto_increment not null  comment '主键',
    university_id         bigint(20)  not null default 0  comment '大学id',
    major_code            varchar(10) not null default '' comment '招生代码',
    guess_rank            int         not null default 0  comment '预测今年最低位次',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4 comment '预测本年最低位次表';