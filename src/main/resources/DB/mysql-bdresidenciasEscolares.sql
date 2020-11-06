-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-11-2019 a las 19:55:34
-- Versión del servidor: 10.4.6-MariaDB
-- Versión de PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdresidenciasescolares`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_insertResidenciaEscolar` (IN `paramNomResidencia` VARCHAR(30), `paramCodUniversidad` CHAR(6), `paramPrecioMensual` INT, `paramComedor` BOOLEAN, OUT `codErrorUniver` INT, OUT `codErrorReside` INT)  BEGIN
	SET codErrorUniver = 0;
	SET codErrorReside = 0;
	IF((SELECT COUNT(codUniversidad) FROM universidades where universidades.codUniversidad = paramCodUniversidad)= 1) THEN
			INSERT INTO residencias(nomResidencia,codUniversidad,precioMensual,comedor)values(paramNomResidencia, paramCodUniversidad, paramPrecioMensual,
                                                                                              paramComedor);
			SET codErrorUniver = 1;
			SET codErrorReside = 1;
		END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_nombreUniversidadPrec` (IN `paramNomUniversidad` VARCHAR(30), `paramPrecioMensual` INT, OUT `cantidadResidencias` INT, OUT `cantidadResidenciasPrecio` INT)  BEGIN
	SELECT count(codResidencia)INTO cantidadResidencias FROM universidades 
	INNER JOIN residencias ON residencias.codUniversidad = universidades.codUniversidad
	where universidades.nomUniversidad = paramNomUniversidad;

	SELECT count(codResidencia)INTO cantidadResidenciasPrecio FROM universidades 
	INNER JOIN residencias ON residencias.codUniversidad = universidades.codUniversidad
	where residencias.precioMensual > paramPrecioMensual and universidades.nomUniversidad = paramNomUniversidad;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_procedimiento1` (IN `dni` CHAR(9))  BEGIN 
	SELECT nomResidencia,nomUniversidad,fechaInicio,fechaFin,precioMensual FROM estancias
	INNER JOIN residencias on residencias.codResidencia = estancias.codResidencia
	INNER JOIN universidades on universidades.codUniversidad = residencias.codUniversidad
    INNER JOIN estudiantes on estudiantes.codEstudiante = estancias.codEstudiante
    WHERE estudiantes.dni = dni
	ORDER BY fechaInicio ASC;
END$$

--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `FN_MesesEstancia` (`paramDni` CHAR(9)) RETURNS INT(11) RETURN (SELECT sum(TIMESTAMPDIFF(MONTH, estancias.fechaInicio, estancias.fechaFin))as NumeroDeMesesTotales 
    FROM estancias
    INNER JOIN estudiantes ON estudiantes.codEstudiante = estancias.codEstudiante
    INNER JOIN residencias ON residencias.codResidencia = estancias.codResidencia
    WHERE estudiantes.dni = paramDni
    GROUP BY estancias.codEstudiante)$$

CREATE DEFINER=`root`@`localhost` FUNCTION `FN_MesesEstanciaPagados` (`paramDni` CHAR(9)) RETURNS INT(11) RETURN (SELECT (sum(TIMESTAMPDIFF(MONTH, estancias.fechaInicio, estancias.fechaFin)) * residencias.precioMensual)as PrecioTotalPagado FROM estancias
INNER JOIN estudiantes ON estudiantes.codEstudiante = estancias.codEstudiante
INNER JOIN residencias ON residencias.codResidencia = estancias.codResidencia
WHERE estudiantes.dni = paramDni
GROUP BY estancias.codEstudiante)$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estancias`
--

CREATE TABLE `estancias` (
  `codEstudiante` int(11) NOT NULL,
  `codResidencia` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date DEFAULT NULL,
  `preciopagado` int(11) DEFAULT NULL
) ;

--
-- Volcado de datos para la tabla `estancias`
--

INSERT INTO `estancias` (`codEstudiante`, `codResidencia`, `fechaInicio`, `fechaFin`, `preciopagado`) VALUES
(1, 1, '2017-02-14', '2017-05-14', 1000),
(1, 1, '2018-09-10', '2018-10-10', 1000),
(2, 2, '2017-09-20', '2017-12-20', 900),
(2, 2, '2019-07-10', '2019-10-10', 900),
(3, 3, '2017-01-02', '2017-07-02', 900),
(3, 3, '2018-01-01', '2018-11-01', 900),
(4, 4, '2018-01-30', '2018-04-30', 1500),
(5, 5, '2016-09-10', '2016-10-10', 1200),
(5, 5, '2019-02-21', '2019-06-21', 1200),
(6, 6, '2019-01-15', '2019-06-15', 2000);

--
-- Disparadores `estancias`
--
DELIMITER $$
CREATE TRIGGER `TR_ModificarFechasInsert` BEFORE INSERT ON `estancias` FOR EACH ROW BEGIN
	DECLARE fechaNueva DATE;
	IF(NEW.fechaInicio > NEW.fechaFin) THEN
	SET fechaNueva = NEW.fechaInicio;
	SET NEW.fechaInicio = NEW.fechaFin;
	SET NEW.fechaFin = fechaNueva;
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `TR_ModificarFechasUpdate` BEFORE UPDATE ON `estancias` FOR EACH ROW BEGIN
	DECLARE fechaNueva DATE;
	IF(NEW.fechaInicio > NEW.fechaFin) THEN
	SET fechaNueva = NEW.fechaInicio;
	SET NEW.fechaInicio = NEW.fechaFin;
	SET NEW.fechaFin = fechaNueva;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiantes`
--

CREATE TABLE `estudiantes` (
  `codEstudiante` int(11) NOT NULL,
  `nomEstudiante` varchar(50) DEFAULT NULL,
  `dni` char(9) DEFAULT NULL,
  `telefonoEstudiante` char(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `estudiantes`
--

INSERT INTO `estudiantes` (`codEstudiante`, `nomEstudiante`, `dni`, `telefonoEstudiante`) VALUES
(1, 'Pablo Urtiaga', '12345678A', '888333222'),
(2, 'Antonio Rodriguez', '22345678B', '899339222'),
(3, 'Pepe Lopez', '12315623C', '882223222'),
(4, 'Ricardo Luis', '12378078R', '844333222'),
(5, 'Ana Perez', '12302378T', '888333666'),
(6, 'Raul Gonzalez', '19634678P', '111333999');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `residencias`
--

CREATE TABLE `residencias` (
  `codResidencia` int(11) NOT NULL,
  `nomResidencia` varchar(30) DEFAULT NULL,
  `codUniversidad` char(6) DEFAULT NULL,
  `precioMensual` int(11) DEFAULT 900,
  `comedor` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `residencias`
--

INSERT INTO `residencias` (`codResidencia`, `nomResidencia`, `codUniversidad`, `precioMensual`, `comedor`) VALUES
(1, 'Residencia 1', 'UNHARV', 1000, 0),
(2, 'Residencia 2', 'UNOXFO', 900, 1),
(3, 'Residencia 3', 'UNCAMB', 900, 0),
(4, 'Residencia 4', 'UNSTAN', 1500, 1),
(5, 'Residencia 5', 'UNCALI', 1200, 0),
(6, 'Residencia 6', 'UNLAGU', 2000, 1),
(7, 'Residencia Antonio', 'UNLAGU', 2000, 1),
(16, 'Residencias Lucas', 'UNLAGU', 7539, 0),
(18, 'Residencia', 'UNLAGU', 1000, 1),
(27, 'Residencia 55', 'UNOXFO', 1963, 1),
(28, 'Residencia', 'UNLAGU', 1000, 1);

--
-- Disparadores `residencias`
--
DELIMITER $$
CREATE TRIGGER `TR_PrecioMensualInsert` BEFORE INSERT ON `residencias` FOR EACH ROW BEGIN
IF(NEW.precioMensual < 900) THEN
signal sqlstate '45000' set message_text='No se puede introducir un precio mensual inferior a 900'; 
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `TR_PrecioMensualUpdate` BEFORE UPDATE ON `residencias` FOR EACH ROW BEGIN
IF(NEW.precioMensual < 900) THEN
signal sqlstate '45000' set message_text='No se puede introducir un precio mensual inferior a 900'; 
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `universidades`
--

CREATE TABLE `universidades` (
  `codUniversidad` char(6) NOT NULL,
  `nomUniversidad` varchar(30) DEFAULT 'La Laguna'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `universidades`
--

INSERT INTO `universidades` (`codUniversidad`, `nomUniversidad`) VALUES
('UNCALI', 'Universidad de California'),
('UNCAMB', 'Universidad de Cambridge'),
('UNHARV', 'Universidad de Harvard'),
('UNLAGU', 'La Laguna'),
('UNOXFO', 'Universidad de Oxford'),
('UNSTAN', 'Universidad de Stanford');

--
-- Disparadores `universidades`
--
DELIMITER $$
CREATE TRIGGER `TR_NoEliminarUniversidad` BEFORE DELETE ON `universidades` FOR EACH ROW BEGIN
signal sqlstate '45000' set message_text='No se puede borrar ninguna universidad'; 
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD PRIMARY KEY (`codEstudiante`,`codResidencia`,`fechaInicio`),
  ADD KEY `FK_codResidenciaEstancias` (`codResidencia`);

--
-- Indices de la tabla `estudiantes`
--
ALTER TABLE `estudiantes`
  ADD PRIMARY KEY (`codEstudiante`),
  ADD UNIQUE KEY `U_dni` (`dni`),
  ADD UNIQUE KEY `U_telefonoEstudiante` (`telefonoEstudiante`);

--
-- Indices de la tabla `residencias`
--
ALTER TABLE `residencias`
  ADD PRIMARY KEY (`codResidencia`),
  ADD KEY `FK_codUniversidad` (`codUniversidad`);

--
-- Indices de la tabla `universidades`
--
ALTER TABLE `universidades`
  ADD PRIMARY KEY (`codUniversidad`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `residencias`
--
ALTER TABLE `residencias`
  MODIFY `codResidencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD CONSTRAINT `FK_codEstudiante` FOREIGN KEY (`codEstudiante`) REFERENCES `estudiantes` (`codEstudiante`),
  ADD CONSTRAINT `FK_codResidenciaEstancias` FOREIGN KEY (`codResidencia`) REFERENCES `residencias` (`codResidencia`);

--
-- Filtros para la tabla `residencias`
--
ALTER TABLE `residencias`
  ADD CONSTRAINT `FK_codUniversidad` FOREIGN KEY (`codUniversidad`) REFERENCES `universidades` (`codUniversidad`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
