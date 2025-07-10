package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assistuteam.assistu.model.Conexion;
import com.assistuteam.assistu.model.entity.Usuario;

/** @author assistu_team **/

public abstract class Repositorio <T extends Usuario> {
    private Conexion conexion;
    private PreparedStatement preparedStatement;

    protected String queryCreate; // INSERT INTO table VALUES(?,...,?)
    protected String queryRead;   // SELECT * FROM table WHERE id IN = ?
    protected String queryUpdate; // REPLACE INTO table VALUES(?,...,?)
    protected String queryDelete; // DELETE FROM table WHERE id IN = ?

    protected abstract T mappingObject(ResultSet result) throws Exception;
    protected abstract void setStatementParameters(PreparedStatement statement, T obj) throws Exception;

    public Repositorio(String table, long parameters) throws Exception {
        try {
            conexion = Conexion.obtenerInstancia();
            iniciarQuerys(table, parameters);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() +
            "\nIn Class:\t" + this.getClass().getName() +
            "\nIn Method:\t Repositorio();");
            throw e;
        }
    }

    private void iniciarQuerys(String table, long parameters) throws Exception {
        // Consulta para crear un nuevo registro
        queryCreate = "INSERT INTO " + table + " VALUES(NULL"; // Reemplazar ?,...,? con los campos necesarios
        for (long i = 0; i < parameters-1; i++) {
            queryCreate += ",?";
        }
        queryCreate += ")";

        // Consulta para leer un registro por ID
        queryRead = "SELECT * FROM " + table + " WHERE id IN (?)"; // Reemplazar ? con el campo id

        // Consulta para actualizar un registro
        queryUpdate = "REPLACE INTO " + table + " VALUES("; // Reemplazar ?,...,? con los campos necesarios
        for (long i = 0; i < parameters; i++) {
            queryUpdate += ",?";
        }
        queryUpdate = queryUpdate.replace("(,?", "(?");
        queryUpdate += ")";

        // Consulta para eliminar un registro por ID
        queryDelete = "DELETE FROM " + table + " WHERE id IN (?)"; // Reemplazar ? con el campo id
    }

    public boolean crear(T obj) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryCreate);
            long result = preparedStatement.executeUpdate();
            return true; // Retornar true si se crea correctamente, false en caso contrario
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t create();");
            throw e;
        }
    }

    public boolean leer(long id) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryRead);
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            T obj = null;
            while (result.next()) {
                obj = mappingObject(result);
            }
            return true; // Retornar true si se crea correctamente, false en caso contrario
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t read();");
            throw e;
        }
    }

    public boolean actualizar(T obj) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryUpdate);
            long result = preparedStatement.executeUpdate();
            return true; // Retornar true si se actualiza correctamente, false en caso contrario
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t update();");
            throw e;
        }
    }

    public boolean borrar(long id) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryDelete);
            preparedStatement.setLong(1, id);
            long result = preparedStatement.executeUpdate();
            return true; // Retornar true si se elimina correctamente, false en caso contrario
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t delete();");
            throw e;
        }
    }
}
