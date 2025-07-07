package com.assistuteam.assistu.model.entity;

import java.util.List;

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

    public void consultarGrupo() {
        // En curso: Implementación para consultar el grupo del docente
    }

    public void asignarCalificaciones(List<MateriaRecursa> materias, float calificacion) {
        // En curso: Implementación para asignar calificaciones a las materias de recursamiento
    }
}
