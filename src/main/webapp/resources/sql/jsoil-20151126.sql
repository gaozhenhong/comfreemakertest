-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: cmsclm
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `km_knowledge`
--

DROP TABLE IF EXISTS `km_knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `km_knowledge` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `categoryId` bigint(20) DEFAULT NULL COMMENT '分类',
  `labels` varchar(100) DEFAULT NULL COMMENT '标签',
  `fileId` bigint(20) DEFAULT NULL COMMENT '文件',
  `parameters` varchar(500) DEFAULT NULL COMMENT '其他参数',
  `fileType` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `logoId` bigint(20) DEFAULT NULL COMMENT 'Logo',
  `browseCount` int(11) DEFAULT NULL COMMENT '浏览次数',
  `downloadCount` int(11) DEFAULT NULL COMMENT '下载次数',
  `orgId` int(11) DEFAULT NULL COMMENT '所属机构',
  `createUserId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastModifyUserId` bigint(20) DEFAULT NULL COMMENT '最后修改人',
  `lastModifyTime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `km_knowledge`
--

LOCK TABLES `km_knowledge` WRITE;
/*!40000 ALTER TABLE `km_knowledge` DISABLE KEYS */;
INSERT INTO `km_knowledge` VALUES (101,'123123123',402,'',0,'[]','',0,0,0,1,1,'2015-11-23 21:41:45',1,'2015-11-23 21:56:05','',1),(121,'123123123',402,'',0,'[]','',0,0,0,1,1,'2015-11-23 21:46:30',1,'2015-11-24 08:22:20','123123',0);
/*!40000 ALTER TABLE `km_knowledge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_category`
--

DROP TABLE IF EXISTS `s_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `categoryIndex` int(11) DEFAULT NULL,
  `orgId` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUserId` varchar(50) DEFAULT NULL,
  `logoId` bigint(20) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `viewUrl` varchar(500) DEFAULT NULL,
  `deleteFlag` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_category`
--

LOCK TABLES `s_category` WRITE;
/*!40000 ALTER TABLE `s_category` DISABLE KEYS */;
INSERT INTO `s_category` VALUES (-1,'顶级分类',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(1,'组织机构分类','org',-1,1,NULL,'2015-09-28 10:00:00','1',NULL,NULL,NULL,0),(2,'系统维护','org001',1,1,NULL,'2015-09-28 10:00:00','1',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `s_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_comment`
--

DROP TABLE IF EXISTS `s_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_comment` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `commentTime` datetime DEFAULT NULL,
  `commentMemberId` bigint(20) DEFAULT NULL,
  `mark` int(11) DEFAULT NULL,
  `modelClassName` varchar(100) DEFAULT NULL,
  `modelPK` varchar(50) DEFAULT NULL,
  `deleteFlag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_comment`
--

LOCK TABLES `s_comment` WRITE;
/*!40000 ALTER TABLE `s_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_counter`
--

DROP TABLE IF EXISTS `s_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_counter` (
  `id` bigint(20) NOT NULL,
  `viewTime` datetime DEFAULT NULL,
  `modelClassName` varchar(100) DEFAULT NULL,
  `viewUserId` varchar(100) DEFAULT NULL,
  `modelPk` varchar(100) DEFAULT NULL,
  `browser` varchar(200) DEFAULT NULL,
  `operateIp` varchar(50) DEFAULT NULL,
  `operateType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_counter`
--

LOCK TABLES `s_counter` WRITE;
/*!40000 ALTER TABLE `s_counter` DISABLE KEYS */;

/*!40000 ALTER TABLE `s_counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_custom_field`
--

DROP TABLE IF EXISTS `s_custom_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_custom_field` (
  `id` bigint(20) NOT NULL,
  `fieldsetId` bigint(20) DEFAULT NULL,
  `fieldsetCode` varchar(100) DEFAULT NULL,
  `fieldName` varchar(100) DEFAULT NULL,
  `fieldType` int(11) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `fieldCode` varchar(100) DEFAULT NULL,
  `fieldIndex` int(11) DEFAULT NULL,
  `modelClassName` varchar(100) DEFAULT NULL,
  `deleteFlag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`fieldsetId`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`fieldsetId`) REFERENCES `s_custom_fieldset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义字段表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_custom_field`
--

LOCK TABLES `s_custom_field` WRITE;
/*!40000 ALTER TABLE `s_custom_field` DISABLE KEYS */;
INSERT INTO `s_custom_field` VALUES (2,1,NULL,'公司电话',1,'','telphone',5,'com.jsoil.platform.sys.vo.Dept',NULL),(3,1,NULL,'公司详细介绍',2,'','description',10,'com.jsoil.platform.sys.vo.Dept',NULL);
/*!40000 ALTER TABLE `s_custom_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_custom_field_text_value`
--

DROP TABLE IF EXISTS `s_custom_field_text_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_custom_field_text_value` (
  `id` bigint(20) NOT NULL,
  `fieldId` bigint(20) DEFAULT NULL,
  `value` text,
  `modelClassName` varchar(100) DEFAULT NULL,
  `modelClassId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_custom_field_text_value`
--

LOCK TABLES `s_custom_field_text_value` WRITE;
/*!40000 ALTER TABLE `s_custom_field_text_value` DISABLE KEYS */;
INSERT INTO `s_custom_field_text_value` VALUES (1,3,'<p><b>公司注册资金</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;伍佰万元</p><p><b>公司资质</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;壹级</p><p><b>公司质量方针</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;以尊贵的顾客为服务对象</p><p>&nbsp;&nbsp;&nbsp;&nbsp;以完美的环境为创建目标</p><p>&nbsp;&nbsp;&nbsp;&nbsp;以规范的管理为服务依托</p><p>&nbsp;&nbsp;&nbsp;&nbsp;以社会的需求为提升源泉</p><p><b>公司价值观</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;关爱业主、关爱员工、关注效率、关注质量</p><p><b>公司主营项目</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;全面物业管理服务</p><p>&nbsp;&nbsp;&nbsp;&nbsp;物业管理顾问服务</p><p>&nbsp;&nbsp;&nbsp;&nbsp;房屋租售代理、招商代理</p><p>&nbsp;&nbsp;&nbsp;&nbsp;单项绿化设计、施工</p><p>&nbsp;&nbsp;&nbsp;&nbsp;单项绿化管养</p><p>&nbsp;&nbsp;&nbsp;&nbsp;装修设计及施工</p><p>&nbsp;&nbsp;&nbsp;&nbsp;简单工程施工</p><p><b>公司荣誉</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南省十五期间物业管理先进单位</p><p>&nbsp;&nbsp;&nbsp;&nbsp;ISO9001质量管理体系认证</p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南省物业管理协会创办单位及副会长单位</p><p>&nbsp;&nbsp;&nbsp;&nbsp;昆明市物业管理协会理事单位</p><p>&nbsp;&nbsp;&nbsp;&nbsp;西山区物业管理协会理事单位</p><p>&nbsp;&nbsp;&nbsp;&nbsp;官渡区物业管理协会创办单位</p><p>&nbsp;&nbsp;&nbsp;&nbsp;昆明市守合同重信用企业</p><p><b>公司管辖楼盘荣誉</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;多个楼盘获得“昆明市物业管理示范小区”</p><p>&nbsp;&nbsp;&nbsp;&nbsp;多个楼盘获得“云南省物业管理示范小区”</p><p>&nbsp;&nbsp;&nbsp;&nbsp;多个楼盘获得“昆明市园林小区”</p><p>&nbsp;&nbsp;&nbsp;&nbsp;多个楼盘获得“昆明市绿色小区”</p><p>&nbsp;&nbsp;&nbsp;&nbsp;多个楼盘获得“昆明市宁静小区”</p><p><b>服务优势</b></p><p><b>1、物业公司对服务开发商具备丰富经验</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南巨和物业服务有限公司长期为云南巨和地产服务，对于地产公司所非常关注的房屋交房、质保期工程处理具备着丰富的经验。能够有效地保障开发商顺利交房，在房屋质保期内能有效的衔接业主与开发商之间的关系，有效地缓解由房屋可能存在的质量问题而引发的业主与开发商之间的矛盾。</p><p><b>2、物业公司对房屋前期介入具备丰富经验</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南巨和物业服务有限公司长期以来参与云南巨和地产各楼盘的前期介入，并且在物业管理顾问服务过程中长期指导楼盘的前期介入工作，积攒了丰富的经验。能有效地协助开发商的图纸审查、配套建筑的规划、设备选型、隐蔽工程及水电工程的验收、相关法律文书的编制。在保证安全的前提下有效的降低开发商的工作量。</p><p><b>3、集团公司实力雄厚</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南巨和物业服务有限公司隶属于云南巨和建设集团，集团所属房地产开发公司及建筑公司均属国家一级资质，注册资金分别在亿元以上，能够给予物业公司最大的资金及技术支持。</p><p><b>4、物业公司资金雄厚</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南巨和物业服务有限公司注册资金500万元，下属控股公司注册资金也达450万元，现金流超500万元，足以应对各种突发事件。						 </p><p><b>5、物业公司人员及技术实力雄厚</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南巨和物业服务有限公司直属人员接近400人，其中大专以上学历占到管理人员的60%以上，客服人员/工程人员持证或培训上岗合格率达到95%以上，另对房屋租售、招商代理、电梯管理、智能化系统管理、绿化管理、高空作业等都有相应的专业人才。</p><p><b>6、物业公司服务项目能有效满足业主及开发商需求</b></p><p>&nbsp;&nbsp;&nbsp;&nbsp;云南巨和物业服务有限公司服务产品众多，不但能按照物业管理协议和相关法律法规提供物业管理服务，而且涵盖了业主接房后，业主及开发商所需要的特约房屋租售及招商代理、绿化管养、房屋装修等多种服务项目，最大程度降低了业主日常房屋维护的工作量，从而提高开发商的美誉度。</p>','com.jsoil.platform.sys.vo.Dept','20150320110659944b232d-36c8-4973-a7bd-3a442eb6e7ff'),(41,3,'这是说明','com.jsoil.platform.sys.vo.Dept','20141216120124fac8c3d7-83c7-4676-ad41-1582bfb887b9');
/*!40000 ALTER TABLE `s_custom_field_text_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_custom_field_value`
--

DROP TABLE IF EXISTS `s_custom_field_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_custom_field_value` (
  `id` bigint(20) NOT NULL,
  `fieldId` bigint(20) DEFAULT NULL,
  `value` varchar(1000) DEFAULT NULL,
  `modelClassName` varchar(100) DEFAULT NULL,
  `modelClassId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义字段值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_custom_field_value`
--

LOCK TABLES `s_custom_field_value` WRITE;
/*!40000 ALTER TABLE `s_custom_field_value` DISABLE KEYS */;
INSERT INTO `s_custom_field_value` VALUES (1,2,'','com.jsoil.platform.sys.vo.Dept','20150320110659944b232d-36c8-4973-a7bd-3a442eb6e7ff'),(21,2,'','com.jsoil.platform.sys.vo.Dept','20150324161607d5a0225e-0c8d-4712-acb9-d76c34306757'),(41,2,'','com.jsoil.platform.sys.vo.Dept','20141216120124fac8c3d7-83c7-4676-ad41-1582bfb887b9');
/*!40000 ALTER TABLE `s_custom_field_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_custom_fieldset`
--

DROP TABLE IF EXISTS `s_custom_fieldset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_custom_fieldset` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `modelClassName` varchar(100) DEFAULT NULL,
  `deleteFlag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_custom_fieldset`
--

LOCK TABLES `s_custom_fieldset` WRITE;
/*!40000 ALTER TABLE `s_custom_fieldset` DISABLE KEYS */;
INSERT INTO `s_custom_fieldset` VALUES (1,'企业详细介绍','companyDetails','','com.jsoil.platform.sys.vo.Dept',0);
/*!40000 ALTER TABLE `s_custom_fieldset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_dictionary`
--

DROP TABLE IF EXISTS `s_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_dictionary` (
  `id` varchar(50) NOT NULL,
  `typeId` int(11) DEFAULT NULL,
  `dictionaryValue` varchar(1000) DEFAULT NULL,
  `name` varchar(1000) DEFAULT NULL,
  `orderIndex` int(11) DEFAULT NULL,
  `demo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_S_DICTIO_REFERENCE_S_DICTIO` (`typeId`),
  CONSTRAINT `FK_S_DICTIO_REFERENCE_S_DICTIO` FOREIGN KEY (`typeId`) REFERENCES `s_dictionary_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Dictionary';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_dictionary`
--

LOCK TABLES `s_dictionary` WRITE;
/*!40000 ALTER TABLE `s_dictionary` DISABLE KEYS */;
INSERT INTO `s_dictionary` VALUES ('20141210235522cfc9517c-cc5a-4877-9524-489afc067046',2,'男','男',1,''),('20141210235533503c4b7e-f032-4de0-8090-32b293c6bb4e',2,'女','女',2,'3'),('2015031413582675541c3f-4e24-4973-8901-244091705701',536,'shopping.jsp','商品列表',1,'');
/*!40000 ALTER TABLE `s_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_dictionary_mapping`
--

DROP TABLE IF EXISTS `s_dictionary_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_dictionary_mapping` (
  `id` varchar(50) NOT NULL,
  `dictionaryTypeId` int(11) DEFAULT NULL,
  `formName` varchar(100) DEFAULT NULL,
  `fieldName` varchar(100) DEFAULT NULL,
  `demo` varchar(100) DEFAULT NULL,
  `other` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Dictionary_Mapping';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_dictionary_mapping`
--

LOCK TABLES `s_dictionary_mapping` WRITE;
/*!40000 ALTER TABLE `s_dictionary_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_dictionary_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_dictionary_type`
--

DROP TABLE IF EXISTS `s_dictionary_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_dictionary_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `demo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Dictionary_Type';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_dictionary_type`
--

LOCK TABLES `s_dictionary_type` WRITE;
/*!40000 ALTER TABLE `s_dictionary_type` DISABLE KEYS */;
INSERT INTO `s_dictionary_type` VALUES (2,'性别',''),(536,'产品分类展现列表','产品分类展现列表');
/*!40000 ALTER TABLE `s_dictionary_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_group`
--

DROP TABLE IF EXISTS `s_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_group` (
  `id` bigint(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `demo` varchar(500) DEFAULT NULL,
  `welcomeUrl` varchar(500) DEFAULT NULL COMMENT '登录后的欢迎地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Group';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_group`
--

LOCK TABLES `s_group` WRITE;
/*!40000 ALTER TABLE `s_group` DISABLE KEYS */;
INSERT INTO `s_group` VALUES (2,'系统管理员','系统管理员',''),(100,'超级系统管理员','超级系统管理员，可以删除所有数据！！！',''),(161,'系统测试用户','','');
/*!40000 ALTER TABLE `s_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_groupmodule`
--

DROP TABLE IF EXISTS `s_groupmodule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_groupmodule` (
  `moduleId` varchar(50) NOT NULL,
  `groupId` varchar(100) NOT NULL,
  PRIMARY KEY (`moduleId`,`groupId`),
  KEY `FK_S_GroupModule_S_Group` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_GroupModule';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_groupmodule`
--

LOCK TABLES `s_groupmodule` WRITE;
/*!40000 ALTER TABLE `s_groupmodule` DISABLE KEYS */;
INSERT INTO `s_groupmodule` VALUES ('1008','100'),('1028','100'),('350','100'),('351','100'),('352','100'),('38','100'),('41','100'),('44','100'),('568','100'),('588','100'),('589','100'),('608','100'),('628','100'),('648','100'),('668','100'),('669','100'),('688','100'),('7','100'),('708','100'),('728','100'),('748','100'),('808','100'),('809','100'),('828','100'),('868','100'),('888','100'),('908','100'),('928','100'),('948','100'),('968','100'),('969','100'),('988','100'),('350','161'),('351','161'),('352','161'),('38','161'),('41','161'),('44','161'),('568','161'),('588','161'),('589','161'),('608','161'),('628','161'),('648','161'),('688','161'),('7','161'),('768','182'),('769','182'),('788','182'),('829','182'),('848','182'),('948','182'),('988','182'),('7','2'),('7','2015031715432399283459-4a76-4519-8a33-1ae16bbd3857'),('350','2015032000290244b0e6a2-1993-48fa-8c81-2e2bf7c30a77'),('38','2015032000290244b0e6a2-1993-48fa-8c81-2e2bf7c30a77'),('41','2015032000290244b0e6a2-1993-48fa-8c81-2e2bf7c30a77'),('7','2015032000290244b0e6a2-1993-48fa-8c81-2e2bf7c30a77');
/*!40000 ALTER TABLE `s_groupmodule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_groupuser`
--

DROP TABLE IF EXISTS `s_groupuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_groupuser` (
  `groupId` varchar(100) NOT NULL,
  `userId` varchar(50) NOT NULL,
  PRIMARY KEY (`groupId`,`userId`),
  KEY `FK_S_GroupUser_S_User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_GroupUser';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_groupuser`
--

LOCK TABLES `s_groupuser` WRITE;
/*!40000 ALTER TABLE `s_groupuser` DISABLE KEYS */;
INSERT INTO `s_groupuser` VALUES ('100','1'),('161','1'),('182','1'),('2','1'),('161','102'),('182','102'),('2','102');
/*!40000 ALTER TABLE `s_groupuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_keyinfo`
--

DROP TABLE IF EXISTS `s_keyinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_keyinfo` (
  `tableName` varchar(50) NOT NULL,
  `keyValue` bigint(20) DEFAULT NULL,
  `lastModifyTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tableName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_KeyInfo';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_keyinfo`
--

LOCK TABLES `s_keyinfo` WRITE;
/*!40000 ALTER TABLE `s_keyinfo` DISABLE KEYS */;
INSERT INTO `s_keyinfo` VALUES ('s_category',420,'2015-11-17 17:09'),('s_comment',60,'2015-07-30 09:45'),('s_counter',3740,'2015-08-04 11:07'),('s_custom_field',20,'2015-03-22 21:35'),('s_custom_fieldset',20,'2015-03-22 21:32'),('s_custom_field_text_value',60,'2015-08-12 20:09'),('s_custom_field_value',60,'2015-08-12 20:09'),('s_group',201,'2015-10-25 00:50'),('S_Module',1067,'2015-11-25 21:01'),('s_notice',280,'2015-11-25 12:07'),('s_order',60,'2015-07-15 17:52'),('s_organization',202,'2015-11-09 21:12'),('s_resource',2320,'2015-11-25 16:29'),('S_Site',555,'2015-03-14 13:57'),('S_User',161,'2015-11-06 15:45');
/*!40000 ALTER TABLE `s_keyinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_log`
--

DROP TABLE IF EXISTS `s_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_log` (
  `id` varchar(50) NOT NULL,
  `userId` bigint(50) DEFAULT NULL COMMENT '用户ID',
  `userName` varchar(45) DEFAULT NULL COMMENT '用户名',
  `operateTime` datetime DEFAULT NULL COMMENT '操作时间',
  `url` varchar(500) DEFAULT NULL COMMENT '请求地址',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  `browser` varchar(500) DEFAULT NULL COMMENT '浏览器信息',
  `operateType` varchar(50) DEFAULT NULL COMMENT '操作类型',
  `parameter` text COMMENT '请求参数',
  `userOrgId` int(11) DEFAULT NULL COMMENT '用户组织机构ID',
  `userOrgName` varchar(100) DEFAULT NULL COMMENT '用户组织机构名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_log`
--

LOCK TABLES `s_log` WRITE;
/*!40000 ALTER TABLE `s_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_loginlog`
--

DROP TABLE IF EXISTS `s_loginlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_loginlog` (
  `id` varchar(50) NOT NULL,
  `userId` bigint(50) DEFAULT NULL,
  `loginTime` datetime DEFAULT NULL,
  `logoutTime` datetime DEFAULT NULL,
  `clientIp` varchar(50) DEFAULT NULL,
  `clientHost` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_S_Log_S_User` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='s_loginlog';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_loginlog`
--

LOCK TABLES `s_loginlog` WRITE;
/*!40000 ALTER TABLE `s_loginlog` DISABLE KEYS */;

/*!40000 ALTER TABLE `s_loginlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_message`
--

DROP TABLE IF EXISTS `s_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_message` (
  `id` varchar(50) NOT NULL,
  `userId` bigint(20) DEFAULT NULL COMMENT '接收人',
  `readStatus` int(11) DEFAULT NULL COMMENT '是否阅读',
  `readTime` datetime DEFAULT NULL COMMENT '阅读时间',
  `messageType` varchar(50) DEFAULT NULL COMMENT '消息类型',
  `originName` varchar(50) DEFAULT NULL COMMENT '消息源',
  `messageTitle` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `link` varchar(200) DEFAULT NULL COMMENT '连接',
  `message` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `deleteFlag` int(11) DEFAULT NULL COMMENT '是否删除',
  `sentTime` datetime DEFAULT NULL COMMENT '发布时间',
  `sentPersonId` bigint(20) DEFAULT NULL COMMENT '发送人',
  `resourceIds` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_message`
--

LOCK TABLES `s_message` WRITE;
/*!40000 ALTER TABLE `s_message` DISABLE KEYS */;

/*!40000 ALTER TABLE `s_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_module`
--

DROP TABLE IF EXISTS `s_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_module` (
  `id` bigint(50) NOT NULL,
  `code` varchar(30) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `forwardUrl` varchar(200) DEFAULT NULL,
  `demo` varchar(200) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `parentId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Module';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_module`
--

LOCK TABLES `s_module` WRITE;
/*!40000 ALTER TABLE `s_module` DISABLE KEYS */;
INSERT INTO `s_module` VALUES (-1,'0','顶级结点','','',NULL,''),(7,'018','系统管理',NULL,NULL,NULL,'-1'),(38,'018000003','机构管理','/sys/organization/list.do','','','350'),(41,'018000004','系统用户管理','/sys/user/list.do','系统用户管理','','350'),(44,'018000007','用户组维护','/sys/group/list.do','','','350'),(350,'018000','基础数据管理','','',NULL,'7'),(351,'018006','权限管理','/sys/group/groupModuleList.do','','list','7'),(352,'018000009','功能模块管理','/sys/module/list.do','','','350'),(568,'018000012','分类管理','/sys/category/list.do','','','350'),(588,'018009','日志查询','','','','7'),(589,'018009003','登录日志查询','/sys/loginLog/list.do','','','588'),(608,'018009006','操作日志查询','/sys/operationLog/list.do','','','588'),(628,'018000015','资源管理','/sys/resource/list.do','','','350'),(748,'018000008','用户组用户管理','/sys/group/groupUserManagerList.do','','','350'),(1008,'018012','系统公告管理','/sys/notice/list.do','','bell','7'),(1028,'015','站内信息','/sys/message/list.do','','bell-alt','-1');
/*!40000 ALTER TABLE `s_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_notice`
--

DROP TABLE IF EXISTS `s_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_notice` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `scope` varchar(100) DEFAULT NULL COMMENT '范围',
  `scopeDetails` varchar(500) DEFAULT NULL COMMENT '范围详情',
  `publishPersonId` bigint(20) DEFAULT NULL COMMENT '发布人',
  `publishTime` datetime DEFAULT NULL COMMENT '发布时间',
  `createPersonId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `resourceIds` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_notice`
--

LOCK TABLES `s_notice` WRITE;
/*!40000 ALTER TABLE `s_notice` DISABLE KEYS */;
INSERT INTO `s_notice` VALUES (241,'第一个通知','这一个第一个通知的内容','group','[\"182\",\"100\"]',1,'2015-11-25 18:45:50',1,'2015-11-24 22:53:36',1,'系统通知',''),(261,'第二个','第二个','group','[\"182\",\"161\",\"100\",\"2\"]',1,'2015-11-25 18:23:29',1,'2015-11-25 12:07:07',1,'','');
/*!40000 ALTER TABLE `s_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_organization`
--

DROP TABLE IF EXISTS `s_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_organization` (
  `id` bigint(20) NOT NULL,
  `code` varchar(30) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `typeId` bigint(20) DEFAULT NULL,
  `parentOrgId` bigint(20) DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `inputUserId` varchar(50) DEFAULT NULL,
  `inputTime` datetime DEFAULT NULL,
  `lastModifiedUserId` varchar(50) DEFAULT NULL,
  `lastModifiedTime` datetime DEFAULT NULL,
  `postCode` varchar(6) DEFAULT NULL,
  `linkMan` varchar(50) DEFAULT NULL,
  `linkTel` varchar(50) DEFAULT NULL,
  `mobilePhone` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `demo` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_S_Dept_S_DeptType` (`typeId`),
  KEY `FK_S_Dept_S_Site` (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Dept';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_organization`
--

LOCK TABLES `s_organization` WRITE;
/*!40000 ALTER TABLE `s_organization` DISABLE KEYS */;
INSERT INTO `s_organization` VALUES (-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1,'0001','系统维护单位',2,-1,26,'1','2014-12-16 12:01:00','1','2015-08-07 13:30:00',NULL,NULL,NULL,NULL,'霖枫国际',NULL,'霖枫国际',0,NULL),(183,'1231','123',NULL,1,123,NULL,NULL,NULL,NULL,NULL,'12','3123123','','','','',NULL,'');
/*!40000 ALTER TABLE `s_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_resource`
--

DROP TABLE IF EXISTS `s_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_resource` (
  `id` bigint(20) NOT NULL,
  `resName` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `resType` varchar(50) DEFAULT NULL COMMENT '资源类型',
  `resPath` varchar(200) DEFAULT NULL COMMENT '存储路径',
  `params` varchar(500) DEFAULT NULL COMMENT '参数',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUserId` bigint(50) DEFAULT NULL COMMENT '创建人',
  `orgId` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `fileType` varchar(50) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_resource`
--

LOCK TABLES `s_resource` WRITE;
/*!40000 ALTER TABLE `s_resource` DISABLE KEYS */;

/*!40000 ALTER TABLE `s_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_site`
--

DROP TABLE IF EXISTS `s_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_site` (
  `id` int(11) NOT NULL,
  `code` varchar(30) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `abbreviation` char(1) DEFAULT NULL,
  `demo` varchar(200) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_Site';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_site`
--

LOCK TABLES `s_site` WRITE;
/*!40000 ALTER TABLE `s_site` DISABLE KEYS */;
INSERT INTO `s_site` VALUES (1,'000','中国','Z','中华人民共和国',-1),(2,'000003','北京','B',NULL,1),(3,'000025','上海','S',NULL,1),(4,'000028','天津','T',NULL,1),(5,'000034','重庆','C',NULL,1),(6,'000010','河北','H',NULL,1),(7,'000023','山西','S',NULL,1),(8,'000019','内蒙古','N',NULL,1),(9,'000018','辽宁','L',NULL,1),(10,'000015','吉林','J',NULL,1),(11,'000012','黑龙江','H',NULL,1),(12,'000016','江苏','J',NULL,1),(13,'000033','浙江','Z',NULL,1),(14,'000001','安徽','A',NULL,1),(15,'000004','福建','F',NULL,1),(16,'000017','江西','J',NULL,1),(17,'000022','山东','S',NULL,1),(18,'000011','河南','H',NULL,1),(19,'000013','湖北','H',NULL,1),(20,'000014','湖南','H',NULL,1),(21,'000006','广东','G',NULL,1),(22,'000007','广西','G',NULL,1),(23,'000009','海南','H',NULL,1),(24,'000026','四川','S',NULL,1),(25,'000008','贵州','G',NULL,1),(26,'000032','云南','Y',NULL,1),(27,'000029','西藏','X',NULL,1),(28,'000024','陕西','S',NULL,1),(29,'000005','甘肃','G',NULL,1),(30,'000021','青海','Q',NULL,1),(31,'000020','宁夏','N',NULL,1),(32,'000031','新疆','X',NULL,1),(33,'000030','香港','X',NULL,1),(34,'000002','澳门','A',NULL,1),(35,'000027','台湾','T',NULL,1),(37,'000010002','石家庄','S',NULL,6),(38,'000010003','唐山','T',NULL,6),(39,'000010001','秦皇岛','Q',NULL,6),(40,'000010','邯郸','H',NULL,6),(41,'000010004','邢台','X',NULL,6),(42,'000010','保定','B',NULL,6),(43,'000010005','张家口','Z',NULL,6),(44,'000010','承德','C',NULL,6),(45,'000010','沧州','C',NULL,6),(46,'000010','廊坊','L',NULL,6),(47,'000010','衡水','H',NULL,6),(48,'000023008','太原','T',NULL,7),(49,'000023002','大同','D',NULL,7),(50,'000023010','阳泉','Y',NULL,7),(51,'000023001','长治','C',NULL,7),(52,'000023003','晋城','J',NULL,7),(53,'000023007','朔州','S',NULL,7),(54,'000023004','晋中','J',NULL,7),(55,'000023011','运城','Y',NULL,7),(56,'000023009','忻州','X',NULL,7),(57,'000023005','临汾','L',NULL,7),(58,'000023006','吕梁','L',NULL,7),(59,'000019006','呼和浩特','H',NULL,8),(60,'000019003','包头','B',NULL,8),(61,'000019009','乌海','W',NULL,8),(62,'000019004','赤峰','C',NULL,8),(63,'000019008','通辽','T',NULL,8),(64,'000019005','鄂尔多斯','E',NULL,8),(65,'000019007','呼伦贝尔','H',NULL,8),(66,'000019010','乌兰察布盟','W',NULL,8),(67,'000019011','锡林郭勒盟','X',NULL,8),(68,'000019002','巴彦淖尔盟','B',NULL,8),(69,'000019001','阿拉善盟','A',NULL,8),(70,'000019012','兴安盟','X',NULL,8),(71,'000018012','沈阳','S',NULL,9),(72,'000018004','大连','D',NULL,9),(73,'000018001','鞍山','A',NULL,9),(74,'000018006','抚顺','F',NULL,9),(75,'000018002','本溪','B',NULL,9),(76,'000018005','丹东','D',NULL,9),(77,'000018009','锦州','J',NULL,9),(78,'000018008','葫芦岛','H',NULL,9),(79,'000018014','营口','Y',NULL,9),(80,'000018011','盘锦','P',NULL,9),(81,'000018007','阜新','F',NULL,9),(82,'000018010','辽阳','L',NULL,9),(83,'000018013','铁岭','T',NULL,9),(84,'000018003','朝阳','C',NULL,9),(85,'000015003','长春','C',NULL,10),(86,'000015004','吉林','J',NULL,10),(87,'000015008','西平','X',NULL,10),(88,'000015005','辽源','L',NULL,10),(89,'000015007','通化','T',NULL,10),(90,'000015002','白山','B',NULL,10),(91,'000015006','松原','S',NULL,10),(92,'000015001','白城','B',NULL,10),(93,'000015009','延边朝鲜族自治州','Y',NULL,10),(94,'000012003','哈尔滨','H',NULL,11),(95,'000012010','齐齐哈尔','Q',NULL,11),(96,'000012004','鹤岗','Q',NULL,11),(97,'000012011','双鸭山','S',NULL,11),(98,'000012006','鸡西','J',NULL,11),(99,'000012001','大庆','D',NULL,11),(100,'000012013','伊春','Y',NULL,11),(101,'000012008','牡丹江','M',NULL,11),(102,'000012007','佳木斯','J',NULL,11),(103,'000012009','七台河','Q',NULL,11),(104,'000012005','黑河','H',NULL,11),(105,'000012012','绥化','S',NULL,11),(106,'000012002','大兴安岭','D',NULL,11),(107,'000016004','南京','N',NULL,12),(108,'000016009','无锡','W',NULL,12),(109,'000016010','徐州','X',NULL,12),(110,'000016001','常州','C',NULL,12),(111,'000016006','苏州','S',NULL,12),(112,'000016005','南通','N',NULL,12),(113,'000016003','连云港','L',NULL,12),(114,'000016002','淮安','H',NULL,12),(115,'000016011','盐城','Y',NULL,12),(116,'000016012','扬州','Y',NULL,12),(117,'000016013','镇江','Z',NULL,12),(118,'000016008','泰州','T',NULL,12),(119,'000016007','宿迁','S',NULL,12),(120,'000033001','杭州','H',NULL,13),(121,'000033006','宁波','N',NULL,13),(122,'000033010','温州','W',NULL,13),(123,'000033003','嘉兴','J',NULL,13),(124,'000033002','湖州','H',NULL,13),(125,'000033008','绍兴','S',NULL,13),(126,'000033004','金华','J',NULL,13),(127,'000033007','衢州','Q',NULL,13),(128,'000033011','舟山','Z',NULL,13),(129,'000033009','台州','T',NULL,13),(130,'000033005','丽水','L',NULL,13),(131,'000001009','合肥','H',NULL,14),(132,'000001006','芙湖','F',NULL,14),(133,'000001016','蛙埠','W',NULL,14),(134,'000001011','淮南','H',NULL,14),(135,'000001013','马鞍山','M',NULL,14),(136,'000001010','淮北','H',NULL,14),(137,'000001015','铜陵','T',NULL,14),(138,'000001001','安庆','A',NULL,14),(139,'000001012','黄山','H',NULL,14),(140,'000001004','滁州','C',NULL,14),(141,'000001007','阜阳','F',NULL,14),(142,'000001014','宿州','S',NULL,14),(143,'000001002','巢湖','C',NULL,14),(144,'000001005','大安','D',NULL,14),(145,'000001008','毫州','H',NULL,14),(146,'000001003','池州','C',NULL,14),(147,'000001017','宣城','X',NULL,14),(148,'000004001','福州','F',NULL,15),(149,'000004008','厦门','X',NULL,15),(150,'000004005','莆田','F',NULL,15),(151,'000004007','三明','S',NULL,15),(152,'000004006','泉州','Q',NULL,15),(153,'000004009','漳州','Z',NULL,15),(154,'000004003','南平','N',NULL,15),(155,'000004002','龙岩','L',NULL,15),(156,'000004004','宁德','N',NULL,15),(157,'000017006','南昌','N',NULL,16),(158,'000017004','景德镇','J',NULL,16),(159,'000017007','萍乡','P',NULL,16),(160,'000017009','新余','X',NULL,16),(161,'000017005','九江','J',NULL,16),(162,'000017011','鹰潭','Y',NULL,16),(163,'000017002','赣州','G',NULL,16),(164,'000017003','吉安','J',NULL,16),(165,'000017010','宜春','Y',NULL,16),(166,'000017001','抚州','W',NULL,16),(167,'000017008','上饶','S',NULL,16),(168,'000022005','济南','J',NULL,17),(169,'000022010','青岛','Q',NULL,17),(170,'000022017','淄博','Z',NULL,17),(171,'000022016','枣庄','Z',NULL,17),(172,'000022003','东营','D',NULL,17),(173,'000022014','潍坊','W',NULL,17),(174,'000022015','烟台','Y',NULL,17),(175,'000022013','威海','W',NULL,17),(176,'000022006','济宁','J',NULL,17),(177,'000022012','泰安','T',NULL,17),(178,'000022011','日照','R',NULL,17),(179,'000022007','莱芜','L',NULL,17),(180,'000022002','德州','D',NULL,17),(181,'000022009','临沂','L',NULL,17),(182,'000022008','聊城','L',NULL,17),(183,'000022001','滨州','B',NULL,17),(184,'000022004','菏泽','H',NULL,17),(185,'000011016','郑州','Z',NULL,18),(186,'000011005','开封','K',NULL,18),(187,'000011006','洛阳','L',NULL,18),(188,'000011009','平顶山','P',NULL,18),(189,'000011004','焦作','J',NULL,18),(190,'000011002','鹤壁','H',NULL,18),(191,'000011013','新乡','X',NULL,18),(192,'000011001','安阳','A',NULL,18),(193,'000011010','濮阳','P',NULL,18),(194,'000011015','许昌','X',NULL,18),(195,'000011007','漯河','L',NULL,18),(196,'000011011','三门峡','S',NULL,18),(197,'000011008','南阳','N',NULL,18),(198,'000011012','商丘','S',NULL,18),(199,'000011014','信阳','X',NULL,18),(200,'000011017','周口','Z',NULL,18),(201,'000011018','驻马店','Z',NULL,18),(202,'000011003','济源','J',NULL,18),(203,'000013012','武汉','W',NULL,19),(204,'000013004','黄石','H',NULL,19),(205,'000013015','襄樊','X',NULL,19),(206,'000013009','十堰','S',NULL,19),(207,'000013006','荆州','J',NULL,19),(208,'000013017','宜昌','Y',NULL,19),(209,'000013005','荆门','J',NULL,19),(210,'000013001','鄂州','E',NULL,19),(211,'000013016','孝感','X',NULL,19),(212,'000013003','黄冈','H',NULL,19),(213,'000013014','咸宁','X',NULL,19),(214,'000013010','随州','S',NULL,19),(215,'000013013','仙桃','X',NULL,19),(216,'000013011','天门','T',NULL,19),(217,'000013007','潜江','Q',NULL,19),(218,'000013008','神农架','S',NULL,19),(219,'000013002','恩施土家族苗族自治州','E',NULL,19),(220,'000014001','长沙','C',NULL,20),(221,'000014013','株州','Z',NULL,20),(222,'000014008','湘潭','X',NULL,20),(223,'000014004','衡阳','H',NULL,20),(224,'000014007','邵阳','S',NULL,20),(225,'000014011','岳阳','Y',NULL,20),(226,'000014002','常德','C',NULL,20),(227,'000014012','张家界','Z',NULL,20),(228,'000014010','益阳','Y',NULL,20),(229,'000014003','郴州','C',NULL,20),(230,'000014005','怀化','H',NULL,20),(231,'000014006','娄底','L',NULL,20),(232,'000014009','湘西土家族苗族自治州','X',NULL,20),(233,'000006004','广州','G',NULL,21),(234,'000006015','深圳','S',NULL,21),(235,'000006021','珠海','Z',NULL,21),(236,'000006012','汕头','S',NULL,21),(237,'000006014','韶关','S',NULL,21),(238,'000006003','佛山','F',NULL,21),(239,'000006007','江门','J',NULL,21),(240,'000006018','湛江','Z',NULL,21),(241,'000006009','茂名','M',NULL,21),(242,'000006019','肇庆','Z',NULL,21),(243,'000006006','惠州','H',NULL,21),(244,'000006010','梅州','M',NULL,21),(245,'000006013','汕尾','S',NULL,21),(246,'000006005','河源','H',NULL,21),(247,'000006016','阳江','J',NULL,21),(248,'000006011','清远','Q',NULL,21),(249,'000006002','东莞','D',NULL,21),(250,'000006020','中山','Z',NULL,21),(251,'000006001','潮州','C',NULL,21),(252,'000006008','揭阳','J',NULL,21),(253,'000006017','云浮','Y',NULL,21),(254,'000007011','南宁','N',NULL,22),(255,'000007010','柳州','L',NULL,22),(256,'000007006','桂林','G',NULL,22),(257,'000007013','梧州','W',NULL,22),(258,'000007002','北海','B',NULL,22),(259,'000007004','防城港','F',NULL,22),(260,'000007012','钦州','Q',NULL,22),(261,'000007005','贵港','G',NULL,22),(262,'000007014','玉林','Y',NULL,22),(263,'000007001','百色','B',NULL,22),(264,'000007008','贺州','H',NULL,22),(265,'000007007','河池','H',NULL,22),(266,'000007009','来宾','L',NULL,22),(267,'000007003','崇左','C',NULL,22),(268,'000009008','海口','H',NULL,23),(269,'000009014','三亚','S',NULL,23),(270,'000009018','五指山','W',NULL,23),(271,'000009012','琼海','Q',NULL,23),(272,'000009005','儋州','Z',NULL,23),(273,'000009017','文昌','W',NULL,23),(274,'000009016','万宁','W',NULL,23),(275,'000009007','东方','D',NULL,23),(276,'000009004','澄迈','C',NULL,23),(277,'000009006','定安','D',NULL,23),(278,'000009015','屯昌','T',NULL,23),(279,'000009010','临高','L',NULL,23),(280,'000009001','白沙黎族自治县','B',NULL,23),(281,'000009003','昌江黎族自治县','C',NULL,23),(282,'000009009','乐东黎族自治县','L',NULL,23),(283,'000009011','陵水黎族自治县','L',NULL,23),(284,'000009002','保定黎族苗族自治县','B',NULL,23),(285,'000009013','琼中黎族苗族自治县','Q',NULL,23),(286,'000026003','成都','C',NULL,24),(287,'000026021','自供','Z',NULL,24),(288,'000026016','攀枝花','P',NULL,24),(289,'000026011','泸州','L',NULL,24),(290,'000026005','德阳','D',NULL,24),(291,'000026013','绵阳','M',NULL,24),(292,'000026008','广元','G',NULL,24),(293,'000026017','遂宁','S',NULL,24),(294,'000026014','内江','N',NULL,24),(295,'000026009','乐山','L',NULL,24),(296,'000026015','南冲','N',NULL,24),(297,'000026019','宜宾','Y',NULL,24),(298,'000026007','广安','G',NULL,24),(299,'000026004','达州','D',NULL,24),(300,'000026012','眉山','M',NULL,24),(301,'000026018','雅安','Y',NULL,24),(302,'000026002','巴中','B',NULL,24),(303,'000026020','资阳','Z',NULL,24),(304,'000026001','阿坝藏族羌族自治州','A',NULL,24),(305,'000026006','甘孜藏族自治州','G',NULL,24),(306,'000026010','凉山彝族自治州','L',NULL,24),(307,'000008003','贵阳','G',NULL,25),(308,'000008004','六盘水','L',NULL,25),(309,'000008009','遵义','Z',NULL,25),(310,'000008001','安顺','A',NULL,25),(311,'000008008','铜仁','T',NULL,25),(312,'000008002','毕节','B',NULL,25),(313,'000008007','黔西南布依族苗族自治州','Q',NULL,25),(314,'000008005','黔东南苗族侗族自治州','Q',NULL,25),(315,'000008006','黔南布依族苗族自治州','Q',NULL,25),(316,'000032007','昆明','K',NULL,26),(317,'000032011','曲靖','Q',NULL,26),(318,'000032015','玉溪','Y',NULL,26),(319,'000032001','保山','B',NULL,26),(320,'000032016','昭通','Z',NULL,26),(321,'000032008','丽江','L',NULL,26),(322,'000032012','思茅','S',NULL,26),(323,'000032009','临沧','L',NULL,26),(324,'000032013','文山','W',NULL,26),(325,'000032006','红河','H',NULL,26),(326,'000032014','版纳','X',NULL,26),(327,'000032002','楚雄','C',NULL,26),(328,'000032003','大理','D',NULL,26),(329,'000032004','德宏','D',NULL,26),(330,'000032010','怒江','N',NULL,26),(331,'000032005','迪庆','D',NULL,26),(332,'000029003','拉萨','L',NULL,27),(333,'000029005','那曲','N',NULL,27),(334,'000029002','昌都','C',NULL,27),(335,'000029007','山南','S',NULL,27),(336,'000029006','日喀则','R',NULL,27),(337,'000029001','阿里','A',NULL,27),(338,'000029004','林芝','L',NULL,27),(339,'000024007','西安','X',NULL,28),(340,'000024005','铜川','T',NULL,28),(341,'000024002','宝鸡','B',NULL,28),(342,'000024008','咸阳','X',NULL,28),(343,'000024006','渭南','W',NULL,28),(344,'000024009','延安','Y',NULL,28),(345,'000024003','汉中','H',NULL,28),(346,'000024010','榆林','Y',NULL,28),(347,'000024001','安康','A',NULL,28),(348,'000024004','商洛','S',NULL,28),(349,'000005007','兰州','L',NULL,29),(350,'000005005','金昌','J',NULL,29),(351,'000005001','白银','B',NULL,29),(352,'000005012','天水','T',NULL,29),(353,'000005004','嘉峪关','J',NULL,29),(354,'000005013','武威','W',NULL,29),(355,'000005014','张掖','Z',NULL,29),(356,'000005010','平凉','P',NULL,29),(357,'000005006','酒泉','J',NULL,29),(358,'000005011','庆阳','Q',NULL,29),(359,'000005002','定西','D',NULL,29),(360,'000005009','陇南','L',NULL,29),(361,'000005008','临夏回族自治州','L',NULL,29),(362,'000005003','甘南藏族自治州','G',NULL,29),(363,'000021007','西宁','X',NULL,30),(364,'000021003','海东','H',NULL,30),(365,'000021002','海北藏族自治州','H',NULL,30),(366,'000021006','黄南藏族自治州','H',NULL,30),(367,'000021004','海南藏族自治州','H',NULL,30),(368,'000021001','果洛藏族自治州','G',NULL,30),(369,'000021008','玉树藏族自治州','Y',NULL,30),(370,'000021005','海西蒙古族藏族自治州','H',NULL,30),(371,'000020004','银川','Y',NULL,31),(372,'000020002','石嘴山','S',NULL,31),(373,'000020003','吴忠','W',NULL,31),(374,'000020001','固原','G',NULL,31),(375,'000031014','乌鲁木齐','W',NULL,32),(376,'000031009','克拉玛依','K',NULL,32),(377,'000031011','石河子','S',NULL,32),(378,'000031002','阿拉尔','A',NULL,32),(379,'000031012','图木舒克','T',NULL,32),(380,'000031015','五家渠','W',NULL,32),(381,'000031013','吐鲁','T',NULL,32),(382,'000031006','哈密','H',NULL,32),(383,'000031007','和田','H',NULL,32),(384,'000031001','阿克苏','A',NULL,32),(385,'000031008','喀什','K',NULL,32),(386,'000031010','克孜勒苏柯尔克孜自治州','K',NULL,32),(387,'000031003','巴音郭楞蒙古自治州','B',NULL,32),(388,'000031005','昌吉回族自治州','J',NULL,32),(389,'000031004','博尔塔拉蒙古自治州','B',NULL,32),(390,'000031016','伊犁哈萨克自治州','Y',NULL,32),(391,'000027011','台北','T',NULL,35),(392,'000027001','高雄','G',NULL,35),(393,'000027004','基隆','J',NULL,35),(394,'000027016','台中','T',NULL,35),(395,'000027014','台南','T',NULL,35),(396,'000027019','新竹','X',NULL,35),(397,'000027005','嘉义','J',NULL,35),(398,'000027012','台北县','T',NULL,35),(399,'000027021','宜兰县','Y',NULL,35),(400,'000027020','新竹县','X',NULL,35),(401,'000027018','桃园县','T',NULL,35),(402,'000027007','苗栗县','M',NULL,35),(403,'000027017','台中县','T',NULL,35),(404,'000027023','彰化县','P',NULL,35),(405,'000027008','南投县','N',NULL,35),(406,'000027006','嘉义县','J',NULL,35),(407,'000027022','云林县','Y',NULL,35),(408,'000027015','台南县','T',NULL,35),(409,'000027002','高雄县','G',NULL,35),(410,'000027010','屏东县','P',NULL,35),(411,'000027013','台东县','T',NULL,35),(412,'000027003','花莲县','H',NULL,35),(413,'000027009','澎湖县','P',NULL,35),(515,'000032003001','祥云县','X',NULL,328);
/*!40000 ALTER TABLE `s_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_user`
--

DROP TABLE IF EXISTS `s_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_user` (
  `id` bigint(50) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `loginName` varchar(20) DEFAULT NULL,
  `loginPswd` varchar(50) DEFAULT NULL,
  `orgId` bigint(20) DEFAULT NULL,
  `createUserId` bigint(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `sequenceNo` int(11) DEFAULT NULL,
  `demo` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `logoId` bigint(50) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  KEY `FK_S_User_S_Dept` (`orgId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S_User';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_user`
--

LOCK TABLES `s_user` WRITE;
/*!40000 ALTER TABLE `s_user` DISABLE KEYS */;
INSERT INTO `s_user` VALUES (1,'系统管理员','sysadmin','9DA1273385336B8D0FF2F460EB1E7FDA',1,NULL,NULL,NULL,'',1,'13888421304',2301),(102,'李春雨','lichunyu','96E79218965EB72C92A549DD5A330112',1,1,'2015-10-25 15:22:57',NULL,'',1,'13888421304',0),(144,'周云川','zhouyunchuan','96E79218965EB72C92A549DD5A330112',1,1,'2015-11-06 15:46:55',NULL,'',1,'',0),(145,'陈雪','chenxue','96E79218965EB72C92A549DD5A330112',1,1,'2015-11-06 15:47:07',NULL,'',1,'',0);
/*!40000 ALTER TABLE `s_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-25 22:27:01
