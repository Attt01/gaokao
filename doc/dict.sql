drop table if exists `tb_dict_one`;
CREATE TABLE `tb_dict_one`
(
    id                    int auto_increment not null  comment '主键',
    value                  varchar(10) not null comment '值',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4 comment '字典表第一层';

drop table if exists `tb_dict_two`;
CREATE TABLE `tb_dict_two`
(
    id                    int auto_increment not null  comment '主键',
    upid                  int not null comment '上层id',
    value                  varchar(50) not null comment '值',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4 comment '字典表第二层';

drop table if exists `tb_dict_three`;
CREATE TABLE `tb_dict_three`
(
    id                    int auto_increment not null  comment '主键',
    upid                  int not null comment '上层id',
    value                  varchar(50) not null comment '值',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4 comment '字典表第三层';