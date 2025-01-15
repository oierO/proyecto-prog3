package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gui.VentanaUsuario;
import main.DeustoTaller;

public class ConsultasVentanaUsuario {
    // Método para actualizar el nombre de usuario en la base de datos
    public static boolean actualizarNombreUsuario(String nuevoNombre) {
        String sql = "UPDATE USUARIO SET nombre = ? WHERE username = ?";
        try (Connection conn = DeustoTaller.getCon();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, DeustoTaller.getSesion().getUsername()); // Usamos el username como identificador
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para actualizar la contraseña en la base de datos
    public static boolean actualizarContraseña(String nuevaContraseña) {
        String sql = "UPDATE CREDENCIALES SET password = ? WHERE username = ?";
        try (Connection conn = DeustoTaller.getCon();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevaContraseña);
            pstmt.setString(2, DeustoTaller.getSesion().getUsername()); // Usamos el username como identificador
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new VentanaUsuario();
    }

}
