package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Docente;
import com.assistuteam.assistu.model.entity.Usuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioDocente extends Repositorio<Docente> {
    public RepositorioDocente() throws Exception {
        super("Docentes", "docente", 4);
    }

    @Override
    protected Docente mappingObject(ResultSet result) throws Exception {
        Docente objDocente = new Docente();
        objDocente.setId(result.getInt(1));
        objDocente.setCargo(result.getString(2));
        objDocente.setHorario(result.getString(3));
        int idUsuario = result.getInt(4);

        // Datos del Usuario Docente
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Usuario usuario = repoUsuario.leer(idUsuario);
        if (usuario != null) {
            objDocente.setMatricula(usuario.getMatricula());
            objDocente.setNombre(usuario.getNombre());
            objDocente.setApellidoPaterno(usuario.getApellidoPaterno());
            objDocente.setApellidoMaterno(usuario.getApellidoMaterno());
            objDocente.setCorreo(usuario.getCorreo());
        }
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
