package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.JToggleButton.ToggleButtonModel;

import domain.*;

public class PanelParking extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int COLUMNAS_PARKING = 5;
	private static final int FILAS_PARKING = 5;
	private JComboBox<String> plantas;
	private JPanel plazas;
	private JTextField matricula;
	private JTextField estado;
	private JTextField tfin;
	private JButton bReservar;
	private JTextField trest;
	private ButtonGroup grupoBotones;
	private String splazaSeleccion;
	protected static HashMap<String, HashMap<String, PlazaParking>> mapaParkings = new HashMap<String, HashMap<String, PlazaParking>>();
	static ArrayList<JToggleButton> plazasGraficas = new ArrayList<>();
	private static final List<String> plantasParking = List.of("Planta 1", "Planta 2", "Planta 3");

	public PanelParking() {
		for (String planta : getPlantasparking()) {
			mapaParkings.put(planta, new HashMap<String, PlazaParking>());
		}
		;

		plazas = new JPanel();
		JPanel informacion = new JPanel();
		plazas.setBackground(Color.WHITE);
		informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));
		informacion.add(new JLabel("ESTADO DEL PARKING"));
		String[] arrayPlantas = getPlantasparking().toArray(new String[getPlantasparking().size()]);
		ComboBoxModel<String> modeloPlantas = new DefaultComboBoxModel<>(arrayPlantas);
		plantas = new JComboBox<>(modeloPlantas);
		JPanel setplantaPanel = new JPanel();
		setplantaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setplantaPanel.add(new JLabel("Planta: "));
		setplantaPanel.add(plantas);
		informacion.add(setplantaPanel);
		informacion.add(new JSeparator());
		informacion.add(new JLabel("PLAZA SELECCIONADA"));
		estado = new JTextField("¿Libre?");
		estado.setEditable(false);
		estado.setHorizontalAlignment(JTextField.CENTER);
		JPanel nomvehiculoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nomvehiculoPanel.add(new JLabel("Matricula: "));
		matricula = new JTextField("#########", JTextField.CENTER);
		matricula.setEditable(false);
		JPanel tfinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfinPanel.add(new JLabel("Fin reserva: "));
		tfin = new JTextField("N/A");
		tfin.setEditable(false);
		tfinPanel.add(tfin);
		JPanel trestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		trestPanel.add(new JLabel("Tiempo restante: "));
		trest = new JTextField("N/A");
		bReservar = new JButton("Reservar");
		bReservar.setIcon(new ImageIcon("resources/images/calendar-icon.png"));
		bReservar.setEnabled(false);
		bReservar.addActionListener(e -> new ReservaParking((String) plantas.getSelectedItem(), splazaSeleccion,this));
		trest.setEditable(false);
		trestPanel.add(trest);
		nomvehiculoPanel.add(matricula);
		informacion.add(estado);
		informacion.add(nomvehiculoPanel);
		informacion.add(trestPanel);
		informacion.add(tfinPanel);
		informacion.add(bReservar);
		plantas.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("comboBoxChanged")) {
					cambioSeleccionPl(splazaSeleccion);
					Enumeration<AbstractButton> eBotones = grupoBotones.getElements();
					while (eBotones.hasMoreElements()) {
						JToggleButton bTemp = (JToggleButton) eBotones.nextElement();
						bTemp.setBackground(fondoBoton(bTemp.getText()));
					}

				}

			}
		});
		setFocusable(false);
		grupoBotones = new ButtonGroup();
		plazas.setLayout(new GridLayout(FILAS_PARKING, COLUMNAS_PARKING, 10, 10));
		plazas.setBorder(BorderFactory.createTitledBorder("PLAZAS"));
		for (int i = 0; i <= FILAS_PARKING - 1; i++) {
			for (int t = 0; t <= COLUMNAS_PARKING - 1; t++) {
				JToggleButton boton = new JToggleButton(String.format("%s%s", (char) (t + 65), i));
				boton.setToolTipText(boton.getText());
				boton.addActionListener(e -> {
					cambioSeleccionPl(boton.getText());
					splazaSeleccion = ((JToggleButton) e.getSource()).getText();
				});
				plazasGraficas.add(boton);
				grupoBotones.add(boton);
				plazas.add(boton);
				boton.setBackground(fondoBoton(boton.getText()));
			}
		}

		// Añadido informacion adicional en el panel
		add(plazas, BorderLayout.CENTER);
		add(informacion, BorderLayout.WEST);

	}

	public void cambioSeleccionPl(String codPlaza) {
		PlazaParking plazaSel = RendererParking.plazafromBD(codPlaza, (String) plantas.getSelectedItem());
		if (plazaSel != null) {
			matricula.setText(plazaSel.getVehiculo().getMatricula());
			estado.setText("Ocupado");
			estado.setBackground(Color.RED);
			tfin.setText(
					plazaSel.getFechaCaducidad().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
			long segundos = Duration.between(LocalDateTime.now(), plazaSel.getFechaCaducidad()).getSeconds();
			trest.setText(String.format("%sD,%sH,%sM,%sS", segundos / 5184000, (segundos % 5184000) / 3600,
					((segundos % 5184000) % 3600) / 60, ((segundos % 5184000) % 3600) % 60));
			bReservar.setEnabled(false);
			bReservar.setToolTipText("Plaza no disponible para la reserva");
		} else {
			matricula.setText("#########");
			estado.setText("Libre");
			estado.setBackground(Color.GREEN);
			tfin.setText("N/A");
			trest.setText("N/A");
			bReservar.setEnabled(true);
			bReservar.setToolTipText("Reservar plaza");

		}
		revalidate();
		repaint();
	}

	private Color fondoBoton(String plaza) {
		return RendererParking.getColor(plaza, (String) plantas.getSelectedItem());
	}

	public static HashMap<String, HashMap<String, PlazaParking>> getMapaParkings() {
		return mapaParkings;
	}

	public static List<String> getPlantasparking() {
		return plantasParking;
	}

}
