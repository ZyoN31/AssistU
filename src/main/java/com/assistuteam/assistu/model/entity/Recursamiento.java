package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recursamiento extends Entidad {
    private String materia;
    private String grupo;
    private String horario;
    private String matriculaUsuario; // FK a Usuarios

    public Recursamiento() {
        this.id = 0;
        this.materia = "";
        this.grupo = "";
        this.horario = "";
        this.matriculaUsuario = "";
    }
}