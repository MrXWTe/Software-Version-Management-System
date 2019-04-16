-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: gz_db
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrator` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `admin_name` varchar(32) NOT NULL COMMENT '管理员姓名',
  `admin_email` varchar(32) NOT NULL COMMENT '管理员邮箱，用于登录用户名',
  `admin_password` varchar(32) NOT NULL COMMENT '管理员密码，用于登录校验',
  `final_admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='管理员表，用于登录信息校验';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'罗国全','luoguoquan@gz.com','123456',1),(2,'廖泳健','liaoyongjian@gz.com','123456',0),(3,'徐伟腾','xuweiteng@gz.com','123456',0);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_software`
--

DROP TABLE IF EXISTS `tb_software`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_software` (
  `soft_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '软件ID',
  `soft_name` varchar(32) NOT NULL COMMENT '软件名称',
  `soft_info` mediumtext NOT NULL COMMENT '软件信息',
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`soft_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='软件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_software`
--

LOCK TABLES `tb_software` WRITE;
/*!40000 ALTER TABLE `tb_software` DISABLE KEYS */;
INSERT INTO `tb_software` VALUES (1,'Thunder BHO Platform1.0','说明：迅雷中某个小压缩包','2019-04-16'),(2,'金橙子','123456','2019-04-16'),(3,'金橙子1','的发生任何设备','2019-04-16'),(4,'金橙子2','123456','2019-04-16'),(5,'金橙子3','阿三发射点VS发表','2019-04-16');
/*!40000 ALTER TABLE `tb_software` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `user_email` varchar(32) NOT NULL COMMENT '用户email',
  `user_password` varchar(32) NOT NULL COMMENT '用户密码',
  `user_enroll_date` datetime NOT NULL,
  `user_status` tinyint(1) NOT NULL COMMENT '用户状态-1表示在公司-0表示已离职',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='普通员工';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'黄海利','huanhaili@gz.com','123456','2019-04-16 14:27:06',1);
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_version`
--

DROP TABLE IF EXISTS `tb_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_version` (
  `sv_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用于主键',
  `sv_info` mediumtext NOT NULL COMMENT '软件版本的信息',
  `sv_link` varchar(50) NOT NULL COMMENT '软件版本的下载链接',
  `soft_version_id` int(11) NOT NULL COMMENT '外键，对应软件ID',
  `sv_versionId` varchar(32) NOT NULL,
  `sv_version` int(10) NOT NULL,
  `sv_date` date DEFAULT NULL,
  PRIMARY KEY (`sv_id`),
  KEY `FK_ID` (`soft_version_id`),
  CONSTRAINT `FK_ID` FOREIGN KEY (`soft_version_id`) REFERENCES `tb_software` (`soft_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='软件版本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_version`
--

LOCK TABLES `tb_version` WRITE;
/*!40000 ALTER TABLE `tb_version` DISABLE KEYS */;
INSERT INTO `tb_version` VALUES (1,'版本：1.0\r\n更新信息：其为人踏实的发展乡村v\r\n更新日期：2018/12/22\r\n更新人：徐伟腾\r\naswcvsb\r\n版本：1.0\r\n更新信息：其为人踏实的发展乡村v\r\n更新日期：2018/12/22\r\n更新人：徐伟腾\r\naswcvsb\r\n版本：1.0\r\n更新信息：其为人踏实的发展乡村v\r\n更新日期：2018/12/22\r\n更新人：徐伟腾\r\naswcvsb','E:/Thunder BHO Platform.zip',1,'Thunder BHO Platform 1.0',0,'2019-04-16'),(2,'版本：2.0','E:/Thunder BHO Platform - 副本.zip',1,'Thunder BHO Platform 2.0',0,'2019-04-16'),(3,'版本：3.0','E:/Thunder BHO Platform - 副本 (2).zip',1,'Thunder BHO Platform 3.0',0,'2019-04-16'),(4,'版本：4.0','E:/Thunder BHO Platform - 副本 (3).zip',1,'Thunder BHO Platform 4.0',0,'2019-04-16'),(5,'版本：5.0','E:/Thunder BHO Platform - 副本 (3).zip',1,'Thunder BHO Platform 5.0',0,'2019-04-16'),(6,'发布日期：201920202\r\nsdasdaef\r\n成都VS人\r\ncsvrb \r\n按法律发\r\n生产出日军覅如果土豪','E:/Thunder BHO Platform - 副本 (2).zip',1,'Thunder BHO Platform 3.0',1,'2019-04-16'),(7,'版本：7.0','E:/software/beta/Thunder BHO Platform - 副本 (2).zip',1,'Thunder BHO Platform 7.0',0,'2019-04-16'),(8,'版本：8.0','E:/software/beta/Thunder BHO Platform - 副本 (2).zip',1,'Thunder BHO Platform 8.0',0,'2019-04-16'),(9,'版本：9.0','E:/software/release/Thunder BHO Platform.zip',1,'Thunder BHO Platform 9.0',1,'2019-04-16');
/*!40000 ALTER TABLE `tb_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-16 15:03:31
