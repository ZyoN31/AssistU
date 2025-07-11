package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Usuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioUsuario extends Repositorio<Usuario> {
    public RepositorioUsuario() throws Exception {
        super("Usuarios", "usuario", 8);
    }

    @Override
    protected Usuario mappingObject(ResultSet result) throws Exception {
        Usuario objUsuario = new Usuario();
        objUsuario.setId(result.getInt(1)); // id_usuario
        objUsuario.setMatricula(result.getString(2));
        objUsuario.setContrasenia(result.getString(3));
        objUsuario.setNombre(result.getString(4));
        objUsuario.setApellidoPaterno(result.getString(5));
        objUsuario.setApellidoMaterno(result.getString(6));
        objUsuario.setCorreo(result.getString(7));
        objUsuario.setTipoUsuario(result.getString(8));
        return objUsuario;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Usuario obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId());
        statement.setString(i++, obj.getMatricula());
        statement.setString(i++, obj.getContrasenia());
        statement.setString(i++, obj.getNombre());
        statement.setString(i++, obj.getApellidoPaterno());
        statement.setString(i++, obj.getApellidoMaterno());
        statement.setString(i++, obj.getCorreo());
        statement.setString(i++, obj.getTipoUsuario());
    }
}
