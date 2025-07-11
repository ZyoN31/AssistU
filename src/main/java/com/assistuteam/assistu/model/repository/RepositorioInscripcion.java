package com.assistuteam.assistu.model.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Inscripcion;

/** @author assistu_team **/

public class RepositorioInscripcion extends Repositorio<Inscripcion> {
    public RepositorioInscripcion() throws Exception {
        super("Inscripciones", 6);
        this.queryReadEverything = "SELECT * FROM Inscripciones";
    }

    @Override
    protected Inscripcion mappingObject(ResultSet result) throws Exception {
        Inscripcion obj = new Inscripcion();
        obj.setId(result.getInt(1));
        obj.setFecha(result.getDate(2).toLocalDate());
        obj.setCalificacion(result.getFloat(3));
        obj.setEstado(result.getBoolean(4));
        // Mapeando alumno y recursamiento usando sus repositorios
        int idAlumno = result.getInt(5);
        int idRecursamiento = result.getInt(6);
        RepositorioAlumno repoAlumno = new RepositorioAlumno();
        RepositorioRecursamiento repoRecursamiento = new RepositorioRecursamiento();
        obj.setAlumno(repoAlumno.leer(idAlumno));
        obj.setRecursamiento(repoRecursamiento.leer(idRecursamiento));
        return obj;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Inscripcion obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId());
        statement.setDate(i++, Date.valueOf(obj.getFecha()));
        statement.setFloat(i++, obj.getCalificacion());
        statement.setBoolean(i++, obj.isEstado());
        // Si tienes los IDs de alumno y recursamiento, agrégalos aquí
        statement.setInt(i++, obj.getAlumno().getId());
        statement.setInt(i++, obj.getRecursamiento().getId());
    }
}
