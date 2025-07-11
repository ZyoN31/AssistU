package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Recursamiento;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioRecursamiento extends Repositorio<Recursamiento> {
    public RepositorioRecursamiento() throws Exception {
        super("Recursamientos", Recursamiento.class.getName(), 6);
    }

    @Override
    protected Recursamiento mappingObject(ResultSet result) throws Exception {
        Recursamiento obj = new Recursamiento();
        obj.setId(result.getInt(1));
        obj.setMateria(result.getString(2));
        obj.setGrupo(result.getString(3));
        obj.setHorario(result.getString(4));
        // Mapear administrador y docente usando sus repositorios
        int idAdministrador = result.getInt(5);
        int idDocente = result.getInt(6);
        RepositorioAdministrador repoAdministrador = new RepositorioAdministrador();
        RepositorioDocente repoDocente = new RepositorioDocente();
        obj.setAdministrador(repoAdministrador.leer(idAdministrador));
        obj.setDocente(repoDocente.leer(idDocente));
        return obj;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Recursamiento obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId());
        statement.setString(i++, obj.getMateria());
        statement.setString(i++, obj.getGrupo());
        statement.setString(i++, obj.getHorario());
        // Si tienes los IDs de administrador y docente, agrégalos aquí
        statement.setInt(i++, obj.getAdministrador().getId());
        statement.setInt(i++, obj.getDocente().getId());
    }
}
