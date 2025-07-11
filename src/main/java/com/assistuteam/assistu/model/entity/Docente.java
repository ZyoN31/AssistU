package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Docente extends Usuario {
    private String cargo;
    private String horario;

    public Docente() {
        super();
        this.id = 0;
        this.matricula = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidoPaterno = "";
        this.apellidoMaterno = "";
        this.correo = "";
        this.cargo = "";
        this.horario = "";
    }

    @Override
    public String toString() {
        return "Docente{" +
            "matricula='" + matricula + '\'' +
            ", nombre='" + nombre + '\'' +
            ", apellidoPaterno='" + apellidoPaterno + '\'' +
            ", apellidoMaterno='" + apellidoMaterno + '\'' +
            ", correo='" + correo + '\'' +
            ", cargo='" + cargo + '\'' +
            ", horario='" + horario + '\'' +
            '}';
    }
}
