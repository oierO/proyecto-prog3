package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame vActual,vAnterior;

	public VentanaGrafica() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();
		vActual=this;
		this.vAnterior=vAnterior;

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
		
		JPanel pUsuario = new PanelSesion();

		menuPestanas.add("Servicios", pServicios);
		menuPestanas.add("Almacen", pAlmacen);
		menuPestanas.add("Parking", pParking);
		menuPestanas.add("Preferencias", pSettings);
		menuPestanas.add("Sesión",pUsuario);
		add(menuPestanas);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				VentanaInicioSesion.logout();
			}
			
		});
		setIconImage(new ImageIcon(getClass().getResource("/res/app-icon.png")).getImage());
		setVisible(true);
	}
}
