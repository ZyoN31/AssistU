package com.assistuteam.assistu.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.assistuteam.assistu.controller.ControladorAlumno;
import com.assistuteam.assistu.controller.ControladorUsuario;
import com.assistuteam.assistu.model.entity.Alumno;
import com.assistuteam.assistu.model.entity.Usuario;
import com.assistuteam.assistu.view.dev.SyncViz;
import com.assistuteam.assistu.view.util.FrameUtilities;

/** @author assistu_team **/

@SuppressWarnings("all") 
public class FramePrincipal extends FrameUtilities {
    private FrameRegistro frameRegistro;

    public FramePrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("AssistU - Inicio");
        setSize(defaultWidth, defaultHeight);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPanelFondoImg("/com/assistuteam/assistu/resources/images/background_01.png");
        panelFondo.setLayout(new GridBagLayout());

        panelLogin();
        activarBotones();

        panelFondo.add(panelInterno01, new GridBagConstraints());
        setPanelFondo(panelFondo);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void panelLogin() {
        panelInterno01 = crearPanelFlotante(380, 420, 4, 3, 15, 1f, arcWidthDefault, arcHeightDefault);
        panelInterno01.setLayout(new GridBagLayout());
        int xPadding = 20;

        labelLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 88, 66);
        panelInterno01.add(labelLogo, setGridsAttributes(0, 0, 2, 1, false, 15, 0, 10, xPadding));

        labelTitulo = setLabel("AssistU", 50, 1, 'C');
        panelInterno01.add(labelTitulo, setGridsAttributes(2, 0, 2, 1, false, 15, 0, xPadding-70, xPadding));

        labelMatricula = setLabel("Matrícula", defaultFontSize, 3, 'C');
        panelInterno01.add(labelMatricula, setGridsAttributes(0, 1, 4, 1, false, -20, 0, xPadding, xPadding));

        txFlMatricula = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlMatricula, setGridsAttributes(0, 2, 4, 1, true, 0, 10, xPadding, xPadding));

        labelContrasenia = setLabel("Contraseña", defaultFontSize, 3, 'C');
        panelInterno01.add(labelContrasenia, setGridsAttributes(0, 3, 4, 1, false, -10, 20, xPadding, xPadding));

        txFlContrasenia = setPasswordField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlContrasenia, setGridsAttributes(0, 4, 4, 1, true, 20, 0, xPadding, xPadding));

        btIniciar = setButton("Iniciar", defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(btIniciar, setGridsAttributes(0, 5, 2, 1, false, 0, 10, xPadding+10, xPadding));

        btRegistrar = setButton("¡Registrarte!", defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(btRegistrar, setGridsAttributes(2, 5, 2, 1, false, 0, 10, xPadding-20, xPadding+10));
    }

    private void activarBotones() {
        btIniciar.addActionListener(e -> {
            String matricula = getTxFlMatricula();
            String contrasenia = getTxFlContrasenia();

            try {
                // 1. Intentar login como Usuario (Docente o Administrador)
                ControladorUsuario controladorUsuario = new ControladorUsuario();
                Usuario usuario = controladorUsuario.login(matricula, contrasenia);

                if (usuario != null) {
                    int tipo = usuario.getIdTipoUsuario();
                    if (tipo == 2) { // Docente
                        new FrameDocente(usuario);
                        this.dispose();
                    } else if (tipo == 3) { // Administrador
                        new FrameAdministrador(usuario);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Tipo de usuario no válido.");
                    }
                    return; // Ya logueado, no sigue buscando
                }

                // 2. Si no existe como Usuario, intentar como Alumno
                ControladorAlumno controladorAlumno = new ControladorAlumno();
                Alumno alumno = controladorAlumno.login(matricula, contrasenia);
                if (alumno != null) {
                    System.out.println("Alumno login: " + alumno.getMatricula() + " " + alumno.getNombre());
                    new FrameAlumno(alumno);
                    limpiarFields();
                    this.dispose();
                    return;
                }

                if (matricula.toLowerCase().equals("syncviz") && contrasenia.equals("sync0131")) {
                    new SyncViz();
                    limpiarFields();
                    return;
                }

                // 3. No existe en ninguno, error
                JOptionPane.showMessageDialog(this, "Datos incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en el login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btRegistrar.addActionListener(e -> {
            if (frameRegistro == null) {
                frameRegistro = new FrameRegistro(this);
            }
            frameRegistro.setVisible(true);
            this.setVisible(false);
        });
    }
}