package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.entity.Alumno;
import com.assistuteam.assistu.model.entity.Usuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioAlumno extends Repositorio<Alumno> {
    public RepositorioAlumno() throws Exception {
        super("Alumnos", "alumno", 5);
    }

    @Override
    protected Alumno mappingObject(ResultSet result) throws Exception {
        Alumno objAlumno = new Alumno();
        objAlumno.setId(result.getInt(1));
        objAlumno.setCuatrimestre(result.getInt(2));
        objAlumno.setGrupo(result.getString(3));
        objAlumno.setCarrera(result.getString(4));
        int idUsuario = result.getInt(5);

        // Datos del Usuario Alumno
        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Usuario usuario = repoUsuario.leer(idUsuario);
        if (usuario != null) {
            objAlumno.setMatricula(usuario.getMatricula());
            objAlumno.setNombre(usuario.getNombre());
            objAlumno.setApellidoPaterno(usuario.getApellidoPaterno());
            objAlumno.setApellidoMaterno(usuario.getApellidoMaterno());
            objAlumno.setCorreo(usuario.getCorreo());
        }
        return objAlumno;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Alumno obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId()); // id_alumno
        statement.setInt(i++, obj.getCuatrimestre());
        statement.setString(i++, obj.getGrupo());
        statement.setString(i++, obj.getCarrera());
        statement.setInt(i++, obj.getId());
    }
}
