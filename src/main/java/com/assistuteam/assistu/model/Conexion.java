package com.assistuteam.assistu.model;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static int SQLite = 1;
   public static int MariaDB = 2;
   private String driverClass;
   private String server;
   private long port;
   private static String dataBase = "pokemonesdb.db";
   private String user;
   private String password;
   private String url;
   private static Conexion instance;
   private Connection conexion;

   public static Conexion getInstance() throws Exception {
      if (instance == null) {
         instance = new Conexion(SQLite);
      }

      return instance;
   }

   public Conexion(int typeServer) throws Exception {
      switch (typeServer) {
         case 1:
            this.obtainDataSQLite();
            break;
         case 2:
            this.obtainDataMariaDB();
            break;
         default:
            throw new Exception("Tipo de servidor no soportado. Use 1 para SQLite o 2 para MariaDB.");
      }

   }

   private void obtainDataSQLite() {
      this.driverClass = "org.sqlite.JDBC";
      this.server = "";
      this.port = 0L;
      this.user = "";
      this.password = "";
      this.url = "jdbc:sqlite:" + dataBase;
   }

   private void obtainDataMariaDB() {
      this.driverClass = "org.mariadb.jdbc.Driver";
      this.server = "localhost";
      this.port = 3306L;
      this.user = "";
      this.password = "";
      this.url = "jdbc:mariadb://" + this.server + ":" + this.port + "/" + dataBase;
   }

   public Connection connect() throws Exception {
      try {
         if (this.conexion == null || this.conexion.isClosed()) {
            Class.forName(this.driverClass);
            this.conexion = DriverManager.getConnection(this.url, this.user, this.password);
         }

         return this.conexion;
      } catch (SQLException | ClassNotFoundException var2) {
         System.out.println("Error al conectar a la base de datos: " + var2.getMessage());
         throw var2;
      }
   }

   public boolean disconnect() throws Exception {
      try {
         if (this.conexion != null) {
            this.conexion.close();
         }

         return true;
      } catch (SQLException var2) {
         PrintStream var10000 = System.out;
         String var10001 = this.getClass().getName();
         var10000.println("Error: " + var10001 + " - " + var2.getMessage());
         return false;
      }
   }
}
