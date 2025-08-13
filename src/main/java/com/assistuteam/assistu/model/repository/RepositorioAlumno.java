package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Alumno;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioAlumno extends Repositorio<Alumno> {
    public RepositorioAlumno() throws Exception {
        super("Alumnos", "alumno", 10); // id_alumno + 9 campos
    }

    @Override
    protected Alumno mappingObject(ResultSet result) throws Exception {
        Alumno alumno = new Alumno();
        alumno.setId(result.getInt("id_alumno"));
        alumno.setMatricula(result.getString("matricula_alumno"));
        alumno.setContrasenia(result.getString("contrasenia"));
        alumno.setNombre(result.getString("nombre"));
        alumno.setApellidoPaterno(result.getString("apellido_paterno"));
        alumno.setApellidoMaterno(result.getString("apellido_materno"));
        alumno.setCorreo(result.getString("correo"));
        alumno.setCuatrimestre(result.getInt("cuatrimestre"));
        alumno.setGrupo(result.getString("grupo"));
        alumno.setCarrera(result.getString("carrera"));
        return alumno;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Alumno alumno, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, alumno.getId());
        statement.setString(i++, alumno.getMatricula());
        statement.setString(i++, alumno.getContrasenia());
        statement.setString(i++, alumno.getNombre());
        statement.setString(i++, alumno.getApellidoPaterno());
        statement.setString(i++, alumno.getApellidoMaterno());
        statement.setString(i++, alumno.getCorreo());
        statement.setInt(i++, alumno.getCuatrimestre());
        statement.setString(i++, alumno.getGrupo());
        statement.setString(i++, alumno.getCarrera());
    }
}