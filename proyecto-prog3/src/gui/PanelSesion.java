package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.swing.*;

import domain.Usuario;
import domain.Vehiculo; // Importa la clase Vehiculo
import main.DeustoTaller;

public class PanelSesion extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelSesion(JFrame ventanaGrafica) {
		// Fuente grande para todo el texto
		Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);

		// Configurar el diseño principal como BorderLayout
		setLayout(new BorderLayout());

		// Obtener información del usuario
		Usuario usuario = DeustoTaller.getSesion();

		// **Parte superior (NORTH): Saludo, última sesión y botón**
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); // Apilado vertical
		JLabel saludo = new JLabel("¡Hola, " + usuario.getNombre() + "!");
		saludo.setFont(fuenteGrande);
		saludo.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(saludo);

		String ultimaSesionTexto = usuario.gethUltimaSesion()
				.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		JLabel ultimaSesion = new JLabel("Último inicio de sesión: " + ultimaSesionTexto);
		ultimaSesion.setFont(fuenteGrande);
		ultimaSesion.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(ultimaSesion);

		JButton botonGestor = new JButton("Acceder al gestor de sesión");
		botonGestor.setFont(fuenteGrande);
		botonGestor.setAlignmentX(CENTER_ALIGNMENT);
		botonGestor.addActionListener(e -> {
			DeustoTaller.getVSesion().openGestor(); // Abrir el gestor
			ventanaGrafica.dispose(); // Cerrar la ventana actual
		});
		panelSuperior.add(botonGestor);

		// Agregar el panel superior al área NORTH
		add(panelSuperior, BorderLayout.NORTH);

		// **Parte central (CENTER): Información del usuario y vehículos**
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS)); // Apilado vertical

		JLabel username = new JLabel("Username: " + usuario.getUsername());
		username.setFont(fuenteGrande);
		panelCentral.add(username);

		JLabel nombre = new JLabel("Nombre: " + usuario.getNombre());
		nombre.setFont(fuenteGrande);
		panelCentral.add(nombre);

		JLabel apellido = new JLabel("Apellido: " + usuario.getApellido());
		apellido.setFont(fuenteGrande);
		panelCentral.add(apellido);

		JLabel vehiculosTitulo = new JLabel("Vehículos:");
		vehiculosTitulo.setFont(fuenteGrande);
		panelCentral.add(vehiculosTitulo);

		List<Vehiculo> vehiculos = usuario.getVehiculos(); // Obtener la lista de vehículos
		if (vehiculos.isEmpty()) {
			JLabel sinVehiculos = new JLabel("No hay vehículos registrados.");
			sinVehiculos.setFont(fuenteGrande);
			panelCentral.add(sinVehiculos);
		} else {
			for (Vehiculo vehiculo : vehiculos) {
				JLabel infoVehiculo = new JLabel("- " + vehiculo.getModelo() + " (" + vehiculo.getMarca() + ", "
						+ vehiculo.getMatricula() + ")");
				infoVehiculo.setFont(fuenteGrande);
				panelCentral.add(infoVehiculo);
			}
		}

		// Agregar el panel central al área CENTER
		add(panelCentral, BorderLayout.CENTER);
	}
}
