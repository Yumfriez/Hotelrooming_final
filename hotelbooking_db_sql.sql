CREATE DATABASE  IF NOT EXISTS `hotelrooms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hotelrooms`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hotelrooms
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL COMMENT 'Логин уч. записи.',
  `pass` varchar(20) NOT NULL COMMENT 'Пароль уч. записи.',
  `name` varchar(20) NOT NULL COMMENT 'Имя.',
  `surname` varchar(20) NOT NULL COMMENT 'Фамилия.',
  `email` varchar(20) NOT NULL COMMENT 'Адрес электронной почты.',
  `role` varchar(25) NOT NULL DEFAULT 'USER' COMMENT 'Роль (пользователь - 0, администратор - 1).',
  `block_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT 'Статус блокировки (0 - не заблокирован, 1 - заблокирован)',
  `locale` varchar(50) NOT NULL,
  PRIMARY KEY (`u_id`,`login`),
  KEY `login_INDEX` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='Таблица, содержащая данные о зарегестрированных учётных записях пользователей и администраторов.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'arnik','auto251','Никита','Аробей','arnik@gmail.com','USER',0,'en'),(2,'bonnie','gjrk25a2','Алексей','Игнатенко','alex1992@mail.ru','ADMINISTRATOR',0,'en'),(3,'chappie','lem217','Владимир','Ларионов','vova1988@mail.ru','USER',0,'en'),(4,'dellusion','del562','Анастасия','Уланова','nastya_ulanova@mail.','USER',0,'en'),(5,'diamond','jkl827','Руслан','Баланович','ruslan1998@mail.ru','USER',1,'en'),(6,'dondo','den256','Даниил','Ишутин','danik1992@mail.ru','USER',0,'en'),(7,'fable','f12345','Дмитрий','Рогозин','dmitriy_rog@gmail.co','ADMINISTRATOR',0,'en'),(8,'grenion','ghjq25','Станислав','Устинов','stanis1978@mail.ru','USER',0,'en'),(9,'jackie','rtg296','Джеки','Чан','jackie_karate@gmail.','USER',0,'en'),(10,'jimbo','frg92','Андрей','Фонарёв','andrew252@gmail.com','USER',0,'en'),(11,'lenny','kfghd662','Николай','Авдеев','kolya1997@mail.ru','USER',0,'en'),(12,'omelion','saf51','Василий','Якутин','vasya1969@gmail.com','USER',0,'en'),(13,'trinity','tr222','Олег','Штатов','shtatov@gmail.com','USER',1,'en'),(14,'user1','qw123','Иван','Будаев','budaev_vanya@mail.ru','USER',0,'ru'),(66,'Canelo','s12345','Saul','Alvarez','saul@gmail.com','USER',0,'ru'),(67,'Sniper','dantes','Aleksandr','Pushkin','pushkin1799@mail.ru','USER',0,'ru_BY');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификационный номер комментария.',
  `c_date` date NOT NULL COMMENT 'Дата написания комментария.',
  `text` varchar(255) NOT NULL COMMENT 'Текст комментария.',
  `u_id` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `userFK_idx` (`u_id`),
  CONSTRAINT `useFK` FOREIGN KEY (`u_id`) REFERENCES `account` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Таблица, в которой хранятся комментарии, оставленные пользователями.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'2017-12-21','Прекрасный отель! Всем доволен!',3),(2,'2017-12-20','Отличная скорость обслуживания!',4),(3,'2017-12-18','Не жалею потраченных денег.',6),(4,'2017-12-22','Осталось положительное впечатление о персонале.',12),(5,'2017-12-22','Очень уютные номера, но дороговато.',8);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `contract_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификационный номер контракта на заселение.',
  `date_in` date NOT NULL COMMENT 'Дата заселения в номер.',
  `date_out` date NOT NULL COMMENT 'Дата выселения из номера.',
  `total_price` decimal(10,0) NOT NULL COMMENT 'Итоговая цена за снятые номера.',
  `u_id` int(11) NOT NULL,
  PRIMARY KEY (`contract_id`),
  KEY `total_price_INDEX` (`total_price`),
  KEY `date_INDEX` (`date_in`,`date_out`),
  KEY `userFK_idx` (`u_id`),
  CONSTRAINT `userFK` FOREIGN KEY (`u_id`) REFERENCES `account` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='Таблица хранит контракты, заключённые с пользователем, после заполнения им заявки на съём номера, подтверждённой администратором.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (1,'2017-12-20','2017-12-28',20000,3),(2,'2017-12-17','2017-12-20',961,4),(3,'2017-12-15','2017-12-26',1650,6),(4,'2017-12-18','2017-12-22',7200,12),(5,'2017-12-22','2017-12-25',692,8),(6,'2017-10-20','2017-10-30',1500,6),(7,'2017-09-27','2017-10-10',41600,3),(8,'2017-05-13','2017-05-25',4200,6);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotelroom`
--

DROP TABLE IF EXISTS `hotelroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hotelroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Номер отеля',
  `number` smallint(5) NOT NULL,
  `places_count` tinyint(3) NOT NULL COMMENT 'Количество мест в номере.',
  `daily_price` decimal(10,2) NOT NULL COMMENT 'Цена за сутки.',
  `floor` tinyint(3) NOT NULL COMMENT 'Этаж номера в отеле.',
  `contract_id` int(11) DEFAULT NULL COMMENT 'Идентификационный номер контракта, на который оформлен номер (null, если номер свободен).',
  `room_type_id` int(11) NOT NULL COMMENT 'Класс номера.',
  `imageName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number_UNIQUE` (`number`),
  KEY `price_INDEX` (`daily_price`),
  KEY `fk_hotelroom_contract1_idx` (`contract_id`),
  KEY `type_fk` (`room_type_id`),
  CONSTRAINT `fk_hotelroom_contract1` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`contract_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `type_fk` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='Таблица, содержащая данные о всех номерах отеля.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotelroom`
--

LOCK TABLES `hotelroom` WRITE;
/*!40000 ALTER TABLE `hotelroom` DISABLE KEYS */;
INSERT INTO `hotelroom` VALUES (2,2,5,270.35,1,NULL,1,'2.jpg'),(3,3,2,250.50,2,NULL,2,'3.jpeg'),(4,4,1,170.45,2,NULL,2,'4.jpg'),(5,5,3,320.25,2,2,2,'5.jpg'),(6,7,2,400.65,3,NULL,3,'7.jpg'),(7,8,2,350.00,3,NULL,3,'8.jpg'),(8,9,2,150.00,4,3,4,'9.jpg'),(9,10,2,140.50,4,NULL,4,'10.jpg'),(10,11,3,170.55,4,NULL,4,'11.jpg'),(11,12,3,1500.00,5,NULL,6,'12.jpg'),(12,13,4,1800.00,5,4,6,'13.jpg'),(13,14,3,2500.00,6,1,5,'14.jpg'),(14,15,5,3200.00,6,NULL,5,'15.jpg'),(15,16,4,3000.00,6,NULL,5,'16.jpg'),(16,6,2,256.25,2,NULL,2,'6.jpg'),(23,1,4,230.50,1,NULL,1,'1.jpg');
/*!40000 ALTER TABLE `hotelroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL COMMENT 'Идентификационный номер контракта',
  `places_count` tinyint(3) NOT NULL COMMENT 'Количество мест в номере, необходимые пользователю.',
  `date_in` date NOT NULL COMMENT 'Дата заселения пользователя.',
  `days_count` smallint(10) NOT NULL COMMENT 'Количество дней пребывания пользователя в отеле.',
  `hotelroom_class` varchar(45) DEFAULT NULL COMMENT 'Класс номер, указываемый в заявке (если не выбран, то подразумевается любой).',
  `u_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `userFK_idx` (`u_id`),
  CONSTRAINT `usFK` FOREIGN KEY (`u_id`) REFERENCES `account` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Заявка пользователя на съём номера, в которой указаны необходимые ему условия.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,2,'2017-12-30',5,'Стандартный',14),(2,4,'2017-12-25',10,'Семейный',10),(3,5,'2017-12-31',2,'Люкс',1),(4,2,'2017-12-24',8,'Бизнес',11),(5,1,'2017-12-25',15,'Бизнес',9);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_types`
--

DROP TABLE IF EXISTS `room_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_types_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_types`
--

LOCK TABLES `room_types` WRITE;
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
INSERT INTO `room_types` VALUES (2,'Бизнес'),(6,'ДеЛюкс'),(3,'Для молодожёнов'),(5,'Люкс'),(1,'Семейный'),(4,'Стандартный');
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-17 14:11:47
