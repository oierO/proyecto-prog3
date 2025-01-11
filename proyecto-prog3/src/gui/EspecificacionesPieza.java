package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Pieza;

public class EspecificacionesPieza extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private String sTitulo;
	private JLabel idLabel,codigoLabel,nomPiezaLabel,descLabel,fabLabel,precioLabel,cantLabel;
	
	
	public EspecificacionesPieza(Pieza p,Locale locale) {
		super();
		
		//Idioma 
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("EspecificacionesPiezaBundle",currentLocale);
		sTitulo = bundle.getString("sTitulo");
		idLabel = new JLabel(bundle.getString("idLabel"));
		codigoLabel = new JLabel(bundle.getString("codigoLabel"));
		nomPiezaLabel = new JLabel(bundle.getString("nomPiezaLabel"));
		descLabel = new JLabel(bundle.getString("descLabel"));
		fabLabel = new JLabel(bundle.getString("fabLabel"));
		precioLabel = new JLabel(bundle.getString("precioLabel"));
		cantLabel = new JLabel(bundle.getString("cantLabel"));
		
		
		
		setTitle(sTitulo);
		setModal(true);
		setSize(600,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.WHITE);
		JPanel panel = new JPanel(new GridLayout(7, 3));
		Image imagen = new ImageIcon("resources/images/FlechaCoche.png").getImage();
		Image cambio = imagen.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon ajustada = new ImageIcon(cambio);
		panel.add(idLabel);
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(String.format("%d", p.getId())));
		panel.add(codigoLabel);
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getCodigo()));
		panel.add(nomPiezaLabel);
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getNombrePieza()));
		panel.add(descLabel);
		panel.add(new JLabel(ajustada));
		JLabel desc = new JLabel(p.getDescripcion());
		desc.setToolTipText(p.getDescripcion());
		panel.add(desc);
		panel.add(fabLabel);
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(p.getFabricante()));
		panel.add(precioLabel);
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(String.format("%.2f", p.getPrecio())));
		panel.add(cantLabel);
		panel.add(new JLabel(ajustada));
		panel.add(new JLabel(String.format("%d", p.getCantidadAlmacen())));
		getContentPane().add(panel, BorderLayout.CENTER);

		setVisible(true);

	}

}
