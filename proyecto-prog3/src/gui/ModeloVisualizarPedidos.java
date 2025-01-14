package gui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;

import domain.PedidoServicios;

public class ModeloVisualizarPedidos  extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sNombre,sTelefono,sFechaDePedido,sFechaDeRealizacion,sServiciosElegidos,sInfromacionAdicional; 
	private ArrayList<PedidoServicios> listaServiciosPedidos;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private List<String> titulo ;
	
	
	public ModeloVisualizarPedidos(ArrayList<PedidoServicios> listaServiciosPedidos,
			 Locale locale) {
		
		//Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("VentanaVisualizarPedidosBundle", currentLocale);
		sNombre = bundle.getString("sNombre");
		sTelefono = bundle.getString("sTelefono");
		sFechaDePedido = bundle.getString("sFechaDePedido");
		sFechaDeRealizacion = bundle.getString("sFechaDeRealizacion");
		sServiciosElegidos = bundle.getString("sServiciosElegidos");
		sInfromacionAdicional = bundle.getString("sInfromacionAdicional");
		
		titulo = Arrays.asList(sNombre,sTelefono,sFechaDePedido,sFechaDeRealizacion,sServiciosElegidos,sInfromacionAdicional);
		this.listaServiciosPedidos = listaServiciosPedidos;
		
	}
	
	
	@Override
	public int getRowCount() {
		if (listaServiciosPedidos == null)
			return 0;
		return listaServiciosPedidos.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titulo.size();
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titulo.get(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		PedidoServicios p = listaServiciosPedidos.get(row);
		switch (column) {
		case 0:
			return p.getNombre();
		case 1:
			return p.getTelefono();
		case 2:
			return p.getfechaDePedido();
		case 3:
			return p.getfechaDeRealizacion();
		case 4:
			return p.getServiciosElegidos();
		case 5:
			return p.getinformacionAdicional();
		default:
			return null;

		}
	}

}
