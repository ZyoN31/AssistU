package com.assistuteam.assistu.model.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Inscripcion {
    private int id;
    private LocalDate fecha;
    private float calificacion;
    private boolean estado;
    private Alumno alumno;
    private Recursamiento recursamiento;

    public Inscripcion() {
        super();
    }
}
