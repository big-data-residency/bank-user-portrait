CREATE DATABASE  IF NOT EXISTS `eamis-helper` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eamis-helper`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: eamis-helper
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `college` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CollegeName` varchar(255) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL COMMENT '学院分类 1234=文理工商',
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES (1,'计算机与控制工程学院',NULL,0),(2,'金融学院',NULL,0),(3,'历史学院',NULL,0),(4,'法学院',NULL,0),(5,'哲学院',NULL,0),(6,'公选课',NULL,0),(7,NULL,1,1);
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_tag`
--

DROP TABLE IF EXISTS `comment_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TagID` int(11) DEFAULT NULL,
  `CommentID` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位：0 is 正常，1 is 删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `TagID` (`TagID`) USING BTREE,
  KEY `CommentID` (`CommentID`) USING BTREE,
  CONSTRAINT `comment_tag_ibfk_1` FOREIGN KEY (`TagID`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_tag_ibfk_2` FOREIGN KEY (`CommentID`) REFERENCES `student_comment_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_tag`
--

LOCK TABLES `comment_tag` WRITE;
/*!40000 ALTER TABLE `comment_tag` DISABLE KEYS */;
INSERT INTO `comment_tag` VALUES (1,2,1,0);
/*!40000 ALTER TABLE `comment_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CourseCode` varchar(255) DEFAULT NULL COMMENT '课程编号',
  `CourseName` varchar(255) DEFAULT NULL,
  `TeacherID` int(11) DEFAULT NULL,
  `StudentNumber` int(11) DEFAULT NULL COMMENT '课程人数',
  `StartWeek` int(11) DEFAULT NULL,
  `EndWeek` int(11) DEFAULT NULL,
  `IsSingleWeek` int(11) DEFAULT '0' COMMENT '单双周标记：0 is 常规课程，1 is 单周课程，2 is 双周课程',
  `Credit` double DEFAULT '0' COMMENT '学分数',
  `ExaminingForm` int(11) DEFAULT '0' COMMENT '考试方式：0 is 闭卷考试，1 is 开卷考试，2 is 论文结课，3 is 其他',
  `CollegeID` int(11) DEFAULT NULL COMMENT '开课学院ID，若为空则为任选课',
  `MajorID` int(11) DEFAULT NULL COMMENT '所属专业ID，若为空则为所属学院的任意专业',
  `Level` varchar(255) DEFAULT NULL COMMENT '对应ABCDE类课',
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位：0 is 正常，1 is 已删除',
  `HasMidExam` int(11) DEFAULT '0' COMMENT '是否有期中考试 0=没有 1=有',
  `FinalExamWeight` int(11) DEFAULT NULL,
  `MidExamWeight` int(11) DEFAULT NULL,
  `PassingCourse` int(11) DEFAULT '0' COMMENT '是否是通过制 0=否 1=是 默认0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `TeacherID` (`TeacherID`) USING BTREE,
  KEY `CollegeID` (`CollegeID`) USING BTREE,
  KEY `MajorID` (`MajorID`) USING BTREE,
  KEY `LevelID` (`Level`) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`TeacherID`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_3` FOREIGN KEY (`MajorID`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'s','计算方法',5,100,3,16,0,3,0,1,NULL,NULL,0,0,NULL,NULL,NULL),(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'B',1,0,0,0,0),(3,NULL,'数据库',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'A',0,0,0,0,0);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_level`
--

DROP TABLE IF EXISTS `course_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `LevelName` varchar(255) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_level`
--

LOCK TABLES `course_level` WRITE;
/*!40000 ALTER TABLE `course_level` DISABLE KEYS */;
INSERT INTO `course_level` VALUES (1,'A',0),(2,'B',0),(3,'C',0),(4,'D',0),(5,'E',0);
/*!40000 ALTER TABLE `course_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_time`
--

DROP TABLE IF EXISTS `course_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ClassId` int(11) DEFAULT NULL,
  `StartLesson` int(11) DEFAULT NULL,
  `EndLesson` int(11) DEFAULT NULL,
  `LessonDay` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ClassId` (`ClassId`) USING BTREE,
  CONSTRAINT `course_time_ibfk_1` FOREIGN KEY (`ClassId`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_time`
--

LOCK TABLES `course_time` WRITE;
/*!40000 ALTER TABLE `course_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(255) DEFAULT NULL,
  `FilePath` varchar(255) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `StudentID` int(11) DEFAULT NULL COMMENT '上传人ID',
  `CourseID` int(11) DEFAULT NULL,
  `DownloadNum` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) DEFAULT '0' COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `StudentID` (`StudentID`) USING BTREE,
  KEY `CourseID` (`CourseID`) USING BTREE,
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `file_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `MajorName` varchar(255) DEFAULT NULL,
  `CollegeID` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `CollegeID` (`CollegeID`) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'计算机科学',1,0),(2,'信息安全',1,0),(3,'自动化',1,0),(4,'智能',1,0);
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `StudentName` varchar(255) DEFAULT NULL,
  `Gender` int(11) DEFAULT '0' COMMENT '性别：0 is 未知，1 is 男，2 is 女',
  `NickName` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `StudentNumber` varchar(255) DEFAULT NULL,
  `Grade` int(11) DEFAULT NULL,
  `CollegeID` int(11) DEFAULT NULL,
  `MajorID` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位：0 is 正常，1 is 已删除',
  `Privilege` int(11) NOT NULL DEFAULT '0' COMMENT '0=普通用户 1=管理员',
  PRIMARY KEY (`id`,`Privilege`) USING BTREE,
  KEY `CollegeID` (`CollegeID`) USING BTREE,
  KEY `MajorID` (`MajorID`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`MajorID`) REFERENCES `major` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'蝙蝠侠',1,'Batman','batman',NULL,2016,1,2,0,0),(2,'超人',1,'Superman','superman',NULL,2017,1,1,0,0),(4,'TestStudent',NULL,NULL,NULL,NULL,0,NULL,NULL,0,0),(5,'TestStudent',NULL,NULL,NULL,NULL,0,NULL,NULL,0,0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_comment_course`
--

DROP TABLE IF EXISTS `student_comment_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_comment_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SelectID` int(11) DEFAULT NULL,
  `Comment` varchar(255) DEFAULT NULL,
  `GradeScore` int(11) DEFAULT NULL COMMENT '成绩打分',
  `ContentScore` int(11) DEFAULT NULL,
  `LikeNumber` int(11) DEFAULT '0',
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位：0 is 正常，1 is 已删除',
  `Anonymous` int(11) DEFAULT '0' COMMENT '0=不匿名，1=匿名 默认不匿名',
  `CommentTime` timestamp NULL DEFAULT NULL,
  `BearScore` int(11) DEFAULT NULL,
  `InterestingScore` int(11) DEFAULT NULL,
  `EasyScore` int(11) DEFAULT NULL,
  `KnowledgeScore` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `SelectID` (`SelectID`) USING BTREE,
  CONSTRAINT `student_comment_course_ibfk_1` FOREIGN KEY (`SelectID`) REFERENCES `student_select_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_comment_course`
--

LOCK TABLES `student_comment_course` WRITE;
/*!40000 ALTER TABLE `student_comment_course` DISABLE KEYS */;
INSERT INTO `student_comment_course` VALUES (1,1,'你很优秀，但这不影响你的优秀',NULL,NULL,2,0,0,NULL,NULL,NULL,NULL,NULL),(5,2,'优秀',0,0,0,0,0,NULL,0,0,0,0);
/*!40000 ALTER TABLE `student_comment_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_select_course`
--

DROP TABLE IF EXISTS `student_select_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_select_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` int(11) DEFAULT NULL,
  `CourseID` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位：0 is 正常，1 is 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `StudentID` (`StudentID`) USING BTREE,
  KEY `CourseID` (`CourseID`) USING BTREE,
  CONSTRAINT `student_select_course_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_select_course_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_select_course`
--

LOCK TABLES `student_select_course` WRITE;
/*!40000 ALTER TABLE `student_select_course` DISABLE KEYS */;
INSERT INTO `student_select_course` VALUES (1,2,3,0),(2,1,1,0);
/*!40000 ALTER TABLE `student_select_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TagName` varchar(255) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'给分高',0),(2,'给分很高',0),(3,'给分非常高',0),(4,'给分贼高',0),(5,'你很优秀',0),(6,'你非常优秀',1);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TeacherName` varchar(255) DEFAULT NULL,
  `Gender` int(11) DEFAULT '0' COMMENT '性别：0 is 未知，1 is 男，2 is 女',
  `TelPhone` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `OfficeAddress` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `CollegeID` int(11) DEFAULT NULL,
  `DeleteStatus` int(11) NOT NULL DEFAULT '0' COMMENT '删除位：0 is 正常，1 is 已删除',
  `Level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `CollegeID` (`CollegeID`) USING BTREE,
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`CollegeID`) REFERENCES `college` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'袁晓洁',2,NULL,NULL,NULL,1,0,'教授'),(2,'王刚',1,NULL,NULL,NULL,1,0,NULL),(3,'刘晓光',1,NULL,NULL,NULL,1,0,NULL),(4,'杨巨峰',1,NULL,NULL,NULL,1,0,NULL),(5,'辛运帏',2,NULL,NULL,NULL,1,0,NULL),(6,'苏明',1,NULL,NULL,NULL,1,0,NULL),(7,'TestTeacher1',NULL,NULL,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'eamis-helper'
--

--
-- Dumping routines for database 'eamis-helper'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-28  1:20:24
