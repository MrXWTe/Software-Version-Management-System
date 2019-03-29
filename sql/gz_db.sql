-- ---
-- Globals
-- ---

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET FOREIGN_KEY_CHECKS=0;

-- ---
-- Table 'administrator'
-- 管理员表，用于登录信息校验
-- ---

DROP TABLE IF EXISTS `administrator`;
		
CREATE TABLE `administrator` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `admin_name` VARCHAR(32) NOT NULL COMMENT '管理员姓名',
  `admin_email` VARCHAR(32) NOT NULL COMMENT '管理员邮箱，用于登录用户名',
  `admin_password` VARCHAR(32) NOT NULL COMMENT '管理员密码，用于登录校验',
  PRIMARY KEY (`id`)
) COMMENT '管理员表，用于登录信息校验';

-- ---
-- Foreign Keys 
-- ---


-- ---
-- Table Properties
-- ---

-- ALTER TABLE `administrator` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ---
-- Test Data
-- ---

-- INSERT INTO `administrator` (`id`,`admin_name`,`admin_email`,`admin_password`) VALUES
-- ('','','','');