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
-- Table structure for table `middle_type`
--

DROP TABLE IF EXISTS `middle_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `middle_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `main_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `main_type_id_idx` (`main_type_id`),
  CONSTRAINT `main_type_id` FOREIGN KEY (`main_type_id`) REFERENCES `main_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `middle_type`
--

LOCK TABLES `middle_type` WRITE;
/*!40000 ALTER TABLE `middle_type` DISABLE KEYS */;
INSERT INTO `middle_type` VALUES (1,'Mobile phones and Accessories',1),(2,'Tablets and Accessories',1),(4,'Computers and Monitors',2),(5,'PC components',2),(6,'Software',2),(7,'Peripheral equipment',2),(8,'Printers and Scanners',2),(9,'Wireless and Security systems',2),(10,'TV',3),(11,'TV accessories',3),(12,'Video projectors and screens',3),(13,'Home theater systems',3),(14,'Electronics',3),(15,'Gaming consoles and Games',3),(16,'Photo and Video',3),(17,'Photo and Video accessories',3),(18,'Refrigerators',4),(19,'Washing machines and Dryers',4),(20,'Dishwashers',4),(21,'Cooking stoves and Microwaves',4),(22,'Boilers, Air conditioners and Heating devices',4),(23,'Vacuum cleaners and Irons',5),(24,'Coffee machines and Juicers',5),(25,'Kitchen appliances',5),(26,'Women\'s purses and Accessories',6),(27,'Women\'s shoes',6),(28,'Women\'s clothing',6),(29,'Men\'s accessories',6),(30,'Men\'s shoes',6),(31,'Men\'s clothing',6),(32,'Kids\' accessories',6),(33,'Kids\' shoes',6),(34,'Kids\' clothing',6),(35,'Accessories and Devices for personal care',7),(36,'Oral hygiene',7),(37,'Perfumes',7),(38,'Make up and manicure',7),(39,'Health',7),(40,'Cosmetics and Products for personal care',7),(41,'Furnitures and Mattresses',8),(42,'Home textiles',8),(43,'Cooking and Serving',8),(44,'Cleaning and Maintenance',8),(45,'Everyday products',8),(46,'Garden',8),(47,'Decoration',8),(48,'Pet\'s food',8),(49,'Pet\'s accessories',8),(50,'Online bookstore',9),(51,'Movies',9),(52,'Music',9),(53,'Office',9),(54,'Books',9),(55,'School supplies',9),(56,'Diapers and Accessories',10),(57,'Baby food and Accessories',10),(58,'Care and Hygiene',10),(59,'Baby strollers',10),(60,'For kid\'s room',10),(61,'Maternity and Pregnancy',10),(62,'Games and Toys',10),(63,'Outdoor games',10),(64,'Educational games',10),(66,'Fitness and Food supplements',11),(67,'Team sports',11),(68,'Suitcases and Traveling bags',11),(69,'Free time',11),(70,'Tennis, Badminton and Table tennis',11),(71,'Water sports',11),(72,'Bicycles, Equipment and Accessories',11),(73,'Rollers and Skateboards',11),(74,'Camping',11),(75,'Hunting and Fishing',11),(76,'Sport\'s clothing',11),(77,'Tires',12),(78,'Auto electronics',12),(79,'Electric vehicles',12),(80,'Insurances',12),(81,'Electrical equipment',12),(82,'For construction',12),(83,'Garden equipment',12),(84,'Lighting',12);
/*!40000 ALTER TABLE `middle_type` ENABLE KEYS */;
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
