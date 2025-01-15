package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.Pieza;

public class Recibo_Compra extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private String sNombre,sApellido,sFecha,sCodigoPostal,sInfo,sPiezasCom,sCerrar;
	

	public Recibo_Compra(ArrayList<Pieza>compra,Locale locale) {
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("ReciboCompraBundle",currentLocale);
		sNombre = bundle.getString("sNombre");
		sApellido = bundle.getString("sApellido");
		sFecha = bundle.getString("sFecha");
		sCodigoPostal = bundle.getString("sCodigoPostal");
		sInfo = bundle.getString("sInfo");
		sPiezasCom = bundle.getString("sPiezasCom");
		sCerrar = bundle.getString("sCerrar");
		setSize(400, 400);
		JPanel clientePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        clientePanel.setBorder(BorderFactory.createTitledBorder(sInfo));
        
        clientePanel.add(new JLabel(sNombre));
        clientePanel.add(new JLabel(VentanaGrafica.getUsuario()));
        clientePanel.add(new JLabel(sApellido));
        clientePanel.add(new JLabel("Proximamente"));
        clientePanel.add(new JLabel(sFecha));
        clientePanel.add(new JLabel(LocalDateTime.now().toString()));
        clientePanel.add(new JLabel(sCodigoPostal));
        clientePanel.add(new JLabel("48993"));

        // Panel central con la lista de piezas compradas
        JPanel piezasPanel = new JPanel(new BorderLayout());
        piezasPanel.setBorder(BorderFactory.createTitledBorder(sPiezasCom));
        
        DefaultListModel<String> piezasModel = new DefaultListModel<>();
        for (Pieza pieza : compra) {
            piezasModel.addElement(pieza.toString());
        }
        JList<String> piezasList = new JList<>(piezasModel);
        JScrollPane scrollPane = new JScrollPane(piezasList);

        piezasPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón de cierre
        JButton cerrarButton = new JButton(sCerrar);
        cerrarButton.addActionListener(e -> dispose());

        // Agregar componentes al JDialog
        add(clientePanel, BorderLayout.NORTH);
        add(piezasPanel, BorderLayout.CENTER);
        add(cerrarButton, BorderLayout.SOUTH);
        
        setVisible(true);
	}
	
	public static void main(String[] args) {
		Locale curLocale= Locale.getDefault();
		
		new Recibo_Compra(null,curLocale);
	}
}

