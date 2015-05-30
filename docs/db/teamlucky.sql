/*
 Navicat Premium Data Transfer

 Source Server         : Dev
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : teamlucky

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 05/31/2015 02:17:16 AM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Document`
-- ----------------------------
DROP TABLE IF EXISTS `Document`;
CREATE TABLE `Document` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `docFullName` varchar(50) NOT NULL,
  `docName` varchar(50) NOT NULL,
  `docExtName` varchar(10) NOT NULL,
  `docSize` bigint(13) NOT NULL,
  `docPath` varchar(100) NOT NULL,
  `isPublic` tinyint(3) NOT NULL,
  `downloadCount` int(10) NOT NULL,
  `groupId` bigint(13) DEFAULT NULL,
  `partyId` bigint(13) NOT NULL,
  `createBy` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Document_groupId` (`groupId`),
  KEY `Document_partyId` (`partyId`),
  KEY `Document_createBy` (`createBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `Forum`
-- ----------------------------
DROP TABLE IF EXISTS `Forum`;
CREATE TABLE `Forum` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `forumTitle` varchar(32) NOT NULL,
  `forumContent` varchar(5000) NOT NULL,
  `isPublic` tinyint(3) NOT NULL,
  `readCount` int(10) NOT NULL,
  `commentCount` int(10) NOT NULL,
  `groupId` bigint(13) DEFAULT NULL,
  `partyId` bigint(13) DEFAULT NULL,
  `createBy` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Forum_groupId` (`groupId`),
  KEY `Forum_partyId` (`partyId`),
  KEY `Forum_createBy` (`createBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ForumComment`
-- ----------------------------
DROP TABLE IF EXISTS `ForumComment`;
CREATE TABLE `ForumComment` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `forumId` bigint(13) NOT NULL,
  `commentContent` varchar(500) NOT NULL,
  `parentId` bigint(13) DEFAULT NULL,
  `createBy` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ForumComment_forumId` (`forumId`),
  KEY `ForumComment_parentId` (`parentId`),
  KEY `ForumComment_createBy` (`createBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `Message`
-- ----------------------------
DROP TABLE IF EXISTS `Message`;
CREATE TABLE `Message` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `messageTitle` varchar(500) NOT NULL,
  `messageContent` varchar(5000) NOT NULL,
  `messageUrl` varchar(100) DEFAULT NULL,
  `isRead` tinyint(3) NOT NULL,
  `messageFrom` varchar(20) NOT NULL,
  `messageTo` varchar(32) NOT NULL,
  `messageTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Message_messageTo` (`messageTo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `News`
-- ----------------------------
DROP TABLE IF EXISTS `News`;
CREATE TABLE `News` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `newsTitle` varchar(32) NOT NULL,
  `newsContent` varchar(5000) NOT NULL,
  `isPublic` tinyint(3) NOT NULL,
  `readCount` int(10) NOT NULL,
  `groupId` bigint(13) DEFAULT NULL,
  `partyId` bigint(13) DEFAULT NULL,
  `createBy` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `News_groupId` (`groupId`),
  KEY `News_partyId` (`partyId`),
  KEY `News_createBy` (`createBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `Party`
-- ----------------------------
DROP TABLE IF EXISTS `Party`;
CREATE TABLE `Party` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `partyName` varchar(20) NOT NULL,
  `partyCode` varchar(20) NOT NULL,
  `partyCover` varchar(80) DEFAULT NULL,
  `partyRemark` varchar(500) NOT NULL,
  `partyStatus` int(10) NOT NULL,
  `isPublic` tinyint(3) NOT NULL,
  `isGroup` tinyint(3) NOT NULL,
  `isCustomBuild` tinyint(3) DEFAULT NULL,
  `memberNumMin` int(10) DEFAULT NULL,
  `memberNumMax` int(10) DEFAULT NULL,
  `buildEndTime` datetime DEFAULT NULL,
  `createBy` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  `memberCount` int(10) NOT NULL,
  `hotCount` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Party_createBy` (`createBy`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `PartyGroup`
-- ----------------------------
DROP TABLE IF EXISTS `PartyGroup`;
CREATE TABLE `PartyGroup` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `partyId` bigint(13) NOT NULL,
  `groupName` varchar(20) NOT NULL,
  `groupCover` varchar(80) DEFAULT NULL,
  `groupRemark` varchar(500) NOT NULL,
  `groupStatus` int(10) NOT NULL,
  `isCustomJoin` tinyint(3) NOT NULL,
  `isSourcePublic` tinyint(3) NOT NULL,
  `createBy` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  `memberCount` int(10) NOT NULL,
  `hotCount` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `PartyGroup_partyId` (`partyId`),
  KEY `PartyGroup_createBy` (`createBy`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `PartyUser`
-- ----------------------------
DROP TABLE IF EXISTS `PartyUser`;
CREATE TABLE `PartyUser` (
  `id` varchar(32) NOT NULL,
  `partyId` bigint(13) NOT NULL,
  `groupId` bigint(13) NOT NULL,
  `loginName` varchar(40) NOT NULL,
  `loginPwd` varchar(64) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `birthDay` date DEFAULT NULL,
  `userAvatar` varchar(80) DEFAULT NULL,
  `userRemark` varchar(200) DEFAULT NULL,
  `userStatus` int(10) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `qq` varchar(11) DEFAULT NULL,
  `weiXin` varchar(20) DEFAULT NULL,
  `registerTime` datetime NOT NULL,
  `registerIP` varchar(20) NOT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `lastLoginIP` varchar(20) DEFAULT NULL,
  `hotCount` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`loginName`),
  KEY `PartyUser_partyId` (`partyId`),
  KEY `PartyUser_groupId` (`groupId`),
  KEY `PartyUser_loginName` (`loginName`),
  KEY `PartyUser_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `SysLog`
-- ----------------------------
DROP TABLE IF EXISTS `SysLog`;
CREATE TABLE `SysLog` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `userName` varchar(40) NOT NULL,
  `userIp` varchar(20) NOT NULL,
  `logAction` int(10) NOT NULL,
  `logDesc` varchar(20) NOT NULL,
  `logTime` datetime NOT NULL,
  `logObjId` bigint(13) DEFAULT NULL,
  `logContent` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `SysPartyUserLink`
-- ----------------------------
DROP TABLE IF EXISTS `SysPartyUserLink`;
CREATE TABLE `SysPartyUserLink` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `sysUserId` varchar(32) NOT NULL,
  `partyUserId` varchar(32) NOT NULL,
  `partyId` bigint(13) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `SysPartyUserLink_sysUserId` (`sysUserId`),
  KEY `SysPartyUserLink_partyUserId` (`partyUserId`),
  KEY `SysPartyUserLink_partyId` (`partyId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `SysUser`
-- ----------------------------
DROP TABLE IF EXISTS `SysUser`;
CREATE TABLE `SysUser` (
  `id` varchar(32) NOT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPwd` varchar(64) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `birthDay` date DEFAULT NULL,
  `userAvatar` varchar(80) DEFAULT NULL,
  `userRemark` varchar(200) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `qq` varchar(11) DEFAULT NULL,
  `weiXin` varchar(20) DEFAULT NULL,
  `registerTime` datetime NOT NULL,
  `registerIP` varchar(20) NOT NULL,
  `lastLoginTime` datetime NOT NULL,
  `lastLoginIP` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`loginName`),
  KEY `SysUser_loginName` (`loginName`),
  KEY `SysUser_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
