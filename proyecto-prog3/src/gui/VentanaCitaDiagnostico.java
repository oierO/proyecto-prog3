package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class VentanaCitaDiagnostico extends JFrame{
	
	private JTextField nombre;
	private JTextField telefono;
	private JFormattedTextField fechaDePedido;
	private JFormattedTextField fechaDeRealizacion;
	private JTextArea diagnosticos;
	private JButton botonReservar;
	private VentanaGrafica ventanaGrafica;
	
	
	public VentanaCitaDiagnostico() {
//		this.ventanaGrafica = ventanaGrafica;
		
		setTitle("Reservar cita");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300,400);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,2));
	
		
		//Para el nombre
		nombre = new JTextField();
		JLabel nombreJLabel = new JLabel();
		nombreJLabel.setText("Nombre: ");
		panel.add(nombreJLabel);
		panel.add(nombre);
		
		//Para el telefono
		telefono = new JTextField();
		JLabel telefonoJLabel = new JLabel();
		telefonoJLabel.setText("Telefono: ");
		panel.add(telefonoJLabel);
		panel.add(telefono);
		
		//Para la fecha del pedido 
		LocalDate fechaActual = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateado = fechaActual.format(formato);
		fechaDePedido = new JFormattedTextField();
		fechaDePedido.setText(""+ fechaFormateado);
		fechaDePedido.setEditable(false);
		JLabel fechaDePedidoJLabel = new JLabel();
		fechaDePedidoJLabel.setText("Fecha de pedido: ");
		panel.add(fechaDePedidoJLabel);
		panel.add(fechaDePedido);
		
		
		//Para la fecha de realización 
		fechaDeRealizacion = crearFechaFormateado("##/##/####");
		JLabel fechaDeRealizacionJLabel = new JLabel();
		fechaDeRealizacionJLabel.setText("Fecha(DD/MM/AAAA) : ");
		panel.add(fechaDeRealizacionJLabel);
		panel.add(fechaDeRealizacion);
		
		//Para el diagnostico
		diagnosticos = new JTextArea();
		diagnosticos.setRows(3);
		diagnosticos.setColumns(30);
		diagnosticos.setLineWrap(true);
	
		JLabel diagnosticosJLabel = new JLabel();
		diagnosticosJLabel.setText("Qué problema tiene?");
		panel.add(diagnosticosJLabel);
		panel.add(diagnosticos);
		
		//para reservar 
		botonReservar = new JButton("Reservar");
		botonReservar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Nombre: " + nombre.getText());
				System.out.println("Telefono: " + telefono.getText());
				System.out.println("Fecha de pedido: " + fechaDePedido.getText());
				System.out.println("Fecha de realización: " + fechaDeRealizacion.getText());
				System.out.println("Diagnosticos: " + diagnosticos.getText());
				
				enviarDatos();
			}
		});
		
		panel.add(botonReservar);
		
		
		
		
		add(panel);
		setVisible(true);
		
		
	}
	
	private JFormattedTextField crearFechaFormateado(String fecha) {
		JFormattedTextField fechaformateado = null; 
		
		try {
			MaskFormatter formatoFecha = new MaskFormatter(fecha);
			formatoFecha.setPlaceholderCharacter('_');
			fechaformateado = new JFormattedTextField(formatoFecha);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fechaformateado;
	}
	
	private void enviarDatos() {
		String nombreString = nombre.getText();
		String telefonoString = telefono.getText();
		String fechaString = fechaDeRealizacion.getText();
		String diagnosticosString = diagnosticos.getText();
		
		
//		ventanaGrafica.setName(nombreString);
//		ventanaGrafica.setTelefono(telefonoString);
//		ventanaGrafica.setLocalDate(fechaString);
//		ventanaGrafica.setDiagnostico(diagnosticosString);
		
		dispose();
	}
	
	public static void main(String[] args) {
		new VentanaCitaDiagnostico();
	}
	

}
