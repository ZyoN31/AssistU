package com.assistuteam.assistu.controller;

import java.util.List;

import com.assistuteam.assistu.model.entity.Alumno;
import com.assistuteam.assistu.model.repository.RepositorioAlumno;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorAlumno extends Controlador<RepositorioAlumno, Alumno> {
    public ControladorAlumno() throws Exception {
        repositorio = new RepositorioAlumno();
    }

    @Override
    protected boolean validar(Alumno obj) throws Exception {
        if (obj.getId() < 0) throw new Exception("El ID del alumno es obligatorio");
        if (obj.getCuatrimestre() < 1) throw new Exception("El cuatrimestre debe ser mayor a 0");
        if (obj.getGrupo() == null || obj.getGrupo().isEmpty()) throw new Exception("El grupo es obligatorio");
        if (obj.getCarrera() == null || obj.getCarrera().isEmpty()) throw new Exception("La carrera es obligatoria");
        return true;
    }

    public Alumno buscarPorMatricula(String matricula) throws Exception {
        return repositorio.leerTodos()
                .stream()
                .filter(a -> a.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElse(null);
    }

    public List<Alumno> buscarPorNombre(String nombre) throws Exception {
        return repositorio.leerTodos()
                .stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .toList();
    }

    public Alumno login(String matricula, String contrasenia) throws Exception {
        return repositorio.leerTodos()
            .stream()
            .filter(a -> a.getMatricula().equalsIgnoreCase(matricula)
                      && a.getContrasenia().equals(contrasenia))
            .findFirst()
            .orElse(null);
    }

    public boolean guardarAlumno(Alumno alumno) throws Exception {
        return guardar(alumno);
    }
}