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
import java.util.List;
import java.util.HashMap;
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
import com.assistuteam.assistu.model.entity.Recursamiento;
import com.assistuteam.assistu.controller.ControladorRecursamiento;
import com.assistuteam.assistu.view.util.FramePanelBase;

/** @author assistu_team **/

@SuppressWarnings("all")
public class FrameDocente extends FramePanelBase {
    // Guarda el usuario actual
    private final Usuario usuario;

    // Constructor que recibe Usuario
    public FrameDocente(Usuario usuario) {
        this.usuario = usuario;
        setTitle("AssistU - Docente " + usuario.getNombre());
        configurarAcciones();
        actualizarDatos();
        setVisible(true);
    }

    private void actualizarDatos() {
        labelNombreUsuario.setText(getUserName());
        labelMatriculaUsuario.setText(getUserMatricula());
    }

    @Override
    protected String getDashboardTitle() {
        return "Panel Docente";
    }

    @Override
    protected String getUserRole() {
        return "Docente";
    }

    @Override
    protected String getUserName() {
        return usuario != null ? usuario.getNombre() + " " + usuario.getApellidoPaterno() : "Sin Nombre";
    }

    @Override
    protected String getUserMatricula() {
        return usuario != null ? usuario.getMatricula() : "Sin Matrícula";
    }

    @Override
    protected JButton[] getSideButtons() {
        botones = new JButton[4];
        botones[0] = setButton("Materias Asignadas", 18, 350, 40);
        botones[1] = setButton("Grupos Asignados", 18, 350, 40);
        botones[2] = setButton("Bandeja", 18, 350, 40);
        botones[3] = setButton("Abandonar Sesión", 18, 350, 40);
        return botones;
    }

    private void configurarAcciones() {
        botones[0].addActionListener(e -> mostrarMateriasAsignadas());
        botones[1].addActionListener(e -> mostrarGruposAsignados());
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
            {"alert", "Administración", "Recordatorio: Revisión de actas", "07/08/2025 09:00", "Recuerda revisar y firmar las actas antes del viernes."}
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

    // Materias asignadas al docente (usando DAO real)
    private void mostrarMateriasAsignadas() {
        try {
            ControladorRecursamiento controlador = new ControladorRecursamiento();
            List<Recursamiento> asignados = controlador.obtenerTodos()
                .stream()
                .filter(r -> r.getMatriculaUsuario().equalsIgnoreCase(usuario.getMatricula()))
                .toList();

            JDialog dialog = new JDialog(this, "Materias Asignadas", true);
            dialog.setSize(520, 320);
            dialog.setLocationRelativeTo(this);
            dialog.setUndecorated(true);

            JPanel fondo = crearPanelFlotante(500, 300, 4, 5, 25, 1f, 32, 32);
            fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));

            JLabel lblTitulo = setLabel("Materias Asignadas", 22, 1, 'C');
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTitulo.setForeground(UIManager.getColor("bankya.color"));
            fondo.add(Box.createRigidArea(new Dimension(0, 14)));
            fondo.add(lblTitulo);
            fondo.add(Box.createRigidArea(new Dimension(0, 18)));

            if (asignados.isEmpty()) {
                JLabel lbl = setLabel("No tienes materias asignadas.", 16, 3, 'C');
                lbl.setForeground(UIManager.getColor("bankya.color"));
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                fondo.add(lbl);
            } else {
                for (Recursamiento r : asignados) {
                    JPanel tarjeta = new JPanel();
                    tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
                    tarjeta.setOpaque(false);
                    tarjeta.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
                    tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

                    JLabel lblMateria = setLabel("Materia: " + r.getMateria(), 18, 1, 'L');
                    lblMateria.setForeground(UIManager.getColor("bankya.color"));
                    JLabel lblGrupo = setLabel("Grupo: " + r.getGrupo(), 16, 3, 'L');
                    lblGrupo.setForeground(UIManager.getColor("keuni.color"));
                    JLabel lblHorario = setLabel("Horario: " + r.getHorario(), 16, 3, 'L');
                    lblHorario.setForeground(UIManager.getColor("keuni.color"));

                    tarjeta.add(lblMateria);
                    tarjeta.add(lblGrupo);
                    tarjeta.add(lblHorario);

                    tarjeta.setMaximumSize(new Dimension(430, 70));
                    tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

                    fondo.add(tarjeta);
                    fondo.add(Box.createRigidArea(new Dimension(0, 10)));
                }
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar materias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Grupos asignados (realmente son los recursamientos, pero puedes mostrar sólo grupo y materia)
    private void mostrarGruposAsignados() {
        try {
            ControladorRecursamiento controlador = new ControladorRecursamiento();
            List<Recursamiento> asignados = controlador.obtenerTodos()
                .stream()
                .filter(r -> r.getMatriculaUsuario().equalsIgnoreCase(usuario.getMatricula()))
                .toList();

            JDialog dialog = new JDialog(this, "Grupos Asignados", true);
            dialog.setSize(520, 320);
            dialog.setLocationRelativeTo(this);
            dialog.setUndecorated(true);

            JPanel fondo = crearPanelFlotante(500, 300, 4, 5, 25, 1f, 32, 32);
            fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));

            JLabel lblTitulo = setLabel("Grupos Asignados", 22, 1, 'C');
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTitulo.setForeground(UIManager.getColor("bankya.color"));
            fondo.add(Box.createRigidArea(new Dimension(0, 14)));
            fondo.add(lblTitulo);
            fondo.add(Box.createRigidArea(new Dimension(0, 18)));

            if (asignados.isEmpty()) {
                JLabel lbl = setLabel("No tienes grupos asignados.", 16, 3, 'C');
                lbl.setForeground(UIManager.getColor("bankya.color"));
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                fondo.add(lbl);
            } else {
                for (Recursamiento r : asignados) {
                    JPanel tarjeta = new JPanel();
                    tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
                    tarjeta.setOpaque(false);
                    tarjeta.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
                    tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

                    JLabel lblGrupo = setLabel("Grupo: " + r.getGrupo(), 18, 1, 'L');
                    lblGrupo.setForeground(UIManager.getColor("bankya.color"));
                    JLabel lblMateria = setLabel("Materia: " + r.getMateria(), 16, 3, 'L');
                    lblMateria.setForeground(UIManager.getColor("keuni.color"));
                    JLabel lblHorario = setLabel("Horario: " + r.getHorario(), 16, 3, 'L');
                    lblHorario.setForeground(UIManager.getColor("keuni.color"));

                    tarjeta.add(lblGrupo);
                    tarjeta.add(lblMateria);
                    tarjeta.add(lblHorario);

                    tarjeta.setMaximumSize(new Dimension(430, 70));
                    tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

                    fondo.add(tarjeta);
                    fondo.add(Box.createRigidArea(new Dimension(0, 10)));
                }
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar grupos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected JPanel buildCardsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Obtener materias y grupos asignados desde DAO
        try {
            ControladorRecursamiento controlador = new ControladorRecursamiento();
            List<Recursamiento> asignados = controlador.obtenerTodos()
                .stream()
                .filter(r -> r.getMatriculaUsuario().equalsIgnoreCase(usuario.getMatricula()))
                .toList();

            if (asignados.isEmpty()) {
                panel.add(setLabel("No tienes materias asignadas.", 20, 3, 'C'));
            } else {
                for (Recursamiento r : asignados) {
                    panel.add(crearTarjetaDocente(
                        r.getMateria(),
                        "Grupo: " + r.getGrupo(),
                        r.getHorario(),
                        1
                    ));
                }
            }
        } catch (Exception e) {
            panel.add(setLabel("Error al cargar materias: " + e.getMessage(), 16, 3, 'C'));
        }

        // Panel de selección de materia y horario para el docente (puedes adaptar la lógica)
        panel.add(panelSeleccionMateriaHorario());

        return panel;
    }

    private JPanel crearTarjetaDocente(String titulo, String detalle, String horario, int colorType) {
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
            case 1 -> colorBar.setBackground(UIManager.getColor("oftani.color"));
            case 2 -> colorBar.setBackground(UIManager.getColor("saron.color"));
            case 3 -> colorBar.setBackground(UIManager.getColor("keuni.color"));
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
        lblTitulo.setForeground(UIManager.getColor("bankya.color"));
        contenido.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 20, 10, 10);
        JLabel lblDetalle = setLabel(detalle, 16, 3, 'L');
        lblDetalle.setForeground(UIManager.getColor("keuni.color"));
        contenido.add(lblDetalle, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(8, 40, 8, 30);
        String horarioTexto = (horario.isEmpty() ? "" : "Horario. " + horario);
        JLabel lblHorario = setLabel(horarioTexto, 16, 2, 'L');
        lblHorario.setForeground(UIManager.getColor("keuni.color"));
        contenido.add(lblHorario, gbc);

        tarjeta.add(colorBar, BorderLayout.WEST);
        tarjeta.add(contenido, BorderLayout.CENTER);

        return tarjeta;
    }

    // Panel de selección para el docente (puedes adaptar lógica si quieres que el docente solicite grupo/materia)
    private JPanel panelSeleccionMateriaHorario() {
        // Ejemplo: materias y horarios disponibles para docentes (puedes poblar dinámicamente si lo deseas)
        String[] materias = {"Selecciona una materia...", "Programación Orientada a Objetos", "Cálculo Integral", "Electrónica Básica"};
        Map<String, String[]> horariosPorMateria = new HashMap<>();
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