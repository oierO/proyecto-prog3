package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaGrafica() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();

		// Pestaña Servicios
		String[] lServicios = new String[] { "Taller", "Piezas", "Diagnóstico" };
		JPanel pServicios = new JPanel();
		JPanel pSelServicios = new JPanel();

		pSelServicios.setLayout(new BoxLayout(pSelServicios, BoxLayout.Y_AXIS));
		pServicios.add(pSelServicios, FlowLayout.LEFT); // FIXME ~ pSelServicios (botones), no alineados a la izquierda.
		for (int i = 0; i < lServicios.length; i++) {
			JButton boton = new JButton(lServicios[i]);
			boton.setAlignmentX(CENTER_ALIGNMENT);
			boton.addActionListener(e -> System.out.println(boton.getText() + " Ha sido pulsado")); // FIXME ~
																									// implementar
																									// lógica
			pSelServicios.add(boton);

		}

		// Pestaña Almacen
		JPanel pAlmacen = new JPanel();

		// Pestaña Parking
		JPanel pParking = new JPanel();

		// Pestaña Preferencias
		JPanel pSettings = new JPanel();

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
