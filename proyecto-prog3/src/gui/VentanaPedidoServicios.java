package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import domain.PedidoServicios;

public class VentanaPedidoServicios extends JFrame {

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
	private JLabel nombreJLabel;
	private JLabel telefonoJLabel;
	private JLabel fechaDePedidoJLabel;
	private JLabel fechaDeRealizacionJLabel;
	private JLabel informacionAdicionalJLabel;
	private JButton botonCancelar;
	private Locale curreLocale;
	private ResourceBundle idiomaBundle;
	private String sTitulo;

	public VentanaPedidoServicios(String usuario, ArrayList<PedidoServicios> listaServiciosPedidos,
			ArrayList<String> serviciosElegidos, Locale locale) {

//      //Los labels
		curreLocale = locale;
		idiomaBundle = ResourceBundle.getBundle("VentanaLabelBundle", curreLocale);
      
      	nombreJLabel = new JLabel(idiomaBundle.getString("nombreJLabel"));
		telefonoJLabel = new JLabel(idiomaBundle.getString("telefonoJLabel"));
		fechaDePedidoJLabel = new JLabel(idiomaBundle.getString("fechaDePedidoJLabel"));
		fechaDeRealizacionJLabel = new JLabel(idiomaBundle.getString("fechaDeRealizacionJLabel"));
		informacionAdicionalJLabel = new JLabel(idiomaBundle.getString("informacionAdicionalJLabel"));
		sTitulo = idiomaBundle.getString("sTitulo");
		
		// Configuraciones de la ventana
		setTitle(sTitulo);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2));


        

        
        JPanel panelIdiomaJPanel = new JPanel();
        
        String[] idiomas = {"Español","English","中文" };
        
        JComboBox<String> comboBox = new JComboBox<>(idiomas);
        
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la opción seleccionada
                String seleccion = (String) comboBox.getSelectedItem();
                System.out.println("Seleccionaste: " + seleccion);
               
                if(seleccion.equals("Español")) {
                	curreLocale = Locale.getDefault();
                } else if (seleccion.equals("English")) {
                	curreLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
				} else if (seleccion.equals("中文")) {
					curreLocale = new Locale.Builder().setLanguage("zh").setRegion("ZH").build();
				}
                
                updateTexts();
                
            }
        });
        
        panelIdiomaJPanel.add(comboBox);
        
        panel.add(panelIdiomaJPanel);
        JLabel labelVacio = new JLabel();
        panel.add(labelVacio);
        

        
        
		// Para el nombre
		nombre = new JTextField(usuario);
		nombre.setEditable(false);
		panel.add(nombreJLabel);
		panel.add(nombre);

		// Para el telefono
		panel.add(telefonoJLabel);
		DecimalFormat formatoVisual = new DecimalFormat("#########");
		NumberFormatter formatoEntrada = new NumberFormatter(formatoVisual) {

			private static final long serialVersionUID = 1L;

			@Override
			public Object stringToValue(String text) throws ParseException {

				if (text == null || text.length() == 0) {
					return null;
				}

				if (text.length() > 9) {
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

		// Para la fecha del pedido
		LocalDate fechaActual = LocalDate.now();
		System.out.println(fechaActual);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String fechaFormateado = fechaActual.format(formato);
		fechaDePedido = new JFormattedTextField();
		fechaDePedido.setText("" + fechaFormateado);
		fechaDePedido.setEditable(false);
		panel.add(fechaDePedidoJLabel);
		panel.add(fechaDePedido);

		// Para la fecha de realización
		fechaDeRealizacion = crearFechaFormateado("##/##/####");
		panel.add(fechaDeRealizacionJLabel);
		panel.add(fechaDeRealizacion);

		// Para el información adicional
		informacionAdicional = new JTextArea();
		informacionAdicional.setRows(3);
		informacionAdicional.setColumns(30);
		informacionAdicional.setLineWrap(true);

		panel.add(informacionAdicionalJLabel);
		panel.add(informacionAdicional);

		// para reservar
		botonReservar = new JButton("Reservar");
		botonReservar.addActionListener(new ActionListener() {
			// Esto no cambia según el idioma
			// Solo estoy sacando por consola lo que ha rellenado el usuario en el
			// formulario
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("\n---Esto es de VentanaPedidoServicios---\n");
				System.out.println("Nombre: " + nombre.getText());
				System.out.println("Telefono: " + telefono.getText());
				System.out.println("Fecha de pedido: " + fechaDePedido.getText());
				System.out.println("Fecha de realización: " + fechaDeRealizacion.getText());
				System.out.println("Información adicional: " + informacionAdicional.getText());

				LocalDate fecha1 = convertirTextoALocalDate(fechaDeRealizacion.getText(), "dd/MM/yyyy");

				// Compruebo si los campos importantes están rellenados
				if (nombre.getText().isEmpty() || telefono.getText().isEmpty()
						|| fechaDeRealizacion.getText().isEmpty()) {
					System.out.println("\n--Mensaje de error--\n");
					System.out.println("Error: Datos incompletos");

					// Compruebo si la fecha que mete el usuario es valida
				} else if (fecha1.isBefore(fechaActual)
						&& (telefono.getText().length() < 9 || telefono.getText().charAt(0) != '6')) {
					System.out.println("\n--Mensaje de error--\n");
					System.out.println("Error: La fecha y el telefono introducidos no son correctos.");

					// Compruebo si el telefono introducido es correcto
				} else if (telefono.getText().length() < 9 || telefono.getText().charAt(0) != '6') {
					System.out.println("\n--Mensaje de error--\n");
					System.out.println("Error: El telefono introducido no es válido.");

				} else if (fecha1.isBefore(fechaActual)) {
					System.out.println("\n--Mensaje de error--\n");
					System.out.println("Error: La fecha introducida no es válida.");

				} else {
					PedidoServicios pedido = new PedidoServicios(nombre.getText(), Integer.parseInt(telefono.getText()),
							fechaActual, fecha1, serviciosElegidos, informacionAdicional.getText());
					listaServiciosPedidos.add(pedido);
					System.out.println("\n--Pedido añadido--\n");
					System.out.println(listaServiciosPedidos);
				}
				dispose();
			}
		});

		panel.add(botonReservar);

		// boton cancelar
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(e -> dispose());

		panel.add(botonCancelar);

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

	private static LocalDate convertirTextoALocalDate(String fechaEnTexto, String forma) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(forma);
		return LocalDate.parse(fechaEnTexto, formatter);
	}
	
	private void updateTexts() {
		idiomaBundle = ResourceBundle.getBundle("VentanaLabelBundle", curreLocale);
		nombreJLabel.setText(idiomaBundle.getString("nombreJLabel"));
		telefonoJLabel.setText(idiomaBundle.getString("telefonoJLabel"));
		fechaDePedidoJLabel.setText(idiomaBundle.getString("fechaDePedidoJLabel"));
		fechaDeRealizacionJLabel.setText(idiomaBundle.getString("fechaDeRealizacionJLabel"));
		informacionAdicionalJLabel.setText(idiomaBundle.getString("informacionAdicionalJLabel"));
    }

	public static void main(String[] args) {

		// Datos de prueba
		ArrayList<String> servicios = new ArrayList<>();
		servicios.add("servicio1");
		servicios.add("servicio2");
		servicios.add("servicio3");

		ArrayList<PedidoServicios> pedidoServicios = new ArrayList<PedidoServicios>();
		PedidoServicios pedido1 = new PedidoServicios("nombre1", 111111111, LocalDate.now(),
				convertirTextoALocalDate("01/12/2024", "yyyy/MM/dd"), servicios, "Infromación adicinal 1");
		PedidoServicios pedido2 = new PedidoServicios("nombre2", 222222222, LocalDate.now(),
				convertirTextoALocalDate("2024/12/12", "yyyy/MM/dd"), servicios, "Infromación adicinal 2");
		PedidoServicios pedido3 = new PedidoServicios("nombre3", 333333333, LocalDate.now(),
				convertirTextoALocalDate("2025/12/15", "yyyy/MM/dd"), servicios, "Infromación adicinal 3");

		pedidoServicios.add(pedido1);
		pedidoServicios.add(pedido2);
		pedidoServicios.add(pedido3);

		// Idioma
		String idioma1 = "Español";
		@SuppressWarnings("unused")
		String idioma2 = "Ingles";
		@SuppressWarnings("unused")
		String idioma3 = "Chino";

		System.out.println(pedidoServicios);
		Locale locale = Locale.getDefault();

		// Probando con cada idioma
		new VentanaPedidoServicios("Usuario", pedidoServicios, servicios, locale);
//		new VentanaPedidoServicios("Usuario",pedidoServicios,servicios,idioma2);
//		new VentanaPedidoServicios("Usuario",pedidoServicios,servicios,idioma3);

	}

}
