package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.invoke.StringConcatFactory;
import java.rmi.server.Operation;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaGrafica() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();

		// Pestaña Servicios
		String[] lServicios = new String[] { "Taller", "Piezas", "Diagnóstico" };
		JPanel pServicios = new JPanel();
		pServicios.setLayout(new BorderLayout());
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(lServicios.length,1));
		botones.setBorder(new TitledBorder("Operaciones"));
		pServicios.add(botones, BorderLayout.WEST);
		for (int i = 0; i < lServicios.length; i++) {
			JButton boton = new JButton(lServicios[i]);
			boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String operacion =  boton.getText();
					if(operacion.equals("Diagnóstico")) {
						JCheckBox checkBox1 = new JCheckBox("Motor");
						JCheckBox checkBox2 = new JCheckBox("Chapa");
						JCheckBox checkBox3 = new JCheckBox("Retrovisor");
						JCheckBox checkBox4 = new JCheckBox("Otros");
						
						JPanel panelDiagnostico = new JPanel();
						Border panelDiagnosticoBorder = BorderFactory.createTitledBorder("¿Qué problema tiene?");
						panelDiagnostico.setBorder(panelDiagnosticoBorder);
						panelDiagnostico.add(checkBox1);
						panelDiagnostico.add(checkBox2);
						panelDiagnostico.add(checkBox3);
						panelDiagnostico.add(checkBox4);
						      
						JButton botonReservar = new JButton("RESERVAR CITA");
						botonReservar.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								ArrayList<String> listaDiagnosticoSeleccionado = new ArrayList<String>();
								if(checkBox1.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox1.getText());
								}
								if(checkBox2.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox2.getText());
								}
								if(checkBox3.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox3.getText());
								}
								if(checkBox4.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox4.getText());
								}
								
								if( !listaDiagnosticoSeleccionado.isEmpty()) {
									System.out.println("El usuario ha seleccionado estos diagnósticos: " );
									for(String diagnostico:listaDiagnosticoSeleccionado) {
										System.out.println(diagnostico);
									}
								} else {
									System.out.println("El usuario no ha seleccionado ningun diagnóstico");
								}
								
								
								
								
							}
						});
						
						
						
						JPanel panelReservar = new JPanel();
						
				        panelReservar.add(botonReservar);
						
						pServicios.add(panelDiagnostico,BorderLayout.CENTER);
						pServicios.add(panelReservar,BorderLayout.EAST);
						
						
					}
					
					
					
					
					
					
					
					
					
					
				}
			}); // TODO ~
																									// implementar
																									// lógica
			botones.add(boton);

		}
		// Pestaña Almacen
		JPanel pAlmacen = new JPanel();

		// Pestaña Parking
		JPanel pParking = new JPanel();

		// Pestaña Preferencias
		JPanel pSettings = new JPanel();
		
		JPanel pUsuario = new PanelSesion();

		menuPestanas.add("Servicios", pServicios);
		menuPestanas.add("Almacen", pAlmacen);
		menuPestanas.add("Parking", pParking);
		menuPestanas.add("Preferencias", pSettings);
		menuPestanas.add("Sesión",pUsuario);
		add(menuPestanas);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				VentanaInicioSesion.logout();
			}
			
		});
		setIconImage(new ImageIcon(getClass().getResource("/res/app-icon.png")).getImage());
		setVisible(true);
	}
}
