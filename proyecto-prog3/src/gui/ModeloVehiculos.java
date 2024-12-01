package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import domain.Vehiculo;

public class ModeloVehiculos extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vehiculo>lVehiculos;
	private List<String>lTitulos= Arrays.asList("Matricula","Marca","Modelo","Año Modelo","Servicios Contratados");
	
	public ModeloVehiculos(List<Vehiculo> lVehiculos) {
		super();
		this.lVehiculos = lVehiculos;
	}
	@Override
	public void addRow(Object[] rowData) {
		//Vehiculo v = new Vehiculo(rowData[0].toString(),rowData[1].toString(),rowData[2].toString(),rowData[3].toString(),rowData[4].toString());
		//lVehiculos.add(v);
	}

	@Override
	public void removeRow(int row) {
		// TODO Auto-generated method stub
		super.removeRow(row);
	}

	@Override
	public int getRowCount() {
		if (lVehiculos == null)
			return 0;
		return lVehiculos.size();
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
		Vehiculo v = lVehiculos.get(row);
		switch (column) {
		case 0:
			return v.getMatricula();
		case 1:
			return v.getMarca();
		case 2:
			return v.getModelo();
		case 3:
			return v.getAnoModelo();
		case 4:
			return v.getServiciosContratados();
		default:
			return null;

		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		// TODO Auto-generated method stub

	}
	
	

}
