drop table if exists `tb_filter_conditions`;
CREATE TABLE `tb_filter_conditions`
(
    id                    int auto_increment not null  comment '主键,从1开始',
    level                 int not null comment '所处层数,从1开始',
    father_id             int not null comment '父亲id,第一层父id为0',
    label                 varchar(50) not null comment '筛选条件的具体值',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4 comment '筛选条件表';