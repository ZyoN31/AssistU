package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Recursamiento;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioRecursamiento extends Repositorio<Recursamiento> {
    public RepositorioRecursamiento() throws Exception {
        super("Recursamientos", "recursamiento", 5); // id_recursamiento + 4 campos
    }

    @Override
    protected Recursamiento mappingObject(ResultSet result) throws Exception {
        Recursamiento recursamiento = new Recursamiento();
        recursamiento.setId(result.getInt("id_recursamiento"));
        recursamiento.setMateria(result.getString("materia"));
        recursamiento.setGrupo(result.getString("grupo"));
        recursamiento.setHorario(result.getString("horario"));
        recursamiento.setMatriculaUsuario(result.getString("matricula_usuario"));
        return recursamiento;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Recursamiento recursamiento, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, recursamiento.getId());
        statement.setString(i++, recursamiento.getMateria());
        statement.setString(i++, recursamiento.getGrupo());
        statement.setString(i++, recursamiento.getHorario());
        statement.setString(i++, recursamiento.getMatriculaUsuario());
    }
}