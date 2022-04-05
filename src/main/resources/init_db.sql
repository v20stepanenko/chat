CREATE SCHEMA IF NOT EXISTS `chat` DEFAULT CHARACTER SET utf8;
USE `chat`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `chat`.`users` (
                                `id` INT NOT NULL AUTO_INCREMENT,
                                `name` VARCHAR(45) NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `messages`;

CREATE TABLE `chat`.`messages` (
                                   `id` INT NOT NULL AUTO_INCREMENT,
                                   `text` TEXT(1000) NULL,
                                   `owner_id` INT NULL,
                                   `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   INDEX `id_owner_idx` (`owner_id` ASC) VISIBLE,
                                   CONSTRAINT `id_owner`
                                       FOREIGN KEY (`owner_id`)
                                           REFERENCES `chat`.`users` (`id`)
                                           ON DELETE NO ACTION
                                           ON UPDATE NO ACTION)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

