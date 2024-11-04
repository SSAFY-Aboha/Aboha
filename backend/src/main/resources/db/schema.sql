SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema aboha
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `aboha` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `aboha`;

-- -----------------------------------------------------
-- Table `aboha`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`sidos` (
                                               `id` INT NOT NULL,
                                               `name` VARCHAR(20) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`guguns` (
                                                `id` INT NOT NULL,
                                                `sido_id` INT NOT NULL,
                                                `name` VARCHAR(20) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `guguns_sido_to_sidos_id_fk_idx` (`sido_id` ASC),
    CONSTRAINT `guguns_sido_to_sidos_id_fk`
    FOREIGN KEY (`sido_id`)
    REFERENCES `aboha`.`sidos` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`contenttypes` (
                                                      `id` INT NOT NULL,
                                                      `name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`attractions` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `content_id` INT NULL DEFAULT NULL,
                                                     `sido_id` INT NULL,
                                                     `gugun_id` INT NULL,
                                                     `content_type_id` INT NULL,
                                                     `first_image1` VARCHAR(100) NULL DEFAULT NULL,
    `first_image2` VARCHAR(100) NULL DEFAULT NULL,
    `map_level` INT NULL DEFAULT NULL,
    `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
    `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
    `tel` VARCHAR(20) NULL DEFAULT NULL,
    `addr1` VARCHAR(100) NULL DEFAULT NULL,
    `addr2` VARCHAR(100) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `attractions_contenttype_fk_idx` (`content_type_id` ASC),
    INDEX `attractions_sido_fk_idx` (`sido_id` ASC),
    INDEX `attractions_gugun_fk_idx` (`gugun_id` ASC),
    CONSTRAINT `attractions_sido_fk`
    FOREIGN KEY (`sido_id`)
    REFERENCES `aboha`.`sidos` (`id`),
    CONSTRAINT `attractions_gugun_fk`
    FOREIGN KEY (`gugun_id`)
    REFERENCES `aboha`.`guguns` (`id`),
    CONSTRAINT `attractions_contenttype_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `aboha`.`contenttypes` (`id`)
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`users` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC),
    UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`trip` (
                                              `id` INT NOT NULL AUTO_INCREMENT,
                                              `user_id` INT NOT NULL,
                                              `attraction_id` INT NOT NULL,
                                              `title` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `view_cnt` INT NOT NULL DEFAULT 0,
    `like_cnt` INT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `trip_user_fk_idx` (`user_id` ASC),
    INDEX `trip_attraction_fk_idx` (`attraction_id` ASC),
    CONSTRAINT `trip_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `trip_attraction_fk`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `aboha`.`attractions` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`likes` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `user_id` INT NOT NULL,
                                               `trip_id` INT NOT NULL,
                                               PRIMARY KEY (`id`),
    INDEX `likes_user_fk_idx` (`user_id` ASC),
    INDEX `likes_trip_fk_idx` (`trip_id` ASC),
    CONSTRAINT `likes_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `likes_trip_fk`
    FOREIGN KEY (`trip_id`)
    REFERENCES `aboha`.`trip` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`comments` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `user_id` INT NOT NULL,
                                                  `trip_id` INT NOT NULL,
                                                  `content` TEXT NOT NULL,
                                                  PRIMARY KEY (`id`),
    INDEX `comments_user_fk_idx` (`user_id` ASC),
    INDEX `comments_trip_fk_idx` (`trip_id` ASC),
    CONSTRAINT `comments_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `comments_trip_fk`
    FOREIGN KEY (`trip_id`)
    REFERENCES `aboha`.`trip` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`trip_images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`trip_images` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `trip_id` INT NOT NULL,
                                                     `image_url` VARCHAR(1000) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `trip_images_trip_fk_idx` (`trip_id` ASC),
    CONSTRAINT `trip_images_trip_fk`
    FOREIGN KEY (`trip_id`)
    REFERENCES `aboha`.`trip` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
