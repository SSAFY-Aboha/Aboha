-- -----------------------------------------------------
-- Schema aboha
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `aboha` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `aboha` ;

-- -----------------------------------------------------
-- Table `aboha`.`sidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`sidos` (
                                               `no` INT NOT NULL AUTO_INCREMENT,
                                               `sido_code` INT NULL DEFAULT NULL,
                                               `sido_name` VARCHAR(20) NULL DEFAULT NULL,
    PRIMARY KEY (`no`),
    UNIQUE INDEX `sido_code_UNIQUE` (`sido_code` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 18
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`guguns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`guguns` (
                                                `no` INT NOT NULL AUTO_INCREMENT,
                                                `sido_code` INT NULL DEFAULT NULL,
                                                `gugun_code` INT NULL DEFAULT NULL,
                                                `gugun_name` VARCHAR(20) NULL DEFAULT NULL,
    PRIMARY KEY (`no`),
    INDEX `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code` ASC) VISIBLE,
    INDEX `gugun_code_idx` (`gugun_code` ASC) VISIBLE,
    CONSTRAINT `guguns_sido_to_sidos_cdoe_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `aboha`.`sidos` (`sido_code`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 235
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`contenttypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`contenttypes` (
                                                      `content_type_id` INT NOT NULL,
                                                      `content_type_name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`content_type_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`attractions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`attractions` (
                                                     `no` INT NOT NULL AUTO_INCREMENT,
                                                     `content_id` INT NULL DEFAULT NULL,
                                                     `title` VARCHAR(500) NULL DEFAULT NULL,
    `content_type_id` INT NULL DEFAULT NULL,
    `area_code` INT NULL DEFAULT NULL,
    `si_gun_gu_code` INT NULL DEFAULT NULL,
    `first_image1` VARCHAR(100) NULL DEFAULT NULL,
    `first_image2` VARCHAR(100) NULL DEFAULT NULL,
    `map_level` INT NULL DEFAULT NULL,
    `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
    `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
    `tel` VARCHAR(20) NULL DEFAULT NULL,
    `addr1` VARCHAR(100) NULL DEFAULT NULL,
    `addr2` VARCHAR(100) NULL DEFAULT NULL,
    `homepage` VARCHAR(1000) NULL DEFAULT NULL,
    `overview` VARCHAR(10000) NULL DEFAULT NULL,
    `view_count` BIGINT NOT NULL DEFAULT 0,
    `like_count` BIGINT NOT NULL DEFAULT 0,
    `review_count` BIGINT NOT NULL DEFAULT 0,
    `rating_sum` DECIMAL(5,1) NOT NULL DEFAULT 0.0, -- 평점 합계 (소수점 첫째자리 포함)
    PRIMARY KEY (`no`),
    INDEX `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id` ASC) VISIBLE,
    INDEX `attractions_sido_to_sidos_code_fk_idx` (`area_code` ASC) VISIBLE,
    INDEX `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code` ASC) VISIBLE,
    CONSTRAINT `attractions_area_to_sidos_code_fk`
    FOREIGN KEY (`area_code`)
    REFERENCES `aboha`.`sidos` (`sido_code`),
    CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk`
    FOREIGN KEY (`si_gun_gu_code`)
    REFERENCES `aboha`.`guguns` (`gugun_code`),
    CONSTRAINT `attractions_typeid_to_types_typeid_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `aboha`.`contenttypes` (`content_type_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 56650
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
    `profile_image_url` TEXT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC),
    UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`attraction_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`attraction_likes` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `user_id` INT NOT NULL,
                                                          `attraction_id` INT NOT NULL,
                                                          PRIMARY KEY (`id`),
    INDEX `attraction_likes_user_fk_idx` (`user_id` ASC),
    INDEX `attraction_likes_attraction_fk_idx` (`attraction_id` ASC),
    CONSTRAINT `attraction_likes_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `attraction_likes_attraction_fk`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `aboha`.`attractions` (`no`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`reviews` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `user_id` INT NOT NULL,
                                                 `attraction_id` INT NOT NULL,
                                                 `rating` INT NOT NULL CHECK (`rating` BETWEEN 1 AND 5), -- 1~5 점 제한
    `content` TEXT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `reviews_user_fk_idx` (`user_id` ASC),
    INDEX `reviews_attraction_fk_idx` (`attraction_id` ASC),
    CONSTRAINT `reviews_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `reviews_attraction_fk`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `aboha`.`attractions` (`no`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`abogs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`abogs` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `user_id` INT NOT NULL,
                                               `attraction_id` INT NULL,
                                               `title` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `comment_count` BIGINT NOT NULL DEFAULT 0,
    `like_count` BIGINT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `abogs_user_fk_idx` (`user_id` ASC),
    INDEX `abogs_attraction_fk_idx` (`attraction_id` ASC),
    CONSTRAINT `abogs_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `abogs_attraction_fk`
    FOREIGN KEY (`attraction_id`)
    REFERENCES `aboha`.`attractions` (`no`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`abog_images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`abog_images` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `abog_id` INT NOT NULL,
                                                     `image_url` TEXT NOT NULL,
                                                     PRIMARY KEY (`id`),
    INDEX `abog_images_abog_fk_idx` (`abog_id` ASC),
    CONSTRAINT `abog_images_abog_fk`
    FOREIGN KEY (`abog_id`)
    REFERENCES `aboha`.`abogs` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`comments` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `user_id` INT NOT NULL,
                                                  `abog_id` INT NOT NULL,
                                                  `parent_id` INT NULL DEFAULT NULL,
                                                  `content` TEXT NOT NULL,
                                                  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `comments_user_fk_idx` (`user_id` ASC),
    INDEX `comments_abog_fk_idx` (`abog_id` ASC),
    INDEX `comments_parent_fk_idx` (`parent_id` ASC),
    CONSTRAINT `comments_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `comments_abog_fk`
    FOREIGN KEY (`abog_id`)
    REFERENCES `aboha`.`abogs` (`id`),
    CONSTRAINT `comments_parent_fk`
    FOREIGN KEY (`parent_id`)
    REFERENCES `aboha`.`comments` (`id`)
    ON DELETE CASCADE
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `aboha`.`abog_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aboha`.`abog_likes` (
                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                    `user_id` INT NOT NULL,
                                                    `abog_id` INT NOT NULL,
                                                    PRIMARY KEY (`id`),
    INDEX `abog_likes_user_fk_idx` (`user_id` ASC),
    INDEX `abog_likes_abog_fk_idx` (`abog_id` ASC),
    CONSTRAINT `abog_likes_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `aboha`.`users` (`id`),
    CONSTRAINT `abog_likes_abog_fk`
    FOREIGN KEY (`abog_id`)
    REFERENCES `aboha`.`abogs` (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
