package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.DeustoTaller;
import javax.swing.JOptionPane;

public class ConsultasPanelPreferencias {


    public synchronized void enviarNotificacion(String titulo, String mensaje, Object frame) {
        String sql = "INSERT INTO NOTIFICACIONES (titulo, mensaje, fecha) VALUES (?, ?, datetime('now'))";
       try (Connection connection = DeustoTaller.getCon();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
             insertarNotificacion(pstmt, titulo, mensaje);
              JOptionPane.showMessageDialog((java.awt.Frame) frame, "Notificacion enviada correctamente.");
         } catch (SQLException e) {
            e.printStackTrace();
             JOptionPane.showMessageDialog((java.awt.Frame) frame, "Error al enviar la notificación.");
        }
    }
    
    private void insertarNotificacion(PreparedStatement pstmt, String titulo, String mensaje) throws SQLException {
          pstmt.setString(1, titulo);
          pstmt.setString(2, mensaje);
          pstmt.executeUpdate();
    }


    public String[][] cargarDatosHistorial(String usuario) {
        String sql = "SELECT fecha, actividad FROM Historial WHERE usuario = ?";
        String[][] datos = null;
      try(Connection connection = DeustoTaller.getCon();
           PreparedStatement pstmt = connection.prepareStatement(sql)){
          
          datos = obtenerDatosHistorial(pstmt, usuario);
      }catch (SQLException e) {
          e.printStackTrace();
          datos = new String[][] {{"Error", "No se pudieron cargar los datos"}};
      }
        return datos;
    }

     private String[][] obtenerDatosHistorial(PreparedStatement pstmt, String usuario) throws SQLException {
         pstmt.setString(1, usuario);
          try (ResultSet rs = pstmt.executeQuery()) {
             return cargarDatosDesdeResultSet(rs);
         }
     }
    
    private String[][] cargarDatosDesdeResultSet(ResultSet rs) throws SQLException {
        rs.last();
        int numFilas = rs.getRow();
        rs.beforeFirst();
        String[][] datos = new String[numFilas][2];
        int i = 0;
        while (rs.next()) {
            datos[i][0] = rs.getString("fecha");
            datos[i][1] = rs.getString("actividad");
            i++;
        }
        return datos;
    }

}