drop table if exists `tb_user_form`;
CREATE TABLE `tb_user_form`
(
    id             bigint auto_increment not null comment '主键',
    user_id        bigint      not null default 0 comment '用户Id',
    name           varchar(20) not null default '' comment '志愿表名称',
    score          int         not null default 0 comment '用户分数',
    subject        varchar(10) not null default '' comment '用户的选课信息',
    generated_type tinyint     not null default 0 comment '生成类型，默认是手动生成',
    is_current     tinyint     not null default 0 comment '是否为当前表',
    generated_time bigint      not null default 0 comment '生成时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户志愿表';

/*
 Navicat Premium Data Transfer

 Source Server         : Gaokao
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : gaokao

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 20/09/2021 20:34:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_form_volunteer
-- ----------------------------
DROP TABLE IF EXISTS `tb_form_volunteer`;
CREATE TABLE `tb_form_volunteer`  (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `form_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '对应志愿表id',
                                      `volunteer_section` tinyint(4) NOT NULL DEFAULT 0 COMMENT '第一段的志愿还是第二段的志愿, 0表示第一段, 1表示第二段',
                                      `volunteer_position` tinyint(4) NOT NULL DEFAULT 0 COMMENT '第几条志愿, 取值为1-96',
                                      `volunteer_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '对应的志愿id',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE INDEX `1`(`form_id`, `volunteer_id`) USING BTREE COMMENT '每个志愿表下不能有相同志愿'
) ENGINE = InnoDB AUTO_INCREMENT = 1051 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '志愿表 与 志愿的对应' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


