package com.assistuteam.assistu.view.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

    // Paneles reutilizables
    protected JPanel panelFondo;
    protected JPanel panelInterno01;
    protected JPanel panelInterno02;

    // Etiquetas reutilizables
    protected JLabel labelTitulo;
    protected JLabel labelSubtitulo;
    protected JLabel labelLogo;
    protected JLabel labelMatricula;
    protected JLabel labelContrasenia;
    protected JLabel labelNombre;
    protected JLabel labelApellidoP;
    protected JLabel labelApellidoM;
    protected JLabel labelCorreo;
    protected JLabel labelCuatrimestre;
    protected JLabel labelGrupo;
    protected JLabel labelCarrera;
    protected JLabel labelTexto01;
    protected JLabel labelTexto02;
    protected JLabel labelTexto03;
    protected JLabel labelTexto04;

    // Campos de texto reutilizables
    protected JTextField txFlMatricula;
    protected JPasswordField txFlContrasenia;
    protected JTextField txFlContraseniaVisible;
    protected JTextField txFlNombre;
    protected JTextField txFlApellidoP;
    protected JTextField txFlApellidoM;
    protected JTextField txFlCorreo;
    protected JComboBox<String> cbCuatrimestre;
    protected JComboBox<String> cbGrupo;
    protected JComboBox<String> cbCarrera;

    // Botones reutilizables
    protected JButton btIniciar;
    protected JButton btRegistrar;
    protected JButton btCancelar;
    protected JButton btBuscar;
    protected JButton btModificar;
    protected JButton btEliminar;
    protected JButton btLimpiar;
    protected JButton btAceptar;
    protected JButton btCerrarSesion;
    protected JButton btVolver;

    // Atributos de configuracion del JFrame y paneles
    protected int defaultWidth = 1280; // Ancho por defecto del JFrame
    protected int defaultHeight = 720; // Alto por defecto del JFrame
    protected int arcHeightDefault = 75; // Alto de los bordes redondeados del panel interno
    protected int arcWidthDefault = 75; // Ancho de los bordes redondeados del panel interno
    protected int heightComponent = 25; // Alto por defecto de los componentes (campos de texto, botones, etc.)
    protected int widthComponent = 300; // Ancho por defecto de los componentes (campos de texto, botones, etc.)
    protected int generalPadPanel = 30;
    protected int defaultFontSize = 21; // Tamaño de fuente por defecto para campos de texto
    protected int centerComponent = (generalPadPanel + 10)*-1; // Padding horizontal para centrar componentes
    private static Image iconoVentana;

    public FrameUtilities() {
        super();
        aplicarIcono();
    }

    public static void cargarIcono(String ruta) {
        java.net.URL url = FrameUtilities.class.getResource(ruta);
        if (url != null) {
            iconoVentana = new ImageIcon(url).getImage();
        }
    }

    private void aplicarIcono() {
        if (iconoVentana != null) {
            setIconImage(iconoVentana);
        }
    }

    // Obtiene una fuente registrada por nombre, o null si no existe
    private Font getRegisteredFont(String fontName, float size, int style) {
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        for (Font f : fonts) {
            if (f.getFontName().equalsIgnoreCase(fontName) || f.getName().equalsIgnoreCase(fontName)) {
                return f.deriveFont(style, size);
            }
        }
        return null;
    }

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
        Font font = null;
        String fontName = switch (type) {
            case 1 -> "Sansation Regular";
            case 2 -> "Readex Pro";
            case 3 -> "Manjari Regular";
            default -> null;
        };
        if (fontName != null) {
            font = getRegisteredFont(fontName, sizeFont, type == 1 ? Font.BOLD : Font.PLAIN);
        }
        if (font == null) {
            font = new Font("SansSerif", type == 1 ? Font.BOLD : Font.PLAIN, sizeFont);
        }
        label.setFont(font);
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
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        Font font = getRegisteredFont("Manjari Regular", sizeFont, Font.PLAIN);
        if (font == null) font = UIManager.getFont("TextField.font").deriveFont(Font.PLAIN, sizeFont);
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(width, height));
        switch (align) {
            case 'L' -> textField.setHorizontalAlignment(JTextField.LEFT);
            case 'C' -> textField.setHorizontalAlignment(JTextField.CENTER);
            case 'R' -> textField.setHorizontalAlignment(JTextField.RIGHT);
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
        JPasswordField passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                super.paintComponent(g2);
                g2.dispose();
            }
        };

        Font font = getRegisteredFont("Manjari Regular", sizeFont, Font.PLAIN);
        if (font == null) font = UIManager.getFont("PasswordField.font").deriveFont(Font.PLAIN, sizeFont);
        passwordField.setFont(font);
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
        Font font = getRegisteredFont("Sansation Regular", sizeFont, Font.BOLD);
        if (font == null) font = UIManager.getFont("Button.font").deriveFont(Font.BOLD, sizeFont);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        return button;
    }

    public JComboBox setComboBox(String[] items, int sizeFont, int width, int height) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        Font font = getRegisteredFont("Manjari Regular", sizeFont, Font.PLAIN);
        if (font == null) font = UIManager.getFont("ComboBox.font").deriveFont(Font.PLAIN, sizeFont);
        comboBox.setFont(font);
        comboBox.setPreferredSize(new Dimension(width, height));
        return comboBox;
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
                java.net.URL url = getClass().getResource(path);
                if (url != null) {
                    Image bg = Toolkit.getDefaultToolkit().getImage(url);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si la imagen no existe, pinta fondo negro y muestra advertencia
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.RED);
                    g.drawString("Imagen no encontrada: " + path, 20, 20);
                }
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
        ImageIcon icon = new ImageIcon(scaledImg);
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    /**
     * Crea un panel interno con fondo de degradado, bordes redondeados y transparencia
     * @param width ancho del panel
     * @param height alto del panel
     * @param colorIndex color de inicio del degradado (indice)
     * @param colorEnd color de fin del degradado (indice)
     * @param padding espaciado interno del panel
     * @param alpha nivel de transparencia (0.0 = totalmente transparente, 1.0 = opaco)
     * @param arcWidth ancho de los bordes redondeados
     * @param arcHeight alto de los bordes redondeados
     * @return JPanel configurado
     */
    public JPanel crearPanelFlotante(int width, int height, int colorIndex, int colorEnd, int padding, float alpha, int arcWidth, int arcHeight) {
        Color start = colorSelected(colorIndex);
        Color end = colorSelected(colorEnd);
        return new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                GradientPaint gp = new java.awt.GradientPaint(
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
     * Crea un panel estatico con color solido o degradado y transparencia
     * @param width ancho del panel
     * @param colorIndex indice del color de inicio
     * @param colorEnd indice del color de fin
     * @param padding espaciado interno del panel
     * @param alpha nivel de transparencia (0.0 = totalmente transparente, 1.0 = opaco)
     * @return JPanel configurado
     */
    public JPanel crearPanelEstatico(int width, int colorIndex, int colorEnd, int padding, float alpha) {
        Color start = colorSelected(colorIndex);
        Color end = colorSelected(colorEnd);
        int height = defaultHeight; // Altura igual a la ventana
        return new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                if (!start.equals(end)) {
                    GradientPaint gp = new GradientPaint(
                        0, 0, start,
                        0, getHeight(), end
                    );
                    g2.setPaint(gp);
                } else {
                    g2.setPaint(start);
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                this.setPreferredSize(new Dimension(width, height));
                this.setOpaque(true);
                this.setBorder(null);
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
     * @param paddingBottom espacio superior en pixeles
     * @param paddingTop espacio inferior en pixeles
     * @param padHorizontal espacio izquierdo y derecho en pixeles
     * @return GridBagConstraints configurado
     */
    public GridBagConstraints setGridsAttributes(int x, int y, int width, int height, boolean responsive, int paddingBottom, int paddingTop, int paddingLeft, int paddingRight) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = responsive ? 1.0 : 0.0;
        gbc.insets = new Insets(paddingTop, paddingLeft, paddingBottom, paddingRight);
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
        return Color.BLACK;
    }

    public String getTxFlMatricula() { return txFlMatricula.getText().trim(); }

    public String getTxFlContrasenia() { return new String(txFlContrasenia.getPassword()).trim(); }

    public String getTxFlContraseniaVisible() { return txFlContraseniaVisible.getText().trim(); }

    public String getTxFlNombre() { return txFlNombre.getText().trim(); }

    public String getTxFlApellidoP() { return txFlApellidoP.getText().trim(); }

    public String getTxFlApellidoM() { return txFlApellidoM.getText().trim(); }

    public String getTxFlCorreo() { return txFlCorreo.getText().trim(); }

    public int getCbCuatrimestre() { return Integer.parseInt((String) cbCuatrimestre.getSelectedItem()); }

    public String getCbGrupo() { return (String) cbGrupo.getSelectedItem(); }

    public String getCbCarrera() { return (String) cbCarrera.getSelectedItem(); }

}
