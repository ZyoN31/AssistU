package com.assistuteam.assistu.controller;

import java.util.List;

import com.assistuteam.assistu.model.entity.Docente;
import com.assistuteam.assistu.model.repository.RepositorioDocente;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorDocente extends Controlador<RepositorioDocente, Docente> {
    public ControladorDocente() throws Exception {
        repositorio = new RepositorioDocente();
    }

    @Override
    protected boolean validar(Docente obj) throws Exception {
        if (obj.getId() < 0) throw new Exception("El ID del docente es obligatorio");
        if (obj.getCargo() == null || obj.getCargo().isEmpty()) throw new Exception("El cargo del docente es obligatorio");
        if (obj.getHorario() == null || obj.getHorario().isEmpty()) throw new Exception("El horario del docente es obligatorio");
        return true;
    }

    public void obtenerPorMatricula(String matricula) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        for (Docente docente : docentes) {
            if (docente.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println(docente);
                return;
            }
        }
        System.out.println("No se encontró ningún docente con la matrícula: " + matricula);
    }

    public void buscarPorNombre(String nombre) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        docentes.forEach(docente -> {
            if (docente.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(docente);
            }
        });
    }

    public void buscarPorMatricula(String matricula) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        docentes.forEach(docente -> {
            if (docente.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println(docente);
            }
        });
    }

    public void buscarPorApellidoPaterno(String apellidoPaterno) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        docentes.forEach(docente -> {
            if (docente.getApellidoPaterno().equalsIgnoreCase(apellidoPaterno)) {
                System.out.println(docente);
            }
        });
    }

    public void buscarPorApellidoMaterno(String apellidoMaterno) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        docentes.forEach(docente -> {
            if (docente.getApellidoMaterno().equalsIgnoreCase(apellidoMaterno)) {
                System.out.println(docente);
            }
        });
    }

    public void buscarPorCorreo(String correo) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        docentes.forEach(docente -> {
            if (docente.getCorreo().equalsIgnoreCase(correo)) {
                System.out.println(docente);
            }
        });
    }

    public void buscarPorCargo(String cargo) throws Exception {
        List<Docente> docentes = repositorio.leerTodos();
        docentes.forEach(docente -> {
            if (docente.getCargo().equalsIgnoreCase(cargo)) {
                System.out.println(docente);
            }
        });
    }

}
