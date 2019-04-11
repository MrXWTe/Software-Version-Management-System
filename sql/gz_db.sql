DROP TABLE IF EXISTS `admin`;
CREATE TABLE `tb_admin` (
  `admin_id` VARCHAR(5) NOT NULL COMMENT '管理员工号',
  `admin_name` VARCHAR(10) NOT NULL COMMENT '管理员姓名',
  `admin_email` VARCHAR(32) NOT NULL COMMENT '管理员邮箱，用于登陆用户名',
  `admin_password` VARCHAR(32) NOT NULL COMMENT '管理员密码，用于登录校验',
  `admin_final` TINYINT(1) NOT NULL COMMENT '判断是否为最终管理员',
  PRIMARY KEY (`admin_id`)
) COMMENT '管理员表，用于登录信息校验';


DROP TABLE IF EXISTS `tb_software`;
CREATE TABLE `tb_software` (
  `soft_id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '软件ID',
  `soft_name` VARCHAR(32) NOT NULL COMMENT '软件名称',
  `soft_info` MEDIUMTEXT NOT NULL COMMENT '软件信息',
  `soft_author` VARCHAR(32) NOT NULL COMMENT '软件作者',
  `soft_last_modified_date` DATETIME NOT NULL COMMENT '软件最后修改日期',
  PRIMARY KEY (`soft_id`)
) COMMENT '软件信息表';

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` VARCHAR(32) NOT NULL COMMENT '用户名',
  `user_email` VARCHAR(32) NOT NULL COMMENT '用户email',
  `user_password` VARCHAR(32) NOT NULL COMMENT '用户密码',
  `user_enroll_date` DATE NOT NULL COMMENT '用户注册日期',
  `user_status` TINYINT(1) NOT NULL COMMENT '用户状态-1表示在公司-0表示已离职',
  PRIMARY KEY (`user_id`)
) COMMENT '普通员工';

DROP TABLE IF EXISTS `tb_softwareversion`;

CREATE TABLE `tb_version` (
  `sv_id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '用于主键',
  `sv_info` MEDIUMTEXT NOT NULL COMMENT '软件版本的信息',
  `sv_link` VARCHAR(50) NOT NULL COMMENT '软件版本的下载链接',
  `soft_version_id` INTEGER NOT NULL COMMENT '外键，对应软件ID',
  PRIMARY KEY (`sv_id`),
  CONSTRAINT `FK_ID` FOREIGN KEY (`soft_version_id`) REFERENCES `tb_software` (`soft_id`)
) COMMENT '软件版本表';
