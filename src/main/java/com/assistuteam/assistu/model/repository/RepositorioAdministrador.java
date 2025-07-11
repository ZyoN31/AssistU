package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Administrador;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioAdministrador extends Repositorio<Administrador> {
    public RepositorioAdministrador() throws Exception {
        super("Administradores", Administrador.class.getName(), 8);
    }

    @Override
    protected Administrador mappingObject(ResultSet result) throws Exception {
        Administrador objAdministrador = new Administrador();
        objAdministrador.setId(result.getInt(1));
        objAdministrador.setMatricula(result.getString(2));
        objAdministrador.setContrasenia(result.getString(3));
        objAdministrador.setNombre(result.getString(4));
        objAdministrador.setApellidoPaterno(result.getString(5));
        objAdministrador.setApellidoMaterno(result.getString(6));
        objAdministrador.setCorreo(result.getString(7));
        objAdministrador.setCargo(result.getString(8));
        return objAdministrador;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Administrador obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setLong(i++, obj.getId());
        statement.setString(i++, obj.getMatricula());
        statement.setString(i++, obj.getContrasenia());
        statement.setString(i++, obj.getNombre());
        statement.setString(i++, obj.getApellidoPaterno());
        statement.setString(i++, obj.getApellidoMaterno());
        statement.setString(i++, obj.getCorreo());
        statement.setString(i++, obj.getCargo());
    }
}
