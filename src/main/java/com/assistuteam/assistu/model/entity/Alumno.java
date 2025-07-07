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
    }

    public void consultarCalificaciones() {
        // En curso: Implementación para consultar las calificaciones del alumno
    }

    public void solicitarRecursamiento(MateriaRecursa materia) {
        // En curso: Implementación para solicitar el recursamiento de una materia
    }
}
