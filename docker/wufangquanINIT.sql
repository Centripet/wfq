/*
 Navicat Premium Dump SQL

 Source Server         : ms8
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:43307
 Source Schema         : wufangquan

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 02/07/2025 14:29:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for w_circle
-- ----------------------------
DROP TABLE IF EXISTS `w_circle`;
CREATE TABLE `w_circle`  (
  `circle_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `is_public` tinyint NULL DEFAULT NULL COMMENT '1公开0私有',
  `view_count` int(10) UNSIGNED ZEROFILL NOT NULL,
  `publisher` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` tinyint NULL DEFAULT NULL COMMENT '1文本2文件3图片4视频',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '1删除0存在',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`circle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_circle
-- ----------------------------

-- ----------------------------
-- Table structure for w_circle_file
-- ----------------------------
DROP TABLE IF EXISTS `w_circle_file`;
CREATE TABLE `w_circle_file`  (
  `circle_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`circle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_circle_file
-- ----------------------------

-- ----------------------------
-- Table structure for w_circle_group
-- ----------------------------
DROP TABLE IF EXISTS `w_circle_group`;
CREATE TABLE `w_circle_group`  (
  `circle_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`circle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_circle_group
-- ----------------------------

-- ----------------------------
-- Table structure for w_circle_project
-- ----------------------------
DROP TABLE IF EXISTS `w_circle_project`;
CREATE TABLE `w_circle_project`  (
  `circle_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`circle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_circle_project
-- ----------------------------

-- ----------------------------
-- Table structure for w_circle_view
-- ----------------------------
DROP TABLE IF EXISTS `w_circle_view`;
CREATE TABLE `w_circle_view`  (
  `circle_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`circle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_circle_view
-- ----------------------------

-- ----------------------------
-- Table structure for w_file
-- ----------------------------
DROP TABLE IF EXISTS `w_file`;
CREATE TABLE `w_file`  (
  `file_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_public_read` tinyint NULL DEFAULT NULL,
  `uploader` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `type` tinyint NULL DEFAULT NULL,
  `suffix` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_file
-- ----------------------------
INSERT INTO `w_file` VALUES ('3534598a3c5548029a014754c3344286', 'uploads/general/6c43d1a846944081acbdadf6b2cad897/2025/06/21/0a186efbc7334b6eb0889dec587daaad.pdf', 0, '6c43d1a846944081acbdadf6b2cad897', '2025-06-21 20:09:23', 1, NULL, 'string', 'string');
INSERT INTO `w_file` VALUES ('f8aa33ddc5744978bd849b961a83e5b5', 'uploads/general/6c43d1a846944081acbdadf6b2cad897/2025/06/21/c208b027beef43f9afb4b997b9593b25.png', NULL, '6c43d1a846944081acbdadf6b2cad897', '2025-06-21 19:16:23', 1, NULL, 'string', 'string');

-- ----------------------------
-- Table structure for w_friend
-- ----------------------------
DROP TABLE IF EXISTS `w_friend`;
CREATE TABLE `w_friend`  (
  `user_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `friend_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `friend_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_friend
-- ----------------------------
INSERT INTO `w_friend` VALUES ('6c43d1a846944081acbdadf6b2cad897', 'c1e61f4f4e1c498c96602ddd18cc9755', '2025-06-11 07:39:23');
INSERT INTO `w_friend` VALUES ('c1e61f4f4e1c498c96602ddd18cc9755', '6c43d1a846944081acbdadf6b2cad897', '2025-06-11 07:39:23');

-- ----------------------------
-- Table structure for w_friend_request
-- ----------------------------
DROP TABLE IF EXISTS `w_friend_request`;
CREATE TABLE `w_friend_request`  (
  `request_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sender_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `receiver_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT NULL COMMENT '0未处理，1同意，2拒绝',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `handle_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`request_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_friend_request
-- ----------------------------
INSERT INTO `w_friend_request` VALUES ('36668d7cee924ee58651bac730163905', '6c43d1a846944081acbdadf6b2cad897', 'c1e61f4f4e1c498c96602ddd18cc9755', 1, NULL, '2025-06-11 07:26:30', NULL);

-- ----------------------------
-- Table structure for w_group
-- ----------------------------
DROP TABLE IF EXISTS `w_group`;
CREATE TABLE `w_group`  (
  `group_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `creator_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `group_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `group_owner` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_group
-- ----------------------------
INSERT INTO `w_group` VALUES ('a60d4145f2ed454e938269a3253ba481', '测试群1', '6c43d1a846944081acbdadf6b2cad897', '2025-06-11 07:15:33', NULL, '6c43d1a846944081acbdadf6b2cad897');
INSERT INTO `w_group` VALUES ('b84bae4d308b4a9184f8f12c6313bbab', '测试群2', '6c43d1a846944081acbdadf6b2cad897', '2025-06-11 07:18:50', NULL, '6c43d1a846944081acbdadf6b2cad897');

-- ----------------------------
-- Table structure for w_group_member
-- ----------------------------
DROP TABLE IF EXISTS `w_group_member`;
CREATE TABLE `w_group_member`  (
  `group_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_role` tinyint NOT NULL COMMENT '1普通群员2管理员3群主',
  `joined_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`group_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_group_member
-- ----------------------------
INSERT INTO `w_group_member` VALUES ('a60d4145f2ed454e938269a3253ba481', '6c43d1a846944081acbdadf6b2cad897', 3, '2025-06-11 07:15:33');
INSERT INTO `w_group_member` VALUES ('b84bae4d308b4a9184f8f12c6313bbab', '6c43d1a846944081acbdadf6b2cad897', 3, '2025-06-11 07:18:50');
INSERT INTO `w_group_member` VALUES ('b84bae4d308b4a9184f8f12c6313bbab', 'c1e61f4f4e1c498c96602ddd18cc9755', 1, '2025-06-11 07:18:50');

-- ----------------------------
-- Table structure for w_group_message
-- ----------------------------
DROP TABLE IF EXISTS `w_group_message`;
CREATE TABLE `w_group_message`  (
  `message_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sender_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `type` tinyint NULL DEFAULT NULL COMMENT '1文本2语音3图片4视频5连接',
  `create_time` datetime NULL DEFAULT NULL,
  `link_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_group_message
-- ----------------------------
INSERT INTO `w_group_message` VALUES ('499977463fe04656a5365213def2e753', 'b84bae4d308b4a9184f8f12c6313bbab', '6c43d1a846944081acbdadf6b2cad897', '123456789', 1, '2025-06-11 07:22:07', '', '');

-- ----------------------------
-- Table structure for w_group_message_read
-- ----------------------------
DROP TABLE IF EXISTS `w_group_message_read`;
CREATE TABLE `w_group_message_read`  (
  `message_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_read` tinyint NULL DEFAULT NULL,
  `read_time` datetime NULL DEFAULT NULL COMMENT '0未读1已读',
  PRIMARY KEY (`message_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_group_message_read
-- ----------------------------

-- ----------------------------
-- Table structure for w_group_request
-- ----------------------------
DROP TABLE IF EXISTS `w_group_request`;
CREATE TABLE `w_group_request`  (
  `request_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sender_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `group_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint NULL DEFAULT NULL COMMENT '0未处理，1同意，2拒绝',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `handle_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`request_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_group_request
-- ----------------------------

-- ----------------------------
-- Table structure for w_message
-- ----------------------------
DROP TABLE IF EXISTS `w_message`;
CREATE TABLE `w_message`  (
  `message_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sender_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `receiver_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `type` tinyint NULL DEFAULT NULL COMMENT '1文本2语音3图片4视频5连接',
  `create_time` datetime NULL DEFAULT NULL,
  `link_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_read` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_message
-- ----------------------------
INSERT INTO `w_message` VALUES ('5c4f9928dcc2402ea780f8ca85b443ed', '6c43d1a846944081acbdadf6b2cad897', 'c1e61f4f4e1c498c96602ddd18cc9755', '哈哈哈', 1, '2025-06-11 06:58:45', '', '', 1);
INSERT INTO `w_message` VALUES ('9753750fd6554780acf365c85f03aa98', '6c43d1a846944081acbdadf6b2cad897', 'c1e61f4f4e1c498c96602ddd18cc9755', '你好，这是测试消息', 1, '2025-06-11 06:57:47', '', '', 1);
INSERT INTO `w_message` VALUES ('b8b592c8ce6a4954b4820b3de5160c10', 'c1e61f4f4e1c498c96602ddd18cc9755', '6c43d1a846944081acbdadf6b2cad897', '1111', 1, '2025-06-11 07:20:12', '', '', 1);
INSERT INTO `w_message` VALUES ('d6c5032c21794679a14d48df745f7e26', 'c1e61f4f4e1c498c96602ddd18cc9755', '6c43d1a846944081acbdadf6b2cad897', '123456789hhh', 1, '2025-06-11 07:06:41', '', '', 1);

-- ----------------------------
-- Table structure for w_notice
-- ----------------------------
DROP TABLE IF EXISTS `w_notice`;
CREATE TABLE `w_notice`  (
  `notice_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_notice
-- ----------------------------
INSERT INTO `w_notice` VALUES ('6eae20b9-3f47-11f0-973c-06e37a099b54', '局⻓韩⽟拴主持会议通知', '请施⼯单\r\n位、设计单位参会11111111111111111111111111111111111111111122222', '2025-06-02 08:21:45');
INSERT INTO `w_notice` VALUES ('6eae20b9-3f47-11f0-973c-06e37a099b55', '11111', '22222', '2025-06-02 08:57:07');

-- ----------------------------
-- Table structure for w_notice_read
-- ----------------------------
DROP TABLE IF EXISTS `w_notice_read`;
CREATE TABLE `w_notice_read`  (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `notice_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `read_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_notice_read
-- ----------------------------
INSERT INTO `w_notice_read` VALUES ('6c43d1a846944081acbdadf6b2cad897', '6eae20b9-3f47-11f0-973c-06e37a099b54', '2025-06-02 08:49:47');

-- ----------------------------
-- Table structure for w_publicity
-- ----------------------------
DROP TABLE IF EXISTS `w_publicity`;
CREATE TABLE `w_publicity`  (
  `pub_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pub_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_publicity
-- ----------------------------
INSERT INTO `w_publicity` VALUES ('3534598a3c5548029a014754c3344286', NULL);
INSERT INTO `w_publicity` VALUES ('f8aa33ddc5744978bd849b961a83e5b5', NULL);

-- ----------------------------
-- Table structure for w_user
-- ----------------------------
DROP TABLE IF EXISTS `w_user`;
CREATE TABLE `w_user`  (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nick_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_status` tinyint(1) NULL DEFAULT NULL,
  `organization` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '隶属哪一方',
  `sex` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `role` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `department` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_user
-- ----------------------------
INSERT INTO `w_user` VALUES ('6c43d1a846944081acbdadf6b2cad897', 'Centripet', 'b01c1fe99143cd42e6103aa177141cd92336c734ec4aefada122f84187670f23', '2a4eb07d1a8eb11f', '13613480352', NULL, 1, NULL, NULL, NULL, NULL, 'user', NULL);
INSERT INTO `w_user` VALUES ('c1e61f4f4e1c498c96602ddd18cc9755', 'test', 'bfe66ba9b54c8a126c8253c6b14bafc5bd384fd743aaef9d7623d876ff9f13ba', '6ed92c156c91e894', '13613480353', NULL, 1, NULL, NULL, NULL, '2025-05-31 21:10:34', 'user', NULL);

SET FOREIGN_KEY_CHECKS = 1;
