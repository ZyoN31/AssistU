package com.assistuteam.assistu.view.dev;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.assistuteam.assistu.model.Conexion;

/**
 * Panel de administración y sincronización entre SQLite y MariaDB.
 * Permite visualizar, comparar y sincronizar tablas y datos de ambas bases de datos.
 * @author assistu_team
 */
public class SyncViz extends JFrame {

    private JComboBox<String> comboDB;
    private JComboBox<String> comboTable;
    private JButton btnRefreshTables, btnViewStructure, btnSyncToMaria, btnSyncToSQLite, btnSyncAllToMaria, btnSyncAllToSQLite;
    private JTable tableData;
    private JTextArea areaLog;
    private DefaultTableModel tableModel;

    private Conexion connSQLite, connMaria;
    private final String[] TABLES = {"Usuarios", "Administradores", "Docentes", "Alumnos", "Recursamientos", "Inscripciones"};
    private final String[] DBS = {"SQLite", "MariaDB"};

    // Orden para sincronización global (dependencias de FK)
    private final String[] BORRAR_ORDER = {"Inscripciones", "Recursamientos", "Alumnos", "Docentes", "Administradores", "Usuarios"};
    private final String[] INSERT_ORDER = {"Usuarios", "Administradores", "Docentes", "Alumnos", "Recursamientos", "Inscripciones"};

    public SyncViz() {
        setTitle("AssistU - Panel Admin/Sync BD (SQLite <-> MariaDB)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            connSQLite = new Conexion(Conexion.SQLite);
            connMaria = new Conexion(Conexion.MariaDB);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar BD: " + e.getMessage());
            return;
        }

        buildUI();
        loadTables();

        setVisible(true);
    }

    private void buildUI() {
        // Panel izquierdo: selector de base y tabla
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setPreferredSize(new Dimension(220, getHeight()));

        comboDB = new JComboBox<>(DBS);
        comboTable = new JComboBox<>(TABLES);
        btnRefreshTables = new JButton("Refrescar");
        panelLeft.add(new JLabel("Base de datos:"));
        panelLeft.add(comboDB);
        panelLeft.add(Box.createVerticalStrut(12));
        panelLeft.add(new JLabel("Tabla:"));
        panelLeft.add(comboTable);
        panelLeft.add(Box.createVerticalStrut(12));
        panelLeft.add(btnRefreshTables);

        // Panel superior: acciones globales de sincronización
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnSyncAllToMaria = new JButton("Sincronizar TODO: SQLite → MariaDB");
        btnSyncAllToSQLite = new JButton("Sincronizar TODO: MariaDB → SQLite");
        panelTop.add(btnSyncAllToMaria);
        panelTop.add(btnSyncAllToSQLite);

        // Panel inferior: log/resultado
        areaLog = new JTextArea(6, 80);
        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);

        // Panel central: tabla de datos + acciones tabla
        JPanel panelCenter = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableData = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(tableData);

        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewStructure = new JButton("Ver estructura");
        btnSyncToMaria = new JButton("Sync: Esta tabla → MariaDB");
        btnSyncToSQLite = new JButton("Sync: Esta tabla → SQLite");
        panelActions.add(btnViewStructure);
        panelActions.add(btnSyncToMaria);
        panelActions.add(btnSyncToSQLite);

        panelCenter.add(scrollTable, BorderLayout.CENTER);
        panelCenter.add(panelActions, BorderLayout.SOUTH);

        add(panelLeft, BorderLayout.WEST);
        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(scrollLog, BorderLayout.SOUTH);

        // Acciones
        comboDB.addActionListener(e -> loadTableData());
        comboTable.addActionListener(e -> loadTableData());
        btnRefreshTables.addActionListener(e -> loadTables());
        btnViewStructure.addActionListener(e -> viewTableStructure());
        btnSyncToMaria.addActionListener(e -> syncTable("SQLite", "MariaDB"));
        btnSyncToSQLite.addActionListener(e -> syncTable("MariaDB", "SQLite"));
        btnSyncAllToMaria.addActionListener(e -> syncAllTables("SQLite", "MariaDB"));
        btnSyncAllToSQLite.addActionListener(e -> syncAllTables("MariaDB", "SQLite"));
    }

    // Carga la lista de tablas
    private void loadTables() {
        comboTable.setModel(new DefaultComboBoxModel<>(TABLES));
        loadTableData();
    }

    // Carga los datos de la tabla seleccionada en el JTable
    private void loadTableData() {
        String dbSelected = (String) comboDB.getSelectedItem();
        String tableSelected = (String) comboTable.getSelectedItem();
        if (dbSelected == null || tableSelected == null) return;

        Connection conn = dbSelected.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        if (conn == null) return;

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM " + tableSelected);
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            for (int i = 1; i <= cols; i++) {
                tableModel.addColumn(meta.getColumnName(i));
            }
            while (rs.next()) {
                Object[] row = new Object[cols];
                for (int i = 1; i <= cols; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }
            areaLog.append("Cargados datos de tabla [" + tableSelected + "] en [" + dbSelected + "]\n");
        } catch (Exception ex) {
            areaLog.append("Error al cargar datos: " + ex.getMessage() + "\n");
        }
    }

    // Muestra estructura de la tabla seleccionada
    private void viewTableStructure() {
        String dbSelected = (String) comboDB.getSelectedItem();
        String tableSelected = (String) comboTable.getSelectedItem();
        Connection conn = dbSelected.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        if (conn == null) return;

        try (Statement st = conn.createStatement()) {
            ResultSet rs;
            if (dbSelected.equals("SQLite")) {
                rs = st.executeQuery("PRAGMA table_info(" + tableSelected + ")");
                StringBuilder structure = new StringBuilder("Estructura de " + tableSelected + ":\n");
                while (rs.next()) {
                    structure.append(rs.getString("name")).append(" ")
                             .append(rs.getString("type")).append(" [PK: ")
                             .append(rs.getInt("pk")).append("]\n");
                }
                JOptionPane.showMessageDialog(this, structure.toString());
            } else { // MariaDB
                rs = st.executeQuery("DESCRIBE " + tableSelected);
                StringBuilder structure = new StringBuilder("Estructura de " + tableSelected + ":\n");
                while (rs.next()) {
                    structure.append(rs.getString("Field")).append(" ")
                             .append(rs.getString("Type")).append(" [Key: ")
                             .append(rs.getString("Key")).append("]\n");
                }
                JOptionPane.showMessageDialog(this, structure.toString());
            }
        } catch (Exception ex) {
            areaLog.append("Error al consultar estructura: " + ex.getMessage() + "\n");
        }
    }

    // Sincroniza una tabla de origen a destino (elimina todo y copia todo)
    private void syncTable(String from, String to) {
        String tableSelected = (String) comboTable.getSelectedItem();
        if (tableSelected == null) return;

        Connection connFrom = from.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        Connection connTo = to.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        if (connFrom == null || connTo == null) return;

        try {
            // Si destino es MariaDB, desactiva foreign_key_checks
            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 0;");
                st.close();
            }

            // Leer todo de origen
            Statement stFrom = connFrom.createStatement();
            ResultSet rsFrom = stFrom.executeQuery("SELECT * FROM " + tableSelected);
            ResultSetMetaData meta = rsFrom.getMetaData();
            int cols = meta.getColumnCount();

            // Eliminar todo en destino
            Statement stTo = connTo.createStatement();
            stTo.executeUpdate("DELETE FROM " + tableSelected);

            // Insertar cada registro
            StringBuilder insertSQL = new StringBuilder("INSERT INTO " + tableSelected + " VALUES (");
            for (int i = 0; i < cols; i++) insertSQL.append(i == 0 ? "?" : ",?");
            insertSQL.append(")");
            PreparedStatement psInsert = connTo.prepareStatement(insertSQL.toString());

            int count = 0;
            while (rsFrom.next()) {
                for (int i = 1; i <= cols; i++) {
                    psInsert.setObject(i, rsFrom.getObject(i));
                }
                psInsert.executeUpdate();
                count++;
            }

            // Si destino es MariaDB, reactiva foreign_key_checks
            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 1;");
                st.close();
            }

            areaLog.append("Sincronizados " + count + " registros de [" + tableSelected + "] (" + from + " → " + to + ")\n");
            loadTableData();
        } catch (Exception ex) {
            areaLog.append("Error al sincronizar tabla [" + tableSelected + "]: " + ex.getMessage() + "\n");
        }
    }

    // Sincroniza todas las tablas de origen a destino respetando dependencias de FK
    private void syncAllTables(String from, String to) {
        Connection connTo = to.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        if (connTo == null) return;

        try {
            // Si destino es MariaDB, desactiva foreign_key_checks
            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 0;");
                st.close();
            }

            // Borrar en orden de dependencias (hijas primero)
            for (String table : BORRAR_ORDER) {
                try {
                    Statement stTo = connTo.createStatement();
                    stTo.executeUpdate("DELETE FROM " + table);
                    stTo.close();
                    areaLog.append("Borrada tabla [" + table + "] en [" + to + "]\n");
                } catch (Exception ex) {
                    areaLog.append("Error al borrar [" + table + "] en [" + to + "]: " + ex.getMessage() + "\n");
                }
            }

            // Insertar en orden de dependencias (padres primero)
            for (String table : INSERT_ORDER) {
                Connection connFrom = from.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
                if (connFrom == null) continue;
                try {
                    Statement stFrom = connFrom.createStatement();
                    ResultSet rsFrom = stFrom.executeQuery("SELECT * FROM " + table);
                    ResultSetMetaData meta = rsFrom.getMetaData();
                    int cols = meta.getColumnCount();

                    StringBuilder insertSQL = new StringBuilder("INSERT INTO " + table + " VALUES (");
                    for (int i = 0; i < cols; i++) insertSQL.append(i == 0 ? "?" : ",?");
                    insertSQL.append(")");
                    PreparedStatement psInsert = connTo.prepareStatement(insertSQL.toString());

                    int count = 0;
                    while (rsFrom.next()) {
                        for (int i = 1; i <= cols; i++) {
                            psInsert.setObject(i, rsFrom.getObject(i));
                        }
                        psInsert.executeUpdate();
                        count++;
                    }
                    psInsert.close();
                    stFrom.close();
                    areaLog.append("Sincronizados " + count + " registros de [" + table + "] (" + from + " → " + to + ")\n");
                } catch (Exception ex) {
                    areaLog.append("Error al insertar [" + table + "] en [" + to + "]: " + ex.getMessage() + "\n");
                }
            }

            // Si destino es MariaDB, reactiva foreign_key_checks
            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 1;");
                st.close();
            }

            areaLog.append("Sincronización global completada (" + from + " → " + to + ")\n");
            loadTableData();
        } catch (Exception ex) {
            areaLog.append("Error global de sincronización: " + ex.getMessage() + "\n");
        }
    }

    // Helper: conecta a la base y maneja error
    private Connection safeConnect(Conexion c) {
        try {
            return c.conectar();
        } catch (Exception ex) {
            areaLog.append("Error de conexión: " + ex.getMessage() + "\n");
            return null;
        }
    }

    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SyncViz::new);
    }
    */
}