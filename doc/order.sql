drop table if exists `tb_order`;
CREATE TABLE `tb_order`
(
    id           BIGINT        NOT NULL AUTO_INCREMENT COMMENT '订单id',
    user_id      BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户id',
    goods_id     BIGINT        NOT NULL  DEFAULT 0 COMMENT '商品id(后续可能增加商品)',
    total_price  INT           NOT NULL DEFAULT 0 COMMENT '总金额',
    real_price   INT           NOT NULL DEFAULT 0 COMMENT '实际付费',
    create_time  BIGINT(20) NOT NULL COMMENT '订单创建时间',
    `pay_time`     bigint(20) NOT NULL DEFAULT 0 COMMENT '支付时间',
    out_trade_no VARCHAR(60)   NOT NULL COMMENT '对外订单号',
    pay_type     TINYINT(3) NOT NULL COMMENT '支付方式',
    third_pay_sn VARCHAR(64) CHARSET dec8 NOT NULL COMMENT '第三方支付流水号',
    `status`     TINYINT(3) NOT NULL DEFAULT 0 COMMENT '状态',
    PRIMARY KEY (`id`)
) ENGINE=INNODB
  DEFAULT CHARSET = utf8mb4 comment '订单信息表';

drop table if exists `tb_order_refund`;
create table `tb_order_refund`
(
    order_id        bigint(20) not null comment '订单Id',
    refund_type     tinyint(3) not null default 0 comment '退款方式',
    refund_money    int          not null default 0 COMMENT '退款金额',
    `status`        tinyint(3) not null default 0 comment '状态',
    third_refund_sn varchar(64)  not null default '' comment '第三方退款流水号',
    refund_time     bigint(20) not null default 0 comment '退款时间',
    note            varchar(200) not null default '' comment '退款原因',
    PRIMARY KEY (`order_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '退款记录表';
