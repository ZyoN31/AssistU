package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class MateriaRecursa {
    private int id;
    private String nombre;
    private float calificacion;
    private String estado; // Puede ser "Aprobada", "Reprobada", "En curso", etc.
    private String horario;
    private Alumno alumno; // Relación con el alumno que está recursando la materia
    private Docente docente; // Relación con el docente que imparte la materia
    private Administrador administrador; // Relación con el administrador que gestiona la materia

    public MateriaRecursa() {
        super();
    }
}
