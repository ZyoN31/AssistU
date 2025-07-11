package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter 
public class Administrador extends Usuario {
    private String cargo;

    public Administrador() {
        super();
        this.id = 0;
        this.matricula = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidoPaterno = "";
        this.apellidoMaterno = "";
        this.correo = "";
        this.cargo = "";
    }

    @Override
    public String toString() {
        return "Administrador{" +
            "matricula='" + matricula + '\'' +
            ", nombre='" + nombre + '\'' +
            ", apellidoPaterno='" + apellidoPaterno + '\'' +
            ", apellidoMaterno='" + apellidoMaterno + '\'' +
            ", correo='" + correo + '\'' +
            ", cargo='" + cargo + '\'' +
            '}';
    }
}
