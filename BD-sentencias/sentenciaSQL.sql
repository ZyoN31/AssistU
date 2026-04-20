-- Estructura y datos para la Base de Datos AssistU en MariaDB
-- Encargad@ de la creacion de la Base de Datos: Samaria & Kevin
-- Nombre de la Base de Datos: assistu_db

DROP TABLE IF EXISTS Inscripciones;
DROP TABLE IF EXISTS Recursamientos;
DROP TABLE IF EXISTS Alumnos;
DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS Tipo_usuario;

CREATE TABLE Tipo_usuario (
    id_tipo_usuario INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);

CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    matricula_usuario VARCHAR(20) UNIQUE NOT NULL,
    contrasenia VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    id_tipo_usuario INT,
    FOREIGN KEY (id_tipo_usuario) REFERENCES Tipo_usuario(id_tipo_usuario)
);

CREATE TABLE Alumnos (
    id_alumno INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    matricula_alumno VARCHAR(20) UNIQUE NOT NULL,
    contrasenia VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    cuatrimestre INT NOT NULL,
    grupo VARCHAR(10) NOT NULL,
    carrera VARCHAR(50) NOT NULL
);

CREATE TABLE Recursamientos (
    id_recursamiento INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    materia VARCHAR(50) NOT NULL,
    grupo VARCHAR(10) NOT NULL,
    horario VARCHAR(20) NOT NULL,
    matricula_usuario VARCHAR(20) NOT NULL,
    FOREIGN KEY (matricula_usuario) REFERENCES Usuarios(matricula_usuario)
);

CREATE TABLE Inscripciones (
    matricula_alumno VARCHAR(20) NOT NULL,
    id_recursamiento INT NOT NULL,
    fecha DATE NOT NULL,
    calificacion FLOAT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    PRIMARY KEY (matricula_alumno, id_recursamiento),
    FOREIGN KEY (matricula_alumno) REFERENCES Alumnos(matricula_alumno),
    FOREIGN KEY (id_recursamiento) REFERENCES Recursamientos(id_recursamiento)
);

INSERT INTO Tipo_usuario (tipo_usuario, cargo) VALUES
('Alumno', 'Estudiante'),
('Docente', 'Profesor'),
('Administrador', 'Personal administrativo');

INSERT INTO Usuarios (matricula_usuario, contrasenia, nombre, apellido_paterno, apellido_materno, correo, id_tipo_usuario) VALUES
('JOSOUBUHAY', 'MINA78', 'Samuel', 'Arroyo', 'Soto', 'JOSOUBUHAY@upemor.edu.mx', 2),
('MDSO245577', 'RANIt@', 'Sebastián', 'Soto', 'Méndez', 'MDSO245577@upemor.edu.mx', 2),
('HDMIAL0970', 'Aldo90', 'Áxel', 'Ortiz', 'Domínguez', 'HDMIAL0970@upemor.edu.mx', 3),
('VRT749O20', 'axel567', 'Sofía', 'Pérez', 'Antolli', 'VRT749O20@upemor.edu.mx', 3);

INSERT INTO Alumnos (matricula_alumno, contrasenia, nombre, apellido_paterno, apellido_materno, correo, cuatrimestre, grupo, carrera) VALUES
('CMSO240614', 'SAM123', 'Samaria', 'Cervantes', 'Martinez', 'cmso240614@upemor.edu.mx', 3, '3C', 'Ingeniería en Software'),
('MJJO240217', 'Jose4567', 'José', 'Martinez', 'Jacobo', 'MJJO240217@upemor.edu.mx', 6, '6B', 'Ingeniería Industrial'),
('DVKO240202', 'Kevin5677', 'Kevin', 'Diaz', 'Vega', 'DVKO240202@upemor.edu.mx', 9, '2D', 'Ingeniería en sistemas');  

INSERT INTO Recursamientos (materia, grupo, horario, matricula_usuario) VALUES
('Cálculo Integral', '3C', '08:00-10:00', 'JOSOUBUHAY'),
('POO', '6B', '10:00-12:00', 'MDSO245577'),
('POO', '2D', '11:00-13:00', 'MDSO245577');

INSERT INTO Inscripciones (matricula_alumno, id_recursamiento, fecha, calificacion, estado) VALUES
('CMSO240614', 1, '2025-07-10', 8.5, 'aprobado'),
('MJJO240217', 2, '2025-07-10', 7.0, 'aprobado'),
('DVKO240202', 3, '2025-07-11', 5.0, 'reprobado');