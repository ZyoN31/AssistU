// Este codigo se desarrollo con la intencion de ser una clase base para los JFrame de la aplicacion,
// con metodos reutilizables para crear frames con estilos consistentes y paneles internos predefinidos para cada dashboard (estudiante, docente, admin).
package com.assistuteam.assistu.view.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/** @author assistu_team **/

@SuppressWarnings("all")
public abstract class FramePanelBase extends FrameUtilities {

    protected JButton[] botones;
    // Añade variables separadas para los labels
    protected JLabel labelLogo;
    protected JLabel labelTitulo;
    protected JLabel labelTexto01;
    protected JLabel labelNombreUsuario; // <-- nuevo
    protected JLabel labelMatriculaUsuario; // <-- nuevo

    // Métodos abstractos a implementar por cada dashboard
    protected abstract String getDashboardTitle();
    protected abstract String getUserRole();
    protected abstract String getUserName();
    protected abstract String getUserMatricula();
    protected abstract JButton[] getSideButtons();
    protected abstract JPanel buildCardsPanel();

    public FramePanelBase(){
        super();
    }

    protected void initPanelBase(){
        setTitle("AssistU - " + getDashboardTitle());
        setSize(defaultWidth, defaultHeight);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo con imagen y layout
        setPanelFondoImg("/com/assistuteam/assistu/resources/images/background_03.png");
        panelFondo.setLayout(new GridBagLayout());
        add(panelFondo, BorderLayout.CENTER);

        crearPanelesInternos();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearPanelesInternos(){
        // Panel lateral izquierdo
        panelInterno01 = crearPanelEstatico(420, 3, 2, 0, 0.98f);
        panelInterno01.setLayout(new GridBagLayout());
        panelInterno01.setPreferredSize(new Dimension(420, getHeight()));
        panelInterno01.setOpaque(false);

        // Marca principal
        labelLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 110, 80);
        panelInterno01.add(labelLogo, setGridsAttributes(0, 0, 1, 1, false, 0, 35, 20, 10));

        // Titulo principal
        labelTitulo = setLabel("AssistU", 48, 1, 'L');
        panelInterno01.add(labelTitulo, setGridsAttributes(1, 0, 1, 1, false, 0, 35, 0, 20));

        // Rol
        labelTexto01 = setLabel(getUserRole(), 22, 3, 'L');
        panelInterno01.add(labelTexto01, setGridsAttributes(0, 1, 2, 1, false, 0, 8, 20, 20));

        JPanel panelUsuario = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new java.awt.Color(31, 37, 97, 215));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 26, 26);
                g2.dispose();
            }
        };
        panelUsuario.setOpaque(false);
        panelUsuario.setPreferredSize(new Dimension(360, 92));

        labelNombreUsuario = setLabel(getUserName(), 36, 1, 'L');
        labelNombreUsuario.setForeground(UIManager.getColor("bankya.color"));
        labelMatriculaUsuario = setLabel(getUserMatricula(), 20, 3, 'L');
        labelMatriculaUsuario.setForeground(UIManager.getColor("bankya.color"));

        panelUsuario.add(labelNombreUsuario, setGridsAttributes(0, 0, 1, 1, true, 0, 10, 18, 12));
        panelUsuario.add(labelMatriculaUsuario, setGridsAttributes(0, 1, 1, 1, true, 6, 0, 18, 12));
        panelInterno01.add(panelUsuario, setGridsAttributes(0, 2, 2, 1, true, 0, 0, 15, 15));

        // Botones laterales
        botones = getSideButtons();
        for (int i = 0; i < botones.length; i++) {
            botones[i].setPreferredSize(new Dimension(330, 46));
            panelInterno01.add(botones[i], setGridsAttributes(0, 3 + i, 2, 1, true, 10, 12, 20, 20));
        }

        JPanel spacerBottom = new JPanel();
        spacerBottom.setOpaque(false);
        GridBagConstraints gbcSpacer = setGridsAttributes(0, 3 + botones.length, 2, 1, true, 0, 0, 0, 0);
        gbcSpacer.fill = GridBagConstraints.BOTH;
        gbcSpacer.weighty = 1.0;
        panelInterno01.add(spacerBottom, gbcSpacer);

        // Panel principal derecho
        panelInterno02 = new JPanel(new GridBagLayout());
        panelInterno02.setOpaque(false);

        JPanel cardsPanel = buildCardsPanel();
        cardsPanel.setOpaque(false);

        GridBagConstraints gbcCards = new GridBagConstraints();
        gbcCards.gridx = 0;
        gbcCards.gridy = 0;
        gbcCards.insets = new Insets(0, 0, 0, 0);
        gbcCards.anchor = GridBagConstraints.CENTER;
        gbcCards.fill = GridBagConstraints.NONE;
        gbcCards.weightx = 1.0;
        gbcCards.weighty = 1.0;
        panelInterno02.add(cardsPanel, gbcCards);

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 0, 0, 0);
        gbcLeft.anchor = GridBagConstraints.NORTHWEST;
        gbcLeft.fill = GridBagConstraints.BOTH;
        gbcLeft.weighty = 1.0;
        gbcLeft.weightx = 0.0;

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 1;
        gbcRight.gridy = 0;
        gbcRight.insets = new Insets(0, 0, 0, 0);
        gbcRight.anchor = GridBagConstraints.NORTHWEST;
        gbcRight.fill = GridBagConstraints.BOTH;
        gbcRight.weightx = 1.0;
        gbcRight.weighty = 1.0;

        panelFondo.add(panelInterno01, gbcLeft);
        panelFondo.add(panelInterno02, gbcRight);
    }

    /**
     * Panel reutilizable para selección de materia y horario
     * @param materias           Arreglo de materias a mostrar
     * @param horariosPorMateria Mapa materia->arreglo de horarios
     * @param onSeleccionar      Acción al presionar el botón "Seleccionar"
     * @return JPanel listo para usarse en el panel central
     */
    protected JPanel crearPanelSeleccionMateriaHorario(
        String[] materias, 
        Map<String, String[]> horariosPorMateria,
        ActionListener onSeleccionar
    ) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        JLabel lblMateria = setLabel("Materia:", 18, 3, 'L');
        JComboBox<String> comboMaterias = new JComboBox<>(materias);
        JLabel lblHorario = setLabel("Horario:", 18, 3, 'L');
        JComboBox<String> comboHorarios = new JComboBox<>(new String[]{"Selecciona un horario..."});
        JButton btnSeleccionar = setButton("Seleccionar", 16, 130, 30);

        // Cambia horarios según materia
        comboMaterias.addActionListener(e -> {
            String materiaSeleccionada = (String) comboMaterias.getSelectedItem();
            comboHorarios.removeAllItems();
            if (horariosPorMateria.containsKey(materiaSeleccionada)) {
                for (String h : horariosPorMateria.get(materiaSeleccionada)) {
                    comboHorarios.addItem(h);
                }
            } else {
                comboHorarios.addItem("Selecciona un horario...");
            }
        });

        btnSeleccionar.addActionListener(e -> {
            if (onSeleccionar != null) {
                String materia = (String) comboMaterias.getSelectedItem();
                String horario = (String) comboHorarios.getSelectedItem();
                // Pasa la información seleccionada como comando de acción
                onSeleccionar.actionPerformed(
                    new java.awt.event.ActionEvent(
                        btnSeleccionar, 
                        java.awt.event.ActionEvent.ACTION_PERFORMED, 
                        materia + "," + horario
                    )
                );
            }
        });

        panel.add(lblMateria);
        panel.add(comboMaterias);
        panel.add(lblHorario);
        panel.add(comboHorarios);
        panel.add(btnSeleccionar);

        return panel;
    }
}
