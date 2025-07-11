-- En esta parte se agregaran las sentencias de comandos para la creacion de la Base de Datos de AssitU en MariaDB
-- Esto significa en la creacion de las tablas que se necesitan para el funcionamiento del software
-- Encargad@ de la creacion de la Base de Datos: Samaria & Kevin

-- Sentencia para crear la tabla de [Usuarios]
CREATE TABLE Usuarios(
    id_usuario INT PRIMARY KEY NOT NULL,
    matricula VARCHAR(20),
    contrasenia VARCHAR(20) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellido_paterno VARCHAR(20) NOT NULL,
    apellido_materno VARCHAR(20) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    tipo_usuario VARCHAR(20)
);

-- Sentencia para crear la tabla de [Alumnos]
CREATE TABLE Alumnos(
    id_alumno INT PRIMARY KEY NOT NULL,
    cuatrimestre INT NOT NULL,
    grupo CHAR(10) NOT NULL,
    carrera VARCHAR(50) NOT NULL,
    id_usuario INT,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Docentes]
CREATE TABLE Docentes(
    id_docente INT PRIMARY KEY NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    horario VARCHAR(20) NOT NULL,
    id_usuario INT,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Administradores]
CREATE TABLE Administradores(
    id_administrador INT PRIMARY KEY NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    id_usuario INT,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Recursamientos]
CREATE TABLE Recursamientos(
    id_recursamiento INT PRIMARY KEY NOT NULL,
    materia VARCHAR(50) NOT NULL,
    grupo CHAR(10) NOT NULL,
    horario VARCHAR(20) NOT NULL,
    id_administrador INT,
    id_docente INT,
    FOREIGN KEY(id_administrador) REFERENCES Administradores(id_administrador),
    FOREIGN KEY(id_docente) REFERENCES Docentes(id_docente)
);

-- Sentencia para crear la tabla de [Inscripciones]
CREATE TABLE Inscripciones (
    id_alumno INT,
    id_recursamiento INT,
    PRIMARY KEY(id_alumno, id_recursamiento),
    FOREIGN KEY(id_alumno) REFERENCES Alumnos(id_alumno),
    FOREIGN KEY(id_recursamiento) REFERENCES Recursamientos(id_recursamiento),
    fecha DATE NOT NULL,
    calificacion FLOAT NOT NULL,
    estado VARCHAR(20) NOT NULL
);