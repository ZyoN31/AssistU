package com.assistuteam.assistu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.assistuteam.assistu.model.entity.Usuario;
import com.assistuteam.assistu.view.util.FramePanelBase;

/** @author assistu_team **/

@SuppressWarnings("all")
public class FrameAlumno extends FramePanelBase {
    //Yo lo puse 
     // Guarda el usuario actual
    private final Usuario usuario;

    // Nuevo constructor que recibe Usuario
    public FrameAlumno(Usuario usuario) {
        this.usuario = usuario;
        setTitle("AssistU - Alumno " + usuario.getNombre());
        configurarAcciones();
        setVisible(true);
    }

    @Override
    protected String getDashboardTitle() {
        return "Alumno";
    }

    @Override
    protected String getUserRole() {
        return "Alumno";
    }

    @Override
    protected String getUserName() {
        return usuario != null ? usuario.getNombre() : "Sin Nombre";
    }

    @Override
    protected String getUserMatricula() {
       return usuario != null ? usuario.getMatricula() : "Sin Matrícula";
    }

    @Override
    protected JButton[] getSideButtons() {
        botones = new JButton[4];
        botones[0] = setButton("Historial de Recursamientos", 18, 350, 40);
        botones[1] = setButton("Solicitar Recursamiento", 18, 350, 40);
        botones[2] = setButton("Bandeja", 18, 350, 40);
        botones[3] = setButton("Abandonar Sesión", 18, 350, 40);
        return botones;
    }

    private void configurarAcciones() {
        botones[0].addActionListener(e -> mostrarHistorialRecursamientos());

        botones[1].addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí se solicita un recursamiento."));
       botones[2].addActionListener(e -> mostrarBandeja());
        botones[3].addActionListener(e -> {
            this.dispose();
            new FramePrincipal();
        });
    }
     // Bandeja de mensajes con fondo temático y bordes redondeados
    private void mostrarBandeja() {
    String[][] mensajes = {
        // tipo, remitente, asunto, fecha, mensaje
        {"success", "Sistema", "Solicitud de recursamiento aprobada", "08/08/2025 09:15", "Se ha aprobado la solicitud de recursamiento para el alumno: Juan Pérez, materia: Cálculo Integral."},
        {"info", "Coordinación", "Nuevo grupo asignado", "07/08/2025 16:30", "Se te ha asignado el grupo 3° \"B\" para Programación Orientada a Objetos."},
        {"alert", "Profesor", "Revisar matriz de secciones", "07/08/2025 09:00", "Recuerda revisar y firmar las matrices antes del viernes."}
    };

    JDialog dialog = new JDialog(this, "Bandeja", true);
    dialog.setSize(540, 370);
    dialog.setLocationRelativeTo(this);
    dialog.setUndecorated(true);

    JPanel fondo = crearPanelFlotante(520, 350, 4, 5, 25, 1f, 32, 32);
    fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));

    // Barra superior con título y X para cerrar
    JPanel barraSuperior = new JPanel(new BorderLayout());
    barraSuperior.setOpaque(false);

    JLabel lblTitulo = setLabel("Bandeja de Mensajes", 22, 1, 'C');
    lblTitulo.setForeground(UIManager.getColor("bankya.color"));
    barraSuperior.add(lblTitulo, BorderLayout.CENTER);

    JLabel lblCerrar = new JLabel("\u2716"); // Unicode X
    lblCerrar.setFont(lblCerrar.getFont().deriveFont(Font.BOLD, 22f));
    lblCerrar.setForeground(new Color(210, 210, 210));
    lblCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    lblCerrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
    lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            dialog.dispose();
        }
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            lblCerrar.setForeground(Color.RED);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            lblCerrar.setForeground(new Color(210, 210, 210));
        }
    });
    barraSuperior.add(lblCerrar, BorderLayout.EAST);

    fondo.add(barraSuperior);
    fondo.add(Box.createRigidArea(new Dimension(0, 4)));

    for (String[] mensaje : mensajes) {
        String tipo = mensaje[0];
        Color barraColor;
        // Misma lógica de color que tus tarjetas principales
        switch (tipo) {
            case "success" -> barraColor = new Color(37, 87, 66); // Verde
            case "alert" -> barraColor = new Color(110, 23, 44); // Rojo
            default -> barraColor = new Color(110, 105, 77); // Beige/gris
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

        // Letras claras para contraste
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

    dialog.setContentPane(fondo);
    dialog.setBackground(new Color(0,0,0,0));
    dialog.setVisible(true);
}



    private void mostrarHistorialRecursamientos() {
        // Simulación: puedes cambiar esto a consulta a base de datos después
        String[] materiasRecursando = {"Programación Orientada a Objetos", "Electrónica Básica"};

        JDialog dialog = new JDialog(this, "Historial de Recursamientos", true);
        dialog.setSize(420, 260);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(22, 22, 29)); // fondo oscuro

        JLabel lblTitulo = setLabel("Historial de Recursamientos", 22, 1, 'C');
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(lblTitulo);

        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        if (materiasRecursando.length == 0) {
            JLabel lbl = setLabel("No estás recursando ninguna materia.", 16, 3, 'C');
            lbl.setForeground(Color.WHITE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lbl);
        } else {
            for (String mat : materiasRecursando) {
                JLabel lbl = setLabel("• " + mat, 18, 3, 'L');
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
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(crearTarjetaAlumno("Programación Orientada a Objetos", "Profesor. Michael Doe", "10:00 am – 14:00 pm", 1));
        panel.add(crearTarjetaAlumno("Cálculo Integral", "Profesor No Disponible", "No Disponible", 2));
        panel.add(crearTarjetaAlumno("Sin Materia", "", "", 3));
        panel.add(panelSeleccionMateriaHorario());
        return panel;
    }

    private JPanel crearTarjetaAlumno(String titulo, String profesor, String horario, int colorType) {
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

        JLabel lblTitulo = setLabel(titulo, 22, 1, 'L');
        contenido.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 20, 10, 10);
        JLabel lblProf = setLabel(profesor, 16, 3, 'L');
        contenido.add(lblProf, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(8, 40, 8, 30);
        String horarioTexto = (horario.isEmpty() ? "" : "Horario. " + horario);
        JLabel lblHorario = setLabel(horarioTexto, 16, 2, 'L');
        contenido.add(lblHorario, gbc);

        tarjeta.add(colorBar, BorderLayout.WEST);
        tarjeta.add(contenido, BorderLayout.CENTER);

        return tarjeta;
    }

    // Panel reutilizable de selección de materia y horario (opcional)
    private JPanel panelSeleccionMateriaHorario() {
        String[] materias = {"Selecciona una materia...", "Programación Orientada a Objetos", "Cálculo Integral", "Electrónica Básica"};
        Map<String, String[]> horariosPorMateria = new java.util.HashMap<>();
        horariosPorMateria.put("Programación Orientada a Objetos", new String[]{"10:00 am – 14:00 pm", "12:00 pm – 16:00 pm"});
        horariosPorMateria.put("Cálculo Integral", new String[]{"07:00 am – 11:00 am"});
        horariosPorMateria.put("Electrónica Básica", new String[]{"09:00 am – 12:00 pm", "04:00 pm – 07:00 pm"});

        return crearPanelSeleccionMateriaHorario(
            materias,
            horariosPorMateria,
            e -> {
                String[] seleccion = e.getActionCommand().split(",");
                String materia = seleccion[0];
                String horario = seleccion.length > 1 ? seleccion[1] : "";
                if (materia.equals("Selecciona una materia...") || horario.equals("Selecciona un horario...") || horario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor selecciona una materia y un horario.");
                } else {
                    JOptionPane.showMessageDialog(this, "Materia: " + materia + "\nHorario: " + horario);
                }
            }
        );
    }
}


