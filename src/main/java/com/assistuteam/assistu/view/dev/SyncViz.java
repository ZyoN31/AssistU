package com.assistuteam.assistu.view.dev;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.assistuteam.assistu.model.Conexion;
import com.assistuteam.assistu.view.util.FrameUtilities;

/**
 * Panel Admin/Sync BD (SQLite <-> MariaDB)
 * Hereda de FrameUtilities.
 * @author assistu_team
 */
@SuppressWarnings("all")
public class SyncViz extends FrameUtilities {

    private JComboBox<String> comboDB;
    private JComboBox<String> comboTable;
    private JButton btnRefreshTables, btnViewStructure, btnSyncToMaria, btnSyncToSQLite, btnSyncAllToMaria, btnSyncAllToSQLite, btnClearLog;
    private JTable tableData;
    private JTextArea areaLog;
    private DefaultTableModel tableModel;

    private Conexion connSQLite, connMaria;
    private final String[] TABLES = {"Usuarios", "Alumnos", "Recursamientos", "Inscripciones"};
    private final String[] DBS = {"SQLite", "MariaDB"};

    // Orden para sincronización global (dependencias de FK)
    private final String[] BORRAR_ORDER = {"Inscripciones", "Recursamientos", "Alumnos", "Usuarios"};
    private final String[] INSERT_ORDER = {"Usuarios", "Alumnos", "Recursamientos", "Inscripciones"};

    public SyncViz() {
        setTitle("AssistU - Panel Admin/Sync BD (SQLite <-> MariaDB)");
        setSize(defaultWidth, defaultHeight);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setPanelFondoImg("/com/assistuteam/assistu/resources/images/background_03.png");
        panelFondo.setLayout(new GridBagLayout());

        try {
            connSQLite = new Conexion(Conexion.SQLite);
            connMaria = new Conexion(Conexion.MariaDB);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar BD: " + e.getMessage());
            return;
        }

        buildUI();
        loadTables();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        // Panel izquierdo: selector de base y tabla (color sólido)
        JPanel panelLeft = crearPanelEstatico(320, 4, 5, 0, 0.92f);
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));

        JLabel lblLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 96, 72);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLeft.add(Box.createVerticalStrut(16));
        panelLeft.add(lblLogo);
        JLabel lblTitulo = setLabel("Sync BD", 34, 1, 'C');
        lblTitulo.setForeground(UIManager.getColor("bankya.color"));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLeft.add(lblTitulo);

        panelLeft.add(Box.createVerticalStrut(24));
        JLabel lblBase = setLabel("Base de datos:", 20, 3, 'C');
        lblBase.setForeground(UIManager.getColor("bankya.color"));
        panelLeft.add(lblBase);
        comboDB = setComboBox(DBS, 18, 240, 32);
        panelLeft.add(comboDB);

        panelLeft.add(Box.createVerticalStrut(14));
        JLabel lblTabla = setLabel("Tabla:", 20, 3, 'L');
        lblTabla.setForeground(UIManager.getColor("bankya.color"));
        panelLeft.add(lblTabla);
        comboTable = setComboBox(TABLES, 18, 240, 32);
        panelLeft.add(comboTable);

        panelLeft.add(Box.createVerticalStrut(14));
        btnRefreshTables = setButton("Refrescar", 17, 220, 38);
        panelLeft.add(btnRefreshTables);

        panelLeft.add(Box.createVerticalGlue());

        // Panel superior: acciones globales (sin fondo especial)
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 16));
        panelTop.setOpaque(false);
        btnSyncAllToMaria = setButton("Sincronizar TODO: SQLite -> MariaDB", 17, 350, 40);
        btnSyncAllToSQLite = setButton("Sincronizar TODO: MariaDB -> SQLite", 17, 350, 40);
        panelTop.add(btnSyncAllToMaria);
        panelTop.add(btnSyncAllToSQLite);

        // Panel inferior: log/resultado + limpiar (sin fondo especial)
        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setOpaque(false);
        areaLog = new JTextArea(6, 80);
        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);
        panelLog.add(scrollLog, BorderLayout.CENTER);
        btnClearLog = setButton("Limpiar log", 15, 160, 32);
        JPanel panelBtnLog = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        panelBtnLog.setOpaque(false);
        panelBtnLog.add(btnClearLog);
        panelLog.add(panelBtnLog, BorderLayout.SOUTH);

        // Panel central: tabla de datos + acciones tabla
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setOpaque(false);
        tableModel = new DefaultTableModel();
        tableData = new JTable(tableModel);
        tableData.setRowHeight(28);
        JScrollPane scrollTable = new JScrollPane(tableData);

        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        panelActions.setOpaque(false);
        btnViewStructure = setButton("Ver estructura", 16, 220, 34);
        btnSyncToMaria = setButton("Sync: Esta tabla -> MariaDB", 16, 320, 34);
        btnSyncToSQLite = setButton("Sync: Esta tabla -> SQLite", 16, 320, 34);
        panelActions.add(btnViewStructure);
        panelActions.add(btnSyncToMaria);
        panelActions.add(btnSyncToSQLite);

        panelCenter.add(scrollTable, BorderLayout.CENTER);
        panelCenter.add(panelActions, BorderLayout.SOUTH);

        // Layout principal en el fondo
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.gridheight = 3;
        gbcLeft.insets = new Insets(0, 0, 0, 0);
        gbcLeft.anchor = GridBagConstraints.NORTHWEST;
        gbcLeft.fill = GridBagConstraints.VERTICAL;
        gbcLeft.weighty = 1.0;

        GridBagConstraints gbcTop = new GridBagConstraints();
        gbcTop.gridx = 1;
        gbcTop.gridy = 0;
        gbcTop.insets = new Insets(18, 0, 0, 0);
        gbcTop.anchor = GridBagConstraints.NORTHWEST;
        gbcTop.fill = GridBagConstraints.HORIZONTAL;
        gbcTop.weightx = 1.0;

        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 1;
        gbcCenter.gridy = 1;
        gbcCenter.insets = new Insets(16, 0, 0, 0);
        gbcCenter.anchor = GridBagConstraints.CENTER;
        gbcCenter.fill = GridBagConstraints.BOTH;
        gbcCenter.weighty = 1.0;

        GridBagConstraints gbcLog = new GridBagConstraints();
        gbcLog.gridx = 1;
        gbcLog.gridy = 2;
        gbcLog.insets = new Insets(16, 0, 0, 0);
        gbcLog.anchor = GridBagConstraints.SOUTHWEST;
        gbcLog.fill = GridBagConstraints.HORIZONTAL;

        panelFondo.add(panelLeft, gbcLeft);
        panelFondo.add(panelTop, gbcTop);
        panelFondo.add(panelCenter, gbcCenter);
        panelFondo.add(panelLog, gbcLog);

        setPanelFondo(panelFondo);

        // Acciones
        comboDB.addActionListener(e -> loadTableData());
        comboTable.addActionListener(e -> loadTableData());
        btnRefreshTables.addActionListener(e -> loadTables());
        btnViewStructure.addActionListener(e -> viewTableStructure());
        btnSyncToMaria.addActionListener(e -> syncTable("SQLite", "MariaDB"));
        btnSyncToSQLite.addActionListener(e -> syncTable("MariaDB", "SQLite"));
        btnSyncAllToMaria.addActionListener(e -> syncAllTables("SQLite", "MariaDB"));
        btnSyncAllToSQLite.addActionListener(e -> syncAllTables("MariaDB", "SQLite"));
        btnClearLog.addActionListener(e -> areaLog.setText(""));
    }

    private void loadTables() {
        comboTable.setModel(new DefaultComboBoxModel<>(TABLES));
        loadTableData();
    }

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

    private void syncTable(String from, String to) {
        String tableSelected = (String) comboTable.getSelectedItem();
        if (tableSelected == null) return;

        Connection connFrom = from.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        Connection connTo = to.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        if (connFrom == null || connTo == null) return;

        try {
            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 0;");
                st.close();
            }

            Statement stFrom = connFrom.createStatement();
            ResultSet rsFrom = stFrom.executeQuery("SELECT * FROM " + tableSelected);
            ResultSetMetaData meta = rsFrom.getMetaData();
            int cols = meta.getColumnCount();

            Statement stTo = connTo.createStatement();
            stTo.executeUpdate("DELETE FROM " + tableSelected);

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

            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 1;");
                st.close();
            }

            areaLog.append("Sincronizados " + count + " registros de [" + tableSelected + "] (" + from + " -> " + to + ")\n");
            loadTableData();
        } catch (Exception ex) {
            areaLog.append("Error al sincronizar tabla [" + tableSelected + "]: " + ex.getMessage() + "\n");
        }
    }

    private void syncAllTables(String from, String to) {
        Connection connTo = to.equals("SQLite") ? safeConnect(connSQLite) : safeConnect(connMaria);
        if (connTo == null) return;

        try {
            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 0;");
                st.close();
            }

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
                    areaLog.append("Sincronizados " + count + " registros de [" + table + "] (" + from + " -> " + to + ")\n");
                } catch (Exception ex) {
                    areaLog.append("Error al insertar [" + table + "] en [" + to + "]: " + ex.getMessage() + "\n");
                }
            }

            if (to.equals("MariaDB")) {
                Statement st = connTo.createStatement();
                st.execute("SET foreign_key_checks = 1;");
                st.close();
            }

            areaLog.append("Sincronización global completada (" + from + " -> " + to + ")\n");
            loadTableData();
        } catch (Exception ex) {
            areaLog.append("Error global de sincronización: " + ex.getMessage() + "\n");
        }
    }

    private Connection safeConnect(Conexion c) {
        try {
            return c.conectar();
        } catch (Exception ex) {
            areaLog.append("Error de conexión: " + ex.getMessage() + "\n");
            return null;
        }
    }
}