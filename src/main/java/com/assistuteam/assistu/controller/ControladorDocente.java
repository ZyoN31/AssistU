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
        if (obj.getMatricula() == null || obj.getMatricula().isEmpty()) throw new Exception("La matricula del docente es obligatoria");
        if (obj.getNombre() == null || obj.getNombre().isEmpty()) throw new Exception("El nombre del docente es obligatorio");
        if (obj.getContrasenia() == null || obj.getContrasenia().isEmpty()) throw new Exception("La contrasenia del docente es obligatoria");
        if (obj.getApellidoPaterno() == null || obj.getApellidoPaterno().isEmpty()) throw new Exception("El apellido paterno del docente es obligatorio");
        if (obj.getApellidoMaterno() == null || obj.getApellidoMaterno().isEmpty()) throw new Exception("El apellido materno del docente es obligatorio");
        if (obj.getCorreo() == null || obj.getCorreo().isEmpty()) throw new Exception("El correo del docente es obligatorio");
        if (obj.getCargo() == null || obj.getCargo().isEmpty()) throw new Exception("El cargo del docente es obligatorio");
        if (obj.getHorario() == null || obj.getHorario().isEmpty()) throw new Exception("El horario del docente es obligatorio");
        return true;
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
