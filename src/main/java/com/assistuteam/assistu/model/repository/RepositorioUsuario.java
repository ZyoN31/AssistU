package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Usuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioUsuario extends Repositorio<Usuario> {
    public RepositorioUsuario() throws Exception {
        super("Usuarios", "usuario", 8); // id_usuario + 7 campos
    }

    @Override
    protected Usuario mappingObject(ResultSet result) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(result.getInt("id_usuario"));
        usuario.setMatricula(result.getString("matricula_usuario"));
        usuario.setContrasenia(result.getString("contrasenia"));
        usuario.setNombre(result.getString("nombre"));
        usuario.setApellidoPaterno(result.getString("apellido_paterno"));
        usuario.setApellidoMaterno(result.getString("apellido_materno"));
        usuario.setCorreo(result.getString("correo"));
        usuario.setIdTipoUsuario(result.getInt("id_tipo_usuario"));
        return usuario;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Usuario usuario, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, usuario.getId());
        statement.setString(i++, usuario.getMatricula());
        statement.setString(i++, usuario.getContrasenia());
        statement.setString(i++, usuario.getNombre());
        statement.setString(i++, usuario.getApellidoPaterno());
        statement.setString(i++, usuario.getApellidoMaterno());
        statement.setString(i++, usuario.getCorreo());
        statement.setInt(i++, usuario.getIdTipoUsuario());
    }
}