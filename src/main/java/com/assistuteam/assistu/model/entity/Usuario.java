package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Usuario extends Entidad {
    protected String matricula;
    protected String contrasenia;
    protected String nombre;
    protected String apellidoPaterno;
    protected String apellidoMaterno;
    protected String correo;
}
