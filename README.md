<p align="center">
  <img src="src/main/java/com/assistuteam/assistu/resources/images/assistu_banner_02.png" alt="Banner de AssistU" width="100%">
</p>

**Sistema de Gestión de Recursamientos**  
Proyecto de escritorio para la Universidad Politécnica del Estado de Morelos

---

## Descripción  
AssistU es una aplicación de escritorio desarrollada en Java que automatiza y optimiza el proceso de recursamiento de materias. Permite gestionar el registro de alumnos, docentes y materias, agrupar estudiantes por horarios compatibles y asignar profesores de forma eficiente, todo en un entorno local usando SQLite o MariaDB.
#### Documentacion oficial y mas detallada: _https://github.com/ZyoN31/AssistU/wiki/AssistU_

---

## Características principales  
- **Gestión de usuarios**: crear y administrar perfiles de administradores, alumnos y docentes.  
- **Solicitud de recursamientos**: registro de materias a recursar por alumno.  
- **Asignación de horarios**: agrupación automática/manual de alumnos y asignación de profesores.  
- **Consultas y reportes**: visualización de estatus, estadísticas y generación de reportes básicos.  
- **Arquitectura modular**: basada en MVC y DAO para facilitar mantenimiento y futuras ampliaciones.  
- **Funcionamiento local**: sin necesidad de conexión a internet; ideal para entornos de laboratorio o aula.

---

## Tecnologías
 - **Java SE 26**
 - **Swing** para GUI con **FlatLaf 3.6**
 - SQLite / MariaDB
 - Maven
 - **Lombok** para reducción de boilerplate
 - **New Relic** para monitoreo

## Requisitos previos
Tener instalado:
- **Java Development Kit (JDK) 26** o superior
- **Maven 3.6+** o superior
- **Git**

Verificar las versiones instaladas:
```bash
java -version
```
```bash
mvn -version
```

---

## Instalación  
1. Clonar este repositorio:  
   ```bash
   git clone https://github.com/ZyoN31/AssistU.git
   ```
   ```bash
   cd AssistU
   ```

2. Instalar las dependencias de Maven:
   ```bash
   mvn clean install
   ```

3. Compilar el proyecto:
   ```bash
   mvn compile
   ```

## Ejecución
Ejecutar la aplicación:
```bash
mvn exec:java -Dexec.mainClass="com.assistuteam.assistu.AssistU"
```

O ejecutar el JAR compilado:
```bash
mvn package
java -jar target/AssistU.jar
```

## Estructura del Proyecto
```
AssistU/
├── src/main/java/com/assistuteam/assistu/
│   ├── AssistU.java                 # Clase principal
│   ├── controller/                  # Controladores (MVC)
│   │   ├── Controlador.java
│   │   ├── ControladorAlumno.java
│   │   ├── ControladorInscripcion.java
│   │   ├── ControladorRecursamiento.java
│   │   └── ControladorUsuario.java
│   ├── model/                       # Modelos y repositorios (DAO)
│   │   ├── Conexion.java
│   │   ├── entity/                  # Entidades de dominio
│   │   └── repository/              # Repositorios de acceso a datos
│   ├── view/                        # Vistas (Swing + FlatLaf)
│   │   ├── FramePrincipal.java
│   │   ├── FrameAdministrador.java
│   │   ├── FrameAlumno.java
│   │   ├── FrameDocente.java
│   │   ├── FrameRegistro.java
│   │   ├── themes/                  # Temas personalizados
│   │   └── util/                    # Utilidades de UI
│   └── resources/                   # Recursos (imágenes, fuentes)
├── SQL/                             # Scripts de base de datos
├── pom.xml                          # Configuración de Maven
└── README.md                        # Este archivo
```

---

## Licencia
Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).

---

## Equipo
 - Cervantes Martínez Samaria
 - Martínez Jacobo José Francisco
 - Díaz Vega Kevin Omar

<p align="center">
  <img src="src/main/java/com/assistuteam/assistu/resources/images/Icon_AU.png" alt="Icono de AssistU" width="25%">
</p>