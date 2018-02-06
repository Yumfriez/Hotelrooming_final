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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='Таблица, содержащая данные о зарегестрированных учётных записях пользователей и администраторов.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'arnik','auto251','Никита','Аробей','arnik@gmail.com','USER',0,'en'),(2,'bonnie','gjrk25a2','Алексей','Игнатенко','alex1992@mail.ru','ADMINISTRATOR',0,'en'),(3,'chappie','lem217','Владимир','Ларионов','vova1988@mail.ru','USER',0,'en'),(4,'dellusion','del562','Анастасия','Уланова','nastya_ulanova@mail.','USER',1,'en'),(5,'diamond','jkl827','Руслан','Баланович','ruslan1998@mail.ru','USER',1,'en'),(6,'dondo','den256','Даниил','Ишутин','danik1992@mail.ru','USER',0,'en'),(7,'fable','f12345','Дмитрий','Рогозин','dmitriy_rog@gmail.co','ADMINISTRATOR',0,'ru'),(8,'grenion','ghjq25','Станислав','Устинов','stanis1978@mail.ru','USER',0,'en'),(9,'jackie','rtg296','Джеки','Чан','jackie_karate@gmail.','USER',1,'en'),(10,'jimbo','frg92','Андрей','Фонарёв','andrew252@gmail.com','USER',0,'en'),(11,'lenny','kfghd662','Николай','Авдеев','kolya1997@mail.ru','USER',0,'en'),(12,'omelion','saf51','Василий','Якутин','vasya1969@gmail.com','USER',0,'en'),(13,'trinity','tr222','Олег','Штатов','shtatov@gmail.com','USER',1,'en'),(14,'user1','qw123','Иван','Будаев','budaev_vanya@mail.ru','USER',0,'ru'),(66,'Canelo','s12345','Saul','Alvarez','saul@gmail.com','USER',0,'en'),(67,'Sniper','dantes','Aleksandr','Pushkin','pushkin1799@mail.ru','USER',0,'ru_BY'),(69,'aridel','aridel','Arina','Delendik','aridel@mail.ru','USER',0,'ru'),(70,'aleks123','A12345','Aleksei','Batishev','aleks@mail.com','USER',0,'en_GB');
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='Таблица, в которой хранятся комментарии, оставленные пользователями.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (19,'2018-01-24','Great hotel!',66),(22,'2018-02-05','Cool',70);
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
  `hotelroom_id` int(11) NOT NULL,
  `accept_status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`contract_id`),
  KEY `total_price_INDEX` (`total_price`),
  KEY `date_INDEX` (`date_in`,`date_out`),
  KEY `userFK_idx` (`u_id`),
  KEY `contract_hotelroom_id_fk` (`hotelroom_id`),
  CONSTRAINT `contract_hotelroom_id_fk` FOREIGN KEY (`hotelroom_id`) REFERENCES `hotelroom` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userFK` FOREIGN KEY (`u_id`) REFERENCES `account` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='Таблица хранит контракты, заключённые с пользователем, после заполнения им заявки на съём номера, подтверждённой администратором.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (20,'2018-01-31','2018-02-07',1050,66,8,1),(21,'2018-02-28','2018-03-20',50000,3,13,0),(22,'2018-02-25','2018-03-12',4055,1,2,0);
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
  `room_type_id` int(11) NOT NULL COMMENT 'Класс номера.',
  `imageName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number_UNIQUE` (`number`),
  KEY `price_INDEX` (`daily_price`),
  KEY `type_fk` (`room_type_id`),
  CONSTRAINT `type_fk` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='Таблица, содержащая данные о всех номерах отеля.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotelroom`
--

LOCK TABLES `hotelroom` WRITE;
/*!40000 ALTER TABLE `hotelroom` DISABLE KEYS */;
INSERT INTO `hotelroom` VALUES (2,2,5,270.35,1,1,'2.jpg'),(3,3,2,250.50,2,2,'3.jpeg'),(4,4,1,170.45,2,2,'4.jpg'),(5,5,3,320.25,2,2,'5.jpg'),(6,7,2,400.65,3,3,'7.jpg'),(7,8,2,350.00,3,3,'8.jpg'),(8,9,2,150.00,4,4,'9.jpg'),(9,10,2,140.50,4,4,'10.jpg'),(10,11,3,170.55,4,4,'11.jpg'),(11,12,3,1500.00,5,6,'12.jpg'),(12,13,4,1800.00,5,6,'13.jpg'),(13,14,3,2500.00,6,5,'14.jpg'),(14,15,5,3200.00,6,5,'15.jpg'),(15,16,4,3000.00,6,5,'16.jpg'),(16,6,2,256.25,2,2,'6.jpg'),(23,1,4,230.50,1,1,'1.jpg'),(31,54,4,5325.50,25,5,'far_cry_22-wallpaper-1080x1920.jpg');
/*!40000 ALTER TABLE `hotelroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификационный номер контракта',
  `places_count` tinyint(3) NOT NULL COMMENT 'Количество мест в номере, необходимые пользователю.',
  `date_in` date NOT NULL COMMENT 'Дата заселения пользователя.',
  `days_count` smallint(10) NOT NULL COMMENT 'Количество дней пребывания пользователя в отеле.',
  `room_type_id` int(11) NOT NULL COMMENT 'Класс номер, указываемый в заявке (если не выбран, то подразумевается любой).',
  `u_id` int(11) NOT NULL,
  `min_daily_price` decimal(10,0) NOT NULL,
  `max_daily_price` decimal(10,0) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `userFK_idx` (`u_id`),
  KEY `order_room_types_id_fk` (`room_type_id`),
  CONSTRAINT `order_room_types_id_fk` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `usFK` FOREIGN KEY (`u_id`) REFERENCES `account` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='Заявка пользователя на съём номера, в которой указаны необходимые ему условия.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (13,2,'2018-02-06',10,4,66,100,1000),(16,3,'2018-02-14',20,6,6,250,400);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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

-- Dump completed on 2018-02-06 17:39:37
