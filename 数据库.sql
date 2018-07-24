/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 100131
 Source Host           : localhost:3306
 Source Schema         : classrecommend

 Target Server Type    : MySQL
 Target Server Version : 100131
 File Encoding         : 65001

 Date: 24/07/2018 16:19:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CollegeName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '计算机与控制工程学院', 0);
INSERT INTO `college` VALUES (2, '金融学院', 0);
INSERT INTO `college` VALUES (3, '历史学院', 0);
INSERT INTO `college` VALUES (4, '法学院', 0);
INSERT INTO `college` VALUES (5, '哲学院', 0);
INSERT INTO `college` VALUES (6, '公选课', 0);

-- ----------------------------
-- Table structure for comment_tag
-- ----------------------------
DROP TABLE IF EXISTS `comment_tag`;
CREATE TABLE `comment_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TagID` int(11) NULL DEFAULT NULL,
  `CommentID` int(11) NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位：0 is 正常，1 is 删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `TagID`(`TagID`) USING BTREE,
  INDEX `CommentID`(`CommentID`) USING BTREE,
  CONSTRAINT `comment_tag_ibfk_1` FOREIGN KEY (`TagID`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_tag_ibfk_2` FOREIGN KEY (`CommentID`) REFERENCES `student_comment_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CourseCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程编号',
  `CourseName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TeacherID` int(11) NULL DEFAULT NULL,
  `StudentNumber` int(11) NULL DEFAULT NULL COMMENT '课程人数',
  `StartWeek` int(11) NULL DEFAULT NULL,
  `EndWeek` int(11) NULL DEFAULT NULL,
  `LessonDay` int(11) NULL DEFAULT NULL,
  `StartLesson` int(11) NULL DEFAULT NULL,
  `EndLesson` int(11) NULL DEFAULT NULL,
  `IsSingleWeek` int(11) NULL DEFAULT 0 COMMENT '单双周标记：0 is 常规课程，1 is 单周课程，2 is 双周课程',
  `Credit` double NULL DEFAULT 0 COMMENT '学分数',
  `ExaminingForm` int(11) NULL DEFAULT 0 COMMENT '考试方式：0 is 闭卷考试，1 is 开卷考试，2 is 论文结课，3 is 其他',
  `CollegeID` int(11) NULL DEFAULT NULL COMMENT '开课学院ID，若为空则为任选课',
  `MajorID` int(11) NULL DEFAULT NULL COMMENT '所属专业ID，若为空则为所属学院的任意专业',
  `Level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应ABCDE类课',
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位：0 is 正常，1 is 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `TeacherID`(`TeacherID`) USING BTREE,
  INDEX `CollegeID`(`CollegeID`) USING BTREE,
  INDEX `MajorID`(`MajorID`) USING BTREE,
  INDEX `LevelID`(`Level`) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`TeacherID`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_3` FOREIGN KEY (`MajorID`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 's', '计算方法', 5, 100, 3, 16, NULL, 4, 5, 0, 3, 0, 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `FilePath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UpdateTime` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `StudentID` int(11) NULL DEFAULT NULL COMMENT '上传人ID',
  `CourseID` int(11) NULL DEFAULT NULL,
  `DownloadNum` int(11) NULL DEFAULT NULL,
  `DeleteStatus` int(11) NULL DEFAULT 0 COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `StudentID`(`StudentID`) USING BTREE,
  INDEX `CourseID`(`CourseID`) USING BTREE,
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `file_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `MajorName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CollegeID` int(11) NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CollegeID`(`CollegeID`) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '计算机科学', 1, 0);
INSERT INTO `major` VALUES (2, '信息安全', 1, 0);
INSERT INTO `major` VALUES (3, '自动化', 1, 0);
INSERT INTO `major` VALUES (4, '智能', 1, 0);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `StudentName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Gender` int(11) NULL DEFAULT 0 COMMENT '性别：0 is 未知，1 is 男，2 is 女',
  `NickName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `StudentNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Grade` int(11) NULL DEFAULT NULL,
  `CollegeID` int(11) NULL DEFAULT NULL,
  `MajorID` int(11) NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位：0 is 正常，1 is 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CollegeID`(`CollegeID`) USING BTREE,
  INDEX `MajorID`(`MajorID`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`MajorID`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '蝙蝠侠', 1, 'Batman', 'batman', NULL, 2016, 1, 2, 0);
INSERT INTO `student` VALUES (2, '超人', 1, 'Superman', 'superman', NULL, 2017, 1, 1, 0);

-- ----------------------------
-- Table structure for student_comment_course
-- ----------------------------
DROP TABLE IF EXISTS `student_comment_course`;
CREATE TABLE `student_comment_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SelectID` int(11) NULL DEFAULT NULL,
  `Comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `GradeScore` int(11) NULL DEFAULT NULL COMMENT '成绩打分',
  `ContentScore` int(11) NULL DEFAULT NULL,
  `LikeNumber` int(11) NULL DEFAULT 0,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位：0 is 正常，1 is 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `SelectID`(`SelectID`) USING BTREE,
  CONSTRAINT `student_comment_course_ibfk_1` FOREIGN KEY (`SelectID`) REFERENCES `student_select_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for student_select_course
-- ----------------------------
DROP TABLE IF EXISTS `student_select_course`;
CREATE TABLE `student_select_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` int(11) NULL DEFAULT NULL,
  `CourseID` int(11) NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位：0 is 正常，1 is 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `StudentID`(`StudentID`) USING BTREE,
  INDEX `CourseID`(`CourseID`) USING BTREE,
  CONSTRAINT `student_select_course_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_select_course_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TagName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '给分高', 0);
INSERT INTO `tag` VALUES (2, '给分很高', 0);
INSERT INTO `tag` VALUES (3, '给分超高', 0);
INSERT INTO `tag` VALUES (4, '给分贼高', 0);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TeacherName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Gender` int(11) NULL DEFAULT 0 COMMENT '性别：0 is 未知，1 is 男，2 is 女',
  `TelPhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OfficeAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办公地址',
  `CollegeID` int(11) NULL DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT 0 COMMENT '删除位：0 is 正常，1 is 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `CollegeID`(`CollegeID`) USING BTREE,
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '袁晓洁', 2, NULL, NULL, NULL, 1, 0);
INSERT INTO `teacher` VALUES (2, '王刚', 1, NULL, NULL, NULL, 1, 0);
INSERT INTO `teacher` VALUES (3, '刘晓光', 1, NULL, NULL, NULL, 1, 0);
INSERT INTO `teacher` VALUES (4, '杨巨峰', 1, NULL, NULL, NULL, 1, 0);
INSERT INTO `teacher` VALUES (5, '辛运帏', 2, NULL, NULL, NULL, 1, 0);
INSERT INTO `teacher` VALUES (6, '苏明', 1, NULL, NULL, NULL, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
