-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: ivag
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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `picture_url` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `middle_type_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `description` varchar(1000) NOT NULL,
  `discount` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `middle_type_id_idx` (`middle_type_id`),
  CONSTRAINT `middle_type_id` FOREIGN KEY (`middle_type_id`) REFERENCES `middle_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `middle_type_idd` FOREIGN KEY (`middle_type_id`) REFERENCES `middle_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (26,'Acer Predator 15 Gaming Laptop - G9-593-77WF','http://res.cloudinary.com/dxnmejm7r/image/upload/v1547388511/tta1rcrjlpyd5sy9qs4l.png',1999,4,6,'Windows 10 Home Intel® Core™ i7-6700HQ processor 2.60 GHz 15.6\" Full HD (1920 x 1080) 16:9 Nvidia® GeForce® GTX 1070 with 8 GB Dedicated Memory 16 GB, DDR4 SDRAM 1 TB HDD + 256 GB SSD 30-day Microsoft Office trial included',10),(27,'Acer Swift 3 Laptop - SF315-41-R8PP','http://res.cloudinary.com/dxnmejm7r/image/upload/v1547388603/wuvranu6bmszwymscvqs.png',749,4,1,'Windows 10 Home AMD Ryzen 5 2500U processor Quad-core 2 GHz 15.6\" Full HD (1920 x 1080) 16:9 IPS AMD Radeon Vega 8 with Shared Memory 8 GB, DDR4 SDRAM 256 GB SSD 30-day Microsoft Office trial included',15),(28,'Acer Spin 7 Laptop - SP714-51-M024','http://res.cloudinary.com/dxnmejm7r/image/upload/v1547388670/tteqm0erswj9fd0jtgjg.png',1199,4,14,'Windows 10 Home Intel® Core™ i7-7Y75 processor 1.30 GHz 14\" Full HD (1920 x 1080) 16:9 IPS Touchscreen Intel® Shared Memory 8 GB, LPDDR3 256 GB SSD 30-day Microsoft Office trial included',45),(29,'MSI GS65 Stealth THIN-051 15.6\"','http://res.cloudinary.com/dxnmejm7r/image/upload/v1547388782/tp8xqumsf5dbh9kudzyw.jpg',1799,4,2,'THIN-051 15.6\" 144Hz 7ms Ultra Thin Gaming Laptop GTX 1060 6G, i7-8750H 6 Core, 16GB RAM, 256GB SSD, RGB KB VR Ready, Metal, Black w/ Gold Diamond Cut, Win 10 Home 64bit',7);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-04 20:42:07
