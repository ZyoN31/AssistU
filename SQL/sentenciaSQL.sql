-- Estructura y datos para la Base de Datos AssistU en MariaDB
-- Encargad@ de la creacion de la Base de Datos: Samaria & Kevin
-- Nombre de la Base de Datos: assistu_db

CREATE TABLE Tipo_usuario (
    id_tipo_usuario INT PRIMARY KEY NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);

CREATE TABLE Usuarios (
    matricula_usuario VARCHAR(20) PRIMARY KEY NOT NULL,
    contrasenia VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    id_tipo_usuario INT ,
    FOREIGN KEY (id_tipo_usuario) REFERENCES Tipo_usuario(id_tipo_usuario)
);

CREATE TABLE Alumnos (
    matricula_alumno VARCHAR(20) PRIMARY KEY NOT NULL,
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
    id_recursamiento INT PRIMARY KEY NOT NULL,
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

INSERT INTO Tipo_usuario (id_tipo_usuario, tipo_usuario, cargo) VALUES
(1, 'Alumno', 'Estudiante'),
(2, 'Docente', 'Profesor'),
(3, 'Administrador', 'Personal administrativo');

INSERT INTO Usuarios (matricula_usuario, contrasenia, nombre, apellido_paterno, apellido_materno, correo, id_tipo_usuario) VALUES
('CMSO240614', 'SAM123', 'Samaria', 'Cervantes', 'Martinez', 'cmso240614@upemor.edu.mx', 1),  -- Samaria y Martinez SIN acento
('MJJO240217', 'Jose4567', 'José', 'Martinez', 'Jacobo', 'MJJO240217@upemor.edu.mx', 1),      -- Martinez SIN acento
('DVKO240202', 'Kevin5677', 'Kevin', 'Diaz', 'Vega', 'DVKO240202@upemor.edu.mx', 1),           -- Diaz SIN acento (corregido de Dias→Diaz)
('JOSOUBUHAY', 'MINA78', 'Samuel', 'Arroyo', 'Soto', 'JOSOUBUHAY@upemor.edu.mx', 2),
('MDSO245577', 'RANIt@', 'Sebastián', 'Soto', 'Méndez', 'MDSO245577@upemor.edu.mx', 2),
('HDMIAL0970', 'Aldo90', 'Áxel', 'Ortiz', 'Domínguez', 'HDMIAL0970@upemor.edu.mx', 3),
('VRT749O20', 'axel567', 'Sofía', 'Pérez', 'Antolli', 'VRT749O20@upemor.edu.mx', 3);

INSERT INTO Alumnos (matricula_alumno, contrasenia, nombre, apellido_paterno, apellido_materno, correo, cuatrimestre, grupo, carrera) VALUES
('CMSO240614', 'SAM123', 'Samaria', 'Cervantes', 'Martinez', 'cmso240614@upemor.edu.mx', 3, '3C', 'Ingeniería en Software'),
('MJJO240217', 'Jose4567', 'José', 'Martinez', 'Jacobo', 'MJJO240217@upemor.edu.mx', 6, '6B', 'Ingeniería Industrial'),
('DVKO240202', 'Kevin5677', 'Kevin', 'Diaz', 'Vega', 'DVKO240202@upemor.edu.mx', 9, '2D', 'Ingeniería en sistemas');  

INSERT INTO Recursamientos (id_recursamiento, materia, grupo, horario, matricula_usuario) VALUES
(11, 'Cálculo Integral', '3C', '08:00-10:00', 'JOSOUBUHAY'),
(12, 'POO', '6B', '10:00-12:00', 'MDSO245577'),
(13, 'POO', '2D', '11:00-13:00', 'MDSO245577');

INSERT INTO Inscripciones (matricula_alumno, id_recursamiento, fecha, calificacion, estado) VALUES
('CMSO240614', 11, '2025-07-10', 8.5, 'aprobado'),
('MJJO240217', 12, '2025-07-10', 7.0, 'aprobado'),
('DVKO240202', 13, '2025-07-11', 5.0, 'reprobado');