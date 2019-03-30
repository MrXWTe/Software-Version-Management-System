-- ---
-- Globals
-- ---

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET FOREIGN_KEY_CHECKS=0;

-- ---
-- Table 'Administrator'
-- ����Ա�����ڵ�¼��ϢУ��
-- ---

DROP TABLE IF EXISTS `administrator`;
		
CREATE TABLE `administrator` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT '����ԱID',
  `admin_name` VARCHAR(32) NOT NULL COMMENT '����Ա����',
  `admin_email` VARCHAR(32) NOT NULL COMMENT '����Ա���䣬���ڵ�¼�û���',
  `admin_password` VARCHAR(32) NOT NULL COMMENT '����Ա���룬���ڵ�¼У��',
  PRIMARY KEY (`id`)
) COMMENT '����Ա�����ڵ�¼��ϢУ��';

-- ---
-- Foreign Keys 
-- ---


-- ---
-- Table Properties
-- ---

-- ALTER TABLE `Administrator` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ---
-- Test Data
-- ---

-- INSERT INTO `Administrator` (`id`,`admin_name`,`admin_email`,`admin_password`) VALUES
-- ('','','','');