package com.assistuteam.assistu.model.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.assistuteam.assistu.model.entity.Inscripcion;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioInscripcion extends Repositorio<Inscripcion> {
    public RepositorioInscripcion() throws Exception {
        super("Inscripciones", "inscripcion", 5); // 5 campos en la tabla
    }

    @Override
    protected Inscripcion mappingObject(ResultSet rs) throws Exception {
        Inscripcion insc = new Inscripcion();
        insc.setMatriculaAlumno(rs.getString("matricula_alumno"));
        insc.setIdRecursamiento(rs.getInt("id_recursamiento"));
        insc.setFecha(rs.getDate("fecha").toLocalDate());
        insc.setCalificacion(rs.getFloat("calificacion"));
        insc.setEstado(rs.getString("estado"));
        // el campo id (de Entidad) lo puedes ignorar, no hay id en esta tabla
        return insc;
    }

    @Override
    protected void setStatementParameters(PreparedStatement ps, Inscripcion obj, boolean nuevo) throws Exception {
        int i = 1;
        ps.setString(i++, obj.getMatriculaAlumno());
        ps.setInt(i++, obj.getIdRecursamiento());
        ps.setDate(i++, Date.valueOf(obj.getFecha()));
        ps.setFloat(i++, obj.getCalificacion());
        ps.setString(i++, obj.getEstado());
    }

    // Operaciones específicas para PK compuesta
    public Inscripcion leer(String matriculaAlumno, int idRecursamiento) throws Exception {
        String query = "SELECT * FROM Inscripciones WHERE matricula_alumno = ? AND id_recursamiento = ?";
        try (PreparedStatement ps = conexion.conectar().prepareStatement(query)) {
            ps.setString(1, matriculaAlumno);
            ps.setInt(2, idRecursamiento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mappingObject(rs);
            }
            return null;
        }
    }

    public boolean borrar(String matriculaAlumno, int idRecursamiento) throws Exception {
        String query = "DELETE FROM Inscripciones WHERE matricula_alumno = ? AND id_recursamiento = ?";
        try (PreparedStatement ps = conexion.conectar().prepareStatement(query)) {
            ps.setString(1, matriculaAlumno);
            ps.setInt(2, idRecursamiento);
            int result = ps.executeUpdate();
            return result > 0;
        }
    }

    public List<Inscripcion> leerTodos() throws Exception {
        List<Inscripcion> lista = new LinkedList<>();
        String query = "SELECT * FROM Inscripciones";
        try (PreparedStatement ps = conexion.conectar().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mappingObject(rs));
            }
        }
        return lista;
    }
}