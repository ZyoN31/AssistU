package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Alumno extends Usuario {
    private int cuatrimestre;
    private String grupo;
    private String carrera;

    public Alumno() {
        super();
        this.id = 0;
        this.matricula = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidoPaterno = "";
        this.apellidoMaterno = "";
        this.correo = "";
        this.cuatrimestre = 0;
        this.grupo = "";
        this.carrera = "";
    }

    @Override
    public String toString() {
        return "Alumno{" +
            "matricula='" + matricula + '\'' +
            ", nombre='" + nombre + '\'' +
            ", apellidoPaterno='" + apellidoPaterno + '\'' +
            ", apellidoMaterno='" + apellidoMaterno + '\'' +
            ", correo='" + correo + '\'' +
            ", cuatrimestre=" + cuatrimestre +
            ", grupo='" + grupo + '\'' +
            ", carrera='" + carrera + '\'' +
            '}';
    }
}
