package com.assistuteam.assistu.model.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Inscripcion extends Entidad {
    private String matriculaAlumno; // FK
    private int idRecursamiento; // FK
    private LocalDate fecha;
    private float calificacion;
    private String estado;

    public Inscripcion() {
        this.matriculaAlumno = "";
        this.idRecursamiento = 0;
        this.fecha = LocalDate.now();
        this.calificacion = 0.0f;
        this.estado = "";
    }
}