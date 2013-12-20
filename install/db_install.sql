-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 19, 2013 at 04:34 PM
-- Server version: 5.5.32
-- PHP Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `cloutree`
--
CREATE DATABASE IF NOT EXISTS `cloutree` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cloutree`;

-- --------------------------------------------------------

--
-- Table structure for table `access`
--

CREATE TABLE IF NOT EXISTS `access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user` varchar(64) NOT NULL,
  `action` varchar(64) NOT NULL,
  `details` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=307 ;

-- --------------------------------------------------------

--
-- Table structure for table `apicall`
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
-- Table structure for table `apihost`
--

CREATE TABLE IF NOT EXISTS `apihost` (
  `name` varchar(64) NOT NULL,
  `url` varchar(128) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `status` int(4) NOT NULL DEFAULT '0',
  `instance` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `instance`
--

CREATE TABLE IF NOT EXISTS `instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `tenant` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `tenant` (`tenant`),
  KEY `tenant_2` (`tenant`),
  KEY `name_2` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Table structure for table `model`
--

CREATE TABLE IF NOT EXISTS `model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(32) NOT NULL DEFAULT 'PMML',
  `activeversion` int(11) NOT NULL,
  `released` tinyint(1) NOT NULL DEFAULT '0',
  `preprocessor` text,
  `postprocessor` text,
  `instance` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `instance` (`instance`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `modelrevision`
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

-- --------------------------------------------------------

--
-- Table structure for table `tenant`
--

CREATE TABLE IF NOT EXISTS `tenant` (
  `name` varchar(64) NOT NULL,
  `adminuser` int(11) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE IF NOT EXISTS `type` (
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`id`) VALUES
('DYNAMICR'),
('PMML'),
('STATICR');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant` varchar(64) NOT NULL,
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
  KEY `tenant` (`tenant`),
  KEY `tenant_2` (`tenant`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `user`
--

;
