-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `dni` bigint(20) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `numero` bigint(20) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (1,'admin','admin123',123456789,'correo@email.com',987654321,'Mi Casa'),(2,'Usuario','contraseña123',78432151,'correo@micorreo.com',987653252,'direcciondemicasa'),(3,'cuentaejemplo1','cuentaejemplo1',12345678,'correo@ejemplo.com',987651234,'avenidamicasa'),(4,'UsuarioNuevo','contraseña123',123432121,'uncorreo@random.com',912345678,'enmicasa');
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) DEFAULT NULL,
  `autor` varchar(255) DEFAULT NULL,
  `genero` varchar(100) DEFAULT NULL,
  `anio_publicacion` int(11) NOT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1,'El eco de las sombras','Lucía Pérez','Fantasía',2015,'978-3-16-148410-0',1),(2,'Crónicas del viento','Mario Sánchez','Aventura',2018,'978-1-86197-876-9',1),(3,'La fórmula del caos','Andrea Ríos','Ciencia Ficción',2022,'978-0-14-044913-6',0),(4,'Jardines de ceniza','Carlos Méndez','Drama',2011,'978-0-670-82162-4',1),(5,'El susurro del mar','Isabel Torres','Romance',2019,'978-1-4028-9462-6',0),(6,'Rastro de tinta','Julio Navarro','Misterio',2014,'978-0-7432-7356-5',0),(7,'Niebla eterna','Sofía Márquez','Terror',2020,'978-0-345-39180-3',1),(8,'Luz de medianoche','Hugo Lira','Suspenso',2017,'978-0-553-57340-1',1),(9,'Viaje al último sol','Ana Beltrán','Ficción Histórica',2016,'978-0-06-112008-4',0),(10,'Universos paralelos','Daniel Herrera','Fantasía',2023,'978-1-250-30045-1',0),(11,'Las Crónicas de Narnia','Juanito Federico Gonzales Constantinopla','Terror',2014,'978-1-123-1232-1',1),(12,'Paco Yunque','Paquito','Ciencia Ficción',1900,'978-14-201-4514-1',0);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prestamos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `libro_id` int(11) NOT NULL,
  `fecha_prestamo` date NOT NULL,
  `fecha_devolucion` date NOT NULL,
  `devuelto` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `libro_id` (`libro_id`),
  CONSTRAINT `fk_prestamos_usuario` FOREIGN KEY (`user_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `prestamos_ibfk_2` FOREIGN KEY (`libro_id`) REFERENCES `libros` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (1,1,1,'2025-07-25','2025-08-04',0),(2,3,2,'2025-07-25','2025-08-09',0),(3,5,5,'2025-07-25','2025-07-30',0),(4,8,8,'2025-07-25','2025-08-04',0),(5,10,10,'2025-07-25','2025-08-09',1),(6,4,4,'2025-07-25','2025-08-09',1),(7,14,5,'2025-07-25','2025-08-04',0),(8,20,10,'2025-07-25','2025-08-04',0);
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `dni` bigint(20) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `numero` bigint(20) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Ana Pérez',12345678901,'ana.perez@example.com',5551234567,'Calle 1 #123'),(2,'Juan Gómez',23456789012,'juan.gomez@example.com',5552345678,'Avenida 2 #456'),(3,'María López',34567890123,'maria.lopez@example.com',5553456789,'Boulevard 3 #789'),(4,'Carlos Martínez',45678901234,'carlos.martinez@example.com',5554567890,'Camino 4 #101'),(5,'Laura Fernández',56789012345,'laura.fernandez@example.com',5555678901,'Plaza 5 #202'),(6,'Miguel Torres',67890123456,'miguel.torres@example.com',5556789012,'Calle 6 #303'),(7,'Sofía Ramírez',78901234567,'sofia.ramirez@example.com',5557890123,'Avenida 7 #404'),(8,'Diego Sánchez',89012345678,'diego.sanchez@example.com',5558901234,'Camino 8 #505'),(9,'Valeria Jiménez',90123456789,'valeria.jimenez@example.com',5559012345,'Plaza 9 #606'),(10,'Jorge Castillo',11234567890,'jorge.castillo@example.com',5551123456,'Calle 10 #707'),(11,'Natalia Herrera',22345678901,'natalia.herrera@example.com',5552234567,'Avenida 11 #808'),(12,'Luis Morales',33456789012,'luis.morales@example.com',5553345678,'Boulevard 12 #909'),(13,'Camila Díaz',44567890123,'camila.diaz@example.com',5554456789,'Camino 13 #1010'),(14,'Andrés Vargas',55678901234,'andres.vargas@example.com',5555567890,'Plaza 14 #1111'),(15,'Gabriela Ruiz',66789012345,'gabriela.ruiz@example.com',5556678901,'Calle 15 #1212'),(16,'Fernando Cruz',77890123456,'fernando.cruz@example.com',5557789012,'Avenida 16 #1313'),(17,'Isabel Molina',88901234567,'isabel.molina@example.com',5558890123,'Boulevard 17 #1414'),(18,'Ricardo Ortiz',99012345678,'ricardo.ortiz@example.com',5559901234,'Camino 18 #1515'),(19,'Paula Castro',10123456789,'paula.castro@example.com',5551012345,'Plaza 19 #1616'),(20,'Esteban Romero',21234567890,'esteban.romero@example.com',5552123456,'Calle 20 #1717'),(21,'Lector Nuevo',12344321,'sucorreo@lector.com',91243211,'sucasa #000');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-25 11:13:44
