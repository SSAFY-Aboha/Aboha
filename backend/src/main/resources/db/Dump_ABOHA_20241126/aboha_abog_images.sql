-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: aboha
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abog_images`
--

DROP TABLE IF EXISTS `abog_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abog_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abog_id` int NOT NULL,
  `image_url` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `abog_images_abog_fk_idx` (`abog_id`),
  CONSTRAINT `abog_images_abog_fk` FOREIGN KEY (`abog_id`) REFERENCES `abogs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abog_images`
--

LOCK TABLES `abog_images` WRITE;
/*!40000 ALTER TABLE `abog_images` DISABLE KEYS */;
INSERT INTO `abog_images` VALUES (1,5,'/uploads/abog/1e60df36c20b40838c22562099690b73_IMG_2778.jpg'),(2,5,'/uploads/abog/61b4399fc9fb4e048ad0b50d6fe92829_IMG_2779.jpg'),(3,5,'/uploads/abog/1287a70965d7489fa0f197a8e0c8e51c_IMG_2784.jpg'),(4,6,'/uploads/abog/05e2d29a50c147088614deb3e1449046_gunbae.jpg'),(5,7,'/uploads/abog/7c1e6c8fdde6478699b09422b0071d52_IMG_9585.jpg'),(6,7,'/uploads/abog/d537ec3b1cd24086be63ae8e1750e7f2_IMG_9582.jpg'),(7,8,'/uploads/abog/e856e87555c1443b93b5b4bfca8e343e_C6A2EEE5-77A7-4946-BF6C-166DACC1687F.jpg'),(8,8,'/uploads/abog/3a05cedebf374141b1e62a4234cbf07d_2C944A4B-2115-4D0E-9B52-2F177BF5B93F.jpg'),(9,9,'/uploads/abog/aee1f527ecd042a58d16084806bc4ddf_IMG_5645.jpg');
/*!40000 ALTER TABLE `abog_images` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 14:09:10
