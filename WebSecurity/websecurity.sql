CREATE TABLE `user` (
  `Id` varchar(100) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Authority` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
  `Enabled` tinyint(1) unsigned zerofill DEFAULT '1',
  `Failure_cnt` tinyint(1) DEFAULT '0',
  `Access_date` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8