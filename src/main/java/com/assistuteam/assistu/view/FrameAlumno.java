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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.assistuteam.assistu.controller.ControladorInscripcion;
import com.assistuteam.assistu.controller.ControladorRecursamiento;
import com.assistuteam.assistu.model.entity.Alumno;
import com.assistuteam.assistu.model.entity.Inscripcion;
import com.assistuteam.assistu.model.entity.Recursamiento;
import com.assistuteam.assistu.view.util.FramePanelBase;

/** @author assistu_team **/

@SuppressWarnings("all")
public class FrameAlumno extends FramePanelBase {
    private final Alumno alumno;

    public FrameAlumno(Alumno alumno) {
        this.alumno = alumno;
        setTitle("AssistU - Alumno " + alumno.getNombre());
        configurarAcciones();
        setVisible(true);
    }

    @Override
    protected String getDashboardTitle() { return "Panel Alumno"; }

    @Override
    protected String getUserRole() { return "Alumno"; }

    @Override
    protected String getUserName() { return alumno != null ? alumno.getNombre() + " " + alumno.getApellidoPaterno() : "Sin Nombre"; }

    @Override
    protected String getUserMatricula() { return alumno != null ? alumno.getMatricula() : "Sin Matrícula"; }

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
        botones[1].addActionListener(e -> mostrarPanelSolicitarRecursamiento());
        botones[2].addActionListener(e -> mostrarBandeja());
        botones[3].addActionListener(e -> {
            this.dispose();
            new FramePrincipal();
        });
    }

    private void mostrarHistorialRecursamientos() {
        try {
            ControladorInscripcion controladorInscripcion = new ControladorInscripcion();
            List<Inscripcion> historial = controladorInscripcion.historialPorAlumno(alumno.getMatricula());

            JDialog dialog = new JDialog(this, "Historial de Recursamientos", true);
            dialog.setSize(520, 320);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(22, 22, 29));

            JLabel lblTitulo = setLabel("Historial de Recursamientos", 22, 1, 'C');
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTitulo.setForeground(Color.WHITE);
            panel.add(Box.createRigidArea(new Dimension(0, 18)));
            panel.add(lblTitulo);
            panel.add(Box.createRigidArea(new Dimension(0, 18)));

            if (historial.isEmpty()) {
                JLabel lbl = setLabel("No tienes recursamientos inscritos.", 16, 3, 'C');
                lbl.setForeground(Color.WHITE);
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(lbl);
            } else {
                for (Inscripcion insc : historial) {
                    JLabel lbl = setLabel("• Materia: " + insc.getIdRecursamiento() + " | Estado: " + insc.getEstado() + " | Calificación: " + insc.getCalificacion(), 18, 3, 'L');
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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener historial: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPanelSolicitarRecursamiento() {
        try {
            ControladorRecursamiento controladorRecursamiento = new ControladorRecursamiento();
            List<Recursamiento> recursamientos = controladorRecursamiento.obtenerTodos();

            // Paso 1: Agrupar horarios por materia
            Map<String, List<String>> horariosPorMateria = new HashMap<>();
            for (Recursamiento r : recursamientos) {
                horariosPorMateria.computeIfAbsent(r.getMateria(), k -> new ArrayList<>()).add(r.getHorario());
            }

            // Paso 2: Convertir a Map<String, String[]>
            Map<String, String[]> horariosFinal = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : horariosPorMateria.entrySet()) {
                horariosFinal.put(entry.getKey(), entry.getValue().toArray(new String[0]));
            }

            // Paso 3: Array de materias
            String[] materias = horariosFinal.keySet().toArray(new String[0]);

            // Paso 4: Panel de selección
            JPanel panel = crearPanelSeleccionMateriaHorario(
                materias,
                horariosFinal,
                e -> {
                    String[] seleccion = e.getActionCommand().split(",");
                    String materia = seleccion[0];
                    String horario = seleccion.length > 1 ? seleccion[1] : "";
                    if (materia.isEmpty() || horario.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Selecciona materia y horario.");
                        return;
                    }
                    Recursamiento recursamientoSeleccionado = recursamientos.stream()
                        .filter(r -> r.getMateria().equals(materia) && r.getHorario().equals(horario))
                        .findFirst().orElse(null);

                    if (recursamientoSeleccionado == null) {
                        JOptionPane.showMessageDialog(this, "No existe recursamiento con esa selección.");
                        return;
                    }

                    try {
                        ControladorInscripcion controladorInscripcion = new ControladorInscripcion();
                        Inscripcion nuevaInscripcion = new Inscripcion();
                        nuevaInscripcion.setMatriculaAlumno(alumno.getMatricula());
                        nuevaInscripcion.setIdRecursamiento(recursamientoSeleccionado.getId());
                        nuevaInscripcion.setFecha(java.time.LocalDate.now());
                        nuevaInscripcion.setEstado("pendiente");
                        nuevaInscripcion.setCalificacion(0.0f);

                        boolean exito = controladorInscripcion.guardar(nuevaInscripcion);
                        if (exito) {
                            JOptionPane.showMessageDialog(this, "Solicitud de recursamiento enviada.");
                        } else {
                            JOptionPane.showMessageDialog(this, "No se pudo registrar la inscripción.");
                        }
                    } catch (Exception err) {
                        JOptionPane.showMessageDialog(this, "Error al inscribir: " + err.getMessage());
                    }
                }
            );

            // Mostrar en un dialog
            JDialog dialog = new JDialog(this, "Solicitar Recursamiento", true);
            dialog.setSize(520, 320);
            dialog.setLocationRelativeTo(this);

            JPanel contenido = new JPanel(new BorderLayout());
            contenido.setOpaque(false);
            JLabel lblTitulo = setLabel("Solicitar Recursamiento", 22, 1, 'C');
            lblTitulo.setForeground(Color.WHITE);
            contenido.add(lblTitulo, BorderLayout.NORTH);
            contenido.add(panel, BorderLayout.CENTER);

            dialog.setContentPane(contenido);
            dialog.setResizable(false);
            dialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar materias o inscribir: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarBandeja() {
        // Puedes mantener tu implementación actual de bandeja
        // Aquí solo se muestra un ejemplo
        String[][] mensajes = {
            {"success", "Sistema", "Solicitud aprobada", "08/08/2025 09:15", "Tu solicitud para recursar Cálculo Integral ha sido aprobada."},
            {"info", "Coordinación", "Nuevo grupo asignado", "07/08/2025 16:30", "Asignado al grupo 3C para POO."},
            {"alert", "Profesor", "Recordatorio", "07/08/2025 09:00", "Revisa las fechas de examen."}
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

    @Override
    protected JPanel buildCardsPanel() {
        // Puedes mantener las tarjetas de materias y el panel de selección aquí
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Ejemplo de tarjetas, puedes poblar con datos reales si lo deseas
        panel.add(crearTarjetaAlumno("Programación Orientada a Objetos", "Profesor. Michael Doe", "10:00 am – 14:00 pm", 1));
        panel.add(crearTarjetaAlumno("Cálculo Integral", "Profesor No Disponible", "No Disponible", 2));
        panel.add(crearTarjetaAlumno("Sin Materia", "", "", 3));
        // Panel de selección de materia y horario
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

    private JPanel panelSeleccionMateriaHorario() {
        // Puedes reutilizar el método ya integrado en mostrarPanelSolicitarRecursamiento
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Puedes poblar con materias y horarios reales si lo necesitas
        panel.add(new JLabel("Panel de selección de materia y horario (ver botón Solicitar Recursamiento)"));
        return panel;
    }
}