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

    public void asignarDocente(MateriaRecursa materia, Docente docente, String horario) {
        // En curso: Implementación para asignar un docente a una materia de recursamiento
    }

    public void crearMateriaRecursa(Alumno alumno, Docente docente, String materia, String horario) {
        // En curso: Implementación para crear una materia de recursamiento
    }
}
