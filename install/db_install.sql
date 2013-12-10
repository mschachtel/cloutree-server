-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 28. Okt 2013 um 10:12
-- Server Version: 5.5.31
-- PHP-Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `cloutree`
--
CREATE DATABASE IF NOT EXISTS `cloutree` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cloutree`;

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `access`
--

CREATE TABLE IF NOT EXISTS `access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user` varchar(64) NOT NULL,
  `action` varchar(64) NOT NULL,
  `details` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Daten f√ºr Tabelle `access`
--

INSERT INTO `access` (`id`, `timestamp`, `user`, `action`, `details`) VALUES
(1, '2013-10-14 12:49:47', 'mschachtel', 'LOGIN', 'SUCCEED'),
(2, '2013-10-14 12:49:57', 'mschachtel', 'STORE_MODEL', 'test123'),
(3, '2013-10-25 11:57:21', 'mschachtel', 'LOGIN', 'SUCCEED'),
(4, '2013-10-25 13:49:29', 'mschachtel', 'LOGIN', 'SUCCEED'),
(5, '2013-10-25 13:49:49', 'mschachtel', 'STORE_REVISION', 'test123_1'),
(6, '2013-10-25 13:55:03', 'mschachtel', 'LOGIN', 'SUCCEED'),
(7, '2013-10-25 13:56:34', 'mschachtel', 'STORE_REVISION', 'test123_1'),
(8, '2013-10-25 13:56:38', 'mschachtel', 'UPDATE_MODEL', 'test123'),
(9, '2013-10-25 14:56:12', 'mschachtel', 'LOGIN', 'SUCCEED');

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `apicall`
--

CREATE TABLE IF NOT EXISTS `apicall` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user` varchar(64) NOT NULL,
  `model` varchar(64) NOT NULL,
  `action` varchar(64) NOT NULL,
  `parameters` text,
  `returnvalues` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `instance`
--

CREATE TABLE IF NOT EXISTS `instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `tenant` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `tenant` (`tenant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `model`
--

CREATE TABLE IF NOT EXISTS `model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `activeversion` int(11) NOT NULL,
  `released` tinyint(1) NOT NULL DEFAULT '0',
  `preprocessor` text,
  `postprocessor` text,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Daten f√ºr Tabelle `model`
--

INSERT INTO `model` (`id`, `name`, `activeversion`, `released`, `preprocessor`, `postprocessor`) VALUES
(1, 'test123', 1, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `modelrevision`
--

CREATE TABLE IF NOT EXISTS `modelrevision` (
  `id` varchar(255) NOT NULL,
  `model` bigint(20) NOT NULL,
  `revision` int(11) NOT NULL,
  `file` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `model` (`model`,`revision`),
  KEY `model_2` (`model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten f√ºr Tabelle `modelrevision`
--

INSERT INTO `modelrevision` (`id`, `model`, `revision`, `file`) VALUES
('test123_1', 1, 1, '/live/pmml/test123/test123_1.xml');

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `tenant`
--

CREATE TABLE IF NOT EXISTS `tenant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `adminuser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Daten f√ºr Tabelle `tenant`
--

INSERT INTO `tenant` (`id`, `name`, `adminuser`) VALUES
(1, 'PARSHIP', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur f√ºr Tabelle `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varbinary(64) NOT NULL,
  `salt` varbinary(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `firstname` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `permission` text,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `tenant` (`tenant`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Daten f√ºr Tabelle `user`
--

INSERT INTO `user` (`id`, `tenant`, `username`, `password`, `salt`, `name`, `firstname`, `email`, `permission`, `active`) VALUES
(1, 1, 'mschachtel', '∆Å6êê:(9ÔŸ§°n1°ﬂs', 'õˆ2¥Z©9', 'Schachtel', 'Marc', 'schachtel.marc@googlemail.com', 'DEVELOPER,SUPERUSER', 1),
(2, 1, 'mselk', 's¶ÑDH¢πRà”c»d€(ƒ', '◊˛˜ÈF’Ç', 'Selk', 'Mario', 'mario.selk@parship.com', 'SUPERUSER', 1),
(3, 1, 'parship-app', '¯¿FzHNUÄúÁ8/∂9Y', 'Nç†ÒˆB¶€', 'APPLICATION', 'PARSHIP', '', 'API', 1),
(4, 1, 'mjaeckel', 'y°Th0Üñ≠n§)‚µl3AM0˝', '\0wHñ—1ã', 'J√§ckel', 'Mario', 'mario.jaeckel@parship.com', 'DEVELOPER', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
