/*Antonio Rodriguez Luis*/
/*DROP DATABASE bdResidenciasEscolares;*/
CREATE DATABASE bdResidenciasEscolares;
go
USE bdResidenciasEscolares;
--drop table universidades
CREATE TABLE universidades (codUniversidad char(6) NOT NULL, 
nomUniversidad varchar(30));

ALTER TABLE universidades
ADD
CONSTRAINT pk_universidad PRIMARY KEY (codUniversidad);

ALTER TABLE universidades
ADD
constraint df_nombreUniversidad DEFAULT 'La Laguna' FOR nomUniversidad;


CREATE TABLE residencias(codResidencia int identity(1,1), CONSTRAINT PK_codResidencia PRIMARY KEY (codResidencia),
nomResidencia varchar(30),codUniversidad char(6), precioMensual int DEFAULT 900, comedor bit DEFAULT 0);

ALTER TABLE residencias
ADD
CONSTRAINT FK_codUniversidad FOREIGN KEY (codUniversidad) REFERENCES universidades(codUniversidad);

CREATE TABLE estudiantes(codEstudiante int , CONSTRAINT PK_codEstudiante PRIMARY KEY(codEstudiante),nomEstudiante varchar(50),dni char(9),
 CONSTRAINT U_dni UNIQUE(dni),telefonoEstudiante char(9), CONSTRAINT U_telefonoEstudiante UNIQUE (telefonoEstudiante));

CREATE TABLE estancias(codEstudiante int,codResidencia int,fechaInicio date, fechaFin date, preciopagado int,
CONSTRAINT PK_codEstudianteEstancias PRIMARY KEY(codEstudiante,codResidencia,fechaInicio),
CONSTRAINT FK_codEstudiante FOREIGN KEY(codEstudiante) REFERENCES estudiantes(codEstudiante),
CONSTRAINT FK_codResidenciaEstancias FOREIGN KEY(codResidencia) REFERENCES residencias(codResidencia),
CONSTRAINT CH_preciopagado CHECK(preciopagado >0));

/*Insertar datos en las tablas*/

INSERT INTO universidades VALUES('UNHARV','Universidad de Harvard'),
('UNOXFO','Universidad de Oxford'),
('UNCAMB','Universidad de Cambridge'),
('UNSTAN','Universidad de Stanford'),
('UNCALI','Universidad de California'),
('UNLAGU',DEFAULT);

INSERT INTO residencias(nomResidencia,codUniversidad,precioMensual,comedor) 
VALUES('Residencia 1','UNHARV',1000,0),
('Residencia 2','UNOXFO',DEFAULT,1),
('Residencia 3','UNCAMB',DEFAULT,0),
('Residencia 4','UNSTAN',1500,1),
('Residencia 5','UNCALI',1200,0),
('Residencia 6','UNLAGU',2000,1);

INSERT INTO estudiantes values(1,'Pablo Urtiaga','12345678A', '888333222'),
(2,'Antonio Rodriguez','22345678B', '899339222'),
(3,'Pepe Lopez','12315623C', '882223222'),
(4,'Ricardo Luis','12378078R', '844333222'),
(5,'Ana Perez','12302378T', '888333666'),
(6,'Raul Gonzalez','19634678P', '111333999');

INSERT INTO estancias VALUES
(1,1,'2018-09-10','2018-10-10',1000),
(2,2,'2017-09-20','2017-12-20',900),
(3,3,'2017-01-02','2017-07-02',900),
(4,4,'2018-01-30','2018-04-30',1500),
(5,5,'2016-09-10','2016-10-10',1200),
(6,6,'2019-01-15','2019-06-15',2000),
(2,2,'2019-07-10','2019-10-10',900),
(1,1,'2017-02-14','2017-05-14',1000),
(3,3,'2018-01-01','2018-11-01',900),
(5,5,'2019-02-21','2019-06-21',1200);

Select * from universidades;
Select * from residencias;
Select * from estudiantes;
Select * from estancias;

/*Crear un procedimiento almacenado que liste todos las estancias de cierto alumno pasando por
parámetro de entrada el dni del alumno ordenado por fecha de inicio.
Los datos a visualizar serán: Nombre de residencia, nombre de universidad, fecha de
inicio,fecha de fin y precio de la estancia.*/
Go
CREATE PROCEDURE SP_procedimiento1
@dni char(9)
as
BEGIN 
	SELECT nomResidencia,nomUniversidad,fechaInicio,fechaFin,precioMensual FROM estancias
	INNER JOIN residencias on residencias.codResidencia = estancias.codResidencia
	INNER JOIN universidades on universidades.codUniversidad = residencias.codUniversidad
    INNER JOIN estudiantes on estudiantes.codEstudiante = estancias.codEstudiante
    WHERE estudiantes.dni like @dni
	ORDER BY fechaInicio ASC;
END
GO
Declare @dni char(9);
EXEC SP_procedimiento1 '22345678B';
select @dni;

/*Crear un procedimiento almacenado que inserte una residenciaEscolar, de modo que se le pase como
parámetros todos los datos.
Comprobar que el codigo de universidad pasado exista en la tabla universidades. En caso de
que no exista la universidad que no inserte .
Devolver en un parámetro de salida: 0 si la universidad no existe y 1 si la universidad existe.
Devolver en otro parámetro de salida 0 si la residencia no se insertó y 1 si la inserción fue
correcta.*/
GO
CREATE PROCEDURE SP_insertResidenciaEscolar 
@paramNomResidencia varchar(30),@paramCodUniversidad char(6), @paramPrecioMensual int , @paramComedor bit,
@codErrorUniver int output, @codErrorReside int output
as
BEGIN
	SET @codErrorUniver = 0;
	SET @codErrorReside = 0;
	IF((SELECT COUNT(codUniversidad) FROM universidades where universidades.codUniversidad = @paramCodUniversidad)= 1)
	Begin
			INSERT INTO residencias(nomResidencia,codUniversidad,precioMensual,comedor)values(@paramNomResidencia, @paramCodUniversidad, @paramPrecioMensual, @paramComedor);
			SET @codErrorUniver = 1;
			SET @codErrorReside = 1;
	
	End;

END
GO
/* Una residencia nueva con una universidad existente*/
Declare @paramNomResidencia varchar(30),@paramCodUniversidad char(6), @paramPrecioMensual int , @paramComedor bit,
@codErrorUniver int, @codErrorReside int;
EXEC SP_insertResidenciaEscolar 'Residencia 7','UNLAGU',2000,1,@codErrorUniver output,@codErrorReside output;
Select @codErrorUniver as 'Codigo Error universidad', @codErrorReside as 'Codigo Error residencia'

/* Una residencia nueva con una universidad no existente*//*
Declare @paramNomResidencia varchar(30),@paramCodUniversidad char(6), @paramPrecioMensual int , @paramComedor bit,
@codErrorUniver int, @codErrorReside int;
EXEC SP_insertResidenciaEscolar 'Residencia 8','PEPEPD',2000,0,@codErrorUniver output,@codErrorReside output;
Select @codErrorUniver as 'Codigo Error universidad', @codErrorReside as 'Codigo Error residencia';*/

/*Crear un procedimiento almacenado que indicándole una universidad (nombre) y precioMensual ,
devuelva en un parámetro de salida la cantidad de residencias que hay en esa universidad y en otro
parámetro de salida la cantidad de residencias de esa universidad pero con un precioMensual inferior
al indicado.*/
GO
ALTER PROCEDURE SP_nombreUniversidadPrec 
@paramNomUniversidad varchar(30), @paramPrecioMensual int, @cantidadResidencias int output, @cantidadResidenciasPrecio int output
as
BEGIN
	SELECT @cantidadResidencias = count(codResidencia) FROM universidades 
	INNER JOIN residencias ON residencias.codUniversidad = universidades.codUniversidad
	where universidades.nomUniversidad = @paramNomUniversidad;

	SELECT @cantidadResidenciasPrecio = count(codResidencia) FROM universidades 
	INNER JOIN residencias ON residencias.codUniversidad = universidades.codUniversidad
	where residencias.precioMensual > @paramPrecioMensual and universidades.nomUniversidad = @paramNomUniversidad;
END
GO
/*Muestra el numero de residencias que tiene esa universidad con un precio mensual inferior*/
/*CALL SP_nombreUniversidadPrec('La Laguna',900,@codErrorUniver,@codErrorReside);
SELECT @codErrorUniver, @codErrorReside;*/
DECLARE  @paramNomUniversidad varchar(30), @paramPrecioMensual int, @cantidadResidencias int, @cantidadResidenciasPrecio int;
EXEC SP_nombreUniversidadPrec 'La Laguna',900,@cantidadResidencias OUTPUT,@cantidadResidenciasPrecio OUTPUT;
SELECT @cantidadResidencias as cantidadResidencias, @cantidadResidenciasPrecio as cantidadResidenciasPrecio;

/*Muestra el numero de residencias que tiene esa universidad  con un precio mensual igual*/
/*CALL SP_nombreUniversidadPrec('La Laguna',2500,@codErrorUniver,@codErrorReside);
SELECT @codErrorUniver, @codErrorReside;*/
/*DECLARE  @paramNomUniversidad varchar(30), @paramPrecioMensual int, @cantidadResidencias int, @cantidadResidenciasPrecio int;
EXEC SP_nombreUniversidadPrec 'La Laguna',2500,@cantidadResidencias output, @cantidadResidenciasPrecio output;
SELECT @cantidadResidencias as cantidadResidencias, @cantidadResidenciasPrecio as cantidadResidenciasPrecio;*/

/*Crear una función que dándole un dni de un alumno nos devuelva en número de meses 
el tiempo que ha estado en residencias escolares en total
 ( contando todos las residencias en las que ha estado )*/
GO
CREATE FUNCTION FN_MesesEstancia (@paramDni char(9)) RETURNS INT
BEGIN   
	RETURN (SELECT sum(DATEDIFF(MONTH, estancias.fechaInicio, estancias.fechaFin))as NumeroDeMesesTotales 
			FROM estancias
			INNER JOIN estudiantes ON estudiantes.codEstudiante = estancias.codEstudiante
			INNER JOIN residencias ON residencias.codResidencia = estancias.codResidencia
			WHERE estudiantes.dni = @paramDni
			GROUP BY estancias.codEstudiante);
END
GO
Select DBO.FN_MesesEstancia('12315623C') AS NumeroDeMesesTotales;

/*HACEMOS OTRA FUNCION QUE NOS DEVUELVA EL PRECIO TOTAL PAGADO DURANTE TODA SU ESTANCIAS EN CUALQUIER RESIDENCIA*/

GO
CREATE FUNCTION FN_MesesEstanciaPagados2 (@paramDni char(9)) RETURNS INT
BEGIN
	RETURN (SELECT (sum(DATEDIFF(MONTH, estancias.fechaInicio, estancias.fechaFin)) * residencias.precioMensual)as PrecioTotalPagado FROM estancias
	INNER JOIN estudiantes ON estudiantes.codEstudiante = estancias.codEstudiante
	INNER JOIN residencias ON residencias.codResidencia = estancias.codResidencia
	WHERE estudiantes.dni = @paramDni
	GROUP BY estancias.codEstudiante,residencias.precioMensual);
END
GO
Select DBO.FN_MesesEstanciaPagados2('12315623C')AS PrecioTotalPagado;
