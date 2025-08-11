package com.assistuteam.assistu.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** @author assistu_team **/

@SuppressWarnings("all")
public class Conexion {
    public static int SQLite = 1;
    public static int MariaDB = 2;
    private String claseControlador;
    private String servidor;
    private long puerto;
    private static String baseDatos = "assistu_db"; // Nombre de la base de datos para SQLite/MariaDB
    private String usuario;
    private String contrasenia;
    private String url;
    private static Conexion instancia;
    private Connection conexion;

    /**
     * Obtiene la instancia unica de la clase Conexion
     * @return La instancia unica de Conexion
     * @throws Exception Si ocurre un error al crear la instancia
     */
    public static Conexion obtenerInstancia() throws Exception {
        if (instancia == null) {
            instancia = new Conexion(SQLite);
        }
        return instancia;
    }

    /**
     * Constructor de la clase Conexion
     * @param tipoServidor Tipo de servidor (1 para SQLite, 2 para MariaDB)
     * @throws Exception Si el tipo de servidor no es soportado
     */
    public Conexion(int tipoServidor) throws Exception {
        switch (tipoServidor) {
            case 1 -> this.configurarSQLite();
            case 2 -> this.configurarMariaDB();
            default -> throw new Exception("Tipo de servidor no soportado Use 1 para SQLite o 2 para MariaDB");
        }
    }

    private void configurarSQLite() {
        this.claseControlador = "org.sqlite.JDBC";
        this.servidor = "";
        this.puerto = 0;
        this.usuario = "";
        this.contrasenia = "";
        this.url = "jdbc:sqlite:" + baseDatos + ".db";
    }

    private void configurarMariaDB() {
        this.claseControlador = "org.mariadb.jdbc.Driver";
        this.servidor = "localhost";
        this.puerto = 3306;
        this.usuario = "root";
        this.contrasenia = "";
        this.url = "jdbc:mariadb://" + this.servidor + ":" + this.puerto + "/" + baseDatos;
    }

    /**
     * Conecta a la base de datos
     * @return La conexion establecida
     * @throws Exception Si ocurre un error al conectar
     */
    public Connection conectar() throws Exception {
        try {
            if (this.conexion == null || this.conexion.isClosed()) {
                Class.forName(this.claseControlador);
                this.conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasenia);
            }
            return this.conexion;
        } catch (SQLException | ClassNotFoundException error) {
            System.out.println("Error al conectar a la base de datos: " + error.getMessage());
            throw error;
        }
    }

    /**
     * Desconecta de la base de datos
     * @return true si la desconexion fue exitosa, false en caso contrario
     * @throws Exception Si ocurre un error al desconectar
     */
    public boolean desconectar() throws Exception {
        try {
            if (this.conexion != null) {
                this.conexion.close();
            }
            return true;
        } catch (SQLException error) {
            System.out.println("Error: " + this.getClass().getName() + " - " + error.getMessage());
            return false;
        }
    }
}
