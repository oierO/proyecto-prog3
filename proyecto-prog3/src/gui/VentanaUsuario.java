package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.DeustoTaller;

public class VentanaUsuario extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaUsuario() {
        setTitle("Configuración de Usuario");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        // Botón para cambiar el nombre de usuario
        JButton btnCambiarNombre = new JButton("Cambiar Nombre de Usuario");
        btnCambiarNombre.addActionListener((ActionEvent e) -> {
            String nuevoNombre = JOptionPane.showInputDialog("Introduce el nuevo nombre de usuario:");
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                if (actualizarNombreUsuario(nuevoNombre)) {
                    JOptionPane.showMessageDialog(this, "Nombre de usuario actualizado a: " + nuevoNombre);
                    DeustoTaller.getSesion().setNombre(nuevoNombre); // Actualizamos el nombre en la sesión
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el nombre de usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nombre de usuario no puede estar vacío.");
            }
        });

        // Botón para cambiar la contraseña
        JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");
        btnCambiarContraseña.addActionListener((ActionEvent e) -> {
            String nuevaContraseña = JOptionPane.showInputDialog("Introduce la nueva contraseña:");
            if (nuevaContraseña != null && !nuevaContraseña.trim().isEmpty()) {
                if (actualizarContraseña(nuevaContraseña)) {
                    JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la contraseña.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía.");
            }
        });

        add(btnCambiarNombre);
        add(btnCambiarContraseña);

        setVisible(true);
    }

    // Método para actualizar el nombre de usuario en la base de datos
    private boolean actualizarNombreUsuario(String nuevoNombre) {
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
    private boolean actualizarContraseña(String nuevaContraseña) {
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
