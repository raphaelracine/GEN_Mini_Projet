SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS scotlandyard;
CREATE SCHEMA scotlandyard;
USE scotlandyard;

CREATE TABLE user (
	username VARCHAR(20) NOT NULL,
	password VARCHAR(20) NOT NULL,
	nbGame INT UNSIGNED NOT NULL DEFAULT 0,
	nbWin INT UNSIGNED NOT NULL DEFAULT 0,
	connected BOOLEAN DEFAULT FALSE,
	blocked BOOLEAN DEFAULT FALSE,	
	PRIMARY KEY (username)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE map (
	name VARCHAR(20) NOT NULL,
	picture VARCHAR(100) NOT NULL,
	PRIMARY KEY (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE station (
	station_id INT UNSIGNED NOT NULL,
	number INT UNSIGNED NOT NULL,
	x INT UNSIGNED NOT NULL,
	y INT UNSIGNED NOT NULL,
	type ENUM('taxi', 'taxibus', 'taxibussubway'),
	map_fk VARCHAR(20) NOT NULL,
	PRIMARY KEY (station_id),
	CONSTRAINT `fk_station_map` FOREIGN KEY (map_fk) REFERENCES map (name) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE link (
	first_station_fk INT UNSIGNED NOT NULL,
	second_station_fk INT UNSIGNED NOT NULL,
	type ENUM('taxi', 'bus', 'subway'),
	PRIMARY KEY(first_station_fk, second_station_fk),
	CONSTRAINT `fk_link_first_station` FOREIGN KEY (first_station_fk) REFERENCES station (station_id),
	CONSTRAINT `fk_link_second_station` FOREIGN KEY (second_station_fk) REFERENCES station (station_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
