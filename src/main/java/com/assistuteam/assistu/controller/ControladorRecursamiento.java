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
        if (obj.getMateria() == null || obj.getMateria().isEmpty()) throw new Exception("La materia es obligatoria");
        if (obj.getGrupo() == null || obj.getGrupo().isEmpty()) throw new Exception("El grupo es obligatorio");
        if (obj.getHorario() == null || obj.getHorario().isEmpty()) throw new Exception("El horario es obligatorio");
        if (obj.getMatriculaUsuario() == null || obj.getMatriculaUsuario().isEmpty()) throw new Exception("La matrícula del usuario es obligatoria");
        return true;
    }

    public List<Recursamiento> buscarPorMateria(String materia) throws Exception {
        List<Recursamiento> lista = repositorio.leerTodos();
        return lista.stream().filter(r -> r.getMateria().equalsIgnoreCase(materia)).toList();
    }

    public List<Recursamiento> buscarPorGrupo(String grupo) throws Exception {
        List<Recursamiento> lista = repositorio.leerTodos();
        return lista.stream().filter(r -> r.getGrupo().equalsIgnoreCase(grupo)).toList();
    }
}