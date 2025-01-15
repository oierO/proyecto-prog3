package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import main.DeustoTaller;

public class ConsultasVentanaPregunta {

    public synchronized void enviarPregunta(String tema, String pregunta, String usuario, Object frame) {
        String sql = "INSERT INTO PREGUNTAS (tema, pregunta, usuario) VALUES (?, ?, ?)";
        Connection connection = null;
        try {
            connection = DeustoTaller.getCon();
            configurarConexion(connection);

            insertarPregunta(connection, sql, tema, pregunta, usuario);
            JOptionPane.showMessageDialog((java.awt.Frame) frame, "Pregunta enviada exitosamente.");
            // Close frame is not here anymore, it is now called from VentanaPregunta

        } catch (SQLException e) {
            e.printStackTrace();
             JOptionPane.showMessageDialog((java.awt.Frame) frame, "Error al enviar la pregunta. La base de datos está bloqueada.");
        } finally {
            //cerrarConexion(connection);
        }
    }

    private void configurarConexion(Connection connection) throws SQLException {
          try (Statement stmt = connection.createStatement()) {
              stmt.execute("PRAGMA busy_timeout = 10000"); // Espera hasta 10 segundos si está bloqueado
              stmt.execute("PRAGMA journal_mode = WAL"); // Activa WAL para evitar bloqueos de escritura
          }
    }

    private void insertarPregunta(Connection connection, String sql, String tema, String pregunta, String usuario) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tema);
            pstmt.setString(2, pregunta);
            pstmt.setString(3, usuario);
            pstmt.executeUpdate();
        }
    }

//    private void cerrarConexion(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
}