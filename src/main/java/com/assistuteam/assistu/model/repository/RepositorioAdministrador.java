package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Administrador;
import com.assistuteam.assistu.model.entity.Usuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioAdministrador extends Repositorio<Administrador> {
    public RepositorioAdministrador() throws Exception {
        super("Administradores", "administrador", 3);
    }

    @Override
    protected Administrador mappingObject(ResultSet result) throws Exception {
        Administrador objAdministrador = new Administrador();
        objAdministrador.setId(result.getInt(1));
        objAdministrador.setCargo(result.getString(2));
        int idUsuario = result.getInt(3);

        // Datos del Usuario Administrador
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Usuario usuario = repoUsuario.leer(idUsuario);
        if (usuario != null) {
            objAdministrador.setMatricula(usuario.getMatricula());
            objAdministrador.setNombre(usuario.getNombre());
            objAdministrador.setApellidoPaterno(usuario.getApellidoPaterno());
            objAdministrador.setApellidoMaterno(usuario.getApellidoMaterno());
            objAdministrador.setCorreo(usuario.getCorreo());
        }
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
