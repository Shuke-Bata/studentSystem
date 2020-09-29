/*
 Navicat Premium Data Transfer

 Source Server         : Bata's Link
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : student

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 29/09/2020 16:32:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `student_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_age` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (35, '11823020401', 'ddd', '人工智能学院', '软件工程', '2018', '118230204', '18');
INSERT INTO `student` VALUES (36, '118230202', 'cc', '人工智能学院', '大数据', '2019', '118230203', '19');
INSERT INTO `student` VALUES (37, '11823020403', '哈哈', '计算机科学与工程学院', '软件工程', '2019', '118230204', '18');
INSERT INTO `student` VALUES (38, '11823020403', '易志钢', '两江人工智能学院', '软件工程', '2018', '4', '18');
INSERT INTO `student` VALUES (39, '11823020404', '伍文浩', '两江人工智能学院', '软件工程', '2018', '4', '19');
INSERT INTO `student` VALUES (40, '11823020406', '周春宇', '两江人工智能学院', '软件工程', '2018', '4', '19');
INSERT INTO `student` VALUES (41, '11823020409', '蔡兴华', '机械学院', '机器人工程', '2018', '2', '20');
INSERT INTO `student` VALUES (42, '11823020410', '曾新', '两江人工智能学院', '软件工程', '2018', '4', '21');
INSERT INTO `student` VALUES (43, '11823020337', '王亚', '两江人工智能学院', '软件工程', '2018', '3', '19');
INSERT INTO `student` VALUES (44, '11823020411', '杨能', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (45, '11823020413', '李友', '两江人工智能学院', '软件工程', '2018', '4', '19');
INSERT INTO `student` VALUES (46, '11823020414', '禹瑾', '两江人工智能学院', '软件工程', '2018', '4', '19');
INSERT INTO `student` VALUES (47, '11823020415', '周明华', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (48, '11823020416', '方家万', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (49, '11823020417', '刘美君', '两江人工智能学院', '软件工程', '2018', '4', '18');
INSERT INTO `student` VALUES (50, '11823020420', '张波浪', '两江人工智能学院', '软件工程', '2018', '4', '21');
INSERT INTO `student` VALUES (51, '11823020421', '周江', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (52, '11823020422', '舒波', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (53, '11823020423', '王俊杰', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (54, '11823020437', '寸泽婉', '两江人工智能学院', '软件工程', '2018', '4', '19');
INSERT INTO `student` VALUES (55, '11823020412', '刘胡鑫', '两江人工智能学院', '软件工程', '2018', '4', '20');
INSERT INTO `student` VALUES (56, '11823020305', '马杰', '两江人工智能学院', '软件工程', '2018', '3', '20');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '123');

SET FOREIGN_KEY_CHECKS = 1;
