-- --------------------------------------------------------
-- Сервер:                       127.0.0.1
-- Версія сервера:               5.6.22-log - MySQL Community Server (GPL)
-- ОС сервера:                   Win64
-- HeidiSQL Версія:              9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for powerlifting
DROP DATABASE IF EXISTS `powerlifting`;
CREATE DATABASE IF NOT EXISTS `powerlifting` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `powerlifting`;


-- Dumping structure for таблиця powerlifting.coach
DROP TABLE IF EXISTS `coach`;
CREATE TABLE IF NOT EXISTS `coach` (
  `coachId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` int(10) unsigned NOT NULL,
  `participant` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  PRIMARY KEY (`coachId`),
  KEY `FK_coach_participant` (`participant`),
  KEY `FK_coach_competition` (`competition`),
  KEY `FK_coach_user` (`user`),
  CONSTRAINT `FK_coach_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`),
  CONSTRAINT `FK_coach_competition_participant` FOREIGN KEY (`participant`) REFERENCES `competition_participant` (`user`),
  CONSTRAINT `FK_coach_user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.competition
DROP TABLE IF EXISTS `competition`;
CREATE TABLE IF NOT EXISTS `competition` (
  `competitionId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `author` int(10) unsigned NOT NULL,
  `name` text NOT NULL,
  `city` varchar(50) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `gender` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0 - women; 1 - men; 2 - everyone',
  `info` text,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 - Eventing; 1 - bench press; 2 - deadlift',
  `ageGroup` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`competitionId`),
  KEY `FK_competition_user` (`author`),
  KEY `FK_competition_dictionary_age_group` (`ageGroup`),
  CONSTRAINT `FK_competition_dictionary_age_group` FOREIGN KEY (`ageGroup`) REFERENCES `dictionary_age_group` (`groupId`),
  CONSTRAINT `FK_competition_user` FOREIGN KEY (`author`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.competition_judge
DROP TABLE IF EXISTS `competition_judge`;
CREATE TABLE IF NOT EXISTS `competition_judge` (
  `user` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  KEY `FK_competition_judge_competition` (`competition`),
  KEY `FK_competition_judge_user` (`user`),
  CONSTRAINT `FK_competition_judge_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_competition_judge_judge` FOREIGN KEY (`user`) REFERENCES `judge` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.competition_participant
DROP TABLE IF EXISTS `competition_participant`;
CREATE TABLE IF NOT EXISTS `competition_participant` (
  `user` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  KEY `FK_competition_participant_user` (`user`),
  KEY `FK_competition_participant_competition` (`competition`),
  CONSTRAINT `FK_competition_participant_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_competition_participant_user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.dictionary_age_group
DROP TABLE IF EXISTS `dictionary_age_group`;
CREATE TABLE IF NOT EXISTS `dictionary_age_group` (
  `groupId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `groupe` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.dictionary_judge_category
DROP TABLE IF EXISTS `dictionary_judge_category`;
CREATE TABLE IF NOT EXISTS `dictionary_judge_category` (
  `categoryId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `category` text NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.dictionary_title/discharge
DROP TABLE IF EXISTS `dictionary_title/discharge`;
CREATE TABLE IF NOT EXISTS `dictionary_title/discharge` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.dictionary_weight_category
DROP TABLE IF EXISTS `dictionary_weight_category`;
CREATE TABLE IF NOT EXISTS `dictionary_weight_category` (
  `categoryId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `gender` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 - female; 1 - male',
  `minWeight` smallint(5) unsigned NOT NULL,
  `maxWeight` smallint(5) unsigned NOT NULL COMMENT 'real weight most be < max weight, NOT <=!!!!',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.judge
DROP TABLE IF EXISTS `judge`;
CREATE TABLE IF NOT EXISTS `judge` (
  `userId` int(10) unsigned NOT NULL,
  `category` tinyint(3) unsigned NOT NULL,
  `assignmentDate` date NOT NULL,
  PRIMARY KEY (`userId`),
  KEY `FK_judge_dictionary_judge_category` (`category`),
  CONSTRAINT `FK_judge_dictionary_judge_category` FOREIGN KEY (`category`) REFERENCES `dictionary_judge_category` (`categoryId`),
  CONSTRAINT `FK_judge_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.participant
DROP TABLE IF EXISTS `participant`;
CREATE TABLE IF NOT EXISTS `participant` (
  `participantId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  `weight` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`participantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for таблиця powerlifting.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `secondName` varchar(50) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `middleName` varchar(50) NOT NULL,
  `gender` tinyint(4) NOT NULL COMMENT '0 - female; 1 - male',
  `birthday` date DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `title/discharge` tinyint(3) unsigned DEFAULT NULL,
  `role` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0 - user; 1 - wait for moder status; 2 - moder; 3 - admin',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_user_title/discharge` (`title/discharge`),
  CONSTRAINT `FK_user_title/discharge` FOREIGN KEY (`title/discharge`) REFERENCES `dictionary_title/discharge` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for тригер powerlifting.user_photo
DROP TRIGGER IF EXISTS `user_photo`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `user_photo` BEFORE INSERT ON `user` FOR EACH ROW BEGIN
	if(NEW.gender <> 1) then
		SET NEW.photo = 'female-no-avatar.png';
	ELSE
		SET NEW.photo = 'male-no-avatar.jpeg';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
