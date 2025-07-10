package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter 
public class Administrador extends Usuario {
    private String cargo;

    public Administrador() {
        super();
    }
}
