package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Docente;

/** @author assistu_team **/

public class RepositorioDocente extends Repositorio<Docente> {
    public RepositorioDocente() throws Exception {
        super("Docentes", 9);
        this.queryReadEverything = "SELECT * FROM Docentes";
    }

    @Override
    protected Docente mappingObject(ResultSet result) throws Exception {
        Docente objDocente = new Docente();
        objDocente.setId(result.getInt(1));
        objDocente.setMatricula(result.getString(2));
        objDocente.setContrasenia(result.getString(3));
        objDocente.setNombre(result.getString(4));
        objDocente.setApellidoPaterno(result.getString(5));
        objDocente.setApellidoMaterno(result.getString(6));
        objDocente.setCorreo(result.getString(7));
        objDocente.setCargo(result.getString(8));
        objDocente.setHorario(result.getString(9));
        return objDocente;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Docente obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId());
        statement.setString(i++, obj.getMatricula());
        statement.setString(i++, obj.getContrasenia());
        statement.setString(i++, obj.getNombre());
        statement.setString(i++, obj.getApellidoPaterno());
        statement.setString(i++, obj.getApellidoMaterno());
        statement.setString(i++, obj.getCorreo());
        statement.setString(i++, obj.getCargo());
        statement.setString(i++, obj.getHorario());
    }
}
