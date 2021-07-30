drop table if exists `tb_order`;
CREATE TABLE `tb_order`
(
id BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单id',
user_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户id',
total_price INT NOT NULL DEFAULT 0 COMMENT '总金额',
real_price INT NOT NULL DEFAULT 0 COMMENT '实际付费',
create_time BIGINT(20) NOT NULL COMMENT '订单创建时间',
out_trade_no VARCHAR(60) NOT NULL COMMENT '对外订单号',
pay_type TINYINT(3) NOT NULL COMMENT '支付方式',
third_pay_sn VARCHAR(64) CHARSET dec8 NOT NULL COMMENT '第三方支付流水号',
note VARCHAR(1000) NOT NULL COMMENT '订货单备注',
`status` TINYINT(3) NOT NULL DEFAULT 0 COMMENT '状态',
PRIMARY KEY (`id`)
) ENGINE=INNODB
  DEFAULT CHARSET = utf8mb4 comment '订单信息表';

CREATE TABLE `tb_order_pay`
(
    `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付单id' ,
    `order_id`  bigint(20) NOT NULL COMMENT '订单id' ,
    `status`  tinyint(3) NOT NULL DEFAULT 0 COMMENT '状态' ,
    `pay_type`  tinyint(3) NOT NULL DEFAULT 0 COMMENT '支付方式' ,
    `pay_money`  int(11) NOT NULL DEFAULT 0 COMMENT '支付金额' ,
    `out_trade_no`  varchar(64) NOT NULL DEFAULT '' COMMENT '对外暴露的订单号' ,
    `third_pay_sn`  varchar(64) NOT NULL DEFAULT '' COMMENT '第三方支付流水号' ,
    `pay_time`  bigint(20) NOT NULL DEFAULT 0 COMMENT '支付时间' ,
    PRIMARY KEY (`id`),
    INDEX `idx_order_id` (`order_id`) USING BTREE
) ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4 COMMENT='订单支付单';

drop table if exists `tb_order_refund`;
create table `tb_order_refund`
(
    order_id        bigint(20)   not null comment '订单Id',
    refund_type     tinyint(3)   not null default 0 comment '退款方式',
    refund_money    int          not null default 0 COMMENT '退款金额',
    `status`        tinyint(3)   not null default 0 comment '状态',
    third_refund_sn varchar(64)  not null default '' comment '第三方退款流水号',
    refund_time     bigint(20)   not null default 0 comment '退款时间',
    note            varchar(200) not null default '' comment '退款原因',
    PRIMARY KEY (`order_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '退款记录表';
