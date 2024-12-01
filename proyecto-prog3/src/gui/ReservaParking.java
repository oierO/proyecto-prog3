package gui;

import java.awt.FlowLayout;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;

import domain.*;
import main.DeustoTaller;

public class ReservaParking extends JDialog {

	/**
	 * 
	 */
	private String planta;
	private String plaza;
	private JComboBox<Vehiculo> listaVehiculos;
	private DateTimePicker selectorFecha;
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final double TARIFA_HORA = 5.5;

	public ReservaParking(String planta, String plaza, PanelParking panel) {
		this.planta = planta;
		this.plaza = plaza;
		Font fuente = new Font("Bahnschrift", Font.BOLD, 15);
		Font fuenteTexto = new Font("Bahnschrift", Font.BOLD, 13);
		setModal(true);
		setTitle("Reservar plaza");
		this.setIconImage(new ImageIcon("resources/calendar-icon.png").getImage());
		setSize(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		JPanel pVehiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel textVehiculo = new JLabel("Vehiculo");
		JPanel pFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pConformidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bReservar = new JButton("Reservar");
		bReservar.setFont(fuenteTexto);
		JButton bCancelar = new JButton("Cancelar");
		bCancelar.setFont(fuenteTexto);
		JCheckBox conformidad = new JCheckBox("Acepto los terminos y condiciones");
		pConformidad.add(conformidad);
		conformidad.setFont(fuenteTexto);
		bReservar.addActionListener(e -> {
			if (!conformidad.isSelected()) {
				JOptionPane.showMessageDialog(this.getContentPane(),
						"No has aceptados los terminos y condiciones del servicio", "Conformidad",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				reservarPlazaBD();
				panel.cambioSeleccionPl(plaza);
				this.getContentPane().repaint();
				panel.actBotones();
				dispose();
			}
		});
		bCancelar.addActionListener(e -> dispose());
		pFecha.add(new JLabel("Fecha"));

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
		JLabel titulo = new JLabel(String.format("Reservar plaza %s en la %s", plaza, planta));

		JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titulo.setFont(fuente);
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

	private void reservarPlazaBD() {
		String sql = "INSERT INTO RESERVA_PARKING VALUES(?,?,?,?)";
		try {
			PreparedStatement stmt = DeustoTaller.getCon().prepareStatement(sql);
			stmt.setString(1, planta);
			stmt.setString(2, plaza);
			Vehiculo coche = (Vehiculo) listaVehiculos.getSelectedItem();
			stmt.setString(3, coche.getMatricula());
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			stmt.setString(4, selectorFecha.getDateTimeStrict().format(formateador));
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(this.getContentPane(), "Reserva realizada correctamente");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this.getContentPane(), "Ha ocurrido un error al procesar la reserva\n" + e,
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
