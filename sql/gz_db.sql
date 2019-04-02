DROP TABLE IF EXISTS `administrator`;
		
CREATE TABLE `administrator` (
  `admin_id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `admin_name` VARCHAR(32) NOT NULL COMMENT '管理员姓名',
  `admin_email` VARCHAR(32) NOT NULL COMMENT '管理员邮箱，用于登陆用户名',
  `admin_password` VARCHAR(32) NOT NULL COMMENT '管理员密码，用于登录校验',
  PRIMARY KEY (`id`)
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