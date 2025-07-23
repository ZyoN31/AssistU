package com.assistuteam.assistu.view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/** @author assistu_team **/

@SuppressWarnings("all")
public abstract class FrameUtilities extends JFrame{

    // Etiquetas reutilizables
    protected JLabel labelTitulo;
    protected JLabel labelSubtitulo;
    protected JLabel labelLogo;
    protected JLabel labelTexto01;
    protected JLabel labelTexto02;
    protected JLabel labelTexto03;
    protected JLabel labelTexto04;

    // Paneles reutilizables
    protected JPanel panelFondo;
    protected JPanel panelInterno01;
    protected JPanel panelInterno02;

    // Campos de texto reutilizables
    protected JTextField txFlMatricula;
    protected JPasswordField txFlContrasenia;
    protected JTextField txFlContraseniaVisible;
    protected JTextField txFlNombre;
    protected JTextField txFlApellidoP;
    protected JTextField txFlApellidoM;
    protected JTextField txFlCorreo;

    // Botones reutilizables
    protected JButton btIniciar;
    protected JButton btRegistrar;
    protected JButton btCancelar;
    protected JButton btBuscar;
    protected JButton btModificar;
    protected JButton btEliminar;
    protected JButton btLimpiar;

    /**
     * Crea un JLabel con la fuente y alineacion especificadas
     * @param text texto del JLabel
     * @param sizeFont tamano de la fuente
     * @param type tipo de etiqueta (1:Titulo, 2:Subtitulo, 3:Texto)
     * @param align alineacion del texto ('L'=izquierda, 'C'=centro, 'R'=derecha)
     * @return JLabel configurado
     */
    public JLabel setLabel(String text, int sizeFont, int type, char align) {
        JLabel label = new JLabel(text);
        switch (type) {
            case 1 -> label.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, sizeFont));
            case 2 -> label.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, sizeFont));
            case 3 -> label.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, sizeFont));
            default -> System.out.println("ERROR: Tipo de etiqueta invalido");
        }
        switch (align) {
            case 'L' -> label.setHorizontalAlignment(JLabel.LEFT);
            case 'C' -> label.setHorizontalAlignment(JLabel.CENTER);
            case 'R' -> label.setHorizontalAlignment(JLabel.RIGHT);
            default -> label.setHorizontalAlignment(JLabel.LEFT);
        }
        return label;
    }

    /**
     * Crea un JTextField con la fuente y alineacion especificadas
     * @param placeholder texto de marcador de posicion
     * @param sizeFont tamano de la fuente
     * @param width ancho del campo en pixeles
     * @param height alto del campo en pixeles
     * @param align alineacion del texto ('L'=izquierda, 'C'=centro, 'R'=derecha)
     * @return JTextField configurado
     */
    public JTextField setTextField(String placeholder, int sizeFont, int width, int height, char align) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(UIManager.getFont("TextField.font").deriveFont(Font.PLAIN, sizeFont));
        textField.setPreferredSize(new Dimension(width, height));
        switch (align) {
            case 'L' -> textField.setHorizontalAlignment(JTextField.LEFT);
            case 'C' -> textField.setHorizontalAlignment(JTextField.CENTER);
            case 'R' -> textField.setHorizontalAlignment(JTextField.RIGHT);
            default -> textField.setHorizontalAlignment(JTextField.LEFT);
        }
        return textField;
    }

    /**
     * Crea un JPasswordField con la fuente y alineacion especificadas
     * @param placeholder texto de marcador de posicion
     * @param sizeFont tamano de la fuente
     * @param width ancho del campo en pixeles
     * @param height alto del campo en pixeles
     * @param align alineacion del texto ('L'=izquierda, 'C'=centro, 'R'=derecha)
     * @return JPasswordField configurado
     */
    public JPasswordField setPasswordField(String placeholder, int sizeFont, int width, int height, char align) {
        JPasswordField passwordField = new JPasswordField(placeholder);
        passwordField.setFont(UIManager.getFont("PasswordField.font").deriveFont(Font.PLAIN, sizeFont));
        passwordField.setPreferredSize(new Dimension(width, height));
        switch (align) {
            case 'L' -> passwordField.setHorizontalAlignment(JTextField.LEFT);
            case 'C' -> passwordField.setHorizontalAlignment(JTextField.CENTER);
            case 'R' -> passwordField.setHorizontalAlignment(JTextField.RIGHT);
            default -> passwordField.setHorizontalAlignment(JTextField.LEFT);
        }
        return passwordField;
    }

    /**
     * Crea un JButton con la fuente y tamano especificados
     * @param text texto del boton
     * @param sizeFont tamano de la fuente
     * @param width ancho del boton en pixeles
     * @param height alto del boton en pixeles
     * @return JButton configurado
     */
    public JButton setButton(String text, int sizeFont, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(UIManager.getFont("Button.font").deriveFont(Font.BOLD, sizeFont));
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false); // Elimina el borde de enfoque
        return button;
    }

    /**
     * Cambia el panel de contenido del JFrame
     * @param content JPanel que se anadira como nuevo contenido
     */
    public void changeContentPanel(JPanel content) {
        this.getContentPane().removeAll();
        this.getContentPane().add(content);
        this.setVisible(true);
    }

    /**
     * Establece un panel de fondo para el JFrame
     * @param panelFondo JPanel que se usara como fondo
     */
    public void setPanelFondo(JPanel panelFondo) {
        this.setContentPane(panelFondo);
        this.setVisible(true);
    }

    /**
     * Crea un panel de fondo con una imagen
     * @param path ruta de la imagen que se usara como fondo
     */
    public void setPanelFondoImg(String path) {
        panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource(path)
                );
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    /**
     * Crea un JLabel con una imagen a partir de un path
     * @param path ruta de la imagen (relativa al classpath)
     * @param width ancho preferido del JLabel (en pixeles)
     * @param height alto preferido del JLabel (en pixeles)
     * @return JLabel con la imagen cargada
     */
    public JLabel setImageLabel(String path, int width, int height) {
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(path));
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(scaledImg);
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    /**
     * Crea un panel interno con fondo de degradado, bordes redondeados y transparencia
     * @param width ancho del panel
     * @param height alto del panel
     * @param colorStart color de inicio del degradado (indice)
     * @param colorEnd color de fin del degradado (indice)
     * @param arcWidth ancho de los bordes redondeados
     * @param arcHeight alto de los bordes redondeados
     * @param padding espaciado interno del panel
     * @param alpha nivel de transparencia (0.0 = totalmente transparente, 1.0 = opaco)
     * @return JPanel configurado
     */
        public JPanel crearPanel(int width, int height, int colorStart, int colorEnd, int arcWidth, int arcHeight, int padding, float alpha) {
        Color start = colorSelected(colorStart);
        Color end = colorSelected(colorEnd);
        return new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
                java.awt.GradientPaint gp = new java.awt.GradientPaint(
                    0, 0, start,
                    0, getHeight(), end
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
                g2.dispose();
                this.setPreferredSize(new Dimension(width, height));
                this.setOpaque(false);
                this.setBorder(new EmptyBorder(padding, padding, padding, padding));
            }
        };
    }
    
    /**
     * Configura los atributos de GridBagConstraints para un componente
     * @param x posicion en la columna (x)
     * @param y posicion en la fila (y)
     * @param width tamano de las columnas que ocupa el componente
     * @param height tamano de las filas que ocupa el componente
     * @param responsive indica si el componente debe ser responsivo
     * @param paddingVerticalTop espacio superior en pixeles
     * @param paddingVerticalBottom espacio inferior en pixeles
     * @param paddingHorizontal espacio izquierdo y derecho en pixeles
     * @return GridBagConstraints configurado
     */
    public GridBagConstraints setGridsAttributes(int x, int y, int width, int height, boolean responsive, int paddingVerticalTop, int paddingVerticalBottom, int paddingHorizontal) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = responsive ? 1.0 : 0.0;
        gbc.insets = new java.awt.Insets(paddingVerticalTop, paddingHorizontal, paddingVerticalBottom, paddingHorizontal);
        return gbc;
    }

    /**
     * Devuelve el color seleccionado basado en un indice
     * @param colorSelection indice del color seleccionado
     * @return Color correspondiente al indice
     */
    private Color colorSelected(int colorSelection) {
        String colorKey = switch (colorSelection) {
            case 1 -> "eigengrau.color";
            case 2 -> "vanwo.color";
            case 3 -> "oftani.color";
            case 4 -> "saron.color";
            case 5 -> "keuni.color";
            case 6 -> "bankya.color";
            default -> null;
        };
        if (colorKey != null) {
            Color color = UIManager.getColor(colorKey);
            if (color != null) return color;
        }
        // Color por defecto si no se encuentra la clave
        return Color.LIGHT_GRAY;
    }

    // Getters para acceso externo
    /**
     * @return campo de texto de matricula
     */
    public JTextField gettxFlMatricula() { return txFlMatricula; }
    /**
     * @return campo de contrasena
     */
    public JPasswordField gettxFlContrasenia() { return txFlContrasenia; }
    /**
     * @return boton de iniciar sesion
     */
    public JButton getBotonIniciar() { return btIniciar; }
    /**
     * @return boton de registrar
     */
    public JButton getLabelRegistrate() { return btRegistrar; }
    /**
     * @return etiqueta de logo
     */
    public JLabel getLabelLogo() { return labelLogo; }
}
