package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Recursamiento extends Entidad {
    private String materia;
    private String grupo;
    private String horario;

    public Recursamiento() {
        super();
        this.id = 0;
        this.materia = "";
        this.grupo = "";
        this.horario = "";
    }

    @Override
    public String toString() {
        return "Recursamiento{" +
                "id=" + id +
                ", materia='" + materia + '\'' +
                ", grupo='" + grupo + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}
