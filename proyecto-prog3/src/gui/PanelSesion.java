package gui;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.*;
import main.DeustoTaller;

public class PanelSesion extends JPanel {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JFrame ventanaGrafica;

	public PanelSesion(JFrame ventanaGrafica) {
		this.ventanaGrafica = ventanaGrafica; // Recibimos la referencia de la ventana principal
		JLabel tEncabezado = new JLabel(String.format("¡Hola, %s!", DeustoTaller.getSesion().getNombre()));
		add(tEncabezado, BorderLayout.NORTH);
		JLabel tSesion = new JLabel(String.format("Ultimo inicio de sesión: %s", DeustoTaller.getSesion()
				.gethUltimaSesion().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
		add(tSesion, BorderLayout.NORTH);
		JButton bAdminSesion = new JButton("Acceder al gestor de sesión");
		bAdminSesion.addActionListener(e -> {
			DeustoTaller.getVSesion().openGestor(); // Lógica de abrir gestor de sesión
			ventanaGrafica.dispose(); // Cierra la ventana gráfica actual
		});
		add(bAdminSesion, BorderLayout.CENTER);
	}
}
