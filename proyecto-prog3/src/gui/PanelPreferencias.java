package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import main.DeustoTaller;
import java.util.ResourceBundle;
public class PanelPreferencias extends JPanel {
   private static final long serialVersionUID = 1L;
   private ResourceBundle bundle; // Uso de ResourceBundle ayudado por ChatGPT
   private String[] lPreferencias;
   private String usuario;
   private JTabbedPane pestañas;
   public PanelPreferencias(ResourceBundle bundle, String usuario) {
       this.bundle = bundle; // Uso de ResourceBundle ayudado por ChatGPT
       this.usuario = usuario;
       this.lPreferencias = new String[] {
           bundle.getString("sNotificaciones"),
           bundle.getString("sHistorial"),
           bundle.getString("sValoraciones"),
           bundle.getString("sSoporte")
       };
       setLayout(new BorderLayout());
       // Panel izquierdo con botones de preferencias
       JPanel botonesPrefer = new JPanel(new GridLayout(lPreferencias.length, 1));
       botonesPrefer.setBorder(new TitledBorder(bundle.getString("sOperaciones")));
       add(botonesPrefer, BorderLayout.WEST);
       // Panel derecho con pestañas
       pestañas = new JTabbedPane();
       add(pestañas, BorderLayout.CENTER);
       for (String pref : lPreferencias) {
           JButton botonPref = new JButton(pref);
           botonPref.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   manejarPreferencia(pref);
               }
           });
           botonesPrefer.add(botonPref);
       }
   }
   private void manejarPreferencia(String pref) {
       pestañas.removeAll(); // Limpiar todas las pestañas
       if (pref.equals(bundle.getString("sNotificaciones"))) {
           pestañas.addTab("Leer Notificaciones", crearPanelLeerNotificaciones());
           if (usuario.equals("deustotaller")) {
               pestañas.addTab("Enviar Notificaciones", crearPanelEnviarNotificaciones());
           }
       } else if (pref.equals(bundle.getString("sHistorial"))) {
           pestañas.addTab("Historial", crearPanelHistorial());
       } else if (pref.equals(bundle.getString("sValoraciones"))) {
           pestañas.addTab("Valoraciones", crearPanelValoraciones());
       } else if (pref.equals(bundle.getString("sSoporte"))) {
           pestañas.addTab("Soporte", crearPanelSoporte());
       }
   }
   // Método para actualizar el contenido dinámicamente
   public void actualizarContenido() {
       pestañas.removeAll(); // Limpiar todas las pestañas existentes
       pestañas.addTab("Notificaciones", crearPanelLeerNotificaciones());
       if (usuario.equals("deustotaller")) {
           pestañas.addTab("Enviar Notificaciones", crearPanelEnviarNotificaciones());
       }
   }
   private JPanel crearPanelValoraciones() {
       JPanel panelValoraciones = new JPanel(new BorderLayout());
       JPanel panelCalificacion = new JPanel(new GridLayout(1, 5, 10, 10));
       ButtonGroup grupoCalificacion = new ButtonGroup();
       for (int i = 1; i <= 5; i++) {
           JRadioButton radioCalificacion = new JRadioButton(String.valueOf(i));
           grupoCalificacion.add(radioCalificacion);
           panelCalificacion.add(radioCalificacion);
       }
       panelValoraciones.add(panelCalificacion, BorderLayout.NORTH);
       JTextArea campoComentario = new JTextArea(5, 20);
       JScrollPane scrollComentario = new JScrollPane(campoComentario);
       panelValoraciones.add(scrollComentario, BorderLayout.CENTER);
       JButton botonEnviar = new JButton(bundle.getString("botonEnviar"));
       botonEnviar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int valoracion = obtenerCalificacion(panelCalificacion);
               String comentario = campoComentario.getText().trim();
               if (valoracion == -1) {
                   JOptionPane.showMessageDialog(panelValoraciones, bundle.getString("sSelCal"));
                   return;
               }
               JOptionPane.showMessageDialog(panelValoraciones, "Valoración enviada: " + valoracion + "\nComentario: " + comentario);
           }
       });
       panelValoraciones.add(botonEnviar, BorderLayout.SOUTH);
       return panelValoraciones;
   }
   private int obtenerCalificacion(JPanel panelCalificacion) {
       for (Component comp : panelCalificacion.getComponents()) {
           if (comp instanceof JRadioButton) {
               JRadioButton radio = (JRadioButton) comp;
               if (radio.isSelected()) {
                   return Integer.parseInt(radio.getText());
               }
           }
       }
       return -1;
   }
   private JPanel crearPanelSoporte() {
       JPanel panelSoporte = new JPanel(new BorderLayout());
       JLabel lblEmail = new JLabel(bundle.getString("sCorreoElectronico") + " " + bundle.getString("sCorreo"));
       JLabel lblTelefono = new JLabel(bundle.getString("sTelefono") + " " + bundle.getString("sTelN"));
       panelSoporte.add(lblEmail, BorderLayout.NORTH);
       panelSoporte.add(lblTelefono, BorderLayout.CENTER);
       return panelSoporte;
   }
   // Método para leer notificaciones
   private JPanel crearPanelLeerNotificaciones() {
       JPanel panel = new JPanel(new BorderLayout());
       JTextArea areaNotificaciones = new JTextArea(10, 30);
       areaNotificaciones.setEditable(false);
       areaNotificaciones.setText("1. Bienvenido a DeustoTaller\n2. Descuento del 10% en revisiones\n3. Aviso: Mantenimiento del sistema el 20/01/2025");
       JScrollPane scroll = new JScrollPane(areaNotificaciones);
       panel.add(scroll, BorderLayout.CENTER);
       return panel;
   }
   // Método para enviar notificaciones
   private JPanel crearPanelEnviarNotificaciones() {
       JPanel panel = new JPanel(new BorderLayout());
       JLabel lblTitulo = new JLabel("Título de la notificación:");
       JTextField tfTitulo = new JTextField(20);
       JLabel lblMensaje = new JLabel("Mensaje de la notificación:");
       JTextArea taMensaje = new JTextArea(5, 30);
       taMensaje.setLineWrap(true);
       taMensaje.setWrapStyleWord(true);
       JButton btnEnviar = new JButton("Enviar Notificación");
       btnEnviar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String titulo = tfTitulo.getText().trim();
               String mensaje = taMensaje.getText().trim();
               if (!titulo.isEmpty() && !mensaje.isEmpty()) {
                   enviarNotificacion(titulo, mensaje);
                   JOptionPane.showMessageDialog(panel, "Notificación enviada a todos los usuarios.");
               } else {
                   JOptionPane.showMessageDialog(panel, "Por favor, completa todos los campos.");
               }
           }
       });
       JPanel panelFormulario = new JPanel(new GridLayout(3, 2));
       panelFormulario.add(lblTitulo);
       panelFormulario.add(tfTitulo);
       panelFormulario.add(lblMensaje);
       panelFormulario.add(new JScrollPane(taMensaje));
       panelFormulario.add(new JLabel());
       panelFormulario.add(btnEnviar);
       panel.add(panelFormulario, BorderLayout.CENTER);
       return panel;
   }
   private void enviarNotificacion(String titulo, String mensaje) {
       String dbPath = "jdbc:sqlite:" + DeustoTaller.getLocDB();
       String sql = "INSERT INTO NOTIFICACIONES (titulo, mensaje, fecha) VALUES (?, ?, datetime('now'))";
       try (Connection connection = DriverManager.getConnection(dbPath);
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
           pstmt.setString(1, titulo);
           pstmt.setString(2, mensaje);
           pstmt.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Error al enviar la notificación.");
       }
   }
   // Método para crear el panel del historial
   private JPanel crearPanelHistorial() {
       JPanel panelHistorial = new JPanel(new BorderLayout());
       // Datos simulados para el historial (sin notificaciones leídas)
       String[] columnas = {"Fecha", "Actividad"};
       String[][] datos = {
           {"2025-01-10", "Revisión del coche"},
           {"2025-01-08", "Cambio de aceite"}
       };
       JTable tablaHistorial = new JTable(datos, columnas);
       JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
       panelHistorial.add(scrollTabla, BorderLayout.CENTER);
       return panelHistorial;
   }
}
