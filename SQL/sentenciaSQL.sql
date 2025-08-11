-- En esta parte se agregaran las sentencias de comandos para la creacion de la Base de Datos de AssitU en MariaDB
-- Esto significa en la creacion de las tablas que se necesitan para el funcionamiento del software
-- Encargad@ de la creacion de la Base de Datos: Samaria & Kevin
-- Nombre de la Base de Datos: assistu_db.db

-- Sentencia para crear la tabla de [Usuarios]
CREATE TABLE Usuarios (id_usuario INT PRIMARY KEY NOT NULL, matricula VARCHAR(20), contrasenia VARCHAR(20) NOT NULL, nombre VARCHAR(20) NOT NULL, apellido_paterno VARCHAR(20) NOT NULL, apellido_materno VARCHAR(20) NOT NULL, correo VARCHAR(50) NOT NULL, tipo_usuario VARCHAR(20));

-- Sentencia para crear la tabla de [Alumnos]
CREATE TABLE Alumnos (id_alumno INT PRIMARY KEY NOT NULL, cuatrimestre INT NOT NULL, grupo CHAR(10) NOT NULL, carrera VARCHAR(50) NOT NULL, id_usuario INT, FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario));

-- Sentencia para crear la tabla de [Docentes]
CREATE TABLE Docentes (id_docente INT PRIMARY KEY NOT NULL, cargo VARCHAR(50) NOT NULL, horario VARCHAR(20) NOT NULL, id_usuario INT, FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario));

-- Sentencia para crear la tabla de [Administradores]
CREATE TABLE Administradores (id_administrador INT PRIMARY KEY NOT NULL, cargo VARCHAR(50) NOT NULL, id_usuario INT, FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario));

-- Sentencia para crear la tabla de [Recursamientos]
CREATE TABLE Recursamientos (id_recursamiento INT PRIMARY KEY NOT NULL, materia VARCHAR(50) NOT NULL, grupo CHAR(10) NOT NULL, horario VARCHAR(20) NOT NULL, id_administrador INT, id_docente INT, FOREIGN KEY(id_administrador) REFERENCES Administradores(id_administrador), FOREIGN KEY(id_docente) REFERENCES Docentes(id_docente));

-- Sentencia para crear la tabla de [Inscripciones]
CREATE TABLE Inscripciones (id_alumno INT, id_recursamiento INT, fecha DATE NOT NULL, calificacion FLOAT NOT NULL, estado VARCHAR(20) NOT NULL, PRIMARY KEY(id_alumno, id_recursamiento), FOREIGN KEY(id_alumno) REFERENCES Alumnos(id_alumno), FOREIGN KEY(id_recursamiento) REFERENCES Recursamientos(id_recursamiento));

-- Insertar datos en Usuarios
INSERT INTO Usuarios (id_usuario, matricula, contrasenia, nombre, apellido_paterno, apellido_materno, correo, tipo_usuario) VALUES
(1,'CMSO240614', 'SAM123', 'Samaria', 'Cervantes', 'Martinez', 'cmso240614@upemor.edu.mx', 'alumna'),
(2,'MJJ0240614', 'Jose4567', 'Jose', 'Martinez', 'Jacobo', 'mjj0240614@upemor.edu.mx', 'alumno'),
(3,'DVKO240202', 'Kevin5677', 'Kevin', 'Diaz', 'Vega', 'dvko240202@upemor.edu.mx', 'alumno'),
(4,'JOSOUBUHAY', 'MINA78', 'Samuel', 'Arroyo', 'Soto', 'josoubuhay@upemor.edu.mx', 'docente'),
(5,'JSKAKSNYYY', 'RANIt@', 'Sebastian', 'Soto', 'Mendez', 'jskaksnyyy@upemor.edu.mx', 'docente'),
(6,'HDMIAL0970', 'Aldo90', 'Axel', 'Ortiz', 'Dominguez', 'hdmial0970@upemor.edu.mx', 'administrador'),
(7,'VRT749O20', 'axel567', 'Sofia', 'Perez', 'Antolli', 'vrt749o20@upemor.edu.mx', 'administrador');

-- Insertar datos en Alumnos
INSERT INTO Alumnos (id_alumno, cuatrimestre, grupo, carrera, id_usuario) VALUES
(101, 3, '3C', 'Ingenieria en Software', 1),
(102, 6, '6B', 'Ingenieria Industrial', 2),
(103, 9, '2D', 'Ingenieria en Sistemas', 3);

-- Insertar datos en Docentes
INSERT INTO Docentes (id_docente, cargo, horario, id_usuario) VALUES
(201, 'Profesor Calculo', '08:00-10:00', 4),
(202, 'Profesor POO', '10:00-12:00', 5);

-- Insertar datos en Administradores
INSERT INTO Administradores (id_administrador, cargo, id_usuario) VALUES
(301, 'Coordinador Academico', 6),
(302, 'Secretaria', 7);

-- Insertar datos en Recursamientos
INSERT INTO Recursamientos (id_recursamiento, materia, grupo, horario, id_administrador, id_docente) VALUES
(401, 'Calculo Integral', '3C', '08:00-10:00', 301, 201),
(402, 'POO', '6B', '10:00-12:00', 302, 202),
(403, 'POO', '2D', '11:00-13:00', 302, 202);

-- Insertar datos en Inscripciones
INSERT INTO Inscripciones (id_alumno, id_recursamiento, fecha, calificacion, estado) VALUES
(101, 401, '2025-07-10', 8.5, 'aprobado'),
(102, 402, '2025-07-10', 7.0, 'aprobado'),
(103, 403, '2025-07-11', 5.0, 'reprobado');