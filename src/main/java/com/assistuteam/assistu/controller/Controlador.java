package com.assistuteam.assistu.controller;

import java.util.List;

import com.assistuteam.assistu.model.entity.Entidad;
import com.assistuteam.assistu.model.repository.Repositorio;

/** @author assistu_team **/

@SuppressWarnings("all")
public abstract class Controlador<R extends Repositorio, E extends Entidad> {
    protected R repositorio;

    protected abstract boolean validar(E obj) throws Exception;

    public boolean guardar(E obj) throws Exception {
        try {
            if (validar(obj)) {
                if (obj.getId() == 0) {
                    return repositorio.crear(obj);
                } else {
                    return repositorio.actualizar(obj);
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: guardar();");
            throw e;
        }
    }

    public List<E> obtenerTodos() throws Exception {
        try {
            return repositorio.leerTodos();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: obtenerTodos();");
            throw e;
        }
    }

    public E obtenerPorId(int id) throws Exception {
        try {
            return (E) repositorio.leer(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: obtenerPorId();");
            throw e;
        }
    }

    public boolean eliminar(int id) throws Exception {
        try {
            return repositorio.borrar(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName()
                    + " in method: eliminar();");
            throw e;
        }
    }
}
