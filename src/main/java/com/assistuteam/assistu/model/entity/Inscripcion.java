package com.assistuteam.assistu.model.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Inscripcion extends Entidad {
    private LocalDate fecha;
    private float calificacion;
    private String estado;
    private Alumno alumno;
    private Recursamiento recursamiento;

    public Inscripcion() {
        super();
        this.fecha = LocalDate.now();
        this.calificacion = 0.0f;
        this.estado = "";
        this.alumno = new Alumno();
        this.recursamiento = new Recursamiento();
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id_alumno=" + (alumno != null ? alumno.getId() : "null") +
                ", id_recursamiento=" + (recursamiento != null ? recursamiento.getId() : "null") +
                ", fecha=" + fecha +
                ", calificacion=" + calificacion +
                ", estado=" + estado +
                "} ";
    }
}
