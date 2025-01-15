package gui;

import javax.swing.*;
import db.ConsultasVentanaUsuario;
import java.awt.*;
import java.awt.event.ActionEvent;
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
				if (ConsultasVentanaUsuario.actualizarNombreUsuario(nuevoNombre)) {
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
				if (ConsultasVentanaUsuario.actualizarContraseña(nuevaContraseña)) {
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

}
