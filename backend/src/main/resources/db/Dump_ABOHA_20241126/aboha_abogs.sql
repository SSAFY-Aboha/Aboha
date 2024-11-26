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
-- Table structure for table `abogs`
--

DROP TABLE IF EXISTS `abogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abogs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `attraction_id` int DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `comment_count` bigint NOT NULL DEFAULT '0',
  `like_count` bigint NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `abogs_user_fk_idx` (`user_id`),
  KEY `abogs_attraction_fk_idx` (`attraction_id`),
  CONSTRAINT `abogs_attraction_fk` FOREIGN KEY (`attraction_id`) REFERENCES `attractions` (`no`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `abogs_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abogs`
--

LOCK TABLES `abogs` WRITE;
/*!40000 ALTER TABLE `abogs` DISABLE KEYS */;
INSERT INTO `abogs` VALUES (5,2,12217,'한남동 뼈찜 맛집','내 인생 뼈찜 맛집 ?\r\n세상 사람들아 제발 뼈찜 먹으러 가자\r\n유명해 아니 더 이상 유명해지지마',4,3,'2024-11-26 03:34:06','2024-11-26 05:01:05',0),(6,1,3822,'퇴근 후 동기들과 아보하 ?','퇴근하고 동기들과 강남에서 한 잔!',4,3,'2024-11-26 03:38:22','2024-11-26 05:01:05',0),(7,3,40592,'경주월드 다녀옴! ?','경주월드 드라켄 3번 탔다!!\r\n거의 수직으로 떨어져서 스릴감이 미쳤음 ☠',4,3,'2024-11-26 03:42:01','2024-11-26 05:01:05',0),(8,2,41433,'경주 월정교, 야경 아보하 ?','야경 맛집 월정교에 다녀왔습니다!\r\n아름다운 조명과 고풍스러운 교각이 어우러져 정말 멋진 밤이었어요.\r\n경주의 대표적인 야경 명소로 강력 추천합니다.',4,2,'2024-11-26 04:28:34','2024-11-26 05:01:05',0),(9,1,12783,'제주에서 인천으로 가는 길 ?','제주에서 인천으로 가는 비행기 안에서 찍은 사진입니다.\r\n맑은 하늘과 구름이 너무 아름다웠고, 창밖을 보며 여유로운 시간을 보냈어요.\r\n짧았지만 알찬 제주 여행을 마무리하며, 다시 일상으로 돌아갈 준비를 했습니다!\r\n여행의 마지막 순간도 이런 멋진 풍경과 함께해서 행복했어요.',4,1,'2024-11-26 04:40:47','2024-11-26 05:01:05',0);
/*!40000 ALTER TABLE `abogs` ENABLE KEYS */;
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
