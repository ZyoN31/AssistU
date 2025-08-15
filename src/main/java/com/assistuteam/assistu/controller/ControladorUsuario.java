package com.assistuteam.assistu.controller;

import com.assistuteam.assistu.model.entity.Usuario;
import com.assistuteam.assistu.model.repository.RepositorioUsuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorUsuario extends Controlador<RepositorioUsuario, Usuario> {
    public ControladorUsuario() throws Exception {
        repositorio = new RepositorioUsuario();
    }

    @Override
    protected boolean validar(Usuario obj) throws Exception {
        if (obj.getId() < 0) throw new Exception("El ID del usuario es obligatorio");
        if (obj.getMatricula() == null || obj.getMatricula().isEmpty()) throw new Exception("La matrícula es obligatoria");
        if (obj.getContrasenia() == null || obj.getContrasenia().isEmpty()) throw new Exception("La contraseña es obligatoria");
        if (obj.getNombre() == null || obj.getNombre().isEmpty()) throw new Exception("El nombre es obligatorio");
        if (obj.getApellidoPaterno() == null || obj.getApellidoPaterno().isEmpty()) throw new Exception("El apellido paterno es obligatorio");
        if (obj.getApellidoMaterno() == null || obj.getApellidoMaterno().isEmpty()) throw new Exception("El apellido materno es obligatorio");
        if (obj.getCorreo() == null || obj.getCorreo().isEmpty()) throw new Exception("El correo es obligatorio");
        if (obj.getIdTipoUsuario() <= 0) throw new Exception("El tipo de usuario es obligatorio");
        return true;
    }

    public Usuario buscarPorMatricula(String matricula) throws Exception {
        return repositorio.leerTodos()
                .stream()
                .filter(u -> u.getMatricula().equalsIgnoreCase(matricula.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    public Usuario login(String matricula, String contrasenia) throws Exception {
        return repositorio.leerTodos()
            .stream()
            .filter(u -> u.getMatricula().equalsIgnoreCase(matricula.toUpperCase())
                      && u.getContrasenia().equals(contrasenia))
            .findFirst()
            .orElse(null);
    }
}