package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import main.DeustoTaller;


//CONSULTA BD



public class VentanaPregunta extends JFrame {
   private static final long serialVersionUID = 1L;
   private String usuario;
   public VentanaPregunta(String usuario) {
       setTitle("Enviar Pregunta");
       setSize(400, 300);
       setLocationRelativeTo(null);
       this.usuario = usuario;
       // Panel principal
       JPanel panelPrincipal = new JPanel();
       panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
       // Panel 1: Tema
       JPanel panelTema = new JPanel(new FlowLayout(FlowLayout.LEFT));
       JLabel lblTema = new JLabel("Tema de la pregunta:");
       String[] temas = {"Cuenta", "Servicios", "Otro"};
       JComboBox<String> comboTemas = new JComboBox<>(temas);
       panelTema.add(lblTema);
       panelTema.add(comboTemas);
       // Panel 2: Pregunta
       JPanel panelPregunta = new JPanel(new FlowLayout(FlowLayout.LEFT));
       JLabel lblPregunta = new JLabel("Escribe tu pregunta:");
       JTextArea taPregunta = new JTextArea(5, 25);
       taPregunta.setLineWrap(true);
       taPregunta.setWrapStyleWord(true);
       JScrollPane scrollPregunta = new JScrollPane(taPregunta);
       panelPregunta.add(lblPregunta);
       panelPregunta.add(scrollPregunta);
       // Panel 3: Botón Enviar
       JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
       JButton btnEnviar = new JButton("Enviar Pregunta");
       btnEnviar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String tema = (String) comboTemas.getSelectedItem();
               String pregunta = taPregunta.getText().trim();
               if (!pregunta.isEmpty()) {
                   enviarPregunta(tema, pregunta, usuario);
               } else {
                   JOptionPane.showMessageDialog(null, "Por favor, escribe una pregunta.");
               }
           }
       });
       panelBoton.add(btnEnviar);
       // Añadir los paneles al panel principal
       panelPrincipal.add(panelTema);
       panelPrincipal.add(panelPregunta);
       panelPrincipal.add(panelBoton);
       // Añadir el panel principal al JFrame
       add(panelPrincipal);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       setVisible(true);
   }
   private synchronized void enviarPregunta(String tema, String pregunta, String usuario) {
       String dbPath = DeustoTaller.getLocDB();
       String sql = "INSERT INTO PREGUNTAS (tema, pregunta, usuario) VALUES (?, ?, ?)";
       Connection connection = null;
       try {
           connection = DriverManager.getConnection(dbPath);
           // Configurar timeout para evitar bloqueos y habilitar WAL
           try (Statement stmt = connection.createStatement()) {
               stmt.execute("PRAGMA busy_timeout = 10000"); // Espera hasta 10 segundos si está bloqueado
               stmt.execute("PRAGMA journal_mode = WAL"); // Activa WAL para evitar bloqueos de escritura
           }// Ayudado con ChatGPT
           // Insertar la pregunta
           try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
               pstmt.setString(1, tema);
               pstmt.setString(2, pregunta);
               pstmt.setString(3, usuario);
               pstmt.executeUpdate();
               JOptionPane.showMessageDialog(this, "Pregunta enviada exitosamente.");
               dispose(); // Cerrar la ventana después de enviar
           }
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error al enviar la pregunta. La base de datos está bloqueada.");
       } finally {
           cerrarConexion(connection);
       }
   }
   private void cerrarConexion(Connection connection) {
       if (connection != null) {
           try {
               connection.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
   }
}
