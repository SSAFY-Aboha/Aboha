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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `abog_id` int NOT NULL,
  `parent_id` int DEFAULT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `comments_user_fk_idx` (`user_id`),
  KEY `comments_abog_fk_idx` (`abog_id`),
  KEY `comments_parent_fk_idx` (`parent_id`),
  CONSTRAINT `comments_abog_fk` FOREIGN KEY (`abog_id`) REFERENCES `abogs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comments_parent_fk` FOREIGN KEY (`parent_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comments_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,1,5,NULL,'저도 여기 가봤는데 정말 맛있었어요!','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(2,2,5,NULL,'추천 감사합니다! 꼭 가보고 싶네요.','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(3,3,5,NULL,'혹시 예약해야 하나요?','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(4,4,5,NULL,'뼈찜 최고죠! 다른 메뉴도 좋나요?','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(5,5,6,NULL,'강남에서 한 잔이라니 너무 부럽습니다!','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(6,6,6,NULL,'다음엔 초대해주세요~','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(7,7,6,NULL,'어떤 메뉴를 드셨나요?','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(8,8,6,NULL,'퇴근 후 모임이라니 최고의 시간 같아요.','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(9,9,7,NULL,'드라켄 진짜 무섭죠! 저도 2번 타고 힘들었어요.','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(10,10,7,NULL,'다음엔 같이 가요!','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(11,1,7,NULL,'경주월드에서 가장 추천하는 놀이기구는 뭐예요?','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(12,2,7,NULL,'정말 스릴 넘치는 곳이네요!','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(13,3,8,NULL,'월정교 정말 멋져요. 사진도 잘 나왔네요!','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(14,4,8,NULL,'다음엔 낮에도 가보세요! 또 다른 매력이 있답니다.','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(15,5,8,NULL,'가족과 함께 가면 좋을까요?','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(16,6,8,NULL,'야경이 이렇게 멋지다니 다음엔 꼭 가야겠어요.','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(17,7,9,NULL,'비행기 안 풍경 정말 멋지네요!','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(18,8,9,NULL,'제주에 또 가고 싶어요.','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(19,9,9,NULL,'여행 잘 마치셨나요?','2024-11-26 04:54:21','2024-11-26 04:54:21',0),(20,10,9,NULL,'이런 순간들을 남길 수 있다니 정말 부럽습니다.','2024-11-26 04:54:21','2024-11-26 04:54:21',0);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 14:09:08
