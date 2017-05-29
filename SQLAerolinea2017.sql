-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.17-0ubuntu0.16.04.1 - (Ubuntu)
-- SO del servidor:              Linux
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para bd_proyecto_aviones15
DROP DATABASE IF EXISTS `bd_proyecto_aviones15`;
CREATE DATABASE IF NOT EXISTS `bd_proyecto_aviones15` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bd_proyecto_aviones15`;

-- Volcando estructura para tabla bd_proyecto_aviones15.aeropuerto
DROP TABLE IF EXISTS `aeropuerto`;
CREATE TABLE IF NOT EXISTS `aeropuerto` (
  `CODIGO_AEROPUERTO` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO_IATA` varchar(5) NOT NULL,
  `CIUDAD` varchar(50) NOT NULL,
  `PAIS` varchar(50) NOT NULL,
  PRIMARY KEY (`CODIGO_AEROPUERTO`),
  UNIQUE KEY `CODIGO_IATA` (`CODIGO_IATA`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.aeropuerto: ~4 rows (aproximadamente)
DELETE FROM `aeropuerto`;
/*!40000 ALTER TABLE `aeropuerto` DISABLE KEYS */;
INSERT INTO `aeropuerto` (`CODIGO_AEROPUERTO`, `CODIGO_IATA`, `CIUDAD`, `PAIS`) VALUES
	(1, 'MAD', 'Madrid', 'España'),
	(3, 'ABC', 'Albacete', 'España'),
	(4, 'PMI', 'Mallorca', 'España'),
	(5, 'SVQ', 'Sevilla', 'España');
/*!40000 ALTER TABLE `aeropuerto` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.avion
DROP TABLE IF EXISTS `avion`;
CREATE TABLE IF NOT EXISTS `avion` (
  `COODIGO_AVION` int(11) NOT NULL AUTO_INCREMENT,
  `MODELO` varchar(50) NOT NULL,
  `MATRICULA` varchar(50) NOT NULL,
  `PLAZAS` int(11) NOT NULL DEFAULT '12',
  PRIMARY KEY (`COODIGO_AVION`),
  UNIQUE KEY `MATRICULA` (`MATRICULA`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.avion: ~0 rows (aproximadamente)
DELETE FROM `avion`;
/*!40000 ALTER TABLE `avion` DISABLE KEYS */;
INSERT INTO `avion` (`COODIGO_AVION`, `MODELO`, `MATRICULA`, `PLAZAS`) VALUES
	(1, 'Boeing 737', 'V001', 12);
/*!40000 ALTER TABLE `avion` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.bebe
DROP TABLE IF EXISTS `bebe`;
CREATE TABLE IF NOT EXISTS `bebe` (
  `CODIGO_BEBE` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDOS` varchar(50) NOT NULL,
  `NIF` varchar(50) NOT NULL,
  `FECHA_NAC` date NOT NULL,
  PRIMARY KEY (`CODIGO_BEBE`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.bebe: ~0 rows (aproximadamente)
DELETE FROM `bebe`;
/*!40000 ALTER TABLE `bebe` DISABLE KEYS */;
INSERT INTO `bebe` (`CODIGO_BEBE`, `NOMBRE`, `APELLIDOS`, `NIF`, `FECHA_NAC`) VALUES
	(1, 'Carlos', 'Galdanez Perez', '65548798T', '2016-05-18');
/*!40000 ALTER TABLE `bebe` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.bebe_backup
DROP TABLE IF EXISTS `bebe_backup`;
CREATE TABLE IF NOT EXISTS `bebe_backup` (
  `CODIGO_BEBE` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDOS` varchar(50) NOT NULL,
  `NIF` varchar(50) NOT NULL,
  `FECHA_NAC` date NOT NULL,
  PRIMARY KEY (`CODIGO_BEBE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.bebe_backup: ~0 rows (aproximadamente)
DELETE FROM `bebe_backup`;
/*!40000 ALTER TABLE `bebe_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `bebe_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.conexion
DROP TABLE IF EXISTS `conexion`;
CREATE TABLE IF NOT EXISTS `conexion` (
  `CODIGO_CONEXION` int(11) NOT NULL AUTO_INCREMENT,
  `ORIGEN` varchar(5) NOT NULL,
  `DESTINO` varchar(5) NOT NULL,
  PRIMARY KEY (`CODIGO_CONEXION`),
  KEY `FK_conexion_aeropuerto` (`ORIGEN`),
  KEY `FK_conexion_aeropuerto_2` (`DESTINO`),
  CONSTRAINT `FK_conexion_aeropuerto` FOREIGN KEY (`ORIGEN`) REFERENCES `aeropuerto` (`CODIGO_IATA`),
  CONSTRAINT `FK_conexion_aeropuerto_2` FOREIGN KEY (`DESTINO`) REFERENCES `aeropuerto` (`CODIGO_IATA`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.conexion: ~12 rows (aproximadamente)
DELETE FROM `conexion`;
/*!40000 ALTER TABLE `conexion` DISABLE KEYS */;
INSERT INTO `conexion` (`CODIGO_CONEXION`, `ORIGEN`, `DESTINO`) VALUES
	(19, 'ABC', 'MAD'),
	(20, 'ABC', 'PMI'),
	(21, 'ABC', 'SVQ'),
	(22, 'MAD', 'ABC'),
	(23, 'MAD', 'PMI'),
	(24, 'MAD', 'SVQ'),
	(25, 'PMI', 'ABC'),
	(26, 'PMI', 'MAD'),
	(27, 'PMI', 'SVQ'),
	(29, 'SVQ', 'ABC'),
	(30, 'SVQ', 'MAD'),
	(31, 'SVQ', 'PMI');
/*!40000 ALTER TABLE `conexion` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.ocupacion
DROP TABLE IF EXISTS `ocupacion`;
CREATE TABLE IF NOT EXISTS `ocupacion` (
  `CODIGO_OCUPACION` int(11) NOT NULL AUTO_INCREMENT,
  `RESERVA` varchar(10) NOT NULL,
  `TIPO` varchar(50) NOT NULL,
  `PASAJERO` int(11) NOT NULL,
  `ASIENTO` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO_OCUPACION`),
  KEY `FK_ocupacion_pasajero` (`PASAJERO`),
  KEY `FK_ocupacion_reserva` (`RESERVA`),
  CONSTRAINT `FK_ocupacion_pasajero` FOREIGN KEY (`PASAJERO`) REFERENCES `pasajero` (`CODIGO_PASAJERO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ocupacion_reserva` FOREIGN KEY (`RESERVA`) REFERENCES `reserva` (`COD_RESERVA`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.ocupacion: ~0 rows (aproximadamente)
DELETE FROM `ocupacion`;
/*!40000 ALTER TABLE `ocupacion` DISABLE KEYS */;
INSERT INTO `ocupacion` (`CODIGO_OCUPACION`, `RESERVA`, `TIPO`, `PASAJERO`, `ASIENTO`) VALUES
	(1, 'K5CYYEXWYE', 'IDA', 1, '1'),
	(2, 'K5CYYEXWYE', 'IDA', 2, '2'),
	(3, 'K5CYYEXWYE', 'IDA', 3, '3');
/*!40000 ALTER TABLE `ocupacion` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.ocupacion_backup
DROP TABLE IF EXISTS `ocupacion_backup`;
CREATE TABLE IF NOT EXISTS `ocupacion_backup` (
  `CODIGO_OCUPACION` int(11) NOT NULL AUTO_INCREMENT,
  `RESERVA` varchar(10) NOT NULL,
  `TIPO` varchar(50) NOT NULL,
  `PASAJERO` int(11) NOT NULL,
  `ASIENTO` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO_OCUPACION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.ocupacion_backup: ~0 rows (aproximadamente)
DELETE FROM `ocupacion_backup`;
/*!40000 ALTER TABLE `ocupacion_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocupacion_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.pagador
DROP TABLE IF EXISTS `pagador`;
CREATE TABLE IF NOT EXISTS `pagador` (
  `CODIGO_PAGADOR` int(11) NOT NULL AUTO_INCREMENT,
  `NIF` varchar(9) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `PASS` varchar(9) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDOS` varchar(50) NOT NULL,
  `FECHA_NAC` date NOT NULL,
  `POBLACION` varchar(50) NOT NULL,
  `DIRECCION` varchar(70) NOT NULL,
  PRIMARY KEY (`CODIGO_PAGADOR`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.pagador: ~0 rows (aproximadamente)
DELETE FROM `pagador`;
/*!40000 ALTER TABLE `pagador` DISABLE KEYS */;
INSERT INTO `pagador` (`CODIGO_PAGADOR`, `NIF`, `EMAIL`, `PASS`, `NOMBRE`, `APELLIDOS`, `FECHA_NAC`, `POBLACION`, `DIRECCION`) VALUES
	(3, '48152179T', 'carlos.93.gal@gmail.com', '1234', 'Carlos', 'Galdanez Tarancon', '1993-07-22', 'Villamalea', 'c/ Iniesta nÂº16');
/*!40000 ALTER TABLE `pagador` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.pasajero
DROP TABLE IF EXISTS `pasajero`;
CREATE TABLE IF NOT EXISTS `pasajero` (
  `CODIGO_PASAJERO` int(11) NOT NULL AUTO_INCREMENT,
  `TRATAMIENTO` varchar(2) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDOS` varchar(50) NOT NULL,
  `NIF` varchar(50) NOT NULL,
  `CADUCIDAD_NIF` date DEFAULT NULL,
  `FECHA_NAC` date NOT NULL,
  `TIPO` varchar(50) NOT NULL,
  PRIMARY KEY (`CODIGO_PASAJERO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.pasajero: ~0 rows (aproximadamente)
DELETE FROM `pasajero`;
/*!40000 ALTER TABLE `pasajero` DISABLE KEYS */;
INSERT INTO `pasajero` (`CODIGO_PASAJERO`, `TRATAMIENTO`, `NOMBRE`, `APELLIDOS`, `NIF`, `CADUCIDAD_NIF`, `FECHA_NAC`, `TIPO`) VALUES
	(1, 'Mr', 'Carlos', 'Galdanez Tarancon', '48152179T', '2022-06-17', '1993-07-22', 'adulto'),
	(2, 'Ms', 'Teresa', 'Perez Garcia', '54879865D', '2020-06-17', '1994-03-18', 'adulto'),
	(3, 'Mr', 'Zeus', 'Galdanez CabaÃ±ero', '87542132J', '2021-06-18', '2009-11-21', 'niño');
/*!40000 ALTER TABLE `pasajero` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.pasajero_backup
DROP TABLE IF EXISTS `pasajero_backup`;
CREATE TABLE IF NOT EXISTS `pasajero_backup` (
  `CODIGO_PASAJERO` int(11) NOT NULL AUTO_INCREMENT,
  `TRATAMIENTO` varchar(2) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDOS` varchar(50) DEFAULT NULL,
  `NIF` varchar(50) DEFAULT NULL,
  `CADUCIDAD_NIF` date DEFAULT NULL,
  `FECHA_NAC` date DEFAULT NULL,
  `TIPO` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO_PASAJERO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.pasajero_backup: ~0 rows (aproximadamente)
DELETE FROM `pasajero_backup`;
/*!40000 ALTER TABLE `pasajero_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `pasajero_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.reserva
DROP TABLE IF EXISTS `reserva`;
CREATE TABLE IF NOT EXISTS `reserva` (
  `CODIGO_RESERVA` int(11) NOT NULL AUTO_INCREMENT,
  `COD_VUELO_IDA` int(11) DEFAULT NULL,
  `COD_VUELO_VUELTA` int(11) DEFAULT NULL,
  `COD_RESERVA` varchar(10) NOT NULL,
  `TARJETA` int(11) NOT NULL,
  `NUMERO_VIAJEROS` int(11) NOT NULL,
  `PRECIO_TOTAL` int(11) DEFAULT NULL,
  `FECHA` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `FACTURADA_IDA` varchar(50) NOT NULL DEFAULT 'N',
  `FACTURADA_VUELTA` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO_RESERVA`),
  UNIQUE KEY `COD_RESERVA` (`COD_RESERVA`),
  KEY `FK_reserva_vuelo_ida` (`COD_VUELO_IDA`),
  KEY `FK3_reserva_vuelo_vuelta` (`COD_VUELO_VUELTA`),
  KEY `FK_reserva_tarjeta` (`TARJETA`),
  CONSTRAINT `FK3_reserva_vuelo_vuelta` FOREIGN KEY (`COD_VUELO_VUELTA`) REFERENCES `vuelo` (`CODIGO_VUELO`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_reserva_tarjeta` FOREIGN KEY (`TARJETA`) REFERENCES `tarjeta` (`CODIGO_TARJETA`),
  CONSTRAINT `FK_reserva_vuelo_ida` FOREIGN KEY (`COD_VUELO_IDA`) REFERENCES `vuelo` (`CODIGO_VUELO`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.reserva: ~0 rows (aproximadamente)
DELETE FROM `reserva`;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` (`CODIGO_RESERVA`, `COD_VUELO_IDA`, `COD_VUELO_VUELTA`, `COD_RESERVA`, `TARJETA`, `NUMERO_VIAJEROS`, `PRECIO_TOTAL`, `FECHA`, `FACTURADA_IDA`, `FACTURADA_VUELTA`) VALUES
	(1, 47, NULL, 'K5CYYEXWYE', 1, 3, 199, '2017-05-29 21:10:18', 'S', NULL);
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.reserva_backup
DROP TABLE IF EXISTS `reserva_backup`;
CREATE TABLE IF NOT EXISTS `reserva_backup` (
  `CODIGO_RESERVA` int(11) NOT NULL,
  `COD_VUELO_IDA` int(11) NOT NULL,
  `COD_VUELO_VUELTA` int(11) DEFAULT NULL,
  `COD_RESERVA` varchar(10) NOT NULL,
  `TARJETA` int(11) NOT NULL,
  `NUMERO_VIAJEROS` int(11) NOT NULL,
  `PRECIO_TOTAL` int(11) DEFAULT NULL,
  `FECHA` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `FACTURADA_IDA` varchar(50) DEFAULT NULL,
  `FACTURADA_VUELTA` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO_RESERVA`),
  UNIQUE KEY `COD_RESERVA` (`COD_RESERVA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.reserva_backup: ~0 rows (aproximadamente)
DELETE FROM `reserva_backup`;
/*!40000 ALTER TABLE `reserva_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserva_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.reserva_servicio
DROP TABLE IF EXISTS `reserva_servicio`;
CREATE TABLE IF NOT EXISTS `reserva_servicio` (
  `CODIGO_RESERVA_SERVICIO` int(11) NOT NULL AUTO_INCREMENT,
  `COD_OCUPACION` int(11) NOT NULL,
  `COD_SERVICIO` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_RESERVA_SERVICIO`),
  KEY `FK_reserva_servicio_ocupacion` (`COD_OCUPACION`),
  KEY `FK_reserva_servicio_servicio` (`COD_SERVICIO`),
  CONSTRAINT `FK2_ocupacion` FOREIGN KEY (`COD_OCUPACION`) REFERENCES `ocupacion` (`CODIGO_OCUPACION`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_reserva_servicio_servicio` FOREIGN KEY (`COD_SERVICIO`) REFERENCES `servicio` (`CODIGO_SERVICIO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.reserva_servicio: ~0 rows (aproximadamente)
DELETE FROM `reserva_servicio`;
/*!40000 ALTER TABLE `reserva_servicio` DISABLE KEYS */;
INSERT INTO `reserva_servicio` (`CODIGO_RESERVA_SERVICIO`, `COD_OCUPACION`, `COD_SERVICIO`) VALUES
	(1, 1, 2),
	(2, 1, 5),
	(3, 2, 2),
	(4, 3, 1);
/*!40000 ALTER TABLE `reserva_servicio` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.reserva_servicio_backup
DROP TABLE IF EXISTS `reserva_servicio_backup`;
CREATE TABLE IF NOT EXISTS `reserva_servicio_backup` (
  `CODIGO_RESERVA_SERVICIO` int(11) NOT NULL AUTO_INCREMENT,
  `COD_OCUPACION` int(11) NOT NULL,
  `COD_SERVICIO` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_RESERVA_SERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.reserva_servicio_backup: ~0 rows (aproximadamente)
DELETE FROM `reserva_servicio_backup`;
/*!40000 ALTER TABLE `reserva_servicio_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserva_servicio_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.servicio
DROP TABLE IF EXISTS `servicio`;
CREATE TABLE IF NOT EXISTS `servicio` (
  `CODIGO_SERVICIO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `DESCRIPCION` varchar(200) NOT NULL,
  `PRECIO` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_SERVICIO`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.servicio: ~7 rows (aproximadamente)
DELETE FROM `servicio`;
/*!40000 ALTER TABLE `servicio` DISABLE KEYS */;
INSERT INTO `servicio` (`CODIGO_SERVICIO`, `NOMBRE`, `DESCRIPCION`, `PRECIO`) VALUES
	(1, 'Bolsa de mano pequeña', 'Servicio gratuito que permite llevar contigo una bolsa de mano pequeña.', 0),
	(2, 'Equipaje de mano grande', 'Servicio que permite llevar contigo un equipaje de mano grande 56x45x25, que podras subir al avio y guardar en el compartimento superior.', 12),
	(3, '1 pieza 24 kg ', 'Equipaje facturable con limite en 24 kg.', 20),
	(4, '1 pieza 32 kg', 'Equipaje facturable con limite en 32 kg.', 30),
	(5, 'Bebe', 'Bebe (0-2 años).', 25),
	(6, 'Asiento reservado', 'Reserva anticipada de los asientos de los pasajeros, permitiendo asi sentarse donde se quiere o rodeado de tus compañeros de viaje.', 5),
	(7, 'Seguro de vuelo', 'Seguro que garantiza pequeñas modificaciones en la reserva.', 20);
/*!40000 ALTER TABLE `servicio` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.servicio_backup
DROP TABLE IF EXISTS `servicio_backup`;
CREATE TABLE IF NOT EXISTS `servicio_backup` (
  `CODIGO_SERVICIO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `DESCRIPCION` varchar(200) NOT NULL,
  `PRECIO` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_SERVICIO`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.servicio_backup: ~0 rows (aproximadamente)
DELETE FROM `servicio_backup`;
/*!40000 ALTER TABLE `servicio_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicio_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.tarjeta
DROP TABLE IF EXISTS `tarjeta`;
CREATE TABLE IF NOT EXISTS `tarjeta` (
  `CODIGO_TARJETA` int(11) NOT NULL AUTO_INCREMENT,
  `NUM_TARJETA` varchar(50) NOT NULL,
  `COD_PAGADOR` int(11) NOT NULL,
  `CODSEGURIDAD` varchar(50) NOT NULL,
  `CADUCIDAD` date NOT NULL,
  `PIN` int(11) NOT NULL,
  `NUM_USOS` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_TARJETA`),
  KEY `FK_tarjeta_pagador` (`COD_PAGADOR`),
  CONSTRAINT `FK_tarjeta_pagador` FOREIGN KEY (`COD_PAGADOR`) REFERENCES `pagador` (`CODIGO_PAGADOR`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.tarjeta: ~0 rows (aproximadamente)
DELETE FROM `tarjeta`;
/*!40000 ALTER TABLE `tarjeta` DISABLE KEYS */;
INSERT INTO `tarjeta` (`CODIGO_TARJETA`, `NUM_TARJETA`, `COD_PAGADOR`, `CODSEGURIDAD`, `CADUCIDAD`, `PIN`, `NUM_USOS`) VALUES
	(1, '7898655432215869', 3, '112', '2018-01-19', 0, 1);
/*!40000 ALTER TABLE `tarjeta` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.tutor
DROP TABLE IF EXISTS `tutor`;
CREATE TABLE IF NOT EXISTS `tutor` (
  `CODIGO_TUTOR` int(11) NOT NULL AUTO_INCREMENT,
  `COD_PASAJERO` int(11) NOT NULL,
  `COD_BEBE` int(11) NOT NULL,
  `COD_RESERVA` varchar(50) NOT NULL,
  `TIPO` varchar(50) NOT NULL,
  PRIMARY KEY (`CODIGO_TUTOR`),
  KEY `FK1_pasajero` (`COD_PASAJERO`),
  KEY `FK2_bebe` (`COD_BEBE`),
  KEY `FK3_reserva` (`COD_RESERVA`),
  CONSTRAINT `FK1_pasajero` FOREIGN KEY (`COD_PASAJERO`) REFERENCES `pasajero` (`CODIGO_PASAJERO`),
  CONSTRAINT `FK2_bebe` FOREIGN KEY (`COD_BEBE`) REFERENCES `bebe` (`CODIGO_BEBE`),
  CONSTRAINT `FK_tutor_reserva` FOREIGN KEY (`COD_RESERVA`) REFERENCES `reserva` (`COD_RESERVA`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.tutor: ~0 rows (aproximadamente)
DELETE FROM `tutor`;
/*!40000 ALTER TABLE `tutor` DISABLE KEYS */;
INSERT INTO `tutor` (`CODIGO_TUTOR`, `COD_PASAJERO`, `COD_BEBE`, `COD_RESERVA`, `TIPO`) VALUES
	(1, 1, 1, 'K5CYYEXWYE', 'IDA');
/*!40000 ALTER TABLE `tutor` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.tutor_backup
DROP TABLE IF EXISTS `tutor_backup`;
CREATE TABLE IF NOT EXISTS `tutor_backup` (
  `CODIGO_TUTOR` int(11) NOT NULL AUTO_INCREMENT,
  `COD_PASAJERO` int(11) NOT NULL,
  `COD_BEBE` int(11) NOT NULL,
  `COD_RESERVA` varchar(50) NOT NULL,
  `TIPO` varchar(50) NOT NULL,
  PRIMARY KEY (`CODIGO_TUTOR`),
  KEY `FK_tutor_backup_pasajero` (`COD_PASAJERO`),
  KEY `FK_tutor_backup_bebe` (`COD_BEBE`),
  CONSTRAINT `FK_tutor_backup_bebe` FOREIGN KEY (`COD_BEBE`) REFERENCES `bebe` (`CODIGO_BEBE`),
  CONSTRAINT `FK_tutor_backup_pasajero` FOREIGN KEY (`COD_PASAJERO`) REFERENCES `pasajero` (`CODIGO_PASAJERO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.tutor_backup: ~0 rows (aproximadamente)
DELETE FROM `tutor_backup`;
/*!40000 ALTER TABLE `tutor_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutor_backup` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.vuelo
DROP TABLE IF EXISTS `vuelo`;
CREATE TABLE IF NOT EXISTS `vuelo` (
  `CODIGO_VUELO` int(11) NOT NULL AUTO_INCREMENT,
  `CONEXION` int(11) NOT NULL,
  `FECHA` date NOT NULL,
  `HORA_SALIDA` time NOT NULL,
  `HORA_LLEGADA` time NOT NULL,
  `DURACION` time NOT NULL,
  `ASIENTOS_LIBRES` int(11) NOT NULL DEFAULT '12',
  `NUMERO` varchar(50) NOT NULL,
  `AVION` varchar(50) NOT NULL,
  `PRECIO` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_VUELO`),
  KEY `FK_vuelo_conexion` (`CONEXION`),
  KEY `FK2_avion` (`AVION`),
  CONSTRAINT `FK2_avion` FOREIGN KEY (`AVION`) REFERENCES `avion` (`MATRICULA`),
  CONSTRAINT `FK_vuelo_conexion` FOREIGN KEY (`CONEXION`) REFERENCES `conexion` (`CODIGO_CONEXION`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla bd_proyecto_aviones15.vuelo: ~2 rows (aproximadamente)
DELETE FROM `vuelo`;
/*!40000 ALTER TABLE `vuelo` DISABLE KEYS */;
INSERT INTO `vuelo` (`CODIGO_VUELO`, `CONEXION`, `FECHA`, `HORA_SALIDA`, `HORA_LLEGADA`, `DURACION`, `ASIENTOS_LIBRES`, `NUMERO`, `AVION`, `PRECIO`) VALUES
	(47, 19, '2017-07-20', '20:07:45', '21:07:54', '01:00:00', 9, 'AB0001', 'V001', 50),
	(48, 22, '2017-07-29', '20:12:41', '21:12:43', '01:00:00', 12, 'AB0002', 'V001', 60);
/*!40000 ALTER TABLE `vuelo` ENABLE KEYS */;

-- Volcando estructura para tabla bd_proyecto_aviones15.vuelo_backup
DROP TABLE IF EXISTS `vuelo_backup`;
CREATE TABLE IF NOT EXISTS `vuelo_backup` (
  `CODIGO_VUELO` int(11) NOT NULL,
  `CONEXION` int(11) NOT NULL,
  `FECHA` date NOT NULL,
  `HORA_SALIDA` time NOT NULL,
  `HORA_LLEGADA` time NOT NULL,
  `DURACION` time NOT NULL,
  `ASIENTOS_LIBRES` int(11) NOT NULL DEFAULT '9',
  `NUMERO` varchar(50) NOT NULL,
  `AVION` varchar(50) NOT NULL,
  `PRECIO` int(11) NOT NULL,
  PRIMARY KEY (`CODIGO_VUELO`),
  KEY `FK_vuelo_conexion` (`CONEXION`),
  KEY `FK2_avion` (`AVION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Volcando datos para la tabla bd_proyecto_aviones15.vuelo_backup: ~0 rows (aproximadamente)
DELETE FROM `vuelo_backup`;
/*!40000 ALTER TABLE `vuelo_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `vuelo_backup` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
