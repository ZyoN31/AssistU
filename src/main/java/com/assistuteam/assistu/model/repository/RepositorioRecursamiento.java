package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Recursamiento;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioRecursamiento extends Repositorio<Recursamiento> {
    public RepositorioRecursamiento() throws Exception {
        super("Recursamientos", "recursamiento", 6);
    }

    @Override
    protected Recursamiento mappingObject(ResultSet result) throws Exception {
        Recursamiento objRecursamiento = new Recursamiento();
        objRecursamiento.setId(result.getInt(1));
        objRecursamiento.setMateria(result.getString(2));
        objRecursamiento.setGrupo(result.getString(3));
        objRecursamiento.setHorario(result.getString(4));
        
        int idAdministrador = result.getInt(5);
        int idDocente = result.getInt(6);
        RepositorioAdministrador repoAdministrador = new RepositorioAdministrador();
        RepositorioDocente repoDocente = new RepositorioDocente();
        objRecursamiento.setAdministrador(repoAdministrador.leer(idAdministrador));
        objRecursamiento.setDocente(repoDocente.leer(idDocente));
        return objRecursamiento;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Recursamiento obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId());
        statement.setString(i++, obj.getMateria());
        statement.setString(i++, obj.getGrupo());
        statement.setString(i++, obj.getHorario());
        statement.setInt(i++, obj.getAdministrador().getId());
        statement.setInt(i++, obj.getDocente().getId());
    }
}
