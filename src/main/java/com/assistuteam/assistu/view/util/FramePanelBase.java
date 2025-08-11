package com.assistuteam.assistu.view.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
        init();
    }

    private void init(){
        setTitle("AssistU - Panel Base");
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
        // Panel lateral izquierdo (estático, SIN padding ni borde)
        panelInterno01 = crearPanelEstatico(370, 3, 2, 0, 0.98f); // padding = 0
        panelInterno01.setLayout(new GridBagLayout());
        panelInterno01.setPreferredSize(new Dimension(500, getHeight()));
        panelInterno01.setOpaque(false); // <-- Añade esto para evitar fondo extra

        // Logo grande arriba
        labelLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 110, 80);
        panelInterno01.add(labelLogo, setGridsAttributes(0, 0, 5, 1, false, 0, 30, 0, 0));

        // Título "AssistU"
        labelTitulo = setLabel("AssistU", 48, 1, 'L');
        panelInterno01.add(labelTitulo, setGridsAttributes(0, 1, 5, 1, false, 0, 10, 0, 0));

        // Rol (Alumno, Docente, etc)
        labelTexto01 = setLabel(getUserRole(), 22, 3, 'L');
        panelInterno01.add(labelTexto01, setGridsAttributes(0, 2, 5, 1, false, 0, 0, 0, 0));

        // Nombre grande (NO reutilices labelTitulo)
        labelNombreUsuario = setLabel(getUserName(), 36, 1, 'L');
        panelInterno01.add(labelNombreUsuario, setGridsAttributes(0, 3, 5, 1, false, 0, 0, 0, 0));

        // Matrícula (más pequeño)
        labelMatriculaUsuario = setLabel(getUserMatricula(), 18, 3, 'L');
        panelInterno01.add(labelMatriculaUsuario, setGridsAttributes(0, 4, 5, 1, false, 0, 20, 0, 0));

        // Botones laterales (espaciados verticalmente)
        botones = getSideButtons();
        for (int i = 0; i < botones.length; i++) {
            botones[i].setPreferredSize(new Dimension(260, 38));
            panelInterno01.add(botones[i], setGridsAttributes(0, 5 + i, 5, 1, false, 12, 12, 0, 0));
        }

        // Panel de tarjetas/materias a la derecha (estilo moderno, flotante)
        panelInterno02 = new JPanel(new GridBagLayout());
        panelInterno02.setOpaque(false);

        JPanel cardsPanel = buildCardsPanel();
        cardsPanel.setOpaque(false);

        // Añade el panel de tarjetas centrado verticalmente
        GridBagConstraints gbcCards = new GridBagConstraints();
        gbcCards.gridx = 0;
        gbcCards.gridy = 0;
        gbcCards.insets = new Insets(0, 0, 0, 0);
        gbcCards.anchor = GridBagConstraints.CENTER;
        panelInterno02.add(cardsPanel, gbcCards);

        // Añadir ambos paneles al fondo, SIN insets y pegados
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 0, 0, 0); // sin margen
        gbcLeft.anchor = GridBagConstraints.NORTHWEST;
        gbcLeft.fill = GridBagConstraints.BOTH; // <-- Cambia a BOTH para ocupar todo el alto
        gbcLeft.weighty = 1.0;
        gbcLeft.weightx = 0.0;

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 1;
        gbcRight.gridy = 0;
        gbcRight.insets = new Insets(0, 0, 0, 0); // sin padding
        gbcRight.anchor = GridBagConstraints.CENTER;

        panelFondo.add(panelInterno01, gbcLeft);
        panelFondo.add(panelInterno02, gbcRight);
    }

    /**
     * Panel reutilizable para selección de materia y horario.
     * 
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
