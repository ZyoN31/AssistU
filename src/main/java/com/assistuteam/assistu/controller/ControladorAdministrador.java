package com.assistuteam.assistu.controller;

import java.util.List;

import com.assistuteam.assistu.model.entity.Administrador;
import com.assistuteam.assistu.model.repository.RepositorioAdministrador;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorAdministrador extends Controlador<RepositorioAdministrador, Administrador> {
    public ControladorAdministrador() throws Exception {
        repositorio = new RepositorioAdministrador();
    }

    @Override
    protected boolean validar(Administrador obj) throws Exception {
        if (obj.getId() < 0) throw new Exception("El ID del administrador es obligatorio");
        if (obj.getCargo() == null || obj.getCargo().isEmpty()) throw new Exception("El cargo del administrador es obligatorio");
        return true;
    }

    public void buscarPorMatricula(String matricula) throws Exception {
        List<Administrador> administradores = repositorio.leerTodos();
        administradores.forEach(administrador -> {
            if (administrador.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println(administrador);
            }
        });
    }

    public void buscarPorNombre(String nombre) throws Exception {
        List<Administrador> administradores = repositorio.leerTodos();
        administradores.forEach(administrador -> {
            if (administrador.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(administrador);
            }
        });
    }

    public void buscarPorApellidoPaterno(String apellidoPaterno) throws Exception {
        List<Administrador> administradores = repositorio.leerTodos();
        administradores.forEach(administrador -> {
            if (administrador.getApellidoPaterno().equalsIgnoreCase(apellidoPaterno)) {
                System.out.println(administrador);
            }
        });
    }

    public void buscarPorApellidoMaterno(String apellidoMaterno) throws Exception {
        List<Administrador> administradores = repositorio.leerTodos();
        administradores.forEach(administrador -> {
            if (administrador.getApellidoMaterno().equalsIgnoreCase(apellidoMaterno)) {
                System.out.println(administrador);
            }
        });
    }

    public void buscarPorCorreo(String correo) throws Exception {
        List<Administrador> administradores = repositorio.leerTodos();
        administradores.forEach(administrador -> {
            if (administrador.getCorreo().equalsIgnoreCase(correo)) {
                System.out.println(administrador);
            }
        });
    }

    public void buscarPorCargo(String cargo) throws Exception {
        List<Administrador> administradores = repositorio.leerTodos();
        administradores.forEach(administrador -> {
            if (administrador.getCargo().equalsIgnoreCase(cargo)) {
                System.out.println(administrador);
            }
        });
    }
}
