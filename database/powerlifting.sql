-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.21-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for powerlifting
DROP DATABASE IF EXISTS `powerlifting`;
CREATE DATABASE IF NOT EXISTS `powerlifting` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `powerlifting`;


-- Dumping structure for table powerlifting.coach
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
  CONSTRAINT `FK_coach_participant` FOREIGN KEY (`participant`) REFERENCES `participant` (`participantId`),
  CONSTRAINT `FK_coach_user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.coach: ~0 rows (approximately)
DELETE FROM `coach`;
/*!40000 ALTER TABLE `coach` DISABLE KEYS */;
/*!40000 ALTER TABLE `coach` ENABLE KEYS */;


-- Dumping structure for table powerlifting.competition
DROP TABLE IF EXISTS `competition`;
CREATE TABLE IF NOT EXISTS `competition` (
  `competitionId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `city` varchar(50) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `gender` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0 - women; 1 - men; 2 - everyone',
  `info` text,
  `author` int(10) unsigned NOT NULL,
  PRIMARY KEY (`competitionId`),
  KEY `FK_competition_user` (`author`),
  CONSTRAINT `FK_competition_user` FOREIGN KEY (`author`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.competition: ~8 rows (approximately)
DELETE FROM `competition`;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` (`competitionId`, `name`, `city`, `startDate`, `endDate`, `gender`, `info`, `author`) VALUES
	(1, 'Чемпіонат України серед юніорів та юніорок, юнаків та дівчат', 'Харків', '2015-02-03', '2015-02-06', 2, 'День приїзду - 02 лютого. Легкоатлетичний манеж ХТЗ.', 6),
	(2, 'Чемпіонат України (чоловіки та жінки)', 'Полтава', '2015-02-22', '2015-02-24', 2, 'День приїзду - 21 лютого.', 6),
	(3, 'Чемпіонат України серед ветеранів', 'Полтава', '2015-02-25', '2015-02-25', 2, 'День приїзду - 24 лютого.', 6),
	(4, 'Чемпіонат України з жиму лежачи', 'Борислав та Трускавець', '2015-03-13', '2015-03-15', 1, 'День приїзду - 12 березня. Проживання у м. Трускавець, змагання - у м. Борислав (Львівська область).', 6),
	(5, 'Обласний чемпіонат зжиму лежачи', 'Черкаси', '2014-03-15', '2014-03-18', 0, 'День приїзду - 14 березня.', 6),
	(7, 'drftgyhujjuhygtfrd rftgyhujik hygtfrde sedrftgyhuji gvfcdxsza', 'asdfvgbnmjuytrfd', '2015-05-01', '2015-05-05', 2, 'dfghj hgfd sasdfghj htrew sdfghjk jhgtfrdes wsdfghj jhgfds wertgyhujk nbvcx wertyu nbvcx ertyui nbvcxs', 6),
	(8, 'Чемпіонат Світу серед чоловіків', 'Люксембург', '2015-03-02', '2015-03-07', 1, 'кшгіпр івглрм ішавглр вкаепнргошл шогрнпеакву ічвсампиртоь орпеакву іцвукаепнргош орнпеаквуі іцвукаепнргошл огрнпеакву іцвукаепнргошл огрнпеакву івукаепнргошл ьотрипасв іцувкаепнргош орпеакву ціувкаепнргошл отрпав йфціувкаепнргош отрипасвчі йфціувкаепнргош лорнпеакву фціувкаепнргош орпаві', 6),
	(9, 'weft7gcyvuhinojkm p,wkpdsofijcu hygtwev audhnmj iu8ceh7qyg7v yhxnewq9miokjv 8uh7gvbhnjwemkidvj uhgybvceq gwudjfjiuce8h7dyq6gctv ghcjxnmijq8cewh 7gybv', 'azsdxcfgvbhnjmk,lkmjnbhgvcfdxszxdcfvgbhnjmkjnuyhb', '2015-05-01', '2015-05-05', 2, 'dfxg \nsdg\n fdg\nsdg\nsdg\nsg\nsg\nsg\nsg\nsg\nsdg\nsg\nsg\nsg\n', 6);
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;


-- Dumping structure for table powerlifting.competition_judge
DROP TABLE IF EXISTS `competition_judge`;
CREATE TABLE IF NOT EXISTS `competition_judge` (
  `user` int(10) unsigned DEFAULT NULL,
  `competition` int(10) unsigned DEFAULT NULL,
  KEY `FK_competition_judge_competition` (`competition`),
  KEY `FK_competition_judge_user` (`user`),
  CONSTRAINT `FK_competition_judge_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`),
  CONSTRAINT `FK_competition_judge_user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.competition_judge: ~0 rows (approximately)
DELETE FROM `competition_judge`;
/*!40000 ALTER TABLE `competition_judge` DISABLE KEYS */;
INSERT INTO `competition_judge` (`user`, `competition`) VALUES
	(2, 5);
/*!40000 ALTER TABLE `competition_judge` ENABLE KEYS */;


-- Dumping structure for table powerlifting.competition_participant
DROP TABLE IF EXISTS `competition_participant`;
CREATE TABLE IF NOT EXISTS `competition_participant` (
  `user` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  KEY `FK_competition_participant_user` (`user`),
  KEY `FK_competition_participant_competition` (`competition`),
  CONSTRAINT `FK_competition_participant_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`),
  CONSTRAINT `FK_competition_participant_user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.competition_participant: ~0 rows (approximately)
DELETE FROM `competition_participant`;
/*!40000 ALTER TABLE `competition_participant` DISABLE KEYS */;
INSERT INTO `competition_participant` (`user`, `competition`) VALUES
	(3, 5);
/*!40000 ALTER TABLE `competition_participant` ENABLE KEYS */;


-- Dumping structure for table powerlifting.participant
DROP TABLE IF EXISTS `participant`;
CREATE TABLE IF NOT EXISTS `participant` (
  `participantId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  `weight` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`participantId`),
  KEY `FK__user` (`user`),
  KEY `FK__competition` (`competition`),
  CONSTRAINT `FK__competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.participant: ~0 rows (approximately)
DELETE FROM `participant`;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;


-- Dumping structure for table powerlifting.title/discharge
DROP TABLE IF EXISTS `title/discharge`;
CREATE TABLE IF NOT EXISTS `title/discharge` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.title/discharge: ~8 rows (approximately)
DELETE FROM `title/discharge`;
/*!40000 ALTER TABLE `title/discharge` DISABLE KEYS */;
INSERT INTO `title/discharge` (`id`, `name`) VALUES
	(1, 'ЗМС'),
	(2, 'МСМК'),
	(3, 'МС'),
	(4, 'КМС'),
	(5, '1 розряд'),
	(6, '2 розряд'),
	(7, '3 розряд'),
	(8, '1 юнацький розряд'),
	(9, '2 юнацький розряд'),
	(10, '3 юнацький розряд');
/*!40000 ALTER TABLE `title/discharge` ENABLE KEYS */;


-- Dumping structure for table powerlifting.user
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
  `photo` varchar(100) NOT NULL DEFAULT 'ui-zac.jpg',
  `title/discharge` tinyint(3) unsigned DEFAULT NULL,
  `role` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0 - user; 1 - wait for moder status; 2 - moder; 3 - admin',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_user_title/discharge` (`title/discharge`),
  CONSTRAINT `FK_user_title/discharge` FOREIGN KEY (`title/discharge`) REFERENCES `title/discharge` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.user: ~7 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userId`, `email`, `password`, `secondName`, `firstName`, `middleName`, `gender`, `birthday`, `photo`, `title/discharge`, `role`) VALUES
	(1, 'mail5@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Vukol', 'Olena', 'Karpivna', 0, '1984-04-12', 'ui-zac.jpg', NULL, 0),
	(2, 'mail@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Mackenzy', 'John', 'Yanger', 1, '1900-03-01', 'ui-zac.jpg', NULL, 0),
	(3, 'mail1@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'bin Laden', 'Osama', 'Mohammed', 1, '1957-03-10', 'ui-zac.jpg', NULL, 2),
	(4, 'mail2@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Alba', 'Jessica', 'Marie', 0, '1981-04-28', 'ui-zac.jpg', NULL, 0),
	(5, 'mail3@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Raymond', 'George', 'Martin', 1, '1948-09-20', 'ui-zac.jpg', NULL, 0),
	(6, 'mail4@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Тимошенко', 'Юлія', 'Володимирівна', 0, '1960-11-27', 'ui-zac.jpg', NULL, 2),
	(7, 'SichaUA@ukr.net', '202cb962ac59075b964b07152d234b70', 'Січкар', 'Іван', 'Борисович', 1, '1994-06-20', 'ui-zac.jpg', NULL, 3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table powerlifting.weight_category
DROP TABLE IF EXISTS `weight_category`;
CREATE TABLE IF NOT EXISTS `weight_category` (
  `categoryId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `gender` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 - female; 1 - male',
  `minWeight` smallint(5) unsigned NOT NULL,
  `maxWeight` smallint(5) unsigned NOT NULL COMMENT 'real weight most be < max weight, NOT <=!!!!',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.weight_category: ~17 rows (approximately)
DELETE FROM `weight_category`;
/*!40000 ALTER TABLE `weight_category` DISABLE KEYS */;
INSERT INTO `weight_category` (`categoryId`, `gender`, `minWeight`, `maxWeight`, `name`) VALUES
	(3, 0, 0, 43, 'до 43 кг'),
	(4, 0, 43, 47, 'до 47 кг'),
	(5, 0, 47, 52, 'до 52 кг'),
	(6, 0, 52, 57, 'до 57 кг'),
	(7, 0, 57, 63, 'до 63 кг'),
	(8, 0, 63, 72, 'до 72 кг'),
	(9, 0, 72, 84, 'до 84 кг'),
	(10, 0, 84, 65535, 'понад 84 кг'),
	(11, 1, 0, 53, 'до 53 кг'),
	(12, 1, 53, 59, 'до 59 кг'),
	(13, 1, 59, 66, 'до 66 кг'),
	(14, 1, 66, 74, 'до 74 кг'),
	(15, 1, 74, 83, 'до 83 кг'),
	(16, 1, 83, 93, 'до 93 кг'),
	(17, 1, 93, 105, 'до 105 кг'),
	(18, 1, 105, 120, 'до 120 кг'),
	(19, 1, 120, 65535, 'понад 120 кг');
/*!40000 ALTER TABLE `weight_category` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
