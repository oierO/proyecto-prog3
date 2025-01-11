package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;

import javax.swing.*;

import db.ConsultasParking;
import domain.*;
import main.DeustoTaller;

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
	private JButton bLiberar;
	private JButton bModificar;
	private static PlazaParking plazaSel;
	protected static HashMap<String, HashMap<String, PlazaParking>> mapaParkings = new HashMap<String, HashMap<String, PlazaParking>>();
	static ArrayList<JToggleButton> plazasGraficas = new ArrayList<>();
	private static final List<String> plantasParking = List.of("P1", "P2", "P3");
	private Locale currentLocale;
	private static ResourceBundle bundle;
	private JLabel estadoLabel,plantaLabel,plazaLabel,matriculaLabel,finLabel,tiempoLabel;
	private static String sLibre,sPlaza,sLibre2,sOcupado,sPlazaNoDisp,sReservar,sSi,sNo,sCancelar,sOk;

	public PanelParking(Locale locale) {
		for (String planta : getPlantasparking()) {
			mapaParkings.put(planta, new HashMap<String, PlazaParking>());
		}
		;
		
		//Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("PanelParkingBundle",currentLocale);
		estadoLabel = new JLabel(bundle.getString("estadoLabel"));
		plantaLabel = new JLabel(bundle.getString("plantaLabel"));
		plazaLabel = new JLabel(bundle.getString("plazaLabel"));
		sLibre = bundle.getString("sLibre");
		sPlaza = bundle.getString("sPlaza");
		matriculaLabel = new JLabel(bundle.getString("matriculaLabel"));
		finLabel = new JLabel(bundle.getString("finLabel"));
		tiempoLabel = new JLabel(bundle.getString("tiempoLabel"));
		sLibre2  = bundle.getString("sLibre2");
		sOcupado = bundle.getString("sOcupado");
		sPlazaNoDisp = bundle.getString("sPlazaNoDisp");
		sReservar = bundle.getString("sReservar");
		sSi = bundle.getString("sSi");
		sNo = bundle.getString("sNo");
		sCancelar = bundle.getString("sCancelar");
		sOk = bundle.getString("sOk");
		
		
		//cambiar el texto de los botones de JOptionPane
		 UIManager.put("OptionPane.yesButtonText", sSi);
	     UIManager.put("OptionPane.noButtonText", sNo);
	     UIManager.put("OptionPane.cancelButtonText", sCancelar);
	     UIManager.put("OptionPane.okButtonText", sOk);
		
		
		plazas = new JPanel();
		JPanel informacion = new JPanel();
		plazas.setBackground(Color.WHITE);
		informacion.setLayout(new BoxLayout(informacion, BoxLayout.Y_AXIS));
		informacion.add(estadoLabel);
		String[] arrayPlantas = getPlantasparking().toArray(new String[getPlantasparking().size()]);
		ComboBoxModel<String> modeloPlantas = new DefaultComboBoxModel<>(arrayPlantas);
		plantas = new JComboBox<>(modeloPlantas);
		JPanel setplantaPanel = new JPanel();
		setplantaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setplantaPanel.add(plantaLabel);
		setplantaPanel.add(plantas);
		informacion.add(setplantaPanel);
		informacion.add(new JSeparator());
		informacion.add(plazaLabel);
		estado = new JTextField(sLibre);
		estado.setEditable(false);
		estado.setHorizontalAlignment(JTextField.CENTER);
		JPanel nomvehiculoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nomvehiculoPanel.add(matriculaLabel);
		matricula = new JTextField("#########", JTextField.CENTER);
		matricula.setEditable(false);
		JPanel tfinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfinPanel.add(finLabel);
		tfin = new JTextField("N/A");
		tfin.setEditable(false);
		tfinPanel.add(tfin);
		JPanel trestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		trestPanel.add(tiempoLabel);
		trest = new JTextField("N/A");
		bModificar = new JButton(bundle.getString("bModificar"));
		bModificar.setVisible(false);
		Image img = new ImageIcon("resources/images/modificar.png").getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		bModificar.setIcon(new ImageIcon(img));
		bLiberar = new JButton(bundle.getString("bModificar"));
		img = new ImageIcon("resources/images/cancelar.png").getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		bLiberar.setIcon(new ImageIcon(img));
		bLiberar.setVisible(false);

		bLiberar.addActionListener(e -> {
			int res = JOptionPane.showConfirmDialog(this, sPlaza);
			if (res == JOptionPane.YES_OPTION) {
				liberarPlaza();
				cambioSeleccionPl(plazaSel.getIdentificador());
				actBotones();
				repaint();
			}
		});
		bReservar = new JButton(bundle.getString("bReservar"));
		bReservar.setIcon(new ImageIcon("resources/images/calendar-icon.png"));
		bReservar.setEnabled(false);
		bReservar.addActionListener(e -> new ReservaParking((String) plantas.getSelectedItem(), splazaSeleccion, this,currentLocale));
		trest.setEditable(false);
		trestPanel.add(trest);
		nomvehiculoPanel.add(matricula);
		informacion.add(estado);
		informacion.add(nomvehiculoPanel);
		informacion.add(trestPanel);
		informacion.add(tfinPanel);
		informacion.add(bReservar);
		informacion.add(bModificar);
		informacion.add(bLiberar);
		plantas.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("comboBoxChanged")) {
					cambioSeleccionPl(splazaSeleccion);
					actBotones();
				}

			}

		});
		setFocusable(false);
		grupoBotones = new ButtonGroup();
		plazas.setLayout(new GridLayout(FILAS_PARKING, COLUMNAS_PARKING, 10, 10));
		plazas.setBorder(BorderFactory.createTitledBorder(sPlaza));
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
	public static String getLocalized(String texto) {
		return bundle.getString(texto);
		}

	public void cambioSeleccionPl(String codPlaza) {
		plazaSel = RendererParking.plazafromBD(codPlaza, (String) plantas.getSelectedItem());
		if (plazaSel != null) {
			matricula.setText(plazaSel.getVehiculo().getMatricula());
			estado.setText(sOcupado);
			estado.setBackground(Color.RED);
			tfin.setText(
					plazaSel.getFechaCaducidad().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
			long segundos = Duration.between(LocalDateTime.now(), plazaSel.getFechaCaducidad()).getSeconds();
			trest.setText(String.format("%sD,%sH,%sM,%sS", segundos / 5184000, (segundos % 5184000) / 3600,
					((segundos % 5184000) % 3600) / 60, ((segundos % 5184000) % 3600) % 60));
			bReservar.setEnabled(false);
			bReservar.setToolTipText(sPlazaNoDisp);
			if (DeustoTaller.getSesion().getMatriculas().contains(matricula.getText())) {
				bLiberar.setVisible(true);
				bModificar.setVisible(true);
			} else {
				bLiberar.setVisible(false);
				bModificar.setVisible(false);
			}
		} else {
			bLiberar.setVisible(false);
			matricula.setText("#########");
			estado.setText(sLibre2);
			estado.setBackground(Color.GREEN);
			tfin.setText("N/A");
			trest.setText("N/A");
			bReservar.setEnabled(true);
			bReservar.setToolTipText(sReservar);

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

	public void actBotones() {
		Enumeration<AbstractButton> eBotones = grupoBotones.getElements();
		while (eBotones.hasMoreElements()) {
			JToggleButton bTemp = (JToggleButton) eBotones.nextElement();
			bTemp.setBackground(fondoBoton(bTemp.getText()));
		}
	}

	public static PlazaParking getPlazaSel() { 
		return plazaSel;
	}
	
	private void liberarPlaza() {
		ConsultasParking.liberarPlaza();
	}

}
