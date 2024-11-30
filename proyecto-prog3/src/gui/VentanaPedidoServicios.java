package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import domain.PedidoServicios;

public class VentanaPedidoServicios extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombre;
	private JFormattedTextField telefono;
	private JFormattedTextField fechaDePedido;
	private JFormattedTextField fechaDeRealizacion;
	private JTextArea informacionAdicional;
	private JButton botonReservar;
	private VentanaGrafica ventanaGrafica;
	private JLabel nombreJLabel;
	private JLabel telefonoJLabel;
	private JLabel fechaDePedidoJLabel;
	private JLabel fechaDeRealizacionJLabel;
	private JLabel informacionAdicionalJLabel;
	
	
	
	
	public VentanaPedidoServicios(ArrayList<PedidoServicios> listaServiciosPedidos,ArrayList<String> serviciosElegidos,String idioma) {

		//Configuraciones de la ventana
		setTitle("Reservar cita");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500,600);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,2));
	
		if(idioma.equals("Español")) {
			nombreJLabel = new JLabel("Nombre: ");
			telefonoJLabel = new JLabel("Telefono: ");
			fechaDePedidoJLabel = new JLabel("Fecha de pedido: ");
			fechaDeRealizacionJLabel = new JLabel("Fecha(DD/MM/AAAA) : ");
			informacionAdicionalJLabel = new JLabel("Información adicional");
		} else if (idioma.equals("Ingles")) {
			nombreJLabel = new JLabel("Name: ");
			telefonoJLabel = new JLabel("Phone number: ");
			fechaDePedidoJLabel = new JLabel("Order date: ");
			fechaDeRealizacionJLabel = new JLabel("Date(DD/MM/YYYY) : ");
			informacionAdicionalJLabel = new JLabel("Additional information");
		} else if (idioma.equals("Chino")) {
			nombreJLabel = new JLabel("名字: ");
			telefonoJLabel = new JLabel("电话号码: ");
			fechaDePedidoJLabel = new JLabel("订单日期: ");
			fechaDeRealizacionJLabel = new JLabel("日期(日/月/年) : ");
			informacionAdicionalJLabel = new JLabel("补充: ");
		}
		
		
		//Para el nombre
		nombre = new JTextField();
		panel.add(nombreJLabel);
		panel.add(nombre);
		
		//Para el telefono
		panel.add(telefonoJLabel);
        DecimalFormat formatoVisual = new DecimalFormat("#########");    
        NumberFormatter formatoEntrada = new NumberFormatter(formatoVisual) {
            
			private static final long serialVersionUID = 1L;

			@Override
            public Object stringToValue(String text) throws ParseException {

                if (text == null || text.length() == 0) {
                    return null;
                }

                if (text.length() > 9 ) {
                    throw new ParseException("El telefono introducido no es correcto", 0);
                }
                return super.stringToValue(text);
            }
        };
        
        formatoEntrada.setValueClass(Integer.class); 
        formatoEntrada.setAllowsInvalid(false); 
        formatoEntrada.setOverwriteMode(true); 
        formatoEntrada.setCommitsOnValidEdit(true); 
		telefono = new JFormattedTextField(formatoEntrada);
		panel.add(telefono);
		
		
		
		//Para la fecha del pedido 
		LocalDate fechaActual = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateado = fechaActual.format(formato);
		fechaDePedido = new JFormattedTextField();
		fechaDePedido.setText(""+ fechaFormateado);
		fechaDePedido.setEditable(false);
		panel.add(fechaDePedidoJLabel);
		panel.add(fechaDePedido);
		
		
		//Para la fecha de realización 
		fechaDeRealizacion = crearFechaFormateado("##/##/####");
		panel.add(fechaDeRealizacionJLabel);
		panel.add(fechaDeRealizacion);
		
		//Para el información adicional
		informacionAdicional = new JTextArea();
		informacionAdicional.setRows(3);
		informacionAdicional.setColumns(30);
		informacionAdicional.setLineWrap(true);
	
		panel.add(informacionAdicionalJLabel);
		panel.add(informacionAdicional);
		
		//para reservar 
		botonReservar = new JButton("Reservar");
		botonReservar.addActionListener(new ActionListener() {
			//Esto no cambia según el idioma
			//Solo estoy sacando por consola lo que ha rellenado el usuario en el formulario
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Nombre: " + nombre.getText());
				System.out.println("Telefono: " + telefono.getText());
				System.out.println("Fecha de pedido: " + fechaDePedido.getText());
				System.out.println("Fecha de realización: " + fechaDeRealizacion.getText());
				System.out.println("Información adicional: " + informacionAdicional.getText());
				
				PedidoServicios pedido = new PedidoServicios(nombre.getText(), Integer.parseInt(telefono.getText()), fechaActual, convertirTextoALocalDate(fechaDeRealizacion.getText(), "dd/MM/yyyy"), serviciosElegidos,informacionAdicional.getText() ); 
				listaServiciosPedidos.add(pedido);
				System.out.println(listaServiciosPedidos);
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
	
	
	private static LocalDate convertirTextoALocalDate (String fechaEnTexto, String forma) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(forma);
		return LocalDate.parse(fechaEnTexto,formatter);
	}
	
	public static void main(String[] args) {

		//Datos de prueba
		ArrayList<String> servicios = new ArrayList<>();
		servicios.add("servicio1");
		servicios.add("servicio2");
		servicios.add("servicio3");
		
		ArrayList<PedidoServicios> pedidoServicios = new ArrayList<PedidoServicios>();
		PedidoServicios pedido1 = new PedidoServicios("nombre1", 111111111, LocalDate.now(), convertirTextoALocalDate("01/12/2024", "dd/MM/yyyy"), servicios, "Infromación adicinal 1");
		PedidoServicios pedido2 = new PedidoServicios("nombre2", 222222222, LocalDate.now(), convertirTextoALocalDate("12/12/2024", "dd/MM/yyyy"), servicios, "Infromación adicinal 2");
		PedidoServicios pedido3 = new PedidoServicios("nombre3", 333333333, LocalDate.now(), convertirTextoALocalDate("25/12/2025", "dd/MM/yyyy"), servicios, "Infromación adicinal 3");
		
		pedidoServicios.add(pedido1);
		pedidoServicios.add(pedido2);
		pedidoServicios.add(pedido3);
		
		//Idioma
		String idioma1 = "Español";
		String idioma2 = "Ingles";
		String idioma3 = "Chino";
		
		System.out.println(pedidoServicios);
		
		//Probando con cada idioma
		new VentanaPedidoServicios(pedidoServicios,servicios,idioma1);
//		new VentanaPedidoServicios(pedidoServicios,servicios,idioma2);
//		new VentanaPedidoServicios(pedidoServicios,servicios,idioma3);
		
		
		
	}
	

}
