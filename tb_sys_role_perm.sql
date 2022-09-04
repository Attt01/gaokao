/*
 Navicat Premium Data Transfer

 Source Server         : localhost22
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : Gaokao

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/06/2022 22:11:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_sys_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role_perm`;
CREATE TABLE `tb_sys_role_perm` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NOT NULL DEFAULT '0' COMMENT '角色Id',
  `perm_id` bigint NOT NULL DEFAULT '0' COMMENT '权限Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_perm` (`role_id`,`perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表';

-- ----------------------------
-- Records of tb_sys_role_perm
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (46, 100, 10);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (53, 100, 20);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (60, 100, 30);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (74, 100, 1010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (80, 100, 1020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (59, 100, 2010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (64, 100, 2020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (72, 100, 2030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (78, 100, 2040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (41, 100, 3010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (56, 100, 3030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (76, 100, 3060);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (83, 100, 3070);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (73, 100, 201010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (84, 100, 201020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (43, 100, 201030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (52, 100, 201040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (58, 100, 202010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (67, 100, 202020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (71, 100, 202030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (79, 100, 202040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (40, 100, 203010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (49, 100, 203020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (54, 100, 203030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (63, 100, 203040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (69, 100, 204010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (77, 100, 204020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (82, 100, 204030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (47, 100, 204040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (55, 100, 301010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (57, 100, 301020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (66, 100, 303010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (68, 100, 303020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (75, 100, 303030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (42, 100, 303040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (48, 100, 303050);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (50, 100, 303060);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (61, 100, 306010);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (62, 100, 306020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (70, 100, 306030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (81, 100, 306040);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (44, 100, 306050);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (45, 100, 307020);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (51, 100, 307030);
INSERT INTO `tb_sys_role_perm` (`id`, `role_id`, `perm_id`) VALUES (65, 100, 307040);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
