package gui;

import java.awt.FlowLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;

import db.ConsultasParking;
import domain.*;
import main.DeustoTaller;

public class ReservaParking extends JDialog {

	/**
	 * 
	 */
	private static String planta;
	private static String plaza;
	private static JComboBox<Vehiculo> listaVehiculos;
	private static DateTimePicker selectorFecha;
	public static DateTimePicker getSelectorFecha() {
		return selectorFecha;
	}
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final double TARIFA_HORA = 5.5;
	private Locale currentLocale;
	private static ResourceBundle bundle;
	private String sTitulo,sNoAcept,sConfirmidad,sReserPlaza,sEnLa;
	private JLabel textVehiculo,fechaLabel;
	private JButton bReservar,bCancelar;
	private JCheckBox conformidad;
	
	
	public ReservaParking(String plantarec, String plazarec, PanelParking panel,Locale locale) {
		//Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("ReservaParkingBundle",currentLocale);
		sTitulo = bundle.getString("sTitulo");
		sNoAcept = bundle.getString("sNoAcept");
		sConfirmidad = bundle.getString("sConfirmidad");
		fechaLabel = new JLabel(bundle.getString("fechaLabel"));
		sReserPlaza = bundle.getString("sReserPlaza");
		sEnLa = bundle.getString("sEnLa");
		
		ReservaParking.planta = plantarec;
		ReservaParking.plaza = plazarec;
//		Font fuente = new Font("Bahnschrift", Font.BOLD, 15);
//		Font fuenteTexto = new Font("Bahnschrift", Font.BOLD, 13);
		setModal(true);
		setTitle(sTitulo);
		this.setIconImage(new ImageIcon("resources/calendar-icon.png").getImage());
		setSize(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		JPanel pVehiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		textVehiculo = new JLabel(bundle.getString("textVehiculo"));
		JPanel pFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pConformidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bReservar = new JButton(bundle.getString("bReservar"));
//		bReservar.setFont(fuenteTexto);
		bCancelar = new JButton(bundle.getString("bCancelar"));
//		bCancelar.setFont(fuenteTexto);
		conformidad = new JCheckBox(bundle.getString("conformidad"));
		pConformidad.add(conformidad);
//		conformidad.setFont(fuenteTexto);
		bReservar.addActionListener(e -> {
			if (!conformidad.isSelected()) {
				JOptionPane.showMessageDialog(this.getContentPane(),
						sNoAcept, sConfirmidad,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				ConsultasParking.reservarPlazaBD();
				panel.cambioSeleccionPl(plazarec);
				this.getContentPane().repaint();
				panel.actBotones();
				dispose();
			}
		});
		bCancelar.addActionListener(e -> dispose());
		pFecha.add(fechaLabel);

		pVehiculo.add(textVehiculo);
		ComboBoxModel<Vehiculo> modeloVehiculos = new DefaultComboBoxModel<Vehiculo>(DeustoTaller.getSesion()
				.getVehiculos().toArray(new Vehiculo[DeustoTaller.getSesion().getVehiculos().size()]));
		listaVehiculos = new JComboBox<Vehiculo>(modeloVehiculos);
		pVehiculo.add(listaVehiculos);
		selectorFecha = new DateTimePicker();

		selectorFecha.setDateTimePermissive(LocalDateTime.now().plusHours(1));
		DatePickerSettings confSelectorFecha = new DatePickerSettings();
		selectorFecha.getDatePicker().setSettings(confSelectorFecha);
		confSelectorFecha.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
		JPanel pTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel titulo = new JLabel(String.format("%s %s %s %s", sReserPlaza,plazarec, sEnLa,plantarec));

		JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
//		titulo.setFont(fuente);
		pFecha.add(selectorFecha);
		pTitulo.add(titulo);
		add(pTitulo);
		add(pVehiculo);
		add(pFecha);
		pBotones.add(bCancelar);
		pBotones.add(bReservar);
		add(pConformidad);
		add(pBotones);
		setVisible(true);
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && e.getKeyCode() == KeyEvent.VK_C) {
					dispose();
				}
				super.keyPressed(e);
			}

		});

	}
	public static JComboBox<Vehiculo> getlistaVehiculos() {
		return listaVehiculos;
	}
	public static String getPlaza() {
		return plaza;
	}
	public static String getPlanta() {
		return planta;
	}
	public static String getLocalized(String texto) {
		return bundle.getString(texto);
	}
}
