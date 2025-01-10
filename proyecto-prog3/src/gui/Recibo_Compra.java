package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JDialog;

import domain.Pieza;

public class Recibo_Compra extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Recibo_Compra(ArrayList<Pieza>compra) {
		super();
		setTitle("Recibo de la compra");
		setModal(true);
		setSize(600,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		
		setVisible(true);
	}
	
}
