package com.assistuteam.assistu.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.assistuteam.assistu.controller.ControladorUsuario;
import com.assistuteam.assistu.view.util.FrameUtilities;

/** @author assistu_team **/

@SuppressWarnings("all") 
public class FrameRegistro extends FrameUtilities {
    private FramePrincipal framePrincipal;
    public FrameRegistro(FramePrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
        initComponents();
    }

    private void initComponents() {
        setTitle("AssistU - Registro");
        setSize(defaultWidth, defaultHeight);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel de fondo con imagen
        setPanelFondoImg("/com/assistuteam/assistu/resources/images/background_02.png");
        panelFondo.setLayout(new GridBagLayout());

        panelRegistro();
        activarBotones();

        panelFondo.add(panelInterno01, new GridBagConstraints());

        setPanelFondo(panelFondo);

        setLocationRelativeTo(null);
    }

    private void panelRegistro() {
        panelInterno01 = crearPanelFlotante(890, 740, 4, 3, 15, 1f, arcWidthDefault, arcHeightDefault);
        panelInterno01.setLayout(new GridBagLayout());
        int xPadding = 20;

        // Logo
        labelLogo = setImageLabel("/com/assistuteam/assistu/resources/images/assistu_logo.png", 128, 106);
        panelInterno01.add(labelLogo, setGridsAttributes(0, 0, 2, 3, false, 15, 0, 10, -170));

        // Título
        labelTitulo = setLabel("AssistU", 50, 1, 'L');
        panelInterno01.add(labelTitulo, setGridsAttributes(2, 1, 4, 1, false, 0, 5, -35, xPadding));

        // Subtítulo
        labelTexto01 = setLabel("Registro", 30, 2, 'L');
        panelInterno01.add(labelTexto01, setGridsAttributes(2, 2, 4, 1, false, 0, -45, -35, xPadding));

        // Matrícula Label
        labelMatricula = setLabel("Matrícula", defaultFontSize, 3, 'C');
        panelInterno01.add(labelMatricula, setGridsAttributes(0, 3, 2, 1, false, -30, 0, xPadding, xPadding+10));
        // Matrícula
        txFlMatricula = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlMatricula, setGridsAttributes(0, 4, 2, 1, true, 0, 0, xPadding, xPadding+10));
        txFlMatricula.setToolTipText("Matricula");

        // Contraseña 1 Label
        labelContrasenia = setLabel("Contraseña", defaultFontSize, 3, 'C');
        panelInterno01.add(labelContrasenia, setGridsAttributes(0, 5, 2, 1, false, -30, 0, xPadding, xPadding+10));
        // Contraseña 1
        txFlContrasenia = setPasswordField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlContrasenia, setGridsAttributes(0, 6, 2, 1, true, 0, 0, xPadding, xPadding+10));
        txFlContrasenia.setToolTipText("Contraseña");

        // Contraseña 2 Label
        labelTexto02 = setLabel("Confirmar Contraseña", defaultFontSize, 3, 'C');
        panelInterno01.add(labelTexto02, setGridsAttributes(0, 7, 2, 1, false, -30, 0, xPadding, xPadding+10));
        // Contraseña 2
        txFlContraseniaVisible = setPasswordField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlContraseniaVisible, setGridsAttributes(0, 8, 2, 1, true, 0, 0, xPadding, xPadding+10));
        txFlContraseniaVisible.setToolTipText("Contraseña");


        // Nombre Label
        labelNombre = setLabel("Nombre", defaultFontSize, 3, 'C');
        panelInterno01.add(labelNombre, setGridsAttributes(2, 3, 2, 1, false, -10, 20, xPadding-30, xPadding));
        // Nombre
        txFlNombre = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlNombre, setGridsAttributes(2, 4, 2, 1, true, 0, 0, xPadding-30, xPadding));
        txFlNombre.setToolTipText("Nombre");

        // Apellido Paterno Label
        labelApellidoP = setLabel("Apellido Paterno", defaultFontSize, 3, 'C');
        panelInterno01.add(labelApellidoP, setGridsAttributes(2, 5, 2, 1, false, -10, 20, xPadding-30, xPadding));
        // Apellido Paterno
        txFlApellidoP = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlApellidoP, setGridsAttributes(2, 6, 2, 1, true, 0, 0, xPadding-30, xPadding));
        txFlApellidoP.setToolTipText("Apellido Paterno");

        // Apellido Materno Label
        labelApellidoM = setLabel("Apellido Materno", defaultFontSize, 3, 'C');
        panelInterno01.add(labelApellidoM, setGridsAttributes(2, 7, 2, 1, false, -10, 20, xPadding-30, xPadding));
        // Apellido Materno
        txFlApellidoM = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlApellidoM, setGridsAttributes(2, 8, 2, 1, true, 0, 0, xPadding-30, xPadding));
        txFlApellidoM.setToolTipText("Apellido Materno");


        // Cuatrimestre Options
        labelCuatrimestre = setLabel("Cuatrimestre", defaultFontSize, 3, 'C');
        panelInterno01.add(labelCuatrimestre, setGridsAttributes(1, 9, 1, 1, false, -30, 20, xPadding, xPadding+140));
        String[] cuatrimestres = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        cbCuatrimestre = setComboBox(cuatrimestres, defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(cbCuatrimestre, setGridsAttributes(1, 10, 1, 1, true, 0, 20, xPadding, xPadding+140));

        // Grupo Options
        labelGrupo = setLabel("Grupo", defaultFontSize, 3, 'C');
        panelInterno01.add(labelGrupo, setGridsAttributes(2, 9, 1, 1, false, -30, 20, xPadding-170, xPadding+280));
        String[] grupos = { "A", "B", "C", "D", "E", "F"};
        cbGrupo = setComboBox(grupos, defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(cbGrupo, setGridsAttributes(2, 10, 1, 1, true, 0, 20, xPadding-170, xPadding+280));

        // Carrera Options
        labelCarrera = setLabel("Carrera", defaultFontSize, 3, 'C');
        panelInterno01.add(labelCarrera, setGridsAttributes(3, 9, 1, 1, false, -30, 20, xPadding-300, xPadding));
        String[] carreras = { "IID", "TSU", "ITI"};
        cbCarrera = setComboBox(carreras, defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(cbCarrera, setGridsAttributes(3, 10, 1, 1, true, 0, 20, xPadding-300, xPadding));

        // Email Label
        labelCorreo = setLabel("Correo institucional", defaultFontSize, 3, 'C');
        panelInterno01.add(labelCorreo, setGridsAttributes(0, 11, 4, 1, false, -10, 20, xPadding, xPadding));
        // Email
        txFlCorreo = setTextField("", defaultFontSize, widthComponent, heightComponent, 'C');
        panelInterno01.add(txFlCorreo, setGridsAttributes(0, 12, 4, 1, true, 0, 0, xPadding, xPadding));
        txFlCorreo.setToolTipText("Email");

        // Boton de volver
        btVolver = setButton("Volver", defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(btVolver, setGridsAttributes(1, 13, 1, 1, false, 0, 20, xPadding+250, xPadding));

        // Boton de registrar
        btRegistrar = setButton("Registrarse", defaultFontSize, widthComponent, heightComponent);
        panelInterno01.add(btRegistrar, setGridsAttributes(2, 13, 1, 1, false, 0, 20, xPadding-20, xPadding+200));
    }

    private void activarBotones() {
        btRegistrar.addActionListener(e -> {
            // Lógica de registro aquí
            String matricula = getTxFlMatricula();
            String contrasenia1 = getTxFlContrasenia();
            String contrasenia2 = getTxFlContraseniaVisible();
            String nombre = getTxFlNombre();
            String apellidoP = getTxFlApellidoP();
            String apellidoM = getTxFlApellidoM();
            String correo = getTxFlCorreo();
            int cuatrimestre = getCbCuatrimestre();
            String grupo = getCbGrupo();
            String carrera = getCbCarrera();

            // Validar que no haya campos vacíos
            if (matricula.isEmpty() || contrasenia1.isEmpty() || contrasenia2.isEmpty() ||
                nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la matricula no tenga menos o mas de 10 caracteres
            if (matricula.length() != 10) {
                JOptionPane.showMessageDialog(this, "La matrícula debe tener exactamente 10 caracteres", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la matricula no contenga solamente numeros
            if (matricula.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "La matrícula no puede contener solamente números", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la matricula no contenga espacios
            if (matricula.contains(" ")) {
                JOptionPane.showMessageDialog(this, "La matrícula no puede contener espacios", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que las contraseñas coincidan
            if (!contrasenia1.equals(contrasenia2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la contraseña tenga al menos 8 caracteres
            if (contrasenia1.length() < 8) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres o mas", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que el correo sea institucional, en la terminacion de: @upemor.edu.mx
            // de la misma forma verificar si contiene mas de 2 puntos o un arroba extra
            if (!correo.endsWith("@upemor.edu.mx") || correo.indexOf('@') != correo.lastIndexOf('@') || correo.indexOf('.') == -1) {
                JOptionPane.showMessageDialog(this, "Correo institucional incorrecto", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Registro en la base de datos de AssutU
            try {
                ControladorUsuario controladorUsuario = new ControladorUsuario();
                boolean exito = controladorUsuario.registrarAlumno(
                    matricula.toUpperCase(), contrasenia1, nombre, apellidoP, apellidoM, correo,
                    cuatrimestre, grupo, carrera
                );
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Registro exitoso", "Registro AssistU", JOptionPane.INFORMATION_MESSAGE);
                    framePrincipal.setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario", "Error de registro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btVolver.addActionListener(e -> {
            framePrincipal.setVisible(true);
            this.setVisible(false);
        });
    }
}