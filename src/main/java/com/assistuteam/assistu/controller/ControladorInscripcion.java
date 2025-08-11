package com.assistuteam.assistu.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.assistuteam.assistu.model.entity.Inscripcion;
import com.assistuteam.assistu.model.repository.RepositorioInscripcion;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorInscripcion extends Controlador<RepositorioInscripcion, Inscripcion> {
    public ControladorInscripcion() throws Exception {
        repositorio = new RepositorioInscripcion();
    }

    @Override
    protected boolean validar(Inscripcion obj) throws Exception {
        if (obj.getAlumno() == null || obj.getAlumno().getId() < 0) throw new Exception("El ID del alumno es obligatorio");
        if (obj.getRecursamiento() == null || obj.getRecursamiento().getId() < 0) throw new Exception("El ID del recursamiento es obligatorio");
        if (obj.getFecha() == null) throw new Exception("La fecha de la inscripcion es obligatoria");
        if (obj.getEstado() == null || obj.getEstado().isEmpty()) throw new Exception("El estado de la inscripcion es obligatorio");
        if (obj.getCalificacion() < 0 || obj.getCalificacion() > 10) throw new Exception("La calificacion de la inscripcion debe estar entre 0 y 10");
        return true;
    }

    public void crearInscripcion(Inscripcion inscripcion) throws Exception {
        try {
            if (validar(inscripcion)) {
                if (repositorio.crear(inscripcion)) {
                    System.out.println("Inscripción creada exitosamente.");
                } else {
                    System.out.println("Error al crear la inscripción.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: crearInscripcion();");
            throw e;
        }
    }

    public void buscarPorFecha(String fecha) throws Exception {
        try {
            DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaBuscada = java.time.LocalDate.parse(fecha, format);
            List<Inscripcion> inscripciones = repositorio.leerTodos();
            List<Inscripcion> encontradas = new java.util.ArrayList<>();
            inscripciones.forEach(inscripcion -> {
                try {
                    if (inscripcion.getFecha().equals(fechaBuscada)) {
                        encontradas.add(inscripcion);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            });
            System.out.println(encontradas);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: buscarPorFecha();");
            throw e;
        }
    }

    public void buscarPorEstado(String estado) throws Exception {
        try {
            repositorio.leerTodos().stream()
                .filter(inscripcion -> inscripcion.getEstado().equalsIgnoreCase(estado))
                .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: buscarPorEstado();");
            throw e;
        }
    }

    public void buscarPorCalificacion(float calificacion) throws Exception {
        try {
            repositorio.leerTodos().stream()
                .filter(inscripcion -> inscripcion.getCalificacion() == calificacion)
                .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: buscarPorCalificacion();");
            throw e;
        }
    }

    public void buscarPorRangoCalificacion(float min, float max) throws Exception {
        try {
            repositorio.leerTodos().stream()
                .filter(inscripcion -> inscripcion.getCalificacion() >= min && inscripcion.getCalificacion() <= max)
                .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: buscarPorRangoCalificacion();");
            throw e;
        }
    }

    public Inscripcion obtenerPorId(int idAlumno, int idRecursamiento) throws Exception {
        try {
            return repositorio.leer(idAlumno, idRecursamiento);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: obtenerPorId();");
            throw e;
        }
    }

    public boolean eliminar(int idAlumno, int idRecursamiento) throws Exception {
        try {
            return repositorio.borrar(idAlumno, idRecursamiento);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: eliminar();");
            throw e;
        }
    }
}
