/**
 *
 * @author abiga
 */

 package com.assistuteam.assistu.view;

 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.FlowLayout;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.GridBagLayout;
 import java.awt.GridLayout;
 import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.assistuteam.assistu.controller.ControladorAdministrador;
import com.assistuteam.assistu.model.entity.Usuario;
import com.assistuteam.assistu.view.util.FramePanelBase;
 
/** @author assistu_team **/

@SuppressWarnings("all")
public class FrameAdministrador extends FramePanelBase {
 
     private final Usuario usuario;
     private ControladorAdministrador controladorAdministrador;
 
     public FrameAdministrador(Usuario usuario) {
         this.usuario = usuario;
         setTitle("AssistU - Administrador " + usuario.getNombre());
         
         try {
             this.controladorAdministrador = new ControladorAdministrador();
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al inicializar el controlador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             this.dispose();
             return;
         }
 
         configurarAcciones();
         setVisible(true);
     }
     
    
     @Override
     protected String getDashboardTitle() {
         return "Panel Administrador";
     }
 
     @Override
     protected String getUserRole() {
         return "Administrador";
     }
 
     @Override
     protected String getUserName() {
         return usuario != null ? usuario.getNombre() + " " + usuario.getApellidoPaterno() : "Nombre de Usuario";
     }
 
     @Override
     protected String getUserMatricula() {
         return usuario != null ? usuario.getMatricula() : "Matrícula";
     }
 
     @Override
     protected JButton[] getSideButtons() {
         botones = new JButton[5];
         botones[0] = setButton("Asignaciones (profesor, materia, grupo, etc.)", 18, 350, 40);
         botones[1] = setButton("Solicitudes de recursamiento", 18, 350, 40);
         botones[2] = setButton("Grupos de recursamiento", 18, 350, 40);
         botones[3] = setButton("Bandeja", 18, 350, 40);
         botones[4] = setButton("Abandonar Sesión", 18, 350, 40);
         return botones;
     }
 
     private void configurarAcciones() {
    
         botones[0].addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí se gestionarán las asignaciones."));
         botones[1].addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí se gestionarán las solicitudes de recursamiento."));
         botones[2].addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí se verán los grupos de recursamiento."));
         botones[3].addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí se mostrará la bandeja de mensajes."));
         botones[4].addActionListener(e -> {
             this.dispose();
 
         });
     }
 
     @Override
     protected JPanel buildCardsPanel() {
         JPanel mainPanel = new JPanel();
         mainPanel.setOpaque(false);
         mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
 
         JPanel panelCantidad = new JPanel(new GridBagLayout());
         panelCantidad.setOpaque(false);
         panelCantidad.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
         // Si quieres panel flotante, reemplaza por:
         // panelCantidad = crearPanelFlotante(520, 200, 4, 5, 15, 1f, 32, 32);
         // panelCantidad.setLayout(new GridBagLayout());
         
         try {
             int numeroDeAdministradores = controladorAdministrador.leerTodos().size(); 
             
             panelCantidad.add(crearTarjetaCantidad("Número de grupos:", "5"), setGridsAttributes(0, 0, 1, 1, false, 5, 5, 0, 0));
             panelCantidad.add(crearTarjetaCantidad("Número de maestros disponibles:", "15"), setGridsAttributes(1, 0, 1, 1, false, 5, 5, 0, 0));
             panelCantidad.add(crearTarjetaCantidad("Números de solicitudes:", "3"), setGridsAttributes(0, 1, 1, 1, false, 5, 5, 0, 0));
             panelCantidad.add(crearTarjetaCantidad("Número de alumnos:", "120"), setGridsAttributes(1, 1, 1, 1, false, 5, 5, 0, 0));
             panelCantidad.add(crearTarjetaCantidad("Número de materias de recursamiento:", "4"), setGridsAttributes(0, 2, 1, 1, false, 5, 5, 0, 0));
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al obtener datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
 
         JPanel panelAsignacion = new JPanel(new BorderLayout());
         panelAsignacion.setOpaque(false);
         panelAsignacion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
         JLabel lblAsignacionTitle = setLabel("Asignacion", 24, 1, 'L');
         panelAsignacion.add(lblAsignacionTitle, BorderLayout.NORTH);
 
         JPanel tablaAsignaciones = new JPanel();
         tablaAsignaciones.setLayout(new BoxLayout(tablaAsignaciones, BoxLayout.Y_AXIS));
         tablaAsignaciones.setOpaque(false);
         
         JPanel headers = new JPanel(new GridLayout(1, 5));
         headers.setOpaque(false);
         headers.add(setLabel("Maestro", 16, 1, 'C'));
         headers.add(setLabel("Materia", 16, 1, 'C'));
         headers.add(setLabel("Grupo", 16, 1, 'C'));
         headers.add(setLabel("Dia", 16, 1, 'C'));
         headers.add(setLabel("Hora", 16, 1, 'C'));
         
         tablaAsignaciones.add(headers);
 
         JPanel fila = new JPanel(new GridLayout(1, 5));
         fila.setOpaque(false);
         fila.add(setLabel("Sebastian Sosa", 14, 3, 'C'));
         fila.add(setLabel("POO", 14, 3, 'C'));
         fila.add(setLabel("B", 14, 3, 'C'));
         fila.add(setLabel("Lunes", 14, 3, 'C'));
         fila.add(setLabel("10:00 am - 14:00 pm", 14, 3, 'C'));
         
         tablaAsignaciones.add(fila);
         
         panelAsignacion.add(tablaAsignaciones, BorderLayout.CENTER);
 
         JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
         panelAcciones.setOpaque(false);
         
     
         JButton btnAnadir = setButton("Añadir asignacion", 14, 150, 30);
         btnAnadir.addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí se abrirá el diálogo para añadir una asignación."));
         panelAcciones.add(btnAnadir);
         
         panelAsignacion.add(panelAcciones, BorderLayout.SOUTH);
 
         mainPanel.add(panelCantidad);
         mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
         mainPanel.add(panelAsignacion);
 
         return mainPanel;
     }
 
     private JPanel crearTarjetaCantidad(String titulo, String valor) {
         JPanel tarjeta = new JPanel() {
             @Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 Graphics2D g2 = (Graphics2D) g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(new Color(19, 22, 34, 230));
                 g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
             }
         };
         tarjeta.setOpaque(false);
         tarjeta.setLayout(new BorderLayout());
         tarjeta.setPreferredSize(new Dimension(250, 70));
         tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
 
         JLabel lblTitulo = setLabel(titulo, 16, 3, 'L');
         lblTitulo.setForeground(UIManager.getColor("keuni.color"));
         tarjeta.add(lblTitulo, BorderLayout.NORTH);
 
         JLabel lblValor = setLabel(valor, 24, 1, 'L');
         lblValor.setForeground(UIManager.getColor("bankya.color"));
         tarjeta.add(lblValor, BorderLayout.SOUTH);
 
         return tarjeta;
     }
 }