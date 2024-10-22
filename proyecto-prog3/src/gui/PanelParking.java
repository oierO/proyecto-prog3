package gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import domain.PlazaParking;
import domain.Vehiculo;
import gui.RendererParking;

public class PanelParking extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int COLUMNAS_PARKING = 5;
	private static final int FILAS_PARKING = 5;
	protected static HashMap<String, HashMap<String, PlazaParking>> mapaParkings = new HashMap<String, HashMap<String, PlazaParking>>();
	static ArrayList<JToggleButton> plazasGraficas = new ArrayList<>();
	private static final List<String> plantasParking = List.of("Planta 1", "Planta 2",
			"Planta 3");

	public PanelParking() {
		add(new JLabel("ESTADO DEL PARKING"), BorderLayout.LINE_START);
		JPanel layout = new JPanel();
		JComboBox<String> plantas = new JComboBox(plantasParking.toArray());

		// JSplitPane pSeparado = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		ButtonGroup grupoBotones = new ButtonGroup();
		layout.setLayout(new GridLayout(FILAS_PARKING, COLUMNAS_PARKING, 10, 10));
		// layout
		layout.setBorder(BorderFactory.createTitledBorder("PLAZAS"));
		for (int i = 0; i <= FILAS_PARKING - 1; i++) {
			for (int t = 0; t <= COLUMNAS_PARKING - 1; t++) {
				JToggleButton boton = new JToggleButton(String.format("%s%s", (char) (t + 65), i));
				plazasGraficas.add(boton);
				grupoBotones.add(boton);
				layout.add(boton);
				boton.setBackground(RendererParking.getColor(boton.getName(), plantas.getSelectedIndex()));
			}
		}
		// RendererParking pRenderer = new RendererParking();

		// tabla.se
		add(layout, BorderLayout.EAST);
		add(plantas, BorderLayout.WEST);

	}

	public static HashMap<String, HashMap<String, PlazaParking>> getMapaParkings() {
		return mapaParkings;
	}

	public static List<String> getPlantasparking() {
		return plantasParking;
	}

	public static void main(String[] args) {
		JFrame ventana = new JFrame();
		ventana.setFont(Font.getFont("Segoe UI"));
		ventana.add(new PanelParking());
		ventana.setSize(600, 480);
		ventana.setVisible(true);
	}

}
