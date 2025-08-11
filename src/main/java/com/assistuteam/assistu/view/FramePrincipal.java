package com.assistuteam.assistu.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.assistuteam.assistu.controller.ControladorUsuario;
import com.assistuteam.assistu.model.entity.Usuario;
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

        // Panel de fondo con imagen
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
        
        // Logo
        labelLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 88, 66);
        panelInterno01.add(labelLogo, setGridsAttributes(0, 0, 2, 1, false, 15, 0, 10, xPadding));

        // Título
        labelTitulo = setLabel("AssistU", 50, 1, 'C');
        panelInterno01.add(labelTitulo, setGridsAttributes(2, 0, 2, 1, false, 15, 0, xPadding-70, xPadding));

        // Matrícula Label
        labelMatricula = setLabel("Matrícula", defaultFontSize, 3, 'C');
        panelInterno01.add(labelMatricula, setGridsAttributes(0, 1, 4, 1, false, -20, 0, xPadding, xPadding));
        // Campo de Matrícula
        txFlMatricula = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlMatricula, setGridsAttributes(0, 2, 4, 1, true, 0, 10, xPadding, xPadding));

        // Contraseña Label
        labelContrasenia = setLabel("Contraseña", defaultFontSize, 3, 'C');
        panelInterno01.add(labelContrasenia, setGridsAttributes(0, 3, 4, 1, false, -10, 20, xPadding, xPadding));
        // Campo de contraseña
        txFlContrasenia = setPasswordField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlContrasenia, setGridsAttributes(0, 4, 4, 1, true, 20, 0, xPadding, xPadding));

        // Boton de iniciar sesion
        btIniciar = setButton("Iniciar", defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(btIniciar, setGridsAttributes(0, 5, 2, 1, false, 0, 10, xPadding+10, xPadding));

        // Boton de registrar
        btRegistrar = setButton("¡Registrarte!", defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(btRegistrar, setGridsAttributes(2, 5, 2, 1, false, 0, 10, xPadding-20, xPadding+10));
    }

    private void activarBotones() {
        btIniciar.addActionListener(e -> {
            String matricula = txFlMatricula.getText().trim();
            String contrasenia = new String(txFlContrasenia.getPassword()).trim();

            // Llama al login
            Usuario usuario = ControladorUsuario.login(matricula, contrasenia);

            // Depuración: imprime resultado
            System.out.println("Usuario: " + usuario);
            if (usuario != null) {
                System.out.println("Nombre: " + usuario.getNombre());
                System.out.println("Matrícula: " + usuario.getMatricula());
                System.out.println("Tipo: " + usuario.getTipoUsuario());
                String tipo = usuario.getTipoUsuario().toLowerCase();
                switch (tipo) {
                    case "docente" -> {
                        new FrameDocente(usuario);
                        this.dispose();
                    }
                    case "alumno", "alumna" -> {
                        new FrameAlumno(usuario);
                        this.dispose();
                    }
                    case "administrador" -> {
                        new FrameAdministrador(usuario);
                        this.dispose();
                    }
                    default -> JOptionPane.showMessageDialog(this, "Usuario sin panel asignado.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Datos incorrectos", "Error de inicio de sesión", javax.swing.JOptionPane.ERROR_MESSAGE);
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