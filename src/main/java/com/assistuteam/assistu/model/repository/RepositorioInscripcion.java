package com.assistuteam.assistu.model.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.assistuteam.assistu.model.entity.Inscripcion;

/** @author assistu_team **/

@SuppressWarnings("all")
public class RepositorioInscripcion extends Repositorio<Inscripcion> {
    public RepositorioInscripcion() throws Exception {
        super("Inscripciones", "inscripcion", 5);
    }

    @Override
    protected Inscripcion mappingObject(ResultSet result) throws Exception {
        Inscripcion objInscripcion = new Inscripcion();
        
        int idAlumno = result.getInt(1);
        int idRecursamiento = result.getInt(2);
        RepositorioAlumno repoAlumno = new RepositorioAlumno();
        RepositorioRecursamiento repoRecursamiento = new RepositorioRecursamiento();
        objInscripcion.setAlumno(repoAlumno.leer(idAlumno));
        objInscripcion.setRecursamiento(repoRecursamiento.leer(idRecursamiento));

        String fechaStr = result.getString(3);
        objInscripcion.setFecha(java.time.LocalDate.parse(fechaStr));

        objInscripcion.setCalificacion(result.getFloat(4));
        objInscripcion.setEstado(result.getString(5));
        return objInscripcion;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Inscripcion obj, boolean nuevoObjeto) throws Exception {
        int i = 1;
        if (!nuevoObjeto) statement.setInt(i++, obj.getId());
        statement.setDate(i++, Date.valueOf(obj.getFecha()));
        statement.setFloat(i++, obj.getCalificacion());
        statement.setString(i++, obj.getEstado());
        statement.setInt(i++, obj.getAlumno().getId());
        statement.setInt(i++, obj.getRecursamiento().getId());
    }
    
    public Inscripcion leer(int idAlumno, int idRecursamiento) throws Exception {
        try {
            String query = "SELECT fecha, calificacion, estado, id_alumno, id_recursamiento FROM Inscripciones WHERE id_alumno = ? AND id_recursamiento = ?";
            Inscripcion obj;
            try (PreparedStatement ps = conexion.conectar().prepareStatement(query)) {
                ps.setInt(1, idAlumno);
                ps.setInt(2, idRecursamiento);
                try (ResultSet rs = ps.executeQuery()) {
                    obj = null;
                    if (rs.next()) {
                        obj = mappingObject(rs);
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName() + " in method: leer()");
            throw e;
        }
    }

    public boolean borrar(int idAlumno, int idRecursamiento) throws Exception {
        try {
            String query = "DELETE FROM Inscripciones WHERE id_alumno = ? AND id_recursamiento = ?";
            int result;
            try (PreparedStatement ps = conexion.conectar().prepareStatement(query)) {
                ps.setInt(1, idAlumno);
                ps.setInt(2, idRecursamiento);
                result = ps.executeUpdate();
            }
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage() + " in class: " + this.getClass().getName() + " in method: borrar()");
            throw e;
        }
    }
}
