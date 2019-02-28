CREATE DATABASE  IF NOT EXISTS `fpsdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fpsdb`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: fpsdb
-- ------------------------------------------------------
-- Server version	8.0.13

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
-- Table structure for table `fr_account`
--

DROP TABLE IF EXISTS `fr_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(50) NOT NULL,
  `FR_Role_id` int(11) NOT NULL,
  `password` varchar(300) NOT NULL,
  `name` varchar(300) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `extra_point` int(11) DEFAULT NULL,
  `report_point` int(11) DEFAULT NULL,
  `user_image` longblob,
  `national_id` varchar(100) DEFAULT NULL,
  `national_id_created_date` bigint(20) unsigned DEFAULT NULL,
  `date_of_birth` bigint(20) unsigned DEFAULT NULL,
  `create_time` bigint(20) unsigned DEFAULT NULL,
  `update_time` bigint(20) unsigned DEFAULT NULL,
  `delete_time` bigint(20) unsigned DEFAULT NULL,
  `note` varchar(300) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `editor_id` int(11) DEFAULT NULL,
  `avatar` longblob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `fk_FR_Account_FR_Role1_idx` (`FR_Role_id`),
  KEY `fk_FR_Account_FR_Account1_idx` (`editor_id`),
  CONSTRAINT `fk_FR_Account_FR_Account1` FOREIGN KEY (`editor_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Account_FR_Role1` FOREIGN KEY (`FR_Role_id`) REFERENCES `fr_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_account`
--

LOCK TABLES `fr_account` WRITE;
/*!40000 ALTER TABLE `fr_account` DISABLE KEYS */;
INSERT INTO `fr_account` VALUES (1,'azx',1,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Nguyễn Tinh Tế','azx@gmail.com',0,0,NULL,NULL,NULL,NULL,1550633969525,NULL,NULL,'',1,NULL,NULL),(2,'222',2,'$2a$10$6qMXDoz0FQagxcOGlKj06OJE1HbNqDWExap/6C8LgGcjiCJfWkd46','Hồ Tuấn Tài','tinhte@gmail.com',100,0,NULL,NULL,NULL,NULL,1550633969525,NULL,NULL,'',1,NULL,NULL),(3,'333',3,'$2a$10$pfNJ/jSJSdB2S1jf1ZNJg.Pd/MSss3g92TlvoWPS/Ysmz.v67IilO','Bà Đầm Già','sara@yahoo.com',0,50,NULL,NULL,NULL,NULL,1550633969525,NULL,NULL,'',1,NULL,NULL),(4,'0932012241',2,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Donal Trump','trummpFirst@tinhte.com',0,0,NULL,NULL,NULL,NULL,1549960380000,NULL,NULL,NULL,0,NULL,NULL),(5,'0924124412',1,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Kim Jong Un','kim@bdhai.ind',0,0,NULL,NULL,NULL,NULL,1549960387000,NULL,NULL,NULL,1,NULL,NULL),(6,'01245212512',2,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Trần Thị Lươu','luong@gmail.com',0,0,NULL,NULL,NULL,NULL,1519834868000,NULL,NULL,NULL,1,NULL,NULL),(7,'01254512125',2,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Ông Như Sơn','sson124@yahoo.com.vn',20,0,NULL,NULL,NULL,NULL,1519834468000,NULL,NULL,NULL,1,NULL,NULL),(8,'4125125125',3,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Vững Thái Bình','thaibtV12@gmail.us',0,100,NULL,NULL,NULL,NULL,1519834867000,NULL,NULL,NULL,1,NULL,NULL),(9,'52214135412',3,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Tài Hoàn Hảo','ttyh@vzw.nwl',10,24,NULL,NULL,NULL,NULL,1519834812000,NULL,NULL,NULL,1,NULL,NULL),(10,'1111111111',2,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Ba Và Con','222@xwo.aus',100,12,NULL,NULL,NULL,NULL,1519834824000,NULL,NULL,NULL,0,NULL,NULL),(11,'3333333333',3,'$2a$10$zOSH64xUh6O0sMW4ttfGa.2RmB2cRoyllJ9vVXM5/B/TVFftRfYHO','Shipper Tốt Tính','tasksh@yahoo.cmb',65,2,NULL,NULL,NULL,NULL,1519834824000,NULL,NULL,NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `fr_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_city`
--

DROP TABLE IF EXISTS `fr_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `postal_code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_city`
--

LOCK TABLES `fr_city` WRITE;
/*!40000 ALTER TABLE `fr_city` DISABLE KEYS */;
INSERT INTO `fr_city` VALUES (1,'Hà Nội','100000'),(2,'Hồ Chí Minh','700000'),(3,'Đà Nẵng','550000'),(4,'Cần Thơ','94000'),(5,'Đắk Lắk','630000'),(6,'Bắc Giang','230000'),(7,'Điện Biên','	380000'),(8,'Hà Tĩnh','	480000'),(9,'Hưng Yên','	160000'),(10,'Long An','	850000'),(11,'Nghệ An','	460000');
/*!40000 ALTER TABLE `fr_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_district`
--

DROP TABLE IF EXISTS `fr_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) DEFAULT NULL,
  `FR_City_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_District_FR_City1_idx` (`FR_City_id`),
  CONSTRAINT `fk_FR_District_FR_City1` FOREIGN KEY (`FR_City_id`) REFERENCES `fr_city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_district`
--

LOCK TABLES `fr_district` WRITE;
/*!40000 ALTER TABLE `fr_district` DISABLE KEYS */;
INSERT INTO `fr_district` VALUES (1,'	Quận 1',2),(2,'Quận 12',2),(3,'Quận Thủ Đức',2),(4,'Quận 9',2),(5,'Quận Gò Vấp',2),(6,'	Quận Bình Thạnh',2),(7,'Quận Tân Bình',2),(8,'Quận Tân Phú',2),(9,'	Quận Phú Nhuận',2),(10,'Quận 2',2),(11,'Quận 3',2),(12,'Quận 10',2),(13,'Quận 11',2),(14,'Quận 4',2),(15,'Quận 5',2),(16,'Quận 6',2),(17,'Quận 8',2),(18,'Quận Bình Tân',2),(19,'Quận 7',2),(20,'Huyện Củ Chi',2),(21,'Huyện Hóc Môn',2),(22,'Huyện Bình Chánh',2),(23,'Huyện Nhà Bè',2),(24,'Huyện Cần Giờ',2),(25,'Quận Ba Đình',1),(26,'Quận Hoàn Kiếm',1),(27,'Quận Hai Bà Trưng',1),(28,'Quận Đống Đa',1),(29,'Quận Tây Hồ',1),(30,'Quận Cầu Giấy',1),(31,'Quận Thanh Xuân',1),(32,'Quận Hoàng Mai',1),(33,'Quận Long Biên',1),(34,'Huyện Từ Liêm',1),(35,'Huyện Thanh Trì',1),(36,'Huyện Gia Lâm',1),(37,'Huyện Đông Anh',1),(38,'Huyện Sóc Sơn',1),(39,'Quận Hà Đông',1),(40,'Thị xã Sơn Tây',1),(41,'Huyện Ba Vì',1),(42,'Huyện Phúc Thọ',1),(43,'Huyện Thạch Thất',1),(44,'Huyện Quốc Oai',1),(45,'Huyện Chương Mỹ',1),(46,'Huyện Đan Phượng',1),(47,'Huyện Hoài Đức',1),(48,'Huyện Thanh Oai',1),(49,'Huyện Mỹ Đức',1),(50,'Huyện Ứng Hoà',1),(51,'Huyện Thường Tín',1),(52,'Huyện Phú Xuyên',1),(53,'Huyện Mê Linh',1);
/*!40000 ALTER TABLE `fr_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_order`
--

DROP TABLE IF EXISTS `fr_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FR_Account_id` int(11) NOT NULL,
  `FR_Shipper_id` int(11) DEFAULT NULL,
  `order_code` varchar(50) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `book_time` bigint(20) unsigned DEFAULT NULL,
  `receive_time` bigint(20) unsigned DEFAULT NULL,
  `shipper_earn` double DEFAULT NULL,
  `ship_address` varchar(300) DEFAULT NULL,
  `FR_District_id` int(11) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `customer_description` varchar(300) DEFAULT NULL,
  `create_time` bigint(20) unsigned DEFAULT NULL,
  `update_time` bigint(20) unsigned DEFAULT NULL,
  `delete_time` bigint(20) unsigned DEFAULT NULL,
  `note` varchar(300) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `editor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_order_FR_Shipper1_idx` (`FR_Shipper_id`),
  KEY `fk_FR_Order_FR_Account1_idx` (`FR_Account_id`),
  KEY `fk_FR_Order_FR_District1_idx` (`FR_District_id`),
  KEY `fk_FR_Order_FR_Account2_idx` (`editor_id`),
  CONSTRAINT `fk_FR_Order_FR_Account1` FOREIGN KEY (`FR_Account_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Order_FR_Account2` FOREIGN KEY (`editor_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Order_FR_District1` FOREIGN KEY (`FR_District_id`) REFERENCES `fr_district` (`id`),
  CONSTRAINT `fk_FR_order_FR_Shipper1` FOREIGN KEY (`FR_Shipper_id`) REFERENCES `fr_shipper` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_order`
--

LOCK TABLES `fr_order` WRITE;
/*!40000 ALTER TABLE `fr_order` DISABLE KEYS */;
INSERT INTO `fr_order` VALUES (1,1,1,'qwerty',10,1551253272000,NULL,NULL,'HCM',NULL,NULL,NULL,'Nothing',NULL,NULL,NULL,NULL,3,NULL),(2,1,1,'test',20,1551342780000,NULL,NULL,'HN',NULL,NULL,NULL,'We need it',NULL,NULL,NULL,NULL,1,NULL),(3,7,2,'Sáng ',300,1553073668000,NULL,NULL,'ĐN',NULL,NULL,NULL,'ok first of all',NULL,NULL,NULL,NULL,2,NULL),(4,11,3,'Chiều',4,1551073668000,NULL,NULL,'Cần Thơ',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(5,5,2,'Tối',124,1552070668000,NULL,NULL,'Nha Trang',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,NULL),(6,2,2,'VN Vô Địch',421,1552076680500,NULL,NULL,'Quận Nine',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(7,8,3,'Và Bạn',412,1552077680500,NULL,NULL,'Quận Tree',NULL,NULL,NULL,'A, B, C, D',NULL,NULL,NULL,NULL,3,NULL);
/*!40000 ALTER TABLE `fr_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_order_detail`
--

DROP TABLE IF EXISTS `fr_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FR_Order_id` int(11) NOT NULL,
  `FR_Product_id` int(11) NOT NULL,
  `unit_price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Order_Detail_FR_Product1_idx` (`FR_Product_id`),
  KEY `fk_FR_Order_Detail_FR_Order1_idx` (`FR_Order_id`),
  CONSTRAINT `fk_FR_Order_Detail_FR_Order1` FOREIGN KEY (`FR_Order_id`) REFERENCES `fr_order` (`id`),
  CONSTRAINT `fk_FR_Order_Detail_FR_Product1` FOREIGN KEY (`FR_Product_id`) REFERENCES `fr_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_order_detail`
--

LOCK TABLES `fr_order_detail` WRITE;
/*!40000 ALTER TABLE `fr_order_detail` DISABLE KEYS */;
INSERT INTO `fr_order_detail` VALUES (1,1,1,12000,2),(2,1,2,13000,1),(3,2,4,28000,2),(5,2,4,47000,4),(6,4,10,90000,1),(10,4,6,53000,2),(11,7,7,100000,2),(12,3,10,90000,5);
/*!40000 ALTER TABLE `fr_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_payment_information`
--

DROP TABLE IF EXISTS `fr_payment_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_payment_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FR_Account_id` int(11) NOT NULL,
  `FR_Payment_Type_id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Payment_Information_FR_Account1_idx` (`FR_Account_id`),
  KEY `fk_FR_Payment_Information_FR_Payment_Type1_idx` (`FR_Payment_Type_id`),
  CONSTRAINT `fk_FR_Payment_Information_FR_Account1` FOREIGN KEY (`FR_Account_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Payment_Information_FR_Payment_Type1` FOREIGN KEY (`FR_Payment_Type_id`) REFERENCES `fr_payment_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_payment_information`
--

LOCK TABLES `fr_payment_information` WRITE;
/*!40000 ALTER TABLE `fr_payment_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `fr_payment_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_payment_type`
--

DROP TABLE IF EXISTS `fr_payment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_payment_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_payment_type`
--

LOCK TABLES `fr_payment_type` WRITE;
/*!40000 ALTER TABLE `fr_payment_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `fr_payment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_price_level`
--

DROP TABLE IF EXISTS `fr_price_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_price_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_name` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_price_level`
--

LOCK TABLES `fr_price_level` WRITE;
/*!40000 ALTER TABLE `fr_price_level` DISABLE KEYS */;
INSERT INTO `fr_price_level` VALUES (1,'1','Fresh recruited',0.6),(2,'2','3 mth exp or 10mil earned',0.7),(3,'3','2 years exp',0.8);
/*!40000 ALTER TABLE `fr_price_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_product`
--

DROP TABLE IF EXISTS `fr_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(300) DEFAULT NULL,
  `FR_Store_id` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `product_image` longblob,
  `rating` double DEFAULT NULL,
  `rating_count` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `create_time` bigint(20) unsigned DEFAULT NULL,
  `update_time` bigint(20) unsigned DEFAULT NULL,
  `delete_time` bigint(20) unsigned DEFAULT NULL,
  `note` varchar(300) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `editor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Product_FR_Store1_idx` (`FR_Store_id`),
  KEY `fk_FR_Product_FR_Account1_idx` (`editor_id`),
  CONSTRAINT `fk_FR_Product_FR_Account1` FOREIGN KEY (`editor_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Product_FR_Store1` FOREIGN KEY (`FR_Store_id`) REFERENCES `fr_store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_product`
--

LOCK TABLES `fr_product` WRITE;
/*!40000 ALTER TABLE `fr_product` DISABLE KEYS */;
INSERT INTO `fr_product` VALUES (1,'Lemon juice',1,19000,NULL,NULL,0,'Hot dayyyyy',1550633969525,NULL,NULL,'',1,NULL),(2,'Black coffee',1,28000,NULL,NULL,0,'Warm Morning',1550633969525,NULL,NULL,'',1,NULL),(3,'Milk cocoa ',1,22000,NULL,NULL,0,'When you are boring',1550633969525,NULL,NULL,'',1,NULL),(4,'Okinawa milk tea',2,47000,NULL,NULL,0,'It nothing to comment',1550633969525,NULL,NULL,'',1,NULL),(5,'Strawberry oreo smoothie',2,59000,NULL,NULL,0,'When you are tired, try me',1550633969525,NULL,NULL,'',1,NULL),(6,'Mango matcha latte',2,53000,NULL,NULL,0,'Mango mango, what make you feed mangooooo',1550633969525,NULL,NULL,'',1,NULL),(7,'Rich Dad Poor Dad',3,100000,NULL,NULL,100,'It is book',1550633969725,NULL,NULL,NULL,1,NULL),(8,'LaLaLa',5,120000,NULL,NULL,2,'4G Connect Everything',1550633969125,NULL,NULL,NULL,1,NULL),(9,'Sony Music',8,99999,NULL,NULL,42,'Waooooo',1550637969125,NULL,NULL,NULL,1,NULL),(10,'Hand Crack',7,90000,NULL,NULL,45,'Know Us, Know Happy',1550634969125,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `fr_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_rating`
--

DROP TABLE IF EXISTS `fr_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FR_Account_id` int(11) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  `FR_Product_id` int(11) DEFAULT NULL,
  `FR_Store_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Store_Rating_FR_Account1_idx` (`FR_Account_id`),
  KEY `fk_FR_Rating_FR_Product1_idx` (`FR_Product_id`),
  KEY `fk_FR_Rating_FR_Store1_idx` (`FR_Store_id`),
  CONSTRAINT `fk_FR_Rating_FR_Product1` FOREIGN KEY (`FR_Product_id`) REFERENCES `fr_product` (`id`),
  CONSTRAINT `fk_FR_Rating_FR_Store1` FOREIGN KEY (`FR_Store_id`) REFERENCES `fr_store` (`id`),
  CONSTRAINT `fk_FR_Store_Rating_FR_Account1` FOREIGN KEY (`FR_Account_id`) REFERENCES `fr_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_rating`
--

LOCK TABLES `fr_rating` WRITE;
/*!40000 ALTER TABLE `fr_rating` DISABLE KEYS */;
INSERT INTO `fr_rating` VALUES (1,1,25,2,1),(2,1,56,3,1),(3,4,12,2,3),(4,5,75,5,5),(5,7,24,10,8),(6,9,97,9,8);
/*!40000 ALTER TABLE `fr_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_role`
--

DROP TABLE IF EXISTS `fr_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_role`
--

LOCK TABLES `fr_role` WRITE;
/*!40000 ALTER TABLE `fr_role` DISABLE KEYS */;
INSERT INTO `fr_role` VALUES (1,'ROLE_ADMIN',''),(2,'ROLE_MEMBER',''),(3,'ROLE_SHIPPER','');
/*!40000 ALTER TABLE `fr_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_schedule`
--

DROP TABLE IF EXISTS `fr_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FR_Store_id` int(11) NOT NULL,
  `FR_Weekday_id` int(11) NOT NULL,
  `open_hour` int(11) DEFAULT NULL,
  `open_minute` int(11) DEFAULT NULL,
  `close_hour` int(11) DEFAULT NULL,
  `close_minute` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Store_has_FR_Date_FR_Store1_idx` (`FR_Store_id`),
  KEY `fk_FR_Schedule_FR_Weekday1_idx` (`FR_Weekday_id`),
  CONSTRAINT `fk_FR_Schedule_FR_Weekday1` FOREIGN KEY (`FR_Weekday_id`) REFERENCES `fr_weekday` (`id`),
  CONSTRAINT `fk_FR_Store_has_FR_Date_FR_Store1` FOREIGN KEY (`FR_Store_id`) REFERENCES `fr_store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_schedule`
--

LOCK TABLES `fr_schedule` WRITE;
/*!40000 ALTER TABLE `fr_schedule` DISABLE KEYS */;
INSERT INTO `fr_schedule` VALUES (1,1,1,0,0,23,59),(2,1,2,0,0,23,59),(3,1,3,0,0,23,59),(4,1,4,0,0,23,59),(5,1,5,0,0,23,59),(6,1,6,0,0,23,59),(7,1,7,0,0,23,59),(8,2,1,9,30,22,0),(9,2,2,9,30,22,0),(10,2,3,9,30,22,0),(11,2,4,9,30,22,0),(12,2,5,9,30,22,0),(13,2,6,9,30,22,0),(14,2,7,9,30,22,0);
/*!40000 ALTER TABLE `fr_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_shipper`
--

DROP TABLE IF EXISTS `fr_shipper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_shipper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FR_Account_id` int(11) NOT NULL,
  `bike_registration_id` varchar(100) DEFAULT NULL,
  `introduce` varchar(300) DEFAULT NULL,
  `national_id_front_image` longblob,
  `national_id_back_image` longblob,
  `sum_revenue` double DEFAULT NULL,
  `bike_registration_front_image` longblob,
  `bike_registration_back_image` longblob,
  `FR_Price_Level_id` int(11) NOT NULL,
  `FR_Source_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Shipper_FR_Resource1_idx` (`FR_Source_id`),
  KEY `fk_FR_Shipper_FR_Account1_idx` (`FR_Account_id`),
  KEY `fk_FR_Shipper_FR_Price_Level1_idx` (`FR_Price_Level_id`),
  CONSTRAINT `fk_FR_Shipper_FR_Account1` FOREIGN KEY (`FR_Account_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Shipper_FR_Price_Level1` FOREIGN KEY (`FR_Price_Level_id`) REFERENCES `fr_price_level` (`id`),
  CONSTRAINT `fk_FR_Shipper_FR_Source1` FOREIGN KEY (`FR_Source_id`) REFERENCES `fr_source` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_shipper`
--

LOCK TABLES `fr_shipper` WRITE;
/*!40000 ALTER TABLE `fr_shipper` DISABLE KEYS */;
INSERT INTO `fr_shipper` VALUES (1,8,'325234523','Fast, Security',NULL,NULL,NULL,NULL,NULL,1,1),(2,9,'124154363','Smart, Angry',NULL,NULL,NULL,NULL,NULL,2,2),(3,11,'412541242','Oh Good',NULL,NULL,NULL,NULL,NULL,3,5);
/*!40000 ALTER TABLE `fr_shipper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_source`
--

DROP TABLE IF EXISTS `fr_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_source`
--

LOCK TABLES `fr_source` WRITE;
/*!40000 ALTER TABLE `fr_source` DISABLE KEYS */;
INSERT INTO `fr_source` VALUES (1,'facebook','Facebook ads, Facebook homepage...'),(2,'magazine','Articles about FPS'),(3,'social','From friends, family...'),(4,'other',''),(5,'none','Prefer not to say');
/*!40000 ALTER TABLE `fr_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_store`
--

DROP TABLE IF EXISTS `fr_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_name` varchar(300) NOT NULL,
  `address` varchar(300) NOT NULL,
  `FR_District_id` int(11) NOT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `store_image` longblob,
  `rating` double DEFAULT NULL,
  `rating_count` int(11) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `create_time` bigint(20) unsigned DEFAULT NULL,
  `update_time` bigint(20) unsigned DEFAULT NULL,
  `delete_time` bigint(20) unsigned DEFAULT NULL,
  `note` varchar(300) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `editor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_FR_Store_FR_District1_idx` (`FR_District_id`),
  KEY `fk_FR_Store_FR_Account1_idx` (`editor_id`),
  CONSTRAINT `fk_FR_Store_FR_Account1` FOREIGN KEY (`editor_id`) REFERENCES `fr_account` (`id`),
  CONSTRAINT `fk_FR_Store_FR_District1` FOREIGN KEY (`FR_District_id`) REFERENCES `fr_district` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_store`
--

LOCK TABLES `fr_store` WRITE;
/*!40000 ALTER TABLE `fr_store` DISABLE KEYS */;
INSERT INTO `fr_store` VALUES (1,'Moda House Coffee-NCCT','11 Nguyễn Oanh, P. 10',5,106.67927390539103,10.82767617410066,NULL,NULL,0,NULL,1550633969525,NULL,NULL,'',1,NULL),(2,'Trà Sữa Gong Cha - 貢茶 ','83 Hồ Tùng Mậu',1,106.70378033619113,10.772064463698943,NULL,NULL,0,NULL,1550634269525,NULL,NULL,'',1,NULL),(3,'Book Store-FEFA','Hồ con rùa',38,NULL,NULL,NULL,NULL,0,NULL,1549960380000,NULL,NULL,NULL,1,NULL),(4,'FPT Shopping','Thành Đống Đa',34,NULL,NULL,NULL,NULL,0,NULL,1549528380000,NULL,NULL,NULL,1,NULL),(5,'Viettel','Trần Hưng Đạo',6,NULL,NULL,NULL,NULL,0,NULL,1546849980000,NULL,NULL,NULL,1,NULL),(6,'DVTH146','Nguyễn Cư Trinh',1,NULL,NULL,NULL,NULL,100,NULL,694231200000,NULL,NULL,NULL,1,NULL),(7,'Pet Toy','12 Đại Sơn Hào',2,NULL,NULL,NULL,NULL,10,NULL,1546847980000,NULL,NULL,NULL,1,NULL),(8,'Sony Ericsion','25 Chí Thiên',23,NULL,NULL,NULL,NULL,20,NULL,1546849980000,NULL,NULL,NULL,0,NULL),(9,'Haivkl','100 Tề Thiên',45,NULL,NULL,NULL,NULL,78,NULL,1546849981000,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `fr_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fr_weekday`
--

DROP TABLE IF EXISTS `fr_weekday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fr_weekday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fr_weekday`
--

LOCK TABLES `fr_weekday` WRITE;
/*!40000 ALTER TABLE `fr_weekday` DISABLE KEYS */;
INSERT INTO `fr_weekday` VALUES (1,'Sunday'),(2,'Monday'),(3,'Tuesday'),(4,'Wednesday'),(5,'Thursday'),(6,'Friday'),(7,'Saturday');
/*!40000 ALTER TABLE `fr_weekday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'fpsdb'
--

--
-- Dumping routines for database 'fpsdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-01  1:52:59
