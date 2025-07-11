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
    private boolean estado;
    private Alumno alumno;
    private Recursamiento recursamiento;

    public Inscripcion() {
        super();
        this.id = 0;
        this.fecha = LocalDate.now();
        this.calificacion = 0.0f;
        this.estado = false;
        this.alumno = new Alumno();
        this.recursamiento = new Recursamiento();
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", calificacion=" + calificacion +
                ", estado=" + estado +
                ", alumno=" + alumno +
                ", recursamiento=" + recursamiento +
                "} " + super.toString();
    }
}
