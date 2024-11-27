package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.DefaultFormatterFactory;

import org.jdatepicker.JDatePicker;

import domain.*;
import main.DeustoTaller;

public class ReservaParking extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate fReserva;

	public ReservaParking(String planta, String plaza) {
		System.out.println(plaza);
		setModal(true);
		setTitle("Reservar plaza");
		this.setIconImage(new ImageIcon("resources/calendar-icon.png").getImage());
		setSize(400, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		JPanel pVehiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel textVehiculo = new JLabel("Vehiculo");
		JPanel pFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bReservar = new JButton("Reservar");

		pFecha.add(new JLabel("Fecha"));

		pVehiculo.add(textVehiculo);
		ComboBoxModel<Vehiculo> modeloVehiculos = new DefaultComboBoxModel<Vehiculo>(DeustoTaller.getSesion()
				.getVehiculos().toArray(new Vehiculo[DeustoTaller.getSesion().getVehiculos().size()]));
		JComboBox<Vehiculo> listaVehiculos = new JComboBox<Vehiculo>(modeloVehiculos);
		pVehiculo.add(listaVehiculos);
		JDatePicker selector = new JDatePicker();
		LocalDate currentDate = LocalDate.now();
		selector.getModel().setDate(currentDate.getYear(), currentDate.getMonthValue() - 1,
				currentDate.getDayOfMonth());
		selector.getModel().setSelected(true);

		selector.getFormattedTextField().setFormatterFactory(new AbstractFormatterFactory() {

			@Override
			public AbstractFormatter getFormatter(JFormattedTextField tf) {
				AbstractFormatter pr = new AbstractFormatter() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Object stringToValue(String text) throws ParseException {
						String[] split = text.split("-");
						GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
						return cal;
					}

					@Override
					public String valueToString(Object value) throws ParseException {
						GregorianCalendar cal = (GregorianCalendar) value;
						String st = String.format("%s-%s-%s", cal.toZonedDateTime().getYear(),
								cal.toZonedDateTime().getMonthValue(), cal.toZonedDateTime().getDayOfMonth());
						return st;
					}

				};
				return pr;
			}

		});
		GregorianCalendar calendar = (GregorianCalendar) selector.getModel().getValue();
		fReserva = calendar.toZonedDateTime().toLocalDate();

		// selector.addActionListener(e ->
		// System.out.println(selector.getFormattedTextField().getText()));
		JPanel pTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titulo = new JLabel(String.format("Reservar plaza %s-%s...", plaza, planta));
		Font fuente = new Font("Consolas", Font.BOLD, 15);
		titulo.setFont(fuente);
		pFecha.add(selector);
		pTitulo.add(titulo);
		add(pTitulo);
		add(Box.createVerticalStrut(5));
		add(pVehiculo);
		add(Box.createVerticalStrut(5));
		add(pFecha);
		add(Box.createVerticalStrut(5));
		add(bReservar);
		add(Box.createVerticalStrut(5));
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
}
