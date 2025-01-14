package gui;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

import domain.PedidoServicios;

public class VentanaVisualiazarPedidos extends JFrame {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pedidosActuales;
	private ArrayList<PedidoServicios> listaServiciosPedidos;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private JButton botonGuardar,botonBorrar;
	private JTable tablaPedidos;
	private ModeloVisualizarPedidos modeloVisualizarPedidos;
	

	public VentanaVisualiazarPedidos (String usuario, ArrayList<PedidoServicios> listaServiciosPedidos,
			 Locale locale) {
		
		//idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("VentanaVisualizarPedidosBundle", currentLocale);
		
		
		// Configuraciones de la ventana
		setTitle(pedidosActuales+usuario);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JScrollPane panelCentral = new JScrollPane();
		//Tablas
		this.listaServiciosPedidos = listaServiciosPedidos;
		modeloVisualizarPedidos = new ModeloVisualizarPedidos(this.listaServiciosPedidos, locale);
		tablaPedidos = new JTable(modeloVisualizarPedidos);
		
		
		panelCentral.add(tablaPedidos);
		
		this.add(panelCentral);
		this.setVisible(true);
		
		
		
		
	}
}
