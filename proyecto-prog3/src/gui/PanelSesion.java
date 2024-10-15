package gui;

import java.awt.BorderLayout;

import javax.swing.*;

import main.DeustoTaller;
public class PanelSesion extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelSesion() {
		JLabel tEncabezado = new JLabel(String.format("¡Hola, %s!", DeustoTaller.getSesion().getNombre()));
		add(tEncabezado,BorderLayout.NORTH);
		JLabel tSesion = new JLabel(String.format("Ultimo inicio de sesión: %s", DeustoTaller.getSesion().getfUltimaSesion()));
		add(tSesion,BorderLayout.NORTH);
		JButton bAdminSesion= new JButton("Acceder al gestor de sesión");
		//bAdminSesion.addActionListener(e-> DeustoTaller.getVSesion().openGestor());
		add(bAdminSesion,BorderLayout.CENTER);
		
	}
}
