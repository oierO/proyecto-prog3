package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;


import domain.PedidoServicios;
import main.DeustoTaller;

public class VentanaVisualiazarPedidos extends JFrame {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pedidosActuales;
	private ArrayList<PedidoServicios> list;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private JButton botonGuardar,botonBorrar;
	private static JTable tablaPedidos;
	private ModeloVisualizarPedidos modeloVisualizarPedidos;
	private static String sSeguro,sConfi,sPrimeroDebesFila,sErrorEnSeleccion;
	

	public VentanaVisualiazarPedidos (String usuario, ArrayList<PedidoServicios> listaServiciosPedidos,
			 Locale locale) {
		
		//idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("VentanaVisualizarPedidosBundle", currentLocale);
		pedidosActuales = bundle.getString("pedidosActuales");
		sSeguro = bundle.getString("sSeguro");
		sConfi = bundle.getString("sConfi");
		sPrimeroDebesFila = bundle.getString("sPrimeroDebesFila");
		sErrorEnSeleccion = bundle.getString("sErrorEnSeleccion");
		
		this.list = listaServiciosPedidos;
		
		// Configuraciones de la ventana
		setTitle(pedidosActuales+usuario);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		

		JPanel panelBotones = new JPanel();
		//Tablas

		
		modeloVisualizarPedidos = new ModeloVisualizarPedidos(list, currentLocale);
		tablaPedidos = new JTable(modeloVisualizarPedidos);
		
		

		
		//Botones
		botonGuardar = new JButton(bundle.getString("botonGuardar"));
		botonBorrar = new JButton(bundle.getString("botonBorrar"));
		
		botonBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null, sSeguro,
						sConfi, JOptionPane.YES_NO_OPTION);
				if(confirmacion==JOptionPane.YES_OPTION) {
					borrarDeLaLista(list);
					repaint();
				} 
				
			}
		});
		
		botonGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null, sSeguro,
						sConfi, JOptionPane.YES_NO_OPTION);
				if(confirmacion==JOptionPane.YES_OPTION) {
					System.out.println("Guardar");
				} 
				
			}
		});
		
		
		this.add(tablaPedidos,BorderLayout.CENTER);
		panelBotones.add(botonGuardar);
		panelBotones.add(botonBorrar);
		this.add(panelBotones,BorderLayout.SOUTH);
		this.setVisible(true);
		
		
		
		
	}
	
	
	private static void borrarDeLaLista(ArrayList<PedidoServicios> listaServiciosPedidos) {
		int fila = tablaPedidos.getSelectedRow();
		if(fila==-1) {
			JOptionPane.showMessageDialog(null, sPrimeroDebesFila,
					sErrorEnSeleccion, JOptionPane.ERROR_MESSAGE);
		} else {
			listaServiciosPedidos.remove(fila);
		}
		
	}
	
	private static void guardarLaLista(ArrayList<PedidoServicios> listaServiciosPedidos) {
		
		try {
			Connection conn = DeustoTaller.getCon();
			String insertSQL = "INSERT INTO PEDIDOS () VALUES (?,?,?,?,?)";
			
			
			PreparedStatement preparedStmt = conn.prepareStatement(insertSQL);
			for(PedidoServicios p:listaServiciosPedidos) {
				
		   
			}
			
			
		} catch (Exception e) {
			
			System.out.println("Error en guardar la lista");
			e.printStackTrace();
		}
		
		
		
	}
	
	private static LocalDate convertirTextoALocalDate(String fechaEnTexto, String forma) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(forma);
		return LocalDate.parse(fechaEnTexto, formatter);
	}
	
	public static void main(String[] args) {
		
		Locale cuLocale=Locale.getDefault();
		ArrayList<String> servicios = new ArrayList<>();
		servicios.add("servicio1");
		servicios.add("servicio2");
		servicios.add("servicio3");

		ArrayList<PedidoServicios> pedidoServicios = new ArrayList<PedidoServicios>();
		PedidoServicios pedido1 = new PedidoServicios("nombre1", 111111111, LocalDate.now(),
				convertirTextoALocalDate("2024/12/01", "yyyy/MM/dd"), servicios, "Infromación adicinal 1");
		PedidoServicios pedido2 = new PedidoServicios("nombre2", 222222222, LocalDate.now(),
				convertirTextoALocalDate("2024/12/12", "yyyy/MM/dd"), servicios, "Infromación adicinal 2");
		PedidoServicios pedido3 = new PedidoServicios("nombre3", 333333333, LocalDate.now(),
				convertirTextoALocalDate("2025/12/15", "yyyy/MM/dd"), servicios, "Infromación adicinal 3");
		pedidoServicios.add(pedido1);
		pedidoServicios.add(pedido2);
		pedidoServicios.add(pedido3);
		
		System.out.println(pedidoServicios);
		
		new VentanaVisualiazarPedidos("Patata",  pedidoServicios, cuLocale );
	}
	
}
