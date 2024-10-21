package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import domain.Pieza;

public class ModeloAlmacen extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JList<Pieza>lPiezas;
	private List<String>lTitulos= Arrays.asList("ID","Código de Pieza","Nombre de Pieza","Descripción","Fabricante","Precio (€)","Cantidad en Almacén");

	public ModeloAlmacen(JList<Pieza> lPiezas) {
		super();
		this.lPiezas = lPiezas;
	}
	
	

}
