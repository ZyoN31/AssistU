package com.assistuteam.assistu.controller;

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
        if (obj.getMatriculaAlumno() == null || obj.getMatriculaAlumno().isEmpty()) throw new Exception("La matrícula del alumno es obligatoria");
        if (obj.getIdRecursamiento() <= 0) throw new Exception("El id del recursamiento es obligatorio");
        if (obj.getFecha() == null) throw new Exception("La fecha es obligatoria");
        if (obj.getEstado() == null || obj.getEstado().isEmpty()) throw new Exception("El estado es obligatorio");
        if (obj.getCalificacion() < 0 || obj.getCalificacion() > 10) throw new Exception("La calificación debe estar entre 0 y 10");
        return true;
    }

    public Inscripcion obtenerPorPK(String matriculaAlumno, int idRecursamiento) throws Exception {
        return repositorio.leer(matriculaAlumno, idRecursamiento);
    }

    public boolean eliminarPorPK(String matriculaAlumno, int idRecursamiento) throws Exception {
        return repositorio.borrar(matriculaAlumno, idRecursamiento);
    }

    public List<Inscripcion> buscarPorEstado(String estado) throws Exception {
        return repositorio.leerTodos()
                .stream()
                .filter(i -> i.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    public List<Inscripcion> buscarPorCalificacion(float min, float max) throws Exception {
        return repositorio.leerTodos()
                .stream()
                .filter(i -> i.getCalificacion() >= min && i.getCalificacion() <= max)
                .toList();
    }

    public List<Inscripcion> buscarPorFecha(java.time.LocalDate fecha) throws Exception {
        return repositorio.leerTodos()
                .stream()
                .filter(i -> i.getFecha().equals(fecha))
                .toList();
    }

    public List<Inscripcion> historialPorAlumno(String matriculaAlumno) throws Exception {
        return repositorio.leerTodos()
            .stream()
            .filter(i -> i.getMatriculaAlumno().equalsIgnoreCase(matriculaAlumno))
            .toList();
    }
}