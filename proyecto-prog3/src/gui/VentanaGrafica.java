package gui;

import javax.swing.*;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaGrafica() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();
		JPanel pServicios = new JPanel(); // Pestaña Servicios
		JPanel pAlmacen = new JPanel(); // Pestaña Almacen
		JPanel pParking = new JPanel(); // Pestaña Parking
		JPanel pSettings = new JPanel(); // Pestaña Preferencias
		menuPestanas.add("Servicios", pServicios);
		menuPestanas.add("Almacen", pAlmacen);
		menuPestanas.add("Parking", pParking);
		menuPestanas.add("Preferencias", pSettings);
		add(menuPestanas);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/res/app-icon.png")).getImage());
		setVisible(true);
	}

	public static void main(String[] args) {
		new VentanaGrafica();
	}

}
