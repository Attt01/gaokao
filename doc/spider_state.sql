create table spider_state
(
    id         bigint auto_increment not null comment '主键',
    start_time bigint  not null default -1 comment '开始时间',
    end_time   bigint  not null default -1 comment '结束时间',
    state      tinyint not null default -1 comment '状态码 0 表示正在运行 1 表示已经结束',
    primary key (id)
) engine = InnoDB default charset = utf8mb4 comment '爬虫状态表';
  