package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Pieza;

public class EspecificacionesPieza extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EspecificacionesPieza(Pieza p) {
		super();
		setTitle("Especificaciones de las piezas");
		setModal(true);
		setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel panel= new JPanel(new GridLayout(7, 3));
		ImageIcon imagen= new ImageIcon("resources/images/FlechaCoche.png");
		Image cambio= imagen.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon ajustada= new ImageIcon(cambio);
		panel.add(new JLabel("ID:"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(String.format("%d", p.getId())));
		panel.add(new JLabel("CODIGO:"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getCodigo()));
		panel.add(new JLabel("NOMBRE PIEZA:"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getNombrePieza()));
		panel.add(new JLabel("DESCRIPCION"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getDescripcion()));
		panel.add(new JLabel("FABRICANTE"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getFabricante()));
		panel.add(new JLabel("PRECIO EN EUROS"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(String.format("%.2f", p.getPrecio())));
		panel.add(new JLabel("CANTIDAD EN ALMACEN:"));
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(String.format("%d", p.getCantidadAlmacen())));
		
		
		
		
		
		
		
			
		
		getContentPane().add(panel,BorderLayout.CENTER);
		
		
		
		
		
		
		
		
		
		
		
		setVisible(true);
		
	}
	
	

}
