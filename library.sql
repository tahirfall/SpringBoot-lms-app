-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.31 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



CREATE DATABASE IF NOT EXISTS `lms_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lms_db`;


CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `role` varchar(45) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;


REPLACE INTO `users` (`id`, `name`, `email`, `address`, `username`, `password`, `role`, `enabled`) VALUES
	(1, 'Tahir FALL', 'tahir@gmail.com', 'Pikine', 'tahir', '$2a$10$Ok5BIHXFJpJFoFUPp.SFYun9O0fS1sh.5yuhuOn/t5wW6yvaFh94y', 'ROLE_USER', 1),
	(2, 'Admin', 'admin@gmail.com', 'Dakar', 'admin', '$2a$10$52xQraLQm0wG0rMnAz.IPuNm/tFRLsVWtM.675eaUQY9aGym0DyMa', 'ROLE_ADMIN', 1);



CREATE TABLE IF NOT EXISTS `authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nationality` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;


REPLACE INTO `authors` (`id`, `name`, `nationality`) VALUES
	(1, 'Plasencia, Juan Luis', 'Perú'),
	(2, 'Llorens Antonia', 'España'),
	(3, 'Sanabria, Carmelo', 'España'),
	(4, 'Richter, Helmut', 'Escocia'),
	(7, 'Llorens, Antonia', 'España'),
	(8, 'Dulac, George', 'EEUU'),
	(9, 'Davolio, Nancy', 'Croacia'),
	(10, 'Bazilian, Eric', 'Brasil'),
	(11, 'Bertomeu, Andrés', 'España');



CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `publisher` varchar(45) DEFAULT NULL,
  `ISBN` varchar(45) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL COMMENT '0 = No, 1 = Si',
  `author_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book-author_idx` (`author_id`),
  CONSTRAINT `fk_book-author` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;


REPLACE INTO `books` (`id`, `title`, `publisher`, `ISBN`, `disponible`, `author_id`) VALUES
	(1, 'El tránsito terreno', 'Larrosa Mas, S.L', '84-121-2310-2', 1, 4),
	(2, 'Poemas intrínsecos', 'Deloria Editores', '84-305-0473-7', 1, 2),
	(3, 'La mente y el sentir', 'Larrosa Mas, S.L', '84-226-2128-2', 1, 1),
	(4, 'Avances en Arquitectura', 'TechniBooks', '84-473-0120-6', 0, 4),
	(5, 'Canto de esperanza', 'McCoy Hill', '84-444-0027-3', 0, 9),
	(36, 'Edicion', 'edicion', 'edicion', 0, 3),
	(37, 'Procesadores cuánticos', 'Grisham Publishing', '84-212-2121-2', 1, 10),
	(38, 'Libro edit', 'TechniBooks', '3393-339393', 0, 7);







CREATE TABLE IF NOT EXISTS `loans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `loan_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `returned` tinyint(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loan-book_idx` (`book_id`),
  KEY `fk_loan-user_idx` (`user_id`),
  CONSTRAINT `fk_loan-book` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_loan-user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;




/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

Cannot add or update a child row: a foreign key constraint fails (`lms_db`.`loans`, CONSTRAINT `fk_loan-user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE)



