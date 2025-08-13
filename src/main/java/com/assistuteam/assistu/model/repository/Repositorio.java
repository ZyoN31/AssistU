package com.assistuteam.assistu.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.assistuteam.assistu.model.Conexion;
import com.assistuteam.assistu.model.entity.Entidad;

/** @author assistu_team **/

public abstract class Repositorio <T extends Entidad> {
    protected Conexion conexion;
    private PreparedStatement preparedStatement;

    private String nombreClase;
    protected String queryCreate; // INSERT INTO table VALUES(?,...,?)
    protected String queryRead;   // SELECT * FROM table WHERE id IN = ?
    protected String queryUpdate; // REPLACE INTO table VALUES(?,...,?)
    protected String queryDelete; // DELETE FROM table WHERE id IN = ?
    protected String queryReadEverything; // SELECT * FROM table

    protected abstract T mappingObject(ResultSet result) throws Exception;
    protected abstract void setStatementParameters(PreparedStatement statement, T obj, boolean nuevoObjeto) throws Exception;

    public Repositorio(String table, String nombreClase, long parameters) throws Exception {
        try {
            this.nombreClase = nombreClase;
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
        // Elimina cualquier punto al final del nombre de la tabla
        if (table.endsWith(".")) {
            table = table.substring(0, table.length() - 1);
        }

        // Consulta para crear un nuevo registro
        queryCreate = "INSERT INTO " + table + " VALUES(NULL"; // Reemplazar ?,...,? con los campos necesarios
        for (long i = 0; i < parameters-1; i++) {
            queryCreate += ",?";
        }
        queryCreate += ")";

        // Consulta para leer un registro por ID
        queryRead = "SELECT * FROM " + table + " WHERE id_" + nombreClase + " = ?";

        // Consulta para actualizar un registro
        queryUpdate = "REPLACE INTO " + table + " VALUES("; // Reemplazar ?,...,? con los campos necesarios
        for (long i = 0; i < parameters; i++) {
            queryUpdate += ",?";
        }
        queryUpdate = queryUpdate.replace("(,?", "(?");
        queryUpdate += ")";

        // Consulta para eliminar un registro por ID
        queryDelete = "DELETE FROM " + table + " WHERE id_" + nombreClase + " = ?";

        // Consulta para leer todos los registros
        queryReadEverything = "SELECT * FROM " + table;
    }

    public List<T> leerTodos() throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryReadEverything);
            ResultSet result = preparedStatement.executeQuery();
            List<T> lista = new LinkedList<>();
            T obj;
            while (result.next()) {
                obj = mappingObject(result);
                lista.add(obj);
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() +
            "\nIn Class:\t" + this.getClass().getName() +
            "\nIn Method:\t leerTodos();");
            throw e;
        }
    }

    public boolean crear(T obj) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryCreate);
            setStatementParameters(preparedStatement, obj, true);
            long result = preparedStatement.executeUpdate();
            return result >= 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t crear();");
            throw e;
        }
    }

    public T leer(long id) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryRead);
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            T obj = null;
            while (result.next()) {
                obj = mappingObject(result);
            }
            return obj;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t leer();");
            throw e;
        }
    }

    public boolean actualizar(T obj) throws Exception {
        try {
            if(obj.getId() <=0) throw new Exception("No hay id <= a 0");
            preparedStatement = conexion.conectar().prepareStatement(queryUpdate);
            setStatementParameters(preparedStatement, obj, false);
            long result = preparedStatement.executeUpdate();
            return result >= 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t actualizar();");
            throw e;
        }
    }

    public boolean borrar(long id) throws Exception {
        try {
            preparedStatement = conexion.conectar().prepareStatement(queryDelete);
            preparedStatement.setLong(1, id);
            long result = preparedStatement.executeUpdate();
            return result >= 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + 
            "\nIn Class:\t" + this.getClass().getName() + 
            "\nIn Method:\t borrar();");
            throw e;
        }
    }

    public void usuarioActivado () {
        //Generar inicio constante de sesion
    }
}