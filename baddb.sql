/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027 (8.0.27)
 Source Host           : localhost:3306
 Source Schema         : baddb

 Target Server Type    : MySQL
 Target Server Version : 80027 (8.0.27)
 File Encoding         : 65001

 Date: 06/11/2024 16:12:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_owner
-- ----------------------------
DROP TABLE IF EXISTS `sys_owner`;
CREATE TABLE `sys_owner`  (
  `owner_id` int NOT NULL AUTO_INCREMENT COMMENT '房东ID',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `owner_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房东类型：个人房东、二房东',
  `color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '红黑榜：推荐、不推荐、还行',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '推荐或者不推荐理由',
  PRIMARY KEY (`owner_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '房东信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_owner
-- ----------------------------
INSERT INTO `sys_owner` VALUES (1, '18888888888', '个人房源', '推荐', 0, '2024-11-06 16:07:38', NULL, '2024-11-06 16:07:38', '人很好，推荐');
INSERT INTO `sys_owner` VALUES (2, '19999999999', '二房东', '不推荐', 0, '2024-11-06 16:07:38', NULL, '2024-11-06 16:11:34', '二房东，太坑了，不推荐');

-- ----------------------------
-- Table structure for sys_room
-- ----------------------------
DROP TABLE IF EXISTS `sys_room`;
CREATE TABLE `sys_room`  (
  `room_id` int NOT NULL AUTO_INCREMENT COMMENT '房屋ID',
  `color` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '红黑榜：推荐、不推荐、还行',
  `room_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房屋类型：合租、整租',
  `country` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房屋地址',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `owner_id` int NOT NULL COMMENT '房东ID',
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房屋信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_room
-- ----------------------------
INSERT INTO `sys_room` VALUES (1, '推荐', '整租', '中国', '江苏', '苏州', '龙惠花苑1599', 0, '2024-11-06 16:07:09', NULL, '2024-11-06 16:09:24', '棒极了，推荐', 1);
INSERT INTO `sys_room` VALUES (2, '不推荐', '合租', '中国', '江苏', '苏州', '龙惠花苑1000', 0, '2024-11-06 16:07:09', NULL, '2024-11-06 16:07:09', '糟透了，不推荐', 2);

SET FOREIGN_KEY_CHECKS = 1;
