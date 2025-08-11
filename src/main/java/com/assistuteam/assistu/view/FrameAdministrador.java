package com.assistuteam.assistu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.assistuteam.assistu.controller.ControladorAdministrador;
import com.assistuteam.assistu.model.entity.Usuario;
import com.assistuteam.assistu.view.util.FramePanelBase;

@SuppressWarnings("all")
public class FrameAdministrador extends FramePanelBase {

    private final Usuario usuario;
    private ControladorAdministrador controladorAdministrador;

    public FrameAdministrador(Usuario usuario) {
        this.usuario = usuario;
        setTitle("AssistU - Administrador " + usuario.getNombre());

        try {
            this.controladorAdministrador = new ControladorAdministrador();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar el controlador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        configurarAcciones();
        setVisible(true);
    }


    @Override
    protected String getDashboardTitle() {
        return "Panel Administrador";
    }

    @Override
    protected String getUserRole() {
        return "Administrador";
    }

    @Override
    protected String getUserName() {
        return usuario != null ? usuario.getNombre() + " " + usuario.getApellidoPaterno() : "Nombre de Usuario";
    }

    @Override
    protected String getUserMatricula() {
        return usuario != null ? usuario.getMatricula() : "Matrícula";
    }

    @Override
    protected JButton[] getSideButtons() {
        botones = new JButton[5];
        botones[0] = setButton("Asignaciones", 18, 350, 40);
        botones[1] = setButton("Solicitudes de recursamiento", 18, 350, 40);
        botones[2] = setButton("Grupos de recursamiento", 18, 350, 40);
        botones[3] = setButton("Bandeja", 18, 350, 40);
        botones[4] = setButton("Abandonar Sesión", 18, 350, 40);
        return botones;
    }

    private void configurarAcciones() {
        botones[0].addActionListener(e -> mostrarAsignaciones());
        botones[1].addActionListener(e -> mostrarSolicitudes());
        botones[2].addActionListener(e -> mostrarGruposRecursamiento());
        botones[3].addActionListener(e -> mostrarBandeja());
        botones[4].addActionListener(e -> {
            this.dispose();
            new FramePrincipal();
        });
    }

    private void mostrarBandeja() {
        String[][] mensajes = {
            {"success", "Sistema", "Nueva solicitud aprobada", "08/08/2025 10:15", "La solicitud para recursar Cálculo Integral ha sido aprobada."},
            {"info", "Docente", "Asignación de grupo", "07/08/2025 15:10", "Se ha asignado el grupo 3C a la materia Programación Orientada a Objetos."},
            {"alert", "Coordinación", "Pendientes", "06/08/2025 09:00", "Faltan por revisar 5 solicitudes de recursamiento."}
        };

        JDialog dialog = new JDialog(this, "Bandeja", true);
        dialog.setSize(540, 370);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel fondo = crearPanelFlotante(520, 350, 4, 5, 25, 1f, 32, 32);
        fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));

        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setOpaque(false);

        JLabel lblTitulo = setLabel("Bandeja de Mensajes", 22, 1, 'C');
        lblTitulo.setForeground(UIManager.getColor("bankya.color"));
        barraSuperior.add(lblTitulo, BorderLayout.CENTER);

        fondo.add(barraSuperior);
        fondo.add(Box.createRigidArea(new Dimension(0, 4)));

        for (String[] mensaje : mensajes) {
            String tipo = mensaje[0];
            Color barraColor;
            switch (tipo) {
                case "success" -> barraColor = new Color(37, 87, 66);
                case "alert" -> barraColor = new Color(110, 23, 44);
                default -> barraColor = new Color(110, 105, 77);
            }

            JPanel tarjeta = new JPanel(new BorderLayout());
            tarjeta.setOpaque(false);
            tarjeta.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
            tarjeta.setMaximumSize(new Dimension(470, 95));
            tarjeta.setPreferredSize(new Dimension(470, 85));

            JPanel barra = new JPanel();
            barra.setPreferredSize(new Dimension(10, 70));
            barra.setBackground(barraColor);

            JPanel contenido = new JPanel();
            contenido.setOpaque(false);
            contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
            contenido.setBorder(BorderFactory.createEmptyBorder(7, 15, 7, 10));

            JLabel lblRemitente = setLabel("De: " + mensaje[1], 16, 1, 'L');
            lblRemitente.setForeground(UIManager.getColor("bankya.color"));
            JLabel lblAsunto = setLabel("Asunto: " + mensaje[2], 16, 1, 'L');
            lblAsunto.setForeground(UIManager.getColor("bankya.color"));
            JLabel lblFecha = setLabel("Fecha: " + mensaje[3], 13, 3, 'L');
            lblFecha.setForeground(UIManager.getColor("keuni.color"));
            JLabel lblMensaje = setLabel(mensaje[4], 15, 3, 'L');
            lblMensaje.setForeground(UIManager.getColor("bankya.color"));

            contenido.add(lblRemitente);
            contenido.add(lblAsunto);
            contenido.add(lblFecha);
            contenido.add(lblMensaje);

            tarjeta.add(barra, BorderLayout.WEST);
            tarjeta.add(contenido, BorderLayout.CENTER);

            fondo.add(tarjeta);
            fondo.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        fondo.add(Box.createVerticalGlue());

        JButton btnCerrar = setButton("Cerrar", 16, 120, 30);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(ev -> dialog.dispose());
        fondo.add(btnCerrar);
        fondo.add(Box.createRigidArea(new Dimension(0, 10)));

        dialog.setContentPane(fondo);
        dialog.setBackground(new Color(0,0,0,0));
        dialog.setVisible(true);
    }

    private void mostrarAsignaciones() {
        JDialog dialog = new JDialog(this, "Asignaciones", true);
        dialog.setSize(520, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel fondo = crearPanelFlotante(500, 300, 4, 5, 25, 1f, 32, 32);
        fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));

        JLabel lblTitulo = setLabel("Asignaciones", 22, 1, 'C');
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(UIManager.getColor("bankya.color"));
        fondo.add(Box.createRigidArea(new Dimension(0, 14)));
        fondo.add(lblTitulo);
        fondo.add(Box.createRigidArea(new Dimension(0, 18)));

        String[][] asignaciones = {
            {"Sebastian Sosa", "POO", "B", "Lunes", "10:00-14:00"},
            {"Samuel Arroyo", "Calculo Integral", "3C", "Martes", "08:00-10:00"}
        };

        for (String[] asignacion : asignaciones) {
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
            tarjeta.setOpaque(false);
            tarjeta.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
            tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblMaestro = setLabel("Maestro: " + asignacion[0], 18, 1, 'L');
            lblMaestro.setForeground(UIManager.getColor("bankya.color"));
            JLabel lblMateria = setLabel("Materia: " + asignacion[1], 16, 3, 'L');
            lblMateria.setForeground(UIManager.getColor("bankya.color"));
            JLabel lblGrupo = setLabel("Grupo: " + asignacion[2], 16, 3, 'L');
            lblGrupo.setForeground(UIManager.getColor("keuni.color"));
            JLabel lblDia = setLabel("Día: " + asignacion[3], 16, 3, 'L');
            lblDia.setForeground(UIManager.getColor("keuni.color"));
            JLabel lblHora = setLabel("Hora: " + asignacion[4], 16, 3, 'L');
            lblHora.setForeground(UIManager.getColor("keuni.color"));

            tarjeta.add(lblMaestro);
            tarjeta.add(lblMateria);
            tarjeta.add(lblGrupo);
            tarjeta.add(lblDia);
            tarjeta.add(lblHora);

            tarjeta.setMaximumSize(new Dimension(430, 110));
            tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

            fondo.add(tarjeta);
            fondo.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        fondo.add(Box.createVerticalGlue());

        JButton btnCerrar = setButton("Cerrar", 16, 120, 30);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(ev -> dialog.dispose());
        fondo.add(btnCerrar);
        fondo.add(Box.createRigidArea(new Dimension(0, 10)));

        dialog.setContentPane(fondo);
        dialog.setBackground(new Color(0,0,0,0));
        dialog.setVisible(true);
    }

    private void mostrarSolicitudes() {
        JDialog dialog = new JDialog(this, "Solicitudes de Recursamiento", true);
        dialog.setSize(420, 260);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(22, 22, 29));

        JLabel lblTitulo = setLabel("Solicitudes de Recursamiento", 22, 1, 'C');
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(lblTitulo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        String[] solicitudes = {"Juan Pérez - Calculo Integral", "Kevin Díaz - POO"};
        if (solicitudes.length == 0) {
            JLabel lbl = setLabel("No hay solicitudes pendientes.", 16, 3, 'C');
            lbl.setForeground(Color.WHITE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lbl);
        } else {
            for (String solicitud : solicitudes) {
                JLabel lbl = setLabel("• " + solicitud, 18, 3, 'L');
                lbl.setForeground(Color.WHITE);
                lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(lbl);
                panel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        panel.add(Box.createVerticalGlue());

        JButton btnCerrar = setButton("Cerrar", 16, 120, 30);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(ev -> dialog.dispose());
        panel.add(btnCerrar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        dialog.setContentPane(panel);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private void mostrarGruposRecursamiento() {
        JDialog dialog = new JDialog(this, "Grupos de Recursamiento", true);
        dialog.setSize(420, 260);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(22, 22, 29));

        JLabel lblTitulo = setLabel("Grupos de Recursamiento", 22, 1, 'C');
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(lblTitulo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        String[] grupos = {"Grupo 3C - Calculo Integral", "Grupo 6B - POO"};
        if (grupos.length == 0) {
            JLabel lbl = setLabel("No hay grupos creados.", 16, 3, 'C');
            lbl.setForeground(Color.WHITE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lbl);
        } else {
            for (String grupo : grupos) {
                JLabel lbl = setLabel("• " + grupo, 18, 3, 'L');
                lbl.setForeground(Color.WHITE);
                lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(lbl);
                panel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        panel.add(Box.createVerticalGlue());

        JButton btnCerrar = setButton("Cerrar", 16, 120, 30);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(ev -> dialog.dispose());
        panel.add(btnCerrar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        dialog.setContentPane(panel);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    @Override
    protected JPanel buildCardsPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel panelCantidad = new JPanel(new GridBagLayout());
        panelCantidad.setOpaque(false);
        panelCantidad.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            int numeroDeAdministradores = controladorAdministrador.leerTodos().size(); // Real
            panelCantidad.add(crearTarjetaCantidad("Número de grupos:", "5"), setGridsAttributes(0, 0, 1, 1, false, 5, 5, 0, 0));
            panelCantidad.add(crearTarjetaCantidad("Número de maestros disponibles:", "15"), setGridsAttributes(1, 0, 1, 1, false, 5, 5, 0, 0));
            panelCantidad.add(crearTarjetaCantidad("Números de solicitudes:", "3"), setGridsAttributes(0, 1, 1, 1, false, 5, 5, 0, 0));
            panelCantidad.add(crearTarjetaCantidad("Número de alumnos:", "120"), setGridsAttributes(1, 1, 1, 1, false, 5, 5, 0, 0));
            panelCantidad.add(crearTarjetaCantidad("Número de materias de recursamiento:", "4"), setGridsAttributes(0, 2, 1, 1, false, 5, 5, 0, 0));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        mainPanel.add(panelCantidad);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(crearTarjetaAsignacion("Sebastian Sosa", "POO", "B", "Lunes", "10:00-14:00", 1));
        mainPanel.add(crearTarjetaAsignacion("Samuel Arroyo", "Calculo Integral", "3C", "Martes", "08:00-10:00", 2));

        return mainPanel;
    }

    private JPanel crearTarjetaCantidad(String titulo, String valor) {
        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(19, 22, 34, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(250, 70));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = setLabel(titulo, 16, 3, 'L');
        lblTitulo.setForeground(UIManager.getColor("keuni.color"));
        tarjeta.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblValor = setLabel(valor, 24, 1, 'L');
        lblValor.setForeground(UIManager.getColor("bankya.color"));
        tarjeta.add(lblValor, BorderLayout.SOUTH);

        return tarjeta;
    }

    private JPanel crearTarjetaAsignacion(String maestro, String materia, String grupo, String dia, String hora, int colorType) {
        JPanel tarjeta = new JPanel();
        tarjeta.setOpaque(false);
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setMaximumSize(new Dimension(700, 90));
        tarjeta.setPreferredSize(new Dimension(700, 90));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel colorBar = new JPanel();
        colorBar.setPreferredSize(new Dimension(16, 90));
        colorBar.setOpaque(true);
        switch (colorType) {
            case 1 -> colorBar.setBackground(new Color(37, 87, 66));
            case 2 -> colorBar.setBackground(new Color(110, 23, 44));
            case 3 -> colorBar.setBackground(new Color(110, 105, 77));
            default -> colorBar.setBackground(Color.GRAY);
        }

        JPanel contenido = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(19, 22, 34, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
            }
        };
        contenido.setOpaque(false);
        contenido.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 20, 0, 10);

        JLabel lblMaestro = setLabel(maestro, 18, 1, 'L');
        contenido.add(lblMaestro, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 20, 10, 10);
        JLabel lblMateriaGrupo = setLabel(materia + " - Grupo " + grupo, 16, 3, 'L');
        contenido.add(lblMateriaGrupo, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(8, 40, 8, 30);
        JLabel lblHorario = setLabel(dia + " " + hora, 16, 2, 'L');
        contenido.add(lblHorario, gbc);

        tarjeta.add(colorBar, BorderLayout.WEST);
        tarjeta.add(contenido, BorderLayout.CENTER);

        return tarjeta;
    }
}