CREATE TABLE `tb_goods`
(
    `id`             BIGINT AUTO_INCREMENT NOT NULL COMMENT '商品Id',
    `name`           VARCHAR(64)           NOT NULL DEFAULT '' COMMENT '名称',
    `summary`        VARCHAR(120)          NOT NULL DEFAULT '' COMMENT '简要描述',
    `sale_price`     INT                   NOT NULL DEFAULT 0 COMMENT '售卖价格',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8 COMMENT '商品基本信息表';