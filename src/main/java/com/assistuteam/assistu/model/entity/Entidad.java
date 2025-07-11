package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Entidad {
    // Se crea esta clase para unificar a todas las clases relacionadas con ID y asi no recurrir a objetos genericos o temas por el estilo
    protected int id;
}
