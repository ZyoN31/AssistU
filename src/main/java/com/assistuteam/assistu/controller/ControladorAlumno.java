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
        if (obj.getId() < 0) throw new Exception("El ID del administrador es obligatorio");
        if (obj.getMatricula() == null || obj.getMatricula().isEmpty()) throw new Exception("La matricula del alumno es obligatoria");
        if (obj.getNombre() == null || obj.getNombre().isEmpty()) throw new Exception("El nombre del alumno es obligatorio");
        if (obj.getContrasenia() == null || obj.getContrasenia().isEmpty()) throw new Exception("La contraseña del alumno es obligatoria");
        if (obj.getApellidoPaterno() == null || obj.getApellidoPaterno().isEmpty()) throw new Exception("El apellido paterno del alumno es obligatorio");
        if (obj.getApellidoMaterno() == null || obj.getApellidoMaterno().isEmpty()) throw new Exception("El apellido materno del alumno es obligatorio");
        if (obj.getCorreo() == null || obj.getCorreo().isEmpty()) throw new Exception("El correo del alumno es obligatorio");
        if (obj.getCuatrimestre() < 1) throw new Exception("El cuatrimestre del alumno es obligatorio y debe ser mayor a 0");
        if (obj.getGrupo() == null || obj.getGrupo().isEmpty()) throw new Exception("El grupo del alumno es obligatorio");
        if (obj.getCarrera() == null || obj.getCarrera().isEmpty()) throw new Exception("La carrera del alumno es obligatoria");
        return true;
    }

    public void buscarPorMatricula(String matricula) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorNombre(String nombre) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorApellidoPaterno(String apellidoPaterno) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getApellidoPaterno().equalsIgnoreCase(apellidoPaterno)) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorApellidoMaterno(String apellidoMaterno) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getApellidoMaterno().equalsIgnoreCase(apellidoMaterno)) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorCorreo(String correo) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getCorreo().equalsIgnoreCase(correo)) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorCuatrimestre(int cuatrimestre) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getCuatrimestre() == cuatrimestre) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorGrupo(String grupo) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getGrupo().equalsIgnoreCase(grupo)) {
                System.out.println(alumno);
            }
        });
    }

    public void buscarPorCarrera(String carrera) throws Exception {
        List<Alumno> alumnos = repositorio.leerTodos();
        alumnos.forEach(alumno -> {
            if (alumno.getCarrera().equalsIgnoreCase(carrera)) {
                System.out.println(alumno);
            }
        });
    }
}
