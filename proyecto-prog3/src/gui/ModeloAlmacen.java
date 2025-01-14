package gui;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;

import domain.Pieza;

public class ModeloAlmacen extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Pieza> lPiezas;
	private List<String> lTitulos;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private String sID,sCod,sNom,sDes,sFab,sPre,sCant;
	
	
	
	public ModeloAlmacen(List<Pieza> lPiezas,Locale locale) {
		super();
		this.lPiezas = lPiezas;
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("ModeloAlmacenBundle",currentLocale);
		sID =  bundle.getString("sID");
		sCod =  bundle.getString("sCod");
		sNom =  bundle.getString("sNom");
		sDes =  bundle.getString("sDes");
		sFab =  bundle.getString("sFab");
		sPre =  bundle.getString("sPre");
		sCant =  bundle.getString("sCant");
		lTitulos = Arrays.asList(sID, sCod, sNom, sDes,
				sFab, sPre,sCant );
		
	}

	@Override
	public void addRow(Object[] rowData) {
		Pieza p = new Pieza(Integer.parseInt((String) rowData[0].toString()), rowData[1].toString(),
				rowData[2].toString(), rowData[3].toString(), rowData[4].toString(),
				Float.parseFloat((String) rowData[5].toString()), Integer.parseInt((String) rowData[6].toString()));
		lPiezas.add(p);
	}

	@Override
	public void removeRow(int row) {
		// TODO Auto-generated method stub
		super.removeRow(row);
	}

	@Override
	public int getRowCount() {
		if (lPiezas == null)
			return 0;
		return lPiezas.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return lTitulos.size();
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return lTitulos.get(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Pieza p = lPiezas.get(row);
		switch (column) {
		case 0:
			return p.getId();
		case 1:
			return p.getCodigo();
		case 2:
			return p.getNombrePieza();
		case 3:
			return p.getDescripcion();
		case 4:
			return p.getFabricante();
		case 5:
			return p.getPrecio();
		case 6:
			return p.getCantidadAlmacen();
		default:
			return null;

		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		// TODO Auto-generated method stub

	}

}
