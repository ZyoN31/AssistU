-- En esta parte se agregaran las sentencias de comandos para la creacion de la Base de Datos de AssitU en SQLite
-- Esto significa en la creacion de las tablas que se necesitan para el funcionamiento del software
-- Encargad@ de la creacion de la Base de Datos: Samaria & Kevin

-- Sentencia para crear la tabla de [Usuarios]
CREATE TABLE Usuarios(
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
CREATE TABLE Alumnos(
    id_alumno INTEGER PRIMARY KEY NOT NULL,
    cuatrimestre INTEGER NOT NULL,
    grupo TEXT NOT NULL,
    carrera TEXT NOT NULL,
    id_usuario INTEGER,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Docentes]
CREATE TABLE Docentes(
    id_docente INTEGER PRIMARY KEY NOT NULL,
    cargo TEXT NOT NULL,
    horario TEXT NOT NULL,
    id_usuario INTEGER,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Administradores]
CREATE TABLE Administradores(
    id_administrador INTEGER PRIMARY KEY NOT NULL,
    cargo TEXT NOT NULL,
    id_usuario INTEGER,
    FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Sentencia para crear la tabla de [Recursamientos]
CREATE TABLE Recursamientos(
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