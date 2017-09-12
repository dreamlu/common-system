/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : news

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-09-12 16:46:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for datadict
-- ----------------------------
DROP TABLE IF EXISTS `datadict`;
CREATE TABLE `datadict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `translate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of datadict
-- ----------------------------
INSERT INTO `datadict` VALUES ('1', 'datadict', '字典');
INSERT INTO `datadict` VALUES ('2', 'privilege', '权限');
INSERT INTO `datadict` VALUES ('3', 'news', '新闻');
INSERT INTO `datadict` VALUES ('4', 'news_title', '标题');
INSERT INTO `datadict` VALUES ('5', 'news_content', '内容');
INSERT INTO `datadict` VALUES ('6', 'user_contact', '邮箱');
INSERT INTO `datadict` VALUES ('8', 'user', '用户');
INSERT INTO `datadict` VALUES ('9', 'comment', '评论');
INSERT INTO `datadict` VALUES ('20', 'news_comments', '新闻评论');
INSERT INTO `datadict` VALUES ('21', 'news_views', '浏览量');

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `right` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('1', '1', 'admin');
INSERT INTO `privilege` VALUES ('2', '1', 'user$operate');
INSERT INTO `privilege` VALUES ('3', '16', 'user$operate');
INSERT INTO `privilege` VALUES ('4', '17', 'user$operate');

-- ----------------------------
-- Table structure for tb_baba
-- ----------------------------
DROP TABLE IF EXISTS `tb_baba`;
CREATE TABLE `tb_baba` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `aa` varchar(32) DEFAULT NULL,
  `bb` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_baba
-- ----------------------------
INSERT INTO `tb_baba` VALUES ('1', null, null);

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tb_news_id` int(11) DEFAULT NULL,
  `tb_news_comments` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES ('1', '2', '呵呵～');

-- ----------------------------
-- Table structure for tb_news
-- ----------------------------
DROP TABLE IF EXISTS `tb_news`;
CREATE TABLE `tb_news` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `news_title` varchar(32) DEFAULT NULL,
  `news_content` text,
  `news_views` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_news
-- ----------------------------
INSERT INTO `tb_news` VALUES ('2', 'aa', '<p><img alt=\"smiley\" src=\"http://localhost:8080/news/ckeditor/plugins/smiley/images/regular_smile.png\" style=\"height:23px; width:23px\" title=\"smiley\" /><img alt=\"cool\" src=\"http://localhost:8080/news/ckeditor/plugins/smiley/images/shades_smile.png\" style=\"height:23px; width:23px\" title=\"cool\" /><img alt=\"devil\" src=\"http://localhost:8080/news/ckeditor/plugins/smiley/images/devil_smile.png\" style=\"height:23px; width:23px\" title=\"devil\" />sssssfs</p>\r\n', '0');
INSERT INTO `tb_news` VALUES ('3', '1', '<p style=\"text-align:center\">&quot;世界，你好!&quot;</p>\r\n\r\n<p style=\"text-align:center\"><img alt=\"\" src=\"upload/f147d254-6828-40d3-a870-49f0acb3597e.jpg\" style=\"height:481px; width:734px\" /></p>\r\n', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(15) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `user_contact` varchar(30) DEFAULT NULL,
  `user_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '46ef3a30801e528983274772c4433fc49e97bf0a', '3147460271@qq.com', 'null');
INSERT INTO `user` VALUES ('16', '111', '07946435ecb38811525116785d1976b9eaaff16c', '862362681@qq.com', 'null');
INSERT INTO `user` VALUES ('17', 'user', '0c3a351b63e742f736060e9ccc67aabbbb811edf', null, null);
SET FOREIGN_KEY_CHECKS=1;
