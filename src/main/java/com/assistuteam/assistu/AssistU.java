package com.assistuteam.assistu;

import com.assistuteam.assistu.controller.ControladorAdministrador;
import com.assistuteam.assistu.controller.ControladorAlumno;
import com.assistuteam.assistu.controller.ControladorDocente;
import com.assistuteam.assistu.controller.ControladorInscripcion;
import com.assistuteam.assistu.controller.ControladorRecursamiento;
import com.assistuteam.assistu.controller.ControladorUsuario;

@SuppressWarnings("all")
public class AssistU {
    public static void main(String[] args) throws Exception {
        // Usuarios
        ControladorUsuario controladorUsuario = new ControladorUsuario();
        System.out.println("\n--- Todos los usuarios ---");
        controladorUsuario.obtenerTodos().forEach(usuario -> {
            System.out.println(usuario + "\n");
        });
        System.out.println("--- Buscar usuario por nombre ---");
        controladorUsuario.buscarPorNombre("Jose");
        System.out.println("\n--- Buscar usuario por rol ---");
        controladorUsuario.buscarPorRol("Administrador");

        // Alumnos
        ControladorAlumno controladorAlumno = new ControladorAlumno();
        System.out.println("\n--- Todos los alumnos ---");
        controladorAlumno.obtenerTodos().forEach(alumno -> {
            System.out.println(alumno + "\n");
        });
        System.out.println("--- Buscar alumno por nombre ---");
        controladorAlumno.buscarPorNombre("Samaria");
        System.out.println("\n--- Buscar alumno por carrera ---");
        controladorAlumno.buscarPorCarrera("Ingenieria en Software");

        // Docentes
        ControladorDocente controladorDocente = new ControladorDocente();
        System.out.println("\n--- Todos los docentes ---");
        controladorDocente.obtenerTodos().forEach(docente -> {
            System.out.println(docente + "\n");
        });
        System.out.println("--- Buscar docente por nombre ---");
        controladorDocente.buscarPorNombre("Samuel");
        System.out.println("\n--- Buscar docente por cargo ---");
        controladorDocente.buscarPorCargo("Profesor calculo");

        // Administradores
        ControladorAdministrador controladorAdministrador = new ControladorAdministrador();
        System.out.println("\n--- Todos los administradores ---");
        controladorAdministrador.obtenerTodos().forEach(administrador -> {
            System.out.println(administrador + "\n");
        });
        System.out.println("--- Buscar administrador por nombre ---");
        controladorAdministrador.buscarPorNombre("Axel");
        System.out.println("\n--- Buscar administrador por cargo ---");
        controladorAdministrador.buscarPorCargo("Coordinador academico");

        // Recursamientos
        ControladorRecursamiento controladorRecursamiento = new ControladorRecursamiento();
        System.out.println("\n--- Todos los recursamientos ---");
        controladorRecursamiento.obtenerTodos().forEach(recursamiento -> {
            System.out.println(recursamiento + "\n");
        });
        System.out.println("--- Buscar recursamiento por materia ---");
        controladorRecursamiento.buscarPorMateria("Calculo Integral");
        System.out.println("\n--- Buscar recursamiento por grupo ---");
        controladorRecursamiento.buscarPorGrupo("3C");

        // Inscripciones
        ControladorInscripcion controladorInscripcion = new ControladorInscripcion();
        System.out.println("\n--- Todas las inscripciones ---");
        controladorInscripcion.obtenerTodos().forEach(inscripcion -> {
            System.out.println(inscripcion + "\n");
        });
        System.out.println("--- Buscar inscripcion por estado ---");
        controladorInscripcion.buscarPorEstado("aprobado");
        System.out.println("\n--- Buscar inscripcion por fecha ---");
        controladorInscripcion.buscarPorFecha("2025-07-10");
    }
}
