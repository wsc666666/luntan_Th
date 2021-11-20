/*
Navicat MySQL Data Transfer

Source Server         : new_1
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : toutiao

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2020-05-03 10:40:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `news_id` int(11) NOT NULL,
  `entity` text NOT NULL,
  `like_count` varchar(255) default '0',
  `comment_count` varchar(255) default '0',
  `create_date` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL auto_increment,
  `content` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `entity_type` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `status` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `entity_index` (`entity_id`,`entity_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `entity_type` int(255) NOT NULL,
  `liked_id` int(11) NOT NULL,
  `liked_type` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login_ticket
-- ----------------------------
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `ticket` varchar(45) NOT NULL,
  `expired` datetime NOT NULL,
  `status` int(11) default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ticket_UNIQUE` (`ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL auto_increment,
  `from_id` int(11) default NULL,
  `to_id` int(11) default NULL,
  `content` text,
  `created_date` datetime default NULL,
  `has_read` int(11) default NULL,
  `conversation_id` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `conversation_index` (`conversation_id`),
  KEY `created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `title` varchar(128) NOT NULL default '',
  `link` varchar(256) NOT NULL default '',
  `image` varchar(256) NOT NULL default '',
  `like_count` int(11) default NULL,
  `comment_count` int(11) default NULL,
  `created_date` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `type` varchar(2550) NOT NULL,
  `an_count` varchar(255) default NULL,
  `follow_count` varchar(255) default NULL,
  `read_count` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` varchar(255) default NULL,
  ` title` varchar(255) default NULL,
  `content` varchar(255) default NULL,
  `comment` varchar(255) default NULL,
  `comment_count` varchar(255) default NULL,
  `created_date` datetime default NULL,
  `user_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `name` varchar(64) NOT NULL default '',
  `password` varchar(128) NOT NULL default '',
  `salt` varchar(32) NOT NULL default '',
  `head_url` varchar(256) NOT NULL default '',
  `zh_name` varchar(255) NOT NULL,
  `jc` varchar(255) NOT NULL,
  `star` varchar(2550) default NULL,
  `star2` varchar(2550) default NULL,
  `star3` varchar(2550) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
