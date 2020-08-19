/*
SQLyog Community
MySQL - 10.4.10-MariaDB 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `persistent_logins` (
	`username` varchar (192),
	`series` varchar (192),
	`token` varchar (192),
	`last_used` timestamp 
); 
