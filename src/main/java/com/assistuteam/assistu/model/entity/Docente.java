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
    }
}
