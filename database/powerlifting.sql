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


-- Dumping structure for таблиця powerlifting.attempt
DROP TABLE IF EXISTS `attempt`;
CREATE TABLE IF NOT EXISTS `attempt` (
  `attemptId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `groupParticipant` bigint(20) unsigned NOT NULL,
  `attemptNumber` tinyint(3) unsigned NOT NULL COMMENT '0 - declared weight; 1 - first attempt; 2 - second attempt;  3 - third attempt',
  `weight` float unsigned NOT NULL DEFAULT '0',
  `exercise` tinyint(4) unsigned NOT NULL,
  `status` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`attemptId`),
  KEY `FK_attempt_dictionary_attempt_status` (`status`),
  KEY `FK_attempt_dictionary_exercise` (`exercise`),
  KEY `FK_attempt_group_participant` (`groupParticipant`),
  CONSTRAINT `FK_attempt_dictionary_attempt_status` FOREIGN KEY (`status`) REFERENCES `dictionary_attempt_status` (`atemptStatusId`),
  CONSTRAINT `FK_attempt_dictionary_exercise` FOREIGN KEY (`exercise`) REFERENCES `dictionary_exercise` (`exerciseId`),
  CONSTRAINT `FK_attempt_group_participant` FOREIGN KEY (`groupParticipant`) REFERENCES `group_participant` (`groupParticipantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.attempt: ~85 rows (приблизно)
DELETE FROM `attempt`;
/*!40000 ALTER TABLE `attempt` DISABLE KEYS */;
INSERT INTO `attempt` (`attemptId`, `groupParticipant`, `attemptNumber`, `weight`, `exercise`, `status`) VALUES
	(129, 143, 1, 85, 2, 1),
	(130, 143, 1, 95, 3, 1),
	(132, 144, 1, 80, 2, 1),
	(133, 144, 1, 90, 3, 1),
	(134, 145, 1, 120, 1, 2),
	(135, 145, 1, 100, 2, 1),
	(136, 145, 1, 110, 3, 1),
	(137, 144, 1, 100, 1, 3),
	(138, 143, 1, 90, 1, 2),
	(139, 152, 1, 90, 1, 2),
	(140, 152, 1, 80, 2, 2),
	(141, 152, 1, 90, 3, 1),
	(142, 154, 1, 110, 1, 2),
	(144, 154, 1, 100, 2, 2),
	(145, 154, 1, 115, 3, 1),
	(146, 153, 1, 110, 1, 2),
	(147, 153, 1, 95, 2, 1),
	(148, 153, 1, 110, 3, 1),
	(149, 156, 1, 100, 1, 3),
	(150, 156, 1, 85, 2, 3),
	(151, 156, 1, 100, 3, 1),
	(152, 155, 1, 100, 1, 2),
	(153, 155, 1, 85, 2, 2),
	(154, 155, 1, 95, 3, 1),
	(155, 157, 1, 120, 1, 2),
	(156, 157, 1, 115, 2, 2),
	(157, 157, 1, 125, 3, 1),
	(158, 160, 1, 130, 1, 3),
	(159, 160, 1, 125, 2, 4),
	(160, 160, 1, 125, 3, 1),
	(161, 158, 1, 125, 1, 3),
	(162, 158, 1, 115, 2, 2),
	(164, 158, 1, 130, 3, 1),
	(165, 159, 1, 120, 1, 2),
	(166, 159, 1, 110, 2, 3),
	(167, 159, 1, 120, 3, 1),
	(168, 164, 1, 140, 1, 2),
	(169, 164, 1, 120, 2, 1),
	(170, 164, 1, 140, 3, 1),
	(171, 162, 1, 130, 1, 2),
	(172, 162, 1, 120, 2, 2),
	(173, 162, 1, 135, 3, 1),
	(174, 161, 1, 135, 1, 3),
	(175, 161, 1, 125, 2, 1),
	(176, 161, 1, 130, 3, 1),
	(177, 163, 1, 140, 1, 3),
	(178, 163, 1, 130, 2, 1),
	(179, 163, 1, 135, 3, 1),
	(180, 152, 2, 100, 1, 2),
	(181, 157, 2, 125, 1, 2),
	(182, 153, 2, 120, 1, 4),
	(183, 156, 2, 100, 1, 2),
	(184, 154, 2, 120, 1, 2),
	(185, 155, 2, 110, 1, 3),
	(186, 160, 2, 130, 1, 2),
	(187, 159, 2, 125, 1, 3),
	(188, 158, 2, 125, 1, 3),
	(190, 156, 3, 110, 1, 2),
	(192, 154, 3, 125, 1, 2),
	(193, 157, 3, 130, 1, 3),
	(194, 158, 3, 125, 1, 2),
	(195, 159, 3, 130, 1, 2),
	(196, 160, 3, 135, 1, 2),
	(197, 155, 3, 115, 1, 2),
	(198, 152, 3, 110, 1, 2),
	(199, 164, 2, 150, 1, 1),
	(200, 162, 2, 135, 1, 2),
	(201, 161, 2, 135, 1, 2),
	(202, 163, 2, 145, 1, 2),
	(203, 162, 3, 145, 1, 2),
	(204, 161, 3, 140, 1, 3),
	(205, 163, 3, 150, 1, 2),
	(206, 152, 2, 90, 2, 2),
	(207, 156, 2, 85, 2, 6),
	(208, 155, 2, 92.5, 2, 2),
	(209, 154, 2, 110, 2, 1),
	(210, 159, 2, 110, 2, 1),
	(211, 157, 2, 120, 2, 1),
	(212, 158, 2, 117.5, 2, 1),
	(213, 156, 3, 90, 2, 1),
	(214, 152, 3, 105, 2, 1),
	(215, 155, 3, 100, 2, 1),
	(216, 143, 2, 100, 1, 1),
	(217, 145, 2, 125, 1, 1),
	(218, 144, 2, 100, 1, 1);
/*!40000 ALTER TABLE `attempt` ENABLE KEYS */;


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
  `broadcasting` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 - not broadcasting; 1 - broadcasting',
  `broadcastingSequence` int(10) unsigned DEFAULT NULL,
  `broadcastingGroup` int(10) unsigned DEFAULT NULL,
  `broadcastingType` tinyint(4) DEFAULT NULL COMMENT '1 - SQ; 2 - BP; 3 - DL',
  PRIMARY KEY (`competitionId`),
  KEY `FK_competition_user` (`author`),
  KEY `FK_competition_sequence` (`broadcastingSequence`),
  KEY `FK_competition_group` (`broadcastingGroup`),
  CONSTRAINT `FK_competition_group` FOREIGN KEY (`broadcastingGroup`) REFERENCES `group` (`groupId`),
  CONSTRAINT `FK_competition_sequence` FOREIGN KEY (`broadcastingSequence`) REFERENCES `sequence` (`sequenceId`),
  CONSTRAINT `FK_competition_user` FOREIGN KEY (`author`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.competition: ~5 rows (приблизно)
DELETE FROM `competition`;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` (`competitionId`, `author`, `name`, `city`, `startDate`, `endDate`, `gender`, `info`, `type`, `broadcasting`, `broadcastingSequence`, `broadcastingGroup`, `broadcastingType`) VALUES
	(1, 6, 'Чемпіонат України серед юніорів та юніорок, юнаків та дівчат', 'Харків', '2015-02-03', '2015-02-06', 2, 'День приїзду - 02 лютого. Легкоатлетичний манеж ХТЗ.', 0, 1, 53, 48, 1),
	(2, 6, 'Чемпіонат України (чоловіки та жінки)', 'Полтава', '2015-02-22', '2015-02-24', 2, 'День приїзду - 21 лютого.', 0, 0, NULL, NULL, NULL),
	(3, 6, 'Чемпіонат України серед ветеранів', 'Полтава', '2015-02-25', '2015-02-25', 2, 'День приїзду - 24 лютого.', 0, 0, NULL, NULL, NULL),
	(4, 6, 'Чемпіонат України з жиму лежачи', 'Борислав та Трускавець', '2015-03-13', '2015-03-15', 1, 'День приїзду - 12 березня. Проживання у м. Трускавець, змагання - у м. Борислав (Львівська область).', 1, 1, 56, 52, 2),
	(5, 7, 'ВСЕСВІТНІ ЗМАГАННЯ ЗІ СТАНОВОЇ ТЯГИ СЕРЕД ВЕТЕРАНІВ', 'Черкаси', '2015-03-04', '2015-03-07', 2, 'емнигт шогрнпеак іцувкаепнрго лшогнрпеакв іцувкаепнрогшл лшогнрпеаквуі ціувкаепнрогшл шогнрпеакву фіцвукаепнрогшл огрнпеаквуіц фіцвукаепнрго шогнрпеакву ічвсампиртоьл лшогнрпеакву яічвсапмирт', 2, 0, NULL, NULL, NULL);
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.competition_age_group
DROP TABLE IF EXISTS `competition_age_group`;
CREATE TABLE IF NOT EXISTS `competition_age_group` (
  `competition` int(10) unsigned NOT NULL,
  `ageGroup` tinyint(3) unsigned NOT NULL,
  KEY `FK_competition_age_group_competition` (`competition`),
  KEY `FK_competition_age_group_dictionary_age_group` (`ageGroup`),
  CONSTRAINT `FK_competition_age_group_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`) ON DELETE CASCADE,
  CONSTRAINT `FK_competition_age_group_dictionary_age_group` FOREIGN KEY (`ageGroup`) REFERENCES `dictionary_age_group` (`groupId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.competition_age_group: ~6 rows (приблизно)
DELETE FROM `competition_age_group`;
/*!40000 ALTER TABLE `competition_age_group` DISABLE KEYS */;
INSERT INTO `competition_age_group` (`competition`, `ageGroup`) VALUES
	(2, 1),
	(3, 4),
	(4, 2),
	(5, 4),
	(1, 2),
	(1, 3);
/*!40000 ALTER TABLE `competition_age_group` ENABLE KEYS */;


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

-- Dumping data for table powerlifting.competition_judge: ~15 rows (приблизно)
DELETE FROM `competition_judge`;
/*!40000 ALTER TABLE `competition_judge` DISABLE KEYS */;
INSERT INTO `competition_judge` (`user`, `competition`) VALUES
	(3, 1),
	(5, 1),
	(9, 1),
	(12, 1),
	(3, 2),
	(12, 2),
	(6, 4),
	(20, 2),
	(10, 2),
	(10, 4),
	(3, 4),
	(8, 1),
	(8, 4),
	(11, 1),
	(10, 1),
	(6, 1);
/*!40000 ALTER TABLE `competition_judge` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_age_group
DROP TABLE IF EXISTS `dictionary_age_group`;
CREATE TABLE IF NOT EXISTS `dictionary_age_group` (
  `groupId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `group` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_age_group: ~4 rows (приблизно)
DELETE FROM `dictionary_age_group`;
/*!40000 ALTER TABLE `dictionary_age_group` DISABLE KEYS */;
INSERT INTO `dictionary_age_group` (`groupId`, `group`, `description`) VALUES
	(1, '1 вікова група', 'чоловіки/жінки'),
	(2, '2 вікова група', 'юніори/юніорки'),
	(3, '3 вікова група', 'юнаки/дівчата'),
	(4, '4 вікова група', 'ветерани');
/*!40000 ALTER TABLE `dictionary_age_group` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_attempt_status
DROP TABLE IF EXISTS `dictionary_attempt_status`;
CREATE TABLE IF NOT EXISTS `dictionary_attempt_status` (
  `atemptStatusId` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `attemptStatusName` text NOT NULL,
  PRIMARY KEY (`atemptStatusId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_attempt_status: ~6 rows (приблизно)
DELETE FROM `dictionary_attempt_status`;
/*!40000 ALTER TABLE `dictionary_attempt_status` DISABLE KEYS */;
INSERT INTO `dictionary_attempt_status` (`atemptStatusId`, `attemptStatusName`) VALUES
	(1, 'Заявлена вага'),
	(2, 'Зарахована спроба'),
	(3, 'Не зарахована спроба'),
	(4, 'Знятий лікарем'),
	(5, 'Відмовився'),
	(6, 'Суддівська помилка');
/*!40000 ALTER TABLE `dictionary_attempt_status` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_exercise
DROP TABLE IF EXISTS `dictionary_exercise`;
CREATE TABLE IF NOT EXISTS `dictionary_exercise` (
  `exerciseId` tinyint(4) unsigned NOT NULL AUTO_INCREMENT,
  `exerciseName` text NOT NULL,
  PRIMARY KEY (`exerciseId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_exercise: ~3 rows (приблизно)
DELETE FROM `dictionary_exercise`;
/*!40000 ALTER TABLE `dictionary_exercise` DISABLE KEYS */;
INSERT INTO `dictionary_exercise` (`exerciseId`, `exerciseName`) VALUES
	(1, 'Squatting'),
	(2, 'Bench Press'),
	(3, 'Deadlift');
/*!40000 ALTER TABLE `dictionary_exercise` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_judge_category
DROP TABLE IF EXISTS `dictionary_judge_category`;
CREATE TABLE IF NOT EXISTS `dictionary_judge_category` (
  `categoryId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `category` text NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_judge_category: ~4 rows (приблизно)
DELETE FROM `dictionary_judge_category`;
/*!40000 ALTER TABLE `dictionary_judge_category` DISABLE KEYS */;
INSERT INTO `dictionary_judge_category` (`categoryId`, `category`) VALUES
	(1, '3 суддівська категорія'),
	(2, '2 суддівська категорія'),
	(3, '1 суддівська категорія'),
	(4, 'Суддя міжнародного класу');
/*!40000 ALTER TABLE `dictionary_judge_category` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_judge_type
DROP TABLE IF EXISTS `dictionary_judge_type`;
CREATE TABLE IF NOT EXISTS `dictionary_judge_type` (
  `typeId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_judge_type: ~7 rows (приблизно)
DELETE FROM `dictionary_judge_type`;
/*!40000 ALTER TABLE `dictionary_judge_type` DISABLE KEYS */;
INSERT INTO `dictionary_judge_type` (`typeId`, `name`) VALUES
	(1, 'Старший суддя'),
	(2, 'Боковий суддя'),
	(3, 'Секретар'),
	(4, 'Помічник секретаря'),
	(5, 'Суддя при учасниках'),
	(6, 'Комп\'ютерний секретар'),
	(7, 'Член журі');
/*!40000 ALTER TABLE `dictionary_judge_type` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_participant_status
DROP TABLE IF EXISTS `dictionary_participant_status`;
CREATE TABLE IF NOT EXISTS `dictionary_participant_status` (
  `statusId` tinyint(3) NOT NULL AUTO_INCREMENT,
  `statusName` text NOT NULL,
  PRIMARY KEY (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_participant_status: ~3 rows (приблизно)
DELETE FROM `dictionary_participant_status`;
/*!40000 ALTER TABLE `dictionary_participant_status` DISABLE KEYS */;
INSERT INTO `dictionary_participant_status` (`statusId`, `statusName`) VALUES
	(1, 'Змагається'),
	(2, 'Дискваліфіковано'),
	(3, 'Знятий лікарем');
/*!40000 ALTER TABLE `dictionary_participant_status` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_region
DROP TABLE IF EXISTS `dictionary_region`;
CREATE TABLE IF NOT EXISTS `dictionary_region` (
  `regionId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`regionId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_region: ~29 rows (приблизно)
DELETE FROM `dictionary_region`;
/*!40000 ALTER TABLE `dictionary_region` DISABLE KEYS */;
INSERT INTO `dictionary_region` (`regionId`, `name`) VALUES
	(1, 'Черкаська область'),
	(3, 'Херсонська область'),
	(4, 'Донецька область'),
	(5, 'Київ'),
	(6, 'Севастополь'),
	(7, 'Черкаси'),
	(8, 'Полтава'),
	(9, 'Донецьк'),
	(10, 'Херсон'),
	(11, 'Одеса'),
	(12, 'Бородянка'),
	(13, 'Волочиський р-н'),
	(14, 'Городоцький р-н'),
	(15, 'Запоріжжя'),
	(16, 'Комсомольськ'),
	(17, 'Кременчук'),
	(18, 'Мукачево'),
	(19, 'Немирів'),
	(20, 'Перечин'),
	(21, 'Соледар'),
	(22, 'Суми'),
	(23, 'Тернопіль'),
	(24, 'Ужгород'),
	(25, 'Хмельницький'),
	(26, 'Чернівці'),
	(27, 'Яворів'),
	(28, 'Харків'),
	(29, 'Луцьк'),
	(30, 'Кам.-Подільський  р-н');
/*!40000 ALTER TABLE `dictionary_region` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_title/discharge
DROP TABLE IF EXISTS `dictionary_title/discharge`;
CREATE TABLE IF NOT EXISTS `dictionary_title/discharge` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_title/discharge: ~10 rows (приблизно)
DELETE FROM `dictionary_title/discharge`;
/*!40000 ALTER TABLE `dictionary_title/discharge` DISABLE KEYS */;
INSERT INTO `dictionary_title/discharge` (`id`, `name`) VALUES
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
/*!40000 ALTER TABLE `dictionary_title/discharge` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.dictionary_weight_category
DROP TABLE IF EXISTS `dictionary_weight_category`;
CREATE TABLE IF NOT EXISTS `dictionary_weight_category` (
  `categoryId` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `gender` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 - female; 1 - male',
  `minWeight` smallint(5) unsigned NOT NULL,
  `maxWeight` smallint(5) unsigned NOT NULL COMMENT 'real weight most be < max weight, NOT <=!!!!',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.dictionary_weight_category: ~17 rows (приблизно)
DELETE FROM `dictionary_weight_category`;
/*!40000 ALTER TABLE `dictionary_weight_category` DISABLE KEYS */;
INSERT INTO `dictionary_weight_category` (`categoryId`, `gender`, `minWeight`, `maxWeight`, `name`) VALUES
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
/*!40000 ALTER TABLE `dictionary_weight_category` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.group
DROP TABLE IF EXISTS `group`;
CREATE TABLE IF NOT EXISTS `group` (
  `groupId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sequenceId` int(10) unsigned NOT NULL,
  `groupNum` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`groupId`),
  KEY `sequenceId_groupNum` (`sequenceId`,`groupNum`),
  CONSTRAINT `FK_group_sequence` FOREIGN KEY (`sequenceId`) REFERENCES `sequence` (`sequenceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.group: ~11 rows (приблизно)
DELETE FROM `group`;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` (`groupId`, `sequenceId`, `groupNum`) VALUES
	(48, 53, 1),
	(49, 54, 1),
	(50, 55, 1),
	(51, 56, 1),
	(52, 56, 2),
	(53, 57, 1),
	(54, 58, 1),
	(55, 58, 2),
	(56, 59, 1),
	(57, 60, 1),
	(58, 60, 2);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.group_participant
DROP TABLE IF EXISTS `group_participant`;
CREATE TABLE IF NOT EXISTS `group_participant` (
  `groupParticipantId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `groupId` int(10) unsigned NOT NULL,
  `participant` bigint(20) unsigned NOT NULL,
  `participantWeight` float unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `ordinalNumber` int(11) NOT NULL,
  PRIMARY KEY (`groupParticipantId`),
  KEY `FK_group_participant_group` (`groupId`),
  KEY `FK_group_participant_participant` (`participant`),
  KEY `FK_group_participant_dictionary_participant_status` (`status`),
  CONSTRAINT `FK_group_participant_dictionary_participant_status` FOREIGN KEY (`status`) REFERENCES `dictionary_participant_status` (`statusId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_group_participant_group` FOREIGN KEY (`groupId`) REFERENCES `group` (`groupId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_group_participant_participant` FOREIGN KEY (`participant`) REFERENCES `participant` (`participantId`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.group_participant: ~67 rows (приблизно)
DELETE FROM `group_participant`;
/*!40000 ALTER TABLE `group_participant` DISABLE KEYS */;
INSERT INTO `group_participant` (`groupParticipantId`, `groupId`, `participant`, `participantWeight`, `status`, `ordinalNumber`) VALUES
	(143, 48, 15, 46, 1, 1),
	(144, 48, 2, 45, 1, 3),
	(145, 48, 3, 71, 1, 2),
	(146, 49, 75, 0, 1, 4),
	(147, 49, 79, 0, 1, 1),
	(148, 49, 14, 0, 1, 3),
	(149, 49, 16, 0, 1, 2),
	(150, 49, 77, 119, 2, 5),
	(151, 50, 76, 51, 1, 1),
	(152, 51, 61, 46, 1, 5),
	(153, 51, 62, 51.5, 3, 8),
	(154, 51, 65, 51, 1, 4),
	(155, 51, 63, 51, 1, 11),
	(156, 51, 64, 50, 1, 9),
	(157, 51, 66, 54, 1, 2),
	(158, 51, 67, 56.6, 1, 7),
	(159, 51, 68, 54.8, 1, 13),
	(160, 51, 69, 56, 3, 6),
	(161, 52, 70, 62.7, 1, 10),
	(162, 52, 71, 60.5, 1, 3),
	(163, 52, 72, 62.8, 1, 12),
	(164, 52, 73, 62, 2, 1),
	(165, 53, 74, 0, 1, 1),
	(166, 54, 17, 0, 1, 6),
	(167, 54, 18, 0, 1, 1),
	(168, 54, 19, 0, 1, 16),
	(169, 54, 20, 0, 1, 10),
	(170, 54, 21, 0, 1, 9),
	(171, 54, 24, 0, 1, 5),
	(172, 54, 22, 0, 1, 3),
	(173, 54, 23, 0, 1, 14),
	(174, 55, 25, 0, 2, 15),
	(175, 55, 26, 0, 2, 4),
	(176, 55, 27, 0, 2, 2),
	(177, 55, 28, 0, 2, 7),
	(178, 55, 32, 0, 2, 12),
	(179, 55, 29, 0, 2, 8),
	(180, 55, 30, 0, 2, 11),
	(181, 55, 31, 0, 2, 13),
	(182, 56, 33, 0, 1, 4),
	(183, 56, 34, 0, 1, 2),
	(184, 56, 35, 0, 1, 5),
	(185, 56, 36, 0, 1, 3),
	(186, 56, 37, 0, 1, 1),
	(187, 56, 38, 0, 1, 8),
	(188, 56, 39, 0, 1, 9),
	(189, 56, 40, 0, 1, 10),
	(190, 56, 41, 0, 1, 7),
	(191, 56, 43, 0, 1, 6),
	(192, 56, 42, 0, 1, 11),
	(193, 57, 44, 0, 1, 7),
	(194, 57, 45, 0, 1, 12),
	(195, 57, 46, 0, 1, 11),
	(196, 57, 47, 0, 1, 16),
	(197, 57, 48, 0, 1, 4),
	(198, 57, 49, 0, 1, 15),
	(199, 58, 50, 0, 2, 14),
	(200, 58, 51, 0, 2, 10),
	(201, 58, 52, 0, 2, 5),
	(202, 58, 55, 0, 2, 6),
	(203, 58, 53, 0, 2, 17),
	(204, 58, 54, 0, 2, 3),
	(205, 58, 56, 0, 2, 1),
	(206, 58, 57, 0, 2, 9),
	(207, 58, 58, 0, 2, 2),
	(208, 58, 60, 0, 2, 8),
	(209, 58, 59, 0, 2, 13);
/*!40000 ALTER TABLE `group_participant` ENABLE KEYS */;


-- Dumping structure for вид powerlifting.group_participant_with_attempt
DROP VIEW IF EXISTS `group_participant_with_attempt`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `group_participant_with_attempt` (
	`groupParticipantId` BIGINT(20) UNSIGNED NOT NULL,
	`groupId` INT(10) UNSIGNED NOT NULL,
	`participant` BIGINT(20) UNSIGNED NOT NULL,
	`participantWeight` FLOAT NOT NULL,
	`status` TINYINT(4) NOT NULL,
	`ordinalNumber` INT(11) NOT NULL,
	`firstAttemptSQ` DOUBLE NULL,
	`firstAttemptSQStatus` INT(5) UNSIGNED NULL,
	`secondAttemptSQ` DOUBLE NULL,
	`secondAttemptSQStatus` INT(5) UNSIGNED NULL,
	`thirdAttemptSQ` DOUBLE NULL,
	`thirdAttemptSQStatus` INT(5) UNSIGNED NULL,
	`firstAttemptBP` DOUBLE NULL,
	`firstAttemptBPStatus` INT(5) UNSIGNED NULL,
	`secondAttemptBP` DOUBLE NULL,
	`secondAttemptBPStatus` INT(5) UNSIGNED NULL,
	`thirdAttemptBP` DOUBLE NULL,
	`thirdAttemptBPStatus` INT(5) UNSIGNED NULL,
	`firstAttemptDL` DOUBLE NULL,
	`firstAttemptDLStatus` INT(5) UNSIGNED NULL,
	`secondAttemptDL` DOUBLE NULL,
	`secondAttemptDLStatus` INT(5) UNSIGNED NULL,
	`thirdAttemptDL` DOUBLE NULL,
	`thirdAttemptDLStatus` INT(5) UNSIGNED NULL
) ENGINE=MyISAM;


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

-- Dumping data for table powerlifting.judge: ~11 rows (приблизно)
DELETE FROM `judge`;
/*!40000 ALTER TABLE `judge` DISABLE KEYS */;
INSERT INTO `judge` (`userId`, `category`, `assignmentDate`) VALUES
	(1, 1, '2015-01-01'),
	(3, 3, '2013-05-09'),
	(4, 2, '2009-03-10'),
	(5, 2, '2012-10-11'),
	(6, 4, '2013-05-07'),
	(8, 1, '2015-01-25'),
	(9, 4, '2009-08-24'),
	(10, 2, '2014-11-04'),
	(11, 4, '2015-03-03'),
	(12, 4, '2004-06-06'),
	(20, 4, '2015-02-15');
/*!40000 ALTER TABLE `judge` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.participant
DROP TABLE IF EXISTS `participant`;
CREATE TABLE IF NOT EXISTS `participant` (
  `participantId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user` int(10) unsigned NOT NULL,
  `competition` int(10) unsigned NOT NULL,
  `ageGroup` tinyint(3) unsigned NOT NULL,
  `category` tinyint(3) unsigned NOT NULL,
  `from` int(10) unsigned NOT NULL,
  `squat` float unsigned DEFAULT NULL,
  `benchPress` float unsigned DEFAULT NULL,
  `deadlift` float unsigned DEFAULT NULL,
  `total` float unsigned DEFAULT NULL,
  `ownParticipation` bit(1) NOT NULL COMMENT '1 - own participation',
  `firstCoach` varchar(60) DEFAULT NULL,
  `personalCoach` varchar(60) DEFAULT NULL,
  `firstAdditionalCoach` varchar(60) DEFAULT NULL,
  `secondAdditionalCoach` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`participantId`),
  KEY `FK_participant_user` (`user`),
  KEY `FK_participant_competition` (`competition`),
  KEY `FK_participant_dictionary_weight_category` (`category`),
  KEY `FK_participant_dictionary_region` (`from`),
  KEY `FK_participant_dictionary_age_group` (`ageGroup`),
  CONSTRAINT `FK_participant_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`),
  CONSTRAINT `FK_participant_dictionary_age_group` FOREIGN KEY (`ageGroup`) REFERENCES `dictionary_age_group` (`groupId`),
  CONSTRAINT `FK_participant_dictionary_region` FOREIGN KEY (`from`) REFERENCES `dictionary_region` (`regionId`),
  CONSTRAINT `FK_participant_dictionary_weight_category` FOREIGN KEY (`category`) REFERENCES `dictionary_weight_category` (`categoryId`),
  CONSTRAINT `FK_participant_user` FOREIGN KEY (`user`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.participant: ~73 rows (приблизно)
DELETE FROM `participant`;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` (`participantId`, `user`, `competition`, `ageGroup`, `category`, `from`, `squat`, `benchPress`, `deadlift`, `total`, `ownParticipation`, `firstCoach`, `personalCoach`, `firstAdditionalCoach`, `secondAdditionalCoach`) VALUES
	(2, 4, 1, 2, 4, 7, 85, 50, 110, 245, b'0', '', '', '', ''),
	(3, 1, 1, 2, 8, 5, 100, 55, 130, 285, b'0', '', '', '', ''),
	(4, 2, 2, 1, 15, 10, 200, 160, 220, 580, b'0', '', '', '', ''),
	(5, 9, 2, 1, 18, 5, 215, 110, 220, 545, b'0', '', '', '', ''),
	(6, 2, 3, 1, 15, 10, 140, 100, 150, 390, b'1', '', '', '', ''),
	(8, 3, 2, 1, 18, 6, 152.5, 102.5, 167.5, 422.5, b'0', '', '', '', ''),
	(9, 1, 2, 1, 5, 5, 65, 40, 60, 165, b'0', '', '', '', ''),
	(10, 5, 2, 1, 18, 6, 170, 140, 190, 500, b'0', '', '', '', ''),
	(13, 8, 2, 1, 17, 7, 170, 150, 190, 510, b'0', '', 'Chuck Norris', '', ''),
	(14, 5, 1, 3, 18, 8, 190, 170, 200, 560, b'1', '', '', '', ''),
	(15, 11, 1, 2, 4, 7, 0, 0, 0, 270, b'1', 'Усама бін Ладен', '', '', ''),
	(16, 3, 1, 2, 19, 3, 0, 0, 0, 572.5, b'0', '', '', '', ''),
	(17, 21, 4, 1, 12, 18, 0, 140, 0, 140, b'0', '', 'М.М. Красилинець', '', ''),
	(18, 22, 4, 1, 13, 19, 0, 240, 0, 240, b'0', '', 'бр-В.А. Мохник', 'Л.М. Миговський', ''),
	(19, 23, 4, 1, 13, 28, 0, 170, 0, 170, b'0', 'Л.М. Плотницький', 'О.В. Чернишов', '', ''),
	(20, 24, 4, 1, 14, 25, 0, 220, 0, 220, b'0', '', 'О. Басістий', 'С.В. Франков', ''),
	(21, 25, 4, 1, 14, 8, 0, 212, 0, 212, b'0', '', 'М.Л. Буланий', '', ''),
	(22, 26, 4, 1, 14, 5, 0, 205, 0, 205, b'1', '', 'В.С. Налейкін', '', ''),
	(23, 27, 4, 1, 14, 24, 0, 205, 0, 205, b'0', '', 'А.А. Федорішко', '', ''),
	(24, 28, 4, 1, 14, 15, 0, 210, 0, 210, b'0', '', 'Є.В. Нескоромний', '', ''),
	(25, 29, 4, 1, 15, 14, 0, 270, 0, 270, b'0', '', 'В.Б. Воронецький', '', ''),
	(26, 30, 4, 1, 15, 23, 0, 217, 0, 217, b'1', '', 'І.В. Лисий', '', ''),
	(27, 31, 4, 1, 15, 8, 0, 205, 0, 205, b'0', '', 'І.І. Мдівнішвілі', '', ''),
	(28, 32, 4, 1, 15, 13, 0, 195, 0, 195, b'0', '', 'О.І. Антонов', '', ''),
	(29, 33, 4, 1, 15, 21, 0, 187, 0, 187, b'0', '', 'М.С. Сташкевич', '', ''),
	(30, 34, 4, 1, 15, 28, 0, 180, 0, 180, b'0', 'О.В. Коваль', 'В.П. Карпенко', '', ''),
	(31, 35, 4, 1, 15, 26, 0, 170, 0, 170, b'0', '', 'Н.С. Ясенецька', 'І.О. Ясенецький', ''),
	(32, 36, 4, 1, 15, 15, 0, 190, 0, 190, b'0', '', 'Є.В. Нескоромний', '', ''),
	(33, 37, 4, 1, 16, 5, 0, 310, 0, 310, b'0', '', 'В.С. Налейкін', '', ''),
	(34, 38, 4, 1, 16, 5, 0, 302, 0, 302, b'0', 'Ф.В. Колтаков', 'В.С. Налейкін', '', ''),
	(35, 39, 4, 1, 16, 5, 0, 232, 0, 232, b'0', '', 'І.О. Капко', 'Р.М. Кузьмук', ''),
	(36, 40, 4, 1, 16, 23, 0, 230, 0, 230, b'0', '', 'І.В. Лисий', '', ''),
	(37, 41, 4, 1, 16, 16, 0, 217, 0, 217, b'0', 'Б.В. Задворний', 'О.М. Шашин', '', ''),
	(38, 42, 4, 1, 16, 5, 0, 210, 0, 210, b'1', 'В.С. Налейкін', '', '', ''),
	(39, 43, 4, 1, 16, 28, 0, 210, 0, 210, b'0', 'Д.С. Стародубцев', 'О.В. Вишницкий', '', ''),
	(40, 44, 4, 1, 16, 21, 0, 200, 0, 200, b'0', '', 'О.І. Петренко', '', ''),
	(41, 45, 4, 1, 16, 21, 0, 200, 0, 200, b'0', '', 'М.С. Сташкевич', '', ''),
	(42, 46, 4, 1, 16, 28, 0, 180, 0, 180, b'0', 'О.В. Коваль', 'О.В. Чернишов', '', ''),
	(43, 47, 4, 1, 16, 25, 0, 200, 0, 200, b'0', '', 'А.О. Басістий', '', ''),
	(44, 48, 4, 1, 17, 5, 0, 297, 0, 297, b'0', '', 'В.С. Налейкін', '', ''),
	(45, 49, 4, 1, 17, 8, 0, 240, 0, 240, b'0', '', 'І.І. Мдівнішвілі', '', ''),
	(46, 50, 4, 1, 17, 12, 0, 240, 0, 240, b'0', '', 'В.В. Беттяр', '', ''),
	(47, 51, 4, 1, 17, 22, 0, 237, 0, 237, b'0', '', 'П.М. Могильніков', 'В.М. Федоренко', ''),
	(48, 52, 4, 1, 17, 18, 0, 200, 0, 200, b'0', '', 'М.М. Красилинець', '', ''),
	(49, 53, 4, 1, 17, 26, 0, 175, 0, 175, b'0', '', 'В.Ю. Крушельницький', '', ''),
	(50, 54, 4, 1, 18, 5, 0, 317, 0, 317, b'0', '', 'В.С. Налейкін', '', ''),
	(51, 55, 4, 1, 18, 21, 0, 285, 0, 285, b'0', '', 'М.С. Сташкевич', '', ''),
	(52, 56, 4, 1, 18, 19, 0, 270, 0, 270, b'0', '', 'І.Ю. Чупринко', '', ''),
	(53, 57, 4, 1, 18, 6, 0, 230, 0, 230, b'0', '', 'І.Л. Ізосімова', '', ''),
	(54, 58, 4, 1, 18, 26, 0, 205, 0, 205, b'0', '', 'В.Ю. Крушельницький', '', ''),
	(55, 59, 4, 1, 18, 17, 0, 250, 0, 250, b'0', '', '', '', ''),
	(56, 60, 4, 1, 19, 27, 0, 302, 0, 302, b'0', 'В.А. Погребінський', 'М.М. Невзоров', '', ''),
	(57, 61, 4, 1, 19, 23, 0, 300, 0, 300, b'0', '', 'І.В. Лисий', '', ''),
	(58, 62, 4, 1, 19, 17, 0, 250, 0, 250, b'0', '', '', '', ''),
	(59, 63, 4, 1, 19, 20, 0, 205, 0, 205, b'0', '', '', '', ''),
	(60, 64, 4, 1, 19, 26, 0, 215, 0, 215, b'0', '', 'В.Ю. Крушельницький', '', ''),
	(61, 109, 4, 1, 4, 23, 0, 75, 0, 75, b'1', '', '', 'І.В. Лисий', ''),
	(62, 110, 4, 1, 5, 27, 0, 85, 0, 85, b'0', '', '', 'В.Г. Сахно', ''),
	(63, 111, 4, 1, 5, 28, 0, 75, 0, 75, b'0', 'Л.М. Плотницький', '', 'Н.О. Дідюк', ''),
	(64, 112, 4, 1, 5, 28, 0, 60, 0, 60, b'0', '', 'Д.С. Стародубцев', 'О.В. Вишницкий', ''),
	(65, 113, 4, 1, 5, 23, 0, 80, 0, 80, b'0', '', '', 'І.В. Лисий', ''),
	(66, 114, 4, 1, 6, 30, 0, 135, 0, 135, b'0', '', '', 'В.Б. Воронецький', 'Г.В. Бесарабчук'),
	(67, 115, 4, 1, 6, 26, 0, 92, 0, 92, b'0', '', '', 'С.Ф. Макаров', ''),
	(68, 116, 4, 1, 6, 28, 0, 92, 0, 92, b'0', '', '', 'Н.О. Дідюк', ''),
	(69, 117, 4, 1, 6, 22, 0, 82, 0, 82, b'0', '', '', 'В.М. Федоренко', ''),
	(70, 118, 4, 1, 7, 29, 0, 125, 0, 125, b'0', '', '', 'О.В. Хом\'як', ''),
	(71, 119, 4, 1, 7, 28, 0, 102, 0, 102, b'1', '', '', 'Н.О. Дідюк', ''),
	(72, 120, 4, 1, 7, 27, 0, 102, 0, 102, b'0', '', '', 'В.Г. Сахно', ''),
	(73, 121, 4, 1, 7, 8, 0, 80, 0, 80, b'0', '', '', 'Д.О. Ніжніченко', ''),
	(74, 122, 4, 1, 10, 6, 0, 160, 0, 160, b'0', 'О.В. Коваль', 'О.В. Стадник', 'І.Л. Ізосімова', ''),
	(75, 8, 1, 3, 15, 18, 0, 0, 0, 330, b'1', '', '', '', ''),
	(76, 12, 1, 3, 5, 9, 0, 0, 0, 230, b'0', '', '', '', ''),
	(77, 2, 1, 2, 19, 24, 0, 0, 0, 465, b'0', '', '', '', ''),
	(79, 10, 1, 3, 17, 19, 0, 0, 0, 0, b'1', '', '', '', '');
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.sequence
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE IF NOT EXISTS `sequence` (
  `sequenceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `competition` int(10) unsigned NOT NULL,
  `date` date NOT NULL,
  `info` text,
  PRIMARY KEY (`sequenceId`),
  KEY `FK_stream_competition` (`competition`),
  CONSTRAINT `FK_stream_competition` FOREIGN KEY (`competition`) REFERENCES `competition` (`competitionId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.sequence: ~8 rows (приблизно)
DELETE FROM `sequence`;
/*!40000 ALTER TABLE `sequence` DISABLE KEYS */;
INSERT INTO `sequence` (`sequenceId`, `competition`, `date`, `info`) VALUES
	(53, 1, '2015-05-07', 'female <47 <72'),
	(54, 1, '2015-05-07', 'male <83 <105 <120 >120'),
	(55, 1, '2015-05-07', 'female <52'),
	(56, 4, '2015-05-07', 'female <47 <52 <57 <63'),
	(57, 4, '2015-05-07', 'female >84'),
	(58, 4, '2015-05-07', 'male <59 <66 <74 <83'),
	(59, 4, '2015-05-07', 'male <93'),
	(60, 4, '2015-05-07', 'male  <105 <120 >120');
/*!40000 ALTER TABLE `sequence` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.sequence_category
DROP TABLE IF EXISTS `sequence_category`;
CREATE TABLE IF NOT EXISTS `sequence_category` (
  `sequence` int(10) unsigned NOT NULL,
  `category` tinyint(3) unsigned NOT NULL,
  `ageGroup` tinyint(3) unsigned NOT NULL,
  KEY `FK_stream_categories_stream` (`sequence`),
  KEY `FK_stream_categories_dictionary_weight_category` (`category`),
  KEY `FK_sequence_category_dictionary_age_group` (`ageGroup`),
  CONSTRAINT `FK_sequence_category_dictionary_age_group` FOREIGN KEY (`ageGroup`) REFERENCES `dictionary_age_group` (`groupId`),
  CONSTRAINT `FK_stream_categories_dictionary_weight_category` FOREIGN KEY (`category`) REFERENCES `dictionary_weight_category` (`categoryId`),
  CONSTRAINT `FK_stream_categories_stream` FOREIGN KEY (`sequence`) REFERENCES `sequence` (`sequenceId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.sequence_category: ~20 rows (приблизно)
DELETE FROM `sequence_category`;
/*!40000 ALTER TABLE `sequence_category` DISABLE KEYS */;
INSERT INTO `sequence_category` (`sequence`, `category`, `ageGroup`) VALUES
	(53, 4, 2),
	(53, 8, 2),
	(54, 19, 2),
	(54, 15, 3),
	(54, 17, 3),
	(54, 18, 3),
	(55, 5, 3),
	(56, 4, 1),
	(56, 5, 1),
	(56, 6, 1),
	(56, 7, 1),
	(57, 10, 1),
	(58, 12, 1),
	(58, 13, 1),
	(58, 14, 1),
	(58, 15, 1),
	(59, 16, 1),
	(60, 17, 1),
	(60, 18, 1),
	(60, 19, 1);
/*!40000 ALTER TABLE `sequence_category` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.sequence_judge
DROP TABLE IF EXISTS `sequence_judge`;
CREATE TABLE IF NOT EXISTS `sequence_judge` (
  `sequenceId` int(10) unsigned NOT NULL,
  `judge` int(10) unsigned NOT NULL,
  `judgeType` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`sequenceId`,`judge`),
  KEY `FK_sequence_judge_judge` (`judge`),
  KEY `FK_sequence_judge_dictionary_judge_type` (`judgeType`),
  CONSTRAINT `FK_sequence_judge_dictionary_judge_type` FOREIGN KEY (`judgeType`) REFERENCES `dictionary_judge_type` (`typeId`),
  CONSTRAINT `FK_sequence_judge_judge` FOREIGN KEY (`judge`) REFERENCES `judge` (`userId`),
  CONSTRAINT `FK_sequence_judge_sequence` FOREIGN KEY (`sequenceId`) REFERENCES `sequence` (`sequenceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.sequence_judge: ~0 rows (приблизно)
DELETE FROM `sequence_judge`;
/*!40000 ALTER TABLE `sequence_judge` DISABLE KEYS */;
/*!40000 ALTER TABLE `sequence_judge` ENABLE KEYS */;


-- Dumping structure for таблиця powerlifting.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- Dumping data for table powerlifting.user: ~73 rows (приблизно)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userId`, `email`, `password`, `secondName`, `firstName`, `middleName`, `gender`, `birthday`, `photo`, `title/discharge`, `role`) VALUES
	(1, 'mail5@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Vukol', 'Olena', 'Karpivna', 0, '1984-04-12', 'female-no-avatar.png', 9, 0),
	(2, 'mail@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Mackenzie', 'John', 'Yanger', 1, '1900-03-01', 'male-no-avatar.jpeg', 2, 2),
	(3, 'mail1@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'bin Laden', 'Osama', 'Mohammed', 1, '1957-03-10', '3.png', 6, 2),
	(4, 'mail2@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Alba', 'Jessica', 'Marie', 0, '1981-04-28', '4.png', NULL, 0),
	(5, 'mail3@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Raymond', 'George', 'Martin', 1, '1948-09-20', '5.png', NULL, 0),
	(6, 'mail4@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Тимошенко', 'Юлія', 'Володимирівна', 0, '1960-11-27', '6.png', 2, 2),
	(7, 'SichaUA@ukr.net', '202cb962ac59075b964b07152d234b70', 'Січкар', 'Іван', 'Борисович', 1, '1994-06-20', '7.png', 4, 3),
	(8, 'mail6@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Statham', 'Jason', 'Michael', 1, '1967-07-26', '8.png', 1, 0),
	(9, 'mail7@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Порошенко', 'Петро', 'Олексійович', 1, '1965-09-12', '9.png', 8, 0),
	(10, 'mail8@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Obama', 'Barack', 'Hussein', 1, '1961-08-04', '10.png', NULL, 0),
	(11, 'mail9@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Green', 'Eva', 'Gaëlle', 0, '1980-07-06', '11.png', NULL, 0),
	(12, 'mail10@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Merkel', 'Angela', 'Dorothea', 0, '1954-07-17', '12.png', NULL, 0),
	(13, 'mail11@mail.com', 'sdvvfsdvfsvfdsvfdsvfdsvfsdvdsv', 'Albanet', 'dfgv', 'efdgv', 1, '2015-02-10', 'male-no-avatar.jpeg', NULL, 0),
	(16, 'mail15@mail.com', '6ea636c3d0b4376a5012679108d43bf8', 'Surname', 'Name', 'middleName', 0, '1970-05-13', 'female-no-avatar.png', NULL, 0),
	(20, 'Disk94@ukr.net', '680a6c5748b25125eabee49b27a5bad8', 'Sichkar', 'Ivan', 'Borysovich', 1, '1994-06-20', 'male-no-avatar.jpeg', NULL, 0),
	(21, 'mail16@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Лийвой', 'Олександр', 'Людвігович', 1, '1990-08-05', 'male-no-avatar.jpeg', NULL, 0),
	(22, 'mail17@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Чупринко', 'Іван', 'Юхимович', 1, '1986-05-11', 'male-no-avatar.jpeg', NULL, 0),
	(23, 'mail18@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Чернишов', 'Сергій', 'Олександрович', 1, '1991-08-08', 'male-no-avatar.jpeg', NULL, 0),
	(24, 'mail19@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Шинкарук', 'Артем', 'Ігорович', 1, '1991-04-04', 'male-no-avatar.jpeg', NULL, 0),
	(25, 'mail20@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Мдівнішвілі', 'Іраклі', 'Іракльович', 1, '1980-07-19', 'male-no-avatar.jpeg', NULL, 0),
	(26, 'mail21@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Баленков', 'Андрій', 'Олександрович', 1, '1981-01-30', 'male-no-avatar.jpeg', NULL, 0),
	(27, 'mail22@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Шляхта', 'Олександр', 'Іванович', 1, '1979-11-17', 'male-no-avatar.jpeg', NULL, 0),
	(28, 'mail23@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Григоров', 'Віталій', 'Миколайович', 1, '1990-02-14', 'male-no-avatar.jpeg', NULL, 0),
	(29, 'mail24@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Андрущенко', 'Андрій', 'Олександрович', 1, '1987-08-29', 'male-no-avatar.jpeg', NULL, 0),
	(30, 'mail25@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Лясковець', 'Антон', 'Васильович', 1, '1989-01-28', 'male-no-avatar.jpeg', NULL, 0),
	(31, 'mail26@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Місюренко', 'Артем', 'Олександрович', 1, '1991-03-24', 'male-no-avatar.jpeg', NULL, 0),
	(32, 'mail27@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Мартинюк', 'Олександр', 'Васильович', 1, '1987-10-13', 'male-no-avatar.jpeg', NULL, 0),
	(33, 'mail28@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Хамула', 'Кирило', 'Андрійович', 1, '1990-05-30', 'male-no-avatar.jpeg', NULL, 0),
	(34, 'mail29@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Лимар', 'Максим', 'Андрійович', 1, '1989-07-11', 'male-no-avatar.jpeg', NULL, 0),
	(35, 'mail30@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Волощук', 'Сергій', 'Анатолійович', 1, '1989-07-04', 'male-no-avatar.jpeg', NULL, 0),
	(36, 'mail31@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Череватий', 'Євген', 'Олександрович', 1, '1988-08-27', 'male-no-avatar.jpeg', NULL, 0),
	(37, 'mail32@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Беттяр', 'Володимир', 'Вікторович', 1, '1984-04-25', 'male-no-avatar.jpeg', NULL, 0),
	(38, 'mail33@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Кримов', 'Андрій', 'Миколайович', 1, '1985-04-27', 'male-no-avatar.jpeg', NULL, 0),
	(39, 'mail34@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Левицький', 'Станіслав', 'Янович', 1, '1984-08-27', 'male-no-avatar.jpeg', NULL, 0),
	(40, 'mail35@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Бубен', 'Тарас', 'Романович', 1, '1990-08-30', 'male-no-avatar.jpeg', NULL, 0),
	(41, 'mail36@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Смольєнінов', 'Максим', 'Юрійович', 1, '1997-07-15', 'male-no-avatar.jpeg', NULL, 0),
	(42, 'mail37@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Бортник', 'Сергій', 'Олегович', 1, '1983-03-17', 'male-no-avatar.jpeg', NULL, 0),
	(43, 'mail38@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Полєщук', 'Артем', 'Андрійович', 1, '1982-09-01', 'male-no-avatar.jpeg', NULL, 0),
	(44, 'mail39@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Лапенко', 'Олександр', 'Миколайович', 1, '1985-03-15', 'male-no-avatar.jpeg', NULL, 0),
	(45, 'mail40@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Кожушко', 'Сергій', 'Вікторович', 1, '1985-07-30', 'male-no-avatar.jpeg', NULL, 0),
	(46, 'mail41@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Шейнін', 'Владислав', 'Геннадійович', 1, '1992-04-13', 'male-no-avatar.jpeg', NULL, 0),
	(47, 'mail42@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Франков', 'Сергій', 'Васильович', 1, '1979-05-30', 'male-no-avatar.jpeg', NULL, 0),
	(48, 'mail43@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Лесковець', 'Віктор', 'Володимирович', 1, '1990-05-15', 'male-no-avatar.jpeg', NULL, 0),
	(49, 'mail44@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Немудрий', 'Анатолій', 'Борисович', 1, '1989-08-05', 'male-no-avatar.jpeg', NULL, 0),
	(50, 'mail45@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Мартиненко', 'Ярослав', 'Ігорович', 1, '1991-11-14', 'male-no-avatar.jpeg', NULL, 0),
	(51, 'mail46@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Дудченко', 'Олександр', 'Олексійович', 1, '1990-08-30', 'male-no-avatar.jpeg', NULL, 0),
	(52, 'mail47@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Миголинець', 'Юрій', 'Дмитрович', 1, '1977-05-18', 'male-no-avatar.jpeg', NULL, 0),
	(53, 'mail48@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Мацюк', 'Андрій', 'Ілліч', 1, '1987-12-07', 'male-no-avatar.jpeg', NULL, 0),
	(54, 'mail49@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Ковальчук', 'Юрій', 'Олександрович', 1, '1979-04-08', 'male-no-avatar.jpeg', NULL, 0),
	(55, 'mail50@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Петренко', 'Олександр', 'Іванович', 1, '1979-04-29', 'male-no-avatar.jpeg', NULL, 0),
	(56, 'mail51@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Тетерук', 'Олександр', 'Васильович', 1, '1983-08-03', 'male-no-avatar.jpeg', NULL, 0),
	(57, 'mail52@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Ізосімов', 'Сергій', 'Анатолійович', 1, '1968-10-20', 'male-no-avatar.jpeg', NULL, 0),
	(58, 'mail53@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Боднар', 'Віктор', 'Богданович', 1, '1985-09-23', 'male-no-avatar.jpeg', NULL, 0),
	(59, 'mail54@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Колєснік', 'Юрій', 'Павлович', 1, '1973-01-04', 'male-no-avatar.jpeg', NULL, 0),
	(60, 'mail55@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Грицан', 'Богдан', 'Антонович', 1, '1981-02-24', 'male-no-avatar.jpeg', NULL, 0),
	(61, 'mail56@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Олійник', 'Артур', 'Михайлович', 1, '1989-08-21', 'male-no-avatar.jpeg', NULL, 0),
	(62, 'mail57@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Сердюк', 'Валерій', 'В\'ячеславович', 1, '1976-10-31', 'male-no-avatar.jpeg', NULL, 0),
	(63, 'mail58@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Ситар', 'Ярослав', 'Михайлович', 1, '1973-05-21', 'male-no-avatar.jpeg', NULL, 0),
	(64, 'mail59@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Пронін', 'Олександр', 'Олександрович', 1, '1978-05-29', 'male-no-avatar.jpeg', NULL, 0),
	(109, 'mail60@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Коляденко', 'Мар\'яна', 'Юріївна', 0, '1991-09-23', 'female-no-avatar.png', NULL, 0),
	(110, 'mail61@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Павлик', 'Марія', 'Зеновіївна', 0, '1991-08-09', 'female-no-avatar.png', NULL, 0),
	(111, 'mail62@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Шевлякова', 'Олена', 'Валентинівна', 0, '1987-04-26', 'female-no-avatar.png', NULL, 0),
	(112, 'mail63@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Савон', 'Анна', 'Миколаївна', 0, '1986-11-24', 'female-no-avatar.png', NULL, 0),
	(113, 'mail64@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Наконечна', 'Анастасія', 'Володимирівна', 0, '1995-05-02', 'female-no-avatar.png', NULL, 0),
	(114, 'mail65@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Гончар', 'Ганна', 'Володимирівна', 0, '1993-09-21', 'female-no-avatar.png', NULL, 0),
	(115, 'mail66@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Макарова', 'Маргарита', 'Сергіївна', 0, '1990-05-02', 'female-no-avatar.png', NULL, 0),
	(116, 'mail67@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Лєншина', 'Юлія', 'Михайлівна', 0, '1985-10-30', 'female-no-avatar.png', NULL, 0),
	(117, 'mail68@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Мелешко', 'Яна', 'Олександрівна', 0, '1988-03-22', 'female-no-avatar.png', NULL, 0),
	(118, 'mail69@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Воробйова', 'Ірина', 'Віталіївна', 0, '1980-04-20', 'female-no-avatar.png', NULL, 0),
	(119, 'mail70@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Деркач', 'Любов', 'Павлівна', 0, '1985-09-30', 'female-no-avatar.png', NULL, 0),
	(120, 'mail71@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Павлик', 'Ірина', 'Зеновіївна', 0, '1987-11-08', 'female-no-avatar.png', NULL, 0),
	(121, 'mail72@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Добрякова', 'Марта', 'Олексіївна', 0, '1986-03-10', 'female-no-avatar.png', NULL, 0),
	(122, 'mail73@mail.com', 'c20ad4d76fe97759aa27a0c99bff6710', 'Білоусова', 'Тетяна', 'Вікторівна', 0, '1975-04-12', 'female-no-avatar.png', NULL, 0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for тригер powerlifting.group_participant_before_update
DROP TRIGGER IF EXISTS `group_participant_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `group_participant_before_update` BEFORE UPDATE ON `group_participant` FOR EACH ROW BEGIN

	if(NEW.status = 1 || (NEW.status = 2 AND OLD.participantWeight <> NEW.participantWeight)) THEN
		if (NEW.participantWeight >= (SELECT dw.maxWeight
											  FROM dictionary_weight_category dw
											  WHERE dw.categoryId = (SELECT p.category
											  								 FROM participant p
																			 WHERE p.participantId = NEW.participant))
			OR
			NEW.participantWeight < (SELECT dw.minWeight
											  FROM dictionary_weight_category dw
											  WHERE dw.categoryId = (SELECT p.category
											  								 FROM participant p
																			 WHERE p.participantId = NEW.participant))) 
		THEN
			SET NEW.status = 2;
		ELSE
			SET NEW.status = 1;
		END IF;
	END IF;
		
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Dumping structure for тригер powerlifting.stop_duplicate_region
DROP TRIGGER IF EXISTS `stop_duplicate_region`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `stop_duplicate_region` BEFORE INSERT ON `dictionary_region` FOR EACH ROW BEGIN	
	IF((SELECT COUNT(*)
	 	  FROM dictionary_region d
	 	  WHERE d.name = NEW.name) > 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Inserting FAILED! This region is already exist.";
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


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


-- Dumping structure for вид powerlifting.group_participant_with_attempt
DROP VIEW IF EXISTS `group_participant_with_attempt`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `group_participant_with_attempt`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` VIEW `group_participant_with_attempt` AS SELECT *, 			  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 1 AND a.attemptNumber = 1) AS firstAttemptSQ,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 1 AND a.attemptNumber = 1) AS firstAttemptSQStatus,
			  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 1 AND a.attemptNumber = 2) AS secondAttemptSQ,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 1 AND a.attemptNumber = 2) AS secondAttemptSQStatus,
			  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 1 AND a.attemptNumber = 3) AS thirdAttemptSQ,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 1 AND a.attemptNumber = 3) AS thirdAttemptSQStatus,
							  
							  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 2 AND a.attemptNumber = 1) AS firstAttemptBP,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 2 AND a.attemptNumber = 1) AS firstAttemptBPStatus,

			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 2 AND a.attemptNumber = 2) AS secondAttemptBP,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 2 AND a.attemptNumber = 2) AS secondAttemptBPStatus,

			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 2 AND a.attemptNumber = 3) AS thirdAttemptBP,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 2 AND a.attemptNumber = 3) AS thirdAttemptBPStatus,
			  
			  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 3 AND a.attemptNumber = 1) AS firstAttemptDL,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 3 AND a.attemptNumber = 1) AS firstAttemptDLStatus,
			  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 3 AND a.attemptNumber = 2) AS secondAttemptDL,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 3 AND a.attemptNumber = 2) AS secondAttemptDLStatus,
			  
			  (SELECT a.weight
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 3 AND a.attemptNumber = 3) AS thirdAttemptDL,
			  (SELECT a.`status`
			  FROM attempt a
			  WHERE a.groupParticipant = gp.groupParticipantId AND a.exercise = 3 AND a.attemptNumber = 3) AS thirdAttemptDLStatus
			  
FROM group_participant gp ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
