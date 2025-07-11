-- En esta parte se agregaran las sentencias de comandos para la creacion de la Base de Datos de AssitU en SQLite
-- Esto significa en la creacion de las tablas que se necesitan para el funcionamiento del software
-- Encargad@ de la creacion de la Base de Datos: Samaria & Kevin
-- Nombre de la Base de Datos: assistu_db.db

-- Sentencia para eliminar las tablas si existen
DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS Alumnos;
DROP TABLE IF EXISTS Docentes;
DROP TABLE IF EXISTS Administradores;
DROP TABLE IF EXISTS Recursamientos;
DROP TABLE IF EXISTS Inscripciones;

-- Sentencia para crear la tabla de [Usuarios]
CREATE TABLE Usuarios (
    id_usuario INTEGER PRIMARY KEY NOT NULL,
    matricula TEXT,
    contrasenia TEXT NOT NULL,
    nombre TEXT NOT NULL,
    apellido_paterno TEXT NOT NULL,
    apellido_materno TEXT NOT NULL,
    correo TEXT NOT NULL,
    tipo_usuario TEXT
);

-- Sentencia para crear la tabla de [Alumnos]
CREATE TABLE Alumnos (
    id_alumno INTEGER PRIMARY KEY NOT NULL,
    cuatrimestre INTEGER NOT NULL,
    grupo TEXT NOT NULL,
    carrera TEXT NOT NULL,
    id_usuario INTEGER,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Docentes]
CREATE TABLE Docentes (
    id_docente INTEGER PRIMARY KEY NOT NULL,
    cargo TEXT NOT NULL,
    horario TEXT NOT NULL,
    id_usuario INTEGER,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Administradores]
CREATE TABLE Administradores (
    id_administrador INTEGER PRIMARY KEY NOT NULL,
    cargo TEXT NOT NULL,
    id_usuario INTEGER,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Recursamientos]
CREATE TABLE Recursamientos (
    id_recursamiento INTEGER PRIMARY KEY NOT NULL,
    materia TEXT NOT NULL,
    grupo TEXT NOT NULL,
    horario TEXT NOT NULL,
    id_administrador INTEGER,
    id_docente INTEGER,
    FOREIGN KEY(id_administrador) REFERENCES Administradores(id_administrador),
    FOREIGN KEY(id_docente) REFERENCES Docentes(id_docente)
);

-- Sentencia para crear la tabla de [Inscripciones]
CREATE TABLE Inscripciones (
    id_alumno INTEGER,
    id_recursamiento INTEGER,
    fecha DATE NOT NULL,
    calificacion REAL NOT NULL,
    estado TEXT NOT NULL,
    PRIMARY KEY(id_alumno, id_recursamiento),
    FOREIGN KEY(id_alumno) REFERENCES Alumnos(id_alumno),
    FOREIGN KEY(id_recursamiento) REFERENCES Recursamientos(id_recursamiento)
);

-- Insertar datos en Usuarios
INSERT INTO Usuarios (id_usuario, matricula, contrasenia, nombre, apellido_paterno, apellido_materno, correo, tipo_usuario) VALUES
(1,'CMSO240614', 'SAM123', 'Samaria', 'Cervantes', 'Martinez', 'cmso240614@upemor.edu.mx', 'alumna'),
(2,'MJJ0240614', 'Jose4567', 'Jose', 'Martinez', 'Jacobo', 'MJJ0240614.@upemor.edu.mx', 'alumno'),
(3,'DVKO240202', 'Kevin5677', 'Kevin', 'Diaz', 'Vega', 'DVKO240202.@upemor.edu.mx', 'Alumno'),
(4,'JOSOUBUHAY', 'MINA78', 'Samuel', 'Arroyo', 'Soto', 'JOSOUBUHAY.@upemor.edu.mx', 'Docente'),
(5,'jskaksnyyy', 'RANIt@', 'Sebastian', 'Soto', 'Mendez', 'jskaksnyyy.@upemor.edu.mx', 'Docente'),
(6,'HDMIAL0970', 'Aldo90', 'Axel', 'Ortiz', 'Dominguez', 'HDMIAL0970.@upemor.edu.mx', 'Administrador'),
(7,'VRT749O20', 'axel567', 'Sofia', 'Perez', 'Antolli', 'VRT749O20.@upemor.edu.mx', 'Administrador');

-- Insertar datos en Alumnos
INSERT INTO Alumnos (id_alumno, cuatrimestre, grupo, carrera, id_usuario) VALUES
(111, 3, '3C', 'Ingenieria en Software', 1),
(222, 6, '6B', 'Ingenieria Industrial', 2),
(333, 9, '2D', 'Ingenieria en sistemas', 3);

-- Insertar datos en Docentes
INSERT INTO Docentes (id_docente, cargo, horario, id_usuario) VALUES
(10, 'Profesor calculo', '08:00-10:00', 4),
(88, 'Profesor POO', '10:00-12:00', 5);

-- Insertar datos en Administradores
INSERT INTO Administradores (id_administrador, cargo, id_usuario) VALUES
(99, 'Coordinador academico', 6),
(22, 'Secretaria', 7);

-- Insertar datos en Recursamientos
INSERT INTO Recursamientos (id_recursamiento, materia, grupo, horario, id_administrador, id_docente) VALUES
(11, 'Calculo Integral', '3C', '08:00-10:00', 99, 10),
(12, 'POO', '6B', '10:00-12:00', 22, 88),
(13, 'POO', '2D', '11:00-13:00', 22, 88);

-- Insertar datos en Inscripciones
INSERT INTO Inscripciones (id_alumno, id_recursamiento, fecha, calificacion, estado) VALUES
(111, 11, '2025-07-10', 8.5, 'aprobado'),
(222, 12, '2025-07-10', 7.0, 'aprobado'),
(333, 13, '2025-07-11', 5.0, 'reprobado');