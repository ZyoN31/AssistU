package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Alumno;

/** @author assistu_team **/

public class RepositorioAlumno extends Repositorio<Alumno> {
    public RepositorioAlumno() throws Exception {
        super("alumnos", 5);
        this.queryReadEverything = "SELECT * FROM alumnos";
    }

    @Override
    protected Alumno mappingObject(ResultSet result) throws Exception {
        Alumno objAlumno = new Alumno();
        objAlumno.setId(result.getInt(1));
        objAlumno.setMatricula(result.getString(2));
        objAlumno.setContrasenia(result.getString(3));
        objAlumno.setNombre(result.getString(4));
        objAlumno.setApellidoPaterno(result.getString(5));
        objAlumno.setApellidoMaterno(result.getString(6));
        objAlumno.setCorreo(result.getString(7));
        objAlumno.setCuatrimestre(result.getInt(8));
        objAlumno.setGrupo(result.getString(9));
        objAlumno.setCarrera(result.getString(10));
        return objAlumno;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Alumno obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setLong(i++, obj.getId());
        statement.setString(i++, obj.getMatricula());
        statement.setString(i++, obj.getContrasenia());
        statement.setString(i++, obj.getNombre());
        statement.setString(i++, obj.getApellidoPaterno());
        statement.setString(i++, obj.getApellidoMaterno());
        statement.setString(i++, obj.getCorreo());
        statement.setInt(i++, obj.getCuatrimestre());
        statement.setString(i++, obj.getGrupo());
        statement.setString(i++, obj.getCarrera());
    }
}
