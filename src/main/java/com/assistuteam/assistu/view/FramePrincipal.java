package com.assistuteam.assistu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.assistuteam.assistu.view.util.FrameUtilities;

/** @author assistu_team **/

public class FramePrincipal extends FrameUtilities {

    public FramePrincipal() {
        txFlMatricula = new JTextField();
        txFlContrasenia = new JPasswordField();
        initComponents();
    }

    private void initComponents() {
        setTitle("Inicio");
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de fondo con imagen
        setPanelFondoImg("/com/assistuteam/assistu/resources/images/Background_iniciarSesion.png");
        panelFondo.setLayout(new GridBagLayout());
        add(panelFondo, BorderLayout.CENTER);

        // Panel interno para el login
        panelLogin();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void panelLogin() {
        // Panel central (login) con color y bordes redondeados
        panelInterno01 = crearPanel(400, 450, 3, 2, 100, 100, 35, 1f);
        panelInterno01.setLayout(new GridBagLayout());

        // Padding para el eje X de la "mayoria" de componentes
        int xPadding = 15;
        // Atributos de modificacion de tamaño
        int[] sizePanel = {};
        int[] verticalPads = {};

        // Logo
        labelLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 78, 59);
        panelInterno01.add(labelLogo, setGridsAttributes(1, 0, 1, 1, true, 0, 10, 0));

        // Título
        labelTitulo = setLabel("AssistU", 50, 3, 'L');
        panelInterno01.add(labelTitulo, setGridsAttributes(3, 0, 2, 1, true, 0, 10, -50));

        // Label y campo de matrícula
        labelTexto01 = setLabel("Matricula", 20, 2, 'C');
        txFlMatricula = setTextField("", 15, 300, 20, 'C');
        panelInterno01.add(labelTexto01, setGridsAttributes(1, 2, 4, 1, false, 0, 0, xPadding));
        panelInterno01.add(txFlMatricula, setGridsAttributes(1, 3, 4, 1, false, 0, 20, xPadding));

        // Label y campo de contraseña
        labelTexto02 = setLabel("Contraseña", 20, 2, 'C');
        txFlContrasenia = setPasswordField("", 15, 300, 20, 'C');
        panelInterno01.add(labelTexto02, setGridsAttributes(1, 4, 4, 1, false, 0, 0, xPadding));
        panelInterno01.add(txFlContrasenia, setGridsAttributes(1, 5, 4, 1, false, 0, 30, xPadding));

        // Botón de iniciar sesión
        btIniciar = setButton("Iniciar Sesión", 16, 300, 20);
        panelInterno01.add(btIniciar, setGridsAttributes(1, 6, 2, 1, false, 5, 5, 10));

        // Boton Registrar
        btRegistrar = setButton("Registrar", 16, 300, 20);
        panelInterno01.add(btRegistrar, setGridsAttributes(3, 6, 2, 1, false, 5, 5, 10));

        // Añadir panelInterno01 centrado en panelFondo
        panelFondo.add(panelInterno01, new GridBagConstraints());
    }
}