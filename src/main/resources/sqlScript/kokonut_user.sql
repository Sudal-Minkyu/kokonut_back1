/*
SQLyog Professional v12.09 (64 bit)
MySQL - 10.6.7-MariaDB-log : Database - kokonut_user
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kokonut_user` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;

USE `kokonut_user`;

/*Table structure for table `common` */

DROP TABLE IF EXISTS `common`;

CREATE TABLE `common` (
  `IDX` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '인덱스(기본적용,수정불가)',
  `SALT` varchar(8) DEFAULT NULL COMMENT 'SALT(기본적용,수정불가)',
  `NAME` varchar(20) NOT NULL COMMENT '이름(기본적용,수정불가)',
  `GENDER` varchar(1) NOT NULL COMMENT '성별(기본적용,수정불가)',
  `BIRTH` varchar(100) NOT NULL COMMENT '생년월일(기본적용,수정불가)',
  `PHONE_NUMBER` varchar(300) NOT NULL COMMENT '핸드폰번호(기본적용,수정불가)',
  `REGDATE` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '등록 일시(기본적용,수정불가)',
  `ID` varchar(128) NOT NULL COMMENT '아이디(기본적용,수정불가)',
  `PASSWORD` varchar(256) NOT NULL COMMENT '비밀번호(암호화,기본적용,수정불가)',
  `PERSONAL_INFO_AGREE` varchar(1) DEFAULT 'N' COMMENT '개인정보 동의(기본적용,수정불가)',
  `STATE` bigint(20) NOT NULL DEFAULT 1 COMMENT '상태(기본적용,수정불가) - [0:탈퇴, 1:사용, 2:휴면]',
  `LAST_LOGIN_DATE` timestamp NULL DEFAULT NULL COMMENT '최종 로그인 일시(기본적용,수정불가)',
  `MODIFY_DATE` timestamp NULL DEFAULT NULL COMMENT '수정 일시(기본적용,수정불가)',
  `EMAIL` varchar(280) DEFAULT NULL COMMENT '이메일(기본적용,수정불가)',
  PRIMARY KEY (`IDX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `common` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
