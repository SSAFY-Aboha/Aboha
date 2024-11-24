-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: aboha
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `profile_image_url` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'changjo@ssafy.com','$2a$10$lGSlYQYTuRgHOgeeYRjfIeJb/OZUp.dkfhBgaWSatBYjdVvXva3bS','짱조',NULL,'2024-11-23 23:32:30','2024-11-23 23:32:30',0),(2,'yerim@ssafy.com','$2a$10$jMKE4Lcy7Q1e0aObPn7h.uXK6sGIML9SJ1WvY/CyDX9K4XqMRnBJO','옝임','/uploads/user/39a97fabd6214957a0dcc5ef9532f06e_yerim.jpg','2024-11-23 23:33:09','2024-11-24 21:34:01',0),(3,'hyeonwoo@ssafy.com','$2a$10$vdWYJQDJE60eR9ggLYSMW.Dj/0JKbeFj/wlMLBKe76gKINFA/Sf.6','현우',NULL,'2024-11-23 23:33:38','2024-11-23 23:33:38',0),(4,'minsoo@ssafy.com','$2a$10$kaPHytrkSIrLkEvYBzBN3Ob4wjcLdaoKW42S2MXnOak5ccI9iWCte','민수',NULL,'2024-11-23 23:33:54','2024-11-23 23:33:54',0),(5,'seoyeon@ssafy.com','$2a$10$b/vyoD.2XfcoARnVwGy9SOx4iufJzRFm5dVgp36aBfwGMVF1y6HMK','서연',NULL,'2024-11-23 23:34:10','2024-11-23 23:34:10',0),(6,'jiyong@ssafy.com','$2a$10$AE1NREzNR60v70Psq8QzlevqmCLsNlZXTtKxNoCtjQKQba5uCPQIG','지용',NULL,'2024-11-23 23:34:34','2024-11-23 23:34:34',0),(7,'eunji@ssafy.com','$2a$10$ibzLziBt62ETAerEhFDaX.ZMfzMyoR4AfYymXE1lMjzhQMe.0a4yS','은지',NULL,'2024-11-23 23:34:50','2024-11-23 23:34:50',0),(8,'taewoo@ssafy.com','$2a$10$zhYeBZKCa3YnrV4mdrj4DOYoFlaptHnxngKRycUnxEganBJWkoVTK','태우',NULL,'2024-11-23 23:35:12','2024-11-23 23:35:12',0),(9,'junho@ssafy.com','$2a$10$0MQsOKI.FQxvdeybR1luIOeAKaG9SMDPZM3vNl7z38GNudWq8f5ui','준호',NULL,'2024-11-23 23:35:27','2024-11-23 23:35:27',0),(10,'soyoung@ssafy.com','$2a$10$.qy/v1KZwrGZ8BudbWq4leCQ12I2lC4vqyVVV4NzWREkzlFsy0Fwy','소영',NULL,'2024-11-23 23:35:47','2024-11-23 23:35:47',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-25  6:37:53
