package com.assistuteam.assistu.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author assistu_team **/

@Getter
@Setter
public class Usuario extends Entidad {
    private String matricula;
    private String contrasenia;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private int idTipoUsuario; // FK a TipoUsuario

    public Usuario() {
        this.id = 0;
        this.matricula = "";
        this.contrasenia = "";
        this.nombre = "";
        this.apellidoPaterno = "";
        this.apellidoMaterno = "";
        this.correo = "";
        this.idTipoUsuario = 0;
    }
}