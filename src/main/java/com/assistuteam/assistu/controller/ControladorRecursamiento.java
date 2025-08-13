package com.assistuteam.assistu.controller;

import java.util.List;

import com.assistuteam.assistu.model.entity.Recursamiento;
import com.assistuteam.assistu.model.repository.RepositorioRecursamiento;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorRecursamiento extends Controlador<RepositorioRecursamiento, Recursamiento> {
    public ControladorRecursamiento() throws Exception {
        repositorio = new RepositorioRecursamiento();
    }

    @Override
    protected boolean validar(Recursamiento obj) throws Exception {
        if (obj.getId() < 0) throw new Exception("El ID del recursamiento es obligatorio");
        if (obj.getMateria() == null || obj.getMateria().isEmpty()) throw new Exception("La materia del recursamiento es obligatoria");
        if (obj.getGrupo() == null || obj.getGrupo().isEmpty()) throw new Exception("El grupo del recursamiento es obligatorio");
        if (obj.getHorario() == null || obj.getHorario().isEmpty()) throw new Exception("El horario del recursamiento es obligatorio");
        /*
        if (obj.getAdministrador() == null) throw new Exception("El administrador del recursamiento es obligatorio");
        if (obj.getDocente() == null) throw new Exception("El docente del recursamiento es obligatorio");
        */
        return true;
    }

    public void crearRecursamiento(Recursamiento recursamiento) throws Exception {
        try {
            if (validar(recursamiento)) {
                if (repositorio.crear(recursamiento)) {
                    System.out.println("Recursamiento creado exitosamente.");
                } else {
                    System.out.println("Error al crear el recursamiento.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: crearRecursamiento();");
            throw e;
        }
    }

    public void buscarPorMateria(String materia) throws Exception {
        List<Recursamiento> recursamientos = repositorio.leerTodos();
        recursamientos.forEach(recursamiento -> {
            if (recursamiento.getMateria().equalsIgnoreCase(materia)) {
                System.out.println(recursamiento);
            }
        });
    }

    public void buscarPorGrupo(String grupo) throws Exception {
        List<Recursamiento> recursamientos = repositorio.leerTodos();
        recursamientos.forEach(recursamiento -> {
            if (recursamiento.getGrupo().equalsIgnoreCase(grupo)) {
                System.out.println(recursamiento);
            }
        });
    }
    /*
    public void buscarPorDocente(String docente) throws Exception {
        List<Recursamiento> recursamientos = repositorio.leerTodos();
        recursamientos.forEach(recursamiento -> {
            if (recursamiento.getDocente().getNombre().equalsIgnoreCase(docente)) {
                System.out.println(recursamiento);
            }
        });
    }
    */
}
