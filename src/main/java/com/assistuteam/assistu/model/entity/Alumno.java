package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alumno extends Entidad {
    private String matricula;
    private String contrasenia;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private int cuatrimestre;
    private String grupo;
    private String carrera;

    public Alumno() {
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
}